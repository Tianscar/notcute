package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.app.A3Assets;
import com.ansdoship.a3wt.app.A3Preferences;
import com.ansdoship.a3wt.graphics.A3Container;
import com.ansdoship.a3wt.graphics.A3Context;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.input.A3ContextListener;
import com.ansdoship.a3wt.input.A3ContainerListener;
import com.ansdoship.a3wt.input.A3InputListener;

import java.awt.EventQueue;
import java.awt.GraphicsConfiguration;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Frame;
import java.awt.Color;
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
import java.util.List;

import static com.ansdoship.a3wt.awt.A3AWTUtils.commonMousePressed;
import static com.ansdoship.a3wt.awt.A3AWTUtils.commonMouseReleased;
import static com.ansdoship.a3wt.awt.A3AWTUtils.commonMouseDragged;
import static com.ansdoship.a3wt.awt.A3AWTUtils.commonMouseMoved;
import static com.ansdoship.a3wt.awt.A3AWTUtils.commonMouseWheelMoved;
import static com.ansdoship.a3wt.awt.A3AWTUtils.setFullscreenWindow;
import static com.ansdoship.a3wt.awt.A3AWTUtils.getFullscreenWindow;

public class A3AWTFrame extends Frame implements AWTA3Container, ComponentListener, WindowListener, WindowFocusListener,
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
    }

    @Override
    public void mouseExited(MouseEvent e) {
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
    public void keyTyped(KeyEvent e) {
        boolean result;
        char keyChar = e.getKeyChar();
        for (A3InputListener listener : handle.inputListeners) {
            result = listener.keyTyped(keyChar);
            if (result) break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        boolean result;
        int keyCode = e.getKeyCode();
        for (A3InputListener listener : handle.inputListeners) {
            result = listener.keyDown(keyCode);
            if (result) break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        boolean result;
        int keyCode = e.getKeyCode();
        for (A3InputListener listener : handle.inputListeners) {
            result = listener.keyUp(keyCode);
            if (result) break;
        }
    }

    protected static class A3AWTFrameHandle implements A3Context.Handle, A3Container.Handle {

        @Override
        public int getScreenWidth() {
            return frame.component.handle.getScreenWidth();
        }

        @Override
        public int getScreenHeight() {
            return frame.component.handle.getScreenHeight();
        }

        @Override
        public int getPPI() {
            return frame.component.handle.getPPI();
        }

        @Override
        public float getDensity() {
            return A3AWTUtils.getDensity();
        }

        @Override
        public float getScaledDensity() {
            return A3AWTUtils.getScaledDensity(frame.getPPIScale());
        }

        @Override
        public void postRunnable(Runnable runnable) {
            EventQueue.invokeLater(runnable);
        }

        protected final A3AWTFrame frame;

        public A3AWTFrameHandle(A3AWTFrame frame) {
            this.frame = frame;
        }

        protected final List<A3ContainerListener> containerListeners = new ArrayList<>();
        protected final List<A3InputListener> inputListeners = new ArrayList<>();

        @Override
        public A3Graphics getGraphics() {
            return frame.component.handle.getGraphics();
        }

        @Override
        public int getWidth() {
            return frame.component.getWidth();
        }

        @Override
        public int getHeight() {
            return frame.component.getHeight();
        }

        @Override
        public int getBackgroundColor() {
            return frame.component.handle.getBackgroundColor();
        }

        @Override
        public void setBackgroundColor(int color) {
            frame.component.handle.setBackgroundColor(color);
        }

        @Override
        public long elapsed() {
            return frame.component.handle.elapsed();
        }

        @Override
        public void update() {
            frame.checkDisposed("Can't call update() on a disposed A3Container");
            frame.component.handle.update();
        }

        @Override
        public A3Image snapshot() {
            return frame.component.handle.snapshot();
        }

        @Override
        public A3Image snapshotBuffer() {
            return frame.component.handle.snapshotBuffer();
        }

        @Override
        public List<A3ContextListener> getContextListeners() {
            return frame.component.handle.getContextListeners();
        }

        @Override
        public void addContextListener(A3ContextListener listener) {
            frame.component.handle.addContextListener(listener);
        }

        @Override
        public List<A3InputListener> getContextInputListeners() {
            return frame.component.handle.inputListeners;
        }

        @Override
        public void addContextInputListener(A3InputListener listener) {
            frame.component.handle.inputListeners.add(listener);
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
            frame.component.handle.paint(graphics);
        }

        @Override
        public A3Preferences getPreferences(String name) {
            return frame.component.handle.getPreferences(name);
        }

        @Override
        public boolean deletePreferences(String name) {
            return frame.component.handle.deletePreferences(name);
        }

        @Override
        public A3Assets getAssets() {
            return frame.component.handle.getAssets();
        }

        @Override
        public File getCacheDir() {
            return frame.component.handle.getCacheDir();
        }

        @Override
        public File getConfigDir() {
            return frame.component.handle.getConfigDir();
        }

        @Override
        public File getFilesDir(String type) {
            return frame.component.handle.getFilesDir(type);
        }

        @Override
        public File getHomeDir() {
            return frame.component.handle.getHomeDir();
        }

        @Override
        public File getTmpDir() {
            return frame.component.handle.getTmpDir();
        }

        @Override
        public List<A3ContainerListener> getContainerListeners() {
            return containerListeners;
        }

        @Override
        public void addContainerListener(A3ContainerListener listener) {
            containerListeners.add(listener);
        }

        private volatile boolean resizable;

        @Override
        public void setFullscreen(boolean fullscreen) {
            if (fullscreen) {
                resizable = frame.isResizable();
                frame.setResizable(false);
                setFullscreenWindow(frame);
            }
            else {
                setFullscreenWindow(null);
                frame.setResizable(resizable);
            }
        }

        @Override
        public boolean isFullscreen() {
            return getFullscreenWindow() == frame;
        }

    }

    protected final A3AWTFrameHandle handle;

    @Override
    public A3Context.Handle getContextHandle() {
        return component.handle;
    }

    @Override
    public A3Container.Handle getContainerHandle() {
        return handle;
    }

    protected final A3AWTComponent component;

    public A3AWTFrame() throws HeadlessException {
        this("");
    }

    public A3AWTFrame(GraphicsConfiguration gc) {
        this("", gc);
    }

    public A3AWTFrame(String title) throws HeadlessException {
        super(title);
        component = new A3AWTComponent();
        add(component);
        addComponentListener(this);
        addWindowListener(this);
        addWindowFocusListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        addKeyListener(this);
        handle = new A3AWTFrameHandle(this);
    }

    public A3AWTFrame(String title, GraphicsConfiguration gc) {
        super(title, gc);
        component = new A3AWTComponent();
        add(component);
        addComponentListener(this);
        addWindowListener(this);
        addWindowFocusListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        addKeyListener(this);
        handle = new A3AWTFrameHandle(this);
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
    public void setBackground(Color bgColor) {
        super.setBackground(bgColor);
        component.setBackground(bgColor);
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
