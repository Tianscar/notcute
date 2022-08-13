package com.ansdoship.a3wt.input;

import com.ansdoship.a3wt.graphics.A3Graphics;

public interface A3ContextListener {

    void contextCreated();
    void contextDisposed();
    void contextResized(int width, int height);
    void contextMoved(int x, int y);
    void contextShown();
    void contextHidden();
    void contextPainted(A3Graphics graphics);
    void contextFocusGained();
    void contextFocusLost();

}
