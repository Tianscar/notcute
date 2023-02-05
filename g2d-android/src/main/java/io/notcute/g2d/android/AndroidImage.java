package io.notcute.g2d.android;

import android.graphics.Bitmap;
import io.notcute.g2d.Graphics;
import io.notcute.g2d.Image;
import io.notcute.util.AlreadyDisposedException;

import java.util.Objects;

public class AndroidImage implements Image {

    private volatile Bitmap bitmap;
    private volatile AndroidGraphics graphics = null;
    public AndroidImage(Bitmap bitmap) {
        this.bitmap = Objects.requireNonNull(bitmap);
    }

    private AndroidImage(AndroidImage image) {
        copy(image, this);
    }

    private static void copy(AndroidImage src, AndroidImage dst) {
        dst.bitmap = Util.copyBitmap(src.bitmap);
        if (src.graphics != null) {
            dst.graphics = new AndroidGraphics(dst.bitmap);
            dst.graphics.setInfo(src.graphics.getInfo());
            dst.graphics.setCacheInfo(src.graphics.getCacheInfo());
        }
    }

    public Bitmap getBitmap() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return bitmap;
    }

    @Override
    public boolean isDisposed() {
        return bitmap.isRecycled();
    }

    @Override
    public void dispose() {
        if (isDisposed()) return;
        bitmap.recycle();
        bitmap = null;
        graphics.dispose();
        graphics = null;
    }

    @Override
    public Graphics getGraphics() {
        if (isDisposed()) throw new AlreadyDisposedException();
        if (graphics == null) graphics = new AndroidGraphics(bitmap);
        return graphics;
    }

    @Override
    public int getWidth() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return bitmap.getHeight();
    }

    @Override
    public int getPixel(int x, int y) {
        if (isDisposed()) throw new AlreadyDisposedException();
        return bitmap.getPixel(x, y);
    }

    @Override
    public void setPixel(int x, int y, int color) {
        if (isDisposed()) throw new AlreadyDisposedException();
        bitmap.setPixel(x, y, color);
    }

    @Override
    public void getPixels(int[] pixels, int offset, int stride, int x, int y, int width, int height) {
        if (isDisposed()) throw new AlreadyDisposedException();
        bitmap.getPixels(pixels, offset, stride, x, y, width, height);
    }

    @Override
    public void setPixels(int[] pixels, int offset, int stride, int x, int y, int width, int height) {
        if (isDisposed()) throw new AlreadyDisposedException();
        bitmap.setPixels(pixels, offset, stride, x, y, width, height);
    }

    @Override
    public int getType() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return Util.toNotcuteImageType(bitmap.getConfig());
    }

    @Override
    public Object clone() {
        if (isDisposed()) throw new AlreadyDisposedException();
        try {
            AndroidImage clone = (AndroidImage) super.clone();
            copy(this, clone);
            return clone;
        }
        catch (CloneNotSupportedException e) {
            return new AndroidImage(this);
        }
    }

}
