package com.ansdoship.a3wt.awt;

import com.adonax.audiocue.AudioCue;
import com.adonax.audiocue.AudioCueInstanceEvent;
import com.adonax.audiocue.AudioCueListener;
import com.adonax.audiocue.AudioMixer;
import com.ansdoship.a3wt.audio.A3Music;
import com.ansdoship.a3wt.audio.A3MusicListener;
import com.ansdoship.a3wt.util.A3Math;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.ansdoship.a3wt.awt.AWTA3AudioKit.getSupportedAudioInputStream;
import static com.ansdoship.a3wt.awt.AWTA3AudioKit.loadAudioInputStream;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class AWTA3Music implements A3Music, AudioCueListener {

    protected volatile boolean disposed = false;

    protected volatile float volume;
    protected volatile int loops;
    protected volatile int pos;

    protected final AudioMixer audioMixer;
    protected final URL url;

    protected volatile AudioCue audioCue = null;
    protected volatile boolean prepared = false;
    protected volatile int instanceID = -1;
    protected volatile int length = -1;

    protected final List<A3MusicListener> listeners = new CopyOnWriteArrayList<>();

    public AWTA3Music(final AudioMixer audioMixer, final URL url) {
        checkArgNotNull(audioMixer, "audioMixer");
        checkArgNotNull(url, "url");
        this.audioMixer = audioMixer;
        this.url = url;
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
        if (audioCue.isPlayerRunning()) {
            audioCue.close();
        }
        if (audioMixer.getTrackCacheCount() < 1) {
            if (audioMixer.isMixerRunning()) audioMixer.stop();
        }
        for (final A3MusicListener listener : listeners) {
            listener.musicDisposed();
        }
    }

    @Override
    public A3Music setVolume(final float volume) {
        this.volume = volume;
        return this;
    }

    @Override
    public A3Music setLooping(final int loops) {
        this.loops = Math.max(-1, loops);
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

    @Override
    public void prepare() throws IOException {
        if (audioCue != null && !prepared) {
            instanceID = audioCue.obtainInstance();
            return;
        }
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            ais = getSupportedAudioInputStream(ais);
            final float[] cue = loadAudioInputStream(ais);
            audioCue = AudioCue.makeStereoCue(cue, url.toString(), 1);
            instanceID = audioCue.obtainInstance();
            length = (int) (audioCue.getFrameLength() * 1000.0 / AudioCue.audioFormat.getFrameRate());
            prepared = true;
            for (final A3MusicListener listener : listeners) {
                listener.musicPrepared();
            }
        }
        catch (final UnsupportedAudioFileException e) {
            throw new IOException(e);
        }
    }

    private void apply() {
        audioCue.setVolume(instanceID, volume);
        audioCue.setLooping(instanceID, loops);
        final double frames = (AudioCue.audioFormat.getFrameRate() * A3Math.clamp(pos, 0, getMillisecondLength())) / 1000.0;
        audioCue.setFramePosition(instanceID, frames);
    }

    public void checkPrepared() {
        checkPrepared("Please prepare first!");
    }

    @Override
    public void start() {
        checkPrepared();
        if (!audioMixer.isMixerRunning()) {
            try {
                audioMixer.start();
            }
            catch (LineUnavailableException e) {
                return;
            }
        }
        if (!audioCue.isPlayerRunning()) audioCue.open(audioMixer);
        if (!audioCue.isPlaying(instanceID)) {
            apply();
            audioCue.setFramePosition(instanceID, 0);
            audioCue.start(instanceID);
            for (final A3MusicListener listener : listeners) {
                listener.musicStarted();
            }
        }
    }

    @Override
    public void pause() {
        checkPrepared();
        audioCue.stop(instanceID);
        for (final A3MusicListener listener : listeners) {
            listener.musicPaused();
        }
    }

    @Override
    public void resume() {
        checkPrepared();
        audioCue.start(instanceID);
        for (final A3MusicListener listener : listeners) {
            listener.musicResumed();
        }
    }

    @Override
    public void stop() {
        checkPrepared();
        if (audioCue.isPlaying(instanceID)) audioCue.stop(instanceID);
        if (audioCue.isPlayerRunning()) {
            audioCue.close();
            audioCue.releaseInstance(instanceID);
            instanceID = -1;
            prepared = false;
        }
        if (audioMixer.getTrackCacheCount() < 1) {
            if (audioMixer.isMixerRunning()) audioMixer.stop();
        }
    }

    @Override
    public int getMillisecondLength() {
        return length;
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
    public void audioCueOpened(final long now, final int threadPriority, final int bufferSize, final AudioCue source) {}

    @Override
    public void audioCueClosed(final long now, final AudioCue source) {}

    @Override
    public void instanceEventOccurred(final AudioCueInstanceEvent event) {
        if (event.instanceID != instanceID) return;
        switch (event.type) {
            case LOOP:
                for (final A3MusicListener listener : listeners) {
                    listener.musicStopped(audioCue.getLooping(instanceID));
                }
                break;
            case STOP_INSTANCE:
                for (final A3MusicListener listener : listeners) {
                    listener.musicStopped(0);
                }
                break;
        }
    }

}
