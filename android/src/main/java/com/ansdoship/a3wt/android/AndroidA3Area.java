package com.ansdoship.a3wt.android;

import android.graphics.Point;
import android.graphics.Rect;
import com.ansdoship.a3wt.graphics.A3Area;
import com.ansdoship.a3wt.graphics.A3Coordinate;
import com.ansdoship.a3wt.graphics.A3Dimension;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class AndroidA3Area implements A3Area {

    protected final Rect rect;

    public AndroidA3Area(final Rect rect) {
        checkArgNotNull(rect, "rect");
        this.rect = rect;
    }

    public Rect getRect() {
        return rect;
    }

    @Override
    public int getLeft() {
        return rect.left;
    }

    @Override
    public int getTop() {
        return rect.top;
    }

    @Override
    public int getRight() {
        return rect.right;
    }

    @Override
    public int getBottom() {
        return rect.bottom;
    }

    @Override
    public int getX() {
        return rect.left;
    }

    @Override
    public int getY() {
        return rect.top;
    }

    @Override
    public A3Coordinate getPos() {
        return new AndroidA3Coordinate(new Point(rect.left, rect.top));
    }

    @Override
    public void getPos(final A3Coordinate pos) {
        checkArgNotNull(pos, "pos");
        pos.set(rect.left, rect.top);
    }

    @Override
    public int getWidth() {
        return rect.right - rect.left;
    }

    @Override
    public int getHeight() {
        return rect.bottom - rect.top;
    }

    @Override
    public A3Dimension getSize() {
        return new AndroidA3Dimension(getWidth(), getHeight());
    }

    @Override
    public void getSize(final A3Dimension size) {
        checkArgNotNull(size, "size");
        size.set(getWidth(), getHeight());
    }

    @Override
    public A3Area setLeft(final int left) {
        rect.left = left;
        return this;
    }

    @Override
    public A3Area setTop(final int top) {
        rect.top = top;
        return this;
    }

    @Override
    public A3Area setRight(final int right) {
        rect.right = right;
        return this;
    }

    @Override
    public A3Area setBottom(final int bottom) {
        rect.bottom = bottom;
        return this;
    }

    @Override
    public A3Area setBounds(final int left, final int top, final int right, final int bottom) {
        rect.set(left, top, right, bottom);
        return this;
    }

    @Override
    public A3Area setX(final int x) {
        rect.right += x - rect.left;
        rect.left = x;
        return this;
    }

    @Override
    public A3Area setY(final int y) {
        rect.bottom += y - rect.bottom;
        rect.top = y;
        return this;
    }

    @Override
    public A3Area setWidth(final int width) {
        rect.right += rect.width() - width;
        return this;
    }

    @Override
    public A3Area setHeight(final int height) {
        rect.bottom += rect.height() - height;
        return this;
    }

    @Override
    public A3Area setPos(final int x, final int y) {
        rect.offsetTo(x, y);
        return this;
    }

    @Override
    public A3Area setPos(final A3Coordinate pos) {
        checkArgNotNull(pos, "pos");
        rect.offsetTo(pos.getX(), pos.getY());
        return this;
    }

    @Override
    public A3Area setSize(final int width, final int height) {
        rect.right += rect.width() - width;
        rect.bottom += rect.height() - height;
        return this;
    }

    @Override
    public A3Area setSize(final A3Dimension size) {
        checkArgNotNull(size, "size");
        rect.right += rect.width() - size.getWidth();
        rect.bottom += rect.height() - size.getHeight();
        return this;
    }

    @Override
    public A3Area set(final int x, final int y, final int width, final int height) {
        rect.set(x, y, x + width, y + height);
        return this;
    }

    @Override
    public A3Area set(final A3Coordinate pos, final A3Dimension size) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        return set(pos.getX(), pos.getY(), size.getWidth(), size.getHeight());
    }

    @Override
    public A3Area copy() {
        return new AndroidA3Area(new Rect(rect));
    }

    @Override
    public void to(final A3Area dst) {
        checkArgNotNull(dst, "dst");
        ((AndroidA3Area)dst).rect.set(rect);
    }

    @Override
    public void from(final A3Area src) {
        checkArgNotNull(src, "src");
        src.to(this);
    }

    @Override
    public A3Area reset() {
        rect.set(0, 0, 0, 0);
        return this;
    }

}
