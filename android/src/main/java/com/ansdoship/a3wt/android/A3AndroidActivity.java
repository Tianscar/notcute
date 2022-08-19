package com.ansdoship.a3wt.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import com.ansdoship.a3wt.app.A3Assets;
import com.ansdoship.a3wt.app.A3Preferences;
import com.ansdoship.a3wt.graphics.A3Container;
import com.ansdoship.a3wt.graphics.A3Context;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.input.A3ContextListener;
import com.ansdoship.a3wt.input.A3ContainerListener;
import com.ansdoship.a3wt.input.A3InputListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.ansdoship.a3wt.android.A3AndroidUtils.commonOnTouchEvent;

public class A3AndroidActivity extends Activity implements AndroidA3Container, View.OnLayoutChangeListener {

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return commonOnTouchEvent(handle.inputListeners, event) || super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    protected static class A3AndroidActivityHandle implements A3Context.Handle, A3Container.Handle {

        @Override
        public int getScreenWidth() {
            return activity.surfaceView.handle.getScreenWidth();
        }

        @Override
        public int getScreenHeight() {
            return activity.surfaceView.handle.getScreenHeight();
        }

        @Override
        public int getPPI() {
            return activity.surfaceView.handle.getPPI();
        }

        @Override
        public float getDensity() {
            return activity.surfaceView.handle.getDensity();
        }

        @Override
        public float getScaledDensity() {
            return activity.surfaceView.handle.getScaledDensity();
        }

        @Override
        public void postRunnable(Runnable runnable) {
            activity.runOnUiThread(runnable);
        }

        protected final A3AndroidActivity activity;
        
        public A3AndroidActivityHandle(A3AndroidActivity activity) {
            this.activity = activity;
        }

        protected final List<A3ContainerListener> containerListeners = new ArrayList<>();
        protected final List<A3InputListener> inputListeners = new ArrayList<>();

        @Override
        public A3Graphics getGraphics() {
            return activity.surfaceView.handle.getGraphics();
        }

        @Override
        public int getWidth() {
            return activity.getWindow().getDecorView().getWidth();
        }

        @Override
        public int getHeight() {
            return activity.getWindow().getDecorView().getHeight();
        }

        @Override
        public int getBackgroundColor() {
            return activity.surfaceView.handle.getBackgroundColor();
        }

        @Override
        public void setBackgroundColor(int color) {
            activity.surfaceView.setBackgroundColor(color);
        }

        @Override
        public long elapsed() {
            return activity.surfaceView.handle.elapsed();
        }

        @Override
        public void paint(A3Graphics graphics) {
            activity.surfaceView.handle.paint(graphics);
        }

        @Override
        public void update() {
            activity.checkDisposed("Can't call update() on a disposed A3Container");
            activity.surfaceView.handle.update();
        }

        @Override
        public A3Image snapshot() {
            return activity.surfaceView.handle.snapshot();
        }

        @Override
        public A3Image snapshotBuffer() {
            return activity.surfaceView.handle.snapshotBuffer();
        }

        @Override
        public List<A3ContextListener> getContextListeners() {
            return activity.surfaceView.handle.getContextListeners();
        }

        @Override
        public void addContextListener(A3ContextListener listener) {
            activity.surfaceView.handle.addContextListener(listener);
        }

        @Override
        public List<A3ContainerListener> getContainerListeners() {
            return containerListeners;
        }

        @Override
        public void addContainerListener(A3ContainerListener listener) {
            containerListeners.add(listener);
        }

        @Override
        public List<A3InputListener> getContextInputListeners() {
            return activity.surfaceView.handle.getContextInputListeners();
        }

        @Override
        public void addContextInputListener(A3InputListener listener) {
            activity.surfaceView.handle.addContextInputListener(listener);
        }

        @Override
        public List<A3InputListener> getContainerInputListeners() {
            return inputListeners;
        }

        @Override
        public void addContainerInputListener(A3InputListener listener) {
            inputListeners.add(listener);
        }

        @Override
        public A3Preferences getPreferences(String name) {
            return activity.surfaceView.handle.getPreferences(name);
        }

        @Override
        public boolean deletePreferences(String name) {
            return activity.surfaceView.handle.deletePreferences(name);
        }

        @Override
        public A3Assets getAssets() {
            return activity.surfaceView.handle.getAssets();
        }

        @Override
        public File getCacheDir() {
            return activity.surfaceView.handle.getCacheDir();
        }

        @Override
        public File getConfigDir() {
            return activity.surfaceView.handle.getConfigDir();
        }

        @Override
        public File getFilesDir(String type) {
            return activity.surfaceView.handle.getFilesDir(type);
        }

        @Override
        public File getHomeDir() {
            return activity.surfaceView.handle.getHomeDir();
        }

        @Override
        public File getTmpDir() {
            return activity.surfaceView.handle.getTmpDir();
        }

        @Override
        public void setFullscreen(boolean fullscreen) {

        }

        @Override
        public boolean isFullscreen() {
            return false;
        }

    }

    @Override
    public Context getContext() {
        return this;
    }
    
    protected volatile A3AndroidActivityHandle handle;

    @Override
    public A3Context.Handle getContextHandle() {
        return surfaceView.handle;
    }

    @Override
    public A3Container.Handle getContainerHandle() {
        return handle;
    }

    protected volatile A3AndroidSurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (surfaceView == null) surfaceView = new A3AndroidSurfaceView(this);
        setContentView(surfaceView);
        getWindow().getDecorView().addOnLayoutChangeListener(this);
        if (handle == null) handle = new A3AndroidActivityHandle(this);
        handle.postRunnable(new Runnable() {
            @Override
            public void run() {
                for (A3ContainerListener listener : handle.containerListeners) {
                    listener.containerCreated();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (A3ContainerListener listener : handle.containerListeners) {
            listener.containerStarted();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (A3ContainerListener listener : handle.containerListeners) {
            listener.containerResumed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (A3ContainerListener listener : handle.containerListeners) {
            listener.containerPaused();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (A3ContainerListener listener : handle.containerListeners) {
            listener.containerStopped();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            for (A3ContainerListener listener : handle.containerListeners) {
                listener.containerFocusGained();
            }
        }
        else {
            for (A3ContainerListener listener : handle.containerListeners) {
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
            for (A3ContainerListener listener : handle.containerListeners) {
                listener.containerDisposed();
            }
            handle = null;
        }
        else {
            super.onDestroy();
        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        int width = right - left;
        int height = bottom - top;
        for (A3ContainerListener listener : handle.containerListeners) {
            listener.containerResized(width, height);
        }
        for (A3ContainerListener listener : handle.containerListeners) {
            listener.containerMoved(left, top);
        }
    }

    @Override
    public void finish() {
        boolean close = true;
        for (A3ContainerListener listener : handle.containerListeners) {
            close = close && listener.containerCloseRequested();
        }
        if (close) super.finish();
    }

}
