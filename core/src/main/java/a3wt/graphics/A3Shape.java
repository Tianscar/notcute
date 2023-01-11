package a3wt.graphics;

import a3wt.util.A3Copyable;
import a3wt.bundle.A3ExtMapBundle;
import a3wt.util.A3Resetable;

public interface A3Shape<T extends A3Shape<T>> extends A3Copyable<T>, A3Boundable, A3Resetable<T>, A3ExtMapBundle.Delegate {

    boolean contains(final float x, final float y);
    boolean contains(final A3Point pos);

    boolean contains(final float x, final float y, final float width, final float height);
    boolean contains(final A3Rect rect);

}
