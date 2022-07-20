package com.ansdoship.a3wt.android;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.ansdoship.a3wt.graphics.A3Container;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.input.A3CanvasListener;
import com.ansdoship.a3wt.input.A3ContainerListener;

import java.util.ArrayList;
import java.util.List;

public class A3AndroidActivity extends Activity implements A3Container {

    protected volatile A3AndroidSurfaceView surfaceView;
    protected final List<A3ContainerListener> a3ContainerListeners = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (surfaceView == null) surfaceView = new A3AndroidSurfaceView(this);
        setContentView(surfaceView);
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
        return A3AndroidUtils.getDisplayWidth(getResources());
    }

    @Override
    public int getHeight() {
        return A3AndroidUtils.getDisplayHeight(getResources());
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
    public List<A3CanvasListener> getA3CanvasListeners() {
        return surfaceView.getA3CanvasListeners();
    }

    @Override
    public void addA3CanvasListener(A3CanvasListener listener) {
        surfaceView.addA3CanvasListener(listener);
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
    public void finish() {
        boolean close = true;
        for (A3ContainerListener listener : a3ContainerListeners) {
            close = close && listener.containerCloseRequested();
        }
        if (close) super.finish();
    }

}
