package com.ansdoship.a3wt.android;

import android.content.ClipboardManager;
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
import com.ansdoship.a3wt.app.A3Platform;
import com.ansdoship.a3wt.app.A3Assets;
import com.ansdoship.a3wt.app.A3Clipboard;
import com.ansdoship.a3wt.app.A3Preferences;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3GraphicsKit;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.input.A3ContextListener;
import com.ansdoship.a3wt.input.A3InputListener;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.ansdoship.a3wt.android.A3AndroidUtils.getSharedPreferencesDir;
import static com.ansdoship.a3wt.android.A3AndroidUtils.isExternalStorageWriteable;
import static com.ansdoship.a3wt.android.A3AndroidUtils.getStorageDir;
import static com.ansdoship.a3wt.android.A3AndroidUtils.commonOnTouchEvent;
import static com.ansdoship.a3wt.android.A3AndroidUtils.commonOnKeyEvent;
import static com.ansdoship.a3wt.util.A3Asserts.checkArgNotEmpty;
import static com.ansdoship.a3wt.util.A3Asserts.checkArgNotNull;
import static com.ansdoship.a3wt.util.A3Colors.WHITE;
import static com.ansdoship.a3wt.util.A3Files.createDirIfNotExist;

public class A3AndroidSurfaceView extends SurfaceView implements AndroidA3Context, SurfaceHolder.Callback,
        View.OnLayoutChangeListener, View.OnTouchListener, View.OnKeyListener {

    @Override
    public boolean onTouch(final View v, final MotionEvent event) {
        return commonOnTouchEvent(handle.inputListeners, event);
    }

    @Override
    public boolean onKey(final View v, final int keyCode, final KeyEvent event) {
        return commonOnKeyEvent(handle.inputListeners, event);
    }

    protected static class A3AndroidSurfaceViewHandle implements Handle {

        protected static final AndroidA3Platform platform = new AndroidA3Platform();
        protected static final AndroidA3GraphicsKit graphicsKit = new AndroidA3GraphicsKit();

        @Override
        public A3Platform getPlatform() {
            return platform;
        }

        @Override
        public A3GraphicsKit getGraphicsKit() {
            return graphicsKit;
        }

        protected final Map<String, AndroidA3Preferences> preferencesMap = new ConcurrentHashMap<>();

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
        public void postRunnable(final Runnable runnable) {
            checkArgNotNull(runnable, "runnable");
            surfaceView.post(runnable);
        }

        protected final A3AndroidSurfaceView surfaceView;

        public A3AndroidSurfaceViewHandle(final A3AndroidSurfaceView surfaceView) {
            checkArgNotNull(surfaceView, "surfaceView");
            this.surfaceView = surfaceView;
            assetsRef.compareAndSet(null, new AndroidA3Assets(surfaceView.getContext().getAssets()));
            clipboardRef.compareAndSet(null, new AndroidA3Clipboard((ClipboardManager) surfaceView.getContext().getSystemService(Context.CLIPBOARD_SERVICE)));
        }

        public static final File TMPDIR;
        static {
            final String tmpDirPath = System.getProperty("java.io.tmpdir");
            if (tmpDirPath == null) TMPDIR = null;
            else TMPDIR = new File(tmpDirPath);
        }
        
        protected static final AtomicReference<AndroidA3Assets> assetsRef = new AtomicReference<>(null);
        protected static final AtomicReference<AndroidA3Clipboard> clipboardRef = new AtomicReference<>(null);
        
        protected volatile long elapsed = 0;
        protected final AndroidA3Graphics graphics = new A3SurfaceViewGraphics();
        protected volatile Bitmap buffer = null;
        protected final ReentrantReadWriteLock bufferLock = new ReentrantReadWriteLock(true);
        protected volatile int backgroundColor = WHITE;

        private volatile boolean surfaceFirstCreated = false;

        protected volatile SurfaceHolder surfaceHolder;

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
        public void paint(final A3Graphics graphics) {
            checkArgNotNull(graphics, "graphics");
            for (A3ContextListener listener : contextListeners) {
                listener.contextPainted(graphics);
            }
        }

        @Override
        public int getBackgroundColor() {
            return backgroundColor;
        }

        @Override
        public void setBackgroundColor(final int backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        @Override
        public void update() {
            surfaceView.checkDisposed("Can't call update() on a disposed A3Context");
            bufferLock.writeLock().lock();
            try {
                final long time = System.currentTimeMillis();
                if (buffer == null || buffer.getWidth() != getWidth() || buffer.getHeight() != getHeight()) {
                    if (buffer != null && !buffer.isRecycled()) buffer.recycle();
                    buffer = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                }
                final Canvas tmpCanvas = new Canvas(buffer);
                tmpCanvas.drawColor(backgroundColor);
                ((A3SurfaceViewGraphics)graphics).setCanvas(tmpCanvas, getWidth(), getHeight());
                tmpCanvas.save();
                paint(graphics);
                tmpCanvas.restore();
                ((A3SurfaceViewGraphics)graphics).setCanvas(null, -1, -1);
                surfaceView.postUpdate();
                final long now = System.currentTimeMillis();
                elapsed = now - time;
            }
            finally {
                bufferLock.writeLock().unlock();
            }
        }

        @Override
        public A3Image snapshot() {
            final Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(bitmap);
            surfaceView.draw(canvas);
            return new AndroidA3Image(bitmap);
        }

        @Override
        public A3Image snapshotBuffer() {
            bufferLock.readLock().lock();
            try {
                return buffer == null ? null : new AndroidA3Image(A3AndroidUtils.copyBitmap(buffer));
            }
            finally {
                bufferLock.readLock().unlock();
            }
        }

        @Override
        public List<A3ContextListener> getContextListeners() {
            return contextListeners;
        }

        @Override
        public void addContextListener(final A3ContextListener listener) {
            checkArgNotNull(listener, "listener");
            contextListeners.add(listener);
        }

        @Override
        public List<A3InputListener> getContextInputListeners() {
            return inputListeners;
        }

        @Override
        public void addContextInputListener(final A3InputListener listener) {
            checkArgNotNull(listener, "listener");
            inputListeners.add(listener);
        }

        @Override
        public A3Preferences getPreferences(final String name) {
            checkArgNotEmpty(name, "name");
            if (!preferencesMap.containsKey(name)) {
                preferencesMap.put(name, new AndroidA3Preferences(surfaceView.getContext().getSharedPreferences(name, Context.MODE_PRIVATE), name));
            }
            return preferencesMap.get(name);
        }

        @Override
        public boolean deletePreferences(final String name) {
            checkArgNotEmpty(name, "name");
            return A3AndroidUtils.deleteSharedPreferences(surfaceView.getContext(), name);
        }

        @Override
        public A3Assets getAssets() {
            return assetsRef.get();
        }

        @Override
        public File getCacheDir() {
            final File cacheDir = isExternalStorageWriteable() ? surfaceView.getContext().getExternalCacheDir() : surfaceView.getContext().getCacheDir();
            createDirIfNotExist(cacheDir);
            return cacheDir;
        }

        @Override
        public File getConfigDir() {
            return getSharedPreferencesDir(surfaceView.getContext());
        }

        @Override
        public File getFilesDir(String type) {
            if (type == null) type = "";
            final File filesDir = isExternalStorageWriteable() ? surfaceView.getContext().getExternalFilesDir(type) : new File(surfaceView.getContext().getFilesDir(), type);
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

        @Override
        public A3Clipboard getClipboard() {
            return clipboardRef.get();
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
            super(null, -1, -1);
        }
        public void setCanvas(final Canvas canvas, final int width, final int height) {
            this.canvas = canvas;
            this.width = width;
            this.height = height;
        }
    }

    public A3AndroidSurfaceView(final Context context) {
        this(context, null);
    }

    public A3AndroidSurfaceView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public A3AndroidSurfaceView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        handle = new A3AndroidSurfaceViewHandle(this);
        handle.surfaceHolder = getHolder();
        handle.surfaceHolder.addCallback(this);
        addOnLayoutChangeListener(this);
        setOnTouchListener(this);
        setOnKeyListener(this);
    }

    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        if (!handle.surfaceFirstCreated) {
            handle.surfaceFirstCreated = true;
            for (A3ContextListener listener : handle.contextListeners) {
                listener.contextCreated();
            }
        }
    }

    @Override
    public void surfaceChanged(final SurfaceHolder holder, final int format, final int width, final int height) {
        postUpdate();
    }

    @Override
    public void surfaceDestroyed(final SurfaceHolder holder) {
    }

    public void update(final Canvas canvas) {
        if (canvas == null) return;
        handle.bufferLock.readLock().lock();
        try {
            if (handle.buffer != null) {
                canvas.save();
                canvas.drawBitmap(handle.buffer, 0, 0, null);
                canvas.restore();
            }
        }
        finally {
            handle.bufferLock.readLock().unlock();
        }
    }

    public synchronized void postUpdate() {
        final Canvas canvas = handle.surfaceHolder.lockCanvas();
        if (canvas == null) return;
        try {
            update(canvas);
        }
        finally {
            handle.surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    protected void onFocusChanged(final boolean gainFocus, final int direction, final Rect previouslyFocusedRect) {
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
    protected void onVisibilityChanged(final View changedView, final int visibility) {
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
    public void onLayoutChange(final View v, final int left, final int top, final int right, final int bottom,
                               final int oldLeft, final int oldTop, final int oldRight, final int oldBottom) {
        final int width = right - left;
        final int height = bottom - top;
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
    public void dispose() {
        if (isDisposed()) return;
        disposed = true;
        if (handle.buffer != null && !handle.buffer.isRecycled()) handle.buffer.recycle();
        handle.buffer = null;
        for (A3ContextListener listener : handle.contextListeners) {
            listener.contextDisposed();
        }
    }

}
