package a3wt.awt;

import com.adonax.audiocue.AudioCue;
import com.adonax.audiocue.AudioMixer;
import a3wt.audio.A3Sound;
import a3wt.audio.A3SoundListener;

import javax.sound.sampled.LineUnavailableException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static a3wt.util.A3Preconditions.checkArgNotNull;

public class AWTA3Sound implements A3Sound {

    protected volatile boolean disposed = false;

    protected volatile float volume;
    protected volatile float speed;
    protected volatile int loops;

    protected final AudioMixer audioMixer;
    protected final AudioCue audioCue;
    protected volatile int instanceID;

    protected final List<A3SoundListener> listeners = new CopyOnWriteArrayList<>();

    public AWTA3Sound(final AudioMixer audioMixer, final AudioCue audioCue) {
        checkArgNotNull(audioMixer, "audioMixer");
        checkArgNotNull(audioCue, "audioCue");
        this.audioMixer = audioMixer;
        this.audioCue = audioCue;
        this.instanceID = audioCue.obtainInstance();
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

    private void apply() {
        audioCue.setVolume(instanceID, volume);
        audioCue.setLooping(instanceID, loops);
        audioCue.setSpeed(instanceID, speed);
    }

    @Override
    public void start() {
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
            for (final A3SoundListener listener : listeners) {
                listener.soundStarted();
            }
        }
    }

    @Override
    public void pause() {
        audioCue.stop(instanceID);
        for (final A3SoundListener listener : listeners) {
            listener.soundPaused();
        }
    }

    @Override
    public void resume() {
        audioCue.start(instanceID);
        for (final A3SoundListener listener : listeners) {
            listener.soundResumed();
        }
    }

    @Override
    public void stop() {
        if (audioCue.isPlaying(instanceID)) audioCue.stop(instanceID);
        if (audioCue.isPlayerRunning()) {
            audioCue.close();
            audioCue.releaseInstance(instanceID);
            instanceID = audioCue.obtainInstance();
        }
        if (audioMixer.getTrackCacheCount() < 1) {
            if (audioMixer.isMixerRunning()) audioMixer.stop();
        }
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
