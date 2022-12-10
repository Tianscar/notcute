package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.util.A3Copyable;

public interface A3Dimension extends A3Copyable<A3Dimension> {

    int getWidth();
    int getHeight();

    void get(final int[] values);

    A3Dimension setWidth(final int width);
    A3Dimension setHeight(final int height);

    void set(final int[] values);
    void set(final int width, final int height);

}
