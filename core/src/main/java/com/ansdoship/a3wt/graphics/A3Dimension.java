package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.bundle.A3ExtMapBundle;
import com.ansdoship.a3wt.util.A3Copyable;
import com.ansdoship.a3wt.util.A3Resetable;

public interface A3Dimension extends A3Copyable<A3Dimension>, A3Resetable<A3Dimension>, A3ExtMapBundle.Delegate {

    int getWidth();
    int getHeight();

    A3Dimension setWidth(final int width);
    A3Dimension setHeight(final int height);

    A3Dimension set(final int width, final int height);

    String KEY_WIDTH = "width";
    String KEY_HEIGHT = "height";

    @Override
    default void save(final A3ExtMapBundle.Saver saver) {
        saver.putInt(KEY_WIDTH, getWidth());
        saver.putInt(KEY_HEIGHT, getHeight());
    }

    @Override
    default void restore(final A3ExtMapBundle.Restorer restorer) {
        set(restorer.getInt(KEY_WIDTH, 0), restorer.getInt(KEY_HEIGHT, 0));
    }

    @Override
    default Class<? extends A3ExtMapBundle.Delegate> typeClass() {
        return A3Dimension.class;
    }

}
