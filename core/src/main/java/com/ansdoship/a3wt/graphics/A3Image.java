package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.util.A3Copyable;
import com.ansdoship.a3wt.util.A3Disposable;

public interface A3Image extends A3Disposable, A3Copyable<A3Image> {

    class Type {
        public static final int ARGB_8888 = 0;
        public static final int RGB_565 = 1;
    }

    default boolean hasAlpha() {
        return getType() == Type.ARGB_8888;
    }

    A3Graphics createGraphics();
    int getWidth();
    int getHeight();
    int getPixel(final int x, final int y);
    A3Image setPixel(final int x, final int y, final int color);
    void getPixels(final int[] pixels, final int offset, final int stride, final int x, final int y, final int width, final int height);
    A3Image setPixels(final int[] pixels, final int offset, final int stride, final int x, final int y, final int width, final int height);

    long getDuration();
    A3Image setDuration(final long duration);

    int getHotSpotX();
    A3Image setHotSpotX(final int hotSpotX);
    int getHotSpotY();
    A3Image setHotSpotY(final int hotSpotY);
    A3Coordinate getHotSpot();
    A3Image setHotSpot(final A3Coordinate hotSpot);

    int getType();

    A3Image copy(final int type);

}
