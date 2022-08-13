package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.app.A3Preferences;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.input.A3ContextListener;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.EventQueue;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.ansdoship.a3wt.util.A3FileUtils.createDirIfNotExist;

public class A3AWTComponent extends Component implements AWTA3Context, ComponentListener, FocusListener {

    protected static final AWTA3Assets assets = new AWTA3Assets();

    public static final File HOME = new File(System.getProperty("user.home"));
    public static final File TMPDIR = new File(System.getProperty("java.io.tmpdir"));
    public static final File BASE_CACHE_DIR;
    public static final File BASE_CONFIG_DIR;
    public static final File BASE_FILES_DIR;
    static {
        String os = AWTA3Platform.OS_NAME.trim().toLowerCase();
        if (os.contains("win")) {
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
        else if (os.contains("nux") || os.contains("nix")) {
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
        else if (os.contains("mac") || os.contains("osx")) {
            BASE_CACHE_DIR = new File(HOME, "Library/Caches");
            BASE_CONFIG_DIR = new File(HOME, "Library/Preferences");
            BASE_FILES_DIR = new File(HOME, "Library/Application Support");
        }
        else {
            BASE_CACHE_DIR = new File(HOME, ".cache");
            BASE_CONFIG_DIR = new File(HOME, ".config");
            BASE_FILES_DIR = new File(HOME, ".local/share");
        }
    }

    protected volatile long elapsed = 0;
    protected final AWTA3Graphics graphics = new A3ComponentGraphics();
    protected volatile Image buffer = null;
    protected final List<A3ContextListener> a3ContextListeners = new ArrayList<>();
    protected volatile boolean disposed = false;
    protected static volatile String companyName = "";
    protected static volatile String appName = "";

    private static class A3ComponentGraphics extends AWTA3Graphics {
        public A3ComponentGraphics() {
            super(null, -1, -1);
        }
        public void setGraphics(Graphics2D graphics2D, int width, int height) {
            this.graphics2D = graphics2D;
            this.width = width;
            this.height = height;
            if (graphics2D != null) graphics2D.setRenderingHints(this.hints);
        }
    }

    public A3AWTComponent() {
        addComponentListener(this);
        addFocusListener(this);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                for (A3ContextListener listener : a3ContextListeners) {
                    listener.contextCreated();
                }
            }
        });
    }

    @Override
    public String getAppName() {
        return appName;
    }

    @Override
    public String getCompanyName() {
        return companyName;
    }

    @Override
    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public A3Graphics getA3Graphics() {
        return graphics;
    }

    @Override
    public void paint(Graphics g) {
        if (buffer != null) g.drawImage(buffer, 0, 0, null);
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void repaint(long tm, int x, int y, int width, int height) {
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

    @Override
    public int getBackgroundColor() {
        return getBackground().getRGB();
    }

    @Override
    public void setBackgroundColor(int color) {
        setBackground(new Color(color));
    }

    @Override
    public synchronized void update() {
        checkDisposed("Can't call update() on a disposed A3Context");
        long time = System.currentTimeMillis();
        buffer = createImage(getWidth(), getHeight());
        Graphics gTmp = buffer.getGraphics();
        gTmp.setColor(getBackground());
        gTmp.fillRect(0, 0, getWidth(), getHeight());
        gTmp.setColor(new Color(0x000000));
        ((A3ComponentGraphics)graphics).setGraphics((Graphics2D) gTmp, getWidth(), getHeight());
        paint(graphics);
        ((A3ComponentGraphics)graphics).setGraphics(null, -1, -1);
        gTmp.dispose();
        update(getGraphics());
        long now = System.currentTimeMillis();
        elapsed = now - time;
    }

    @Override
    public synchronized A3Image snapshot() {
        BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D gTmp = bufferedImage.createGraphics();
        print(gTmp);
        gTmp.dispose();
        return new AWTA3Image(bufferedImage);
    }

    @Override
    public synchronized A3Image snapshotBuffer() {
        if (buffer == null) return null;
        return new AWTA3Image(A3AWTUtils.copyImage(buffer));
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
    public void componentResized(ComponentEvent e) {
        for (A3ContextListener listener : a3ContextListeners) {
            listener.contextResized(e.getComponent().getWidth(), e.getComponent().getHeight());
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        for (A3ContextListener listener : a3ContextListeners) {
            listener.contextMoved(e.getComponent().getX(), e.getComponent().getY());
        }
    }

    @Override
    public void componentShown(ComponentEvent e) {
        for (A3ContextListener listener : a3ContextListeners) {
            listener.contextShown();
        }
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        for (A3ContextListener listener : a3ContextListeners) {
            listener.contextHidden();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        for (A3ContextListener listener : a3ContextListeners) {
            listener.contextFocusGained();
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        for (A3ContextListener listener : a3ContextListeners) {
            listener.contextFocusLost();
        }
    }

    @Override
    public A3Preferences getPreferences(String name) {
        return new AWTA3Preferences(getPreferencesFile(name));
    }

    @Override
    public boolean deletePreferences(String name) {
        getPreferences(name).clear();
        return getPreferencesFile(name).delete();
    }

    @Override
    public File getPreferencesFile(String name) {
        return new File(getConfigDir(), name);
    }

    @Override
    public AWTA3Assets getA3Assets() {
        return assets;
    }

    @Override
    public File getCacheDir() {
        final String ext;
        if (AWTA3Platform.OS_NAME.trim().toLowerCase().contains("win")) ext = "Cache";
        else ext = "";
        File cacheDir = new File(BASE_CACHE_DIR, getCompanyName() + "/" + getAppName() + "/" + ext);
        createDirIfNotExist(cacheDir);
        return cacheDir;
    }

    @Override
    public File getConfigDir() {
        final String ext;
        if (AWTA3Platform.OS_NAME.trim().toLowerCase().contains("win")) ext = "Settings";
        else ext = "";
        File configDir = new File(BASE_CONFIG_DIR, getCompanyName() + "/" + getAppName() + "/" + ext);
        createDirIfNotExist(configDir);
        return configDir;
    }

    @Override
    public File getFilesDir(String type) {
        File filesDir = new File(new File(BASE_FILES_DIR, getCompanyName() + "/" + getAppName()), type);
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
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public void dispose() {
        if (isDisposed()) return;
        disposed = true;
        buffer = null;
        for (A3ContextListener listener : a3ContextListeners) {
            listener.contextDisposed();
        }
    }

}
