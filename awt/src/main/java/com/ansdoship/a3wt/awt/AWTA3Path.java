package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Path;

import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class AWTA3Path implements A3Path {

    protected final Path2D path2D;

    public AWTA3Path(final Path2D path2D) {
        checkArgNotNull(path2D, "path2D");
        this.path2D = path2D;
    }

    public Path2D getPath2D() {
        return path2D;
    }

    @Override
    public void reset() {
        path2D.reset();
    }

    @Override
    public void close() {
        path2D.closePath();
    }

    @Override
    public void moveTo(final float x, final float y) {
        path2D.moveTo(x, y);
    }

    @Override
    public void lineTo(final float x, final float y) {
        path2D.lineTo(x, y);
    }

    @Override
    public void quadTo(final float x1, final float y1, final float x2, final float y2) {
        path2D.quadTo(x1, y1, x2, y2);
    }

    @Override
    public void cubicTo(final float x1, final float y1, final float x2, final float y2, final float x3, final float y3) {
        path2D.curveTo(x1, y1, x2, y2, x3, y3);
    }

    @Override
    public void addPath(final A3Path path) {
        checkArgNotNull(path, "path");
        path2D.append(((AWTA3Path)path).getPath2D(), false);
    }

    @Override
    public void addArc(final float left, final float top, final float right, final float bottom, final float startAngle, final float sweepAngle) {
        path2D.append(new Arc2D.Float(new Rectangle2D.Float(left, top, right - left, bottom - top), startAngle, sweepAngle, Arc2D.OPEN), false);
    }

    @Override
    public void addOval(final float left, final float top, final float right, final float bottom) {
        path2D.append(new Ellipse2D.Float(left, top, right - left, bottom - top), false);
    }

    @Override
    public void addRect(final float left, final float top, final float right, final float bottom) {
        path2D.append(new Rectangle2D.Float(left, top, right - left, bottom - top), false);
    }

    @Override
    public void addRoundRect(final float left, final float top, final float right, final float bottom, final float rx, final float ry) {
        path2D.append(new RoundRectangle2D.Float(left, top, right - left, bottom - top, rx, ry), false);
    }

    @Override
    public boolean contains(final float x, final float y) {
        return path2D.contains(x, y);
    }

    @Override
    public A3Path copy() {
        return new AWTA3Path((Path2D) path2D.clone());
    }

}
