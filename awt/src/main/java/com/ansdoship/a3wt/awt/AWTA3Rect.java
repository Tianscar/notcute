package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Point;
import com.ansdoship.a3wt.graphics.A3Rect;
import com.ansdoship.a3wt.graphics.A3Size;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static com.ansdoship.a3wt.awt.A3AWTUtils.floatRectangle2D;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgRangeBounds;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgRangeLeftRight;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgRangeTopBottom;

public class AWTA3Rect implements A3Rect {

    protected Rectangle2D.Float rectangle2D;

    public AWTA3Rect(final Rectangle2D.Float rectangle2D) {
        checkArgNotNull(rectangle2D, "rectangle2D");
        this.rectangle2D = rectangle2D;
    }

    @Override
    public float getLeft() {
        return rectangle2D.x;
    }

    @Override
    public float getTop() {
        return rectangle2D.y;
    }

    @Override
    public float getRight() {
        return rectangle2D.x + rectangle2D.width;
    }

    @Override
    public float getBottom() {
        return rectangle2D.y + rectangle2D.height;
    }

    @Override
    public float getX() {
        return rectangle2D.x;
    }

    @Override
    public float getY() {
        return rectangle2D.y;
    }

    @Override
    public A3Point getPos() {
        return new AWTA3Point(new Point2D.Float(rectangle2D.x, rectangle2D.y));
    }

    @Override
    public void getPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        pos.set(rectangle2D.x, rectangle2D.y);
    }

    @Override
    public float getWidth() {
        return rectangle2D.width;
    }

    @Override
    public float getHeight() {
        return rectangle2D.height;
    }

    @Override
    public A3Size getSize() {
        return new AWTA3Size(new Dimension2D.Float(rectangle2D.width, rectangle2D.height));
    }

    @Override
    public void getSize(final A3Size size) {
        checkArgNotNull(size, "size");
        size.set(rectangle2D.width, rectangle2D.height);
    }

    @Override
    public A3Rect setLeft(final float left) {
        checkArgRangeLeftRight(left, getRight());
        rectangle2D.width += rectangle2D.x - left;
        rectangle2D.x = left;
        return this;
    }

    @Override
    public A3Rect setTop(final float top) {
        checkArgRangeTopBottom(top, getBottom());
        rectangle2D.height += rectangle2D.y - top;
        rectangle2D.y = top;
        return this;
    }

    @Override
    public A3Rect setRight(final float right) {
        checkArgRangeLeftRight(getLeft(), right);
        rectangle2D.width = right - rectangle2D.x;
        return this;
    }

    @Override
    public A3Rect setBottom(final float bottom) {
        checkArgRangeTopBottom(getTop(), bottom);
        rectangle2D.height = bottom - rectangle2D.y;
        return this;
    }

    @Override
    public A3Rect setBounds(final float left, final float top, final float right, final float bottom) {
        checkArgRangeBounds(left, top, right, bottom);
        rectangle2D.x = left;
        rectangle2D.y = top;
        rectangle2D.width = right - left;
        rectangle2D.height = bottom - top;
        return this;
    }

    @Override
    public A3Rect setX(final float x) {
        rectangle2D.x = x;
        return this;
    }

    @Override
    public A3Rect setY(final float y) {
        rectangle2D.y = y;
        return this;
    }

    @Override
    public A3Rect setPos(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        rectangle2D.x = pos.getX();
        rectangle2D.y = pos.getY();
        return this;
    }

    @Override
    public A3Rect setWidth(final float width) {
        rectangle2D.width = width;
        return this;
    }

    @Override
    public A3Rect setHeight(final float height) {
        rectangle2D.height = height;
        return this;
    }

    @Override
    public A3Rect setSize(final A3Size size) {
        checkArgNotNull(size, "size");
        rectangle2D.width = size.getWidth();
        rectangle2D.height = size.getHeight();
        return this;
    }

    @Override
    public A3Rect setPos(final float x, final float y) {
        rectangle2D.x = x;
        rectangle2D.y = y;
        return this;
    }

    @Override
    public A3Rect setSize(final float width, final float height) {
        rectangle2D.width = width;
        rectangle2D.height = height;
        return this;
    }

    @Override
    public void set(final float x, final float y, final float width, final float height) {
        rectangle2D.setRect(x, y, width, height);
    }

    @Override
    public void set(final A3Point pos, final A3Size size) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        rectangle2D.x = pos.getX();
        rectangle2D.y = pos.getY();
        rectangle2D.width = size.getWidth();
        rectangle2D.height = size.getHeight();
    }

    @Override
    public A3Rect copy() {
        return new AWTA3Rect((Rectangle2D.Float) rectangle2D.clone());
    }

    @Override
    public void to(final A3Rect dst) {
        checkArgNotNull(dst, "dst");
        ((AWTA3Rect)dst).rectangle2D.setRect(rectangle2D);
    }

    @Override
    public void from(final A3Rect src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

    @Override
    public A3Rect getBounds() {
        return new AWTA3Rect(floatRectangle2D(rectangle2D.getBounds2D()));
    }

    @Override
    public void getBounds(final A3Rect bounds) {
        checkArgNotNull(bounds, "bounds");
        ((AWTA3Rect)bounds).rectangle2D.setRect(rectangle2D.getBounds2D());
    }

    @Override
    public boolean contains(final float x, final float y) {
        return rectangle2D.contains(x, y);
    }

    @Override
    public boolean contains(final A3Point pos) {
        checkArgNotNull(pos, "pos");
        return rectangle2D.contains(((AWTA3Point)pos).point2D);
    }

    @Override
    public boolean contains(final float x, final float y, final float width, final float height) {
        return rectangle2D.contains(x, y, width, height);
    }

    @Override
    public boolean contains(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        return rectangle2D.contains(((AWTA3Rect)rect).rectangle2D);
    }

    @Override
    public void reset() {
        rectangle2D.setRect(0, 0, 0, 0);
    }

}
