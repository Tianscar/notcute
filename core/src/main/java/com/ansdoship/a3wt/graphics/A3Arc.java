package com.ansdoship.a3wt.graphics;

public interface A3Arc extends A3Shape<A3Arc> {

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
    float getStartAngle();
    float getSweepAngle();
    boolean isUseCenter();

    A3Arc setLeft(final float left);
    A3Arc setTop(final float top);
    A3Arc setRight(final float right);
    A3Arc setBottom(final float bottom);
    A3Arc setBounds(final float left, final float top, final float right, final float bottom);
    A3Arc setX(final float x);
    A3Arc setY(final float y);
    A3Arc setWidth(final float width);
    A3Arc setHeight(final float height);
    A3Arc setPos(final float x, final float y);
    A3Arc setPos(final A3Point pos);
    A3Arc setSize(final float width, final float height);
    A3Arc setSize(final A3Size size);
    A3Arc setRect(final float x, final float y, final float width, final float height);
    A3Arc setRect(final A3Point pos, final A3Size size);
    A3Arc setStartAngle(final float startAngle);
    A3Arc setSweepAngle(final float sweepAngle);
    A3Arc setUseCenter(final boolean useCenter);

    void set(final float x, final float y, final float width, final float height, final float startAngle, final float sweepAngle, final boolean useCenter);
    void set(final A3Point pos, final A3Size size, final float startAngle, final float sweepAngle, final boolean useCenter);
    void set(final A3Rect rect, final float startAngle, final float sweepAngle, final boolean useCenter);
    
}
