package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.app.A3Assets;
import com.ansdoship.a3wt.app.A3Preferences;
import com.ansdoship.a3wt.input.A3ContextListener;
import com.ansdoship.a3wt.util.A3Disposable;

import java.io.File;
import java.util.List;

public interface A3Context extends A3Disposable {

    A3Graphics getA3Graphics();

    int getWidth();
    int getHeight();
    int getBackgroundColor();
    void setBackgroundColor(int color);

    long elapsed();
    void paint(A3Graphics graphics);
    void update();

    A3Image snapshot();
    A3Image snapshotBuffer();

    List<A3ContextListener> getA3ContextListeners();
    void addA3ContextListener(A3ContextListener listener);

    A3Preferences getPreferences(String name);
    boolean deletePreferences(String name);
    A3Assets getA3Assets();
    File getConfigDir();
    File getCacheDir();
    File getFilesDir(String type);
    File getHomeDir();
    File getTmpDir();

}
