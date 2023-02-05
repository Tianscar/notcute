package io.notcute.g2d.awt;

import io.notcute.g2d.Graphics;
import io.notcute.g2d.Image;
import io.notcute.util.AlreadyDisposedException;

import java.awt.image.BufferedImage;
import java.util.Objects;

public class AWTImage implements Image {

    private volatile BufferedImage bufferedImage;
    private volatile AWTGraphics graphics = null;
    private volatile boolean disposed = false;

    public AWTImage(BufferedImage bufferedImage) {
        this.bufferedImage = Objects.requireNonNull(bufferedImage);
    }

    private AWTImage(AWTImage image) {
        copy(image, this);
    }

    private static void copy(AWTImage src, AWTImage dst) {
        dst.bufferedImage = Util.copyBufferedImage(src.bufferedImage);
        if (src.graphics != null) {
            dst.graphics = new AWTGraphics(dst.bufferedImage);
            dst.graphics.setInfo(src.graphics.getInfo());
            dst.graphics.setCacheInfo(src.graphics.getCacheInfo());
        }
    }

    public BufferedImage getBufferedImage() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return bufferedImage;
    }

    @Override
    public Graphics getGraphics() {
        if (isDisposed()) throw new AlreadyDisposedException();
        if (graphics == null) graphics = new AWTGraphics(bufferedImage);
        return graphics;
    }

    @Override
    public int getType() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return Util.toNotcuteImageType(bufferedImage.getType());
    }

    @Override
    public int getWidth() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return bufferedImage.getWidth();
    }

    @Override
    public int getHeight() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return bufferedImage.getHeight();
    }

    @Override
    public int getPixel(int x, int y) {
        if (isDisposed()) throw new AlreadyDisposedException();
        return bufferedImage.getRGB(x, y);
    }

    @Override
    public void setPixel(int x, int y, int color) {
        if (isDisposed()) throw new AlreadyDisposedException();
        bufferedImage.setRGB(x, y, color);
    }

    @Override
    public void getPixels(int[] pixels, int offset, int stride, int x, int y, int width, int height) {
        Objects.requireNonNull(pixels);
        if (isDisposed()) throw new AlreadyDisposedException();
        bufferedImage.getRGB(x, y, width, height, pixels, offset, stride);
    }

    @Override
    public void setPixels(int[] pixels, int offset, int stride, int x, int y, int width, int height) {
        Objects.requireNonNull(pixels);
        if (isDisposed()) throw new AlreadyDisposedException();
        bufferedImage.setRGB(x, y, width, height, pixels, offset, stride);
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public void dispose() {
        if (isDisposed()) return;
        disposed = true;
        if (graphics != null) {
            graphics.dispose();
            graphics = null;
        }
        bufferedImage.flush();
        bufferedImage = null;
    }

    @Override
    public Image clone() {
        if (isDisposed()) throw new AlreadyDisposedException();
        try {
            AWTImage clone = (AWTImage) super.clone();
            copy(this, clone);
            return clone;
        }
        catch (CloneNotSupportedException e) {
            return new AWTImage(this);
        }
    }

}
