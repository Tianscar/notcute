package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.util.A3Copyable;

public interface A3Area extends A3Copyable<A3Area> {

    int getLeft();
    int getTop();
    int getRight();
    int getBottom();
    int getX();
    int getY();
    A3Coordinate getPos();
    int getWidth();
    int getHeight();
    A3Dimension getSize();

    void get(final int[] values);

    A3Area setLeft(final int left);
    A3Area setTop(final int top);
    A3Area setRight(final int right);
    A3Area setBottom(final int bottom);
    A3Area setBounds(final int left, final int top, final int right, final int bottom);
    A3Area setX(final int x);
    A3Area setY(final int y);
    A3Area setWidth(final int width);
    A3Area setHeight(final int height);
    A3Area setPos(final int x, final int y);
    A3Area setPos(final A3Coordinate pos);
    A3Area setSize(final int width, final int height);
    A3Area setSize(final A3Dimension size);

    void set(final int[] values);
    void set(final int x, final int y, final int width, final int height);
    void set(final A3Coordinate pos, final A3Dimension size);

    default boolean isSquare() {
        return getWidth() == getHeight();
    }
    
}
