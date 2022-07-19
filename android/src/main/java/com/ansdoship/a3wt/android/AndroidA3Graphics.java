package com.ansdoship.a3wt.android;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;

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

    @Override
    public void drawImage(A3Image image, int x, int y) {
        checkDisposed("Can't call drawImage() on a disposed A3Graphics");
        canvas.drawBitmap(((AndroidA3Image)image).getBitmap(), x, y, paint);
        canvas.save();
        canvas.restore();
    }

    @Override
    public void drawArc(int left, int top, int right, int bottom, int startAngle, int sweepAngle) {
        checkDisposed("Can't call drawArc() on a disposed A3Graphics");
        canvas.drawArc(new RectF(left, top, right, bottom), startAngle, sweepAngle, false, paint);
        canvas.save();
        canvas.restore();
    }

    @Override
    public void drawLine(int startX, int startY, int stopX, int stopY) {
        checkDisposed("Can't call drawLine() on a disposed A3Graphics");
        canvas.drawLine(startX, startY, stopX, stopY, paint);
        canvas.save();
        canvas.restore();
    }

    @Override
    public void drawOval(int left, int top, int right, int bottom) {
        checkDisposed("Can't call drawOval() on a disposed A3Graphics");
        canvas.drawOval(new RectF(left, top, right, bottom), paint);
        canvas.save();
        canvas.restore();
    }

    @Override
    public void drawRect(int left, int top, int right, int bottom) {
        checkDisposed("Can't call drawRect() on a disposed A3Graphics");
        canvas.drawRect(new RectF(left, top, right, bottom), paint);
        canvas.save();
        canvas.restore();
    }

    @Override
    public void drawRoundRect(int left, int top, int right, int bottom, int rx, int ry) {
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
    public void drawPolygon(int[] xpoints, int[] ypoints, int npoints, boolean close) {
        checkDisposed("Can't call drawPolygon() on a disposed A3Graphics");
        Path path = new Path();
        path.moveTo(xpoints[0], ypoints[0]);
        for (int i = 1; i < npoints; i ++) {
            path.lineTo(xpoints[i], ypoints[i]);
        }
        if (close) path.close();
        canvas.drawPath(path, paint);
        canvas.save();
        canvas.restore();
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public void dispose() {
        checkDisposed("Can't call dispose() on a disposed A3Graphics");
        disposed = true;
        canvas = null;
        paint = null;
    }

    private void checkDisposed(String errorMessage) {
        if (isDisposed()) {
            throw new IllegalStateException(errorMessage);
        }
    }

}
