package io.notcute.audio.javase;

import com.adonax.audiocue.AudioCue;
import com.adonax.audiocue.AudioCueInstanceEvent;
import com.adonax.audiocue.AudioCueListener;
import com.adonax.audiocue.AudioMixer;
import io.notcute.app.Assets;
import io.notcute.app.javase.JavaSEAssets;
import io.notcute.audio.AudioPlayer;
import io.notcute.util.MathUtils;
import io.notcute.util.signalslot.VoidSignal1;
import io.notcute.util.signalslot.VoidSignal2;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class JavaSEAudioPlayer implements AudioPlayer, AudioCueListener {

    protected static AudioInputStream getSupportedAudioInputStream(AudioInputStream ais) {
        if (ais == null) throw new IllegalArgumentException("ais == NULL!");
        if (ais.getFormat().matches(AudioCue.audioFormat)) return ais;
        return new AudioInputStream(AudioSystem.getAudioInputStream(AudioCue.audioFormat, ais), AudioCue.audioFormat, ais.getFrameLength());
    }

    protected static float[] loadAudioInputStream(AudioInputStream ais) throws IOException {
        int framesCount;
        if (ais.getFrameLength() > Integer.MAX_VALUE >> 1) {
            System.out.println(
                    "WARNING: Clip is too large to entirely fit!");
            framesCount = Integer.MAX_VALUE >> 1;
        }
        else {
            framesCount = (int)ais.getFrameLength();
        }
        // stereo output, so two entries per frame
        float[] temp = new float[framesCount * 2];
        long tempCountdown = temp.length;
        int bytesRead = 0;
        int bufferIdx;
        int clipIdx = 0;
        byte[] buffer = new byte[1024];
        while((bytesRead = ais.read(buffer, 0, 1024)) != -1) {
            bufferIdx = 0;
            for (int i = 0, n = (bytesRead >> 1); i < n; i ++) {
                if ( tempCountdown-- >= 0 ) {
                    temp[clipIdx++] =
                            ( buffer[bufferIdx++] & 0xff )
                                    | ( buffer[bufferIdx++] << 8 ) ;
                }
            }
        }
        for (int i = 0; i < temp.length; i++) {
            temp[i] = temp[i] / 32767f;
        }
        return temp;
    }

    private final AudioMixer audioMixer = new AudioMixer();

    public AudioMixer getAudioMixer() {
        return audioMixer;
    }

    protected final List<JavaSEMusic> musics = new CopyOnWriteArrayList<>();
    protected final List<JavaSESound> sounds = new CopyOnWriteArrayList<>();

    @Override
    public Sound loadSound(File input) {
        Objects.requireNonNull(input);
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(input);
            ais = getSupportedAudioInputStream(ais);
            float[] cue = loadAudioInputStream(ais);
            JavaSESound sound = new JavaSESound(AudioCue.makeStereoCue(cue, input.toURI().toString(), 1));
            sounds.add(sound);
            onSoundLoad.emit(sound);
            return sound;
        }
        catch (UnsupportedAudioFileException | IOException e) {
            return null;
        }
    }

    @Override
    public Sound loadSound(Assets assets, String input) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(assets.readAsset(input));
            ais = getSupportedAudioInputStream(ais);
            float[] cue = loadAudioInputStream(ais);
            JavaSESound sound = new JavaSESound(AudioCue.makeStereoCue(cue, input, 1));
            sounds.add(sound);
            onSoundLoad.emit(sound);
            return sound;
        }
        catch (UnsupportedAudioFileException | IOException e) {
            return null;
        }
    }

    @Override
    public Music loadMusic(File input) {
        Objects.requireNonNull(input);
        JavaSEMusic music = new JavaSEMusic(input);
        musics.add(music);
        onMusicLoad.emit(music);
        return music;
    }

    @Override
    public Music loadMusic(Assets assets, String input) {
        JavaSEMusic music = new JavaSEMusic(input);
        musics.add(music);
        onMusicLoad.emit(music);
        return music;
    }

    private static final String[] LOADER_MIME_TYPES = new String[] {
            "audio/wav", "audio/mpeg", "audio/ogg", "audio/basic",
            "audio/flac", "audio/aiff", "audio/x-aiff", "audio/midi", "audio/x-midi" };

    @Override
    public String[] getAudioLoaderMIMETypes() {
        return LOADER_MIME_TYPES.clone();
    }

    @Override
    public void prepareMusic(Music music) throws IOException {
        AudioCue audioCue = ((JavaSEMusic) music).audioCue;
        if (audioCue != null && !((JavaSEMusic) music).isPrepared()) {
            ((JavaSEMusic) music).setInstanceID(audioCue.obtainInstance());
            return;
        }
        try {
            String name;
            AudioInputStream ais;
            File file = ((JavaSEMusic) music).file;
            if (file != null) {
                ais = AudioSystem.getAudioInputStream(file);
                name = file.getAbsolutePath();
            }
            else {
                String assetName = ((JavaSEMusic) music).assetName;
                InputStream input = JavaSEAssets.class.getClassLoader().getResourceAsStream(assetName);
                Objects.requireNonNull(input);
                ais = AudioSystem.getAudioInputStream(input);
                name = assetName;
            }
            ais = getSupportedAudioInputStream(ais);
            float[] cue = loadAudioInputStream(ais);
            ((JavaSEMusic) music).setAudioCue(AudioCue.makeStereoCue(cue, name, 1));
            audioCue = ((JavaSEMusic) music).audioCue;
            ((JavaSEMusic) music).setInstanceID(audioCue.obtainInstance());
            ((JavaSEMusic) music).setLength((int) (audioCue.getFrameLength() * 1000.0 / AudioCue.audioFormat.getFrameRate()));
            ((JavaSEMusic) music).setPrepared(true);
            onMusicPrepare.emit(music);
        }
        catch (UnsupportedAudioFileException e) {
            throw new IOException(e);
        }
    }

    @Override
    public boolean isMusicPrepared(Music music) {
        Objects.requireNonNull(music);
        return ((JavaSEMusic) music).prepared;
    }

    private void checkMusicPrepared(Music music) {
        if (!((JavaSEMusic) music).prepared) throw new IllegalStateException("Please prepare the music first!");
    }

    @Override
    public void startMusic(Music music) {
        Objects.requireNonNull(music);
        checkMusicPrepared(music);
        if (!audioMixer.isMixerRunning()) {
            try {
                audioMixer.start();
            }
            catch (LineUnavailableException e) {
                return;
            }
        }
        AudioCue audioCue = ((JavaSEMusic)music).audioCue;
        int instanceID = ((JavaSEMusic)music).instanceID;
        if (!audioCue.isPlayerRunning()) audioCue.open(audioMixer);
        if (!audioCue.isPlaying(instanceID)) {
            ((JavaSEMusic)music).apply();
            audioCue.setFramePosition(instanceID, 0);
            audioCue.start(instanceID);
            onMusicStart.emit(music);
        }
    }

    @Override
    public void pauseMusic(Music music) {
        Objects.requireNonNull(music);
        checkMusicPrepared(music);
        AudioCue audioCue = ((JavaSEMusic)music).audioCue;
        int instanceID = ((JavaSEMusic)music).instanceID;
        audioCue.stop(instanceID);
        onMusicPause.emit(music);
    }

    @Override
    public void resumeMusic(Music music) {
        Objects.requireNonNull(music);
        checkMusicPrepared(music);
        AudioCue audioCue = ((JavaSEMusic)music).audioCue;
        int instanceID = ((JavaSEMusic)music).instanceID;
        audioCue.start(instanceID);
        onMusicResume.emit(music);
    }

    @Override
    public void stopMusic(Music music) {
        Objects.requireNonNull(music);
        checkMusicPrepared(music);
        AudioCue audioCue = ((JavaSEMusic)music).audioCue;
        int instanceID = ((JavaSEMusic)music).instanceID;
        if (audioCue.isPlaying(instanceID)) audioCue.stop(instanceID);
        if (audioCue.isPlayerRunning()) {
            audioCue.close();
            audioCue.releaseInstance(instanceID);
            ((JavaSEMusic) music).setInstanceID(-1);
            ((JavaSEMusic) music).setPrepared(false);
        }
        if (audioMixer.getTrackCacheCount() < 1) {
            if (audioMixer.isMixerRunning()) audioMixer.stop();
        }
    }

    @Override
    public void unloadMusic(Music music) {
        Objects.requireNonNull(music);
        musics.remove(((JavaSEMusic) music));
        AudioCue audioCue = ((JavaSEMusic)music).audioCue;
        if (audioCue.isPlayerRunning()) {
            audioCue.close();
        }
        if (audioMixer.getTrackCacheCount() < 1) {
            if (audioMixer.isMixerRunning()) audioMixer.stop();
        }
        onMusicUnload.emit(music);
    }

    @Override
    public void startSound(Sound sound) {
        Objects.requireNonNull(sound);
        if (!audioMixer.isMixerRunning()) {
            try {
                audioMixer.start();
            }
            catch (LineUnavailableException e) {
                return;
            }
        }
        AudioCue audioCue = ((JavaSESound)sound).audioCue;
        int instanceID = ((JavaSESound)sound).instanceID;
        if (!audioCue.isPlayerRunning()) audioCue.open(audioMixer);
        if (!audioCue.isPlaying(instanceID)) {
            ((JavaSESound)sound).apply();
            audioCue.setFramePosition(instanceID, 0);
            audioCue.start(instanceID);
            onSoundStart.emit(sound);
        }
    }

    @Override
    public void pauseSound(Sound sound) {
        Objects.requireNonNull(sound);
        AudioCue audioCue = ((JavaSESound)sound).audioCue;
        int instanceID = ((JavaSESound)sound).instanceID;
        audioCue.stop(instanceID);
        onSoundPause.emit(sound);
    }

    @Override
    public void resumeSound(Sound sound) {
        Objects.requireNonNull(sound);
        AudioCue audioCue = ((JavaSESound)sound).audioCue;
        int instanceID = ((JavaSESound)sound).instanceID;
        audioCue.start(instanceID);
        onSoundResume.emit(sound);
    }

    @Override
    public void stopSound(Sound sound) {
        Objects.requireNonNull(sound);
        AudioCue audioCue = ((JavaSESound)sound).audioCue;
        int instanceID = ((JavaSESound)sound).instanceID;
        if (audioCue.isPlaying(instanceID)) audioCue.stop(instanceID);
        if (audioCue.isPlayerRunning()) {
            audioCue.close();
            audioCue.releaseInstance(instanceID);
            ((JavaSESound)sound).setInstanceID(audioCue.obtainInstance());
        }
        if (audioMixer.getTrackCacheCount() < 1) {
            if (audioMixer.isMixerRunning()) audioMixer.stop();
        }
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
    public void unloadSound(Sound sound) {
        Objects.requireNonNull(sound);
        sounds.remove(((JavaSESound) sound));
        AudioCue audioCue = ((JavaSESound)sound).audioCue;
        if (audioCue.isPlayerRunning()) {
            audioCue.close();
        }
        if (audioMixer.getTrackCacheCount() < 1) {
            if (audioMixer.isMixerRunning()) audioMixer.stop();
        }
        onSoundUnload.emit(sound);
    }

    @Override
    public void audioCueOpened(long now, int threadPriority, int bufferSize, AudioCue source) {
    }

    @Override
    public void audioCueClosed(long now, AudioCue source) {
    }

    @Override
    public void instanceEventOccurred(AudioCueInstanceEvent event) {
        for (JavaSEMusic music : musics) {
            if (event.source == music.audioCue && event.instanceID == music.instanceID) {
                switch (event.type) {
                    case LOOP:
                        onMusicStop.emit(music, music.audioCue.getLooping(music.instanceID));
                        break;
                    case STOP_INSTANCE:
                        onMusicStop.emit(music, 0);
                        break;
                }
                break;
            }
        }
    }

    @Override
    public void unloadAll() {
        for (JavaSEMusic music : musics) {
            unloadMusic(music);
        }
        for (JavaSESound sound : sounds) {
            unloadSound(sound);
        }
    }

    public static class JavaSEMusic implements Music {

        private volatile float volume;
        private volatile int loops;
        private volatile int pos;

        protected final File file;
        protected final String assetName;

        protected volatile AudioCue audioCue = null;
        protected volatile boolean prepared = false;
        protected volatile int instanceID = -1;
        private volatile int length = -1;

        public JavaSEMusic(File file) {
            Objects.requireNonNull(file);
            this.file = file;
            this.assetName = null;
            reset();
        }

        public JavaSEMusic(String assetName) {
            Objects.requireNonNull(assetName);
            this.file = null;
            this.assetName = assetName;
            reset();
        }

        public String getAssetName() {
            return assetName;
        }

        public File getFile() {
            return file;
        }

        public void setAudioCue(AudioCue audioCue) {
            this.audioCue = audioCue;
        }

        public AudioCue getAudioCue() {
            return audioCue;
        }

        public int getInstanceID() {
            return instanceID;
        }

        public void setInstanceID(int instanceID) {
            this.instanceID = instanceID;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public boolean isPrepared() {
            return prepared;
        }

        public void setPrepared(boolean prepared) {
            this.prepared = prepared;
        }

        @Override
        public void setVolume(float volume) {
            this.volume = volume;
        }

        @Override
        public void setLooping(int loops) {
            this.loops = Math.max(-1, loops);
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

        public void apply() {
            audioCue.setVolume(instanceID, volume);
            audioCue.setLooping(instanceID, loops);
            double frames = (AudioCue.audioFormat.getFrameRate() * MathUtils.clamp(pos, 0, getMillisecondLength())) / 1000.0;
            audioCue.setFramePosition(instanceID, frames);
        }

        @Override
        public int getMillisecondLength() {
            return length;
        }

    }

    public static class JavaSESound implements Sound {

        private volatile float volume;
        private volatile float speed;
        private volatile int loops;

        protected final AudioCue audioCue;
        protected volatile int instanceID;

        public JavaSESound(AudioCue audioCue) {
            this.audioCue = audioCue;
            this.instanceID = audioCue.obtainInstance();
            reset();
        }

        public int getInstanceID() {
            return instanceID;
        }

        public void setInstanceID(int instanceID) {
            this.instanceID = instanceID;
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

        public void apply() {
            audioCue.setVolume(instanceID, volume);
            audioCue.setLooping(instanceID, loops);
            audioCue.setSpeed(instanceID, speed);
        }

    }

}
