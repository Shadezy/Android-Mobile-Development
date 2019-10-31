package com.cartmell.travis.tcartmelllab4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;

public class SevenSegment extends android.view.View {
    int displayedValue = 10;
    boolean[] onState = {false,false,false,false,false,false,false};
    int w;
    int h;
    Path path = new Path();
    Paint paint = new Paint();
    float[] verts = {2, 2, 3, 1, 13, 1, 14, 2, 13, 3, 3, 3};
    final int[][] sets = {{1,2,3,4,5,6},{2,3},{1,2,4,5,7},{1,2,3,4,7},{2,3,6,7},{1,3,4,6,7},{1,3,4,5,6,7},{1,2,3},{1,2,3,4,5,6,7},{1,2,3,4,6,7},{}};

    public SevenSegment (Context context) {
        super(context);
    }

    public SevenSegment (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SevenSegment (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void set (int val) {
        if (val < 0 || val > 10)
            return;

        displayedValue = val;

        for (int i = 0; i < onState.length; i++)
            onState[i] = false;

        for(int i = 0; i < sets[val].length; i++)
            onState[sets[val][i]-1] = true;
    }

    public int get () {
        return displayedValue;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int realHeight = MeasureSpec.getSize(heightMeasureSpec);
        int realWidth = MeasureSpec.getSize(widthMeasureSpec);
        double w = 16;
        double h = 28;
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
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putBooleanArray("onState", onState);
        bundle.putInt("displayedValue", displayedValue);

        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle bundle = (Bundle) state;
        onState = bundle.getBooleanArray("onState");
        displayedValue = bundle.getInt("displayedValue");

        state = bundle.getParcelable("instanceState");
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setLayerType(this.LAYER_TYPE_SOFTWARE, null);
        canvas.drawRGB( 0,0,0);
        paint.setARGB(50,255,0,0);
        float width = (float) w/16;
        float height = (float) h/28;
        canvas.scale(width, height);
        paint.setStyle(Paint.Style.FILL);

        path.moveTo(verts[0], verts[1]);
        path.lineTo(verts[2], verts[3]);
        path.lineTo(verts[4], verts[5]);
        path.lineTo(verts[6], verts[7]);
        path.lineTo(verts[8], verts[9]);
        path.lineTo(verts[10], verts[11]);
        path.close();

        drawSeg1(canvas);
        drawSeg2(canvas);
        drawSeg3(canvas);
        drawSeg4(canvas);
        drawSeg5(canvas);
        drawSeg6(canvas);
        drawSeg7(canvas);

        paint.setARGB(255,255,0,0);

        if (onState[0])
            drawSeg1(canvas);
        if (onState[1])
            drawSeg2(canvas);
        if (onState[2])
            drawSeg3(canvas);
        if (onState[3])
            drawSeg4(canvas);
        if (onState[4])
            drawSeg5(canvas);
        if (onState[5])
            drawSeg6(canvas);
        if (onState[6])
            drawSeg7(canvas);

        super.onDraw(canvas);
    }

    private void drawSeg1 (Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    private void drawSeg2 (Canvas canvas) {
        canvas.save();
        canvas.translate(16,0);
        canvas.rotate(90);
        canvas.drawPath(path, paint);
        canvas.restore();
    }

    private void drawSeg3 (Canvas canvas) {
        canvas.save();
        canvas.translate(16,12);
        canvas.rotate(90);
        canvas.drawPath(path, paint);
        canvas.restore();
    }

    private void drawSeg4 (Canvas canvas) {
        canvas.save();
        canvas.translate(0,24);
        canvas.drawPath(path, paint);
        canvas.restore();
    }

    private void drawSeg5 (Canvas canvas) {
        canvas.save();
        canvas.translate(4, 12);
        canvas.rotate(90);
        canvas.drawPath(path, paint);
        canvas.restore();
    }

    private void drawSeg6 (Canvas canvas) {
        canvas.save();
        canvas.translate(4, 0);
        canvas.rotate(90);
        canvas.drawPath(path, paint);
        canvas.restore();
    }

    private void drawSeg7 (Canvas canvas) {
        canvas.save();
        canvas.translate(0,12);
        canvas.drawPath(path, paint);
        canvas.restore();
    }
}
