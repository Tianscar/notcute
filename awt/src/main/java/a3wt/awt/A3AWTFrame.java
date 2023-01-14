package a3wt.awt;

import a3wt.app.*;
import a3wt.audio.A3AudioPlayer;
import a3wt.bundle.A3BundleKit;
import a3wt.graphics.A3Cursor;
import a3wt.graphics.A3Graphics;
import a3wt.graphics.A3GraphicsKit;
import a3wt.graphics.A3Image;
import a3wt.input.A3ContextListener;
import a3wt.input.A3ContainerListener;
import a3wt.input.A3InputListener;
import a3wt.util.A3Collections;

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

public class A3AWTFrame extends Frame implements AWTA3Container, ComponentListener, WindowListener, WindowFocusListener,
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

    @Override
    public void setMinimumSize(final int width, final int height) {
        setMinimumSize(new Dimension(width, height));
    }

    @Override
    public void setMaximumSize(final int width, final int height) {
        setMaximumSize(new Dimension(width, height));
    }

    @Override
    public void setPreferredSize(final int width, final int height) {
        setPreferredSize(new Dimension(width, height));
    }

    protected static class A3AWTFrameHolder implements A3Context.Holder, A3Container.Holder {

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
            return frame.canvas.holder.getPlatform();
        }

        @Override
        public A3Logger getLogger() {
            return frame.canvas.holder.getLogger();
        }

        @Override
        public A3Factory getFactory() {
            return frame.canvas.holder.getFactory();
        }

        @Override
        public A3I18NText getI18NText() {
            return frame.canvas.holder.getI18NText();
        }

        @Override
        public A3GraphicsKit getGraphicsKit() {
            return frame.canvas.holder.getGraphicsKit();
        }

        @Override
        public A3BundleKit getBundleKit() {
            return frame.canvas.holder.getBundleKit();
        }

        @Override
        public A3AudioPlayer getAudioPlayer() {
            return frame.canvas.holder.getAudioPlayer();
        }

        @Override
        public int getScreenWidth() {
            return frame.canvas.holder.getScreenWidth();
        }

        @Override
        public int getScreenHeight() {
            return frame.canvas.holder.getScreenHeight();
        }

        @Override
        public int getMinScreenWidth() {
            return frame.canvas.holder.getMinScreenWidth();
        }

        @Override
        public int getMinScreenHeight() {
            return frame.canvas.holder.getMinScreenHeight();
        }

        @Override
        public int getMaxScreenWidth() {
            return frame.canvas.holder.getMaxScreenWidth();
        }

        @Override
        public int getMaxScreenHeight() {
            return frame.canvas.holder.getMaxScreenHeight();
        }

        @Override
        public int getPPI() {
            return frame.canvas.holder.getPPI();
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

        public A3AWTFrameHolder(A3AWTFrame frame) {
            this.frame = frame;
        }

        protected final List<A3ContainerListener> containerListeners = A3Collections.checkNullList(new CopyOnWriteArrayList<>());
        protected final List<A3InputListener> inputListeners = A3Collections.checkNullList(new CopyOnWriteArrayList<>());

        @Override
        public A3Graphics getGraphics() {
            return frame.canvas.holder.getGraphics();
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
            return frame.canvas.holder.getBackgroundColor();
        }

        @Override
        public void setBackgroundColor(final int color) {
            frame.canvas.holder.setBackgroundColor(color);
        }

        @Override
        public long elapsed() {
            return frame.canvas.holder.elapsed();
        }

        @Override
        public void update() {
            frame.checkDisposed("Can't call update() on a disposed A3Container");
            frame.canvas.holder.update();
        }

        @Override
        public A3Image updateAndSnapshot() {
            return frame.canvas.holder.updateAndSnapshot();
        }

        @Override
        public List<A3ContextListener> getContextListeners() {
            return frame.canvas.holder.getContextListeners();
        }

        @Override
        public void addContextListener(final A3ContextListener listener) {
            frame.canvas.holder.addContextListener(listener);
        }

        @Override
        public List<A3InputListener> getContextInputListeners() {
            return frame.canvas.holder.inputListeners;
        }

        @Override
        public void addContextInputListener(final A3InputListener listener) {
            frame.canvas.holder.inputListeners.add(listener);
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
            frame.canvas.holder.paint(graphics, snapshot);
        }

        @Override
        public A3Preferences getPreferences(final String name) {
            return frame.canvas.holder.getPreferences(name);
        }

        @Override
        public boolean deletePreferences(final String name) {
            return frame.canvas.holder.deletePreferences(name);
        }

        @Override
        public A3Assets getAssets() {
            return frame.canvas.holder.getAssets();
        }

        @Override
        public File getCacheDir() {
            return frame.canvas.holder.getCacheDir();
        }

        @Override
        public File getConfigDir() {
            return frame.canvas.holder.getConfigDir();
        }

        @Override
        public File getFilesDir(final String type) {
            return frame.canvas.holder.getFilesDir(type);
        }

        @Override
        public File getHomeDir() {
            return frame.canvas.holder.getHomeDir();
        }

        @Override
        public File getTmpDir() {
            return frame.canvas.holder.getTmpDir();
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
            return frame.canvas.holder.getClipboard();
        }

        @Override
        public A3Clipboard getSelection() {
            return frame.canvas.holder.getSelection();
        }

        @Override
        public A3Clipboard createClipboard(final String name) {
            return frame.canvas.holder.createClipboard(name);
        }

        @Override
        public A3Cursor getCursor() {
            return frame.canvas.holder.getCursor();
        }

        @Override
        public boolean browse(final URI uri) {
            return frame.canvas.holder.browse(uri);
        }

        @Override
        public boolean open(final File file) {
            return frame.canvas.holder.open(file);
        }

        @Override
        public void setCursor(final A3Cursor cursor) {
            frame.canvas.holder.setCursor(cursor);
        }

    }

    protected final A3AWTFrameHolder holder;

    @Override
    public A3Context.Holder getContextHolder() {
        return canvas.holder;
    }

    @Override
    public A3Container.Holder getContainerHolder() {
        return holder;
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
        holder = new A3AWTFrameHolder(this);
        setMinimumSize(new Dimension(holder.getMinScreenWidth(), holder.getMinScreenHeight()));
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
        holder = new A3AWTFrameHolder(this);
        setMinimumSize(new Dimension(holder.getMinScreenWidth(), holder.getMinScreenHeight()));
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
            listener.containerStopped();
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
