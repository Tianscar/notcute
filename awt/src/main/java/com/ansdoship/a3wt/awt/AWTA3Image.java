package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;

import java.awt.image.BufferedImage;

public class AWTA3Image implements A3Image {

    protected volatile BufferedImage bufferedImage;
    protected volatile AWTA3Graphics graphics;
    protected volatile boolean disposed = false;

    public AWTA3Image(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        this.graphics = new AWTA3Graphics(bufferedImage);
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    @Override
    public A3Graphics getGraphics() {
        return graphics;
    }

    @Override
    public int getWidth() {
        checkDisposed("Can't call getWidth() on a disposed A3Image");
        return bufferedImage.getWidth();
    }

    @Override
    public int getHeight() {
        checkDisposed("Can't call getHeight() on a disposed A3Image");
        return bufferedImage.getHeight();
    }

    @Override
    public int getPixel(int x, int y) {
        checkDisposed("Can't call getPixel() on a disposed A3Image");
        return bufferedImage.getRGB(x, y);
    }

    @Override
    public void setPixel(int x, int y, int color) {
        checkDisposed("Can't call setPixel() on a disposed A3Image");
        bufferedImage.setRGB(x, y, color);
    }

    @Override
    public void getPixels(int[] pixels, int offset, int stride, int x, int y, int width, int height) {
        checkDisposed("Can't call getPixels() on a disposed A3Image");
        bufferedImage.getRGB(x, y, width, height, pixels, offset, stride);
    }

    @Override
    public void setPixels(int[] pixels, int offset, int stride, int x, int y, int width, int height) {
        checkDisposed("Can't call setPixels() on a disposed A3Image");
        bufferedImage.setRGB(x, y, width, height, pixels, offset, stride);
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public synchronized void dispose() {
        checkDisposed("Can't call dispose() on a disposed A3Image");
        disposed = true;
        graphics.dispose();
        graphics = null;
        bufferedImage = null;
    }

    private void checkDisposed(String errorMessage) {
        if (disposed) {
            throw new IllegalStateException(errorMessage);
        }
    }

    @Override
    public A3Image copy() {
        checkDisposed("Can't call copy() on a disposed A3Image");
        return new AWTA3Image(A3AWTUtils.copyBufferedImage(bufferedImage));
    }

}
