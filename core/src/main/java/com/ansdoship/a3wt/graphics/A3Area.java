package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.bundle.A3ExtMapBundle;
import com.ansdoship.a3wt.util.A3Copyable;
import com.ansdoship.a3wt.util.A3Resetable;

public interface A3Area extends A3Copyable<A3Area>, A3Resetable<A3Area>, A3ExtMapBundle.Delegate {

    int getLeft();
    int getTop();
    int getRight();
    int getBottom();
    int getX();
    int getY();
    A3Coordinate getPos();
    void getPos(final A3Coordinate pos);
    int getWidth();
    int getHeight();
    A3Dimension getSize();
    void getSize(final A3Dimension size);

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

    A3Area set(final int x, final int y, final int width, final int height);
    A3Area set(final A3Coordinate pos, final A3Dimension size);

    default boolean isSquare() {
        return getWidth() == getHeight();
    }

    String KEY_X = "x";
    String KEY_Y = "y";
    String KEY_WIDTH = "width";
    String KEY_HEIGHT = "height";

    @Override
    default void save(final A3ExtMapBundle.Saver saver) {
        saver.putInt(KEY_X, getX());
        saver.putInt(KEY_Y, getY());
        saver.putInt(KEY_WIDTH, getWidth());
        saver.putInt(KEY_HEIGHT, getHeight());
    }

    @Override
    default void restore(final A3ExtMapBundle.Restorer restorer) {
        set(restorer.getInt(KEY_X, 0), restorer.getInt(KEY_Y, 0),
                restorer.getInt(KEY_WIDTH, 0), restorer.getInt(KEY_HEIGHT, 0));
    }

    @Override
    default Class<? extends A3ExtMapBundle.Delegate> typeClass() {
        return A3Area.class;
    }

}
