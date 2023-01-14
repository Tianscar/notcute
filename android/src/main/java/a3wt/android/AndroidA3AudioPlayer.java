package a3wt.android;

import a3wt.audio.A3MusicListener;
import a3wt.audio.A3SoundListener;
import a3wt.util.A3Collections;
import a3wt.util.A3Math;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import a3wt.app.A3Assets;
import a3wt.audio.A3AudioPlayer;
import a3wt.util.A3Arrays;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static a3wt.util.A3Preconditions.checkArgNotNull;

public class AndroidA3AudioPlayer implements A3AudioPlayer, MediaPlayer.OnCompletionListener {

    protected static final int SOUNDPOOL_MAX_STREAMS = 50;

    protected final SoundPool soundPool;
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(SOUNDPOOL_MAX_STREAMS)
                    .setAudioAttributes(new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                            .build()).build();
        }
        else soundPool = new SoundPool(SOUNDPOOL_MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
    }
    protected final List<AndroidMusic> musics = new CopyOnWriteArrayList<>();
    protected final List<AndroidSound> sounds = new CopyOnWriteArrayList<>();

    @Override
    public Sound loadSound(final File input) {
        checkArgNotNull(input, "input");
        final AndroidSound sound = new AndroidSound(soundPool.load(input.getAbsolutePath(), 1));
        sounds.add(sound);
        for (final A3SoundListener listener : soundListeners) {
            listener.soundLoaded(sound);
        }
        return sound;
    }

    @Override
    public Sound loadSound(final A3Assets assets, final String input) {
        checkArgNotNull(assets, "assets");
        checkArgNotNull(input, "input");
        try {
            final AndroidSound sound = new AndroidSound(soundPool.load(((AndroidA3Assets)assets).assets.openFd(input), 1));
            sounds.add(sound);
            for (final A3SoundListener listener : soundListeners) {
                listener.soundLoaded(sound);
            }
            return sound;
        }
        catch (final IOException e) {
            return null;
        }
    }

    @Override
    public Music loadMusic(final File input) {
        checkArgNotNull(input, "input");
        try {
            final MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(input.getAbsolutePath());
            mediaPlayer.setOnCompletionListener(this);
            final AndroidMusic music = new AndroidMusic(mediaPlayer);
            musics.add(music);
            for (final A3MusicListener listener : musicListeners) {
                listener.musicLoaded(music);
            }
            return music;
        }
        catch (final IOException e) {
            return null;
        }
    }

    @Override
    public Music loadMusic(final A3Assets assets, final String input) {
        checkArgNotNull(assets, "assets");
        checkArgNotNull(input, "input");
        try {
            final MediaPlayer mediaPlayer = new MediaPlayer();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mediaPlayer.setDataSource(((AndroidA3Assets)assets).assets.openFd(input));
            }
            else {
                final AssetFileDescriptor afd = ((AndroidA3Assets)assets).assets.openFd(input);
                mediaPlayer.setDataSource(afd.getFileDescriptor());
            }
            mediaPlayer.setOnCompletionListener(this);
            final AndroidMusic music = new AndroidMusic(mediaPlayer);
            musics.add(music);
            for (final A3MusicListener listener : musicListeners) {
                listener.musicLoaded(music);
            }
            return music;
        }
        catch (final IOException e) {
            return null;
        }
    }

    @Override
    public void unloadSound(final Sound sound) {
        checkArgNotNull(sound, "sound");
        sounds.add((AndroidSound) sound);
        soundPool.unload(((AndroidSound)sound).soundId);
        for (final A3SoundListener listener : soundListeners) {
            listener.soundUnloaded(sound);
        }
    }

    @Override
    public void unloadMusic(final Music music) {
        checkArgNotNull(music, "music");
        musics.remove((AndroidMusic) music);
        ((AndroidMusic)music).mediaPlayer.release();
        for (final A3MusicListener listener : musicListeners) {
            listener.musicUnloaded(music);
        }
    }

    private static final String[] LOADER_FORMAT_NAMES = new String[] {"wav", "mp3", "ogg", "flac", "3gp", "aac", "midi", "mid"};

    @Override
    public String[] getAudioLoaderFormatNames() {
        return A3Arrays.copy(LOADER_FORMAT_NAMES);
    }

    @Override
    public void prepareMusic(final Music music) throws IOException {
        checkArgNotNull(music, "music");
        final MediaPlayer mediaPlayer = ((AndroidMusic)music).mediaPlayer;
        mediaPlayer.prepare();
        ((AndroidMusic) music).setPrepared(true);
        for (final A3MusicListener listener : musicListeners) {
            listener.musicPrepared(music);
        }
    }

    @Override
    public boolean isMusicPrepared(final Music music) {
        checkArgNotNull(music, "music");
        return ((AndroidMusic) music).prepared;
    }

    private void checkMusicPrepared(final Music music) {
        if (!((AndroidMusic) music).prepared) throw new IllegalStateException("Please prepare the music first!");
    }

    @Override
    public void startMusic(final Music music) {
        checkArgNotNull(music, "music");
        checkMusicPrepared(music);
        ((AndroidMusic) music).apply();
        ((AndroidMusic) music).mediaPlayer.start();
        for (final A3MusicListener listener : musicListeners) {
            listener.musicStarted(music);
        }
    }

    @Override
    public void pauseMusic(final Music music) {
        checkArgNotNull(music, "music");
        checkMusicPrepared(music);
        ((AndroidMusic) music).mediaPlayer.pause();
        for (final A3MusicListener listener : musicListeners) {
            listener.musicPaused(music);
        }
    }

    @Override
    public void resumeMusic(final Music music) {
        checkArgNotNull(music, "music");
        checkMusicPrepared(music);
        ((AndroidMusic) music).mediaPlayer.start();
        for (final A3MusicListener listener : musicListeners) {
            listener.musicResumed(music);
        }
    }

    @Override
    public void stopMusic(final Music music) {
        checkArgNotNull(music, "music");
        checkMusicPrepared(music);
        ((AndroidMusic) music).mediaPlayer.stop();
        ((AndroidMusic) music).setPrepared(false);
    }

    @Override
    public void startSound(final Sound sound) {
        checkArgNotNull(sound, "sound");
        final AndroidSound androidSound = (AndroidSound) sound;
        if (soundPool.play(androidSound.soundId, androidSound.volume, androidSound.volume, 1, androidSound.loops, androidSound.speed) != 0) {
            for (final A3SoundListener listener : soundListeners) {
                listener.soundStarted(sound);
            }
        }
    }

    @Override
    public void pauseSound(final Sound sound) {
        checkArgNotNull(sound, "sound");
        soundPool.pause(((AndroidSound)sound).soundId);
        for (final A3SoundListener listener : soundListeners) {
            listener.soundPaused(sound);
        }
    }

    @Override
    public void resumeSound(final Sound sound) {
        checkArgNotNull(sound, "sound");
        soundPool.resume(((AndroidSound)sound).soundId);
        for (final A3SoundListener listener : soundListeners) {
            listener.soundResumed(sound);
        }
    }

    @Override
    public void stopSound(final Sound sound) {
        checkArgNotNull(sound, "sound");
        soundPool.stop(((AndroidSound)sound).soundId);
    }

    protected final List<A3MusicListener> musicListeners = A3Collections.checkNullList(new CopyOnWriteArrayList<>());

    @Override
    public void addMusicListener(final A3MusicListener listener) {
        musicListeners.add(listener);
    }

    @Override
    public List<A3MusicListener> getMusicListeners() {
        return musicListeners;
    }

    protected final List<A3SoundListener> soundListeners = A3Collections.checkNullList(new CopyOnWriteArrayList<>());

    @Override
    public void addSoundListener(final A3SoundListener listener) {
        soundListeners.add(listener);
    }

    @Override
    public List<A3SoundListener> getSoundListeners() {
        return soundListeners;
    }

    @Override
    public void onCompletion(final MediaPlayer mp) {
        for (final AndroidMusic music : musics) {
            if (music.mediaPlayer == mp) {
                final AtomicInteger loopsLeft = music.loopsLeft;
                if (loopsLeft.get() > 0) loopsLeft.set(loopsLeft.get() - 1);
                if (loopsLeft.get() == 0) mp.setLooping(false);
                for (final A3MusicListener listener : musicListeners) {
                    listener.musicStopped(music, loopsLeft.get());
                }
                break;
            }
        }
    }

    @Override
    public void unloadAll() {
        for (final AndroidMusic music : musics) {
            unloadMusic(music);
        }
        for (final AndroidSound sound : sounds) {
            unloadSound(sound);
        }
    }

    public static class AndroidMusic implements Music {

        protected volatile boolean prepared = false;

        protected volatile float volume;
        protected volatile int loops;
        protected AtomicInteger loopsLeft = new AtomicInteger();
        protected volatile int pos;

        protected final MediaPlayer mediaPlayer;

        public AndroidMusic(final MediaPlayer mediaPlayer) {
            checkArgNotNull(mediaPlayer, "mediaPlayer");
            this.mediaPlayer = mediaPlayer;
            reset();
        }

        @Override
        public Music reset() {
            Music.super.reset();
            loopsLeft.set(loops);
            return this;
        }

        protected void apply() {
            mediaPlayer.setVolume(volume, volume);
            if (loopsLeft.get() != 0) mediaPlayer.setLooping(true);
            mediaPlayer.seekTo(A3Math.clamp(pos, 0, getMillisecondLength()));
        }

        @Override
        public Music setVolume(final float volume) {
            this.volume = volume;
            return this;
        }

        @Override
        public Music setLooping(final int loops) {
            this.loops = Math.max(-1, loops);
            loopsLeft.set(this.loops);
            return this;
        }

        @Override
        public Music setMillisecondPos(final int pos) {
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

        public void setPrepared(final boolean prepared) {
            this.prepared = prepared;
        }

        public boolean isPrepared() {
            return prepared;
        }

        public AtomicInteger getLoopsLeft() {
            return loopsLeft;
        }

        @Override
        public int getMillisecondLength() {
            return mediaPlayer.getDuration();
        }

    }

    public static class AndroidSound implements Sound {

        protected volatile float volume;
        protected volatile float speed;
        protected volatile int loops;

        protected final int soundId;

        public AndroidSound(final int soundId) {
            this.soundId = soundId;
            reset();
        }

        public int getSoundId() {
            return soundId;
        }

        @Override
        public Sound setVolume(final float volume) {
            this.volume = volume;
            return this;
        }

        @Override
        public Sound setSpeed(final float speed) {
            this.speed = speed;
            return this;
        }

        @Override
        public Sound setLooping(final int loops) {
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

    }

}
