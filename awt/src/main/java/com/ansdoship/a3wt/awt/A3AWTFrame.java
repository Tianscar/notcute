package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.app.*;
import com.ansdoship.a3wt.audio.A3AudioKit;
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
import java.awt.HeadlessException;
import java.awt.Frame;
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

    protected static class A3AWTFrameHandle implements A3Context.Handle, A3Container.Handle {

        @Override
        public A3Container getContainer() {
            return frame;
        }

        @Override
        public void setIconImages(final List<A3Image> images) {
            frame.setIconImages(A3Images2BufferedImages(images));
        }

        @Override
        public List<A3Image> getIconImages() {
            return awtImages2A3Images(frame.getIconImages());
        }

        @Override
        public A3Context getContext() {
            return frame.canvas;
        }

        @Override
        public A3Platform getPlatform() {
            return frame.canvas.handle.getPlatform();
        }

        @Override
        public A3Logger getLogger() {
            return frame.canvas.handle.getLogger();
        }

        @Override
        public A3I18NText getI18NText() {
            return frame.canvas.handle.getI18NText();
        }

        @Override
        public A3GraphicsKit getGraphicsKit() {
            return frame.canvas.handle.getGraphicsKit();
        }

        @Override
        public A3BundleKit getBundleKit() {
            return frame.canvas.handle.getBundleKit();
        }

        @Override
        public A3AudioKit getAudioKit() {
            return frame.canvas.handle.getAudioKit();
        }

        @Override
        public int getScreenWidth() {
            return frame.canvas.handle.getScreenWidth();
        }

        @Override
        public int getScreenHeight() {
            return frame.canvas.handle.getScreenHeight();
        }

        @Override
        public int getMinScreenWidth() {
            return frame.canvas.handle.getMinScreenWidth();
        }

        @Override
        public int getMinScreenHeight() {
            return frame.canvas.handle.getMinScreenHeight();
        }

        @Override
        public int getMaxScreenWidth() {
            return frame.canvas.handle.getMaxScreenWidth();
        }

        @Override
        public int getMaxScreenHeight() {
            return frame.canvas.handle.getMaxScreenHeight();
        }

        @Override
        public int getPPI() {
            return frame.canvas.handle.getPPI();
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

        protected final List<A3ContainerListener> containerListeners = new CopyOnWriteArrayList<>();
        protected final List<A3InputListener> inputListeners = new CopyOnWriteArrayList<>();

        @Override
        public A3Graphics getGraphics() {
            return frame.canvas.handle.getGraphics();
        }

        @Override
        public int getWidth() {
            return frame.canvas.getWidth();
        }

        @Override
        public int getHeight() {
            return frame.canvas.getHeight();
        }

        @Override
        public int getBackgroundColor() {
            return frame.canvas.handle.getBackgroundColor();
        }

        @Override
        public void setBackgroundColor(final int color) {
            frame.canvas.handle.setBackgroundColor(color);
        }

        @Override
        public long elapsed() {
            return frame.canvas.handle.elapsed();
        }

        @Override
        public void update() {
            frame.checkDisposed("Can't call update() on a disposed A3Container");
            frame.canvas.handle.update();
        }

        @Override
        public A3Image updateAndSnapshot() {
            return frame.canvas.handle.updateAndSnapshot();
        }

        @Override
        public List<A3ContextListener> getContextListeners() {
            return frame.canvas.handle.getContextListeners();
        }

        @Override
        public void addContextListener(final A3ContextListener listener) {
            frame.canvas.handle.addContextListener(listener);
        }

        @Override
        public List<A3InputListener> getContextInputListeners() {
            return frame.canvas.handle.inputListeners;
        }

        @Override
        public void addContextInputListener(final A3InputListener listener) {
            frame.canvas.handle.inputListeners.add(listener);
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
            frame.canvas.handle.paint(graphics, snapshot);
        }

        @Override
        public A3Preferences getPreferences(final String name) {
            return frame.canvas.handle.getPreferences(name);
        }

        @Override
        public boolean deletePreferences(final String name) {
            return frame.canvas.handle.deletePreferences(name);
        }

        @Override
        public A3Assets getAssets() {
            return frame.canvas.handle.getAssets();
        }

        @Override
        public File getCacheDir() {
            return frame.canvas.handle.getCacheDir();
        }

        @Override
        public File getConfigDir() {
            return frame.canvas.handle.getConfigDir();
        }

        @Override
        public File getFilesDir(final String type) {
            return frame.canvas.handle.getFilesDir(type);
        }

        @Override
        public File getHomeDir() {
            return frame.canvas.handle.getHomeDir();
        }

        @Override
        public File getTmpDir() {
            return frame.canvas.handle.getTmpDir();
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
                setFullscreenWindow(frame);
            }
            else {
                setFullscreenWindow(null);
            }
        }

        @Override
        public boolean isFullscreen() {
            return getFullscreenWindow() == frame;
        }

        @Override
        public A3Clipboard getClipboard() {
            return frame.canvas.handle.getClipboard();
        }

        @Override
        public A3Clipboard getSelection() {
            return frame.canvas.handle.getSelection();
        }

        @Override
        public A3Clipboard createClipboard(final String name) {
            return frame.canvas.handle.createClipboard(name);
        }

        @Override
        public A3Cursor getCursor() {
            return frame.canvas.handle.getCursor();
        }

        @Override
        public boolean browse(final URI uri) {
            return frame.canvas.handle.browse(uri);
        }

        @Override
        public boolean open(final File file) {
            return frame.canvas.handle.open(file);
        }

        @Override
        public void setCursor(final A3Cursor cursor) {
            frame.canvas.handle.setCursor(cursor);
        }

    }

    protected final A3AWTFrameHandle handle;

    @Override
    public A3Context.Handle getContextHandle() {
        return canvas.handle;
    }

    @Override
    public A3Container.Handle getContainerHandle() {
        return handle;
    }

    protected final A3AWTCanvas canvas;

    public A3AWTFrame() throws HeadlessException {
        this("");
    }

    public A3AWTFrame(GraphicsConfiguration gc) {
        this("", gc);
    }

    public A3AWTFrame(String title) throws HeadlessException {
        super(title);
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
        addKeyListener(this);
        handle = new A3AWTFrameHandle(this);
        setMinimumSize(new Dimension(handle.getMinScreenWidth(), handle.getMinScreenHeight()));
    }

    public A3AWTFrame(String title, GraphicsConfiguration gc) {
        super(title, gc);
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
        addKeyListener(this);
        handle = new A3AWTFrameHandle(this);
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
            listener.containerStopped();
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
        canvas.requestFocus();
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
