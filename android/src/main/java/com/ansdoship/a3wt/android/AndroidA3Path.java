package com.ansdoship.a3wt.android;

import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import com.ansdoship.a3wt.graphics.*;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class AndroidA3Path implements A3Path {

    protected final Path path;
    
    protected final RectF mRectF = new RectF();
    protected final Path mPath = new Path();

    public AndroidA3Path(final Path path) {
        checkArgNotNull(path, "path");
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    @Override
    public A3Path reset() {
        path.reset();
        return this;
    }

    @Override
    public void close() {
        path.close();
    }

    @Override
    public A3Path moveTo(final float x, final float y) {
        path.moveTo(x, y);
        return this;
    }

    @Override
    public A3Path moveTo(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        return moveTo(pos.getX(), pos.getY());
    }

    @Override
    public A3Path lineTo(final float x, final float y) {
        path.lineTo(x, y);
        return this;
    }

    @Override
    public A3Path lineTo(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        return lineTo(pos.getX(), pos.getY());
    }

    @Override
    public A3Path quadTo(final float x1, final float y1, final float x2, final float y2) {
        path.quadTo(x1, y1, x2, y2);
        return this;
    }

    @Override
    public A3Path quadTo(final A3Point pos1, final A3Point pos2) {
        checkArgNotNull(pos1, "pos1");
        checkArgNotNull(pos2, "pos2");
        return quadTo(pos1.getX(), pos1.getY(), pos2.getX(), pos2.getY());
    }

    @Override
    public A3Path cubicTo(final float x1, final float y1, final float x2, final float y2, final float x3, final float y3) {
        path.cubicTo(x1, y1, x2, y2, x3, y3);
        return this;
    }

    @Override
    public A3Path cubicTo(final A3Point pos1, final A3Point pos2, final A3Point pos3) {
        checkArgNotNull(pos1, "pos1");
        checkArgNotNull(pos2, "pos2");
        checkArgNotNull(pos3, "pos3");
        return cubicTo(pos1.getX(), pos1.getY(), pos2.getX(), pos2.getY(), pos3.getX(), pos3.getY());
    }

    @Override
    public A3Path addPath(final A3Path path) {
        checkArgNotNull(path, "path");
        this.path.addPath(((AndroidA3Path)path).getPath());
        return this;
    }

    @Override
    public A3Path addArc(final float x, final float y, final float width, final float height, final float startAngle, final float sweepAngle) {
        mRectF.set(x, y, x + width, y + height);
        path.addArc(mRectF, startAngle, sweepAngle);
        return this;
    }

    @Override
    public A3Path addArc(final A3Point pos, final A3Size size, final float startAngle, final float sweepAngle) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        return addArc(pos.getX(), pos.getY(), size.getWidth(), size.getHeight(), startAngle, sweepAngle);
    }

    @Override
    public A3Path addArc(final A3Rect rect, final float startAngle, final float sweepAngle) {
        checkArgNotNull(rect, "rect");
        path.addArc(((AndroidA3Rect)rect).rectF, startAngle, sweepAngle);
        return this;
    }

    @Override
    public A3Path addArc(final A3Arc arc) {
        checkArgNotNull(arc, "arc");
        return addArc(arc.getX(), arc.getY(), arc.getWidth(), arc.getHeight(), arc.getStartAngle(), arc.getSweepAngle());
    }

    @Override
    public A3Path addLine(final float startX, final float startY, final float endX, final float endY) {
        mPath.reset();
        mPath.moveTo(startX, startY);
        mPath.lineTo(endX, endY);
        path.addPath(mPath);
        return this;
    }

    @Override
    public A3Path addLine(final A3Point startPos, final A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(endPos, "endPos");
        return addLine(startPos.getX(), startPos.getY(), endPos.getX(), endPos.getY());
    }

    @Override
    public A3Path addLine(final A3Line line) {
        checkArgNotNull(line, "line");
        return addLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
    }

    @Override
    public A3Path addQuadCurve(final float startX, final float startY, final float ctrlX, final float ctrlY, final float endX, final float endY) {
        mPath.reset();
        mPath.moveTo(startX, startY);
        mPath.quadTo(ctrlX, ctrlY, endX, endY);
        return this;
    }

    @Override
    public A3Path addQuadCurve(final A3Point startPos, final A3Point ctrlPos, final A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(ctrlPos, "ctrlPos");
        checkArgNotNull(endPos, "endPos");
        return addQuadCurve(startPos.getX(), startPos.getY(), ctrlPos.getX(), ctrlPos.getY(), endPos.getX(), endPos.getY());
    }

    @Override
    public A3Path addQuadCurve(final A3QuadCurve quadCurve) {
        checkArgNotNull(quadCurve, "quadCurve");
        return addQuadCurve(quadCurve.getStartX(), quadCurve.getStartY(),
                quadCurve.getCtrlX(), quadCurve.getCtrlY(), quadCurve.getEndX(), quadCurve.getEndY());
    }

    @Override
    public A3Path addCubicCurve(final float startX, final float startY, final float ctrlX1, final float ctrlY1,
                                final float ctrlX2, final float ctrlY2, final float endX, final float endY) {
        mPath.reset();
        mPath.moveTo(startX, startY);
        mPath.cubicTo(ctrlX1, ctrlY1, ctrlX2, ctrlY2, endX, endY);
        path.addPath(mPath);
        return this;
    }

    @Override
    public A3Path addCubicCurve(final A3Point startPos, final A3Point ctrlPos1, final A3Point ctrlPos2, final A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(ctrlPos1, "ctrlPos1");
        checkArgNotNull(ctrlPos2, "ctrlPos2");
        checkArgNotNull(endPos, "endPos");
        return addCubicCurve(startPos.getX(), startPos.getY(), ctrlPos1.getX(), ctrlPos1.getY(),
                ctrlPos2.getX(), ctrlPos2.getY(), endPos.getX(), endPos.getY());
    }

    @Override
    public A3Path addCubicCurve(final A3CubicCurve cubicCurve) {
        checkArgNotNull(cubicCurve, "cubicCurve");
        return addCubicCurve(cubicCurve.getStartX(), cubicCurve.getStartY(), cubicCurve.getCtrlX1(), cubicCurve.getCtrlY1(),
                cubicCurve.getCtrlX2(), cubicCurve.getCtrlY2(), cubicCurve.getEndX(), cubicCurve.getEndY());
    }

    @Override
    public A3Path addOval(final float x, final float y, final float width, final float height) {
        mRectF.set(x, y, x + width, y + height);
        path.addOval(mRectF, Path.Direction.CW);
        return this;
    }

    @Override
    public A3Path addOval(final A3Point pos, final A3Size size) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        return addOval(pos.getX(), pos.getY(), size.getWidth(), size.getHeight());
    }

    @Override
    public A3Path addOval(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        path.addOval(((AndroidA3Rect)rect).rectF, Path.Direction.CW);
        return this;
    }

    @Override
    public A3Path addOval(final A3Oval oval) {
        checkArgNotNull(oval, "oval");
        return addOval(oval.getX(), oval.getY(), oval.getWidth(), oval.getHeight());
    }

    @Override
    public A3Path addRect(final float x, final float y, final float width, final float height) {
        mRectF.set(x, y, x + width, y + height);
        path.addOval(mRectF, Path.Direction.CW);
        return this;
    }

    @Override
    public A3Path addRect(final A3Point pos, final A3Size size) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        return addRect(pos.getX(), pos.getY(), size.getWidth(), size.getHeight());
    }

    @Override
    public A3Path addRect(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        path.addRect(((AndroidA3Rect)rect).rectF, Path.Direction.CW);
        return this;
    }

    @Override
    public A3Path addRoundRect(final float x, final float y, final float width, final float height, final float rx, final float ry) {
        mRectF.set(x, y, x + width, y + height);
        path.addRoundRect(mRectF, rx, ry, Path.Direction.CW);
        return this;
    }

    @Override
    public A3Path addRoundRect(final A3Point pos, final A3Size size, final A3Size corner) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        checkArgNotNull(corner, "corner");
        return addRoundRect(pos.getX(), pos.getY(), size.getWidth(), size.getHeight(), corner.getWidth(), corner.getWidth());
    }

    @Override
    public A3Path addRoundRect(final A3RoundRect roundRect) {
        checkArgNotNull(roundRect, "roundRect");
        return addRoundRect(roundRect.getX(), roundRect.getY(), roundRect.getWidth(), roundRect.getHeight(), roundRect.getArcWidth(), roundRect.getArcHeight());
    }

    @Override
    public boolean contains(final float x, final float y) {
        final RectF bounds = new RectF();
        path.computeBounds(bounds, true);
        final Region pathRegion = new Region();
        pathRegion.setPath(path, new Region((int) bounds.left, (int) bounds.top, (int) bounds.right, (int) bounds.bottom));
        return pathRegion.contains((int) x, (int) y);
    }

    @Override
    public boolean contains(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        return contains(pos.getX(), pos.getY());
    }

    @Override
    public boolean contains(final float x, final float y, final float width, final float height) {
        final RectF bounds = new RectF();
        path.computeBounds(bounds, true);
        final Region pathRegion = new Region();
        pathRegion.setPath(path, new Region((int) bounds.left, (int) bounds.top, (int) bounds.right, (int) bounds.bottom));
        return pathRegion.op((int) x, (int) y, (int) (x + width), (int) (y + height), Region.Op.INTERSECT);
    }

    @Override
    public boolean contains(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        return contains(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    @Override
    public A3Path copy() {
        return new AndroidA3Path(new Path(path));
    }

    @Override
    public void to(final A3Path dst) {
        checkArgNotNull(dst, "dst");
        ((AndroidA3Path)dst).path.set(path);
    }

    @Override
    public void from(final A3Path src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

    @Override
    public A3Rect getBounds() {
        final RectF bounds = new RectF();
        path.computeBounds(bounds, true);
        return new AndroidA3Rect(bounds);
    }

    @Override
    public void getBounds(final A3Rect bounds) {
        checkArgNotNull(bounds, "bounds");
        path.computeBounds(((AndroidA3Rect)bounds).rectF, true);
    }

}
