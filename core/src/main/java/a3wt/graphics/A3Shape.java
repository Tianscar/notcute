package a3wt.graphics;

import a3wt.util.A3MutableCopyable;
import a3wt.bundle.A3ExtMapBundle;
import a3wt.util.A3Resetable;

public interface A3Shape<T extends A3Shape<T>> extends A3MutableCopyable<T>, A3Boundable, A3Resetable<T>, A3ExtMapBundle.Bundleable {

    boolean contains(final float x, final float y);
    boolean contains(final A3Point pos);
    boolean contains(final float x, final float y, final float width, final float height);
    boolean contains(final A3Rect rect);
    default boolean contains(final A3Point pos, final A3Size size) {
        return contains(pos.getX(), pos.getY(), size.getWidth(), size.getHeight());
    }

    boolean intersects(final float x, final float y, final float width, final float height);
    boolean intersects(final A3Rect rect);
    default boolean intersects(final A3Point pos, final A3Size size) {
        return intersects(pos.getX(), pos.getY(), size.getWidth(), size.getHeight());
    }

}
