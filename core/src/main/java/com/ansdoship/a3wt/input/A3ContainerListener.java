package com.ansdoship.a3wt.input;

public interface A3ContainerListener {

    void containerCreated();
    void containerDisposed();
    void containerStarted();
    void containerStopped();
    void containerResumed();
    void containerPaused();
    void containerFocusGained();
    void containerFocusLost();
    void containerResized(int width, int height);
    void containerMoved(int x, int y);
    boolean containerCloseRequested();

}
