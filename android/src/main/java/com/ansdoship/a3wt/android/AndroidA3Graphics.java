package com.ansdoship.a3wt.android;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.graphics.A3Path;

import static com.ansdoship.a3wt.android.A3AndroidUtils.paintStrokeCap2StrokeCap;
import static com.ansdoship.a3wt.android.A3AndroidUtils.paintStrokeJoin2StrokeJoin;
import static com.ansdoship.a3wt.android.A3AndroidUtils.strokeCap2PaintStrokeCap;
import static com.ansdoship.a3wt.android.A3AndroidUtils.strokeJoin2PaintStrokeJoin;

public class AndroidA3Graphics implements A3Graphics {

    protected volatile Canvas canvas;
    protected volatile Paint paint;
    protected volatile boolean disposed = false;
    protected volatile int width, height;

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public AndroidA3Graphics(Bitmap bitmap) {
        this(new Canvas(bitmap), bitmap.getWidth(), bitmap.getHeight());
    }

    public AndroidA3Graphics(Canvas canvas, int width, int height) {
        this.canvas = canvas;
        this.width = width;
        this.height = height;
        paint = new Paint();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Paint getPaint() {
        return paint;
    }

    protected void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void drawPath(Path path) {
        checkDisposed("Can't call drawPath() on a disposed A3Graphics");
        canvas.drawPath(path, paint);
        canvas.save();
        canvas.restore();
    }

    @Override
    public void drawPath(A3Path path) {
        checkDisposed("Can't call drawPath() on a disposed A3Graphics");
        canvas.drawPath(((AndroidA3Path)path).getPath(), paint);
        canvas.save();
        canvas.restore();
    }

    @Override
    public void drawImage(A3Image image, int x, int y) {
        checkDisposed("Can't call drawImage() on a disposed A3Graphics");
        canvas.drawBitmap(((AndroidA3Image)image).getBitmap(), x, y, paint);
        canvas.save();
        canvas.restore();
    }

    @Override
    public void drawPoint(float x, float y) {
        checkDisposed("Can't call drawPoint() on a disposed A3Graphics");
        canvas.drawPoint(x, y, paint);
        canvas.save();
        canvas.restore();
    }

    @Override
    public void drawArc(float left, float top, float right, float bottom, float startAngle, float sweepAngle, boolean useCenter) {
        checkDisposed("Can't call drawArc() on a disposed A3Graphics");
        canvas.drawArc(new RectF(left, top, right, bottom), startAngle, sweepAngle, useCenter, paint);
        canvas.save();
        canvas.restore();
    }

    @Override
    public void drawLine(float startX, float startY, float stopX, float stopY) {
        checkDisposed("Can't call drawLine() on a disposed A3Graphics");
        canvas.drawLine(startX, startY, stopX, stopY, paint);
        canvas.save();
        canvas.restore();
    }

    @Override
    public void drawOval(float left, float top, float right, float bottom) {
        checkDisposed("Can't call drawOval() on a disposed A3Graphics");
        canvas.drawOval(new RectF(left, top, right, bottom), paint);
        canvas.save();
        canvas.restore();
    }

    @Override
    public void drawRect(float left, float top, float right, float bottom) {
        checkDisposed("Can't call drawRect() on a disposed A3Graphics");
        canvas.drawRect(new RectF(left, top, right, bottom), paint);
        canvas.save();
        canvas.restore();
    }

    @Override
    public void drawRoundRect(float left, float top, float right, float bottom, float rx, float ry) {
        checkDisposed("Can't call drawRoundRect() on a disposed A3Graphics");
        canvas.drawRoundRect(new RectF(left, top, right, bottom), rx, ry, paint);
        canvas.save();
        canvas.restore();
    }

    @Override
    public void drawText(String text, float x, float y) {
        checkDisposed("Can't call drawText() on a disposed A3Graphics");
        canvas.drawText(text, x, y, paint);
        canvas.save();
        canvas.restore();
    }

    @Override
    public int getColor() {
        checkDisposed("Can't call getColor() on a disposed A3Graphics");
        return paint.getColor();
    }

    @Override
    public void setColor(int color) {
        checkDisposed("Can't call setColor() on a disposed A3Graphics");
        paint.setColor(color);
    }

    @Override
    public int getStyle() {
        checkDisposed("Can't call getStyle() on a disposed A3Graphics");
        switch (paint.getStyle()) {
            case STROKE:
                return Style.STROKE;
            case FILL:
                return Style.FILL;
        }
        return -1;
    }

    @Override
    public void setStyle(int style) {
        checkDisposed("Can't call setStyle() on a disposed A3Graphics");
        switch (style) {
            case Style.STROKE: default:
                paint.setStyle(Paint.Style.STROKE);
                break;
            case Style.FILL:
                paint.setStyle(Paint.Style.FILL);
                break;
        }
    }

    @Override
    public float getStrokeWidth() {
        checkDisposed("Can't call getStrokeWidth() on a disposed A3Graphics");
        return paint.getStrokeWidth();
    }

    @Override
    public void setStrokeWidth(float width) {
        checkDisposed("Can't call setStrokeWidth() on a disposed A3Graphics");
        paint.setStrokeWidth(width);
    }

    @Override
    public int getStrokeJoin() {
        checkDisposed("Can't call getStrokeJoin() on a disposed A3Graphics");
        return paintStrokeJoin2StrokeJoin(paint.getStrokeJoin());
    }

    @Override
    public void setStrokeJoin(int join) {
        checkDisposed("Can't call setStrokeJoin() on a disposed A3Graphics");
        paint.setStrokeJoin(strokeJoin2PaintStrokeJoin(join));
    }

    @Override
    public int getStrokeCap() {
        checkDisposed("Can't call getStrokeCap() on a disposed A3Graphics");
        return paintStrokeCap2StrokeCap(paint.getStrokeCap());
    }

    @Override
    public void setStrokeCap(int cap) {
        checkDisposed("Can't call setStrokeCap() on a disposed A3Graphics");
        paint.setStrokeCap(strokeCap2PaintStrokeCap(cap));
    }

    @Override
    public float getStrokeMiter() {
        checkDisposed("Can't call getStrokeMiter() on a disposed A3Graphics");
        return paint.getStrokeMiter();
    }

    @Override
    public void setStrokeMiter(float miter) {
        checkDisposed("Can't call setStrokeMiter() on a disposed A3Graphics");
        paint.setStrokeMiter(miter);
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public synchronized void dispose() {
        if (isDisposed()) return;
        disposed = true;
        canvas = null;
        paint = null;
    }

}
