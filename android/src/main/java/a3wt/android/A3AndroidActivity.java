package a3wt.android;

import a3wt.app.*;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import a3wt.audio.A3AudioPlayer;
import a3wt.bundle.A3BundleKit;
import a3wt.graphics.A3Cursor;
import a3wt.graphics.A3Graphics;
import a3wt.graphics.A3GraphicsKit;
import a3wt.graphics.A3Image;
import a3wt.input.A3ContextListener;
import a3wt.input.A3ContainerListener;
import a3wt.input.A3InputListener;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.ArrayList;

import static a3wt.util.A3Preconditions.checkArgNotNull;

public class A3AndroidActivity extends Activity implements AndroidA3Container, View.OnLayoutChangeListener, View.OnHoverListener {

    @Override
    public boolean onGenericMotionEvent(final MotionEvent event) {
        return A3AndroidUtils.commonOnMouseWheelMotion(holder.inputListeners, event) || super.onGenericMotionEvent(event);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        return A3AndroidUtils.commonOnTouchEvent(holder.inputListeners, event) || super.onTouchEvent(event);
    }

    @Override
    public boolean onHover(final View v, final MotionEvent event) {
        return A3AndroidUtils.commonOnHoverEvent(holder.inputListeners, event);
    }

    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        return A3AndroidUtils.commonOnKeyEvent(holder.inputListeners, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(final int keyCode, final KeyEvent event) {
        return A3AndroidUtils.commonOnKeyEvent(holder.inputListeners, event) || super.onKeyUp(keyCode, event);
    }

    protected static class A3AndroidActivityHolder implements A3Context.Holder, A3Container.Holder {

        @Override
        public A3Context getContext() {
            return activity.surfaceView.holder.getContext();
        }

        @Override
        public A3Platform getPlatform() {
            return activity.surfaceView.holder.getPlatform();
        }

        @Override
        public A3Logger getLogger() {
            return activity.surfaceView.holder.getLogger();
        }

        @Override
        public A3Factory getFactory() {
            return activity.surfaceView.holder.getFactory();
        }

        @Override
        public A3I18NText getI18NText() {
            return activity.surfaceView.holder.getI18NText();
        }

        @Override
        public A3GraphicsKit getGraphicsKit() {
            return activity.surfaceView.holder.getGraphicsKit();
        }

        @Override
        public A3BundleKit getBundleKit() {
            return activity.surfaceView.holder.getBundleKit();
        }

        @Override
        public A3AudioPlayer getAudioPlayer() {
            return activity.surfaceView.holder.getAudioPlayer();
        }

        @Override
        public int getScreenWidth() {
            return activity.surfaceView.holder.getScreenWidth();
        }

        @Override
        public int getScreenHeight() {
            return activity.surfaceView.holder.getScreenHeight();
        }

        @Override
        public int getMinScreenWidth() {
            return activity.surfaceView.holder.getMinScreenWidth();
        }

        @Override
        public int getMinScreenHeight() {
            return activity.surfaceView.holder.getMinScreenHeight();
        }

        @Override
        public int getMaxScreenWidth() {
            return activity.surfaceView.holder.getMaxScreenWidth();
        }

        @Override
        public int getMaxScreenHeight() {
            return activity.surfaceView.holder.getMaxScreenHeight();
        }

        @Override
        public int getPPI() {
            return activity.surfaceView.holder.getPPI();
        }

        @Override
        public float getDensity() {
            return activity.surfaceView.holder.getDensity();
        }

        @Override
        public float getScaledDensity() {
            return activity.surfaceView.holder.getScaledDensity();
        }

        @Override
        public void postRunnable(final Runnable runnable) {
            checkArgNotNull(runnable, "runnable");
            activity.runOnUiThread(runnable);
        }

        protected final A3AndroidActivity activity;
        
        public A3AndroidActivityHolder(final A3AndroidActivity activity) {
            checkArgNotNull(activity, "activity");
            this.activity = activity;
        }

        protected final List<A3ContainerListener> containerListeners = new ArrayList<>();
        protected final List<A3InputListener> inputListeners = new ArrayList<>();

        @Override
        public A3Graphics getGraphics() {
            return activity.surfaceView.holder.getGraphics();
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
            return activity.surfaceView.holder.getBackgroundColor();
        }

        @Override
        public void setBackgroundColor(final int color) {
            activity.surfaceView.holder.setBackgroundColor(color);
        }

        @Override
        public long elapsed() {
            return activity.surfaceView.holder.elapsed();
        }

        @Override
        public void paint(final A3Graphics graphics, final boolean snapshot) {
            activity.surfaceView.holder.paint(graphics, snapshot);
        }

        @Override
        public void update() {
            activity.checkDisposed("Can't call update() on a disposed A3Container");
            activity.surfaceView.holder.update();
        }

        @Override
        public A3Image updateAndSnapshot() {
            return activity.surfaceView.holder.updateAndSnapshot();
        }

        @Override
        public List<A3ContextListener> getContextListeners() {
            return activity.surfaceView.holder.getContextListeners();
        }

        @Override
        public void addContextListener(final A3ContextListener listener) {
            activity.surfaceView.holder.addContextListener(listener);
        }

        @Override
        public A3Container getContainer() {
            return activity;
        }

        @Override
        public void setIconImages(final List<A3Image> images) {
        }

        @Override
        public List<A3Image> getIconImages() {
            return null;
        }

        @Override
        public List<A3ContainerListener> getContainerListeners() {
            return containerListeners;
        }

        @Override
        public void addContainerListener(final A3ContainerListener listener) {
            containerListeners.add(listener);
        }

        @Override
        public List<A3InputListener> getContextInputListeners() {
            return activity.surfaceView.holder.getContextInputListeners();
        }

        @Override
        public void addContextInputListener(final A3InputListener listener) {
            activity.surfaceView.holder.addContextInputListener(listener);
        }

        @Override
        public List<A3InputListener> getContainerInputListeners() {
            return inputListeners;
        }

        @Override
        public void addContainerInputListener(final A3InputListener listener) {
            inputListeners.add(listener);
        }

        @Override
        public A3Preferences getPreferences(final String name) {
            return activity.surfaceView.holder.getPreferences(name);
        }

        @Override
        public boolean deletePreferences(final String name) {
            return activity.surfaceView.holder.deletePreferences(name);
        }

        @Override
        public A3Assets getAssets() {
            return activity.surfaceView.holder.getAssets();
        }

        @Override
        public File getCacheDir() {
            return activity.surfaceView.holder.getCacheDir();
        }

        @Override
        public File getConfigDir() {
            return activity.surfaceView.holder.getConfigDir();
        }

        @Override
        public File getFilesDir(final String type) {
            return activity.surfaceView.holder.getFilesDir(type);
        }

        @Override
        public File getHomeDir() {
            return activity.surfaceView.holder.getHomeDir();
        }

        @Override
        public File getTmpDir() {
            return activity.surfaceView.holder.getTmpDir();
        }

        @Override
        public void setFullscreen(final boolean fullscreen) {
            // FIXME
        }

        @Override
        public boolean isFullscreen() {
            // FIXME
            return false;
        }

        @Override
        public A3Clipboard getClipboard() {
            return activity.surfaceView.holder.getClipboard();
        }

        @Override
        public A3Clipboard getSelection() {
            return activity.surfaceView.holder.getSelection();
        }

        @Override
        public A3Clipboard createClipboard(final String name) {
            return activity.surfaceView.holder.createClipboard(name);
        }

        @Override
        public void setCursor(final A3Cursor cursor) {
            activity.surfaceView.holder.setCursor(cursor);
        }

        @Override
        public A3Cursor getCursor() {
            return activity.surfaceView.holder.getCursor();
        }

        @Override
        public boolean browse(final URI uri) {
            return activity.surfaceView.holder.browse(uri);
        }

        @Override
        public boolean open(final File file) {
            return activity.surfaceView.holder.open(file);
        }

    }

    @Override
    public Context getContext() {
        return this;
    }
    
    protected A3AndroidActivityHolder holder;

    @Override
    public A3Context.Holder getContextHolder() {
        return surfaceView.holder;
    }

    @Override
    public A3Container.Holder getContainerHolder() {
        return holder;
    }

    protected A3AndroidSurfaceView surfaceView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (surfaceView == null) surfaceView = new A3AndroidSurfaceView(this);
        setContentView(surfaceView);
        getWindow().getDecorView().addOnLayoutChangeListener(this);
        getWindow().getDecorView().setOnHoverListener(this);
        if (holder == null) holder = new A3AndroidActivityHolder(this);
        holder.postRunnable(new Runnable() {
            @Override
            public void run() {
                for (A3ContainerListener listener : holder.containerListeners) {
                    listener.containerCreated();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (A3ContainerListener listener : holder.containerListeners) {
            listener.containerStarted();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (A3ContainerListener listener : holder.containerListeners) {
            listener.containerResumed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (A3ContainerListener listener : holder.containerListeners) {
            listener.containerPaused();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (A3ContainerListener listener : holder.containerListeners) {
            listener.containerStopped();
        }
    }

    @Override
    public void onWindowFocusChanged(final boolean hasFocus) {
        if (hasFocus) {
            for (final A3ContainerListener listener : holder.containerListeners) {
                listener.containerFocusGained();
            }
            surfaceView.requestFocus();
        }
        else {
            for (final A3ContainerListener listener : holder.containerListeners) {
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
        if (isDisposed()) return;
        surfaceView.dispose();
    }

    @Override
    protected void onDestroy() {
        if (isFinishing()) {
            dispose();
            super.onDestroy();
            for (final A3ContainerListener listener : holder.containerListeners) {
                listener.containerDisposed();
            }
            holder = null;
        }
        else {
            super.onDestroy();
        }
    }

    @Override
    public void onLayoutChange(final View v, final int left, final int top, final int right, final int bottom,
                               final int oldLeft, final int oldTop, final int oldRight, final int oldBottom) {
        final int width = right - left;
        final int height = bottom - top;
        for (final A3ContainerListener listener : holder.containerListeners) {
            listener.containerResized(width, height);
        }
        for (final A3ContainerListener listener : holder.containerListeners) {
            listener.containerMoved(left, top);
        }
    }

    @Override
    public void finish() {
        boolean close = true;
        for (final A3ContainerListener listener : holder.containerListeners) {
            close = close && listener.containerCloseRequested();
        }
        if (close) super.finish();
    }

}
