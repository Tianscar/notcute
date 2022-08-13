package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.app.A3Preferences;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.input.A3ContextListener;
import com.ansdoship.a3wt.input.A3ContainerListener;

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
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class A3AWTFrame extends Frame implements AWTA3Container, ComponentListener, WindowListener, WindowFocusListener {

    protected final A3AWTComponent component;
    protected final List<A3ContainerListener> a3ContainerListeners = new ArrayList<>();

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
    }

    public A3AWTFrame(String title, GraphicsConfiguration gc) {
        super(title, gc);
        component = new A3AWTComponent();
        add(component);
        addComponentListener(this);
        addWindowListener(this);
        addWindowFocusListener(this);
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
    public A3Graphics getA3Graphics() {
        return component.getA3Graphics();
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
    public long elapsed() {
        return component.elapsed();
    }

    @Override
    public void paint(A3Graphics graphics) {
        component.paint(graphics);
    }

    @Override
    public void setBackground(Color bgColor) {
        super.setBackground(bgColor);
        component.setBackground(bgColor);
    }

    @Override
    public int getBackgroundColor() {
        return component.getBackgroundColor();
    }

    @Override
    public void setBackgroundColor(int color) {
        component.setBackgroundColor(color);
    }

    @Override
    public void update() {
        checkDisposed("Can't call update() on a disposed A3Container");
        component.update();
    }

    @Override
    public A3Image snapshot() {
        return component.snapshot();
    }

    @Override
    public A3Image snapshotBuffer() {
        return component.snapshotBuffer();
    }

    @Override
    public List<A3ContextListener> getA3ContextListeners() {
        return component.a3ContextListeners;
    }

    @Override
    public void addA3ContextListener(A3ContextListener listener) {
        component.addA3ContextListener(listener);
    }

    @Override
    public List<A3ContainerListener> getA3ContainerListeners() {
        return a3ContainerListeners;
    }

    @Override
    public void addA3ContainerListener(A3ContainerListener listener) {
        a3ContainerListeners.add(listener);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        for (A3ContainerListener listener : a3ContainerListeners) {
            listener.containerResized(getWidth(), getHeight());
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        for (A3ContainerListener listener : a3ContainerListeners) {
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
        for (A3ContainerListener listener : a3ContainerListeners) {
            listener.containerCreated();
        }
        for (A3ContainerListener listener : a3ContainerListeners) {
            listener.containerStarted();
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        boolean close = true;
        for (A3ContainerListener listener : a3ContainerListeners) {
            close = close && listener.containerCloseRequested();
        }
        if (close) dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        for (A3ContainerListener listener : a3ContainerListeners) {
            listener.containerDisposed();
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {
        for (A3ContainerListener listener : a3ContainerListeners) {
            listener.containerStopped();
        }
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        for (A3ContainerListener listener : a3ContainerListeners) {
            listener.containerStarted();
        }
    }

    @Override
    public void windowActivated(WindowEvent e) {
        for (A3ContainerListener listener : a3ContainerListeners) {
            listener.containerResumed();
        }
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        for (A3ContainerListener listener : a3ContainerListeners) {
            listener.containerPaused();
        }
    }

    @Override
    public void windowGainedFocus(WindowEvent e) {
        for (A3ContainerListener listener : a3ContainerListeners) {
            listener.containerFocusGained();
        }
    }

    @Override
    public void windowLostFocus(WindowEvent e) {
        for (A3ContainerListener listener : a3ContainerListeners) {
            listener.containerFocusLost();
        }
    }

    @Override
    public A3Preferences getPreferences(String name) {
        return component.getPreferences(name);
    }

    @Override
    public boolean deletePreferences(String name) {
        return component.deletePreferences(name);
    }

    @Override
    public File getPreferencesFile(String name) {
        return component.getPreferencesFile(name);
    }

    @Override
    public AWTA3Assets getA3Assets() {
        return component.getA3Assets();
    }

    @Override
    public File getCacheDir() {
        return component.getCacheDir();
    }

    @Override
    public File getConfigDir() {
        return component.getConfigDir();
    }

    @Override
    public File getFilesDir(String type) {
        return component.getFilesDir(type);
    }

    @Override
    public File getHomeDir() {
        return component.getHomeDir();
    }

    @Override
    public File getTmpDir() {
        return component.getTmpDir();
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
