package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.util.A3Copyable;
import com.ansdoship.a3wt.util.A3Disposable;

public interface A3Image extends A3Disposable, A3Copyable<A3Image> {

    A3Graphics getGraphics();
    int getWidth();
    int getHeight();
    int getPixel(int x, int y);
    void setPixel(int x, int y, int color);
    void getPixels(int[] pixels, int offset, int stride, int x, int y, int width, int height);
    void setPixels(int[] pixels, int offset, int stride, int x, int y, int width, int height);

}
