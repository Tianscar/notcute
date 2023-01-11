package a3wt.app;

import a3wt.audio.A3AudioKit;
import a3wt.bundle.A3BundleKit;
import a3wt.graphics.A3Cursor;
import a3wt.graphics.A3Graphics;
import a3wt.graphics.A3GraphicsKit;
import a3wt.graphics.A3Image;
import a3wt.input.A3ContextListener;
import a3wt.input.A3InputListener;
import a3wt.util.A3Disposable;

import java.io.File;
import java.net.URI;
import java.util.List;

public interface A3Context extends A3Disposable {

    interface Handle {

        A3Context getContext();

        A3Platform getPlatform();
        A3Logger getLogger();
        A3I18NText getI18NText();
        A3GraphicsKit getGraphicsKit();
        A3BundleKit getBundleKit();
        A3AudioKit getAudioKit();

        void postRunnable(final Runnable runnable);

        A3Graphics getGraphics();

        int getWidth();
        int getHeight();
        int getBackgroundColor();
        void setBackgroundColor(final int color);

        long elapsed();
        void paint(final A3Graphics graphics, final boolean snapshot);
        void update();

        A3Image updateAndSnapshot();

        List<A3ContextListener> getContextListeners();
        void addContextListener(final A3ContextListener listener);

        List<A3InputListener> getContextInputListeners();
        void addContextInputListener(final A3InputListener listener);

        A3Preferences getPreferences(final String name);
        boolean deletePreferences(final String name);
        A3Assets getAssets();
        File getConfigDir();
        File getCacheDir();
        File getFilesDir(final String type);
        File getHomeDir();
        File getTmpDir();

        int getScreenWidth();
        int getScreenHeight();
        int getMinScreenWidth();
        int getMinScreenHeight();
        int getMaxScreenWidth();
        int getMaxScreenHeight();
        int getPPI();
        float getDensity();
        float getScaledDensity();
        default float px2dp(final float px) {
            return px / getDensity();
        }
        default float dp2px(final float dp) {
            return dp * getDensity();
        }
        default float px2sp(final float px) {
            return px / getScaledDensity();
        }
        default float sp2px(final float sp) {
            return sp * getScaledDensity();
        }
        A3Clipboard getClipboard();
        A3Clipboard getSelection();
        A3Clipboard createClipboard(final String name);

        void setCursor(final A3Cursor cursor);
        A3Cursor getCursor();

        boolean browse(final URI uri);
        boolean open(final File file);
    }

    Handle getContextHandle();

}