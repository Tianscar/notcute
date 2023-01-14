package a3wt.audio;

public interface A3MusicListener {

    void musicLoaded(final A3AudioPlayer.Music music);
    void musicPrepared(final A3AudioPlayer.Music music);
    void musicStarted(final A3AudioPlayer.Music music);
    void musicPaused(final A3AudioPlayer.Music music);
    void musicResumed(final A3AudioPlayer.Music music);
    void musicStopped(final A3AudioPlayer.Music music, final int loopsLeft);
    void musicUnloaded(final A3AudioPlayer.Music music);

}
