package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.util.A3Copyable;

public interface A3Point extends A3Copyable<A3Point> {

    float getX();
    float getY();

    void get(final float[] values);

    A3Point setX(final float x);
    A3Point setY(final float y);

    void set(final float[] values);
    void set(final float x, final float y);

}
