package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.util.A3Copyable;

public interface A3Coordinate extends A3Copyable<A3Coordinate> {

    int getX();
    int getY();

    void get(final int[] values);

    A3Coordinate setX(final int x);
    A3Coordinate setY(final int y);

    void set(final int[] values);
    void set(final int x, int y);

}
