package io.notcute.g2d.swt;

import io.notcute.g2d.Graphics;
import io.notcute.util.AlreadyDisposedException;
import org.eclipse.swt.graphics.ImageData;

import java.util.Objects;

public class SWTImage implements io.notcute.g2d.Image {

    private volatile ImageData imageData;
    private volatile boolean disposed = false;
    public SWTImage(ImageData imageData) {
        this.imageData = Objects.requireNonNull(imageData);
        if (imageData.depth < 24) throw new IllegalArgumentException("imageData's depth should >= 24");
    }

    public ImageData getImageData() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return imageData;
    }

    @Override
    public Object clone() {
        try {
            SWTImage clone = (SWTImage) super.clone();
            clone.imageData = (ImageData) imageData.clone();
            return clone;
        }
        catch (CloneNotSupportedException e) {
            return new SWTImage((ImageData) imageData.clone());
        }
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public void dispose() {
        if (isDisposed()) return;
        disposed = true;
        imageData = null;
    }

    @Override
    public Graphics getGraphics() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return null;
    }

    @Override
    public int getWidth() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return imageData.width;
    }

    @Override
    public int getHeight() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return imageData.height;
    }

    @Override
    public int getPixel(int x, int y) {
        if (isDisposed()) throw new AlreadyDisposedException();
        return imageData.getPixel(x, y);
    }

    @Override
    public void setPixel(int x, int y, int color) {
        if (isDisposed()) throw new AlreadyDisposedException();
        imageData.setPixel(x, y, color);
    }

    @Override
    public void getPixels(int[] pixels, int offset, int stride, int x, int y, int width, int height) {
        if (isDisposed()) throw new AlreadyDisposedException();
        Util.getPixels(imageData, x, y, width * height, pixels, offset, stride);
    }

    @Override
    public void setPixels(int[] pixels, int offset, int stride, int x, int y, int width, int height) {
        if (isDisposed()) throw new AlreadyDisposedException();
        Util.setPixels(imageData, x, y, width * height, pixels, offset, stride);
    }

    @Override
    public int getType() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return Util.toNotcuteImageType(imageData.depth);
    }

}
