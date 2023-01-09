package com.ansdoship.a3wt.android;

import android.media.MediaPlayer;
import com.ansdoship.a3wt.audio.A3Music;
import com.ansdoship.a3wt.audio.A3MusicListener;
import com.ansdoship.a3wt.util.A3Math;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class AndroidA3Music implements A3Music, MediaPlayer.OnCompletionListener {

    protected volatile boolean disposed = false;
    protected volatile boolean prepared = false;

    protected volatile float volume;
    protected volatile int loops;
    protected AtomicInteger loopsLeft = new AtomicInteger();
    protected volatile int pos;

    protected final MediaPlayer mediaPlayer;

    protected final List<A3MusicListener> listeners = new CopyOnWriteArrayList<>();

    public AndroidA3Music(final MediaPlayer mediaPlayer) {
        checkArgNotNull(mediaPlayer, "mediaPlayer");
        this.mediaPlayer = mediaPlayer;
        reset();
        mediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public A3Music reset() {
        A3Music.super.reset();
        loopsLeft.set(loops);
        return this;
    }

    protected void apply() {
        mediaPlayer.setVolume(volume, volume);
        if (loopsLeft.get() != 0) mediaPlayer.setLooping(true);
        mediaPlayer.seekTo(A3Math.clamp(pos, 0, getMillisecondLength()));
    }

    @Override
    public A3Music setVolume(final float volume) {
        this.volume = volume;
        return this;
    }

    @Override
    public A3Music setLooping(final int loops) {
        this.loops = Math.max(-1, loops);
        loopsLeft.set(this.loops);
        return this;
    }

    @Override
    public A3Music setMillisecondPos(final int pos) {
        this.pos = pos;
        return this;
    }

    @Override
    public float getVolume() {
        return volume;
    }

    @Override
    public int getLooping() {
        return loops;
    }

    @Override
    public int getMillisecondPos() {
        return pos;
    }

    @Override
    public boolean isPrepared() {
        return prepared;
    }

    public void checkPrepared() {
        checkPrepared("Please prepare first!");
    }

    @Override
    public void prepare() throws IOException {
        mediaPlayer.prepare();
        prepared = true;
        for (final A3MusicListener listener : listeners) {
            listener.musicPrepared();
        }
    }

    @Override
    public void start() {
        checkPrepared();
        apply();
        mediaPlayer.start();
        for (final A3MusicListener listener : listeners) {
            listener.musicStarted();
        }
    }

    @Override
    public void pause() {
        checkPrepared();
        mediaPlayer.pause();
        for (final A3MusicListener listener : listeners) {
            listener.musicPaused();
        }
    }

    @Override
    public void resume() {
        checkPrepared();
        mediaPlayer.start();
        for (final A3MusicListener listener : listeners) {
            listener.musicResumed();
        }
    }

    @Override
    public void stop() {
        checkPrepared();
        mediaPlayer.stop();
        prepared = false;
    }

    @Override
    public int getMillisecondLength() {
        return mediaPlayer.getDuration();
    }

    @Override
    public void addMusicListener(final A3MusicListener listener) {
        checkArgNotNull(listener, "listener");
        listeners.add(listener);
    }

    @Override
    public List<A3MusicListener> getMusicListeners() {
        return listeners;
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public void dispose() {
        if (isDisposed()) return;
        disposed = true;
        mediaPlayer.release();
        for (final A3MusicListener listener : listeners) {
            listener.musicDisposed();
        }
    }

    @Override
    public void onCompletion(final MediaPlayer mp) {
        if (loopsLeft.get() > 0) loopsLeft.set(loopsLeft.get() - 1);
        if (loopsLeft.get() == 0) mp.setLooping(false);
        for (final A3MusicListener listener : listeners) {
            listener.musicStopped(loopsLeft.get());
        }
    }

}
