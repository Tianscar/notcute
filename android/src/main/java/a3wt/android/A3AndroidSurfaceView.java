package a3wt.android;

import a3wt.app.*;
import a3wt.graphics.*;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.MotionEvent;
import android.view.KeyEvent;
import android.view.PointerIcon;
import a3wt.audio.A3AudioPlayer;
import a3wt.bundle.A3BundleKit;
import a3wt.bundle.DefaultA3BundleKit;
import a3wt.input.A3ContextListener;
import a3wt.input.A3InputListener;
import a3wt.util.A3Maps;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import static a3wt.android.A3AndroidUtils.getSharedPreferencesDir;
import static a3wt.android.A3AndroidUtils.isExternalStorageWriteable;
import static a3wt.android.A3AndroidUtils.getStorageDir;
import static a3wt.android.A3AndroidUtils.commonOnTouchEvent;
import static a3wt.android.A3AndroidUtils.commonOnHoverEvent;
import static a3wt.android.A3AndroidUtils.commonOnKeyEvent;
import static a3wt.android.A3AndroidUtils.commonOnMouseWheelMotion;
import static a3wt.util.A3Preconditions.checkArgNotEmpty;
import static a3wt.util.A3Preconditions.checkArgNotNull;
import static a3wt.util.A3Colors.WHITE;
import static a3wt.util.A3Files.createDirIfNotExist;

public class A3AndroidSurfaceView extends SurfaceView implements AndroidA3Context, SurfaceHolder.Callback,
        View.OnLayoutChangeListener, View.OnTouchListener, View.OnHoverListener, View.OnKeyListener, View.OnGenericMotionListener {

    {
        final ServiceLoader<A3Initializer> serviceLoader = ServiceLoader.load(A3Initializer.class, A3Initializer.class.getClassLoader());
        for (final A3Initializer initializer : serviceLoader) {
            initializer.initialize();
        }
    }

    @Override
    public boolean onTouch(final View v, final MotionEvent event) {
        return commonOnTouchEvent(holder.inputListeners, event);
    }

    @Override
    public boolean onHover(final View v, final MotionEvent event) {
        return commonOnHoverEvent(holder.inputListeners, event);
    }

    @Override
    public boolean onKey(final View v, final int keyCode, final KeyEvent event) {
        return commonOnKeyEvent(holder.inputListeners, event);
    }

    @Override
    public boolean onGenericMotion(final View v, final MotionEvent event) {
        return commonOnMouseWheelMotion(holder.inputListeners, event);
    }

    protected static class A3AndroidSurfaceViewHolder implements Holder {

        protected static final AndroidA3Platform platform = new AndroidA3Platform();
        protected static final AndroidA3GraphicsKit graphicsKit = new AndroidA3GraphicsKit();
        protected static final DefaultA3BundleKit bundleKit = new DefaultA3BundleKit();
        static {
            bundleKit.putExtMapBundleableMapping(A3Arc.class, graphicsKit::createArc);
            bundleKit.putExtMapBundleableMapping(A3CubicCurve.class, graphicsKit::createCubicCurve);
            bundleKit.putExtMapBundleableMapping(A3Line.class, graphicsKit::createLine);
            bundleKit.putExtMapBundleableMapping(A3Oval.class, graphicsKit::createOval);
            bundleKit.putExtMapBundleableMapping(A3Point.class, graphicsKit::createPoint);
            bundleKit.putExtMapBundleableMapping(A3QuadCurve.class, graphicsKit::createQuadCurve);
            bundleKit.putExtMapBundleableMapping(A3Rect.class, graphicsKit::createRect);
            bundleKit.putExtMapBundleableMapping(A3RoundRect.class, graphicsKit::createRoundRect);
            bundleKit.putExtMapBundleableMapping(A3Size.class, graphicsKit::createSize);
            bundleKit.putExtMapBundleableMapping(A3Transform.class, graphicsKit::createTransform);
        }
        protected static final AndroidA3AudioPlayer audioPlayer = new AndroidA3AudioPlayer();

        @Override
        public A3Factory getFactory() {
            return factory;
        }

        @Override
        public A3Context getContext() {
            return surfaceView;
        }

        @Override
        public A3Platform getPlatform() {
            return platform;
        }

        @Override
        public A3GraphicsKit getGraphicsKit() {
            return graphicsKit;
        }

        @Override
        public A3BundleKit getBundleKit() {
            return bundleKit;
        }

        @Override
        public A3AudioPlayer getAudioPlayer() {
            return audioPlayer;
        }

        protected static final A3Logger logger = new AndroidA3Logger();
        @Override
        public A3Logger getLogger() {
            return logger;
        }
        protected static final A3I18NText i18NText = new DefaultA3I18NText();
        @Override
        public A3I18NText getI18NText() {
            return i18NText;
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
        public int getMinScreenWidth() {
            return 0;
        }

        @Override
        public int getMinScreenHeight() {
            return 0;
        }

        @Override
        public int getMaxScreenWidth() {
            return 0;
        }

        @Override
        public int getMaxScreenHeight() {
            return 0;
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

        public A3AndroidSurfaceViewHolder(final A3AndroidSurfaceView surfaceView) {
            checkArgNotNull(surfaceView, "surfaceView");
            this.surfaceView = surfaceView;
            graphicsKit.attach(surfaceView.getContext());
            cursor = (AndroidA3Cursor) graphicsKit.getDefaultCursor();
            assetsRef.compareAndSet(null, new AndroidA3Assets(surfaceView.getContext().getAssets()));
            clipboardRef.compareAndSet(null, new AndroidA3Clipboard((ClipboardManager) surfaceView.getContext().getSystemService(Context.CLIPBOARD_SERVICE)));
            selectionRef.compareAndSet(null, new AndroidA3Clipboard(A3Clipboard.SelectionType.SELECTION));
        }

        public static final File TMPDIR;
        static {
            final String tmpDirPath = System.getProperty("java.io.tmpdir");
            if (tmpDirPath == null) TMPDIR = null;
            else TMPDIR = new File(tmpDirPath);
        }
        
        protected static final AtomicReference<AndroidA3Assets> assetsRef = new AtomicReference<>();
        protected static final AtomicReference<AndroidA3Clipboard> clipboardRef = new AtomicReference<>();
        protected static final AtomicReference<AndroidA3Clipboard> selectionRef = new AtomicReference<>();
        
        protected volatile long elapsed = 0;
        protected final AndroidA3Graphics graphics = new A3SurfaceViewGraphics();
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
        public void paint(final A3Graphics graphics, final boolean snapshot) {
            checkArgNotNull(graphics, "graphics");
            for (A3ContextListener listener : contextListeners) {
                listener.contextPainted(graphics, snapshot);
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

        private void renderOffscreen(final Canvas canvas, final boolean snapshot) {
            canvas.drawColor(backgroundColor);
            graphics.setCanvas(canvas, getWidth(), getHeight());
            canvas.save();
            paint(graphics, snapshot);
            canvas.restore();
            graphics.setCanvas(null, -1, -1);
        }

        @Override
        public void update() {
            surfaceView.checkDisposed("Can't call update() on a disposed A3Context");
            final long time = System.currentTimeMillis();
            final Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                renderOffscreen(canvas, false);
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
            final long now = System.currentTimeMillis();
            elapsed = now - time;
        }

        @Override
        public A3Image updateAndSnapshot() {
            final Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            final Canvas tmp = new Canvas(bitmap);
            renderOffscreen(tmp, true);
            final Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                canvas.drawBitmap(bitmap, 0, 0, null);
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
            return new AndroidA3Image(bitmap);
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

        @Override
        public A3Clipboard getSelection() {
            return selectionRef.get();
        }

        protected static final Map<String, A3Clipboard> applicationClipboards = new ConcurrentHashMap<>();
        @Override
        public A3Clipboard createClipboard(final String name) {
            checkArgNotEmpty(name, "name");
            if (!applicationClipboards.containsKey(name)) A3Maps.putIfAbsent(applicationClipboards, name, new AndroidA3Clipboard(A3Clipboard.SelectionType.APPLICATION));
            return applicationClipboards.get(name);
        }

        protected volatile A3Cursor cursor = graphicsKit.getDefaultCursor();
        protected volatile CursorAnimator cursorAnimator;

        @Override
        public void setCursor(final A3Cursor cursor) {
            checkArgNotNull(cursor, "cursor");
            if (cursorAnimator != null) cursorAnimator.stop();
            this.cursor = cursor;
            if (cursor instanceof A3FramedCursor) {
                cursorAnimator = new CursorAnimator(surfaceView, (A3FramedCursor) cursor);
                cursorAnimator.start();
            }
            else {
                cursorAnimator = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    surfaceView.setPointerIcon((PointerIcon) ((AndroidA3Cursor) cursor).pointerIcon);
                }
            }
        }

        @Override
        public A3Cursor getCursor() {
            return cursor;
        }

        @Override
        public boolean browse(final URI uri) {
            checkArgNotNull(uri, "uri");
            final Context context = ((AndroidA3Context)getContext()).getContext();
            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString()));
            try {
                context.startActivity(intent);
                return true;
            }
            catch (final ActivityNotFoundException e) {
                return false;
            }
        }

        @Override
        public boolean open(final File file) {
            checkArgNotNull(file, "file");
            final Context context = ((AndroidA3Context)getContext()).getContext();
            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(file.getAbsolutePath()));
            try {
                context.startActivity(intent);
                return true;
            }
            catch (final ActivityNotFoundException e) {
                return false;
            }
        }

        protected static final DefaultA3Factory factory = new DefaultA3Factory();
        static {
            factory.putMapping("a3wt", "assets", assetsRef::get);
            factory.putMapping("a3wt", "i18nTest", () -> i18NText);
            factory.putMapping("a3wt", "logger", () -> logger);
            factory.putMapping("a3wt", "platform", () -> platform);
            factory.putMapping("a3wt", "audioPlayer", () -> audioPlayer);
            factory.putMapping("a3wt", "bundleKit", () -> bundleKit);
            factory.putMapping("a3wt", "graphicsKit", () -> graphicsKit);
        }

    }

    protected final A3AndroidSurfaceViewHolder holder;

    @Override
    public Holder getContextHolder() {
        return holder;
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
        holder = new A3AndroidSurfaceViewHolder(this);
        holder.surfaceHolder = getHolder();
        holder.surfaceHolder.addCallback(this);
        addOnLayoutChangeListener(this);
        setOnTouchListener(this);
        setOnHoverListener(this);
        setOnGenericMotionListener(this);
        setOnKeyListener(this);
    }

    @Override
    public void surfaceCreated(final SurfaceHolder surfaceHolder) {
        if (!holder.surfaceFirstCreated) {
            holder.surfaceFirstCreated = true;
            for (A3ContextListener listener : holder.contextListeners) {
                listener.contextCreated();
            }
        }
    }

    @Override
    public void surfaceChanged(final SurfaceHolder surfaceHolder, final int format, final int width, final int height) {
    }

    @Override
    public void surfaceDestroyed(final SurfaceHolder surfaceHolder) {
    }

    @Override
    protected void onFocusChanged(final boolean gainFocus, final int direction, final Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus) {
            for (final A3ContextListener listener : holder.contextListeners) {
                listener.contextFocusGained();
            }
        }
        else {
            for (final A3ContextListener listener : holder.contextListeners) {
                listener.contextFocusLost();
            }
        }
    }

    @Override
    protected void onVisibilityChanged(final View changedView, final int visibility) {
        if (visibility == VISIBLE) {
            for (final A3ContextListener listener : holder.contextListeners) {
                listener.contextShown();
            }
        }
        else {
            for (final A3ContextListener listener : holder.contextListeners) {
                listener.contextHidden();
            }
        }
    }

    @Override
    public void onLayoutChange(final View v, final int left, final int top, final int right, final int bottom,
                               final int oldLeft, final int oldTop, final int oldRight, final int oldBottom) {
        final int width = right - left;
        final int height = bottom - top;
        for (final A3ContextListener listener : holder.contextListeners) {
            listener.contextResized(width, height);
        }
        for (final A3ContextListener listener : holder.contextListeners) {
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
        for (final A3ContextListener listener : holder.contextListeners) {
            listener.contextDisposed();
        }
    }

}
