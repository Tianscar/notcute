package a3wt.awt;

import a3wt.app.*;
import a3wt.audio.A3AudioKit;
import a3wt.bundle.A3BundleKit;
import a3wt.graphics.A3Cursor;
import a3wt.graphics.A3Graphics;
import a3wt.graphics.A3GraphicsKit;
import a3wt.graphics.A3Image;
import a3wt.input.A3ContextListener;
import a3wt.input.A3ContainerListener;
import a3wt.input.A3InputListener;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsConfiguration;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ComponentListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowFocusListener;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static a3wt.awt.A3AWTSharedState.getFullscreenWindow;
import static a3wt.awt.A3AWTSharedState.setFullscreenWindow;
import static a3wt.awt.A3AWTUtils.commonMousePressed;
import static a3wt.awt.A3AWTUtils.commonMouseReleased;
import static a3wt.awt.A3AWTUtils.commonMouseDragged;
import static a3wt.awt.A3AWTUtils.commonMouseMoved;
import static a3wt.awt.A3AWTUtils.commonMouseEntered;
import static a3wt.awt.A3AWTUtils.commonMouseExited;
import static a3wt.awt.A3AWTUtils.commonMouseWheelMoved;
import static a3wt.awt.A3AWTUtils.commonKeyTyped;
import static a3wt.awt.A3AWTUtils.commonKeyPressed;
import static a3wt.awt.A3AWTUtils.commonKeyReleased;
import static a3wt.awt.A3AWTUtils.A3Images2BufferedImages;
import static a3wt.awt.A3AWTUtils.awtImages2A3Images;

public class A3AWTWindow extends Window implements AWTA3Container, ComponentListener, WindowListener, WindowFocusListener,
        MouseInputListener, MouseWheelListener, KeyListener {

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        commonMousePressed(holder.inputListeners, e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        commonMouseReleased(holder.inputListeners, e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        commonMouseEntered(holder.inputListeners, e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        commonMouseExited(holder.inputListeners, e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        commonMouseDragged(holder.inputListeners, e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        commonMouseMoved(holder.inputListeners, e);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        commonMouseWheelMoved(holder.inputListeners, e);
    }

    @Override
    public void keyTyped(final KeyEvent e) {
        commonKeyTyped(holder.inputListeners, e);
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        commonKeyPressed(holder.inputListeners, e);
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        commonKeyReleased(holder.inputListeners, e);
    }

    protected static class A3AWTWindowHolder implements A3Context.Holder, A3Container.Holder {

        @Override
        public A3Container getContainer() {
            return window;
        }

        @Override
        public void setIconImages(final List<A3Image> images) {
            window.setIconImages(A3Images2BufferedImages(images));
        }

        @Override
        public List<A3Image> getIconImages() {
            return awtImages2A3Images(window.getIconImages());
        }

        @Override
        public A3Context getContext() {
            return window.canvas.holder.getContext();
        }

        @Override
        public A3Platform getPlatform() {
            return window.canvas.holder.getPlatform();
        }

        @Override
        public A3Logger getLogger() {
            return window.canvas.holder.getLogger();
        }

        @Override
        public A3Factory getFactory() {
            return window.canvas.holder.getFactory();
        }

        @Override
        public A3I18NText getI18NText() {
            return window.canvas.holder.getI18NText();
        }

        @Override
        public A3GraphicsKit getGraphicsKit() {
            return window.canvas.holder.getGraphicsKit();
        }

        @Override
        public A3BundleKit getBundleKit() {
            return window.canvas.holder.getBundleKit();
        }

        @Override
        public A3AudioKit getAudioKit() {
            return window.canvas.holder.getAudioKit();
        }

        @Override
        public int getScreenWidth() {
            return window.canvas.holder.getScreenWidth();
        }

        @Override
        public int getScreenHeight() {
            return window.canvas.holder.getScreenHeight();
        }

        @Override
        public int getMinScreenWidth() {
            return window.canvas.holder.getMinScreenWidth();
        }

        @Override
        public int getMinScreenHeight() {
            return window.canvas.holder.getMinScreenHeight();
        }

        @Override
        public int getMaxScreenWidth() {
            return window.canvas.holder.getMaxScreenWidth();
        }

        @Override
        public int getMaxScreenHeight() {
            return window.canvas.holder.getMaxScreenHeight();
        }

        @Override
        public int getPPI() {
            return window.canvas.holder.getPPI();
        }

        @Override
        public float getDensity() {
            return A3AWTUtils.getDensity();
        }

        @Override
        public float getScaledDensity() {
            return A3AWTUtils.getScaledDensity(window.getPPIScale());
        }

        @Override
        public void postRunnable(Runnable runnable) {
            EventQueue.invokeLater(runnable);
        }

        protected final A3AWTWindow window;

        public A3AWTWindowHolder(A3AWTWindow window) {
            this.window = window;
        }

        protected final List<A3ContainerListener> containerListeners = new CopyOnWriteArrayList<>();
        protected final List<A3InputListener> inputListeners = new CopyOnWriteArrayList<>();

        @Override
        public A3Graphics getGraphics() {
            return window.canvas.holder.getGraphics();
        }

        @Override
        public int getWidth() {
            return window.canvas.getWidth();
        }

        @Override
        public int getHeight() {
            return window.canvas.getHeight();
        }

        @Override
        public int getBackgroundColor() {
            return window.canvas.holder.getBackgroundColor();
        }

        @Override
        public void setBackgroundColor(final int color) {
            window.canvas.holder.setBackgroundColor(color);
        }

        @Override
        public long elapsed() {
            return window.canvas.holder.elapsed();
        }

        @Override
        public void update() {
            window.checkDisposed("Can't call update() on a disposed A3Container");
            window.canvas.holder.update();
        }

        @Override
        public A3Image updateAndSnapshot() {
            return window.canvas.holder.updateAndSnapshot();
        }

        @Override
        public List<A3ContextListener> getContextListeners() {
            return window.canvas.holder.getContextListeners();
        }

        @Override
        public void addContextListener(A3ContextListener listener) {
            window.canvas.holder.addContextListener(listener);
        }

        @Override
        public List<A3InputListener> getContextInputListeners() {
            return window.canvas.holder.inputListeners;
        }

        @Override
        public void addContextInputListener(A3InputListener listener) {
            window.canvas.holder.inputListeners.add(listener);
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
        public void paint(final A3Graphics graphics, final boolean snapshot) {
            window.canvas.holder.paint(graphics, snapshot);
        }

        @Override
        public A3Preferences getPreferences(final String name) {
            return window.canvas.holder.getPreferences(name);
        }

        @Override
        public boolean deletePreferences(final String name) {
            return window.canvas.holder.deletePreferences(name);
        }

        @Override
        public A3Assets getAssets() {
            return window.canvas.holder.getAssets();
        }

        @Override
        public File getCacheDir() {
            return window.canvas.holder.getCacheDir();
        }

        @Override
        public File getConfigDir() {
            return window.canvas.holder.getConfigDir();
        }

        @Override
        public File getFilesDir(final String type) {
            return window.canvas.holder.getFilesDir(type);
        }

        @Override
        public File getHomeDir() {
            return window.canvas.holder.getHomeDir();
        }

        @Override
        public File getTmpDir() {
            return window.canvas.holder.getTmpDir();
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
        public void setFullscreen(final boolean fullscreen) {
            if (fullscreen) {
                setFullscreenWindow(window);
            }
            else {
                setFullscreenWindow(null);
            }
        }

        @Override
        public boolean isFullscreen() {
            return getFullscreenWindow() == window;
        }

        @Override
        public A3Clipboard getClipboard() {
            return window.canvas.holder.getClipboard();
        }

        @Override
        public A3Clipboard getSelection() {
            return window.canvas.holder.getSelection();
        }

        @Override
        public A3Clipboard createClipboard(final String name) {
            return window.canvas.holder.createClipboard(name);
        }

        @Override
        public A3Cursor getCursor() {
            return window.canvas.holder.getCursor();
        }

        @Override
        public boolean browse(final URI uri) {
            return window.canvas.holder.browse(uri);
        }

        @Override
        public boolean open(final File file) {
            return window.canvas.holder.open(file);
        }

        @Override
        public void setCursor(final A3Cursor cursor) {
            window.canvas.holder.setCursor(cursor);
        }

    }

    protected final A3AWTWindowHolder holder;

    @Override
    public A3Context.Holder getContextHolder() {
        return canvas.holder;
    }

    @Override
    public A3Container.Holder getContainerHolder() {
        return holder;
    }

    protected final A3AWTCanvas canvas;

    public A3AWTWindow(Frame owner) {
        super(owner);
        setLocationByPlatform(true);
        enableInputMethods(false);
        canvas = new A3AWTCanvas();
        add(canvas);
        addComponentListener(this);
        addWindowListener(this);
        addWindowFocusListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        holder = new A3AWTWindowHolder(this);
        setMinimumSize(new Dimension(holder.getMinScreenWidth(), holder.getMinScreenHeight()));
    }

    public A3AWTWindow(Window owner) {
        super(owner);
        setLocationByPlatform(true);
        enableInputMethods(false);
        canvas = new A3AWTCanvas();
        add(canvas);
        addComponentListener(this);
        addWindowListener(this);
        addWindowFocusListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        holder = new A3AWTWindowHolder(this);
        setMinimumSize(new Dimension(holder.getMinScreenWidth(), holder.getMinScreenHeight()));
    }

    public A3AWTWindow(Window owner, GraphicsConfiguration gc) {
        super(owner, gc);
        setLocationByPlatform(true);
        enableInputMethods(false);
        canvas = new A3AWTCanvas();
        add(canvas);
        addComponentListener(this);
        addWindowListener(this);
        addWindowFocusListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        holder = new A3AWTWindowHolder(this);
        setMinimumSize(new Dimension(holder.getMinScreenWidth(), holder.getMinScreenHeight()));
    }

    @Override
    public void setVisible(final boolean b) {
        super.setVisible(b);
        if (b) canvas.requestFocus();
    }

    @Override
    public String getCompanyName() {
        return canvas.getCompanyName();
    }

    @Override
    public String getAppName() {
        return canvas.getAppName();
    }

    @Override
    public void setCompanyName(String companyName) {
        canvas.setCompanyName(companyName);
    }

    @Override
    public void setAppName(String appName) {
        canvas.setAppName(appName);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        for (A3ContainerListener listener : holder.containerListeners) {
            listener.containerResized(getWidth(), getHeight());
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        for (A3ContainerListener listener : holder.containerListeners) {
            listener.containerMoved(getX(), getY());
        }
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
        for (A3ContainerListener listener : holder.containerListeners) {
            listener.containerCreated();
        }
        for (A3ContainerListener listener : holder.containerListeners) {
            listener.containerStarted();
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        boolean close = true;
        for (A3ContainerListener listener : holder.containerListeners) {
            close = close && listener.containerCloseRequested();
        }
        if (close) dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        for (A3ContainerListener listener : holder.containerListeners) {
            listener.containerDisposed();
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {
        for (A3ContainerListener listener : holder.containerListeners) {
            listener.containerStopped();
        }
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        for (A3ContainerListener listener : holder.containerListeners) {
            listener.containerStarted();
        }
    }

    @Override
    public void windowActivated(WindowEvent e) {
        for (A3ContainerListener listener : holder.containerListeners) {
            listener.containerResumed();
        }
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        for (A3ContainerListener listener : holder.containerListeners) {
            listener.containerPaused();
        }
    }

    @Override
    public void windowGainedFocus(WindowEvent e) {
        for (A3ContainerListener listener : holder.containerListeners) {
            listener.containerFocusGained();
        }
        canvas.requestFocus();
    }

    @Override
    public void windowLostFocus(WindowEvent e) {
        for (A3ContainerListener listener : holder.containerListeners) {
            listener.containerFocusLost();
        }
    }

    @Override
    public File getPreferencesFile(String name) {
        return canvas.getPreferencesFile(name);
    }

    @Override
    public void setPPIScale(float scale) {
        canvas.setPPIScale(scale);
    }

    @Override
    public float getPPIScale() {
        return canvas.getPPIScale();
    }

    @Override
    public boolean isDisposed() {
        return canvas.isDisposed();
    }

    @Override
    public void dispose() {
        if (isDisposed()) return;
        canvas.dispose();
        super.dispose();
    }

}
