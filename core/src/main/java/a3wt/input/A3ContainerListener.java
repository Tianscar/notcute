package a3wt.input;

public interface A3ContainerListener {

    void containerCreated();
    void containerDisposed();
    void containerStarted();
    void containerStopped();
    void containerResumed();
    void containerPaused();
    void containerFocusGained();
    void containerFocusLost();
    void containerResized(final int width, final int height);
    void containerMoved(final int x, final int y);
    default boolean containerCloseRequested() {
        return true;
    }

}
