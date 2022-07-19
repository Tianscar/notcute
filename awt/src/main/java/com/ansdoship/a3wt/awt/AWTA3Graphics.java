package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class AWTA3Graphics implements A3Graphics {

    protected volatile Graphics2D graphics2D;
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

    public AWTA3Graphics(BufferedImage bufferedImage) {
        this(bufferedImage.createGraphics(), bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    public AWTA3Graphics(Graphics2D graphics, int width, int height) {
        this.graphics2D = graphics;
        this.width = width;
        this.height = height;
    }

    public Graphics2D getGraphics() {
        return graphics2D;
    }

    @Override
    public void drawImage(A3Image image, int x, int y) {
        checkDisposed("Can't call drawImage() on a disposed A3Graphics");
        graphics2D.drawImage(((AWTA3Image)image).getBufferedImage(), x, y, image.getWidth(), image.getHeight(), null);
    }

    @Override
    public void drawArc(int left, int top, int right, int bottom, int startAngle, int sweepAngle) {
        checkDisposed("Can't call drawArc() on a disposed A3Graphics");
        graphics2D.drawArc(left, top, right - left, bottom - top, startAngle, sweepAngle);
    }

    @Override
    public void drawLine(int startX, int startY, int stopX, int stopY) {
        checkDisposed("Can't call drawLine() on a disposed A3Graphics");
        graphics2D.drawLine(startX, startY, stopX, stopY);
    }

    @Override
    public void drawOval(int left, int top, int right, int bottom) {
        checkDisposed("Can't call drawOval() on a disposed A3Graphics");
        graphics2D.drawOval(left, top, right - left, bottom - top);
    }

    @Override
    public void drawRect(int left, int top, int right, int bottom) {
        checkDisposed("Can't call drawRect() on a disposed A3Graphics");
        graphics2D.drawRect(left, top, right - left, bottom - top);
    }

    @Override
    public void drawRoundRect(int left, int top, int right, int bottom, int rx, int ry) {
        checkDisposed("Can't call drawRoundRect() on a disposed A3Graphics");
        graphics2D.drawRoundRect(left, top, right - left, bottom - top, rx, ry);
    }

    @Override
    public void drawText(String text, float x, float y) {
        checkDisposed("Can't call drawText() on a disposed A3Graphics");
        graphics2D.drawString(text, x, y);
    }

    @Override
    public void drawPolygon(int[] xpoints, int[] ypoints, int npoints, boolean close) {
        checkDisposed("Can't call drawPolygon() on a disposed A3Graphics");
        if (close) graphics2D.drawPolygon(xpoints, ypoints, npoints);
        else graphics2D.drawPolyline(xpoints, ypoints, npoints);
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public synchronized void dispose() {
        checkDisposed("Can't call dispose() on a disposed A3Graphics");
        disposed = true;
        graphics2D.dispose();
        graphics2D = null;
    }

    private void checkDisposed(String errorMessage) {
        if (disposed) {
            throw new IllegalStateException(errorMessage);
        }
    }

}
