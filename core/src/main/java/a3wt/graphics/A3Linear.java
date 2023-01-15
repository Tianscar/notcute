package a3wt.graphics;

import a3wt.bundle.A3ExtMapBundle;

public interface A3Linear<T extends A3Linear<T>> extends A3Shape<T> {

    float getStartX();
    float getStartY();
    float getEndX();
    float getEndY();
    A3Point getStartPos();
    void getStartPos(final A3Point pos);
    A3Point getEndPos();
    void getEndPos(final A3Point pos);

    T setStartX(final float startX);
    T setStartY(final float startY);
    T setEndX(final float endX);
    T setEndY(final float endY);
    T setStartPos(final A3Point pos);
    T setEndPos(final A3Point pos);

    T setLine(final float startX, final float startY, final float endX, final float endY);
    T setLine(final A3Point startPos, final A3Point endPos);

    String KEY_START_X = "startX";
    String KEY_START_Y = "startY";
    String KEY_END_X = "endX";
    String KEY_END_Y = "endY";

    @Override
    default void save(final A3ExtMapBundle.Saver saver) {
        saver.putFloat(KEY_START_X, getStartX());
        saver.putFloat(KEY_START_Y, getStartY());
        saver.putFloat(KEY_END_X, getEndX());
        saver.putFloat(KEY_END_Y, getEndY());
    }

    @Override
    default void restore(final A3ExtMapBundle.Restorer restorer) {
        setLine(restorer.getFloat(KEY_START_X, 0), restorer.getFloat(KEY_START_Y, 0),
                restorer.getFloat(KEY_END_X, 0), restorer.getFloat(KEY_END_Y, 0));
    }

}
