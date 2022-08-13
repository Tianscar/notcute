package com.ansdoship.a3wt.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import com.ansdoship.a3wt.app.A3Assets;
import com.ansdoship.a3wt.app.A3Preferences;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.input.A3ContextListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.ansdoship.a3wt.android.A3AndroidUtils.getSharedPreferencesDir;
import static com.ansdoship.a3wt.android.A3AndroidUtils.isExternalStorageWriteable;
import static com.ansdoship.a3wt.android.A3AndroidUtils.getStorageDir;
import static com.ansdoship.a3wt.util.A3FileUtils.createDirIfNotExist;

public class A3AndroidSurfaceView extends SurfaceView implements AndroidA3Context, SurfaceHolder.Callback, View.OnLayoutChangeListener {

    public static final File TMPDIR = new File(System.getProperty("java.io.tmpdir"));

    protected static volatile AndroidA3Assets assets = null;

    protected volatile long elapsed = 0;
    protected final AndroidA3Graphics graphics = new A3SurfaceViewGraphics();
    protected volatile Bitmap buffer = null;
    protected volatile int backgroundColor = 0xFF000000;
    protected volatile boolean surfaceFirstCreated = false;

    protected SurfaceHolder surfaceHolder;

    protected final List<A3ContextListener> a3ContextListeners = new ArrayList<>();
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
        if (assets == null) assets = new AndroidA3Assets(getContext().getAssets());
    }

    @Override
    public AndroidA3Graphics getA3Graphics() {
        return graphics;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!surfaceFirstCreated) {
            surfaceFirstCreated = true;
            for (A3ContextListener listener : a3ContextListeners) {
                listener.contextCreated();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        postUpdate();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    public long elapsed() {
        return elapsed;
    }

    @Override
    public void paint(A3Graphics graphics) {
        for (A3ContextListener listener : a3ContextListeners) {
            listener.contextPainted(graphics);
        }
    }

    public void update(Canvas canvas) {
        if (buffer == null) return;
        canvas.save();
        canvas.drawBitmap(buffer, 0, 0, null);
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
        checkDisposed("Can't call update() on a disposed A3Context");
        long time = System.currentTimeMillis();
        if (buffer != null && !buffer.isRecycled()) buffer.recycle();
        buffer = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas tmpCanvas = new Canvas(buffer);
        tmpCanvas.drawColor(backgroundColor);
        graphics.setCanvas(tmpCanvas);
        tmpCanvas.save();
        paint(graphics);
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
    public List<A3ContextListener> getA3ContextListeners() {
        return a3ContextListeners;
    }

    @Override
    public void addA3ContextListener(A3ContextListener listener) {
        a3ContextListeners.add(listener);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus) {
            for (A3ContextListener listener : a3ContextListeners) {
                listener.contextFocusGained();
            }
        }
        else {
            for (A3ContextListener listener : a3ContextListeners) {
                listener.contextFocusLost();
            }
        }
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        if (visibility == VISIBLE) {
            for (A3ContextListener listener : a3ContextListeners) {
                listener.contextShown();
            }
        }
        else {
            for (A3ContextListener listener : a3ContextListeners) {
                listener.contextHidden();
            }
        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        int width = right - left;
        int height = bottom - top;
        for (A3ContextListener listener : a3ContextListeners) {
            listener.contextResized(width, height);
        }
        for (A3ContextListener listener : a3ContextListeners) {
            listener.contextMoved(left, top);
        }
    }

    @Override
    public A3Preferences getPreferences(String name) {
        return new AndroidA3Preferences(getContext().getSharedPreferences(name, Context.MODE_PRIVATE), name);
    }

    @Override
    public boolean deletePreferences(String name) {
        return A3AndroidUtils.deleteSharedPreferences(getContext(), name);
    }

    @Override
    public A3Assets getA3Assets() {
        return assets;
    }

    @Override
    public File getCacheDir() {
        File cacheDir = isExternalStorageWriteable() ? getContext().getExternalCacheDir() : getContext().getCacheDir();
        createDirIfNotExist(cacheDir);
        return cacheDir;
    }

    @Override
    public File getConfigDir() {
        return getSharedPreferencesDir(getContext());
    }

    @Override
    public File getFilesDir(String type) {
        File filesDir = isExternalStorageWriteable() ? getContext().getExternalFilesDir(type) : new File(getContext().getFilesDir(), type);
        createDirIfNotExist(filesDir);
        return filesDir;
    }

    @Override
    public File getHomeDir() {
        return getStorageDir();
    }

    @Override
    public File getTmpDir() {
        return TMPDIR;
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
        for (A3ContextListener listener : a3ContextListeners) {
            listener.contextDisposed();
        }
    }

}
