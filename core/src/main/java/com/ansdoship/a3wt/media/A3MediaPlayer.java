package com.ansdoship.a3wt.media;

import com.ansdoship.a3wt.util.A3Disposable;

import java.util.List;

public interface A3MediaPlayer extends A3Disposable {

    class Mode {
        private Mode(){}
        public static final int STATIC = 0;
        public static final int STREAM = 1;
    }

    boolean loadAudio(A3Audio audio, int mode);
    void playAudio(A3Audio audio);
    void pauseAudio(A3Audio audio);
    void stopAudio(A3Audio audio);
    void unloadAudio(A3Audio audio);

    void addAudioListener(A3AudioListener listener);
    List<A3AudioListener> getAudioListeners();

}
