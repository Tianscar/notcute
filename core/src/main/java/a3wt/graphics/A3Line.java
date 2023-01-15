package a3wt.graphics;

import a3wt.bundle.A3ExtMapBundle;

public interface A3Line extends A3Linear<A3Line> {

    boolean intersectsLine(final A3Line line);
    boolean intersectsLine(final float startX, final float startY, final float endX, final float endY);
    default boolean intersectsLine(final A3Point startPos, final A3Point endPos) {
        return intersectsLine(startPos.getX(), startPos.getY(), endPos.getX(), endPos.getY());
    }

    default A3Line set(final float startX, final float startY, final float endX, final float endY) {
        return setLine(startX, startY, endX, endY);
    }
    default A3Line set(final A3Point startPos, final A3Point endPos) {
        return setLine(startPos, endPos);
    }

    @Override
    default Class<? extends A3ExtMapBundle.Bundleable> typeClass() {
        return A3Line.class;
    }
    
}
