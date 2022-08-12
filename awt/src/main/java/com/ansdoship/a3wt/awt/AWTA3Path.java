package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Path;

import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class AWTA3Path implements A3Path {

    protected final Path2D path2D;

    public AWTA3Path(Path2D path2D) {
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
    public void moveTo(float x, float y) {
        path2D.moveTo(x, y);
    }

    @Override
    public void lineTo(float x, float y) {
        path2D.lineTo(x, y);
    }

    @Override
    public void quadTo(float x1, float y1, float x2, float y2) {
        path2D.quadTo(x1, y1, x2, y2);
    }

    @Override
    public void cubicTo(float x1, float y1, float x2, float y2, float x3, float y3) {
        path2D.curveTo(x1, y1, x2, y2, x3, y3);
    }

    @Override
    public void addPath(A3Path path) {
        path2D.append(((AWTA3Path)path).getPath2D(), false);
    }

    @Override
    public void addArc(float left, float top, float right, float bottom, float startAngle, float sweepAngle) {
        path2D.append(new Arc2D.Float(new Rectangle2D.Float(left, top, right - left, bottom - top), startAngle, sweepAngle, Arc2D.OPEN), false);
    }

    @Override
    public void addOval(float left, float top, float right, float bottom) {
        path2D.append(new Ellipse2D.Float(left, top, right - left, bottom - top), false);
    }

    @Override
    public void addRect(float left, float top, float right, float bottom) {
        path2D.append(new Rectangle2D.Float(left, top, right - left, bottom - top), false);
    }

    @Override
    public void addRoundRect(float left, float top, float right, float bottom, float rx, float ry) {
        path2D.append(new RoundRectangle2D.Float(left, top, right - left, bottom - top, rx, ry), false);
    }

    @Override
    public A3Path copy() {
        return new AWTA3Path((Path2D) path2D.clone());
    }

}
