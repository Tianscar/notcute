package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.app.A3Platform;
import com.ansdoship.a3wt.app.A3Assets;
import com.ansdoship.a3wt.app.A3Preferences;
import com.ansdoship.a3wt.app.A3Clipboard;
import com.ansdoship.a3wt.app.A3Container;
import com.ansdoship.a3wt.app.A3Context;
import com.ansdoship.a3wt.bundle.A3BundleKit;
import com.ansdoship.a3wt.graphics.A3Cursor;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3GraphicsKit;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.input.A3ContextListener;
import com.ansdoship.a3wt.input.A3ContainerListener;
import com.ansdoship.a3wt.input.A3InputListener;

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
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.ansdoship.a3wt.awt.A3AWTSharedState.getFullscreenWindow;
import static com.ansdoship.a3wt.awt.A3AWTSharedState.setFullscreenWindow;
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
import static com.ansdoship.a3wt.awt.A3AWTUtils.A3Images2BufferedImages;
import static com.ansdoship.a3wt.awt.A3AWTUtils.awtImages2A3Images;

public class A3AWTWindow extends Window implements AWTA3Container, ComponentListener, WindowListener, WindowFocusListener,
        MouseInputListener, MouseWheelListener, KeyListener {

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        commonMousePressed(handle.inputListeners, e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        commonMouseReleased(handle.inputListeners, e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        commonMouseEntered(handle.inputListeners, e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        commonMouseExited(handle.inputListeners, e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        commonMouseDragged(handle.inputListeners, e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        commonMouseMoved(handle.inputListeners, e);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        commonMouseWheelMoved(handle.inputListeners, e);
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

    protected static class A3AWTWindowHandle implements A3Context.Handle, A3Container.Handle {

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
            return window.canvas.handle.getContext();
        }

        @Override
        public A3Platform getPlatform() {
            return window.canvas.handle.getPlatform();
        }

        @Override
        public A3GraphicsKit getGraphicsKit() {
            return window.canvas.handle.getGraphicsKit();
        }

        @Override
        public A3BundleKit getBundleKit() {
            return window.canvas.handle.getBundleKit();
        }

        @Override
        public int getScreenWidth() {
            return window.canvas.handle.getScreenWidth();
        }

        @Override
        public int getScreenHeight() {
            return window.canvas.handle.getScreenHeight();
        }

        @Override
        public int getMinScreenWidth() {
            return window.canvas.handle.getMinScreenWidth();
        }

        @Override
        public int getMinScreenHeight() {
            return window.canvas.handle.getMinScreenHeight();
        }

        @Override
        public int getMaxScreenWidth() {
            return window.canvas.handle.getMaxScreenWidth();
        }

        @Override
        public int getMaxScreenHeight() {
            return window.canvas.handle.getMaxScreenHeight();
        }

        @Override
        public int getPPI() {
            return window.canvas.handle.getPPI();
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

        public A3AWTWindowHandle(A3AWTWindow window) {
            this.window = window;
        }

        protected final List<A3ContainerListener> containerListeners = new CopyOnWriteArrayList<>();
        protected final List<A3InputListener> inputListeners = new CopyOnWriteArrayList<>();

        @Override
        public A3Graphics getGraphics() {
            return window.canvas.handle.getGraphics();
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
            return window.canvas.handle.getBackgroundColor();
        }

        @Override
        public void setBackgroundColor(final int color) {
            window.canvas.handle.setBackgroundColor(color);
        }

        @Override
        public long elapsed() {
            return window.canvas.handle.elapsed();
        }

        @Override
        public void update() {
            window.checkDisposed("Can't call update() on a disposed A3Container");
            window.canvas.handle.update();
        }

        @Override
        public A3Image updateAndSnapshot() {
            return window.canvas.handle.updateAndSnapshot();
        }

        @Override
        public List<A3ContextListener> getContextListeners() {
            return window.canvas.handle.getContextListeners();
        }

        @Override
        public void addContextListener(A3ContextListener listener) {
            window.canvas.handle.addContextListener(listener);
        }

        @Override
        public List<A3InputListener> getContextInputListeners() {
            return window.canvas.handle.inputListeners;
        }

        @Override
        public void addContextInputListener(A3InputListener listener) {
            window.canvas.handle.inputListeners.add(listener);
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
            window.canvas.handle.paint(graphics, snapshot);
        }

        @Override
        public A3Preferences getPreferences(final String name) {
            return window.canvas.handle.getPreferences(name);
        }

        @Override
        public boolean deletePreferences(final String name) {
            return window.canvas.handle.deletePreferences(name);
        }

        @Override
        public A3Assets getAssets() {
            return window.canvas.handle.getAssets();
        }

        @Override
        public File getCacheDir() {
            return window.canvas.handle.getCacheDir();
        }

        @Override
        public File getConfigDir() {
            return window.canvas.handle.getConfigDir();
        }

        @Override
        public File getFilesDir(final String type) {
            return window.canvas.handle.getFilesDir(type);
        }

        @Override
        public File getHomeDir() {
            return window.canvas.handle.getHomeDir();
        }

        @Override
        public File getTmpDir() {
            return window.canvas.handle.getTmpDir();
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
            return window.canvas.handle.getClipboard();
        }

        @Override
        public A3Clipboard getSelection() {
            return window.canvas.handle.getSelection();
        }

        @Override
        public A3Clipboard createClipboard(final String name) {
            return window.canvas.handle.createClipboard(name);
        }

        @Override
        public A3Cursor getCursor() {
            return window.canvas.handle.getCursor();
        }

        @Override
        public void setCursor(final A3Cursor cursor) {
            window.canvas.handle.setCursor(cursor);
        }

    }

    protected final A3AWTWindowHandle handle;

    @Override
    public A3Context.Handle getContextHandle() {
        return canvas.handle;
    }

    @Override
    public A3Container.Handle getContainerHandle() {
        return handle;
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
        handle = new A3AWTWindowHandle(this);
        setMinimumSize(new Dimension(handle.getMinScreenWidth(), handle.getMinScreenHeight()));
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
        handle = new A3AWTWindowHandle(this);
        setMinimumSize(new Dimension(handle.getMinScreenWidth(), handle.getMinScreenHeight()));
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
        handle = new A3AWTWindowHandle(this);
        setMinimumSize(new Dimension(handle.getMinScreenWidth(), handle.getMinScreenHeight()));
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
        for (A3ContainerListener listener : handle.containerListeners) {
            listener.containerResized(getWidth(), getHeight());
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        for (A3ContainerListener listener : handle.containerListeners) {
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
        for (A3ContainerListener listener : handle.containerListeners) {
            listener.containerCreated();
        }
        for (A3ContainerListener listener : handle.containerListeners) {
            listener.containerStarted();
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        boolean close = true;
        for (A3ContainerListener listener : handle.containerListeners) {
            close = close && listener.containerCloseRequested();
        }
        if (close) dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        for (A3ContainerListener listener : handle.containerListeners) {
            listener.containerDisposed();
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {
        for (A3ContainerListener listener : handle.containerListeners) {
            listener.containerStopped();
        }
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        for (A3ContainerListener listener : handle.containerListeners) {
            listener.containerStarted();
        }
    }

    @Override
    public void windowActivated(WindowEvent e) {
        for (A3ContainerListener listener : handle.containerListeners) {
            listener.containerResumed();
        }
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        for (A3ContainerListener listener : handle.containerListeners) {
            listener.containerPaused();
        }
    }

    @Override
    public void windowGainedFocus(WindowEvent e) {
        for (A3ContainerListener listener : handle.containerListeners) {
            listener.containerFocusGained();
        }
    }

    @Override
    public void windowLostFocus(WindowEvent e) {
        for (A3ContainerListener listener : handle.containerListeners) {
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
