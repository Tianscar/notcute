package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.app.A3Platform;
import com.ansdoship.a3wt.app.A3Preferences;
import com.ansdoship.a3wt.app.A3Clipboard;
import com.ansdoship.a3wt.graphics.A3Cursor;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3GraphicsKit;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.input.A3ContextListener;
import com.ansdoship.a3wt.input.A3InputListener;

import java.awt.Color;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.EventQueue;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import static com.ansdoship.a3wt.awt.A3AWTUtils.commonMousePressed;
import static com.ansdoship.a3wt.awt.A3AWTUtils.commonMouseReleased;
import static com.ansdoship.a3wt.awt.A3AWTUtils.commonMouseDragged;
import static com.ansdoship.a3wt.awt.A3AWTUtils.commonMouseMoved;
import static com.ansdoship.a3wt.awt.A3AWTUtils.commonMouseEntered;
import static com.ansdoship.a3wt.awt.A3AWTUtils.commonMouseExited;
import static com.ansdoship.a3wt.awt.A3AWTUtils.commonMouseWheelMoved;
import static com.ansdoship.a3wt.awt.A3AWTUtils.commonKeyTyped;
import static com.ansdoship.a3wt.awt.A3AWTUtils.commonKeyPressed;
import static com.ansdoship.a3wt.awt.A3AWTUtils.commonKeyReleased;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotEmpty;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;
import static com.ansdoship.a3wt.util.A3Files.createDirIfNotExist;

public class A3AWTCanvas extends Canvas implements AWTA3Context, ComponentListener, FocusListener,
        MouseInputListener, MouseWheelListener, HierarchyListener, KeyListener {

    @Override
    public void mouseClicked(final MouseEvent e) {
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        commonMousePressed(handle.inputListeners, e);
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        commonMouseReleased(handle.inputListeners, e);
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
        commonMouseEntered(handle.inputListeners, e);
    }

    @Override
    public void mouseExited(final MouseEvent e) {
        commonMouseExited(handle.inputListeners, e);
    }

    @Override
    public void mouseDragged(final MouseEvent e) {
        commonMouseDragged(handle.inputListeners, e);
    }

    @Override
    public void mouseMoved(final MouseEvent e) {
        commonMouseMoved(handle.inputListeners, e);
    }

    @Override
    public void mouseWheelMoved(final MouseWheelEvent e) {
        commonMouseWheelMoved(handle.inputListeners, e);
    }

    private boolean componentFirstVisible = true;

    @Override
    public void hierarchyChanged(final HierarchyEvent e) {
        if (!componentFirstVisible) return;
        if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) == HierarchyEvent.SHOWING_CHANGED) {
            componentFirstVisible = false;
            for (A3ContextListener listener : handle.contextListeners) {
                if (isVisible()) listener.contextCreated();
            }
        }
    }

    @Override
    public void keyTyped(final KeyEvent e) {
        commonKeyTyped(handle.inputListeners, e);
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        commonKeyPressed(handle.inputListeners, e);
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        commonKeyReleased(handle.inputListeners, e);
    }

    protected static class A3AWTCanvasHandle implements Handle {

        protected static final AWTA3Platform platform = new AWTA3Platform();
        protected static final AWTA3GraphicsKit graphicsKit = new AWTA3GraphicsKit();
        protected static final AWTA3MediaKit mediaKit = new AWTA3MediaKit();
        protected static final AWTA3MediaPlayer mediaPlayer = new AWTA3MediaPlayer();

        @Override
        public A3Platform getPlatform() {
            return platform;
        }

        @Override
        public A3GraphicsKit getGraphicsKit() {
            return graphicsKit;
        }

        @Override
        public AWTA3MediaKit getMediaKit() {
            return mediaKit;
        }

        @Override
        public AWTA3MediaPlayer getMediaPlayer() {
            return mediaPlayer;
        }

        protected final Map<String, AWTA3Preferences> preferencesMap = new ConcurrentHashMap<>();

        @Override
        public int getScreenWidth() {
            return A3AWTUtils.getScreenWidth();
        }

        @Override
        public int getScreenHeight() {
            return A3AWTUtils.getScreenHeight();
        }

        @Override
        public int getMinScreenWidth() {
            return A3AWTUtils.getMinScreenWidth();
        }

        @Override
        public int getMinScreenHeight() {
            return A3AWTUtils.getMinScreenHeight();
        }

        @Override
        public int getMaxScreenWidth() {
            return A3AWTUtils.getMaxScreenWidth();
        }

        @Override
        public int getMaxScreenHeight() {
            return A3AWTUtils.getMaxScreenHeight();
        }

        @Override
        public int getPPI() {
            return A3AWTUtils.getPPI();
        }

        @Override
        public float getDensity() {
            return A3AWTUtils.getDensity();
        }

        @Override
        public float getScaledDensity() {
            return A3AWTUtils.getScaledDensity(A3AWTSharedState.getPPIScale());
        }

        @Override
        public void postRunnable(final Runnable runnable) {
            checkArgNotNull(runnable, "runnable");
            EventQueue.invokeLater(runnable);
        }

        protected final A3AWTCanvas canvas;
        protected final ReentrantLock bufferLock = new ReentrantLock(true);

        public A3AWTCanvasHandle(final A3AWTCanvas canvas) {
            checkArgNotNull(canvas, "canvas");
            this.canvas = canvas;
        }

        protected static final AWTA3Assets assets = new AWTA3Assets();
        protected static final AWTA3Clipboard clipboard = new AWTA3Clipboard(A3Clipboard.SelectionType.CLIPBOARD);
        protected static final AWTA3Clipboard selection = new AWTA3Clipboard(A3Clipboard.SelectionType.SELECTION);

        public static final File HOME = new File(System.getProperty("user.home"));
        public static final File TMPDIR = new File(System.getProperty("java.io.tmpdir"));
        public static final File BASE_CACHE_DIR;
        public static final File BASE_CONFIG_DIR;
        public static final File BASE_FILES_DIR;
        static {
            if (AWTA3Platform.isWindows()) {
                final String APPDATA = System.getenv("APPDATA");
                final String LOCALAPPDATA = System.getenv("LOCALAPPDATA");
                final File Local;
                final File Roaming;
                if (LOCALAPPDATA == null) {
                    if (Float.parseFloat(AWTA3Platform.OS_VERSION) < 6.0) {
                        if (APPDATA == null) Local = new File(HOME, "Application Data");
                        else Local = new File(APPDATA);
                    }
                    else Local = new File(HOME, "AppData\\Local");
                }
                else Local = new File(LOCALAPPDATA);
                if (APPDATA == null) {
                    if (Float.parseFloat(AWTA3Platform.OS_VERSION) < 6.0) {
                        Roaming = new File(HOME, "Application Data");
                    }
                    else Roaming = new File(HOME, "AppData\\Roaming");
                }
                else Roaming = new File(APPDATA);
                BASE_CACHE_DIR = Local;
                BASE_FILES_DIR = BASE_CONFIG_DIR = Roaming;
            }
            else if (AWTA3Platform.isMac()) {
                BASE_CACHE_DIR = new File(HOME, "Library/Caches");
                BASE_CONFIG_DIR = new File(HOME, "Library/Preferences");
                BASE_FILES_DIR = new File(HOME, "Library/Application Support");
            }
            else if (AWTA3Platform.isX11()) {
                final String XDG_CACHE_HOME = System.getenv("XDG_CACHE_HOME");
                final String XDG_CONFIG_HOME = System.getenv("XDG_CONFIG_HOME");
                final String XDG_DATA_HOME = System.getenv("XDG_DATA_HOME");
                if (XDG_CACHE_HOME == null) BASE_CACHE_DIR = new File(HOME, ".cache");
                else BASE_CACHE_DIR = new File(XDG_CACHE_HOME);
                if (XDG_CONFIG_HOME == null) BASE_CONFIG_DIR = new File(HOME, ".config");
                else BASE_CONFIG_DIR = new File(XDG_CONFIG_HOME);
                if (XDG_DATA_HOME == null) BASE_FILES_DIR = new File(HOME, ".local/share");
                else BASE_FILES_DIR = new File(XDG_DATA_HOME);
            }
            else {
                BASE_CACHE_DIR = new File(HOME, ".cache");
                BASE_CONFIG_DIR = new File(HOME, ".config");
                BASE_FILES_DIR = new File(HOME, ".local/share");
            }
        }

        protected volatile long elapsed = 0;
        protected final AWTA3Graphics graphics = new A3CanvasGraphics();

        protected final List<A3ContextListener> contextListeners = new CopyOnWriteArrayList<>();
        protected final List<A3InputListener> inputListeners = new CopyOnWriteArrayList<>();

        @Override
        public A3Graphics getGraphics() {
            return graphics;
        }

        @Override
        public int getWidth() {
            return canvas.getBounds().width;
        }

        @Override
        public int getHeight() {
            return canvas.getBounds().height;
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
            return canvas.getBackground().getRGB();
        }

        @Override
        public void setBackgroundColor(final int color) {
            canvas.setBackground(new Color(color));
        }

        private void renderOffscreen(final Graphics g) {
            g.setColor(canvas.getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.BLACK);
            ((A3CanvasGraphics)graphics).setGraphics((Graphics2D) g, getWidth(), getHeight());
            paint(graphics);
            ((A3CanvasGraphics)graphics).setGraphics(null, -1, -1);
        }

        @Override
        public void update() {
            canvas.checkDisposed("Can't call update() on a disposed A3Context");
            bufferLock.lock();
            try {
                final long time = System.currentTimeMillis();
                if (!canvas.isDisposed() && canvas.getBufferStrategy() == null) canvas.createBufferStrategy(2);
                final BufferStrategy bufferStrategy = canvas.getBufferStrategy();
                if (bufferStrategy != null) do {
                    do {
                        final Graphics g = bufferStrategy.getDrawGraphics();
                        try {
                            renderOffscreen(g);
                        }
                        finally {
                            g.dispose();
                        }
                        try {
                            bufferStrategy.show();
                        }
                        catch (final Exception e) {
                            break;
                        }
                        finally {
                            Toolkit.getDefaultToolkit().sync();
                        }
                    } while (bufferStrategy.contentsRestored());
                } while (bufferStrategy.contentsLost());
                final long now = System.currentTimeMillis();
                elapsed = now - time;
            }
            finally {
                bufferLock.unlock();
            }
        }

        @Override
        public A3Image snapshot() {
            canvas.checkDisposed("Can't call snapshot() on a disposed A3Context");
            final BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            final Graphics2D g2d = bufferedImage.createGraphics();
            bufferLock.lock();
            try {
                renderOffscreen(g2d);
            }
            finally {
                bufferLock.unlock();
            }
            g2d.dispose();
            return new AWTA3Image(bufferedImage);
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
                preferencesMap.put(name, new AWTA3Preferences(canvas.getPreferencesFile(name)));
            }
            return preferencesMap.get(name);
        }

        @Override
        public boolean deletePreferences(final String name) {
            checkArgNotEmpty(name, "name");
            if (preferencesMap.containsKey(name)) {
                AWTA3Preferences preferences = preferencesMap.get(name);
                preferences.clear();
                return preferences.getFile().delete();
            }
            else return canvas.getPreferencesFile(name).delete();
        }

        @Override
        public AWTA3Assets getAssets() {
            return assets;
        }

        @Override
        public File getCacheDir() {
            final String ext;
            if (AWTA3Platform.isWindows()) ext = "Cache";
            else ext = "";
            final File cacheDir = new File(BASE_CACHE_DIR, canvas.getCompanyName() + "/" + canvas.getAppName() + "/" + ext);
            createDirIfNotExist(cacheDir);
            return cacheDir;
        }

        @Override
        public File getConfigDir() {
            final String ext;
            if (AWTA3Platform.isWindows()) ext = "Settings";
            else ext = "";
            final File configDir = new File(BASE_CONFIG_DIR, canvas.getCompanyName() + "/" + canvas.getAppName() + "/" + ext);
            createDirIfNotExist(configDir);
            return configDir;
        }

        @Override
        public File getFilesDir(String type) {
            if (type == null) type = "";
            final File filesDir = new File(new File(BASE_FILES_DIR, canvas.getCompanyName() + "/" + canvas.getAppName()), type);
            createDirIfNotExist(filesDir);
            return filesDir;
        }

        @Override
        public File getHomeDir() {
            return HOME;
        }

        @Override
        public File getTmpDir() {
            return TMPDIR;
        }

        @Override
        public A3Clipboard getClipboard() {
            return clipboard;
        }

        @Override
        public A3Clipboard getSelection() {
            return selection;
        }

        protected volatile AWTA3Cursor cursor = AWTA3Cursor.getDefaultCursor();

        @Override
        public void setCursor(final A3Cursor cursor) {
            checkArgNotNull(cursor, "cursor");
            this.cursor = (AWTA3Cursor) cursor;
            canvas.setCursor(this.cursor.cursor);
        }

        @Override
        public A3Cursor getCursor() {
            return cursor;
        }

    }

    protected final A3AWTCanvasHandle handle;

    @Override
    public Handle getContextHandle() {
        return handle;
    }

    protected volatile boolean disposed = false;

    private static class A3CanvasGraphics extends AWTA3Graphics {
        public A3CanvasGraphics() {
            super(null, -1, -1);
        }
        public void setGraphics(final Graphics2D graphics2D, final int width, final int height) {
            save();
            this.graphics2D = graphics2D;
            this.width = width;
            this.height = height;
            restore();
        }
    }

    public A3AWTCanvas() {
        setIgnoreRepaint(true);
        setBackground(Color.WHITE);
        enableInputMethods(false);
        addComponentListener(this);
        addFocusListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        addHierarchyListener(this);
        addKeyListener(this);
        handle = new A3AWTCanvasHandle(this);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                repaint();
            }
        });
    }

    @Override
    public String getAppName() {
        return A3AWTSharedState.getAppName();
    }

    @Override
    public String getCompanyName() {
        return A3AWTSharedState.getCompanyName();
    }

    @Override
    public void setAppName(final String appName) {
        A3AWTSharedState.setAppName(appName);
    }

    @Override
    public void setCompanyName(final String companyName) {
        A3AWTSharedState.setCompanyName(companyName);
    }

    @Override
    public void componentResized(final ComponentEvent e) {
        for (A3ContextListener listener : handle.contextListeners) {
            listener.contextResized(e.getComponent().getWidth(), e.getComponent().getHeight());
        }
    }

    @Override
    public void componentMoved(final ComponentEvent e) {
        for (A3ContextListener listener : handle.contextListeners) {
            listener.contextMoved(e.getComponent().getX(), e.getComponent().getY());
        }
    }

    @Override
    public void componentShown(final ComponentEvent e) {
        for (A3ContextListener listener : handle.contextListeners) {
            listener.contextShown();
        }
    }

    @Override
    public void componentHidden(final ComponentEvent e) {
        for (A3ContextListener listener : handle.contextListeners) {
            listener.contextHidden();
        }
    }

    @Override
    public void focusGained(final FocusEvent e) {
        for (A3ContextListener listener : handle.contextListeners) {
            listener.contextFocusGained();
        }
    }

    @Override
    public void focusLost(final FocusEvent e) {
        for (A3ContextListener listener : handle.contextListeners) {
            listener.contextFocusLost();
        }
    }

    @Override
    public File getPreferencesFile(final String name) {
        checkArgNotEmpty(name, "name");
        return new File(handle.getConfigDir(), name + ".xml");
    }

    @Override
    public void setPPIScale(final float scale) {
        A3AWTSharedState.setPPIScale(scale);
    }

    @Override
    public float getPPIScale() {
        return A3AWTSharedState.getPPIScale();
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public void dispose() {
        if (isDisposed()) return;
        disposed = true;
        final BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy != null) bufferStrategy.dispose();
        for (A3ContextListener listener : handle.contextListeners) {
            listener.contextDisposed();
        }
    }

}
