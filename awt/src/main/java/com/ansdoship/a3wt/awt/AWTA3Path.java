package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Path;

import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Path2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class AWTA3Path implements A3Path {

    protected final Path2D.Float path2D;

    public AWTA3Path(final Path2D.Float path2D) {
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
    public void addLine(final float startX, final float startY, final float endX, final float endY) {
        path2D.append(new Line2D.Float(startX, startY, endX, endY), false);
    }

    @Override
    public void addQuadCurve(final float startX, final float startY, final float endX, final float endY, final float ctrlX, final float ctrlY) {
        path2D.append(new QuadCurve2D.Float(startX, startY, endX, endY, ctrlX, ctrlY), false);
    }

    @Override
    public void addCubicCurve(final float startX, final float startY, final float endX, final float endY,
                              final float ctrlX1, final float ctrlY1, final float ctrlX2, final float ctrlY2) {
        path2D.append(new CubicCurve2D.Float(startX, startY, endX, endY, ctrlX1, ctrlY1, ctrlX2, ctrlY2), false);
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
        return new AWTA3Path((Path2D.Float) path2D.clone());
    }

    @Override
    public void to(final A3Path dst) {
        checkArgNotNull(dst, "dst");
        ((AWTA3Path)dst).path2D.reset();
        ((AWTA3Path)dst).path2D.append(path2D, false);
    }

    @Override
    public void from(final A3Path src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

}
