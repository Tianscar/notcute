package io.notcute.internal.awt.X11;

import jnr.ffi.annotations.In;

public interface Gdk extends GObject {

    long
    gdk_display_get_default ();

    long
    gdk_x11_window_foreign_new_for_display (
            @In long display,
            @In long window
    );

}
