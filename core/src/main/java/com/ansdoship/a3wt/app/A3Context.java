package com.ansdoship.a3wt.app;

import com.ansdoship.a3wt.bundle.A3BundleKit;
import com.ansdoship.a3wt.graphics.A3Cursor;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3GraphicsKit;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.input.A3ContextListener;
import com.ansdoship.a3wt.input.A3InputListener;
import com.ansdoship.a3wt.util.A3Disposable;
import com.ansdoship.a3wt.util.A3Paintable;

import java.io.File;
import java.util.List;

public interface A3Context extends A3Disposable {

    interface Handle {

        A3Context getContext();

        A3Platform getPlatform();
        A3GraphicsKit getGraphicsKit();
        A3BundleKit getBundleKit();

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
    }

    Handle getContextHandle();

}
