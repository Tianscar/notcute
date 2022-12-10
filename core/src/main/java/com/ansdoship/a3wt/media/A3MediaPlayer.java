package com.ansdoship.a3wt.media;

import com.ansdoship.a3wt.util.A3Disposable;

import java.util.List;

public interface A3MediaPlayer extends A3Disposable {

    class Mode {
        private Mode(){}
        public static final int STATIC = 0;
        public static final int STREAM = 1;
    }

    boolean loadAudio(final A3Audio audio, final int mode);
    void playAudio(final A3Audio audio);
    void pauseAudio(final A3Audio audio);
    void stopAudio(final A3Audio audio);
    void unloadAudio(final A3Audio audio);

    void addAudioListener(final A3AudioListener listener);
    List<A3AudioListener> getAudioListeners();

}
