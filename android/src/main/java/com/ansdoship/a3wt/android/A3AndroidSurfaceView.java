package com.ansdoship.a3wt.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.ansdoship.a3wt.graphics.A3Canvas;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.input.A3CanvasListener;

import java.util.ArrayList;
import java.util.List;

public class A3AndroidSurfaceView extends SurfaceView implements A3Canvas, SurfaceHolder.Callback, View.OnLayoutChangeListener {

    protected volatile long elapsed = 0;
    protected AndroidA3Graphics graphics = new A3SurfaceViewGraphics();
    protected volatile Bitmap buffer = null;
    protected volatile int backgroundColor = 0xFF000000;
    protected volatile boolean surfaceFirstCreated = false;

    protected SurfaceHolder surfaceHolder;

    protected final List<A3CanvasListener> a3CanvasListeners = new ArrayList<>();
    protected volatile boolean disposed = false;

    private static class A3SurfaceViewGraphics extends AndroidA3Graphics {
        public A3SurfaceViewGraphics() {
            super(null, -1, -1);
        }
        public void setCanvas(Canvas canvas) {
            this.canvas = canvas;
        }
    }

    public A3AndroidSurfaceView(Context context) {
        this(context, null);
    }

    public A3AndroidSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public A3AndroidSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        addOnLayoutChangeListener(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        if (!surfaceFirstCreated) {
            surfaceFirstCreated = true;
            for (A3CanvasListener listener : a3CanvasListeners) {
                listener.canvasCreated();
            }
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        postUpdate();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
    }

    @Override
    public long elapsed() {
        return elapsed;
    }

    @Override
    public void paint(A3Graphics graphics) {
        for (A3CanvasListener listener : a3CanvasListeners) {
            listener.canvasPainted(graphics);
        }
    }

    public void update(Canvas canvas) {
        if (buffer == null) return;
        canvas.drawBitmap(buffer, 0, 0, null);
        canvas.save();
        canvas.restore();
    }

    public synchronized void postUpdate() {
        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas == null) return;
        try {
            update(canvas);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void setBackgroundColor(int color) {
        this.backgroundColor = color;
    }

    @Override
    public int getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public synchronized void update() {
        checkDisposed("Can't call update() on a disposed A3Canvas");
        long time = System.currentTimeMillis();
        if (buffer != null && !buffer.isRecycled()) buffer.recycle();
        buffer = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas tmpCanvas = new Canvas(buffer);
        tmpCanvas.drawColor(backgroundColor);
        graphics.setCanvas(tmpCanvas);
        paint(graphics);
        tmpCanvas.save();
        tmpCanvas.restore();
        graphics.setCanvas(null);
        postUpdate();
        long now = System.currentTimeMillis();
        elapsed = now - time;
    }

    @Override
    public synchronized A3Image snapshot() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        draw(canvas);
        return new AndroidA3Image(bitmap);
    }

    @Override
    public synchronized A3Image snapshotBuffer() {
        if (buffer == null) return null;
        return new AndroidA3Image(A3AndroidUtils.copyBitmap(buffer));
    }

    @Override
    public List<A3CanvasListener> getA3CanvasListeners() {
        return a3CanvasListeners;
    }

    @Override
    public void addA3CanvasListener(A3CanvasListener listener) {
        a3CanvasListeners.add(listener);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus) {
            for (A3CanvasListener listener : a3CanvasListeners) {
                listener.canvasFocusGained();
            }
        }
        else {
            for (A3CanvasListener listener : a3CanvasListeners) {
                listener.canvasFocusLost();
            }
        }
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        if (visibility == VISIBLE) {
            for (A3CanvasListener listener : a3CanvasListeners) {
                listener.canvasShown();
            }
        }
        else {
            for (A3CanvasListener listener : a3CanvasListeners) {
                listener.canvasHidden();
            }
        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        int width = right - left;
        int height = bottom - top;
        for (A3CanvasListener listener : a3CanvasListeners) {
            listener.canvasResized(width, height);
        }
        for (A3CanvasListener listener : a3CanvasListeners) {
            listener.canvasMoved(left, top);
        }
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public synchronized void dispose() {
        if (isDisposed()) return;
        disposed = true;
        if (buffer != null && !buffer.isRecycled()) buffer.recycle();
        buffer = null;
        for (A3CanvasListener listener : a3CanvasListeners) {
            listener.canvasDisposed();
        }
    }

}
