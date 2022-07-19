package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.input.A3CanvasListener;

import java.util.List;

public interface A3Canvas {

    int getWidth();
    int getHeight();

    long elapsed();
    void paint(A3Graphics graphics);
    void update();

    A3Image snapshot();
    A3Image snapshotBuffer();

    List<A3CanvasListener> getA3CanvasListeners();
    void addA3CanvasListener(A3CanvasListener listener);

}
