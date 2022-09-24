package com.adonax.audiocue;

public abstract class AudioCueAdapter implements AudioCueListener {

    @Override
    public void audioCueOpened(long now, int threadPriority, int bufferSize, AudioCue source) {

    }

    @Override
    public void audioCueClosed(long now, AudioCue source) {

    }

    @Override
    public void instanceEventOccurred(AudioCueInstanceEvent event) {

    }

}
