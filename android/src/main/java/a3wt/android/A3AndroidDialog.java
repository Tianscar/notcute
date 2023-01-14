package a3wt.android;

import a3wt.app.*;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class A3AndroidDialog extends Dialog implements AndroidA3Container,
        View.OnLayoutChangeListener, View.OnHoverListener, DialogInterface.OnDismissListener, DialogInterface.OnKeyListener {

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
    public boolean onKey(final DialogInterface dialog, final int keyCode, final KeyEvent event) {
        return A3AndroidUtils.commonOnKeyEvent(holder.inputListeners, event);
    }

    protected static class A3AndroidDialogHolder implements A3Context.Holder, A3Container.Holder {

        @Override
        public A3Context getContext() {
            return dialog.surfaceView.holder.getContext();
        }

        @Override
        public A3Platform getPlatform() {
            return dialog.surfaceView.holder.getPlatform();
        }

        @Override
        public A3Logger getLogger() {
            return dialog.surfaceView.holder.getLogger();
        }

        @Override
        public A3Factory getFactory() {
            return dialog.surfaceView.holder.getFactory();
        }

        @Override
        public A3I18NText getI18NText() {
            return dialog.surfaceView.holder.getI18NText();
        }

        @Override
        public A3GraphicsKit getGraphicsKit() {
            return dialog.surfaceView.holder.getGraphicsKit();
        }

        @Override
        public A3BundleKit getBundleKit() {
            return dialog.surfaceView.holder.getBundleKit();
        }

        @Override
        public A3AudioPlayer getAudioPlayer() {
            return dialog.surfaceView.holder.getAudioPlayer();
        }

        @Override
        public int getScreenWidth() {
            return dialog.surfaceView.holder.getScreenWidth();
        }

        @Override
        public int getScreenHeight() {
            return dialog.surfaceView.holder.getScreenHeight();
        }

        @Override
        public int getMinScreenWidth() {
            return dialog.surfaceView.holder.getMinScreenWidth();
        }

        @Override
        public int getMinScreenHeight() {
            return dialog.surfaceView.holder.getMinScreenHeight();
        }

        @Override
        public int getMaxScreenWidth() {
            return dialog.surfaceView.holder.getMaxScreenWidth();
        }

        @Override
        public int getMaxScreenHeight() {
            return dialog.surfaceView.holder.getMaxScreenHeight();
        }

        @Override
        public int getPPI() {
            return dialog.surfaceView.holder.getPPI();
        }

        @Override
        public float getDensity() {
            return dialog.surfaceView.holder.getDensity();
        }

        @Override
        public float getScaledDensity() {
            return dialog.surfaceView.holder.getScaledDensity();
        }

        @Override
        public void postRunnable(final Runnable runnable) {
            dialog.surfaceView.post(runnable);
        }

        protected final A3AndroidDialog dialog;

        public A3AndroidDialogHolder(final A3AndroidDialog dialog) {
            checkArgNotNull(dialog, "dialog");
            this.dialog = dialog;
        }

        protected final List<A3ContainerListener> containerListeners = new ArrayList<>();
        protected final List<A3InputListener> inputListeners = new ArrayList<>();

        @Override
        public A3Graphics getGraphics() {
            return dialog.surfaceView.holder.getGraphics();
        }

        @Override
        public int getWidth() {
            return dialog.getWindow().getDecorView().getWidth();
        }

        @Override
        public int getHeight() {
            return dialog.getWindow().getDecorView().getHeight();
        }

        @Override
        public int getBackgroundColor() {
            return dialog.surfaceView.holder.getBackgroundColor();
        }

        @Override
        public void setBackgroundColor(int color) {
            dialog.surfaceView.holder.setBackgroundColor(color);
        }

        @Override
        public long elapsed() {
            return dialog.surfaceView.holder.elapsed();
        }

        @Override
        public void paint(final A3Graphics graphics, final boolean snapshot) {
            dialog.surfaceView.holder.paint(graphics, snapshot);
        }

        @Override
        public void update() {
            dialog.checkDisposed("Can't call update() on a disposed A3Container");
            dialog.surfaceView.holder.update();
        }

        @Override
        public A3Image updateAndSnapshot() {
            return dialog.surfaceView.holder.updateAndSnapshot();
        }

        @Override
        public List<A3ContextListener> getContextListeners() {
            return dialog.surfaceView.holder.getContextListeners();
        }

        @Override
        public void addContextListener(final A3ContextListener listener) {
            dialog.surfaceView.holder.addContextListener(listener);
        }

        @Override
        public A3Container getContainer() {
            return dialog;
        }

        @Override
        public void setIconImages(List<A3Image> images) {

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
            return dialog.surfaceView.holder.getContextInputListeners();
        }

        @Override
        public void addContextInputListener(final A3InputListener listener) {
            dialog.surfaceView.holder.addContextInputListener(listener);
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
            return dialog.surfaceView.holder.getPreferences(name);
        }

        @Override
        public boolean deletePreferences(final String name) {
            return dialog.surfaceView.holder.deletePreferences(name);
        }

        @Override
        public A3Assets getAssets() {
            return dialog.surfaceView.holder.getAssets();
        }

        @Override
        public File getCacheDir() {
            return dialog.surfaceView.holder.getCacheDir();
        }

        @Override
        public File getConfigDir() {
            return dialog.surfaceView.holder.getConfigDir();
        }

        @Override
        public File getFilesDir(final String type) {
            return dialog.surfaceView.holder.getFilesDir(type);
        }

        @Override
        public File getHomeDir() {
            return dialog.surfaceView.holder.getHomeDir();
        }

        @Override
        public File getTmpDir() {
            return dialog.surfaceView.holder.getTmpDir();
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
            return dialog.surfaceView.holder.getClipboard();
        }

        @Override
        public A3Clipboard getSelection() {
            return dialog.surfaceView.holder.getSelection();
        }

        @Override
        public A3Clipboard createClipboard(final String name) {
            return dialog.surfaceView.holder.createClipboard(name);
        }

        @Override
        public void setCursor(final A3Cursor cursor) {
            dialog.surfaceView.holder.setCursor(cursor);
        }

        @Override
        public A3Cursor getCursor() {
            return dialog.surfaceView.holder.getCursor();
        }

        @Override
        public boolean browse(final URI uri) {
            return dialog.surfaceView.holder.browse(uri);
        }

        @Override
        public boolean open(final File file) {
            return dialog.surfaceView.holder.open(file);
        }

    }

    protected A3AndroidDialogHolder holder;

    @Override
    public A3Context.Holder getContextHolder() {
        return surfaceView.holder;
    }

    @Override
    public A3Container.Holder getContainerHolder() {
        return holder;
    }

    protected A3AndroidSurfaceView surfaceView;

    public A3AndroidDialog(final Context context) {
        super(context);
    }

    public A3AndroidDialog(final Context context, final int themeResId) {
        super(context, themeResId);
    }

    protected A3AndroidDialog(final Context context, final boolean cancelable, final OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (surfaceView == null) surfaceView = new A3AndroidSurfaceView(getOwnerActivity());
        setContentView(surfaceView);
        getWindow().getDecorView().addOnLayoutChangeListener(this);
        getWindow().getDecorView().setOnHoverListener(this);
        setOnDismissListener(this);
        setOnKeyListener(this);
        if (holder == null) holder = new A3AndroidDialogHolder(this);
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
        for (A3ContainerListener listener : holder.containerListeners) {
            listener.containerResumed();
        }
    }

    @Override
    protected void onStop() {
        for (A3ContainerListener listener : holder.containerListeners) {
            listener.containerPaused();
        }
        super.onStop();
        for (A3ContainerListener listener : holder.containerListeners) {
            listener.containerStopped();
        }
    }

    @Override
    public void onWindowFocusChanged(final boolean hasFocus) {
        if (hasFocus) {
            for (A3ContainerListener listener : holder.containerListeners) {
                listener.containerFocusGained();
            }
            surfaceView.requestFocus();
        }
        else {
            for (A3ContainerListener listener : holder.containerListeners) {
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
    public void onDismiss(final DialogInterface dialog) {
        dispose();
        for (A3ContainerListener listener : holder.containerListeners) {
            listener.containerDisposed();
        }
        holder = null;
    }

    @Override
    public void onLayoutChange(final View v, final int left, final int top, final int right, final int bottom,
                               final int oldLeft, final int oldTop, final int oldRight, final int oldBottom) {
        final int width = right - left;
        final int height = bottom - top;
        for (A3ContainerListener listener : holder.containerListeners) {
            listener.containerResized(width, height);
        }
        for (A3ContainerListener listener : holder.containerListeners) {
            listener.containerMoved(left, top);
        }
    }

    @Override
    public void dismiss() {
        boolean close = true;
        for (A3ContainerListener listener : holder.containerListeners) {
            close = close && listener.containerCloseRequested();
        }
        if (close) super.dismiss();
    }

}
