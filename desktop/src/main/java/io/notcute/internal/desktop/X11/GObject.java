package io.notcute.internal.desktop.X11;

import jnr.ffi.annotations.In;

public interface GObject {

    void
    g_object_unref (
            @In long object
    );

}
