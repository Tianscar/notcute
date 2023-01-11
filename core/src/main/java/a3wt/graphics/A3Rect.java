package a3wt.graphics;

import a3wt.bundle.A3ExtMapBundle;

public interface A3Rect extends A3Shape<A3Rect> {

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

    A3Rect set(final float x, final float y, final float width, final float height);
    A3Rect set(final A3Point pos, final A3Size size);

    default boolean isSquare() {
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
        return A3Rect.class;
    }

}
