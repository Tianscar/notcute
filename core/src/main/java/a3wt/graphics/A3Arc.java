package a3wt.graphics;

import a3wt.bundle.A3ExtMapBundle;

public interface A3Arc extends A3Rectangular<A3Arc> {

    float getStartX();
    float getStartY();
    float getEndX();
    float getEndY();
    A3Point getStartPos();
    void getStartPos(final A3Point pos);
    A3Point getEndPos();
    void getEndPos(final A3Point pos);

    A3Rect getRect();
    void getRect(final A3Rect rect);
    float getStartAngle();
    float getSweepAngle();
    boolean isUseCenter();

    A3Arc setRect(final A3Rect rect);
    A3Arc setStartAngle(final float startAngle);
    A3Arc setSweepAngle(final float sweepAngle);
    A3Arc setUseCenter(final boolean useCenter);

    A3Arc set(final float x, final float y, final float width, final float height, final float startAngle, final float sweepAngle, final boolean useCenter);
    A3Arc set(final A3Point pos, final A3Size size, final float startAngle, final float sweepAngle, final boolean useCenter);
    A3Arc set(final A3Rect rect, final float startAngle, final float sweepAngle, final boolean useCenter);

    String KEY_START_ANGLE = "startAngle";
    String KEY_SWEEP_ANGLE = "sweepAngle";
    String KEY_USE_CENTER = "useCenter";

    @Override
    default void save(final A3ExtMapBundle.Saver saver) {
        A3Rectangular.super.save(saver);
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
    default Class<? extends A3ExtMapBundle.Bundleable> typeClass() {
        return A3Arc.class;
    }

}
