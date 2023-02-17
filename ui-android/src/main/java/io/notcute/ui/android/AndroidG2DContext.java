package io.notcute.ui.android;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.*;
import io.notcute.app.Clipboard;
import io.notcute.app.android.AndroidClipboard;
import io.notcute.app.android.AndroidContext;
import io.notcute.audio.AudioPlayer;
import io.notcute.audio.android.AndroidAudioPlayer;
import io.notcute.context.Context;
import io.notcute.context.Identifier;
import io.notcute.context.Initializer;
import io.notcute.g2d.Graphics;
import io.notcute.g2d.GraphicsKit;
import io.notcute.g2d.Image;
import io.notcute.g2d.android.AndroidGraphics;
import io.notcute.g2d.android.AndroidGraphicsKit;
import io.notcute.g2d.android.AndroidImage;
import io.notcute.input.Input;
import io.notcute.ui.Cursor;
import io.notcute.ui.G2DContext;
import io.notcute.ui.UIKit;
import io.notcute.g2d.Color;
import io.notcute.util.collections.CollectionUtils;
import io.notcute.util.signalslot.*;

import java.io.File;
import java.net.URI;
import java.util.Objects;
import java.util.ServiceLoader;

public class AndroidG2DContext extends SurfaceView implements G2DContext, SurfaceHolder.Callback,
        View.OnLayoutChangeListener, View.OnTouchListener, View.OnHoverListener, View.OnKeyListener, View.OnGenericMotionListener {

    static {
        ServiceLoader<Initializer> serviceLoader = ServiceLoader.load(Initializer.class, Initializer.class.getClassLoader());
        for (Initializer initializer : serviceLoader) {
            initializer.initialize();
        }
    }

    @Override
    public boolean onGenericMotion(View v, MotionEvent event) {
        if ((event.getSource() & InputDevice.SOURCE_MOUSE) == 0) return false;
        else if (event.getAction() == MotionEvent.ACTION_SCROLL) {
            holder.onMouseWheelScroll.emit(this, event.getAxisValue(MotionEvent.AXIS_VSCROLL), Input.ScrollType.UNIT);
            return true;
        }
        else return false;
    }

    @Override
    public boolean onHover(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_HOVER_ENTER:
                holder.onMouseEnter.emit(this, event.getX(), event.getY());
                return true;
            case MotionEvent.ACTION_HOVER_MOVE:
                holder.onMouseMove.emit(this, event.getX(), event.getY());
                return true;
            case MotionEvent.ACTION_HOVER_EXIT:
                holder.onMouseExit.emit(this, event.getX(), event.getY());
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        switch (event.getAction()) {
            case KeyEvent.ACTION_DOWN:
                holder.onKeyDown.emit(this, keyCode, Input.KeyLocation.STANDARD);
                return true;
            case KeyEvent.ACTION_UP:
                holder.onKeyUp.emit(this, keyCode, Input.KeyLocation.STANDARD);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (left != oldLeft || top != oldTop) {
            holder.onMove.emit(this, left, top);
        }
        int width = right - left;
        int height = bottom - top;
        int oldWidth = oldRight - oldLeft;
        int oldHeight = oldBottom - oldTop;
        if (width != oldWidth || height != oldHeight) {
            holder.onResize.emit(this, width, height);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int pointerIndex = event.getPointerCount() - 1;
        float x = event.getX(pointerIndex);
        float y = event.getY(pointerIndex);
        int buttonState = event.getButtonState();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_BUTTON_PRESS:
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                holder.onPointerDown.emit(this, x, y, pointerIndex, Util.toNotcuteButton(buttonState));
                return true;
            case MotionEvent.ACTION_MOVE:
                holder.onPointerDrag.emit(this, x, y, pointerIndex);
                return true;
            case MotionEvent.ACTION_BUTTON_RELEASE:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                holder.onPointerUp.emit(this, x, y, pointerIndex, Util.toNotcuteButton(buttonState));
                return true;
            default:
                return false;
        }
    }

    protected static class Holder implements G2DContext.Holder {

        private volatile int backgroundColor = Color.WHITE;
        private final AndroidG2DContext context;
        public Holder(AndroidG2DContext context) {
            this.context = Objects.requireNonNull(context);
            CollectionUtils.putIfAbsent(AndroidContext.PRODUCER, new Identifier("notcute", "graphicsKit"), this::getGraphicsKit);
            CollectionUtils.putIfAbsent(AndroidContext.PRODUCER, new Identifier("notcute", "uiKit"), this::getUIKit);
            CollectionUtils.putIfAbsent(AndroidContext.PRODUCER, new Identifier("notcute", "audioPlayer"), this::getAudioPlayer);
            cursor = AndroidCursor.getDefaultCursor(context.getContext());
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
            return backgroundColor;
        }

        @Override
        public void setBackgroundColor(int color) {
            backgroundColor = color;
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
        public int getScreenWidth() {
            return context.getResources().getDisplayMetrics().widthPixels;
        }

        @Override
        public int getScreenHeight() {
            return context.getResources().getDisplayMetrics().heightPixels;
        }

        @Override
        public int getDPI() {
            return context.getResources().getDisplayMetrics().densityDpi;
        }

        @Override
        public float getDensity() {
            return context.getResources().getDisplayMetrics().density;
        }

        @Override
        public float getScaledDensity() {
            return context.getResources().getDisplayMetrics().scaledDensity;
        }

        @Override
        public Clipboard getClipboard() {
            return AndroidClipboard.getSystemClipboard((ClipboardManager) context.getContext().getSystemService(android.content.Context.CLIPBOARD_SERVICE));
        }

        @Override
        public Clipboard getSelection() {
            return AndroidClipboard.SELECTION;
        }

        @Override
        public Clipboard createClipboard(String name) {
            return new AndroidClipboard(name);
        }

        private volatile AndroidCursor cursor;
        @Override
        public void setCursor(Cursor cursor) {
            this.cursor = cursor == null ? AndroidCursor.getDefaultCursor(context.getContext()) : (AndroidCursor) cursor;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                context.setPointerIcon((PointerIcon) this.cursor.getPointerIcon());
            }
        }

        @Override
        public Cursor getCursor() {
            return cursor;
        }

        @Override
        public boolean open(URI uri) {
            return Util.open(context.getContext(), Uri.parse(uri.toString()));
        }

        @Override
        public boolean open(File file) {
            return Util.open(context.getContext(), Uri.parse(file.getAbsolutePath()));
        }

        private static volatile AndroidGraphicsKit graphicsKit = null;
        @Override
        public GraphicsKit getGraphicsKit() {
            if (graphicsKit == null) graphicsKit = new AndroidGraphicsKit();
            return graphicsKit;
        }

        @SuppressLint("StaticFieldLeak")
        static volatile AndroidUIKit uiKit = null;
        @Override
        public UIKit getUIKit() {
            if (uiKit == null) uiKit = new AndroidUIKit(context.getContext());
            return uiKit;
        }

        private static volatile AndroidAudioPlayer audioPlayer = null;
        @Override
        public AudioPlayer getAudioPlayer() {
            if (audioPlayer == null) audioPlayer = new AndroidAudioPlayer();
            return audioPlayer;
        }

    }

    public AndroidG2DContext(android.content.Context context) {
        this(context, null);
    }
    public AndroidG2DContext(android.content.Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private final AndroidContext androidContext;
    private final Holder holder;
    private final SurfaceHolder surfaceHolder;
    private volatile boolean disposed = false;
    public AndroidG2DContext(android.content.Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        androidContext = new AndroidContext(context);
        holder = new Holder(this);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        addOnLayoutChangeListener(this);
        setOnTouchListener(this);
        setOnHoverListener(this);
        setOnGenericMotionListener(this);
        setOnKeyListener(this);
    }

    private void renderOffscreen(Canvas canvas, boolean snapshot) {
        canvas.drawColor(holder.backgroundColor);
        holder.onPaint.emit(this, new AndroidGraphics(canvas, getWidth(), getHeight()), snapshot);
    }

    private volatile boolean requestSnapshot = false;
    private void requestSnapshot() {
        requestSnapshot = true;
    }
    public void requestUpdate() {
        post(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap;
                if (requestSnapshot) {
                    requestSnapshot = false;
                    bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                    renderOffscreen(new Canvas(bitmap), true);
                    holder.onSnapshot.emit(AndroidG2DContext.this, new AndroidImage(bitmap));
                }
                else bitmap = null;
                Canvas canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    if (bitmap != null) canvas.drawBitmap(bitmap, 0, 0, null);
                    else renderOffscreen(canvas, false);
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        });
    }

    private volatile boolean surfaceFirstCreated = true;
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!surfaceFirstCreated) return;
        surfaceFirstCreated = false;
        this.holder.onCreate.emit(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public void dispose() {
        if (isDisposed()) return;
        disposed = true;
        Holder.uiKit = null;
    }

    @Override
    public Context.Holder getContextHolder() {
        return androidContext.getContextHolder();
    }

    @Override
    public Holder getG2DContextHolder() {
        return holder;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dispose();
    }

}
