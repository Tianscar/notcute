package io.notcute.ui.android;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import io.notcute.app.FileChooser;
import io.notcute.app.android.AndroidFileChooser;
import io.notcute.context.Context;
import io.notcute.g2d.Image;
import io.notcute.g2d.android.AndroidImage;
import io.notcute.input.Input;
import io.notcute.ui.Container;
import io.notcute.ui.G2DContext;
import io.notcute.util.signalslot.*;

import java.util.Objects;

public class AndroidContainer extends Activity implements Container, View.OnLayoutChangeListener, View.OnHoverListener {

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if ((event.getSource() & InputDevice.SOURCE_MOUSE) == 0) return false;
        else if (event.getAction() == MotionEvent.ACTION_SCROLL) {
            holder.onMouseWheelScroll.emit(this, event.getAxisValue(MotionEvent.AXIS_VSCROLL), Input.ScrollType.UNIT);
            return true;
        }
        else return super.onGenericMotionEvent(event);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            holder.onKeyDown.emit(this, keyCode, Input.KeyLocation.STANDARD);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP) {
            holder.onKeyUp.emit(this, keyCode, Input.KeyLocation.STANDARD);
            return true;
        }
        return super.onKeyUp(keyCode, event);
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
    public boolean onTouchEvent(MotionEvent event) {
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
                return super.onTouchEvent(event);
        }
    }

    protected static final class Holder implements Container.Holder {

        private final AndroidContainer container;
        public Holder(AndroidContainer container) {
            this.container = Objects.requireNonNull(container);
        }

        @Override
        public Container getContainer() {
            return container;
        }

        private volatile Image[] iconImages = null;
        @Override
        public void setIconImages(Image... images) {
            this.iconImages = images;
            ActionBar actionBar = container.getActionBar();
            if (actionBar != null) {
                actionBar.setIcon(new BitmapDrawable(container.getResources(), ((AndroidImage) images[0]).getBitmap()));
            }
        }

        @Override
        public Image[] getIconImages() {
            return iconImages == null ? new Image[0] : iconImages;
        }

        @Override
        public void setTitle(CharSequence title) {
            container.setTitle(title);
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

        }

        @Override
        public boolean isFullscreen() {
            return false;
        }

        private static volatile AndroidFileChooser fileChooser = null;
        @Override
        public FileChooser getFileChooser() {
            if (fileChooser == null) fileChooser = new AndroidFileChooser();
            return fileChooser;
        }

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

    private volatile AndroidG2DContext g2DContext;
    private volatile Holder holder;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (g2DContext == null) g2DContext = new AndroidG2DContext(this);
        setContentView(g2DContext);
        getWindow().getDecorView().addOnLayoutChangeListener(this);
        getWindow().getDecorView().setOnHoverListener(this);
        if (holder == null) holder = new Holder(this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                holder.onCreate.emit(AndroidContainer.this);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        holder.onStart.emit(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        holder.onResume.emit(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        holder.onPause.emit(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        holder.onStop.emit(this);
    }

    @Override
    public void onWindowFocusChanged(final boolean hasFocus) {
        if (hasFocus) {
            holder.onFocusGain.emit(this);
            g2DContext.requestFocus();
        }
        else {
            holder.onFocusLost.emit(this);
        }
    }

    @Override
    public boolean isDisposed() {
        return g2DContext.isDisposed();
    }

    @Override
    public void dispose() {
        if (isDisposed()) return;
        g2DContext.dispose();
    }

    @Override
    protected void onDestroy() {
        if (isFinishing()) {
            dispose();
            super.onDestroy();
            holder.onDispose.emit(this);
            holder = null;
        }
        else {
            super.onDestroy();
        }
    }

    @Override
    public void finish() {
        if (holder.onCloseRequest.emit(this) == Boolean.TRUE) super.finish();
    }


}
