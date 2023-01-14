package a3wt.audio;

public interface A3SoundListener {

    void soundLoaded(final A3AudioPlayer.Sound sound);
    void soundStarted(final A3AudioPlayer.Sound sound);
    void soundPaused(final A3AudioPlayer.Sound sound);
    void soundResumed(final A3AudioPlayer.Sound sound);
    void soundUnloaded(final A3AudioPlayer.Sound sound);

}
