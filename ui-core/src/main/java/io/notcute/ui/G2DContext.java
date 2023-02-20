package io.notcute.ui;

import io.notcute.app.Clipboard;
import io.notcute.audio.AudioPlayer;
import io.notcute.context.Context;
import io.notcute.g2d.Graphics;
import io.notcute.g2d.GraphicsKit;
import io.notcute.g2d.Image;
import io.notcute.g2d.geom.Rectangle;
import io.notcute.util.signalslot.*;

import java.io.File;
import java.net.URI;

public interface G2DContext extends Context {

    interface Holder {

        G2DContext getG2DContext();

        int getWidth();
        int getHeight();
        int getBackgroundColor();
        void setBackgroundColor(int color);

        void requestUpdate();
        void requestSnapshot();

        VoidSignal2<G2DContext, Long> onUpdate();
        VoidSignal2<G2DContext, Image> onSnapshot();
        VoidSignal1<G2DContext> onCreate();
        VoidSignal1<G2DContext> onDispose();
        VoidSignal3<G2DContext, Integer, Integer> onResize();
        VoidSignal3<G2DContext, Integer, Integer> onMove();
        VoidSignal1<G2DContext> onShow();
        VoidSignal1<G2DContext> onHide();
        VoidSignal3<G2DContext, Graphics, Boolean> onPaint();
        VoidSignal1<G2DContext> onFocusGain();
        VoidSignal1<G2DContext> onFocusLost();

        VoidSignal3<G2DContext, Integer, Integer> onKeyDown();
        VoidSignal3<G2DContext, Integer, Integer> onKeyUp();
        VoidSignal2<G2DContext, Character> onKeyTyped();

        VoidSignal5<G2DContext, Float, Float, Integer, Integer> onPointerDown();
        VoidSignal5<G2DContext, Float, Float, Integer, Integer> onPointerUp();
        VoidSignal4<G2DContext, Float, Float, Integer> onPointerDrag();

        VoidSignal3<G2DContext, Float, Float> onMouseMove();
        VoidSignal3<G2DContext, Float, Float> onMouseEnter();
        VoidSignal3<G2DContext, Float, Float> onMouseExit();
        VoidSignal3<G2DContext, Float, Integer> onMouseWheelScroll();

        Rectangle getScreenBounds();
        Rectangle getScreenInsets();
        Rectangle getScreenClientArea();
        int getDPI();
        float getDensity();
        float getScaledDensity();
        default float px2dp(float px) {
            return px / getDensity();
        }
        default float dp2px(float dp) {
            return dp * getDensity();
        }
        default float px2sp(float px) {
            return px / getScaledDensity();
        }
        default float sp2px(float sp) {
            return sp * getScaledDensity();
        }

        Clipboard getClipboard();
        Clipboard getSelection();
        Clipboard createClipboard(String name);

        void setCursor(Cursor cursor);
        Cursor getCursor();

        boolean open(URI uri);
        boolean open(File file);

        GraphicsKit getGraphicsKit();
        UIKit getUIKit();
        AudioPlayer getAudioPlayer();

    }

    Holder getG2DContextHolder();

}
