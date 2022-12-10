package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Arc;
import com.ansdoship.a3wt.graphics.A3Point;
import com.ansdoship.a3wt.graphics.A3Size;

import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

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
    public void setLeft(final float left) {
        checkArgRangeLeftRight(left, getRight());
        arc2D.width += arc2D.x - left;
        arc2D.x = left;
    }

    @Override
    public void setTop(final float top) {
        checkArgRangeTopBottom(top, getBottom());
        arc2D.height += arc2D.y - top;
        arc2D.y = top;
    }

    @Override
    public void setRight(final float right) {
        checkArgRangeLeftRight(getLeft(), right);
        arc2D.width = right - arc2D.x;
    }

    @Override
    public void setBottom(final float bottom) {
        checkArgRangeTopBottom(getTop(), bottom);
        arc2D.height = bottom - arc2D.y;
    }

    @Override
    public void setBounds(final float left, final float top, final float right, final float bottom) {
        checkArgRangeBounds(left, top, right, bottom);
        arc2D.x = left;
        arc2D.y = top;
        arc2D.width = right - left;
        arc2D.height = bottom - top;
    }

    @Override
    public void setX(final float x) {
        arc2D.x = x;
    }

    @Override
    public void setY(final float y) {
        arc2D.y = y;
    }

    @Override
    public void setWidth(final float width) {
        arc2D.width = width;
    }

    @Override
    public void setHeight(final float height) {
        arc2D.height = height;
    }

    @Override
    public void setPos(final float x, final float y) {
        arc2D.x = x;
        arc2D.y = y;
    }

    @Override
    public void setPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        arc2D.x = pos.getX();
        arc2D.y = pos.getY();
    }

    @Override
    public void setSize(final float width, final float height) {
        arc2D.width = width;
        arc2D.height = height;
    }

    @Override
    public void setSize(final A3Size size) {
        checkArgNotNull(size, "size");
        arc2D.width = size.getWidth();
        arc2D.height = size.getHeight();
    }

    @Override
    public void setRect(final float x, final float y, final float width, final float height) {
        arc2D.x = x;
        arc2D.y = y;
        arc2D.width = width;
        arc2D.height = height;
    }

    @Override
    public void setRect(final A3Point pos, final A3Size size) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        arc2D.x = pos.getX();
        arc2D.y = pos.getY();
        arc2D.width = size.getWidth();
        arc2D.height = size.getHeight();
    }

    @Override
    public void setStartAngle(final float startAngle) {
        arc2D.start = startAngle;
    }

    @Override
    public void setSweepAngle(final float sweepAngle) {
        arc2D.extent = sweepAngle;
    }

    @Override
    public void setUseCenter(final boolean useCenter) {
        arc2D.setArcType(useCenter ? Arc2D.PIE : Arc2D.OPEN);
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

}
