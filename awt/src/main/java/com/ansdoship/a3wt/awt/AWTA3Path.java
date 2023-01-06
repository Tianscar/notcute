package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Arc;
import com.ansdoship.a3wt.graphics.A3Path;
import com.ansdoship.a3wt.graphics.A3Point;
import com.ansdoship.a3wt.graphics.A3Size;
import com.ansdoship.a3wt.graphics.A3Rect;
import com.ansdoship.a3wt.graphics.A3RoundRect;
import com.ansdoship.a3wt.graphics.A3Oval;
import com.ansdoship.a3wt.graphics.A3Line;
import com.ansdoship.a3wt.graphics.A3QuadCurve;
import com.ansdoship.a3wt.graphics.A3CubicCurve;

import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Path2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import static com.ansdoship.a3wt.awt.A3AWTUtils.floatRectangle2D;
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
    public A3Path reset() {
        path2D.reset();
        return this;
    }

    @Override
    public void close() {
        path2D.closePath();
    }

    @Override
    public A3Path moveTo(final float x, final float y) {
        path2D.moveTo(x, y);
        return this;
    }

    @Override
    public A3Path moveTo(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        path2D.moveTo(pos.getX(), pos.getY());
        return this;
    }

    @Override
    public A3Path lineTo(final float x, final float y) {
        path2D.lineTo(x, y);
        return this;
    }

    @Override
    public A3Path lineTo(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        path2D.lineTo(pos.getX(), pos.getY());
        return this;
    }

    @Override
    public A3Path quadTo(final float x1, final float y1, final float x2, final float y2) {
        path2D.quadTo(x1, y1, x2, y2);
        return this;
    }

    @Override
    public A3Path quadTo(final A3Point pos1, final A3Point pos2) {
        checkArgNotNull(pos1, "pos1");
        checkArgNotNull(pos2, "pos2");
        path2D.quadTo(pos1.getX(), pos1.getY(), pos2.getX(), pos2.getY());
        return this;
    }

    @Override
    public A3Path cubicTo(final float x1, final float y1, final float x2, final float y2, final float x3, final float y3) {
        path2D.curveTo(x1, y1, x2, y2, x3, y3);
        return this;
    }

    @Override
    public A3Path cubicTo(final A3Point pos1, final A3Point pos2, final A3Point pos3) {
        checkArgNotNull(pos1, "pos1");
        checkArgNotNull(pos2, "pos2");
        checkArgNotNull(pos3, "pos3");
        path2D.curveTo(pos1.getX(), pos1.getY(), pos2.getX(), pos2.getY(), pos3.getX(), pos3.getY());
        return this;
    }

    @Override
    public A3Path addPath(final A3Path path) {
        checkArgNotNull(path, "path");
        path2D.append(((AWTA3Path)path).getPath2D(), false);
        return this;
    }

    @Override
    public A3Path addArc(final float x, final float y, final float width, final float height, final float startAngle, final float sweepAngle) {
        path2D.append(new Arc2D.Float(x, y, width, height, startAngle, sweepAngle, Arc2D.OPEN), false);
        return this;
    }

    @Override
    public A3Path addArc(final A3Point pos, final A3Size size, final float startAngle, final float sweepAngle) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        path2D.append(new Arc2D.Float(pos.getX(), pos.getY(), size.getWidth(), size.getHeight(), startAngle, sweepAngle, Arc2D.OPEN), false);
        return this;
    }

    @Override
    public A3Path addArc(final A3Rect rect, final float startAngle, final float sweepAngle) {
        checkArgNotNull(rect, "rect");
        path2D.append(new Arc2D.Float(((AWTA3Rect)rect).rectangle2D, startAngle, sweepAngle, Arc2D.OPEN), false);
        return this;
    }

    @Override
    public A3Path addArc(final A3Arc arc) {
        checkArgNotNull(arc, "arc");
        path2D.append(((AWTA3Arc)arc).arc2D, false);
        return this;
    }

    @Override
    public A3Path addLine(final float startX, final float startY, final float endX, final float endY) {
        path2D.append(new Line2D.Float(startX, startY, endX, endY), false);
        return this;
    }

    @Override
    public A3Path addLine(final A3Point startPos, final A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(endPos, "endPos");
        path2D.append(new Line2D.Float(((AWTA3Point)startPos).point2D, ((AWTA3Point)endPos).point2D), false);
        return this;
    }

    @Override
    public A3Path addLine(final A3Line line) {
        checkArgNotNull(line, "line");
        path2D.append(((AWTA3Line)line).line2D, false);
        return this;
    }

    @Override
    public A3Path addQuadCurve(final float startX, final float startY, final float ctrlX, final float ctrlY, final float endX, final float endY) {
        path2D.append(new QuadCurve2D.Float(startX, startY, ctrlX, ctrlY, endX, endY), false);
        return this;
    }

    @Override
    public A3Path addQuadCurve(final A3Point startPos, final A3Point ctrlPos, final A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(ctrlPos, "ctrlPos");
        checkArgNotNull(endPos, "endPos");
        path2D.append(new QuadCurve2D.Float(startPos.getX(), startPos.getY(), ctrlPos.getX(), ctrlPos.getY(), endPos.getX(), endPos.getY()), false);
        return this;
    }

    @Override
    public A3Path addQuadCurve(final A3QuadCurve quadCurve) {
        checkArgNotNull(quadCurve, "quadCurve");
        path2D.append(((AWTA3QuadCurve)quadCurve).quadCurve2D, false);
        return this;
    }

    @Override
    public A3Path addCubicCurve(final float startX, final float startY,
                                final float ctrlX1, final float ctrlY1,
                                final float ctrlX2, final float ctrlY2,
                                final float endX, final float endY) {
        path2D.append(new CubicCurve2D.Float(startX, startY, ctrlX1, ctrlY1, ctrlX2, ctrlY2, endX, endY), false);
        return this;
    }

    @Override
    public A3Path addCubicCurve(final A3Point startPos, final A3Point ctrlPos1, final A3Point ctrlPos2, final A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(ctrlPos1, "ctrlPos1");
        checkArgNotNull(ctrlPos2, "ctrlPos2");
        checkArgNotNull(endPos, "endPos");
        path2D.append(new CubicCurve2D.Float(startPos.getX(), startPos.getY(), ctrlPos1.getX(), ctrlPos1.getY(),
                ctrlPos2.getX(), ctrlPos2.getY(), endPos.getX(), endPos.getY()), false);
        return this;
    }

    @Override
    public A3Path addCubicCurve(final A3CubicCurve cubicCurve) {
        checkArgNotNull(cubicCurve, "cubicCurve");
        path2D.append(((AWTA3CubicCurve)cubicCurve).cubicCurve2D, false);
        return this;
    }

    @Override
    public A3Path addOval(final float x, final float y, final float width, final float height) {
        path2D.append(new Ellipse2D.Float(x, y, width, height), false);
        return this;
    }

    @Override
    public A3Path addOval(final A3Point pos, final A3Size size) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        path2D.append(new Ellipse2D.Float(pos.getX(), pos.getY(), size.getWidth(), size.getHeight()), false);
        return this;
    }

    @Override
    public A3Path addOval(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        path2D.append(new Ellipse2D.Float(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight()), false);
        return this;
    }

    @Override
    public A3Path addOval(final A3Oval oval) {
        checkArgNotNull(oval, "oval");
        path2D.append(((AWTA3Oval)oval).ellipse2D, false);
        return this;
    }

    @Override
    public A3Path addRect(final float x, final float y, final float width, final float height) {
        path2D.append(new Rectangle2D.Float(x, y, width, height), false);
        return this;
    }

    @Override
    public A3Path addRect(final A3Point pos, final A3Size size) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        path2D.append(new Rectangle2D.Float(pos.getX(), pos.getY(), size.getWidth(), size.getHeight()), false);
        return this;
    }

    @Override
    public A3Path addRect(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        path2D.append(((AWTA3Rect)rect).rectangle2D, false);
        return this;
    }

    @Override
    public A3Path addRoundRect(final float x, final float y, final float width, final float height, final float rx, final float ry) {
        path2D.append(new RoundRectangle2D.Float(x, y, width, height, rx, ry), false);
        return this;
    }

    @Override
    public A3Path addRoundRect(final A3Point pos, final A3Size size, final A3Size corner) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        checkArgNotNull(corner, "corner");
        path2D.append(new RoundRectangle2D.Float(pos.getX(), pos.getY(), size.getWidth(), size.getHeight(),
                corner.getWidth(), corner.getHeight()), false);
        return this;
    }

    @Override
    public A3Path addRoundRect(final A3RoundRect roundRect) {
        checkArgNotNull(roundRect, "roundRect");
        path2D.append(((AWTA3RoundRect)roundRect).roundRectangle2D, false);
        return this;
    }

    @Override
    public boolean contains(final float x, final float y) {
        return path2D.contains(x, y);
    }

    @Override
    public boolean contains(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        return path2D.contains(((AWTA3Point)pos).point2D);
    }

    @Override
    public boolean contains(final float x, final float y, final float width, final float height) {
        return path2D.contains(x, y, width, height);
    }

    @Override
    public boolean contains(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        return path2D.contains(((AWTA3Rect)rect).rectangle2D);
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

    @Override
    public A3Rect getBounds() {
        return new AWTA3Rect(floatRectangle2D(path2D.getBounds2D()));
    }

    @Override
    public void getBounds(final A3Rect bounds) {
        checkArgNotNull(bounds, "bounds");
        ((AWTA3Rect)bounds).rectangle2D.setRect(path2D.getBounds2D());
    }

}
