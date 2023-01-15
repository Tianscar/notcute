package a3wt.graphics;

import a3wt.bundle.A3ExtMapBundle;
import a3wt.util.A3MutableCopyable;
import a3wt.util.A3Resetable;

public interface A3Size extends A3MutableCopyable<A3Size>, A3Resetable<A3Size>, A3ExtMapBundle.Bundleable {

    float getWidth();
    float getHeight();

    A3Size setWidth(final float width);
    A3Size setHeight(final float height);

    A3Size set(final float width, final float height);

    String KEY_WIDTH = "width";
    String KEY_HEIGHT = "height";

    @Override
    default void save(final A3ExtMapBundle.Saver saver) {
        saver.putFloat(KEY_WIDTH, getWidth());
        saver.putFloat(KEY_HEIGHT, getHeight());
    }

    @Override
    default void restore(final A3ExtMapBundle.Restorer restorer) {
        set(restorer.getFloat(KEY_WIDTH, 0), restorer.getFloat(KEY_HEIGHT, 0));
    }

    @Override
    default Class<? extends A3ExtMapBundle.Bundleable> typeClass() {
        return A3Size.class;
    }
    
}
