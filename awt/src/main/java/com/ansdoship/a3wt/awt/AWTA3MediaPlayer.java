package com.ansdoship.a3wt.awt;

import com.adonax.audiocue.AudioCue;
import com.adonax.audiocue.AudioCueListener;
import com.adonax.audiocue.AudioMixer;
import com.adonax.audiocue.AudioCueInstanceEvent;
import com.adonax.audiocue.AudioMixerTrack;
import com.ansdoship.a3wt.media.A3Audio;
import com.ansdoship.a3wt.media.A3AudioListener;
import com.ansdoship.a3wt.media.A3MediaPlayer;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import static com.ansdoship.a3wt.util.A3Asserts.checkArgNotNull;

public class AWTA3MediaPlayer implements A3MediaPlayer, AudioCueListener {

    protected static final AudioMixer mixer = new AudioMixer();

    protected final Map<A3Audio, AudioMixerTrack> keys = new ConcurrentHashMap<>();
    protected final Map<AudioCue, Integer> values = new ConcurrentHashMap<>();
    protected final ReentrantLock unloadLock = new ReentrantLock(true);
    protected volatile boolean disposed = false;

    protected final List<A3AudioListener> audioListeners = new CopyOnWriteArrayList<>();

    private static void apply(final A3Audio audio, final AudioCue audioCue, final int instanceID) {
        audioCue.setVolume(instanceID, audio.getVolume());
        audioCue.setPan(instanceID, audio.getPan());
        audioCue.setSpeed(instanceID, audio.getSpeed());
        audioCue.setLooping(instanceID, audio.getLooping());
        audioCue.setFramePosition(instanceID, audio.getFramePos());
    }

    @Override
    public boolean loadAudio(final A3Audio audio, final int mode) {
        checkArgNotNull(audio, "audio");
        AudioMixerTrack track = null;
        try {
            if (mode == Mode.STATIC) {
                for (A3Audio a : keys.keySet()) {
                    if (((AWTA3Audio)a).matchMD5((AWTA3Audio)audio)) {
                        track = AudioCue.makeStereoCue(((AudioCue)keys.get(a)).getCue(), audio.toString(), 1);
                        break;
                    }
                }
                if (track == null) track = AudioCue.makeStereoCue(((AWTA3Audio)audio).getStream(), audio.toString(), 1);
                values.put((AudioCue) track, ((AudioCue) track).obtainInstance());
            }
            else {
                track = new StreamedAudioMixerTrack(((AWTA3Audio)audio).getStream());
            }
        } catch (UnsupportedAudioFileException | IOException e) {
            return false;
        }
        if (track instanceof AudioCue) ((AudioCue) track).addAudioCueListener(this);
        keys.put(audio, track);
        for (final A3AudioListener listener : audioListeners) {
            listener.audioLoaded();
        }
        return true;
    }

    @Override
    public void playAudio(final A3Audio audio) {
        checkArgNotNull(audio, "audio");
        if (!mixer.isRunning()) {
            try {
                mixer.start();
            } catch (LineUnavailableException e) {
                return;
            }
        }
        final AudioMixerTrack track = keys.get(audio);
        if (track == null) return;
        if (track instanceof AudioCue) {
            final AudioCue audioCue = (AudioCue) track;
            final int instanceID = values.get(audioCue);
            if (!audioCue.isRunning()) audioCue.open(mixer);
            if (!audioCue.isPlaying(instanceID)) {
                apply(audio, audioCue, instanceID);
                audioCue.start(instanceID);
            }
        }
        else if (track instanceof StreamedAudioMixerTrack) {
            final StreamedAudioMixerTrack mixerTrack = (StreamedAudioMixerTrack) track;
            if (!mixerTrack.isRunning()) mixerTrack.open(mixer);
            if (!mixerTrack.isPlaying()) {
                mixerTrack.start();
            }
        }
    }

    @Override
    public void pauseAudio(final A3Audio audio) {
        checkArgNotNull(audio, "audio");
        final AudioMixerTrack track = keys.get(audio);
        if (track == null) return;
        if (track instanceof AudioCue) {
            final AudioCue audioCue = (AudioCue) track;
            final int instanceID = values.get(audioCue);
            if (audioCue.isPlaying(instanceID)) audioCue.stop(instanceID);
        }
        else if (track instanceof StreamedAudioMixerTrack) {
            final StreamedAudioMixerTrack mixerTrack = (StreamedAudioMixerTrack) track;
            if (mixerTrack.isPlaying()) mixerTrack.stop();
        }
    }

    @Override
    public void stopAudio(final A3Audio audio) {
        checkArgNotNull(audio, "audio");
        final AudioMixerTrack track = keys.get(audio);
        if (track == null) return;
        if (track instanceof AudioCue) {
            final AudioCue audioCue = (AudioCue) track;
            int instanceID = values.get(audioCue);
            if (audioCue.isPlaying(instanceID)) audioCue.stop(instanceID);
            if (audioCue.isRunning()) {
                final float volume = audioCue.getVolume(instanceID);
                final float pan = audioCue.getPan(instanceID);
                final double speed = audioCue.getSpeed(instanceID);
                final int looping = audioCue.getLooping(instanceID);
                audioCue.close();
                audioCue.releaseInstance(instanceID);
                instanceID = audioCue.obtainInstance();
                values.put(audioCue, instanceID);
                audioCue.setVolume(instanceID, volume);
                audioCue.setPan(instanceID, pan);
                audioCue.setSpeed(instanceID, speed);
                audioCue.setLooping(instanceID, looping);
            }
        }
        else if (track instanceof StreamedAudioMixerTrack) {
            ((StreamedAudioMixerTrack) track).close();
            keys.put(audio, new StreamedAudioMixerTrack(((AWTA3Audio)audio).getStream()));
        }
        if (mixer.getTrackCacheCount() < 1) {
            if (mixer.isRunning()) mixer.stop();
        }
    }

    @Override
    public void unloadAudio(final A3Audio audio) {
        checkArgNotNull(audio, "audio");
        unloadLock.lock();
        try {
            mUnloadAudio(audio);
        }
        finally {
            unloadLock.unlock();
        }
    }

    @Override
    public void addAudioListener(final A3AudioListener listener) {
        checkArgNotNull(listener, "listener");
        audioListeners.add(listener);
    }

    @Override
    public List<A3AudioListener> getAudioListeners() {
        return audioListeners;
    }

    private void mUnloadAudio(final A3Audio audio) {
        final AudioMixerTrack track = keys.get(audio);
        if (track == null) return;
        keys.remove(audio);
        if (track instanceof AudioCue) {
            final AudioCue audioCue = (AudioCue) track;
            values.remove(audioCue);
            if (audioCue.isRunning()) {
                audioCue.close();
            }
        }
        else if (track instanceof StreamedAudioMixerTrack) {
            final StreamedAudioMixerTrack mixerTrack = (StreamedAudioMixerTrack) track;
            if (mixerTrack.isRunning()) {
                mixerTrack.close();
            }
        }
        if (mixer.getTrackCacheCount() < 1) {
            if (mixer.isRunning()) mixer.stop();
        }
        for (final A3AudioListener listener : audioListeners) {
            listener.audioUnloaded();
        }
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public void dispose() {
        if (disposed) return;
        disposed = true;
        for (A3Audio audio : keys.keySet()) {
            mUnloadAudio(audio);
        }
    }

    @Override
    public void audioCueOpened(final long now, final int threadPriority, final int bufferSize, final AudioCue source) {
    }

    @Override
    public void audioCueClosed(final long now, final AudioCue source) {
        if (disposed) return;
        for (final A3AudioListener listener : audioListeners) {
            listener.audioStopped();
        }
    }

    @Override
    public void instanceEventOccurred(final AudioCueInstanceEvent event) {
        for (final A3AudioListener listener : audioListeners) {
            switch (event.type) {
                case START_INSTANCE:
                    listener.audioStarted();
                    break;
                case STOP_INSTANCE:
                    listener.audioPaused();
                    break;
                case LOOP:
                    listener.audioLooped();
                    break;
            }
        }
        if (event.type == AudioCueInstanceEvent.Type.STOP_INSTANCE) {
            for (final A3Audio audio : keys.keySet()) {
                if (keys.get(audio) == event.source) {
                    stopAudio(audio);
                    break;
                }
            }
        }
    }

}
