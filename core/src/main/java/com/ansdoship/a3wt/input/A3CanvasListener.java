package com.ansdoship.a3wt.input;

import com.ansdoship.a3wt.graphics.A3Graphics;

public interface A3CanvasListener {

    void canvasCreated();
    void canvasDisposed();
    void canvasResized(int width, int height);
    void canvasMoved(int x, int y);
    void canvasShown();
    void canvasHidden();
    void canvasPainted(A3Graphics graphics);
    void canvasFocusGained();
    void canvasFocusLost();

}
