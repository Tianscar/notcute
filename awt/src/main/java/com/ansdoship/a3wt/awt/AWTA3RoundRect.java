package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Point;
import com.ansdoship.a3wt.graphics.A3RoundRect;
import com.ansdoship.a3wt.graphics.A3Size;

import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgRangeBounds;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgRangeLeftRight;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgRangeTopBottom;

public class AWTA3RoundRect implements A3RoundRect {

    protected final RoundRectangle2D.Float roundRectangle2D;

    public AWTA3RoundRect(final RoundRectangle2D.Float roundRectangle2D) {
        checkArgNotNull(roundRectangle2D, "roundRectangle2D");
        this.roundRectangle2D = roundRectangle2D;
    }

    public RoundRectangle2D.Float getRoundRectangle2D() {
        return roundRectangle2D;
    }

    @Override
    public float getLeft() {
        return roundRectangle2D.x;
    }

    @Override
    public float getTop() {
        return roundRectangle2D.y;
    }

    @Override
    public float getRight() {
        return roundRectangle2D.x + roundRectangle2D.width;
    }

    @Override
    public float getBottom() {
        return roundRectangle2D.y + roundRectangle2D.height;
    }

    @Override
    public float getX() {
        return roundRectangle2D.x;
    }

    @Override
    public float getY() {
        return roundRectangle2D.y;
    }

    @Override
    public A3Point getPos() {
        return new AWTA3Point(new Point2D.Float(roundRectangle2D.x, roundRectangle2D.y));
    }

    @Override
    public float getWidth() {
        return roundRectangle2D.width;
    }

    @Override
    public float getHeight() {
        return roundRectangle2D.height;
    }

    @Override
    public A3Size getSize() {
        return new AWTA3Size(new Dimension2D.Float(roundRectangle2D.width, roundRectangle2D.height));
    }

    @Override
    public float getArcWidth() {
        return roundRectangle2D.arcwidth;
    }

    @Override
    public float getArcHeight() {
        return roundRectangle2D.archeight;
    }

    @Override
    public A3Size getCorner() {
        return new AWTA3Size(new Dimension2D.Float(roundRectangle2D.arcwidth, roundRectangle2D.archeight));
    }

    @Override
    public void setLeft(final float left) {
        checkArgRangeLeftRight(left, getRight());
        roundRectangle2D.width += roundRectangle2D.x - left;
        roundRectangle2D.x = left;
    }

    @Override
    public void setTop(final float top) {
        checkArgRangeTopBottom(top, getBottom());
        roundRectangle2D.height += roundRectangle2D.y - top;
        roundRectangle2D.y = top;
    }

    @Override
    public void setRight(final float right) {
        checkArgRangeLeftRight(getLeft(), right);
        roundRectangle2D.width = right - roundRectangle2D.x;
    }

    @Override
    public void setBottom(final float bottom) {
        checkArgRangeTopBottom(getTop(), bottom);
        roundRectangle2D.height = bottom - roundRectangle2D.y;
    }

    @Override
    public void setBounds(final float left, final float top, final float right, final float bottom) {
        checkArgRangeBounds(left, top, right, bottom);
        roundRectangle2D.x = left;
        roundRectangle2D.y = top;
        roundRectangle2D.width = right - left;
        roundRectangle2D.height = bottom - top;
    }

    @Override
    public void setX(final float x) {
        roundRectangle2D.x = x;
    }

    @Override
    public void setY(final float y) {
        roundRectangle2D.y = y;
    }

    @Override
    public void setPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        roundRectangle2D.x = pos.getX();
        roundRectangle2D.y = pos.getY();
    }

    @Override
    public void setWidth(final float width) {
        roundRectangle2D.width = width;
    }

    @Override
    public void setHeight(final float height) {
        roundRectangle2D.height = height;
    }

    @Override
    public void setSize(final A3Size size) {
        checkArgNotNull(size, "size");
        roundRectangle2D.width = size.getWidth();
        roundRectangle2D.height = size.getHeight();
    }

    @Override
    public void setPos(final float x, final float y) {
        roundRectangle2D.x = x;
        roundRectangle2D.y = y;
    }

    @Override
    public void setSize(final float width, final float height) {
        roundRectangle2D.width = width;
        roundRectangle2D.height = height;
    }

    @Override
    public void setRect(final float x, final float y, final float width, final float height) {
        roundRectangle2D.setFrame(x, y, width, height);
    }

    @Override
    public void setRect(final A3Point pos, final A3Size size) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        roundRectangle2D.x = pos.getX();
        roundRectangle2D.y = pos.getY();
        roundRectangle2D.width = size.getWidth();
        roundRectangle2D.height = size.getHeight();
    }

    @Override
    public void setArcWidth(final float arcWidth) {
        roundRectangle2D.arcwidth = arcWidth;
    }

    @Override
    public void setArcHeight(final float arcHeight) {
        roundRectangle2D.archeight = arcHeight;
    }

    @Override
    public void setCorner(final A3Size corner) {
        checkArgNotNull(corner, "corner");
        roundRectangle2D.arcwidth = corner.getWidth();
        roundRectangle2D.archeight = corner.getHeight();
    }

    @Override
    public A3RoundRect copy() {
        return new AWTA3RoundRect((RoundRectangle2D.Float) roundRectangle2D.clone());
    }

    @Override
    public void to(final A3RoundRect dst) {
        checkArgNotNull(dst, "dst");
    }

    @Override
    public void from(final A3RoundRect src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }
    
}
