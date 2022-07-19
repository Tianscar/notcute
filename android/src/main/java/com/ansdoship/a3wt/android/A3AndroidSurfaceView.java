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
    protected volatile Canvas buffer = null;

    protected SurfaceHolder surfaceHolder;
    protected boolean destroyed = false;

    protected final List<A3CanvasListener> a3CanvasListeners = new ArrayList<>();

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
        destroyed = false;
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        destroyed = true;
    }

    @Override
    public long elapsed() {
        return elapsed;
    }

    @Override
    public void paint(A3Graphics graphics) {
        for (A3CanvasListener listener : a3CanvasListeners) {
            listener.canvasPaint(graphics);
        }
    }

    @Override
    public synchronized void update() {
        long time = System.currentTimeMillis();
        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas == null) return;
        Bitmap tmpBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        buffer = new Canvas(tmpBitmap);
        try {
            graphics.setCanvas(buffer);
            paint(graphics);
            buffer.save();
            buffer.restore();
            canvas.drawBitmap(tmpBitmap, 0, 0, null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            graphics.setCanvas(null);
            buffer.setBitmap(null);
            tmpBitmap.recycle();
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
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
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        buffer.setBitmap(bitmap);
        return new AndroidA3Image(bitmap);
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
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
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
        for (A3CanvasListener listener : a3CanvasListeners) {
            listener.canvasMoved(left, top);
            listener.canvasResized(right - left, bottom - top);
        }
    }

}
