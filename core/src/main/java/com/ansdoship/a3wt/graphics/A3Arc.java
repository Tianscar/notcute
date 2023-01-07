package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.bundle.A3ExtMapBundle;

public interface A3Arc extends A3Shape<A3Arc> {

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
    A3Rect getRect();
    void getRect(final A3Rect rect);
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
    A3Arc setRect(final A3Rect rect);
    A3Arc setStartAngle(final float startAngle);
    A3Arc setSweepAngle(final float sweepAngle);
    A3Arc setUseCenter(final boolean useCenter);

    A3Arc set(final float x, final float y, final float width, final float height, final float startAngle, final float sweepAngle, final boolean useCenter);
    A3Arc set(final A3Point pos, final A3Size size, final float startAngle, final float sweepAngle, final boolean useCenter);
    A3Arc set(final A3Rect rect, final float startAngle, final float sweepAngle, final boolean useCenter);

    String KEY_X = "x";
    String KEY_Y = "y";
    String KEY_WIDTH = "width";
    String KEY_HEIGHT = "height";
    String KEY_START_ANGLE = "startAngle";
    String KEY_SWEEP_ANGLE = "sweepAngle";
    String KEY_USE_CENTER = "useCenter";

    @Override
    default void save(final A3ExtMapBundle.Saver saver) {
        saver.putFloat(KEY_X, getX());
        saver.putFloat(KEY_Y, getY());
        saver.putFloat(KEY_WIDTH, getWidth());
        saver.putFloat(KEY_HEIGHT, getHeight());
        saver.putFloat(KEY_START_ANGLE, getStartAngle());
        saver.putFloat(KEY_SWEEP_ANGLE, getSweepAngle());
        saver.putBoolean(KEY_USE_CENTER, isUseCenter());
    }

    @Override
    default void restore(final A3ExtMapBundle.Restorer restorer) {
        set(restorer.getFloat(KEY_X, 0), restorer.getFloat(KEY_Y, 0),
                restorer.getFloat(KEY_WIDTH, 0), restorer.getFloat(KEY_HEIGHT, 0),
                restorer.getFloat(KEY_START_ANGLE, 0), restorer.getFloat(KEY_SWEEP_ANGLE, 0),
                restorer.getBoolean(KEY_USE_CENTER, false));
    }

    @Override
    default Class<? extends A3ExtMapBundle.Delegate> typeClass() {
        return A3Arc.class;
    }

}
