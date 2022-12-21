package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Coordinate;
import com.ansdoship.a3wt.graphics.A3Area;
import com.ansdoship.a3wt.graphics.A3Dimension;

import java.awt.Rectangle;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgRangeBounds;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgRangeLeftRight;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgRangeTopBottom;

public class AWTA3Area implements A3Area {

    protected final Rectangle rectangle;

    public AWTA3Area(final Rectangle rectangle) {
        checkArgNotNull(rectangle, "rectangle");
        this.rectangle = rectangle;
    }

    @Override
    public int getLeft() {
        return rectangle.x;
    }

    @Override
    public int getTop() {
        return rectangle.y;
    }

    @Override
    public int getRight() {
        return rectangle.x + rectangle.width;
    }

    @Override
    public int getBottom() {
        return rectangle.y + rectangle.height;
    }

    @Override
    public int getX() {
        return rectangle.x;
    }

    @Override
    public int getY() {
        return rectangle.y;
    }

    @Override
    public A3Coordinate getPos() {
        return new AWTA3Coordinate(rectangle.getLocation());
    }

    @Override
    public void getPos(final A3Coordinate pos) {
        checkArgNotNull(pos, "pos");
        pos.set(rectangle.x, rectangle.y);
    }

    @Override
    public int getWidth() {
        return rectangle.width;
    }

    @Override
    public int getHeight() {
        return rectangle.height;
    }

    @Override
    public A3Dimension getSize() {
        return new AWTA3Dimension(rectangle.getSize());
    }

    @Override
    public void getSize(final A3Dimension size) {
        checkArgNotNull(size, "size");
        size.set(rectangle.width, rectangle.height);
    }

    @Override
    public A3Area setLeft(final int left) {
        checkArgRangeLeftRight(left, getRight());
        rectangle.width += rectangle.x - left;
        rectangle.x = left;
        return this;
    }

    @Override
    public A3Area setTop(final int top) {
        checkArgRangeTopBottom(top, getBottom());
        rectangle.height += rectangle.y - top;
        rectangle.y = top;
        return this;
    }

    @Override
    public A3Area setRight(final int right) {
        checkArgRangeLeftRight(getLeft(), right);
        rectangle.width = right - rectangle.x;
        return this;
    }

    @Override
    public A3Area setBottom(final int bottom) {
        checkArgRangeTopBottom(getTop(), bottom);
        rectangle.height = bottom - rectangle.y;
        return this;
    }

    @Override
    public A3Area setBounds(final int left, final int top, final int right, final int bottom) {
        checkArgRangeBounds(left, top, right, bottom);
        rectangle.x = left;
        rectangle.y = top;
        rectangle.width = right - left;
        rectangle.height = bottom - top;
        return this;
    }

    @Override
    public A3Area setX(final int x) {
        rectangle.x = x;
        return this;
    }

    @Override
    public A3Area setY(final int y) {
        rectangle.y = y;
        return this;
    }

    @Override
    public A3Area setPos(final A3Coordinate pos) {
        checkArgNotNull(pos, "pos");
        rectangle.setLocation(((AWTA3Coordinate)pos).point);
        return this;
    }

    @Override
    public A3Area setWidth(final int width) {
        rectangle.width = width;
        return this;
    }

    @Override
    public A3Area setHeight(final int height) {
        rectangle.height = height;
        return this;
    }

    @Override
    public A3Area setSize(final A3Dimension size) {
        checkArgNotNull(size, "size");
        rectangle.setSize(((AWTA3Dimension)size).dimension);
        return this;
    }

    @Override
    public A3Area setPos(final int x, final int y) {
        rectangle.x = x;
        rectangle.y = y;
        return this;
    }

    @Override
    public A3Area setSize(final int width, final int height) {
        rectangle.width = width;
        rectangle.height = height;
        return this;
    }

    @Override
    public void set(final int x, final int y, final int width, final int height) {
        rectangle.setRect(x, y, width, height);
    }

    @Override
    public void set(final A3Coordinate pos, final A3Dimension size) {
        checkArgNotNull(pos);
        checkArgNotNull(size);
        rectangle.setLocation(((AWTA3Coordinate)pos).point);
        rectangle.setSize(((AWTA3Dimension)size).dimension);
    }

    @Override
    public A3Area copy() {
        return new AWTA3Area((Rectangle) rectangle.clone());
    }

    @Override
    public void to(final A3Area dst) {
        checkArgNotNull(dst, "dst");
        ((AWTA3Area)dst).rectangle.setRect(rectangle);
    }

    @Override
    public void from(final A3Area src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

    @Override
    public void reset() {
        rectangle.x = rectangle.y = rectangle.width = rectangle.height = 0;
    }

}
