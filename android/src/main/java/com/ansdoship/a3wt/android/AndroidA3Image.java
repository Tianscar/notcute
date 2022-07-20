package com.ansdoship.a3wt.android;

import android.graphics.Bitmap;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;

public class AndroidA3Image implements A3Image {

    protected volatile Bitmap bitmap;
    protected volatile AndroidA3Graphics graphics;
    protected volatile boolean disposed = false;

    public AndroidA3Image(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.graphics = new AndroidA3Graphics(bitmap);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public A3Graphics getGraphics() {
        return graphics;
    }

    @Override
    public int getWidth() {
        checkDisposed("Can't call getWidth() on a disposed A3Image");
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        checkDisposed("Can't call getHeight() on a disposed A3Image");
        return bitmap.getHeight();
    }

    @Override
    public int getPixel(int x, int y) {
        checkDisposed("Can't call getPixel() on a disposed A3Image");
        return bitmap.getPixel(x, y);
    }

    @Override
    public void setPixel(int x, int y, int color) {
        checkDisposed("Can't call setPixel() on a disposed A3Image");
        bitmap.setPixel(x, y, color);
    }

    @Override
    public void getPixels(int[] pixels, int offset, int stride, int x, int y, int width, int height) {
        checkDisposed("Can't call getPixels() on a disposed A3Image");
        bitmap.getPixels(pixels, offset, stride, x, y, width, height);
    }

    @Override
    public void setPixels(int[] pixels, int offset, int stride, int x, int y, int width, int height) {
        checkDisposed("Can't call setPixels() on a disposed A3Image");
        bitmap.setPixels(pixels, offset, stride, x, y, width, height);
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public synchronized void dispose() {
        if (isDisposed()) return;
        disposed = true;
        graphics.dispose();
        graphics = null;
        bitmap.recycle();
        bitmap = null;
    }

    @Override
    public A3Image copy() {
        checkDisposed("Can't call copy() on a disposed A3Image");
        return new AndroidA3Image(A3AndroidUtils.copyBitmap(bitmap));
    }

}
