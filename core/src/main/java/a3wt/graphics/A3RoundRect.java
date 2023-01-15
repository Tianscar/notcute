package a3wt.graphics;

import a3wt.bundle.A3ExtMapBundle;

public interface A3RoundRect extends A3Rectangular<A3RoundRect> {

    float getArcWidth();
    float getArcHeight();
    A3Size getCorner();
    void getCorner(final A3Size corner);

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

    String KEY_ARC_WIDTH = "arcWidth";
    String KEY_ARC_HEIGHT = "arcHeight";

    @Override
    default void save(final A3ExtMapBundle.Saver saver) {
        A3Rectangular.super.save(saver);
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
    default Class<? extends A3ExtMapBundle.Bundleable> typeClass() {
        return A3RoundRect.class;
    }
    
}
