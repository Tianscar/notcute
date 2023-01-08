package com.ansdoship.a3wt.audio;

import com.ansdoship.a3wt.util.A3Disposable;
import com.ansdoship.a3wt.util.A3Resetable;

import java.io.IOException;
import java.util.List;

public interface A3Music extends A3Disposable, A3Resetable<A3Music> {

    A3Music setVolume(final float volume);
    A3Music setLooping(final int loops);
    A3Music setMillisecondPos(final int pos);

    float getVolume();
    int getLooping();
    int getMillisecondPos();

    void prepare() throws IOException;
    void start();
    void pause();
    void resume();
    void stop();

    int getMillisecondLength();

    void addMusicListener(final A3MusicListener listener);
    List<A3MusicListener> getMusicListeners();

    @Override
    default A3Music reset() {
        setVolume(1.0f);
        setLooping(0);
        setMillisecondPos(0);
        return this;
    }

}
