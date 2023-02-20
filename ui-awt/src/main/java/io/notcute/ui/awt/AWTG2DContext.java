package io.notcute.ui.awt;

import io.notcute.app.Clipboard;
import io.notcute.app.awt.AWTClipboard;
import io.notcute.app.awt.AWTPlatform;
import io.notcute.audio.AudioPlayer;
import io.notcute.context.Context;
import io.notcute.context.Identifier;
import io.notcute.context.Producer;
import io.notcute.g2d.Graphics;
import io.notcute.g2d.GraphicsKit;
import io.notcute.g2d.Image;
import io.notcute.g2d.awt.AWTGraphics;
import io.notcute.g2d.awt.AWTImage;
import io.notcute.g2d.geom.Rectangle;
import io.notcute.input.Input;
import io.notcute.internal.awt.AWTG2DUtils;
import io.notcute.internal.awt.AWTUIUtils;
import io.notcute.internal.awt.Desktop;
import io.notcute.internal.awt.MouseInputListener;
import io.notcute.ui.Cursor;
import io.notcute.ui.G2DContext;
import io.notcute.ui.UIKit;
import io.notcute.util.signalslot.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.util.Objects;

public class AWTG2DContext extends Canvas implements G2DContext, ComponentListener, FocusListener, MouseInputListener,
        MouseWheelListener, KeyListener, HierarchyListener {

    private static final long serialVersionUID = 8419282102874603985L;

    @Override
    public void componentResized(ComponentEvent e) {
        holder.onResize.emit(this, e.getComponent().getWidth(), e.getComponent().getHeight());
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        holder.onMove.emit(this, e.getComponent().getX(), e.getComponent().getY());
    }

    @Override
    public void componentShown(ComponentEvent e) {
        holder.onShow.emit(this);
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        holder.onHide.emit(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        holder.onFocusGain.emit(this);
    }

    @Override
    public void focusLost(FocusEvent e) {
        holder.onFocusLost.emit(this);
    }

    private volatile boolean componentFirstVisible = true;
    @Override
    public void hierarchyChanged(final HierarchyEvent e) {
        if (!componentFirstVisible) return;
        if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) == HierarchyEvent.SHOWING_CHANGED) {
            componentFirstVisible = false;
            holder.onCreate.emit(this);
        }
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

    protected static class Holder implements G2DContext.Holder {

        private final AWTG2DContext context;
        public Holder(AWTG2DContext context) {
            this.context = Objects.requireNonNull(context);
        }

        @Override
        public G2DContext getG2DContext() {
            return context;
        }

        @Override
        public int getWidth() {
            return context.getWidth();
        }

        @Override
        public int getHeight() {
            return context.getHeight();
        }

        @Override
        public int getBackgroundColor() {
            return context.getBackground().getRGB();
        }

        @Override
        public void setBackgroundColor(int color) {
            context.setBackground(new Color(color, true));
        }

        @Override
        public void requestUpdate() {
            context.requestUpdate();
        }

        @Override
        public void requestSnapshot() {
            context.requestSnapshot();
        }

        private final VoidSignal2<G2DContext, Long> onUpdate = new VoidSignal2<>();
        @Override
        public VoidSignal2<G2DContext, Long> onUpdate() {
            return null;
        }

        private final VoidSignal2<G2DContext, Image> onSnapshot = new VoidSignal2<>();
        @Override
        public VoidSignal2<G2DContext, Image> onSnapshot() {
            return onSnapshot;
        }

        private final VoidSignal1<G2DContext> onCreate = new VoidSignal1<>();
        @Override
        public VoidSignal1<G2DContext> onCreate() {
            return onCreate;
        }

        private final VoidSignal1<G2DContext> onDispose = new VoidSignal1<>();
        @Override
        public VoidSignal1<G2DContext> onDispose() {
            return onDispose;
        }

        private final VoidSignal3<G2DContext, Integer, Integer> onResize = new VoidSignal3<>();
        @Override
        public VoidSignal3<G2DContext, Integer, Integer> onResize() {
            return onResize;
        }

        private final VoidSignal3<G2DContext, Integer, Integer> onMove = new VoidSignal3<>();
        @Override
        public VoidSignal3<G2DContext, Integer, Integer> onMove() {
            return onMove;
        }

        private final VoidSignal1<G2DContext> onShow = new VoidSignal1<>();
        @Override
        public VoidSignal1<G2DContext> onShow() {
            return onShow;
        }

        private final VoidSignal1<G2DContext> onHide = new VoidSignal1<>();
        @Override
        public VoidSignal1<G2DContext> onHide() {
            return onHide;
        }

        private final VoidSignal3<G2DContext, Graphics, Boolean> onPaint = new VoidSignal3<>();
        @Override
        public VoidSignal3<G2DContext, Graphics, Boolean> onPaint() {
            return onPaint;
        }

        private final VoidSignal1<G2DContext> onFocusGain = new VoidSignal1<>();
        @Override
        public VoidSignal1<G2DContext> onFocusGain() {
            return onFocusGain;
        }

        private final VoidSignal1<G2DContext> onFocusLost = new VoidSignal1<>();
        @Override
        public VoidSignal1<G2DContext> onFocusLost() {
            return onFocusLost;
        }

        private final VoidSignal3<G2DContext, Integer, Integer> onKeyDown = new VoidSignal3<>();
        @Override
        public VoidSignal3<G2DContext, Integer, Integer> onKeyDown() {
            return onKeyDown;
        }

        private final VoidSignal3<G2DContext, Integer, Integer> onKeyUp = new VoidSignal3<>();
        @Override
        public VoidSignal3<G2DContext, Integer, Integer> onKeyUp() {
            return onKeyUp;
        }

        private final VoidSignal2<G2DContext, Character> onKeyTyped = new VoidSignal2<>();
        @Override
        public VoidSignal2<G2DContext, Character> onKeyTyped() {
            return onKeyTyped;
        }

        private final VoidSignal5<G2DContext, Float, Float, Integer, Integer> onPointerDown = new VoidSignal5<>();
        @Override
        public VoidSignal5<G2DContext, Float, Float, Integer, Integer> onPointerDown() {
            return onPointerDown;
        }

        private final VoidSignal5<G2DContext, Float, Float, Integer, Integer> onPointerUp = new VoidSignal5<>();
        @Override
        public VoidSignal5<G2DContext, Float, Float, Integer, Integer> onPointerUp() {
            return onPointerUp;
        }

        private final VoidSignal4<G2DContext, Float, Float, Integer> onPointerDrag = new VoidSignal4<>();
        @Override
        public VoidSignal4<G2DContext, Float, Float, Integer> onPointerDrag() {
            return onPointerDrag;
        }

        private final VoidSignal3<G2DContext, Float, Float> onMouseMove = new VoidSignal3<>();
        @Override
        public VoidSignal3<G2DContext, Float, Float> onMouseMove() {
            return onMouseMove;
        }

        private final VoidSignal3<G2DContext, Float, Float> onMouseEnter = new VoidSignal3<>();
        @Override
        public VoidSignal3<G2DContext, Float, Float> onMouseEnter() {
            return onMouseEnter;
        }

        private final VoidSignal3<G2DContext, Float, Float> onMouseExit = new VoidSignal3<>();
        @Override
        public VoidSignal3<G2DContext, Float, Float> onMouseExit() {
            return onMouseExit;
        }

        private final VoidSignal3<G2DContext, Float, Integer> onMouseWheelScroll = new VoidSignal3<>();
        @Override
        public VoidSignal3<G2DContext, Float, Integer> onMouseWheelScroll() {
            return onMouseWheelScroll;
        }

        @Override
        public Rectangle getScreenBounds() {
            return AWTG2DUtils.toNotcuteRectangle(context.getGraphicsConfiguration().getBounds());
        }

        @Override
        public Rectangle getScreenInsets() {
            return AWTUIUtils.toNotcuteRectangle(context.getToolkit().getScreenInsets(context.getGraphicsConfiguration()));
        }

        @Override
        public Rectangle getScreenClientArea() {
            java.awt.Rectangle bounds = context.getGraphicsConfiguration().getBounds();
            Insets insets = context.getToolkit().getScreenInsets(context.getGraphicsConfiguration());
            return new Rectangle(
                    bounds.x + insets.left,
                    bounds.y + insets.top,
                    bounds.width - (insets.left + insets.right),
                    bounds.height - (insets.top + insets.bottom));
        }

        @Override
        public int getDPI() {
            return (int) (context.getToolkit().getScreenResolution() * AWTUIUtils.getDPIScale(context));
        }

        @Override
        public float getDensity() {
            return getDPI() / (float) AWTPlatform.BASELINE_DPI;
        }

        @Override
        public float getScaledDensity() {
            return (float) AWTPlatform.BASELINE_SPI / getDPI() * Float.parseFloat(System.getProperty("io.notcute.ui.awt.dpiscale", "1.0"));
        }

        @Override
        public Clipboard getClipboard() {
            return AWTClipboard.CLIPBOARD;
        }

        @Override
        public Clipboard getSelection() {
            return AWTClipboard.SELECTION;
        }

        @Override
        public Clipboard createClipboard(String name) {
            return new AWTClipboard(name);
        }

        private volatile AWTCursor cursor = AWTCursor.DEFAULT_CURSOR;
        @Override
        public void setCursor(Cursor cursor) {
            this.cursor = cursor == null ? AWTCursor.DEFAULT_CURSOR : (AWTCursor) cursor;
            context.setCursor(this.cursor.getCursor());
        }

        @Override
        public Cursor getCursor() {
            return cursor;
        }

        @Override
        public boolean open(URI uri) {
            if (uri.getScheme().equals("mailto")) return Desktop.mail(uri);
            else return Desktop.browse(uri);
        }

        @Override
        public boolean open(File file) {
            return Desktop.open(file);
        }

        @Override
        public GraphicsKit getGraphicsKit() {
            return Producer.GLOBAL.produce(new Identifier("notcute", "graphicsKit"), GraphicsKit.class);
        }

        @Override
        public UIKit getUIKit() {
            return Producer.GLOBAL.produce(new Identifier("notcute", "uiKit"), UIKit.class);
        }

        @Override
        public AudioPlayer getAudioPlayer() {
            return Producer.GLOBAL.produce(new Identifier("notcute", "audioPlayer"), AudioPlayer.class);
        }

    }

    private final AWTContext awtContext;
    private final Holder holder;
    private volatile boolean disposed = false;
    public AWTG2DContext() {
        awtContext = new AWTContext();
        setIgnoreRepaint(true);
        setBackground(Color.WHITE);
        enableInputMethods(false);
        addComponentListener(this);
        addFocusListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        addHierarchyListener(this);
        addKeyListener(this);
        holder = new Holder(this);
        EventQueue.invokeLater(this::repaint);
    }

    private void renderOffscreen(final java.awt.Graphics g, final boolean snapshot) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        holder.onPaint.emit(this, new AWTGraphics((Graphics2D) g, getWidth(), getHeight()), snapshot);
    }

    private volatile boolean requestSnapshot = false;
    private void requestSnapshot() {
        requestSnapshot = true;
    }
    public void requestUpdate() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                long time = System.currentTimeMillis();
                boolean snapshot = requestSnapshot;
                BufferedImage bufferedImage = null;
                if (requestSnapshot) {
                    requestSnapshot = false;
                    bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = bufferedImage.createGraphics();
                    renderOffscreen(g2d, true);
                    g2d.dispose();
                    holder.onSnapshot.emit(AWTG2DContext.this, new AWTImage(bufferedImage));
                }
                if (!isDisposed() && getBufferStrategy() == null) createBufferStrategy(2);
                BufferStrategy bufferStrategy = getBufferStrategy();
                if (bufferStrategy != null) do {
                    do {
                        java.awt.Graphics g = bufferStrategy.getDrawGraphics();
                        if (snapshot) g.drawImage(bufferedImage, 0, 0, null);
                        else renderOffscreen(g, false);
                        g.dispose();
                        bufferStrategy.show();
                        Toolkit.getDefaultToolkit().sync();
                    } while (bufferStrategy.contentsRestored());
                } while (bufferStrategy.contentsLost());
                long now = System.currentTimeMillis();
                holder.onUpdate.emit(AWTG2DContext.this, now - time);
            }
        });
    }

    @Override
    public Context.Holder getContextHolder() {
        return awtContext.getContextHolder();
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public void dispose() {
        if (isDisposed()) return;
        disposed = true;
        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy != null) bufferStrategy.dispose();
        awtContext.dispose();
    }

    @Override
    public Holder getG2DContextHolder() {
        return holder;
    }

}
