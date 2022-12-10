package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.util.A3Copyable;

public interface A3Size extends A3Copyable<A3Size> {

    float getWidth();
    float getHeight();

    void get(final float[] values);

    A3Size setWidth(final float width);
    A3Size setHeight(final float height);

    void set(final float[] values);
    void set(final float width, final float height);
    
}
