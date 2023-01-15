package a3wt.graphics;

import a3wt.bundle.A3ExtMapBundle;

public interface A3Oval extends A3Rectangular<A3Oval> {

    default A3Oval set(final float x, final float y, final float width, final float height) {
        return setRect(x, y, width, height);
    }
    default A3Oval set(final A3Point pos, final A3Size size) {
        return setRect(pos, size);
    }

    default boolean isCircle() {
        return getWidth() == getHeight();
    }

    @Override
    default Class<? extends A3ExtMapBundle.Bundleable> typeClass() {
        return A3Oval.class;
    }

}
