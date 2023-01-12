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
import java.net.URI;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static a3wt.awt.A3AWTSharedState.getFullscreenWindow;
import static a3wt.awt.A3AWTSharedState.setFullscreenWindow;
import static a3wt.util.A3Preconditions.checkArgNotNull;

public class A3AWTDialog extends Dialog implements AWTA3Container, ComponentListener, WindowListener, WindowFocusListener,
        MouseInputListener, MouseWheelListener, KeyListener {

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        A3AWTUtils.commonMousePressed(holder.inputListeners, e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        A3AWTUtils.commonMouseReleased(holder.inputListeners, e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        A3AWTUtils.commonMouseEntered(holder.inputListeners, e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        A3AWTUtils.commonMouseExited(holder.inputListeners, e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        A3AWTUtils.commonMouseDragged(holder.inputListeners, e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        A3AWTUtils.commonMouseMoved(holder.inputListeners, e);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        A3AWTUtils.commonMouseWheelMoved(holder.inputListeners, e);
    }

    @Override
    public void keyTyped(final KeyEvent e) {
        A3AWTUtils.commonKeyTyped(holder.inputListeners, e);
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        A3AWTUtils.commonKeyPressed(holder.inputListeners, e);
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        A3AWTUtils.commonKeyReleased(holder.inputListeners, e);
    }

    protected static class A3AWTDialogHolder implements A3Context.Holder, A3Container.Holder {

        @Override
        public A3Container getContainer() {
            return dialog;
        }

        @Override
        public void setIconImages(final List<A3Image> images) {
            dialog.setIconImages(A3AWTUtils.A3Images2BufferedImages(images));
        }

        @Override
        public List<A3Image> getIconImages() {
            return A3AWTUtils.awtImages2A3Images(dialog.getIconImages());
        }

        @Override
        public A3Context getContext() {
            return dialog.canvas.holder.getContext();
        }

        @Override
        public A3Platform getPlatform() {
            return dialog.canvas.holder.getPlatform();
        }

        @Override
        public A3Logger getLogger() {
            return dialog.canvas.holder.getLogger();
        }

        @Override
        public A3Factory getFactory() {
            return dialog.canvas.holder.getFactory();
        }

        @Override
        public A3I18NText getI18NText() {
            return dialog.canvas.holder.getI18NText();
        }

        @Override
        public A3GraphicsKit getGraphicsKit() {
            return dialog.canvas.holder.getGraphicsKit();
        }

        @Override
        public A3BundleKit getBundleKit() {
            return dialog.canvas.holder.getBundleKit();
        }

        @Override
        public A3AudioKit getAudioKit() {
            return dialog.canvas.holder.getAudioKit();
        }

        @Override
        public int getScreenWidth() {
            return dialog.canvas.holder.getScreenWidth();
        }

        @Override
        public int getScreenHeight() {
            return dialog.canvas.holder.getScreenHeight();
        }

        @Override
        public int getMinScreenWidth() {
            return dialog.canvas.holder.getMinScreenWidth();
        }

        @Override
        public int getMinScreenHeight() {
            return dialog.canvas.holder.getMinScreenHeight();
        }

        @Override
        public int getMaxScreenWidth() {
            return dialog.canvas.holder.getMaxScreenWidth();
        }

        @Override
        public int getMaxScreenHeight() {
            return dialog.canvas.holder.getMaxScreenHeight();
        }

        @Override
        public int getPPI() {
            return dialog.canvas.holder.getPPI();
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

        public A3AWTDialogHolder(A3AWTDialog dialog) {
            checkArgNotNull(dialog, "dialog");
            this.dialog = dialog;
        }

        protected final List<A3ContainerListener> containerListeners = new CopyOnWriteArrayList<>();
        protected final List<A3InputListener> inputListeners = new CopyOnWriteArrayList<>();

        @Override
        public A3Graphics getGraphics() {
            return dialog.canvas.holder.getGraphics();
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
            return dialog.canvas.holder.getBackgroundColor();
        }

        @Override
        public void setBackgroundColor(final int color) {
            dialog.canvas.holder.setBackgroundColor(color);
        }

        @Override
        public long elapsed() {
            return dialog.canvas.holder.elapsed();
        }

        @Override
        public void update() {
            dialog.checkDisposed("Can't call update() on a disposed A3Container");
            dialog.canvas.holder.update();
        }

        @Override
        public A3Image updateAndSnapshot() {
            return dialog.canvas.holder.updateAndSnapshot();
        }

        @Override
        public List<A3ContextListener> getContextListeners() {
            return dialog.canvas.holder.getContextListeners();
        }

        @Override
        public void addContextListener(final A3ContextListener listener) {
            dialog.canvas.holder.addContextListener(listener);
        }

        @Override
        public List<A3InputListener> getContextInputListeners() {
            return dialog.canvas.holder.inputListeners;
        }

        @Override
        public void addContextInputListener(final A3InputListener listener) {
            dialog.canvas.holder.inputListeners.add(listener);
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
            dialog.canvas.holder.paint(graphics, snapshot);
        }

        @Override
        public A3Preferences getPreferences(final String name) {
            return dialog.canvas.holder.getPreferences(name);
        }

        @Override
        public boolean deletePreferences(final String name) {
            return dialog.canvas.holder.deletePreferences(name);
        }

        @Override
        public A3Assets getAssets() {
            return dialog.canvas.holder.getAssets();
        }

        @Override
        public File getCacheDir() {
            return dialog.canvas.holder.getCacheDir();
        }

        @Override
        public File getConfigDir() {
            return dialog.canvas.holder.getConfigDir();
        }

        @Override
        public File getFilesDir(final String type) {
            return dialog.canvas.holder.getFilesDir(type);
        }

        @Override
        public File getHomeDir() {
            return dialog.canvas.holder.getHomeDir();
        }

        @Override
        public File getTmpDir() {
            return dialog.canvas.holder.getTmpDir();
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
            return dialog.canvas.holder.getClipboard();
        }

        @Override
        public A3Clipboard getSelection() {
            return dialog.canvas.holder.getSelection();
        }

        @Override
        public A3Clipboard createClipboard(final String name) {
            return dialog.canvas.holder.createClipboard(name);
        }

        @Override
        public A3Cursor getCursor() {
            return dialog.canvas.holder.getCursor();
        }

        @Override
        public boolean browse(final URI uri) {
            return dialog.canvas.holder.browse(uri);
        }

        @Override
        public boolean open(final File file) {
            return dialog.canvas.holder.open(file);
        }

        @Override
        public void setCursor(final A3Cursor cursor) {
            dialog.canvas.holder.setCursor(cursor);
        }

    }

    protected final A3AWTDialogHolder holder;

    @Override
    public A3Context.Holder getContextHolder() {
        return canvas.holder;
    }

    @Override
    public A3Container.Holder getContainerHolder() {
        return holder;
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
        holder = new A3AWTDialogHolder(this);
        setMinimumSize(new Dimension(holder.getMinScreenWidth(), holder.getMinScreenHeight()));
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
        holder = new A3AWTDialogHolder(this);
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
