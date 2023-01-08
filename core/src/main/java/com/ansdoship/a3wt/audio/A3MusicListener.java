package com.ansdoship.a3wt.audio;

public interface A3MusicListener {

    void musicPrepared();
    void musicStarted();
    void musicPaused();
    void musicResumed();
    void musicStopped(final int loopsLeft);
    void musicDisposed();

}
