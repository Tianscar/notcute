package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;

import java.awt.image.BufferedImage;

import static com.ansdoship.a3wt.util.A3Asserts.checkArgNotNull;

public class AWTA3Image implements A3Image {

    protected volatile BufferedImage bufferedImage;
    protected volatile AWTA3Graphics graphics;
    protected volatile boolean disposed = false;

    public AWTA3Image(final BufferedImage bufferedImage) {
        checkArgNotNull(bufferedImage, "bufferedImage");
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
    public int getPixel(final int x, final int y) {
        checkDisposed("Can't call getPixel() on a disposed A3Image");
        return bufferedImage.getRGB(x, y);
    }

    @Override
    public void setPixel(final int x, final int y, final int color) {
        checkDisposed("Can't call setPixel() on a disposed A3Image");
        bufferedImage.setRGB(x, y, color);
    }

    @Override
    public void getPixels(final int[] pixels, final int offset, final int stride, final int x, final int y, final int width, final int height) {
        checkArgNotNull(pixels, "pixels");
        checkDisposed("Can't call getPixels() on a disposed A3Image");
        bufferedImage.getRGB(x, y, width, height, pixels, offset, stride);
    }

    @Override
    public void setPixels(final int[] pixels, final int offset, final int stride, final int x, final int y, final int width, final int height) {
        checkArgNotNull(pixels, "pixels");
        checkDisposed("Can't call setPixels() on a disposed A3Image");
        bufferedImage.setRGB(x, y, width, height, pixels, offset, stride);
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public void dispose() {
        if (disposed) return;
        disposed = true;
        graphics.dispose();
        graphics = null;
        bufferedImage.flush();
        bufferedImage = null;
    }

    @Override
    public A3Image copy() {
        checkDisposed("Can't call copy() on a disposed A3Image");
        return new AWTA3Image(A3AWTUtils.copyBufferedImage(bufferedImage));
    }

}
