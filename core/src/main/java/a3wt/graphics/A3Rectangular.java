package a3wt.graphics;

import a3wt.bundle.A3ExtMapBundle;

public interface A3Rectangular<T extends A3Rectangular<T>> extends A3Shape<T> {

    float getLeft();
    float getTop();
    float getRight();
    float getBottom();
    float getCenterX();
    float getCenterY();
    A3Point getCenter();
    void getCenter(final A3Point pos);
    float getX();
    float getY();
    A3Point getPos();
    void getPos(final A3Point pos);
    float getWidth();
    float getHeight();
    A3Size getSize();
    void getSize(final A3Size size);

    T setLeft(final float left);
    T setTop(final float top);
    T setRight(final float right);
    T setBottom(final float bottom);
    T setBounds(final float left, final float top, final float right, final float bottom);
    T setCenterX(final float centerX);
    T setCenterY(final float centerY);
    T setCenter(final A3Point center);
    T setX(final float x);
    T setY(final float y);
    T setWidth(final float width);
    T setHeight(final float height);
    T setPos(final float x, final float y);
    T setPos(final A3Point pos);
    T setSize(final float width, final float height);
    T setSize(final A3Size size);

    T setRect(final float x, final float y, final float width, final float height);
    T setRect(final A3Point pos, final A3Size size);

    default boolean isEmpty() {
        return getWidth() <= 0.f || getHeight() <= 0.f;
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
        setRect(restorer.getFloat(KEY_X, 0), restorer.getFloat(KEY_Y, 0),
                restorer.getFloat(KEY_WIDTH, 0), restorer.getFloat(KEY_HEIGHT, 0));
    }

}
