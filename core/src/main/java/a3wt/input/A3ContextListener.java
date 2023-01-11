package a3wt.input;

import a3wt.graphics.A3Graphics;

public interface A3ContextListener {

    void contextCreated();
    void contextDisposed();
    void contextResized(final int width, final int height);
    void contextMoved(final int x, final int y);
    void contextShown();
    void contextHidden();
    void contextPainted(final A3Graphics graphics, final boolean snapshot);
    void contextFocusGained();
    void contextFocusLost();

}
