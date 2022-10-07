package com.ansdoship.a3wt.android;

import android.graphics.Bitmap;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgRangeMin;

public class AndroidA3Image implements A3Image {

    protected volatile Bitmap bitmap;
    protected volatile AndroidA3Graphics graphics;
    protected volatile boolean disposed = false;

    protected volatile long time;
    protected volatile int hotSpotX;
    protected volatile int hotSpotY;

    public AndroidA3Image(final Bitmap bitmap, final long time, final int hotSpotX, final int hotSpotY) {
        checkArgNotNull(bitmap, "bitmap");
        checkArgRangeMin(time, 0, true, "time");
        this.bitmap = bitmap;
        this.graphics = new AndroidA3Graphics(bitmap);
        this.time = time;
        this.hotSpotX = hotSpotX;
        this.hotSpotY = hotSpotY;
    }

    public AndroidA3Image(final Bitmap bitmap) {
        this(bitmap, 0, 0, 0);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public long getTime() {
        checkDisposed("Can't call getTime() on a disposed A3Image");
        return time;
    }

    @Override
    public void setTime(final long time) {
        checkDisposed("Can't call setTime() on a disposed A3Image");
        checkArgRangeMin(time, 0, true, "time");
        this.time = time;
    }

    @Override
    public int getHotSpotX() {
        checkDisposed("Can't call getHotSpotX() on a disposed A3Image");
        return hotSpotX;
    }

    @Override
    public void setHotSpotX(final int hotSpotX) {
        checkDisposed("Can't call setHotSpotX() on a disposed A3Image");
        this.hotSpotX = hotSpotX;
    }

    @Override
    public int getHotSpotY() {
        checkDisposed("Can't call getHotSpotY() on a disposed A3Image");
        return hotSpotY;
    }

    @Override
    public void setHotSpotY(final int hotSpotY) {
        checkDisposed("Can't call setHotSpotY() on a disposed A3Image");
        this.hotSpotY = hotSpotY;
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
    public int getPixel(final int x, final int y) {
        checkDisposed("Can't call getPixel() on a disposed A3Image");
        return bitmap.getPixel(x, y);
    }

    @Override
    public void setPixel(final int x, final int y, final int color) {
        checkDisposed("Can't call setPixel() on a disposed A3Image");
        bitmap.setPixel(x, y, color);
    }

    @Override
    public void getPixels(final int[] pixels, final int offset, final int stride, final int x, final int y, final int width, final int height) {
        checkArgNotNull(pixels, "pixels");
        checkDisposed("Can't call getPixels() on a disposed A3Image");
        bitmap.getPixels(pixels, offset, stride, x, y, width, height);
    }

    @Override
    public void setPixels(final int[] pixels, final int offset, final int stride, final int x, final int y, final int width, final int height) {
        checkArgNotNull(pixels, "pixels");
        checkDisposed("Can't call setPixels() on a disposed A3Image");
        bitmap.setPixels(pixels, offset, stride, x, y, width, height);
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public void dispose() {
        if (isDisposed()) return;
        disposed = true;
        graphics.dispose();
        graphics = null;
        if (!bitmap.isRecycled()) bitmap.recycle();
        bitmap = null;
        time = -1;
    }

    @Override
    public A3Image copy() {
        checkDisposed("Can't call copy() on a disposed A3Image");
        return new AndroidA3Image(A3AndroidUtils.copyBitmap(bitmap));
    }

}
