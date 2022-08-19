package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.app.A3Assets;
import com.ansdoship.a3wt.app.A3Preferences;
import com.ansdoship.a3wt.input.A3ContextListener;
import com.ansdoship.a3wt.input.A3InputListener;
import com.ansdoship.a3wt.util.A3Disposable;

import java.io.File;
import java.util.List;

public interface A3Context extends A3Disposable {

    interface Handle {
        void postRunnable(Runnable runnable);

        A3Graphics getGraphics();

        int getWidth();
        int getHeight();
        int getBackgroundColor();
        void setBackgroundColor(int color);

        long elapsed();
        void paint(A3Graphics graphics);
        void update();

        A3Image snapshot();
        A3Image snapshotBuffer();

        List<A3ContextListener> getContextListeners();
        void addContextListener(A3ContextListener listener);

        List<A3InputListener> getContextInputListeners();
        void addContextInputListener(A3InputListener listener);

        A3Preferences getPreferences(String name);
        boolean deletePreferences(String name);
        A3Assets getAssets();
        File getConfigDir();
        File getCacheDir();
        File getFilesDir(String type);
        File getHomeDir();
        File getTmpDir();

        int getScreenWidth();
        int getScreenHeight();
        int getPPI();
        float getDensity();
        float getScaledDensity();
    }

    Handle getContextHandle();

}
