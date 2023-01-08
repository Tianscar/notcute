package com.ansdoship.a3wt.android;

import android.media.SoundPool;
import com.ansdoship.a3wt.audio.A3Sound;
import com.ansdoship.a3wt.audio.A3SoundListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class AndroidA3Sound implements A3Sound {

    protected volatile boolean disposed = false;

    protected volatile float volume;
    protected volatile float speed;
    protected volatile int loops;

    protected final SoundPool soundPool;
    protected final int soundId;

    protected final List<A3SoundListener> listeners = new CopyOnWriteArrayList<>();

    public AndroidA3Sound(final SoundPool soundPool, final int soundId) {
        checkArgNotNull(soundPool, "soundPool");
        this.soundPool = soundPool;
        this.soundId = soundId;
        reset();
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public void dispose() {
        if (isDisposed()) return;
        disposed = true;
        soundPool.unload(soundId);
        for (final A3SoundListener listener : listeners) {
            listener.soundDisposed();
        }
    }

    @Override
    public A3Sound setVolume(final float volume) {
        this.volume = volume;
        return this;
    }

    @Override
    public A3Sound setSpeed(final float speed) {
        this.speed = speed;
        return this;
    }

    @Override
    public A3Sound setLooping(final int loops) {
        this.loops = Math.max(-1, loops);
        return this;
    }

    @Override
    public float getVolume() {
        return volume;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public int getLooping() {
        return loops;
    }

    @Override
    public void start() {
        soundPool.play(soundId, volume, volume, 1, loops, speed);
        for (final A3SoundListener listener : listeners) {
            listener.soundStarted();
        }
    }

    @Override
    public void pause() {
        soundPool.pause(soundId);
        for (final A3SoundListener listener : listeners) {
            listener.soundPaused();
        }
    }

    @Override
    public void resume() {
        soundPool.resume(soundId);
        for (final A3SoundListener listener : listeners) {
            listener.soundResumed();
        }
    }

    @Override
    public void stop() {
        soundPool.stop(soundId);
    }

    @Override
    public void addSoundListener(final A3SoundListener listener) {
        checkArgNotNull(listener, "listener");
        listeners.add(listener);
    }

    @Override
    public List<A3SoundListener> getSoundListeners() {
        return listeners;
    }

}
