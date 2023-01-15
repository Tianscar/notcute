package a3wt.awt;

import a3wt.audio.A3AudioPlayer;
import a3wt.audio.A3MusicListener;
import a3wt.audio.A3SoundListener;
import a3wt.util.A3Collections;
import a3wt.util.A3Math;
import com.adonax.audiocue.AudioCue;
import com.adonax.audiocue.AudioCueInstanceEvent;
import com.adonax.audiocue.AudioCueListener;
import com.adonax.audiocue.AudioMixer;
import a3wt.app.A3Assets;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.spi.FormatConversionProvider;
import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import static a3wt.util.A3Preconditions.checkArgNotNull;

public class AWTA3AudioPlayer implements A3AudioPlayer, AudioCueListener {

    protected static AudioInputStream getSupportedAudioInputStream(AudioInputStream ais) {
        if (ais == null) throw new IllegalArgumentException("ais == NULL!");
        if (ais.getFormat().matches(AudioCue.audioFormat)) return ais;
        return new AudioInputStream(AudioSystem.getAudioInputStream(AudioCue.audioFormat, ais), AudioCue.audioFormat, ais.getFrameLength());
    }

    protected static float[] loadAudioInputStream(final AudioInputStream ais) throws IOException {
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

    protected final AudioMixer audioMixer = new AudioMixer();
    protected final List<AWTMusic> musics = new CopyOnWriteArrayList<>();
    protected final List<AWTSound> sounds = new CopyOnWriteArrayList<>();

    @Override
    public Sound loadSound(final File input) {
        checkArgNotNull(input, "input");
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(input);
            ais = getSupportedAudioInputStream(ais);
            final float[] cue = loadAudioInputStream(ais);
            final AWTSound sound = new AWTSound(AudioCue.makeStereoCue(cue, input.toURI().toString(), 1));
            sounds.add(sound);
            for (final A3SoundListener listener : soundListeners) {
                listener.soundLoaded(sound);
            }
            return sound;
        }
        catch (final UnsupportedAudioFileException | IOException e) {
            return null;
        }
    }

    @Override
    public Sound loadSound(final A3Assets assets, final String input) {
        checkArgNotNull(assets, "assets");
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(assets.readAsset(input));
            ais = getSupportedAudioInputStream(ais);
            float[] cue = loadAudioInputStream(ais);
            final AWTSound sound = new AWTSound(AudioCue.makeStereoCue(cue, input, 1));
            sounds.add(sound);
            for (final A3SoundListener listener : soundListeners) {
                listener.soundLoaded(sound);
            }
            return sound;
        }
        catch (final UnsupportedAudioFileException | IOException e) {
            return null;
        }
    }

    @Override
    public Music loadMusic(final File input) {
        checkArgNotNull(input, "input");
        final AWTMusic music = new AWTMusic(input);
        musics.add(music);
        for (final A3MusicListener listener : musicListeners) {
            listener.musicLoaded(music);
        }
        return music;
    }

    @Override
    public Music loadMusic(final A3Assets assets, final String input) {
        checkArgNotNull(assets, "assets");
        final AWTMusic music = new AWTMusic(input);
        musics.add(music);
        for (final A3MusicListener listener : musicListeners) {
            listener.musicLoaded(music);
        }
        return music;
    }

    // I have no better way :(
    @Override
    public String[] getAudioLoaderFormatNames() {
        final Set<String> formatNames = new HashSet<>();
        // JDK
        for (AudioFileFormat.Type type : AudioSystem.getAudioFileTypes()) {
            formatNames.add(type.getExtension().toLowerCase());
            if (type.toString().equalsIgnoreCase("aiff")) formatNames.add("aiff");
        }
        formatNames.add("mid");
        formatNames.add("midi");
        // SPI
        ServiceLoader<FormatConversionProvider> serviceLoader = ServiceLoader.load(FormatConversionProvider.class);
        String encodingString;
        for (FormatConversionProvider provider : serviceLoader) {
            for (AudioFormat.Encoding encoding : provider.getSourceEncodings()) {
                if (encoding.equals(AudioFormat.Encoding.PCM_SIGNED) || encoding.equals(AudioFormat.Encoding.PCM_UNSIGNED) ||
                        encoding.equals(AudioFormat.Encoding.PCM_FLOAT) || encoding.equals(AudioFormat.Encoding.ALAW) ||
                        encoding.equals(AudioFormat.Encoding.ULAW)) continue;
                encodingString = encoding.toString().toLowerCase();
                if (encodingString.contains("vorbis")) formatNames.add("ogg");
                else formatNames.add(encodingString);
            }
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public void prepareMusic(final Music music) throws IOException {
        AudioCue audioCue = ((AWTMusic) music).audioCue;
        if (audioCue != null && !((AWTMusic) music).isPrepared()) {
            ((AWTMusic) music).setInstanceID(audioCue.obtainInstance());
            return;
        }
        try {
            final String name;
            AudioInputStream ais;
            final File file = ((AWTMusic) music).file;
            if (file != null) {
                ais = AudioSystem.getAudioInputStream(file);
                name = file.getAbsolutePath();
            }
            else {
                final String assetName = ((AWTMusic) music).assetName;
                final InputStream input = AWTA3Assets.class.getClassLoader().getResourceAsStream(assetName);
                checkArgNotNull(input, "input");
                ais = AudioSystem.getAudioInputStream(input);
                name = assetName;
            }
            ais = AWTA3AudioPlayer.getSupportedAudioInputStream(ais);
            final float[] cue = AWTA3AudioPlayer.loadAudioInputStream(ais);
            ((AWTMusic) music).setAudioCue(AudioCue.makeStereoCue(cue, name, 1));
            audioCue = ((AWTMusic) music).audioCue;
            ((AWTMusic) music).setInstanceID(audioCue.obtainInstance());
            ((AWTMusic) music).setLength((int) (audioCue.getFrameLength() * 1000.0 / AudioCue.audioFormat.getFrameRate()));
            ((AWTMusic) music).setPrepared(true);
            for (final A3MusicListener listener : musicListeners) {
                listener.musicPrepared(music);
            }
        }
        catch (final UnsupportedAudioFileException e) {
            throw new IOException(e);
        }
    }

    @Override
    public boolean isMusicPrepared(final Music music) {
        checkArgNotNull(music, "music");
        return ((AWTMusic) music).prepared;
    }

    private void checkMusicPrepared(final Music music) {
        if (!((AWTMusic) music).prepared) throw new IllegalStateException("Please prepare the music first!");
    }

    @Override
    public void startMusic(final Music music) {
        checkArgNotNull(music, "music");
        checkMusicPrepared(music);
        if (!audioMixer.isMixerRunning()) {
            try {
                audioMixer.start();
            }
            catch (LineUnavailableException e) {
                return;
            }
        }
        final AudioCue audioCue = ((AWTMusic)music).audioCue;
        final int instanceID = ((AWTMusic)music).instanceID;
        if (!audioCue.isPlayerRunning()) audioCue.open(audioMixer);
        if (!audioCue.isPlaying(instanceID)) {
            ((AWTMusic)music).apply();
            audioCue.setFramePosition(instanceID, 0);
            audioCue.start(instanceID);
            for (final A3MusicListener listener : musicListeners) {
                listener.musicStarted(music);
            }
        }
    }

    @Override
    public void pauseMusic(final Music music) {
        checkArgNotNull(music, "music");
        checkMusicPrepared(music);
        final AudioCue audioCue = ((AWTMusic)music).audioCue;
        final int instanceID = ((AWTMusic)music).instanceID;
        audioCue.stop(instanceID);
        for (final A3MusicListener listener : musicListeners) {
            listener.musicPaused(music);
        }
    }

    @Override
    public void resumeMusic(final Music music) {
        checkArgNotNull(music, "music");
        checkMusicPrepared(music);
        final AudioCue audioCue = ((AWTMusic)music).audioCue;
        final int instanceID = ((AWTMusic)music).instanceID;
        audioCue.start(instanceID);
        for (final A3MusicListener listener : musicListeners) {
            listener.musicResumed(music);
        }
    }

    @Override
    public void stopMusic(final Music music) {
        checkArgNotNull(music, "music");
        checkMusicPrepared(music);
        final AudioCue audioCue = ((AWTMusic)music).audioCue;
        final int instanceID = ((AWTMusic)music).instanceID;
        if (audioCue.isPlaying(instanceID)) audioCue.stop(instanceID);
        if (audioCue.isPlayerRunning()) {
            audioCue.close();
            audioCue.releaseInstance(instanceID);
            ((AWTMusic) music).setInstanceID(-1);
            ((AWTMusic) music).setPrepared(false);
        }
        if (audioMixer.getTrackCacheCount() < 1) {
            if (audioMixer.isMixerRunning()) audioMixer.stop();
        }
    }

    @Override
    public void unloadMusic(final Music music) {
        checkArgNotNull(music, "music");
        musics.remove(((AWTMusic) music));
        final AudioCue audioCue = ((AWTMusic)music).audioCue;
        if (audioCue.isPlayerRunning()) {
            audioCue.close();
        }
        if (audioMixer.getTrackCacheCount() < 1) {
            if (audioMixer.isMixerRunning()) audioMixer.stop();
        }
        for (final A3MusicListener listener : musicListeners) {
            listener.musicUnloaded(music);
        }
    }

    @Override
    public void startSound(final Sound sound) {
        checkArgNotNull(sound, "sound");
        if (!audioMixer.isMixerRunning()) {
            try {
                audioMixer.start();
            }
            catch (LineUnavailableException e) {
                return;
            }
        }
        final AudioCue audioCue = ((AWTSound)sound).audioCue;
        final int instanceID = ((AWTSound)sound).instanceID;
        if (!audioCue.isPlayerRunning()) audioCue.open(audioMixer);
        if (!audioCue.isPlaying(instanceID)) {
            ((AWTSound)sound).apply();
            audioCue.setFramePosition(instanceID, 0);
            audioCue.start(instanceID);
            for (final A3SoundListener listener : soundListeners) {
                listener.soundStarted(sound);
            }
        }
    }

    @Override
    public void pauseSound(final Sound sound) {
        checkArgNotNull(sound, "sound");
        final AudioCue audioCue = ((AWTSound)sound).audioCue;
        final int instanceID = ((AWTSound)sound).instanceID;
        audioCue.stop(instanceID);
        for (final A3SoundListener listener : soundListeners) {
            listener.soundPaused(sound);
        }
    }

    @Override
    public void resumeSound(final Sound sound) {
        checkArgNotNull(sound, "sound");
        final AudioCue audioCue = ((AWTSound)sound).audioCue;
        final int instanceID = ((AWTSound)sound).instanceID;
        audioCue.start(instanceID);
        for (final A3SoundListener listener : soundListeners) {
            listener.soundResumed(sound);
        }
    }

    @Override
    public void stopSound(final Sound sound) {
        checkArgNotNull(sound, "sound");
        final AudioCue audioCue = ((AWTSound)sound).audioCue;
        final int instanceID = ((AWTSound)sound).instanceID;
        if (audioCue.isPlaying(instanceID)) audioCue.stop(instanceID);
        if (audioCue.isPlayerRunning()) {
            audioCue.close();
            audioCue.releaseInstance(instanceID);
            ((AWTSound)sound).setInstanceID(audioCue.obtainInstance());
        }
        if (audioMixer.getTrackCacheCount() < 1) {
            if (audioMixer.isMixerRunning()) audioMixer.stop();
        }
    }

    @Override
    public void unloadSound(final Sound sound) {
        checkArgNotNull(sound, "sound");
        sounds.remove(((AWTSound) sound));
        final AudioCue audioCue = ((AWTSound)sound).audioCue;
        if (audioCue.isPlayerRunning()) {
            audioCue.close();
        }
        if (audioMixer.getTrackCacheCount() < 1) {
            if (audioMixer.isMixerRunning()) audioMixer.stop();
        }
        for (final A3SoundListener listener : soundListeners) {
            listener.soundUnloaded(sound);
        }
    }

    protected final List<A3MusicListener> musicListeners = A3Collections.checkNullList(new CopyOnWriteArrayList<>());
    protected final List<A3SoundListener> soundListeners = A3Collections.checkNullList(new CopyOnWriteArrayList<>());

    @Override
    public void addMusicListener(final A3MusicListener listener) {
        musicListeners.add(listener);
    }

    @Override
    public List<A3MusicListener> getMusicListeners() {
        return musicListeners;
    }

    @Override
    public void addSoundListener(final A3SoundListener listener) {
        soundListeners.add(listener);
    }

    @Override
    public List<A3SoundListener> getSoundListeners() {
        return soundListeners;
    }

    @Override
    public void audioCueOpened(final long now, final int threadPriority, final int bufferSize, final AudioCue source) {}

    @Override
    public void audioCueClosed(final long now, final AudioCue source) {}

    @Override
    public void instanceEventOccurred(final AudioCueInstanceEvent event) {
        for (final AWTMusic music : musics) {
            if (event.source == music.audioCue && event.instanceID == music.instanceID) {
                switch (event.type) {
                    case LOOP:
                        for (final A3MusicListener listener : musicListeners) {
                            listener.musicStopped(music, music.audioCue.getLooping(music.instanceID));
                        }
                        break;
                    case STOP_INSTANCE:
                        for (final A3MusicListener listener : musicListeners) {
                            listener.musicStopped(music, 0);
                        }
                        break;
                }
                break;
            }
        }
    }

    @Override
    public void unloadAll() {
        for (final AWTMusic music : musics) {
            unloadMusic(music);
        }
        for (final AWTSound sound : sounds) {
            unloadSound(sound);
        }
    }

    public static class AWTMusic implements Music {

        protected volatile float volume;
        protected volatile int loops;
        protected volatile int pos;

        protected final File file;
        protected final String assetName;

        protected volatile AudioCue audioCue = null;
        protected volatile boolean prepared = false;
        protected volatile int instanceID = -1;
        protected volatile int length = -1;

        public AWTMusic(final File file) {
            checkArgNotNull(file, "file");
            this.file = file;
            this.assetName = null;
            reset();
        }

        public AWTMusic(final String assetName) {
            checkArgNotNull(assetName, "assetName");
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

        public void setAudioCue(final AudioCue audioCue) {
            this.audioCue = audioCue;
        }

        public AudioCue getAudioCue() {
            return audioCue;
        }

        public int getInstanceID() {
            return instanceID;
        }

        public void setInstanceID(final int instanceID) {
            this.instanceID = instanceID;
        }

        public void setLength(final int length) {
            this.length = length;
        }

        public boolean isPrepared() {
            return prepared;
        }

        public void setPrepared(final boolean prepared) {
            this.prepared = prepared;
        }

        @Override
        public Music setVolume(final float volume) {
            this.volume = volume;
            return this;
        }

        @Override
        public Music setLooping(final int loops) {
            this.loops = Math.max(-1, loops);
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

        public void apply() {
            audioCue.setVolume(instanceID, volume);
            audioCue.setLooping(instanceID, loops);
            final double frames = (AudioCue.audioFormat.getFrameRate() * A3Math.clamp(pos, 0, getMillisecondLength())) / 1000.0;
            audioCue.setFramePosition(instanceID, frames);
        }

        @Override
        public int getMillisecondLength() {
            return length;
        }

    }

    public static class AWTSound implements Sound {

        protected volatile float volume;
        protected volatile float speed;
        protected volatile int loops;

        protected final AudioCue audioCue;
        protected volatile int instanceID;

        public AWTSound(final AudioCue audioCue) {
            checkArgNotNull(audioCue, "audioCue");
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

        public void apply() {
            audioCue.setVolume(instanceID, volume);
            audioCue.setLooping(instanceID, loops);
            audioCue.setSpeed(instanceID, speed);
        }

    }

}
