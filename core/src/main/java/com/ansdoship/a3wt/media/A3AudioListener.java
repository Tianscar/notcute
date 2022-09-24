package com.ansdoship.a3wt.media;

public interface A3AudioListener {

    void audioLoaded();
    void audioStarted();
    void audioPaused();
    void audioLooped();
    void audioStopped();
    void audioUnloaded();

}
