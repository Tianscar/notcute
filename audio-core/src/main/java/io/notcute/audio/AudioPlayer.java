package io.notcute.audio;

import io.notcute.app.Assets;
import io.notcute.util.Resetable;
import io.notcute.util.signalslot.VoidSignal1;
import io.notcute.util.signalslot.VoidSignal2;

import java.io.File;
import java.io.IOException;

public interface AudioPlayer {

    Sound loadSound(File input);
    Sound loadSound(Assets assets, String input);

    Music loadMusic(File input);
    Music loadMusic(Assets assets, String input);

    void unloadSound(Sound sound);
    void unloadMusic(Music music);

    String[] getAudioLoaderMIMETypes();

    void prepareMusic(Music music) throws IOException;
    boolean isMusicPrepared(Music music);
    void startMusic(Music music);
    void pauseMusic(Music music);
    void resumeMusic(Music music);
    void stopMusic(Music music);

    void startSound(Sound sound);
    void pauseSound(Sound sound);
    void resumeSound(Sound sound);
    void stopSound(Sound sound);
    
    VoidSignal1<Music> onMusicLoad();
    VoidSignal1<Music> onMusicPrepare();
    VoidSignal1<Music> onMusicStart();
    VoidSignal1<Music> onMusicPause();
    VoidSignal1<Music> onMusicResume();
    VoidSignal2<Music, Integer> onMusicStop();
    VoidSignal1<Music> onMusicUnload();

    VoidSignal1<Sound> onSoundLoad();
    VoidSignal1<Sound> onSoundStart();
    VoidSignal1<Sound> onSoundPause();
    VoidSignal1<Sound> onSoundResume();
    VoidSignal1<Sound> onSoundUnload();

    void unloadAll();

    interface Music extends Resetable {

        void setLeftVolume(float leftVol);
        void setRightVolume(float rightVol);
        default void setVolume(float leftVol, float rightVol) {
            setLeftVolume(leftVol);
            setRightVolume(rightVol);
        }
        void setLooping(int loops);
        void setMillisecondPos(int pos);

        float getLeftVolume();
        float getRightVolume();
        int getLooping();
        int getMillisecondPos();

        int getMillisecondLength();

        @Override
        default void reset() {
            setVolume(1.0f, 1.0f);
            setLooping(0);
            setMillisecondPos(0);
        }

    }

    interface Sound extends Resetable {

        void setLeftVolume(float leftVol);
        void setRightVolume(float rightVol);
        default void setVolume(float leftVol, float rightVol) {
            setLeftVolume(leftVol);
            setRightVolume(rightVol);
        }
        void setSpeed(float speed);
        void setLooping(int loops);

        float getLeftVolume();
        float getRightVolume();
        float getSpeed();
        int getLooping();

        @Override
        default void reset() {
            setVolume(1.0f, 1.0f);
            setSpeed(1.0f);
            setLooping(0);
        }

    }

}
