package a3wt.graphics;

import a3wt.bundle.A3ExtMapBundle;

public interface A3Rect extends A3Rectangular<A3Rect> {

    boolean intersectsLine(final A3Line line);
    boolean intersectsLine(final float startX, final float startY, final float endX, final float endY);
    default boolean intersectsLine(final A3Point startPos, final A3Point endPos) {
        return intersectsLine(startPos.getX(), startPos.getY(), endPos.getX(), endPos.getY());
    }

    default A3Rect set(final float x, final float y, final float width, final float height) {
        return setRect(x, y, width, height);
    }
    default A3Rect set(final A3Point pos, final A3Size size) {
        return setRect(pos, size);
    }

    default boolean isSquare() {
        return getWidth() == getHeight();
    }

    @Override
    default Class<? extends A3ExtMapBundle.Bundleable> typeClass() {
        return A3Rect.class;
    }

}
