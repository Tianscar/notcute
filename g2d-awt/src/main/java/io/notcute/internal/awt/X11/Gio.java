package io.notcute.internal.awt.X11;

import jnr.ffi.LibraryLoader;
import jnr.ffi.Platform;
import jnr.ffi.annotations.In;

public interface Gio extends GLib {

    Gio INSTANCE = (Platform.getNativePlatform().getOS() == Platform.OS.WINDOWS || Platform.getNativePlatform().getOS() == Platform.OS.DARWIN)
            ? null : LibraryLoader.create(Gio.class).load("gio-2.0");

    long
    g_settings_new (
            @In String schema_id
    );

    long
    g_settings_get_string (
            @In long settings,
            @In String key
    );

    long
    g_settings_get_default_value (
            @In long settings,
            @In String key
    );

}
