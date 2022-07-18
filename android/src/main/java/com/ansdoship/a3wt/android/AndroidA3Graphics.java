package com.ansdoship.a3wt.android;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;

public class AndroidA3Graphics implements A3Graphics {

    protected volatile Canvas canvas;
    protected volatile Paint paint;
    protected volatile Matrix matrix;
    protected volatile boolean disposed = false;

    public AndroidA3Graphics(Bitmap bitmap) {
        this(new Canvas(bitmap));
    }

    public AndroidA3Graphics(Canvas canvas) {
        this.canvas = canvas;
        paint = new Paint();
        matrix = new Matrix();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Paint getPaint() {
        return paint;
    }

    public Matrix getMatrix() {
        return matrix;
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
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public void dispose() {
        checkDisposed("Can't call dispose() on a disposed A3Graphics");
        disposed = true;
        canvas = null;
        paint = null;
        matrix = null;
    }

    private void checkDisposed(String errorMessage) {
        if (isDisposed()) {
            throw new IllegalStateException(errorMessage);
        }
    }

}
