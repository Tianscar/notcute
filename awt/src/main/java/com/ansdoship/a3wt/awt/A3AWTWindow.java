package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.app.A3Platform;
import com.ansdoship.a3wt.app.A3Assets;
import com.ansdoship.a3wt.app.A3Preferences;
import com.ansdoship.a3wt.app.A3Clipboard;
import com.ansdoship.a3wt.app.A3Container;
import com.ansdoship.a3wt.app.A3Context;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.input.A3ContextListener;
import com.ansdoship.a3wt.input.A3ContainerListener;
import com.ansdoship.a3wt.input.A3InputListener;

import java.awt.EventQueue;
import java.awt.GraphicsConfiguration;
import java.awt.Graphics;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        public A3Platform getPlatform() {
            return window.component.handle.getPlatform();
        }

        @Override
        public int getScreenWidth() {
            return window.component.handle.getScreenWidth();
        }

        @Override
        public int getScreenHeight() {
            return window.component.handle.getScreenHeight();
        }

        @Override
        public int getPPI() {
            return window.component.handle.getPPI();
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

        protected final List<A3ContainerListener> containerListeners = Collections.synchronizedList(new ArrayList<>());
        protected final List<A3InputListener> inputListeners = Collections.synchronizedList(new ArrayList<>());

        @Override
        public A3Graphics getGraphics() {
            return window.component.handle.getGraphics();
        }

        @Override
        public int getWidth() {
            return window.component.getWidth();
        }

        @Override
        public int getHeight() {
            return window.component.getHeight();
        }

        @Override
        public int getBackgroundColor() {
            return window.component.handle.getBackgroundColor();
        }

        @Override
        public void setBackgroundColor(int color) {
            window.component.handle.setBackgroundColor(color);
        }

        @Override
        public long elapsed() {
            return window.component.handle.elapsed();
        }

        @Override
        public void update() {
            window.checkDisposed("Can't call update() on a disposed A3Container");
            window.component.handle.update();
        }

        @Override
        public A3Image snapshot() {
            return window.component.handle.snapshot();
        }

        @Override
        public A3Image snapshotBuffer() {
            return window.component.handle.snapshotBuffer();
        }

        @Override
        public List<A3ContextListener> getContextListeners() {
            return window.component.handle.getContextListeners();
        }

        @Override
        public void addContextListener(A3ContextListener listener) {
            window.component.handle.addContextListener(listener);
        }

        @Override
        public List<A3InputListener> getContextInputListeners() {
            return window.component.handle.inputListeners;
        }

        @Override
        public void addContextInputListener(A3InputListener listener) {
            window.component.handle.inputListeners.add(listener);
        }

        @Override
        public List<A3InputListener> getContainerInputListeners() {
            return inputListeners;
        }

        @Override
        public void addContainerInputListener(A3InputListener listener) {
            inputListeners.add(listener);
        }

        @Override
        public void paint(A3Graphics graphics) {
            window.component.handle.paint(graphics);
        }

        @Override
        public A3Preferences getPreferences(String name) {
            return window.component.handle.getPreferences(name);
        }

        @Override
        public boolean deletePreferences(String name) {
            return window.component.handle.deletePreferences(name);
        }

        @Override
        public A3Assets getAssets() {
            return window.component.handle.getAssets();
        }

        @Override
        public File getCacheDir() {
            return window.component.handle.getCacheDir();
        }

        @Override
        public File getConfigDir() {
            return window.component.handle.getConfigDir();
        }

        @Override
        public File getFilesDir(String type) {
            return window.component.handle.getFilesDir(type);
        }

        @Override
        public File getHomeDir() {
            return window.component.handle.getHomeDir();
        }

        @Override
        public File getTmpDir() {
            return window.component.handle.getTmpDir();
        }

        @Override
        public List<A3ContainerListener> getContainerListeners() {
            return containerListeners;
        }

        @Override
        public void addContainerListener(A3ContainerListener listener) {
            containerListeners.add(listener);
        }

        @Override
        public void setFullscreen(boolean fullscreen) {
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
            return window.component.handle.getClipboard();
        }

    }

    protected final A3AWTWindowHandle handle;

    @Override
    public A3Context.Handle getContextHandle() {
        return component.handle;
    }

    @Override
    public A3Container.Handle getContainerHandle() {
        return handle;
    }

    protected final A3AWTComponent component;

    public A3AWTWindow(Frame owner) {
        super(owner);
        enableInputMethods(false);
        component = new A3AWTComponent();
        add(component);
        addComponentListener(this);
        addWindowListener(this);
        addWindowFocusListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        handle = new A3AWTWindowHandle(this);
    }

    public A3AWTWindow(Window owner) {
        super(owner);
        enableInputMethods(false);
        component = new A3AWTComponent();
        add(component);
        addComponentListener(this);
        addWindowListener(this);
        addWindowFocusListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        handle = new A3AWTWindowHandle(this);
    }

    public A3AWTWindow(Window owner, GraphicsConfiguration gc) {
        super(owner, gc);
        enableInputMethods(false);
        component = new A3AWTComponent();
        add(component);
        addComponentListener(this);
        addWindowListener(this);
        addWindowFocusListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        handle = new A3AWTWindowHandle(this);
    }

    @Override
    public String getCompanyName() {
        return component.getCompanyName();
    }

    @Override
    public String getAppName() {
        return component.getAppName();
    }

    @Override
    public void setCompanyName(String companyName) {
        component.setCompanyName(companyName);
    }

    @Override
    public void setAppName(String appName) {
        component.setAppName(appName);
    }

    @Override
    public void paint(Graphics g) {
        component.paint(component.getGraphics());
    }

    @Override
    public void update(Graphics g) {
        component.paint(component.getGraphics());
    }

    @Override
    public void repaint(long tm, int x, int y, int width, int height) {
        component.repaint(tm, x, y, width, height);
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
        return component.getPreferencesFile(name);
    }

    @Override
    public void setPPIScale(float scale) {
        component.setPPIScale(scale);
    }

    @Override
    public float getPPIScale() {
        return component.getPPIScale();
    }

    @Override
    public boolean isDisposed() {
        return component.isDisposed();
    }

    @Override
    public void dispose() {
        component.dispose();
        super.dispose();
    }

}
