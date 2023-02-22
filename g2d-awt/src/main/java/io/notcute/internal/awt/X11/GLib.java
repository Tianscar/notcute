package io.notcute.internal.awt.X11;

import jnr.ffi.annotations.In;
import jnr.ffi.byref.IntByReference;

public interface GLib extends GObject {

    void
    g_free (
            @In long mem
    );

    void
    g_slist_free (
            @In GSList list
    );

    String
    g_variant_get_string (
            @In long value,
            @In IntByReference length
    );

}
