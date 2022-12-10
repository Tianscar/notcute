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
    public void setLeft(final int left) {
        checkArgRangeLeftRight(left, getRight());
        rectangle.width += rectangle.x - left;
        rectangle.x = left;
    }

    @Override
    public void setTop(final int top) {
        checkArgRangeTopBottom(top, getBottom());
        rectangle.height += rectangle.y - top;
        rectangle.y = top;
    }

    @Override
    public void setRight(final int right) {
        checkArgRangeLeftRight(getLeft(), right);
        rectangle.width = right - rectangle.x;
    }

    @Override
    public void setBottom(final int bottom) {
        checkArgRangeTopBottom(getTop(), bottom);
        rectangle.height = bottom - rectangle.y;
    }

    @Override
    public void setBounds(final int left, final int top, final int right, final int bottom) {
        checkArgRangeBounds(left, top, right, bottom);
        rectangle.x = left;
        rectangle.y = top;
        rectangle.width = right - left;
        rectangle.height = bottom - top;
    }

    @Override
    public void setX(final int x) {
        rectangle.x = x;
    }

    @Override
    public void setY(final int y) {
        rectangle.y = y;
    }

    @Override
    public void setPos(final A3Coordinate pos) {
        checkArgNotNull(pos, "pos");
        rectangle.setLocation(((AWTA3Coordinate)pos).point);
    }

    @Override
    public void setWidth(final int width) {
        rectangle.width = width;
    }

    @Override
    public void setHeight(final int height) {
        rectangle.height = height;
    }

    @Override
    public void setSize(A3Dimension size) {
        checkArgNotNull(size, "size");
        rectangle.setSize(((AWTA3Dimension)size).dimension);
    }

    @Override
    public void setPos(final int x, final int y) {
        rectangle.x = x;
        rectangle.y = y;
    }

    @Override
    public void setSize(final int width, final int height) {
        rectangle.width = width;
        rectangle.height = height;
    }

    @Override
    public void setRect(final int x, final int y, final int width, final int height) {
        rectangle.setRect(x, y, width, height);
    }

    @Override
    public void setRect(final A3Coordinate pos, final A3Dimension size) {
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

}
