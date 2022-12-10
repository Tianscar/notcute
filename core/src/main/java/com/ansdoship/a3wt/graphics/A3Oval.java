package com.ansdoship.a3wt.graphics;

public interface A3Oval extends A3Shape<A3Oval> {

    float getLeft();
    float getTop();
    float getRight();
    float getBottom();
    float getX();
    float getY();
    A3Point getPos();
    float getWidth();
    float getHeight();
    A3Size getSize();

    void get(final float[] values);

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

    void set(final float[] values);
    void set(float x, float y, float width, float height);
    void set(A3Point pos, A3Size size);

    default boolean isCircle() {
        return getWidth() == getHeight();
    }

}
