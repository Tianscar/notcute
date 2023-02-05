package io.notcute.ui;

import io.notcute.g2d.Image;
import io.notcute.util.signalslot.*;

public interface Container extends G2DContext {

    interface Holder {

        Container getContainer();

        void setIconImages(Image... images);
        Image[] getIconImages();
        void setTitle(CharSequence title);
        CharSequence getTitle();

        VoidSignal1<Container> onCreate();
        VoidSignal1<Container> onDispose();
        VoidSignal1<Container> onStart();
        VoidSignal1<Container> onStop();
        VoidSignal1<Container> onResume();
        VoidSignal1<Container> onPause();
        VoidSignal1<Container> onFocusGain();
        VoidSignal1<Container> onFocusLost();
        VoidSignal3<Container, Integer, Integer> onResize();
        VoidSignal3<Container, Integer, Integer> onMove();
        Signal1<Container, Boolean> onCloseRequest();

        VoidSignal3<Container, Integer, Integer> onKeyDown();
        VoidSignal3<Container, Integer, Integer> onKeyUp();
        VoidSignal2<Container, Character> onKeyTyped();

        VoidSignal5<Container, Float, Float, Integer, Integer> onPointerDown();
        VoidSignal5<Container, Float, Float, Integer, Integer> onPointerUp();
        VoidSignal4<Container, Float, Float, Integer> onPointerDrag();

        VoidSignal3<Container, Float, Float> onMouseMove();
        VoidSignal3<Container, Float, Float> onMouseEnter();
        VoidSignal3<Container, Float, Float> onMouseExit();
        VoidSignal3<Container, Float, Integer> onMouseWheelScroll();

        void setFullscreen(boolean fullscreen);
        boolean isFullscreen();

    }

    Holder getContainerHolder();

}
