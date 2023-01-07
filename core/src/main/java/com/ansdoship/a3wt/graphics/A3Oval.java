package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.bundle.A3ExtMapBundle;

public interface A3Oval extends A3Shape<A3Oval> {

    float getLeft();
    float getTop();
    float getRight();
    float getBottom();
    float getX();
    float getY();
    A3Point getPos();
    void getPos(final A3Point pos);
    float getWidth();
    float getHeight();
    A3Size getSize();
    void getSize(final A3Size size);

    A3Oval setLeft(float left);
    A3Oval setTop(float top);
    A3Oval setRight(float right);
    A3Oval setBottom(float bottom);
    A3Oval setBounds(float left, float top, float right, float bottom);
    A3Oval setX(float x);
    A3Oval setY(float y);
    A3Oval setWidth(float width);
    A3Oval setHeight(float height);
    A3Oval setPos(float x, float y);
    A3Oval setPos(A3Point pos);
    A3Oval setSize(float width, float height);
    A3Oval setSize(A3Size size);

    A3Oval set(float x, float y, float width, float height);
    A3Oval set(A3Point pos, A3Size size);

    default boolean isCircle() {
        return getWidth() == getHeight();
    }

    String KEY_X = "x";
    String KEY_Y = "y";
    String KEY_WIDTH = "width";
    String KEY_HEIGHT = "height";

    @Override
    default void save(final A3ExtMapBundle.Saver saver) {
        saver.putFloat(KEY_X, getX());
        saver.putFloat(KEY_Y, getY());
        saver.putFloat(KEY_WIDTH, getWidth());
        saver.putFloat(KEY_HEIGHT, getHeight());
    }

    @Override
    default void restore(final A3ExtMapBundle.Restorer restorer) {
        set(restorer.getFloat(KEY_X, 0), restorer.getFloat(KEY_Y, 0),
                restorer.getFloat(KEY_WIDTH, 0), restorer.getFloat(KEY_HEIGHT, 0));
    }

    @Override
    default Class<? extends A3ExtMapBundle.Delegate> typeClass() {
        return A3Oval.class;
    }

}
