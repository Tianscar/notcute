package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.graphics.A3Path;

import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Point2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;

import static com.ansdoship.a3wt.awt.A3AWTUtils.strokeCap2BasicStrokeCap;
import static com.ansdoship.a3wt.awt.A3AWTUtils.strokeJoin2BasicStrokeJoin;

public class AWTA3Graphics implements A3Graphics {

    protected volatile Graphics2D graphics2D;
    protected volatile int style = Style.STROKE;
    protected volatile float strokeWidth = 1.0f;
    protected volatile int strokeJoin = Join.MITER;
    protected volatile int strokeCap = Cap.BUTT;
    protected volatile float strokeMiter = 10.0f;
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

    public AWTA3Graphics(Graphics2D graphics2D, int width, int height) {
        this.graphics2D = graphics2D;
        this.width = width;
        this.height = height;
    }

    public Graphics2D getGraphics() {
        return graphics2D;
    }

    public void drawShape(Shape shape) {
        checkDisposed("Can't call drawShape() on a disposed A3Graphics");
        mDrawShape(shape);
    }

    private void mDrawShape(Shape shape) {
        switch (style) {
            case Style.STROKE: default:
                graphics2D.draw(shape);
                break;
            case Style.FILL:
                graphics2D.fill(shape);
                break;
        }
    }

    @Override
    public void drawPath(A3Path path) {
        checkDisposed("Can't call drawPath() on a disposed A3Graphics");
        mDrawShape(((AWTA3Path)path).getPath2D());
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
        mDrawShape(new Line2D.Float(point2D, point2D));
    }

    @Override
    public void drawArc(float left, float top, float right, float bottom, float startAngle, float sweepAngle, boolean useCenter) {
        checkDisposed("Can't call drawArc() on a disposed A3Graphics");
        mDrawShape(new Arc2D.Float(new Rectangle2D.Float(left, top, right - left, bottom - top), startAngle, sweepAngle,
                useCenter ? Arc2D.PIE : Arc2D.OPEN));
    }

    @Override
    public void drawLine(float startX, float startY, float stopX, float stopY) {
        checkDisposed("Can't call drawLine() on a disposed A3Graphics");
        mDrawShape(new Line2D.Float(startX, startY, stopX, stopY));
    }

    @Override
    public void drawOval(float left, float top, float right, float bottom) {
        checkDisposed("Can't call drawOval() on a disposed A3Graphics");
        mDrawShape(new Ellipse2D.Float(left, top, right - left, bottom - top));
    }

    @Override
    public void drawRect(float left, float top, float right, float bottom) {
        checkDisposed("Can't call drawRect() on a disposed A3Graphics");
        mDrawShape(new Rectangle2D.Float(left, top, right - left, bottom - top));
    }

    @Override
    public void drawRoundRect(float left, float top, float right, float bottom, float rx, float ry) {
        checkDisposed("Can't call drawRoundRect() on a disposed A3Graphics");
        mDrawShape(new RoundRectangle2D.Float(left, top, right - left, bottom - top, rx, ry));
    }

    @Override
    public void drawText(String text, float x, float y) {
        checkDisposed("Can't call drawText() on a disposed A3Graphics");
        graphics2D.drawString(text, x, y);
    }

    @Override
    public int getColor() {
        checkDisposed("Can't call getColor() on a disposed A3Graphics");
        return graphics2D.getColor().getRGB();
    }

    @Override
    public void setColor(int color) {
        checkDisposed("Can't call setColor() on a disposed A3Graphics");
        graphics2D.setColor(new Color(color));
    }

    @Override
    public int getStyle() {
        checkDisposed("Can't call getStyle() on a disposed A3Graphics");
        return style;
    }

    @Override
    public void setStyle(int style) {
        checkDisposed("Can't call setStyle() on a disposed A3Graphics");
        this.style = style;
    }

    @Override
    public float getStrokeWidth() {
        checkDisposed("Can't call getStrokeWidth() on a disposed A3Graphics");
        return strokeWidth;
    }

    @Override
    public void setStrokeWidth(float strokeWidth) {
        checkDisposed("Can't call setStrokeWidth() on a disposed A3Graphics");
        this.strokeWidth = strokeWidth;
        graphics2D.setStroke(new BasicStroke(strokeWidth, strokeCap2BasicStrokeCap(strokeCap), strokeJoin2BasicStrokeJoin(strokeJoin), strokeMiter));
    }

    @Override
    public int getStrokeJoin() {
        checkDisposed("Can't call getStrokeJoin() on a disposed A3Graphics");
        return strokeJoin;
    }

    @Override
    public void setStrokeJoin(int join) {
        checkDisposed("Can't call setStrokeJoin() on a disposed A3Graphics");
        this.strokeJoin = join;
        graphics2D.setStroke(new BasicStroke(strokeWidth, strokeCap2BasicStrokeCap(strokeCap), strokeJoin2BasicStrokeJoin(join), strokeMiter));
    }

    @Override
    public int getStrokeCap() {
        checkDisposed("Can't call getStrokeCap() on a disposed A3Graphics");
        return strokeCap;
    }

    @Override
    public void setStrokeCap(int cap) {
        checkDisposed("Can't call setStrokeCap() on a disposed A3Graphics");
        this.strokeCap = cap;
        graphics2D.setStroke(new BasicStroke(strokeWidth, strokeCap2BasicStrokeCap(cap), strokeJoin2BasicStrokeJoin(strokeJoin), strokeMiter));
    }

    @Override
    public float getStrokeMiter() {
        checkDisposed("Can't call getStrokeMiter() on a disposed A3Graphics");
        return strokeMiter;
    }

    @Override
    public void setStrokeMiter(float miter) {
        checkDisposed("Can't call setStrokeMiter() on a disposed A3Graphics");
        this.strokeMiter = miter;
        graphics2D.setStroke(new BasicStroke(strokeWidth, strokeCap2BasicStrokeCap(strokeCap), strokeJoin2BasicStrokeJoin(strokeJoin), miter));
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
