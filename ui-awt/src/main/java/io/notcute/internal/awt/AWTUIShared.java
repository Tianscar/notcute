package io.notcute.internal.awt;

import java.awt.*;

public final class AWTUIShared {

    private AWTUIShared() {
        throw new UnsupportedOperationException();
    }

    private static volatile Window mFullscreenWindow = null;
    private static volatile boolean mUndecorated = false;
    private static volatile boolean mResizable = false;

    public static Window getFullscreenWindow() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getFullScreenWindow();
    }

    public static void setFullscreenWindow(final Window window) {
        if (mFullscreenWindow != window) {
            if (mFullscreenWindow != null) {
                final boolean visible = mFullscreenWindow.isVisible();
                if (mFullscreenWindow instanceof Dialog) {
                    final Dialog dialog = (Dialog) mFullscreenWindow;
                    dialog.setVisible(false);
                    dialog.setResizable(mResizable);
                    dialog.setUndecorated(mUndecorated);
                    dialog.setVisible(visible);
                }
                else if (mFullscreenWindow instanceof Frame) {
                    final Frame frame = (Frame) mFullscreenWindow;
                    frame.setVisible(false);
                    frame.setResizable(mResizable);
                    frame.setUndecorated(mUndecorated);
                    frame.setVisible(visible);
                }
            }
            mFullscreenWindow = window;
            final boolean visible = window.isVisible();
            window.setVisible(false);
            if (mFullscreenWindow instanceof Dialog) {
                final Dialog dialog = (Dialog) mFullscreenWindow;
                mResizable = dialog.isResizable();
                mUndecorated = dialog.isUndecorated();
                dialog.setResizable(false);
                dialog.setUndecorated(false);
            }
            else if (mFullscreenWindow instanceof Frame) {
                final Frame frame = (Frame) mFullscreenWindow;
                mResizable = frame.isResizable();
                mUndecorated = frame.isUndecorated();
                frame.setResizable(false);
                frame.setUndecorated(false);
            }
            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(window);
            window.setVisible(visible);
        }
    }

}
