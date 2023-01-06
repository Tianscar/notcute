package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Arc;
import com.ansdoship.a3wt.graphics.A3Point;
import com.ansdoship.a3wt.graphics.A3Rect;
import com.ansdoship.a3wt.graphics.A3Size;

import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static com.ansdoship.a3wt.awt.A3AWTUtils.floatRectangle2D;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgRangeBounds;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgRangeLeftRight;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgRangeTopBottom;

public class AWTA3Arc implements A3Arc {

    protected Arc2D.Float arc2D;

    public AWTA3Arc(final Arc2D.Float arc2D) {
        checkArgNotNull(arc2D, "arc2D");
        this.arc2D = arc2D;
    }

    public Arc2D getArc2D() {
        return arc2D;
    }

    @Override
    public float getLeft() {
        return arc2D.x;
    }

    @Override
    public float getTop() {
        return arc2D.y;
    }

    @Override
    public float getRight() {
        return arc2D.x + arc2D.width;
    }

    @Override
    public float getBottom() {
        return arc2D.y + arc2D.height;
    }

    @Override
    public float getX() {
        return arc2D.x;
    }

    @Override
    public float getY() {
        return arc2D.y;
    }

    @Override
    public A3Point getPos() {
        return new AWTA3Point(new Point2D.Float(arc2D.x, arc2D.y));
    }

    @Override
    public void getPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        pos.set(arc2D.x, arc2D.y);
    }

    @Override
    public float getWidth() {
        return arc2D.width;
    }

    @Override
    public float getHeight() {
        return arc2D.height;
    }

    @Override
    public A3Size getSize() {
        return new AWTA3Size(new Dimension2D.Float(arc2D.width, arc2D.height));
    }

    @Override
    public void getSize(final A3Size size) {
        checkArgNotNull(size, "size");
        size.set(arc2D.width, arc2D.height);
    }

    @Override
    public A3Rect getRect() {
        return new AWTA3Rect(new Rectangle2D.Float(arc2D.x, arc2D.y, arc2D.width, arc2D.height));
    }

    @Override
    public void getRect(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        rect.set(arc2D.x, arc2D.y, arc2D.width, arc2D.height);
    }

    @Override
    public float getStartAngle() {
        return arc2D.start;
    }

    @Override
    public float getSweepAngle() {
        return arc2D.extent;
    }

    @Override
    public boolean isUseCenter() {
        return arc2D.getArcType() == Arc2D.PIE;
    }

    @Override
    public A3Arc setLeft(final float left) {
        checkArgRangeLeftRight(left, getRight());
        arc2D.width += arc2D.x - left;
        arc2D.x = left;
        return this;
    }

    @Override
    public A3Arc setTop(final float top) {
        checkArgRangeTopBottom(top, getBottom());
        arc2D.height += arc2D.y - top;
        arc2D.y = top;
        return this;
    }

    @Override
    public A3Arc setRight(final float right) {
        checkArgRangeLeftRight(getLeft(), right);
        arc2D.width = right - arc2D.x;
        return this;
    }

    @Override
    public A3Arc setBottom(final float bottom) {
        checkArgRangeTopBottom(getTop(), bottom);
        arc2D.height = bottom - arc2D.y;
        return this;
    }

    @Override
    public A3Arc setBounds(final float left, final float top, final float right, final float bottom) {
        checkArgRangeBounds(left, top, right, bottom);
        arc2D.x = left;
        arc2D.y = top;
        arc2D.width = right - left;
        arc2D.height = bottom - top;
        return this;
    }

    @Override
    public A3Arc setX(final float x) {
        arc2D.x = x;
        return this;
    }

    @Override
    public A3Arc setY(final float y) {
        arc2D.y = y;
        return this;
    }

    @Override
    public A3Arc setWidth(final float width) {
        arc2D.width = width;
        return this;
    }

    @Override
    public A3Arc setHeight(final float height) {
        arc2D.height = height;
        return this;
    }

    @Override
    public A3Arc setPos(final float x, final float y) {
        arc2D.x = x;
        arc2D.y = y;
        return this;
    }

    @Override
    public A3Arc setPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        arc2D.x = pos.getX();
        arc2D.y = pos.getY();
        return this;
    }

    @Override
    public A3Arc setSize(final float width, final float height) {
        arc2D.width = width;
        arc2D.height = height;
        return this;
    }

    @Override
    public A3Arc setSize(final A3Size size) {
        checkArgNotNull(size, "size");
        arc2D.width = size.getWidth();
        arc2D.height = size.getHeight();
        return this;
    }

    @Override
    public A3Arc setRect(final float x, final float y, final float width, final float height) {
        arc2D.x = x;
        arc2D.y = y;
        arc2D.width = width;
        arc2D.height = height;
        return this;
    }

    @Override
    public A3Arc setRect(final A3Point pos, final A3Size size) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        arc2D.x = pos.getX();
        arc2D.y = pos.getY();
        arc2D.width = size.getWidth();
        arc2D.height = size.getHeight();
        return this;
    }

    @Override
    public A3Arc setRect(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        arc2D.x = rect.getX();
        arc2D.y = rect.getY();
        arc2D.width = rect.getWidth();
        arc2D.height = rect.getHeight();
        return this;
    }

    @Override
    public A3Arc setStartAngle(final float startAngle) {
        arc2D.start = startAngle;
        return this;
    }

    @Override
    public A3Arc setSweepAngle(final float sweepAngle) {
        arc2D.extent = sweepAngle;
        return this;
    }

    @Override
    public A3Arc setUseCenter(final boolean useCenter) {
        arc2D.setArcType(useCenter ? Arc2D.PIE : Arc2D.OPEN);
        return this;
    }

    @Override
    public A3Arc set(final float x, final float y, final float width, final float height,
                    final float startAngle, final float sweepAngle, final boolean useCenter) {
        arc2D.setArc(x, y, width, height, startAngle, sweepAngle, useCenter ? Arc2D.PIE : Arc2D.OPEN);
        return this;
    }

    @Override
    public A3Arc set(final A3Point pos, final A3Size size, final float startAngle, final float sweepAngle, final boolean useCenter) {
        arc2D.setArc(((AWTA3Point)pos).point2D, ((AWTA3Size)size).dimension2D, startAngle, sweepAngle, useCenter ? Arc2D.PIE : Arc2D.OPEN);
        return this;
    }

    @Override
    public A3Arc set(final A3Rect rect, final float startAngle, final float sweepAngle, final boolean useCenter) {
        arc2D.setArc(((AWTA3Rect)rect).rectangle2D, startAngle, sweepAngle, useCenter ? Arc2D.PIE : Arc2D.OPEN);
        return this;
    }

    @Override
    public A3Arc copy() {
        return new AWTA3Arc((Arc2D.Float) arc2D.clone());
    }

    @Override
    public void to(final A3Arc dst) {
        checkArgNotNull(dst, "dst");
        ((AWTA3Arc)dst).arc2D.setArc(arc2D);
    }

    @Override
    public void from(final A3Arc src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

    @Override
    public A3Rect getBounds() {
        return new AWTA3Rect(floatRectangle2D(arc2D.getBounds2D()));
    }

    @Override
    public void getBounds(final A3Rect bounds) {
        checkArgNotNull(bounds, "bounds");
        ((AWTA3Rect)bounds).rectangle2D.setRect(arc2D.getBounds2D());
    }

    @Override
    public boolean contains(final float x, final float y) {
        return arc2D.contains(x, y);
    }

    @Override
    public boolean contains(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        return arc2D.contains(((AWTA3Point)pos).point2D);
    }

    @Override
    public boolean contains(final float x, final float y, final float width, final float height) {
        return arc2D.contains(x, y, width, height);
    }

    @Override
    public boolean contains(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        return arc2D.contains(((AWTA3Rect)rect).rectangle2D);
    }

    @Override
    public A3Arc reset() {
        arc2D.setArc(0, 0, 0, 0, 0, 0, Arc2D.OPEN);
        return this;
    }

}
