package io.notcute.audio.android;

import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import io.notcute.app.Assets;
import io.notcute.app.android.AndroidAssets;
import io.notcute.audio.AudioPlayer;
import io.notcute.util.MathUtils;
import io.notcute.util.signalslot.VoidSignal1;
import io.notcute.util.signalslot.VoidSignal2;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class AndroidAudioPlayer implements AudioPlayer, MediaPlayer.OnCompletionListener {

    protected static final int SOUNDPOOL_MAX_STREAMS = 50;

    private final SoundPool soundPool;
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
    public SoundPool getSoundPool() {
        return soundPool;
    }

    protected final List<AndroidMusic> musics = new CopyOnWriteArrayList<>();
    protected final List<AndroidSound> sounds = new CopyOnWriteArrayList<>();

    @Override
    public Sound loadSound(File input) {
        Objects.requireNonNull(input);
        AndroidSound sound = new AndroidSound(soundPool.load(input.getAbsolutePath(), 1));
        sounds.add(sound);
        onSoundLoad.emit(sound);
        return sound;
    }

    @Override
    public Sound loadSound(Assets assets, String input) {
        Objects.requireNonNull(assets);
        Objects.requireNonNull(input);
        try {
            AndroidSound sound = new AndroidSound(soundPool.load(((AndroidAssets) assets).getAssets().openFd(input), 1));
            sounds.add(sound);
            onSoundLoad.emit(sound);
            return sound;
        }
        catch (IOException e) {
            return null;
        }
    }

    @Override
    public Music loadMusic(File input) {
        Objects.requireNonNull(input);
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(input.getAbsolutePath());
            mediaPlayer.setOnCompletionListener(this);
            AndroidMusic music = new AndroidMusic(mediaPlayer);
            musics.add(music);
            onMusicLoad.emit(music);
            return music;
        }
        catch (IOException e) {
            return null;
        }
    }

    @Override
    public Music loadMusic(Assets assets, String input) {
        Objects.requireNonNull(assets);
        Objects.requireNonNull(input);
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mediaPlayer.setDataSource(((AndroidAssets) assets).getAssets().openFd(input));
            }
            else {
                AssetFileDescriptor afd = ((AndroidAssets) assets).getAssets().openFd(input);
                mediaPlayer.setDataSource(afd.getFileDescriptor());
            }
            mediaPlayer.setOnCompletionListener(this);
            AndroidMusic music = new AndroidMusic(mediaPlayer);
            musics.add(music);
            onMusicLoad.emit(music);
            return music;
        }
        catch (IOException e) {
            return null;
        }
    }

    @Override
    public void unloadSound(Sound sound) {
        Objects.requireNonNull(sound);
        sounds.add((AndroidSound) sound);
        soundPool.unload(((AndroidSound)sound).soundId);
        onSoundUnload.emit(sound);
    }

    @Override
    public void unloadMusic(Music music) {
        Objects.requireNonNull(music);
        musics.remove((AndroidMusic) music);
        ((AndroidMusic)music).mediaPlayer.release();
        onMusicUnload.emit(music);
    }

    private static final String[] LOADER_MIME_TYPES = new String[] {
            "audio/wav", "audio/mpeg", "audio/ogg",
            "audio/flac", "audio/3gpp", "audio/aac", "audio/midi", "audio/x-midi" };

    @Override
    public String[] getAudioLoaderMIMETypes() {
        return LOADER_MIME_TYPES.clone();
    }

    @Override
    public void prepareMusic(Music music) throws IOException {
        Objects.requireNonNull(music);
        MediaPlayer mediaPlayer = ((AndroidMusic) music).mediaPlayer;
        mediaPlayer.prepare();
        ((AndroidMusic) music).setPrepared(true);
        onMusicPrepare.emit(music);
    }

    @Override
    public boolean isMusicPrepared(Music music) {
        Objects.requireNonNull(music);
        return ((AndroidMusic) music).prepared;
    }

    private void checkMusicPrepared(Music music) {
        if (!((AndroidMusic) music).prepared) throw new IllegalStateException("Please prepare the music first!");
    }

    @Override
    public void startMusic(Music music) {
        Objects.requireNonNull(music);
        checkMusicPrepared(music);
        ((AndroidMusic) music).apply();
        ((AndroidMusic) music).mediaPlayer.start();
        onMusicStart.emit(music);
    }

    @Override
    public void pauseMusic(Music music) {
        Objects.requireNonNull(music);
        checkMusicPrepared(music);
        ((AndroidMusic) music).mediaPlayer.pause();
        onMusicPause.emit(music);
    }

    @Override
    public void resumeMusic(Music music) {
        Objects.requireNonNull(music);
        checkMusicPrepared(music);
        ((AndroidMusic) music).mediaPlayer.start();
        onMusicResume.emit(music);
    }

    @Override
    public void stopMusic(Music music) {
        Objects.requireNonNull(music);
        checkMusicPrepared(music);
        ((AndroidMusic) music).mediaPlayer.stop();
        ((AndroidMusic) music).setPrepared(false);
    }

    @Override
    public void startSound(Sound sound) {
        Objects.requireNonNull(sound);
        AndroidSound androidSound = (AndroidSound) sound;
        if (soundPool.play(androidSound.soundId, androidSound.volume, androidSound.volume, 1, androidSound.loops, androidSound.speed) != 0) {
            onSoundStart.emit(sound);
        }
    }

    @Override
    public void pauseSound(Sound sound) {
        Objects.requireNonNull(sound);
        soundPool.pause(((AndroidSound)sound).soundId);
        onSoundPause.emit(sound);
    }

    @Override
    public void resumeSound(Sound sound) {
        Objects.requireNonNull(sound);
        soundPool.resume(((AndroidSound)sound).soundId);
        onSoundResume.emit(sound);
    }

    @Override
    public void stopSound(Sound sound) {
        Objects.requireNonNull(sound);
        soundPool.stop(((AndroidSound)sound).soundId);
    }

    private final VoidSignal1<Music> onMusicLoad = new VoidSignal1<>();
    @Override
    public VoidSignal1<Music> onMusicLoad() {
        return onMusicLoad;
    }

    private final VoidSignal1<Music> onMusicPrepare = new VoidSignal1<>();
    @Override
    public VoidSignal1<Music> onMusicPrepare() {
        return onMusicPrepare;
    }

    private final VoidSignal1<Music> onMusicStart = new VoidSignal1<>();
    @Override
    public VoidSignal1<Music> onMusicStart() {
        return onMusicStart;
    }

    private final VoidSignal1<Music> onMusicPause = new VoidSignal1<>();
    @Override
    public VoidSignal1<Music> onMusicPause() {
        return onMusicPause;
    }

    private final VoidSignal1<Music> onMusicResume = new VoidSignal1<>();
    @Override
    public VoidSignal1<Music> onMusicResume() {
        return onMusicResume;
    }

    private final VoidSignal2<Music, Integer> onMusicStop = new VoidSignal2<>();
    @Override
    public VoidSignal2<Music, Integer> onMusicStop() {
        return onMusicStop;
    }

    private final VoidSignal1<Music> onMusicUnload = new VoidSignal1<>();
    @Override
    public VoidSignal1<Music> onMusicUnload() {
        return onMusicUnload;
    }

    private final VoidSignal1<Sound> onSoundLoad = new VoidSignal1<>();
    @Override
    public VoidSignal1<Sound> onSoundLoad() {
        return onSoundLoad;
    }

    private final VoidSignal1<Sound> onSoundStart = new VoidSignal1<>();
    @Override
    public VoidSignal1<Sound> onSoundStart() {
        return onSoundStart;
    }

    private final VoidSignal1<Sound> onSoundPause = new VoidSignal1<>();
    @Override
    public VoidSignal1<Sound> onSoundPause() {
        return onSoundPause;
    }

    private final VoidSignal1<Sound> onSoundResume = new VoidSignal1<>();
    @Override
    public VoidSignal1<Sound> onSoundResume() {
        return onSoundResume;
    }

    private final VoidSignal1<Sound> onSoundUnload = new VoidSignal1<>();
    @Override
    public VoidSignal1<Sound> onSoundUnload() {
        return onSoundUnload;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        for (AndroidMusic music : musics) {
            if (music.mediaPlayer == mp) {
                AtomicInteger loopsLeft = music.loopsLeft;
                if (loopsLeft.get() > 0) loopsLeft.set(loopsLeft.get() - 1);
                if (loopsLeft.get() == 0) mp.setLooping(false);
                onMusicStop.emit(music, loopsLeft.get());
                break;
            }
        }
    }

    @Override
    public void unloadAll() {
        for (AndroidMusic music : musics) {
            unloadMusic(music);
        }
        for (AndroidSound sound : sounds) {
            unloadSound(sound);
        }
    }

    public static class AndroidMusic implements Music {

        private volatile boolean prepared = false;

        private volatile float volume;
        private volatile int loops;
        protected final AtomicInteger loopsLeft = new AtomicInteger();
        private volatile int pos;

        private final MediaPlayer mediaPlayer;

        public MediaPlayer getMediaPlayer() {
            return mediaPlayer;
        }

        public AndroidMusic(MediaPlayer mediaPlayer) {
            this.mediaPlayer = Objects.requireNonNull(mediaPlayer);
            reset();
        }

        @Override
        public void reset() {
            Music.super.reset();
            loopsLeft.set(loops);
        }

        protected void apply() {
            mediaPlayer.setVolume(volume, volume);
            if (loopsLeft.get() != 0) mediaPlayer.setLooping(true);
            mediaPlayer.seekTo(MathUtils.clamp(pos, 0, getMillisecondLength()));
        }

        @Override
        public void setVolume(float volume) {
            this.volume = volume;
        }

        @Override
        public void setLooping(int loops) {
            this.loops = Math.max(-1, loops);
            loopsLeft.set(this.loops);
        }

        @Override
        public void setMillisecondPos(int pos) {
            this.pos = pos;
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

        public void setPrepared(boolean prepared) {
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

        private volatile float volume;
        private volatile float speed;
        private volatile int loops;

        private final int soundId;

        public AndroidSound(int soundId) {
            this.soundId = soundId;
            reset();
        }

        public int getSoundId() {
            return soundId;
        }

        @Override
        public void setVolume(float volume) {
            this.volume = volume;
        }

        @Override
        public void setSpeed(float speed) {
            this.speed = speed;
        }

        @Override
        public void setLooping(int loops) {
            this.loops = Math.max(-1, loops);
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
