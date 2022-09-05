package com.ansdoship.a3wt.app;

import com.ansdoship.a3wt.input.A3ContainerListener;
import com.ansdoship.a3wt.input.A3InputListener;

import java.util.List;

public interface A3Container extends A3Context {

    interface Handle extends A3Context.Handle {
        List<A3ContainerListener> getContainerListeners();
        void addContainerListener(A3ContainerListener listener);

        List<A3InputListener> getContainerInputListeners();
        void addContainerInputListener(A3InputListener listener);

        void setFullscreen(boolean fullscreen);
        boolean isFullscreen();
    }

    Handle getContainerHandle();

}
