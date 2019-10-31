package com.cartmell.travis.tcartmelllab7;

import android.animation.TimeAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;

public class AnalogClock extends android.view.View implements TimeAnimator.TimeListener , MediaPlayer.OnCompletionListener {

    int w;
    int h;
    int diameter = 16;
    String type = "None";
    Boolean check_time_24 = false;
    Boolean check_partial_seconds = false;
    Path minPath = new Path();
    Path hourPath = new Path();
    Paint paint = new Paint();
    Bitmap regularClock = BitmapFactory.decodeResource(getResources(), R.drawable.regular);
    Bitmap romanClock = BitmapFactory.decodeResource(getResources(), R.drawable.roman);
    Bitmap pictureClock = BitmapFactory.decodeResource(getResources(), R.drawable.picture);
    RectF location = new RectF(-8,-8,8,8);
    TimeAnimator timer = new TimeAnimator();
    MediaPlayer mp;//= MediaPlayer.create(getContext(), R.raw.click);
    int mClipID = -1;
    boolean isStop = false;
    private static long TIMER_MSEC = 1000;
    private static long TIMER_SEC = System.currentTimeMillis()/1000;
    long mLastTime = System.currentTimeMillis();
    AnalogClock.AnalogClockObservable test = new AnalogClockObservable();

    static final float[] minuteVerts = {0,0,.15f,.15f,7f,0,.15f,-.15f};
    static final float[] hourVerts = {0,0,.3f,.3f,5f,0,.3f,-.3f};

    public AnalogClock (Context context) {
        super(context);
        init(context);
        //mp.setVolume(0.6f,0.6f);
    }

    public AnalogClock (Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        //mp.setVolume(0.6f,0.6f);
    }

    public AnalogClock (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
        //mp.setVolume(0.6f,0.6f);
    }

    private void init(Context context) {
        test.addObserver((Observer)context);
        timer.setTimeListener(this);
        timer.start();
        setLayerType(this.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    public void onTimeUpdate(TimeAnimator timeAnimator, long l, long l1) {
        invalidate();
        if (isStop)
            return;
        long now = System.currentTimeMillis() ;
        if ((now-mLastTime)<TIMER_MSEC)
            return ;
        mLastTime = now ;
        playClip(R.raw.click);
    }

    public void setisStop() {
        isStop = false;
    }

    public MediaPlayer getMp() {
        return mp;
    }

    public void stopMe() {
        if (mp != null) {
            mp.stop();
            mp.release();
        }
        isStop = true;
        Log.e("******", "stop");
        //timer.pause();
        mp = null;
    }

    public void playMe() {
        mp.start();
    }

    private void playClip(int id) {
        if (mp!=null && id==mClipID) {
            mp.pause();
            mp.seekTo(0);
            mp.start();
        }
        else {
            if (mp!=null) mp.release() ;
            mClipID = id ;
            mp = MediaPlayer.create(getContext(), id) ;
            mp.setOnCompletionListener(this) ;
            mp.setVolume(0.6f,0.6f) ;
            mp.start() ;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int realHeight = MeasureSpec.getSize(heightMeasureSpec);
        int realWidth = MeasureSpec.getSize(widthMeasureSpec);
        double w = 18;
        double h = 18;
        double widthRatio = (w/h) * realHeight;
        double heightRatio = (h/w) * realWidth;

        double high = Math.min(widthRatio * realHeight, heightRatio * realWidth);

        if (high == widthRatio * realHeight) {
            setMeasuredDimension((int) widthRatio, realHeight);
        }

        else {
            setMeasuredDimension( realWidth, (int) heightRatio);
        }
    }

    public void set(String type, Boolean check_time_24, Boolean check_partial_seconds){
        this.type = type;
        this.check_time_24 = check_time_24;
        this.check_partial_seconds = check_partial_seconds;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRGB( 255,255,255);
        canvas.translate((float) w/2, (float) h/2);
        float width = (float) w/16;
        float height = (float) h/16;
        Calendar cal = new GregorianCalendar();
        float mil = cal.get(Calendar.MILLISECOND);
        float sec = cal.get(Calendar.SECOND);
        float min = cal.get(Calendar.MINUTE);
        float hour = cal.get(Calendar.HOUR_OF_DAY);
        float hour_12 = cal.get(Calendar.HOUR);
        float secDegrees = sec * 6 - 90 + (mil/1000f) * 6;
        float minDegrees = min * 6 - 90 + (sec/10);
        float hourDegrees = hour * 30 - 90  + (min/2);
        mil = mil/100;

        canvas.scale(width, -height);
        paint.setARGB(255, 0, 0, 0);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(.05f);

        if (type.compareTo("None") == 0) {
            canvas.drawCircle(0, 0, (float) diameter/2, paint);
            canvas.save();
            for (int i = 0; i < 60; i++) {
                if (i % 5 != 0)
                    canvas.drawLine(8f,0,8f-(8f/50f),0,paint);
                else
                    canvas.drawLine(8f,0,8f-(8f/11f),0,paint);
                canvas.rotate(6);
            }
            canvas.restore();
        }
        else if (type.compareTo("Regular") == 0) {
            canvas.save();
            canvas.scale(1,-1);
            canvas.drawBitmap(regularClock, null, location, null);
            canvas.restore();
        }
        else if (type.compareTo("Roman") == 0) {
            canvas.save();
            canvas.scale(1,-1);
            canvas.drawBitmap(romanClock, null, location, null);
            canvas.restore();
        }
        else if (type.compareTo("Picture") == 0){
            canvas.save();
            canvas.scale(1,-1);
            canvas.drawBitmap(pictureClock, null, location, null);
            canvas.restore();

            canvas.save();
            for (int i = 0; i < 60; i++) {
                if (i % 5 != 0)
                    canvas.drawLine(8f,0,8f-(8f/50f),0,paint);
                else
                    canvas.drawLine(8f,0,8f-(8f/11f),0,paint);
                canvas.rotate(6);
            }
            canvas.restore();
        }

        paint.setARGB(255,0,0,0);
        paint.setStrokeWidth(.05f);

        canvas.save();
        canvas.rotate(-secDegrees);
        canvas.drawLine(0, 0, 7.8f, 0, paint);
        canvas.restore();

        minPath.moveTo(minuteVerts[0], minuteVerts[1]);
        minPath.lineTo(minuteVerts[2], minuteVerts[3]);
        minPath.lineTo(minuteVerts[4], minuteVerts[5]);
        minPath.lineTo(minuteVerts[6], minuteVerts[7]);
        minPath.close();

        paint.reset();

        canvas.save();
        canvas.rotate(-minDegrees);
        canvas.drawPath(minPath, paint);
        canvas.restore();

        hourPath.moveTo((hourVerts[0]), hourVerts[1]);
        hourPath.lineTo((hourVerts[2]), hourVerts[3]);
        hourPath.lineTo((hourVerts[4]), hourVerts[5]);
        hourPath.lineTo((hourVerts[6]), hourVerts[7]);

        canvas.save();
        canvas.rotate(-hourDegrees);
        canvas.drawPath(hourPath, paint);
        canvas.restore();

        if (!check_partial_seconds && !check_time_24)
            test.set(String.valueOf((int)hour_12) + ":" + String.valueOf((int)min) + ":" + String.valueOf((int)sec));
        else if (!check_partial_seconds && check_time_24)
            test.set(String.valueOf((int)hour) + ":" + String.valueOf((int)min) + ":" + String.valueOf((int)sec));
        else if (check_partial_seconds && !check_time_24)
            test.set(String.valueOf((int)hour) + ":" + String.valueOf((int)min) + ":" + String.valueOf((int)sec) + "." + String.valueOf((int)mil));
        else
            test.set(String.valueOf((int)hour_12) + ":" + String.valueOf((int)min) + ":" + String.valueOf((int)sec) + "." + String.valueOf((int)mil));

        super.onDraw(canvas);
    }

    @Override
    public void onCompletion(MediaPlayer amp) {
        amp.release() ;
        mp = null ;
    }

    public class AnalogClockObservable extends Observable {
        String val;
        void set(String time) {
            val = time;
            setChanged();
            notifyObservers();
        }
         String get() {
            return val;
        }
    }

}
