package a3wt.android;

import a3wt.app.*;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import a3wt.audio.A3AudioKit;
import a3wt.bundle.A3BundleKit;
import a3wt.graphics.A3Cursor;
import a3wt.graphics.A3Graphics;
import a3wt.graphics.A3GraphicsKit;
import a3wt.graphics.A3Image;
import a3wt.input.A3ContainerListener;
import a3wt.input.A3ContextListener;
import a3wt.input.A3InputListener;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.ArrayList;

import static a3wt.util.A3Preconditions.checkArgNotNull;

public class A3AndroidPopupWindow extends PopupWindow implements AndroidA3Container,
        View.OnLayoutChangeListener, PopupWindow.OnDismissListener, View.OnFocusChangeListener {

    protected static class A3AndroidPopupWindowHolder implements A3Context.Holder, A3Container.Holder {

        @Override
        public A3Context getContext() {
            return popupWindow.surfaceView.holder.getContext();
        }

        @Override
        public A3Platform getPlatform() {
            return popupWindow.surfaceView.holder.getPlatform();
        }

        @Override
        public A3Logger getLogger() {
            return popupWindow.surfaceView.holder.getLogger();
        }

        @Override
        public A3Factory getFactory() {
            return popupWindow.surfaceView.holder.getFactory();
        }

        @Override
        public A3I18NText getI18NText() {
            return popupWindow.surfaceView.holder.getI18NText();
        }

        @Override
        public A3GraphicsKit getGraphicsKit() {
            return popupWindow.surfaceView.holder.getGraphicsKit();
        }

        @Override
        public A3BundleKit getBundleKit() {
            return popupWindow.surfaceView.holder.getBundleKit();
        }

        @Override
        public A3AudioKit getAudioKit() {
            return popupWindow.surfaceView.holder.getAudioKit();
        }

        @Override
        public int getScreenWidth() {
            return popupWindow.surfaceView.holder.getScreenWidth();
        }

        @Override
        public int getScreenHeight() {
            return popupWindow.surfaceView.holder.getScreenHeight();
        }

        @Override
        public int getMinScreenWidth() {
            return popupWindow.surfaceView.holder.getMinScreenWidth();
        }

        @Override
        public int getMinScreenHeight() {
            return popupWindow.surfaceView.holder.getMinScreenHeight();
        }

        @Override
        public int getMaxScreenWidth() {
            return popupWindow.surfaceView.holder.getMaxScreenWidth();
        }

        @Override
        public int getMaxScreenHeight() {
            return popupWindow.surfaceView.holder.getMaxScreenHeight();
        }

        @Override
        public int getPPI() {
            return popupWindow.surfaceView.holder.getPPI();
        }

        @Override
        public float getDensity() {
            return popupWindow.surfaceView.holder.getDensity();
        }

        @Override
        public float getScaledDensity() {
            return popupWindow.surfaceView.holder.getScaledDensity();
        }

        @Override
        public void postRunnable(final Runnable runnable) {
            popupWindow.surfaceView.post(runnable);
        }

        protected final A3AndroidPopupWindow popupWindow;

        public A3AndroidPopupWindowHolder(final A3AndroidPopupWindow popupWindow) {
            checkArgNotNull(popupWindow, "popupWindow");
            this.popupWindow = popupWindow;
        }

        protected final List<A3ContainerListener> containerListeners = new ArrayList<>();
        protected final List<A3InputListener> inputListeners = new ArrayList<>();

        @Override
        public A3Graphics getGraphics() {
            return popupWindow.surfaceView.holder.getGraphics();
        }

        @Override
        public int getWidth() {
            return popupWindow.getWidth();
        }

        @Override
        public int getHeight() {
            return popupWindow.getHeight();
        }

        @Override
        public int getBackgroundColor() {
            return popupWindow.surfaceView.holder.getBackgroundColor();
        }

        @Override
        public void setBackgroundColor(final int color) {
            popupWindow.surfaceView.holder.setBackgroundColor(color);
        }

        @Override
        public long elapsed() {
            return popupWindow.surfaceView.holder.elapsed();
        }

        @Override
        public void paint(final A3Graphics graphics, final boolean snapshot) {
            popupWindow.surfaceView.holder.paint(graphics, snapshot);
        }

        @Override
        public void update() {
            popupWindow.checkDisposed("Can't call update() on a disposed A3Container");
            popupWindow.surfaceView.holder.update();
        }

        @Override
        public A3Image updateAndSnapshot() {
            return popupWindow.surfaceView.holder.updateAndSnapshot();
        }

        @Override
        public List<A3ContextListener> getContextListeners() {
            return popupWindow.surfaceView.holder.getContextListeners();
        }

        @Override
        public void addContextListener(final A3ContextListener listener) {
            popupWindow.surfaceView.holder.addContextListener(listener);
        }

        @Override
        public A3Container getContainer() {
            return popupWindow;
        }

        @Override
        public void setIconImages(List<A3Image> images) {

        }

        @Override
        public List<A3Image> getIconImages() {
            return null;
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
        public List<A3InputListener> getContextInputListeners() {
            return popupWindow.surfaceView.holder.getContextInputListeners();
        }

        @Override
        public void addContextInputListener(final A3InputListener listener) {
            popupWindow.surfaceView.holder.addContextInputListener(listener);
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
        public A3Preferences getPreferences(final String name) {
            return popupWindow.surfaceView.holder.getPreferences(name);
        }

        @Override
        public boolean deletePreferences(final String name) {
            return popupWindow.surfaceView.holder.deletePreferences(name);
        }

        @Override
        public A3Assets getAssets() {
            return popupWindow.surfaceView.holder.getAssets();
        }

        @Override
        public File getCacheDir() {
            return popupWindow.surfaceView.holder.getCacheDir();
        }

        @Override
        public File getConfigDir() {
            return popupWindow.surfaceView.holder.getConfigDir();
        }

        @Override
        public File getFilesDir(final String type) {
            return popupWindow.surfaceView.holder.getFilesDir(type);
        }

        @Override
        public File getHomeDir() {
            return popupWindow.surfaceView.holder.getHomeDir();
        }

        @Override
        public File getTmpDir() {
            return popupWindow.surfaceView.holder.getTmpDir();
        }

        @Override
        public void setFullscreen(final boolean fullscreen) {
            // FIXME
        }

        @Override
        public boolean isFullscreen() {
            // FIXME
            return false;
        }

        @Override
        public A3Clipboard getClipboard() {
            return popupWindow.surfaceView.holder.getClipboard();
        }

        @Override
        public A3Clipboard getSelection() {
            return popupWindow.surfaceView.holder.getSelection();
        }

        @Override
        public A3Clipboard createClipboard(final String name) {
            return popupWindow.surfaceView.holder.createClipboard(name);
        }

        @Override
        public void setCursor(final A3Cursor cursor) {
            popupWindow.surfaceView.holder.setCursor(cursor);
        }

        @Override
        public A3Cursor getCursor() {
            return popupWindow.surfaceView.holder.getCursor();
        }

        @Override
        public boolean browse(final URI uri) {
            return popupWindow.surfaceView.holder.browse(uri);
        }

        @Override
        public boolean open(final File file) {
            return popupWindow.surfaceView.holder.open(file);
        }

    }

    protected A3AndroidPopupWindowHolder holder;

    @Override
    public A3Context.Holder getContextHolder() {
        return surfaceView.holder;
    }

    @Override
    public A3Container.Holder getContainerHolder() {
        return holder;
    }

    protected final A3AndroidSurfaceView surfaceView;
    protected final Context context;

    public A3AndroidPopupWindow(final Context context) {
        this(context, null);
    }

    public A3AndroidPopupWindow(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        setClippingEnabled(false);
        this.context = context;
        surfaceView = new A3AndroidSurfaceView(context) {
            @Override
            public boolean onTouchEvent(final MotionEvent event) {
                return A3AndroidUtils.commonOnTouchEvent(holder.inputListeners, event) || performClick() || super.onTouchEvent(event);
            }
            @Override
            public boolean onHoverEvent(final MotionEvent event) {
                return A3AndroidUtils.commonOnHoverEvent(holder.inputListeners, event) || super.onHoverEvent(event);
            }
            @Override
            public boolean onGenericMotionEvent(final MotionEvent event) {
                return A3AndroidUtils.commonOnMouseWheelMotion(holder.inputListeners, event) || super.onGenericMotionEvent(event);
            }
            @Override
            public boolean performClick() {
                return super.performClick();
            }
            @Override
            public boolean onKeyDown(final int keyCode, final KeyEvent event) {
                return A3AndroidUtils.commonOnKeyEvent(holder.inputListeners, event) || super.onKeyDown(keyCode, event);
            }
            @Override
            public boolean onKeyUp(final int keyCode, final KeyEvent event) {
                return A3AndroidUtils.commonOnKeyEvent(holder.inputListeners, event) || super.onKeyUp(keyCode, event);
            }
        };
        setContentView(surfaceView);
        surfaceView.setOnFocusChangeListener(this);
        surfaceView.addOnLayoutChangeListener(this);
        setOnDismissListener(this);
        holder = new A3AndroidPopupWindowHolder(this);
        holder.postRunnable(new Runnable() {
            @Override
            public void run() {
                for (A3ContainerListener listener : holder.containerListeners) {
                    listener.containerCreated();
                }
            }
        });
    }

    public A3AndroidPopupWindow(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public A3AndroidPopupWindow(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setFocusable(true);
        setClippingEnabled(false);
        this.context = context;
        surfaceView = new A3AndroidSurfaceView(context);
        setContentView(surfaceView);
        surfaceView.setOnFocusChangeListener(this);
        surfaceView.addOnLayoutChangeListener(this);
        setOnDismissListener(this);
        holder = new A3AndroidPopupWindowHolder(this);
        for (A3ContainerListener listener : holder.containerListeners) {
            listener.containerCreated();
        }
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void setWidth(final int width) {
        super.setWidth(width);
        for (A3ContainerListener listener : holder.containerListeners) {
            listener.containerResized(width, getHeight());
        }
    }

    @Override
    public void setHeight(final int height) {
        super.setHeight(height);
        for (A3ContainerListener listener : holder.containerListeners) {
            listener.containerResized(getWidth(), height);
        }
    }

    @Override
    public void onFocusChange(final View v, final boolean hasFocus) {
        if (hasFocus) {
            for (A3ContainerListener listener : holder.containerListeners) {
                listener.containerFocusGained();
            }
            surfaceView.requestFocus();
        }
        else {
            for (A3ContainerListener listener : holder.containerListeners) {
                listener.containerFocusLost();
            }
        }
    }

    @Override
    public boolean isDisposed() {
        return surfaceView.isDisposed();
    }

    @Override
    public void dispose() {
        if (isDisposed()) return;
        surfaceView.dispose();
    }

    @Override
    public void onDismiss() {
        dispose();
        for (A3ContainerListener listener : holder.containerListeners) {
            listener.containerDisposed();
        }
        holder = null;
    }

    @Override
    public void onLayoutChange(final View v, final int left, final int top, final int right, final int bottom,
                               final int oldLeft, final int oldTop, final int oldRight, final int oldBottom) {
        for (A3ContainerListener listener : holder.containerListeners) {
            listener.containerMoved(left, top);
        }
    }

    @Override
    public void dismiss() {
        boolean close = true;
        for (A3ContainerListener listener : holder.containerListeners) {
            close = close && listener.containerCloseRequested();
        }
        if (close) super.dismiss();
    }
    
}
