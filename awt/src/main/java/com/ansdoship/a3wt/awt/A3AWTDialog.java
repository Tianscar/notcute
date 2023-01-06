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
import java.awt.Dialog;
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
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;
import static com.ansdoship.a3wt.awt.A3AWTUtils.A3Images2BufferedImages;
import static com.ansdoship.a3wt.awt.A3AWTUtils.awtImages2A3Images;

public class A3AWTDialog extends Dialog implements AWTA3Container, ComponentListener, WindowListener, WindowFocusListener,
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

    protected static class A3AWTDialogHandle implements A3Context.Handle, A3Container.Handle {

        @Override
        public A3Container getContainer() {
            return dialog;
        }

        @Override
        public void setIconImages(final List<A3Image> images) {
            dialog.setIconImages(A3Images2BufferedImages(images));
        }

        @Override
        public List<A3Image> getIconImages() {
            return awtImages2A3Images(dialog.getIconImages());
        }

        @Override
        public A3Context getContext() {
            return dialog.canvas.handle.getContext();
        }

        @Override
        public A3Platform getPlatform() {
            return dialog.canvas.handle.getPlatform();
        }

        @Override
        public A3GraphicsKit getGraphicsKit() {
            return dialog.canvas.handle.getGraphicsKit();
        }

        @Override
        public A3BundleKit getBundleKit() {
            return dialog.canvas.handle.getBundleKit();
        }

        @Override
        public int getScreenWidth() {
            return dialog.canvas.handle.getScreenWidth();
        }

        @Override
        public int getScreenHeight() {
            return dialog.canvas.handle.getScreenHeight();
        }

        @Override
        public int getMinScreenWidth() {
            return dialog.canvas.handle.getMinScreenWidth();
        }

        @Override
        public int getMinScreenHeight() {
            return dialog.canvas.handle.getMinScreenHeight();
        }

        @Override
        public int getMaxScreenWidth() {
            return dialog.canvas.handle.getMaxScreenWidth();
        }

        @Override
        public int getMaxScreenHeight() {
            return dialog.canvas.handle.getMaxScreenHeight();
        }

        @Override
        public int getPPI() {
            return dialog.canvas.handle.getPPI();
        }

        @Override
        public float getDensity() {
            return A3AWTUtils.getDensity();
        }

        @Override
        public float getScaledDensity() {
            return A3AWTUtils.getScaledDensity(dialog.getPPIScale());
        }

        @Override
        public void postRunnable(Runnable runnable) {
            checkArgNotNull(runnable, "runnable");
            EventQueue.invokeLater(runnable);
        }

        protected final A3AWTDialog dialog;

        public A3AWTDialogHandle(A3AWTDialog dialog) {
            checkArgNotNull(dialog, "dialog");
            this.dialog = dialog;
        }

        protected final List<A3ContainerListener> containerListeners = new CopyOnWriteArrayList<>();
        protected final List<A3InputListener> inputListeners = new CopyOnWriteArrayList<>();

        @Override
        public A3Graphics getGraphics() {
            return dialog.canvas.handle.getGraphics();
        }

        @Override
        public int getWidth() {
            return dialog.canvas.getWidth();
        }

        @Override
        public int getHeight() {
            return dialog.canvas.getHeight();
        }

        @Override
        public int getBackgroundColor() {
            return dialog.canvas.handle.getBackgroundColor();
        }

        @Override
        public void setBackgroundColor(final int color) {
            dialog.canvas.handle.setBackgroundColor(color);
        }

        @Override
        public long elapsed() {
            return dialog.canvas.handle.elapsed();
        }

        @Override
        public void update() {
            dialog.checkDisposed("Can't call update() on a disposed A3Container");
            dialog.canvas.handle.update();
        }

        @Override
        public A3Image updateAndSnapshot() {
            return dialog.canvas.handle.updateAndSnapshot();
        }

        @Override
        public List<A3ContextListener> getContextListeners() {
            return dialog.canvas.handle.getContextListeners();
        }

        @Override
        public void addContextListener(final A3ContextListener listener) {
            dialog.canvas.handle.addContextListener(listener);
        }

        @Override
        public List<A3InputListener> getContextInputListeners() {
            return dialog.canvas.handle.inputListeners;
        }

        @Override
        public void addContextInputListener(final A3InputListener listener) {
            dialog.canvas.handle.inputListeners.add(listener);
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
            dialog.canvas.handle.paint(graphics, snapshot);
        }

        @Override
        public A3Preferences getPreferences(final String name) {
            return dialog.canvas.handle.getPreferences(name);
        }

        @Override
        public boolean deletePreferences(final String name) {
            return dialog.canvas.handle.deletePreferences(name);
        }

        @Override
        public A3Assets getAssets() {
            return dialog.canvas.handle.getAssets();
        }

        @Override
        public File getCacheDir() {
            return dialog.canvas.handle.getCacheDir();
        }

        @Override
        public File getConfigDir() {
            return dialog.canvas.handle.getConfigDir();
        }

        @Override
        public File getFilesDir(final String type) {
            return dialog.canvas.handle.getFilesDir(type);
        }

        @Override
        public File getHomeDir() {
            return dialog.canvas.handle.getHomeDir();
        }

        @Override
        public File getTmpDir() {
            return dialog.canvas.handle.getTmpDir();
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
                setFullscreenWindow(dialog);
            }
            else {
                setFullscreenWindow(null);
            }
        }

        @Override
        public boolean isFullscreen() {
            return getFullscreenWindow() == dialog;
        }

        @Override
        public A3Clipboard getClipboard() {
            return dialog.canvas.handle.getClipboard();
        }

        @Override
        public A3Clipboard getSelection() {
            return dialog.canvas.handle.getSelection();
        }

        @Override
        public A3Clipboard createClipboard(final String name) {
            return dialog.canvas.handle.createClipboard(name);
        }

        @Override
        public A3Cursor getCursor() {
            return dialog.canvas.handle.getCursor();
        }

        @Override
        public void setCursor(final A3Cursor cursor) {
            dialog.canvas.handle.setCursor(cursor);
        }

    }

    protected final A3AWTDialogHandle handle;

    @Override
    public A3Context.Handle getContextHandle() {
        return canvas.handle;
    }

    @Override
    public A3Container.Handle getContainerHandle() {
        return handle;
    }

    protected final A3AWTCanvas canvas;

    public A3AWTDialog(Frame owner) {
        this(owner, "", false);
    }

    public A3AWTDialog(Frame owner, boolean modal) {
        this(owner, "", modal);
    }

    public A3AWTDialog(Frame owner, String title) {
        this(owner, title, false);
    }

    public A3AWTDialog(Frame owner, String title, boolean modal) {
        this(owner, title, modal ? DEFAULT_MODALITY_TYPE : ModalityType.MODELESS);
    }

    public A3AWTDialog(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
        this(owner, title, modal ? DEFAULT_MODALITY_TYPE : ModalityType.MODELESS, gc);
    }

    public A3AWTDialog(Dialog owner) {
        this(owner, "", false);
    }

    public A3AWTDialog(Dialog owner, String title) {
        this(owner, title, false);
    }

    public A3AWTDialog(Dialog owner, String title, boolean modal) {
        this(owner, title, modal ? DEFAULT_MODALITY_TYPE : ModalityType.MODELESS);
    }

    public A3AWTDialog(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
        this(owner, title, modal ? DEFAULT_MODALITY_TYPE : ModalityType.MODELESS, gc);
    }

    public A3AWTDialog(Window owner) {
        this(owner, "", ModalityType.MODELESS);
    }

    public A3AWTDialog(Window owner, String title) {
        this(owner, title, ModalityType.MODELESS);
    }

    public A3AWTDialog(Window owner, ModalityType modalityType) {
        this(owner, "", modalityType);
    }

    public A3AWTDialog(Window owner, String title, ModalityType modalityType) {
        super(owner, title, modalityType);
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
        handle = new A3AWTDialogHandle(this);
        setMinimumSize(new Dimension(handle.getMinScreenWidth(), handle.getMinScreenHeight()));
    }

    public A3AWTDialog(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
        super(owner, title, modalityType, gc);
        setLocationByPlatform(true);
        canvas = new A3AWTCanvas();
        add(canvas);
        addComponentListener(this);
        addWindowListener(this);
        addWindowFocusListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        handle = new A3AWTDialogHandle(this);
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
