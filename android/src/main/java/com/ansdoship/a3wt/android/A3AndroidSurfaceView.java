package com.ansdoship.a3wt.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.MotionEvent;
import android.view.KeyEvent;
import com.ansdoship.a3wt.A3WT;
import com.ansdoship.a3wt.app.A3Assets;
import com.ansdoship.a3wt.app.A3Preferences;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.input.A3ContextListener;
import com.ansdoship.a3wt.input.A3InputListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.ansdoship.a3wt.android.A3AndroidUtils.getSharedPreferencesDir;
import static com.ansdoship.a3wt.android.A3AndroidUtils.isExternalStorageWriteable;
import static com.ansdoship.a3wt.android.A3AndroidUtils.getStorageDir;
import static com.ansdoship.a3wt.android.A3AndroidUtils.commonOnTouchEvent;
import static com.ansdoship.a3wt.util.A3FileUtils.createDirIfNotExist;

public class A3AndroidSurfaceView extends SurfaceView implements AndroidA3Context, SurfaceHolder.Callback,
        View.OnLayoutChangeListener, View.OnTouchListener, View.OnKeyListener {

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return commonOnTouchEvent(handle.inputListeners, event);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }

    protected static class A3AndroidSurfaceViewHandle implements Handle {

        @Override
        public int getScreenWidth() {
            return A3AndroidUtils.getScreenWidth(surfaceView.getResources());
        }

        @Override
        public int getScreenHeight() {
            return A3AndroidUtils.getScreenHeight(surfaceView.getResources());
        }

        @Override
        public int getPPI() {
            return A3AndroidUtils.getPPI(surfaceView.getResources());
        }

        @Override
        public float getDensity() {
            return A3AndroidUtils.getDensity(surfaceView.getResources());
        }

        @Override
        public float getScaledDensity() {
            return A3AndroidUtils.getScaledDensity(surfaceView.getResources());
        }

        @Override
        public void postRunnable(Runnable runnable) {
            surfaceView.post(runnable);
        }

        protected final A3AndroidSurfaceView surfaceView;

        public A3AndroidSurfaceViewHandle(A3AndroidSurfaceView surfaceView) {
            this.surfaceView = surfaceView;
            if (assets == null) assets = new AndroidA3Assets(surfaceView.getContext().getAssets());
        }

        public static final File TMPDIR;
        static {
            String tmpDirPath = System.getProperty("java.io.tmpdir");
            if (tmpDirPath == null) TMPDIR = null;
            else TMPDIR = new File(tmpDirPath);
        }
        
        protected static volatile AndroidA3Assets assets = null;
        
        protected volatile long elapsed = 0;
        protected final AndroidA3Graphics graphics = new A3SurfaceViewGraphics();
        protected volatile Bitmap buffer = null;
        protected volatile int backgroundColor = 0xFF000000;
        protected volatile boolean surfaceFirstCreated = false;

        protected SurfaceHolder surfaceHolder;

        protected final List<A3ContextListener> contextListeners = new ArrayList<>();
        protected final List<A3InputListener> inputListeners = new ArrayList<>();

        @Override
        public AndroidA3Graphics getGraphics() {
            return graphics;
        }

        @Override
        public int getWidth() {
            return surfaceView.getWidth();
        }

        @Override
        public int getHeight() {
            return surfaceView.getHeight();
        }

        @Override
        public long elapsed() {
            return elapsed;
        }

        @Override
        public void paint(A3Graphics graphics) {
            for (A3ContextListener listener : contextListeners) {
                listener.contextPainted(graphics);
            }
        }

        @Override
        public int getBackgroundColor() {
            return backgroundColor;
        }

        @Override
        public void setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        @Override
        public synchronized void update() {
            surfaceView.checkDisposed("Can't call update() on a disposed A3Context");
            long time = System.currentTimeMillis();
            if (buffer != null && !buffer.isRecycled()) buffer.recycle();
            buffer = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas tmpCanvas = new Canvas(buffer);
            tmpCanvas.drawColor(backgroundColor);
            ((A3SurfaceViewGraphics)graphics).setCanvas(tmpCanvas, getWidth(), getHeight());
            tmpCanvas.save();
            paint(graphics);
            tmpCanvas.restore();
            ((A3SurfaceViewGraphics)graphics).setCanvas(null, 0, 0);
            surfaceView.postUpdate();
            long now = System.currentTimeMillis();
            elapsed = now - time;
        }

        @Override
        public synchronized A3Image snapshot() {
            Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            surfaceView.draw(canvas);
            return new AndroidA3Image(bitmap);
        }

        @Override
        public synchronized A3Image snapshotBuffer() {
            if (buffer == null) return null;
            return new AndroidA3Image(A3AndroidUtils.copyBitmap(buffer));
        }

        @Override
        public List<A3ContextListener> getContextListeners() {
            return contextListeners;
        }

        @Override
        public void addContextListener(A3ContextListener listener) {
            contextListeners.add(listener);
        }

        @Override
        public List<A3InputListener> getContextInputListeners() {
            return inputListeners;
        }

        @Override
        public void addContextInputListener(A3InputListener listener) {
            inputListeners.add(listener);
        }

        @Override
        public A3Preferences getPreferences(String name) {
            return new AndroidA3Preferences(surfaceView.getContext().getSharedPreferences(name, Context.MODE_PRIVATE), name);
        }

        @Override
        public boolean deletePreferences(String name) {
            return A3AndroidUtils.deleteSharedPreferences(surfaceView.getContext(), name);
        }

        @Override
        public A3Assets getAssets() {
            return assets;
        }

        @Override
        public File getCacheDir() {
            File cacheDir = isExternalStorageWriteable() ? surfaceView.getContext().getExternalCacheDir() : surfaceView.getContext().getCacheDir();
            createDirIfNotExist(cacheDir);
            return cacheDir;
        }

        @Override
        public File getConfigDir() {
            return getSharedPreferencesDir(surfaceView.getContext());
        }

        @Override
        public File getFilesDir(String type) {
            File filesDir = isExternalStorageWriteable() ? surfaceView.getContext().getExternalFilesDir(type) : new File(surfaceView.getContext().getFilesDir(), type);
            createDirIfNotExist(filesDir);
            return filesDir;
        }

        @Override
        public File getHomeDir() {
            return getStorageDir();
        }

        @Override
        public File getTmpDir() {
            return TMPDIR == null ? getFilesDir("tmp") : TMPDIR;
        }
        
    }

    protected final A3AndroidSurfaceViewHandle handle;

    @Override
    public Handle getContextHandle() {
        return handle;
    }
    
    protected volatile boolean disposed = false;

    private static class A3SurfaceViewGraphics extends AndroidA3Graphics {
        public A3SurfaceViewGraphics() {
            super(null, 0, 0);
        }
        public void setCanvas(Canvas canvas, int width, int height) {
            this.canvas = canvas;
            this.width = width;
            this.height = height;
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
        if (A3WT.getPlatform() == null) A3WT.setPlatform(new AndroidA3Platform());
        handle = new A3AndroidSurfaceViewHandle(this);
        handle.surfaceHolder = getHolder();
        handle.surfaceHolder.addCallback(this);
        addOnLayoutChangeListener(this);
        setOnTouchListener(this);
        setOnKeyListener(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!handle.surfaceFirstCreated) {
            handle.surfaceFirstCreated = true;
            for (A3ContextListener listener : handle.contextListeners) {
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

    public void update(Canvas canvas) {
        if (handle.buffer == null) return;
        canvas.save();
        canvas.drawBitmap(handle.buffer, 0, 0, null);
        canvas.restore();
    }

    public synchronized void postUpdate() {
        Canvas canvas = handle.surfaceHolder.lockCanvas();
        if (canvas == null) return;
        try {
            update(canvas);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            handle.surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus) {
            for (A3ContextListener listener : handle.contextListeners) {
                listener.contextFocusGained();
            }
        }
        else {
            for (A3ContextListener listener : handle.contextListeners) {
                listener.contextFocusLost();
            }
        }
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        if (visibility == VISIBLE) {
            for (A3ContextListener listener : handle.contextListeners) {
                listener.contextShown();
            }
        }
        else {
            for (A3ContextListener listener : handle.contextListeners) {
                listener.contextHidden();
            }
        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        int width = right - left;
        int height = bottom - top;
        for (A3ContextListener listener : handle.contextListeners) {
            listener.contextResized(width, height);
        }
        for (A3ContextListener listener : handle.contextListeners) {
            listener.contextMoved(left, top);
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
        if (handle.buffer != null && !handle.buffer.isRecycled()) handle.buffer.recycle();
        handle.buffer = null;
        for (A3ContextListener listener : handle.contextListeners) {
            listener.contextDisposed();
        }
    }

}
