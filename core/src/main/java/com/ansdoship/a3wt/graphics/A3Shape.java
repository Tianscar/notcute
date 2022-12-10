package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.util.A3Copyable;
import com.ansdoship.a3wt.bundle.A3ExtensiveBundle;

public interface A3Shape<T extends A3Shape<T>> extends A3Copyable<T>, A3ExtensiveBundle.Delegate {

    A3Rect getBounds();

    boolean contains(final float x, final float y);
    boolean contains(final A3Point xy);

}
