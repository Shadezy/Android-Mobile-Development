package com.cartmell.travis.tcartmelllab5;

import android.animation.TimeAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;

public class AnalogClock extends android.view.View implements TimeAnimator.TimeListener {

    int w;
    int h;
    int diameter = 16;
    Path minPath = new Path();
    Path hourPath = new Path();
    Paint paint = new Paint();
    TimeAnimator timer = new TimeAnimator();
    AnalogClock.AnalogClockObservable test = new AnalogClockObservable();

    static final float[] minuteVerts = {0,0,.15f,.15f,7f,0,.15f,-.15f};
    static final float[] hourVerts = {0,0,.3f,.3f,5f,0,.3f,-.3f};

    public AnalogClock (Context context) {
        super(context);
        test.addObserver((Observer)context);
        timer.setTimeListener(this);
        timer.start();
    }

    public AnalogClock (Context context, AttributeSet attrs) {
        super(context, attrs);
        test.addObserver((Observer)context);
        timer.setTimeListener(this);
        timer.start();
    }

    public AnalogClock (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        test.addObserver((Observer)context);
        timer.setTimeListener(this);
        timer.start();
    }

    @Override
    public void onTimeUpdate(TimeAnimator timeAnimator, long l, long l1) {
        invalidate();
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

    @Override
    protected void onDraw(Canvas canvas) {
        setLayerType(this.LAYER_TYPE_SOFTWARE, null);
        canvas.drawRGB( 255,255,255);
        canvas.translate((float) w/2, (float) h/2);
        float width = (float) w/16;
        float height = (float) h/16;
        Calendar cal = new GregorianCalendar();
        float sec = cal.get(Calendar.SECOND);
        float min = cal.get(Calendar.MINUTE);
        float hour = cal.get(Calendar.HOUR_OF_DAY);
        float secDegrees = sec * 6 - 90;
        float minDegrees = min * 6 - 90 + (sec/10);
        float hourDegrees = hour * 30 - 90  + (min/2);

        test.set(String.valueOf((int)hour) + ":" + String.valueOf((int)min) + ":" + String.valueOf((int)sec));
        canvas.scale(width, -height);
        paint.setARGB(255, 0, 0, 0);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(.05f);
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

        super.onDraw(canvas);
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
