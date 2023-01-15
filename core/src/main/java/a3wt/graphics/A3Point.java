package a3wt.graphics;

import a3wt.bundle.A3ExtMapBundle;
import a3wt.util.A3MutableCopyable;
import a3wt.util.A3Resetable;

public interface A3Point extends A3MutableCopyable<A3Point>, A3Resetable<A3Point>, A3ExtMapBundle.Bundleable {

    float getX();
    float getY();

    A3Point setX(final float x);
    A3Point setY(final float y);

    A3Point set(final float x, final float y);

    String KEY_X = "x";
    String KEY_Y = "y";

    @Override
    default void save(final A3ExtMapBundle.Saver saver) {
        saver.putFloat(KEY_X, getX());
        saver.putFloat(KEY_Y, getY());
    }

    @Override
    default void restore(final A3ExtMapBundle.Restorer restorer) {
        set(restorer.getFloat(KEY_X, 0), restorer.getFloat(KEY_Y, 0));
    }

    @Override
    default Class<? extends A3ExtMapBundle.Bundleable> typeClass() {
        return A3Point.class;
    }

}
