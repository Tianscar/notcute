package com.ansdoship.a3wt.android;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import com.ansdoship.a3wt.app.*;
import com.ansdoship.a3wt.bundle.A3BundleKit;
import com.ansdoship.a3wt.graphics.A3Cursor;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3GraphicsKit;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.input.A3ContextListener;
import com.ansdoship.a3wt.input.A3ContainerListener;
import com.ansdoship.a3wt.input.A3InputListener;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

import static com.ansdoship.a3wt.android.A3AndroidUtils.commonOnTouchEvent;
import static com.ansdoship.a3wt.android.A3AndroidUtils.commonOnHoverEvent;
import static com.ansdoship.a3wt.android.A3AndroidUtils.commonOnMouseWheelMotion;
import static com.ansdoship.a3wt.android.A3AndroidUtils.commonOnKeyEvent;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class A3AndroidDialog extends Dialog implements AndroidA3Container,
        View.OnLayoutChangeListener, View.OnHoverListener, DialogInterface.OnDismissListener, DialogInterface.OnKeyListener {

    @Override
    public boolean onGenericMotionEvent(final MotionEvent event) {
        return commonOnMouseWheelMotion(handle.inputListeners, event) || super.onGenericMotionEvent(event);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        return commonOnTouchEvent(handle.inputListeners, event) || super.onTouchEvent(event);
    }

    @Override
    public boolean onHover(final View v, final MotionEvent event) {
        return commonOnHoverEvent(handle.inputListeners, event);
    }

    @Override
    public boolean onKey(final DialogInterface dialog, final int keyCode, final KeyEvent event) {
        return commonOnKeyEvent(handle.inputListeners, event);
    }

    protected static class A3AndroidDialogHandle implements A3Context.Handle, A3Container.Handle {

        @Override
        public A3Context getContext() {
            return dialog.surfaceView.handle.getContext();
        }

        @Override
        public A3Platform getPlatform() {
            return dialog.surfaceView.handle.getPlatform();
        }

        @Override
        public A3Logger getLogger() {
            return dialog.surfaceView.handle.getLogger();
        }

        @Override
        public A3I18NText getI18NText() {
            return dialog.surfaceView.handle.getI18NText();
        }

        @Override
        public A3GraphicsKit getGraphicsKit() {
            return dialog.surfaceView.handle.getGraphicsKit();
        }

        @Override
        public A3BundleKit getBundleKit() {
            return dialog.surfaceView.handle.getBundleKit();
        }

        @Override
        public int getScreenWidth() {
            return dialog.surfaceView.handle.getScreenWidth();
        }

        @Override
        public int getScreenHeight() {
            return dialog.surfaceView.handle.getScreenHeight();
        }

        @Override
        public int getMinScreenWidth() {
            return dialog.surfaceView.handle.getMinScreenWidth();
        }

        @Override
        public int getMinScreenHeight() {
            return dialog.surfaceView.handle.getMinScreenHeight();
        }

        @Override
        public int getMaxScreenWidth() {
            return dialog.surfaceView.handle.getMaxScreenWidth();
        }

        @Override
        public int getMaxScreenHeight() {
            return dialog.surfaceView.handle.getMaxScreenHeight();
        }

        @Override
        public int getPPI() {
            return dialog.surfaceView.handle.getPPI();
        }

        @Override
        public float getDensity() {
            return dialog.surfaceView.handle.getDensity();
        }

        @Override
        public float getScaledDensity() {
            return dialog.surfaceView.handle.getScaledDensity();
        }

        @Override
        public void postRunnable(final Runnable runnable) {
            dialog.surfaceView.post(runnable);
        }

        protected final A3AndroidDialog dialog;

        public A3AndroidDialogHandle(final A3AndroidDialog dialog) {
            checkArgNotNull(dialog, "dialog");
            this.dialog = dialog;
        }

        protected final List<A3ContainerListener> containerListeners = new ArrayList<>();
        protected final List<A3InputListener> inputListeners = new ArrayList<>();

        @Override
        public A3Graphics getGraphics() {
            return dialog.surfaceView.handle.getGraphics();
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
            return dialog.surfaceView.handle.getBackgroundColor();
        }

        @Override
        public void setBackgroundColor(int color) {
            dialog.surfaceView.handle.setBackgroundColor(color);
        }

        @Override
        public long elapsed() {
            return dialog.surfaceView.handle.elapsed();
        }

        @Override
        public void paint(final A3Graphics graphics, final boolean snapshot) {
            dialog.surfaceView.handle.paint(graphics, snapshot);
        }

        @Override
        public void update() {
            dialog.checkDisposed("Can't call update() on a disposed A3Container");
            dialog.surfaceView.handle.update();
        }

        @Override
        public A3Image updateAndSnapshot() {
            return dialog.surfaceView.handle.updateAndSnapshot();
        }

        @Override
        public List<A3ContextListener> getContextListeners() {
            return dialog.surfaceView.handle.getContextListeners();
        }

        @Override
        public void addContextListener(final A3ContextListener listener) {
            dialog.surfaceView.handle.addContextListener(listener);
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
            return dialog.surfaceView.handle.getContextInputListeners();
        }

        @Override
        public void addContextInputListener(final A3InputListener listener) {
            dialog.surfaceView.handle.addContextInputListener(listener);
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
            return dialog.surfaceView.handle.getPreferences(name);
        }

        @Override
        public boolean deletePreferences(final String name) {
            return dialog.surfaceView.handle.deletePreferences(name);
        }

        @Override
        public A3Assets getAssets() {
            return dialog.surfaceView.handle.getAssets();
        }

        @Override
        public File getCacheDir() {
            return dialog.surfaceView.handle.getCacheDir();
        }

        @Override
        public File getConfigDir() {
            return dialog.surfaceView.handle.getConfigDir();
        }

        @Override
        public File getFilesDir(final String type) {
            return dialog.surfaceView.handle.getFilesDir(type);
        }

        @Override
        public File getHomeDir() {
            return dialog.surfaceView.handle.getHomeDir();
        }

        @Override
        public File getTmpDir() {
            return dialog.surfaceView.handle.getTmpDir();
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
            return dialog.surfaceView.handle.getClipboard();
        }

        @Override
        public A3Clipboard getSelection() {
            return dialog.surfaceView.handle.getSelection();
        }

        @Override
        public A3Clipboard createClipboard(final String name) {
            return dialog.surfaceView.handle.createClipboard(name);
        }

        @Override
        public void setCursor(final A3Cursor cursor) {
            dialog.surfaceView.handle.setCursor(cursor);
        }

        @Override
        public A3Cursor getCursor() {
            return dialog.surfaceView.handle.getCursor();
        }

    }

    protected A3AndroidDialogHandle handle;

    @Override
    public A3Context.Handle getContextHandle() {
        return surfaceView.handle;
    }

    @Override
    public A3Container.Handle getContainerHandle() {
        return handle;
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
        if (handle == null) handle = new A3AndroidDialogHandle(this);
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
        for (A3ContainerListener listener : handle.containerListeners) {
            listener.containerResumed();
        }
    }

    @Override
    protected void onStop() {
        for (A3ContainerListener listener : handle.containerListeners) {
            listener.containerPaused();
        }
        super.onStop();
        for (A3ContainerListener listener : handle.containerListeners) {
            listener.containerStopped();
        }
    }

    @Override
    public void onWindowFocusChanged(final boolean hasFocus) {
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
        if (isDisposed()) return;
        surfaceView.dispose();
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        dispose();
        for (A3ContainerListener listener : handle.containerListeners) {
            listener.containerDisposed();
        }
        handle = null;
    }

    @Override
    public void onLayoutChange(final View v, final int left, final int top, final int right, final int bottom,
                               final int oldLeft, final int oldTop, final int oldRight, final int oldBottom) {
        final int width = right - left;
        final int height = bottom - top;
        for (A3ContainerListener listener : handle.containerListeners) {
            listener.containerResized(width, height);
        }
        for (A3ContainerListener listener : handle.containerListeners) {
            listener.containerMoved(left, top);
        }
    }

    @Override
    public void dismiss() {
        boolean close = true;
        for (A3ContainerListener listener : handle.containerListeners) {
            close = close && listener.containerCloseRequested();
        }
        if (close) super.dismiss();
    }

}
