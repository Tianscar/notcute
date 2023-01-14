package a3wt.audio;

import a3wt.app.A3Assets;
import a3wt.util.A3Resetable;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface A3AudioPlayer {

    Sound loadSound(final File input);
    Sound loadSound(final A3Assets assets, final String input);

    Music loadMusic(final File input);
    Music loadMusic(final A3Assets assets, final String input);

    void unloadSound(final Sound sound);
    void unloadMusic(final Music music);

    String[] getAudioLoaderFormatNames();

    void prepareMusic(final Music music) throws IOException;
    boolean isMusicPrepared(final Music music);
    void startMusic(final Music music);
    void pauseMusic(final Music music);
    void resumeMusic(final Music music);
    void stopMusic(final Music music);

    void startSound(final Sound sound);
    void pauseSound(final Sound sound);
    void resumeSound(final Sound sound);
    void stopSound(final Sound sound);

    void addMusicListener(final A3MusicListener listener);
    List<A3MusicListener> getMusicListeners();

    void addSoundListener(final A3SoundListener listener);
    List<A3SoundListener> getSoundListeners();

    void unloadAll();

    interface Music extends A3Resetable<Music> {

        Music setVolume(final float volume);
        Music setLooping(final int loops);
        Music setMillisecondPos(final int pos);

        float getVolume();
        int getLooping();
        int getMillisecondPos();

        int getMillisecondLength();

        @Override
        default Music reset() {
            setVolume(1.0f);
            setLooping(0);
            setMillisecondPos(0);
            return this;
        }

    }

    interface Sound extends A3Resetable<Sound> {

        Sound setVolume(final float volume);
        Sound setSpeed(final float speed);
        Sound setLooping(final int loops);

        float getVolume();
        float getSpeed();
        int getLooping();

        @Override
        default Sound reset() {
            setVolume(1.0f);
            setSpeed(1.0f);
            setLooping(0);
            return this;
        }

    }

}
