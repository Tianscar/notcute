package com.ansdoship.a3wt.graphics;

public interface A3Canvas {

    int getWidth();
    int getHeight();

    long elapsed();
    void paint(A3Graphics graphics);
    void update();

    A3Image snapshot();
    A3Image snapshotBuffer();

}
