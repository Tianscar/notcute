package com.ansdoship.a3wt.graphics;

public interface A3RoundRect extends A3Shape<A3RoundRect> {

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
    float getArcWidth();
    float getArcHeight();
    A3Size getCorner();

    void get(final float[] values);

    A3RoundRect setLeft(final float left);
    A3RoundRect setTop(final float top);
    A3RoundRect setRight(final float right);
    A3RoundRect setBottom(final float bottom);
    A3RoundRect setBounds(final float left, final float top, final float right, final float bottom);
    A3RoundRect setX(final float x);
    A3RoundRect setY(final float y);
    A3RoundRect setWidth(final float width);
    A3RoundRect setHeight(final float height);
    A3RoundRect setPos(final float x, final float y);
    A3RoundRect setPos(final A3Point pos);
    A3RoundRect setSize(final float width, final float height);
    A3RoundRect setSize(final A3Size size);
    A3RoundRect setRect(final float x, final float y, final float width, final float height);
    A3RoundRect setRect(final A3Point pos, final A3Size size);
    A3RoundRect setArcWidth(final float rx);
    A3RoundRect setArcHeight(final float ry);
    A3RoundRect setCorner(final A3Size corner);

    void set(final float[] values);
    void set(final float x, final float y, final float width, final float height, final float rx, final float ry);
    void set(final A3Point pos, final A3Size size, final A3Size corner);


    default boolean isSquareSize() {
        return getWidth() == getHeight();
    }

    default boolean isSquareCorner() {
        return getArcWidth() == getArcHeight();
    }
    
}
