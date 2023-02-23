package io.notcute.ui.awt;

import io.notcute.app.FileChooser;
import io.notcute.context.Context;
import io.notcute.context.Identifier;
import io.notcute.context.Producer;
import io.notcute.g2d.Image;
import io.notcute.input.Input;
import io.notcute.internal.awt.AWTUIShared;
import io.notcute.internal.awt.AWTUIUtils;
import io.notcute.internal.awt.MouseInputListener;
import io.notcute.ui.Container;
import io.notcute.ui.G2DContext;
import io.notcute.util.signalslot.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class AWTFrame extends Frame implements AWTContainer, ComponentListener, WindowListener, WindowFocusListener,
        MouseInputListener, MouseWheelListener, KeyListener {

    private static final long serialVersionUID = 7151128109966848731L;

    @Override
    public void componentResized(ComponentEvent e) {
        holder.onResize.emit(this, getWidth(), getHeight());
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        holder.onMove.emit(this, getX(), getY());
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        holder.onPointerDown.emit(this, (float) e.getX(), (float) e.getY(), e.getClickCount() - 1, AWTUIUtils.toNotcuteButton(e.getButton()));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        holder.onPointerUp.emit(this, (float) e.getX(), (float) e.getY(), e.getClickCount() - 1, AWTUIUtils.toNotcuteButton(e.getButton()));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        holder.onMouseEnter.emit(this, (float) e.getX(), (float) e.getY());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        holder.onMouseExit.emit(this, (float) e.getX(), (float) e.getY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        holder.onPointerDrag.emit(this, (float) e.getX(), (float) e.getY(), 0);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        holder.onMouseMove.emit(this, (float) e.getX(), (float) e.getY());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int scrollType = AWTUIUtils.toNotcuteScrollType(e.getScrollType());
        double amount = 0;
        if (scrollType == Input.ScrollType.UNIT) {
            amount = Math.abs(e.getUnitsToScroll()) * e.getPreciseWheelRotation();
        }
        amount = -amount;
        holder.onMouseWheelScroll.emit(this, (float) amount, scrollType);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        holder.onKeyTyped.emit(this, e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        holder.onKeyDown.emit(this, e.getExtendedKeyCode(), AWTUIUtils.toNotcuteKeyLocation(e.getKeyLocation()));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        holder.onKeyUp.emit(this, e.getExtendedKeyCode(), AWTUIUtils.toNotcuteKeyLocation(e.getKeyLocation()));
    }

    @Override
    public void windowGainedFocus(WindowEvent e) {
        holder.onFocusGain.emit(this);
        g2DContext.requestFocus();
    }

    @Override
    public void windowLostFocus(WindowEvent e) {
        holder.onFocusLost.emit(this);
    }

    @Override
    public void windowOpened(WindowEvent e) {
        holder.onCreate.emit(this);
        holder.onStart.emit(this);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (holder.onCloseRequest.emit(this) == Boolean.TRUE) dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        holder.onStop.emit(this);
        holder.onDispose.emit(this);
    }

    @Override
    public void windowIconified(WindowEvent e) {
        holder.onStop.emit(this);
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        holder.onStart.emit(this);
    }

    @Override
    public void windowActivated(WindowEvent e) {
        holder.onResume.emit(this);
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        holder.onPause.emit(this);
    }

    @Override
    public Window getWindow() {
        return this;
    }

    @Override
    public Canvas getComponent() {
        return g2DContext;
    }

    protected static class Holder implements Container.Holder {

        private final AWTFrame container;
        public Holder(AWTFrame container) {
            this.container = Objects.requireNonNull(container);
        }

        @Override
        public Container getContainer() {
            return container;
        }

        @Override
        public void setIconImages(Image... images) {
            container.setIconImages(AWTUIUtils.toAWTBufferedImages(images));
        }

        @Override
        public Image[] getIconImages() {
            return AWTUIUtils.toNotcuteImages(container.getIconImages());
        }

        @Override
        public void setTitle(CharSequence title) {
            container.setTitle(title == null ? null : title.toString());
        }

        @Override
        public CharSequence getTitle() {
            return container.getTitle();
        }

        private final VoidSignal1<Container> onCreate = new VoidSignal1<>();
        @Override
        public VoidSignal1<Container> onCreate() {
            return onCreate;
        }

        private final VoidSignal1<Container> onDispose = new VoidSignal1<>();
        @Override
        public VoidSignal1<Container> onDispose() {
            return onDispose;
        }

        private final VoidSignal1<Container> onStart = new VoidSignal1<>();
        @Override
        public VoidSignal1<Container> onStart() {
            return onStart;
        }

        private final VoidSignal1<Container> onStop = new VoidSignal1<>();
        @Override
        public VoidSignal1<Container> onStop() {
            return onStop;
        }

        private final VoidSignal1<Container> onResume = new VoidSignal1<>();
        @Override
        public VoidSignal1<Container> onResume() {
            return onResume;
        }

        private final VoidSignal1<Container> onPause = new VoidSignal1<>();
        @Override
        public VoidSignal1<Container> onPause() {
            return onPause;
        }

        private final VoidSignal3<Container, Integer, Integer> onResize = new VoidSignal3<>();
        @Override
        public VoidSignal3<Container, Integer, Integer> onResize() {
            return onResize;
        }

        private final VoidSignal3<Container, Integer, Integer> onMove = new VoidSignal3<>();
        @Override
        public VoidSignal3<Container, Integer, Integer> onMove() {
            return onMove;
        }

        private final Signal1<Container, Boolean> onCloseRequest = new Signal1<>();
        {
            onCloseRequest.connect(container -> true, Connection.Type.DIRECT);
        }
        @Override
        public Signal1<Container, Boolean> onCloseRequest() {
            return onCloseRequest;
        }

        private final VoidSignal1<Container> onFocusGain = new VoidSignal1<>();
        @Override
        public VoidSignal1<Container> onFocusGain() {
            return onFocusGain;
        }

        private final VoidSignal1<Container> onFocusLost = new VoidSignal1<>();
        @Override
        public VoidSignal1<Container> onFocusLost() {
            return onFocusLost;
        }

        private final VoidSignal3<Container, Integer, Integer> onKeyDown = new VoidSignal3<>();
        @Override
        public VoidSignal3<Container, Integer, Integer> onKeyDown() {
            return onKeyDown;
        }

        private final VoidSignal3<Container, Integer, Integer> onKeyUp = new VoidSignal3<>();
        @Override
        public VoidSignal3<Container, Integer, Integer> onKeyUp() {
            return onKeyUp;
        }

        private final VoidSignal2<Container, Character> onKeyTyped = new VoidSignal2<>();
        @Override
        public VoidSignal2<Container, Character> onKeyTyped() {
            return onKeyTyped;
        }

        private final VoidSignal5<Container, Float, Float, Integer, Integer> onPointerDown = new VoidSignal5<>();
        @Override
        public VoidSignal5<Container, Float, Float, Integer, Integer> onPointerDown() {
            return onPointerDown;
        }

        private final VoidSignal5<Container, Float, Float, Integer, Integer> onPointerUp = new VoidSignal5<>();
        @Override
        public VoidSignal5<Container, Float, Float, Integer, Integer> onPointerUp() {
            return onPointerUp;
        }

        private final VoidSignal4<Container, Float, Float, Integer> onPointerDrag = new VoidSignal4<>();
        @Override
        public VoidSignal4<Container, Float, Float, Integer> onPointerDrag() {
            return onPointerDrag;
        }

        private final VoidSignal3<Container, Float, Float> onMouseMove = new VoidSignal3<>();
        @Override
        public VoidSignal3<Container, Float, Float> onMouseMove() {
            return onMouseMove;
        }

        private final VoidSignal3<Container, Float, Float> onMouseEnter = new VoidSignal3<>();
        @Override
        public VoidSignal3<Container, Float, Float> onMouseEnter() {
            return onMouseEnter;
        }

        private final VoidSignal3<Container, Float, Float> onMouseExit = new VoidSignal3<>();
        @Override
        public VoidSignal3<Container, Float, Float> onMouseExit() {
            return onMouseExit;
        }

        private final VoidSignal3<Container, Float, Integer> onMouseWheelScroll = new VoidSignal3<>();
        @Override
        public VoidSignal3<Container, Float, Integer> onMouseWheelScroll() {
            return onMouseWheelScroll;
        }

        @Override
        public void setFullscreen(boolean fullscreen) {
            if (fullscreen) {
                AWTUIShared.setFullscreenWindow(container);
            }
            else {
                AWTUIShared.setFullscreenWindow(null);
            }
        }

        @Override
        public boolean isFullscreen() {
            return AWTUIShared.getFullscreenWindow() == container;
        }

        @Override
        public FileChooser getFileChooser() {
            return Producer.GLOBAL.produce(new Identifier("notcute", "fileChooser"), FileChooser.class);
        }

    }

    private final AWTCanvas g2DContext;
    private final AWTFrame.Holder holder;

    public AWTFrame() {
        this("");
    }

    public AWTFrame(GraphicsConfiguration gc) {
        this("", gc);
    }

    public AWTFrame(String title) {
        this(title, null);
    }

    public AWTFrame(String title, GraphicsConfiguration gc) {
        super(title, gc);
        setLocationByPlatform(true);
        enableInputMethods(false);
        g2DContext = new AWTCanvas();
        add(g2DContext);
        addComponentListener(this);
        addWindowListener(this);
        addWindowFocusListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        addKeyListener(this);
        holder = new Holder(this);
        setMinimumSize(640, 480);
    }
    
    public void setMinimumSize(int width, int height) {
        setMinimumSize(new Dimension(width, height));
    }
    
    public void setMaximumSize(int width, int height) {
        setMaximumSize(new Dimension(width, height));
    }
    
    public void setPreferredSize(int width, int height) {
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public boolean isDisposed() {
        return g2DContext.isDisposed();
    }

    @Override
    public void dispose() {
        if (isDisposed()) return;
        g2DContext.dispose();
        super.dispose();
    }

    @Override
    public Context.Holder getContextHolder() {
        return g2DContext.getContextHolder();
    }

    @Override
    public Container.Holder getContainerHolder() {
        return holder;
    }

    @Override
    public G2DContext.Holder getG2DContextHolder() {
        return g2DContext.getG2DContextHolder();
    }

}
