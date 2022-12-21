package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.bundle.A3ExtensiveBundle;
import com.ansdoship.a3wt.util.A3Copyable;
import com.ansdoship.a3wt.util.A3Resetable;

public interface A3Dimension extends A3Copyable<A3Dimension>, A3Resetable, A3ExtensiveBundle.Delegate {

    int getWidth();
    int getHeight();

    A3Dimension setWidth(final int width);
    A3Dimension setHeight(final int height);

    void set(final int width, final int height);

    String KEY_WIDTH = "width";
    String KEY_HEIGHT = "height";

    @Override
    default void save(final A3ExtensiveBundle.Saver saver) {
        saver.putInt(KEY_WIDTH, getWidth());
        saver.putInt(KEY_HEIGHT, getHeight());
    }

    @Override
    default void restore(final A3ExtensiveBundle.Restorer restorer) {
        set(restorer.getInt(KEY_WIDTH, 0), restorer.getInt(KEY_HEIGHT, 0));
    }

}
