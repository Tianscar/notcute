package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.util.A3Copyable;
import com.ansdoship.a3wt.bundle.A3ExtensiveBundle;
import com.ansdoship.a3wt.util.A3Resetable;

public interface A3Shape<T extends A3Shape<T>> extends A3Copyable<T>, A3Boundable, A3Resetable, A3ExtensiveBundle.Delegate {

    boolean contains(final float x, final float y);
    boolean contains(final A3Point pos);

    boolean contains(final float x, final float y, final float width, final float height);
    boolean contains(final A3Rect rect);

}
