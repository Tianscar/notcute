package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.bundle.A3ExtMapBundle;

public interface A3RoundRect extends A3Shape<A3RoundRect> {

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
    float getArcWidth();
    float getArcHeight();
    A3Size getCorner();
    void getCorner(final A3Size corner);

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

    A3RoundRect set(final float x, final float y, final float width, final float height, final float rx, final float ry);
    A3RoundRect set(final A3Point pos, final A3Size size, final A3Size corner);
    A3RoundRect set(final A3Rect rect, final A3Size corner);

    default boolean isSquareSize() {
        return getWidth() == getHeight();
    }
    default boolean isSquareCorner() {
        return getArcWidth() == getArcHeight();
    }

    String KEY_X = "x";
    String KEY_Y = "y";
    String KEY_WIDTH = "width";
    String KEY_HEIGHT = "height";
    String KEY_ARC_WIDTH = "arcWidth";
    String KEY_ARC_HEIGHT = "arcHeight";

    @Override
    default void save(final A3ExtMapBundle.Saver saver) {
        saver.putFloat(KEY_X, getX());
        saver.putFloat(KEY_Y, getY());
        saver.putFloat(KEY_WIDTH, getWidth());
        saver.putFloat(KEY_HEIGHT, getHeight());
        saver.putFloat(KEY_ARC_WIDTH, getArcWidth());
        saver.putFloat(KEY_ARC_HEIGHT, getArcHeight());
    }

    @Override
    default void restore(final A3ExtMapBundle.Restorer restorer) {
        set(restorer.getFloat(KEY_X, 0), restorer.getFloat(KEY_Y, 0),
                restorer.getFloat(KEY_WIDTH, 0), restorer.getFloat(KEY_HEIGHT, 0),
                restorer.getFloat(KEY_ARC_WIDTH, 0), restorer.getFloat(KEY_ARC_HEIGHT, 0));
    }

    @Override
    default Class<? extends A3ExtMapBundle.Delegate> typeClass() {
        return A3RoundRect.class;
    }
    
}
