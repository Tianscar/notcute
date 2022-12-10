package com.ansdoship.a3wt.graphics;

public interface A3Rect extends A3Shape<A3Rect> {

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

    A3Rect setLeft(final float left);
    A3Rect setTop(final float top);
    A3Rect setRight(final float right);
    A3Rect setBottom(final float bottom);
    A3Rect setBounds(final float left, final float top, final float right, final float bottom);
    A3Rect setX(final float x);
    A3Rect setY(final float y);
    A3Rect setWidth(final float width);
    A3Rect setHeight(final float height);
    A3Rect setPos(final float x, final float y);
    A3Rect setPos(final A3Point pos);
    A3Rect setSize(final float width, final float height);
    A3Rect setSize(final A3Size size);

    void set(final float[] values);
    void set(final float x, final float y, final float width, final float height);
    void set(final A3Point pos, final A3Size size);

    default boolean isSquare() {
        return getWidth() == getHeight();
    }

}
