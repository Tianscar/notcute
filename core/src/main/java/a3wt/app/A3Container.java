package a3wt.app;

import a3wt.graphics.A3Image;
import a3wt.input.A3ContainerListener;
import a3wt.input.A3InputListener;

import java.util.List;

public interface A3Container extends A3Context {

    interface Handle extends A3Context.Handle {
        A3Container getContainer();

        void setIconImages(final List<A3Image> images);
        List<A3Image> getIconImages();
        List<A3ContainerListener> getContainerListeners();
        void addContainerListener(final A3ContainerListener listener);

        List<A3InputListener> getContainerInputListeners();
        void addContainerInputListener(final A3InputListener listener);

        void setFullscreen(final boolean fullscreen);
        boolean isFullscreen();
    }

    Handle getContainerHandle();

}
