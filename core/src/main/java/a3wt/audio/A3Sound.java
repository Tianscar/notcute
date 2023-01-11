package a3wt.audio;

import a3wt.util.A3Disposable;
import a3wt.util.A3Resetable;

import java.util.List;

public interface A3Sound extends A3Disposable, A3Resetable<A3Sound> {

    A3Sound setVolume(final float volume);
    A3Sound setSpeed(final float speed);
    A3Sound setLooping(final int loops);

    float getVolume();
    float getSpeed();
    int getLooping();

    void start();
    void pause();
    void resume();
    void stop();

    void addSoundListener(final A3SoundListener listener);
    List<A3SoundListener> getSoundListeners();

    @Override
    default A3Sound reset() {
        setVolume(1.0f);
        setSpeed(1.0f);
        setLooping(0);
        return this;
    }

}
