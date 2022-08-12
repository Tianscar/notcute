package com.ansdoship.a3wt.android;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.ansdoship.a3wt.app.A3Context;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.input.A3CanvasListener;
import com.ansdoship.a3wt.input.A3ContainerListener;

import java.util.ArrayList;
import java.util.List;

public class A3AndroidDialog extends Dialog implements AndroidA3Container, View.OnLayoutChangeListener, DialogInterface.OnDismissListener {

    protected final AndroidA3Context context = new AndroidA3Context(this);

    protected volatile A3AndroidSurfaceView surfaceView;
    protected final List<A3ContainerListener> a3ContainerListeners = new ArrayList<>();

    public A3AndroidDialog(@NonNull Context context) {
        super(context);
    }

    public A3AndroidDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected A3AndroidDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public A3Context getA3Context() {
        return context;
    }

    @Override
    public A3Graphics getA3Graphics() {
        return surfaceView.getA3Graphics();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (surfaceView == null) surfaceView = new A3AndroidSurfaceView(getOwnerActivity());
        setContentView(surfaceView);
        getWindow().getDecorView().addOnLayoutChangeListener(this);
        setOnDismissListener(this);
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
        for (A3ContainerListener listener : a3ContainerListeners) {
            listener.containerResumed();
        }
    }

    @Override
    protected void onStop() {
        for (A3ContainerListener listener : a3ContainerListeners) {
            listener.containerPaused();
        }
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
    public void onDismiss(DialogInterface dialog) {
        dispose();
        for (A3ContainerListener listener : a3ContainerListeners) {
            listener.containerDisposed();
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
    public void dismiss() {
        boolean close = true;
        for (A3ContainerListener listener : a3ContainerListeners) {
            close = close && listener.containerCloseRequested();
        }
        if (close) super.dismiss();
    }

}
