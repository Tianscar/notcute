package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.graphics.A3Path;

import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Point2D;
import java.awt.Shape;
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

    public void drawShape(Shape shape) {
        checkDisposed("Can't call drawShape() on a disposed A3Graphics");
        graphics2D.draw(shape);
    }

    @Override
    public void drawPath(A3Path path) {
        checkDisposed("Can't call drawPath() on a disposed A3Graphics");
        graphics2D.draw(((AWTA3Path)path).getPath2D());
    }

    @Override
    public void drawImage(A3Image image, int x, int y) {
        checkDisposed("Can't call drawImage() on a disposed A3Graphics");
        graphics2D.drawImage(((AWTA3Image)image).getBufferedImage(), x, y, image.getWidth(), image.getHeight(), null);
    }

    @Override
    public void drawPoint(float x, float y) {
        checkDisposed("Can't call drawPoint() on a disposed A3Graphics");
        Point2D point2D = new Point2D.Float(x, y);
        graphics2D.draw(new Line2D.Float(point2D, point2D));
    }

    @Override
    public void drawArc(float left, float top, float right, float bottom, float startAngle, float sweepAngle, boolean useCenter) {
        checkDisposed("Can't call drawArc() on a disposed A3Graphics");
        graphics2D.draw(new Arc2D.Float(new Rectangle2D.Float(left, top, right - left, bottom - top), startAngle, sweepAngle,
                useCenter ? Arc2D.PIE : Arc2D.OPEN));
    }

    @Override
    public void drawLine(float startX, float startY, float stopX, float stopY) {
        checkDisposed("Can't call drawLine() on a disposed A3Graphics");
        graphics2D.draw(new Line2D.Float(startX, startY, stopX, stopY));
    }

    @Override
    public void drawOval(float left, float top, float right, float bottom) {
        checkDisposed("Can't call drawOval() on a disposed A3Graphics");
        graphics2D.draw(new Ellipse2D.Float(left, top, right - left, bottom - top));
    }

    @Override
    public void drawRect(float left, float top, float right, float bottom) {
        checkDisposed("Can't call drawRect() on a disposed A3Graphics");
        graphics2D.draw(new Rectangle2D.Float(left, top, right - left, bottom - top));
    }

    @Override
    public void drawRoundRect(float left, float top, float right, float bottom, float rx, float ry) {
        checkDisposed("Can't call drawRoundRect() on a disposed A3Graphics");
        graphics2D.draw(new RoundRectangle2D.Float(left, top, right - left, bottom - top, rx, ry));
    }

    @Override
    public void drawText(String text, float x, float y) {
        checkDisposed("Can't call drawText() on a disposed A3Graphics");
        graphics2D.drawString(text, x, y);
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public synchronized void dispose() {
        if (isDisposed()) return;
        disposed = true;
        graphics2D.dispose();
        graphics2D = null;
    }

}
