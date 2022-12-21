package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.bundle.A3ExtensiveBundle;
import com.ansdoship.a3wt.util.A3Copyable;
import com.ansdoship.a3wt.util.A3Resetable;

public interface A3Coordinate extends A3Copyable<A3Coordinate>, A3Resetable, A3ExtensiveBundle.Delegate {

    int getX();
    int getY();

    A3Coordinate setX(final int x);
    A3Coordinate setY(final int y);

    void set(final int x, int y);

    String KEY_X = "x";
    String KEY_Y = "y";

    @Override
    default void save(final A3ExtensiveBundle.Saver saver) {
        saver.putInt(KEY_X, getX());
        saver.putInt(KEY_Y, getY());
    }

    @Override
    default void restore(final A3ExtensiveBundle.Restorer restorer) {
        set(restorer.getInt(KEY_X, 0), restorer.getInt(KEY_Y, 0));
    }

}
