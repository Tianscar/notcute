package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;

import java.awt.image.BufferedImage;

import static com.ansdoship.a3wt.awt.A3AWTUtils.bufferedImageType2ImageType;
import static com.ansdoship.a3wt.awt.A3AWTUtils.imageType2BufferedImageType;
import static com.ansdoship.a3wt.awt.A3AWTUtils.getImage;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgRangeMin;

public class AWTA3Image implements A3Image {

    protected volatile BufferedImage bufferedImage;
    protected volatile AWTA3Graphics graphics;
    protected volatile boolean disposed = false;

    protected volatile long duration;
    protected volatile int hotSpotX;
    protected volatile int hotSpotY;

    public AWTA3Image(final BufferedImage bufferedImage, final long duration, final int hotSpotX, final int hotSpotY) {
        checkArgNotNull(bufferedImage, "bufferedImage");
        checkArgRangeMin(duration, 0, true, "duration");
        this.bufferedImage = bufferedImage;
        this.graphics = new AWTA3Graphics(bufferedImage);
        this.duration = duration;
        this.hotSpotX = hotSpotX;
        this.hotSpotY = hotSpotY;
    }

    public AWTA3Image(final BufferedImage bufferedImage) {
        this(bufferedImage, 0, 0, 0);
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    @Override
    public A3Graphics getGraphics() {
        return graphics;
    }

    @Override
    public long getDuration() {
        checkDisposed("Can't call getDuration() on a disposed A3Image");
        return duration;
    }

    @Override
    public void setDuration(final long duration) {
        checkDisposed("Can't call setDuration() on a disposed A3Image");
        checkArgRangeMin(duration, 0, true, "duration");
        this.duration = duration;
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
    public int getType() {
        checkDisposed("Can't call getType() on a disposed A3Image");
        return bufferedImageType2ImageType(bufferedImage.getType());
    }

    @Override
    public void setType(final int type) {
        checkDisposed("Can't call setType() on a disposed A3Image");
        final BufferedImage newImage = getImage(bufferedImage, imageType2BufferedImageType(type));
        if (newImage != bufferedImage) setBufferedImage(newImage);
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
        if (isDisposed()) return;
        disposed = true;
        graphics.dispose();
        graphics = null;
        bufferedImage.flush();
        bufferedImage = null;
        duration = -1;
    }

    @Override
    public A3Image copy() {
        checkDisposed("Can't call copy() on a disposed A3Image");
        final AWTA3Image copy = new AWTA3Image(A3AWTUtils.copyBufferedImage(bufferedImage));
        copy.duration = duration;
        copy.hotSpotX = hotSpotX;
        copy.hotSpotY = hotSpotY;
        return copy;
    }

    public void setBufferedImage(final BufferedImage bufferedImage) {
        checkArgNotNull(bufferedImage, "bufferedImage");
        this.bufferedImage = bufferedImage;
        setGraphics(new AWTA3Graphics(this.bufferedImage));
    }

    public void setGraphics(final AWTA3Graphics graphics) {
        checkArgNotNull(graphics, "graphics");
        this.graphics.dispose();
        this.graphics = graphics;
    }

    @Override
    public void to(final A3Image dst) {
        checkArgNotNull(dst, "dst");
        final AWTA3Image dst0 = (AWTA3Image) dst;
        dst0.bufferedImage = A3AWTUtils.copyBufferedImage(bufferedImage);
        setGraphics(new AWTA3Graphics(dst0.bufferedImage));
        dst0.duration = duration;
        dst0.hotSpotX = hotSpotX;
        dst0.hotSpotY = hotSpotY;
    }

    @Override
    public void from(final A3Image src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

}
