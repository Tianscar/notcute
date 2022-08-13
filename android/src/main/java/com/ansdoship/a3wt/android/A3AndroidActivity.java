package com.ansdoship.a3wt.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.ansdoship.a3wt.app.A3Assets;
import com.ansdoship.a3wt.app.A3Preferences;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.input.A3ContextListener;
import com.ansdoship.a3wt.input.A3ContainerListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class A3AndroidActivity extends Activity implements AndroidA3Container, View.OnLayoutChangeListener {

    @Override
    public Context getContext() {
        return this;
    }

    protected volatile A3AndroidSurfaceView surfaceView;
    protected final List<A3ContainerListener> a3ContainerListeners = new ArrayList<>();

    @Override
    public A3Graphics getA3Graphics() {
        return surfaceView.getA3Graphics();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (surfaceView == null) surfaceView = new A3AndroidSurfaceView(this);
        setContentView(surfaceView);
        getWindow().getDecorView().addOnLayoutChangeListener(this);
        for (A3ContainerListener listener : a3ContainerListeners) {
            listener.containerCreated();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (A3ContainerListener listener : a3ContainerListeners) {
            listener.containerStarted();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (A3ContainerListener listener : a3ContainerListeners) {
            listener.containerResumed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (A3ContainerListener listener : a3ContainerListeners) {
            listener.containerPaused();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (A3ContainerListener listener : a3ContainerListeners) {
            listener.containerStopped();
        }
    }

    @Override
    public int getWidth() {
        return getWindow().getDecorView().getWidth();
    }

    @Override
    public int getHeight() {
        return getWindow().getDecorView().getHeight();
    }

    @Override
    public int getBackgroundColor() {
        return surfaceView.getBackgroundColor();
    }

    @Override
    public void setBackgroundColor(int color) {
        surfaceView.setBackgroundColor(color);
    }

    @Override
    public long elapsed() {
        return surfaceView.elapsed();
    }

    @Override
    public void paint(A3Graphics graphics) {
        surfaceView.paint(graphics);
    }

    @Override
    public void update() {
        checkDisposed("Can't call update() on a disposed A3Container");
        surfaceView.update();
    }

    @Override
    public A3Image snapshot() {
        return surfaceView.snapshot();
    }

    @Override
    public A3Image snapshotBuffer() {
        return surfaceView.snapshotBuffer();
    }

    @Override
    public List<A3ContextListener> getA3ContextListeners() {
        return surfaceView.getA3ContextListeners();
    }

    @Override
    public void addA3ContextListener(A3ContextListener listener) {
        surfaceView.addA3ContextListener(listener);
    }

    @Override
    public List<A3ContainerListener> getA3ContainerListeners() {
        return a3ContainerListeners;
    }

    @Override
    public void addA3ContainerListener(A3ContainerListener listener) {
        a3ContainerListeners.add(listener);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            for (A3ContainerListener listener : a3ContainerListeners) {
                listener.containerFocusGained();
            }
        }
        else {
            for (A3ContainerListener listener : a3ContainerListeners) {
                listener.containerFocusLost();
            }
        }
    }

    @Override
    public boolean isDisposed() {
        return surfaceView.isDisposed();
    }

    @Override
    public void dispose() {
        surfaceView.dispose();
    }

    @Override
    protected void onDestroy() {
        if (isFinishing()) {
            dispose();
            super.onDestroy();
            for (A3ContainerListener listener : a3ContainerListeners) {
                listener.containerDisposed();
            }
        }
        else {
            super.onDestroy();
        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        int width = right - left;
        int height = bottom - top;
        for (A3ContainerListener listener : a3ContainerListeners) {
            listener.containerResized(width, height);
        }
        for (A3ContainerListener listener : a3ContainerListeners) {
            listener.containerMoved(left, top);
        }
    }

    @Override
    public void finish() {
        boolean close = true;
        for (A3ContainerListener listener : a3ContainerListeners) {
            close = close && listener.containerCloseRequested();
        }
        if (close) super.finish();
    }

    @Override
    public A3Preferences getPreferences(String name) {
        return surfaceView.getPreferences(name);
    }

    @Override
    public boolean deletePreferences(String name) {
        return surfaceView.deletePreferences(name);
    }

    @Override
    public A3Assets getA3Assets() {
        return surfaceView.getA3Assets();
    }

    @Override
    public File getCacheDir() {
        return surfaceView.getCacheDir();
    }

    @Override
    public File getConfigDir() {
        return surfaceView.getConfigDir();
    }

    @Override
    public File getFilesDir(String type) {
        return surfaceView.getFilesDir(type);
    }

    @Override
    public File getHomeDir() {
        return surfaceView.getHomeDir();
    }

    @Override
    public File getTmpDir() {
        return surfaceView.getTmpDir();
    }

}
