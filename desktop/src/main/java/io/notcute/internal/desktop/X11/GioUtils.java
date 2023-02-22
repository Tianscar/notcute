package io.notcute.internal.desktop.X11;

import jnr.ffi.Pointer;
import jnr.ffi.Runtime;

public final class GioUtils {

    private static final long NULL = 0L;

    private GioUtils() {
        throw new UnsupportedOperationException();
    }

    public static String getDefaultFontName() {
        Gio GIO = Gio.INSTANCE;
        if (GIO == null) return null;
        long gsettings = GIO.g_settings_new("org.gnome.desktop.interface");
        if (gsettings == NULL) return null;
        long nFontName = GIO.g_settings_get_string(gsettings, "font-name");
        String fontName = Pointer.wrap(Runtime.getRuntime(GIO), nFontName).getString(0).replace(", ", "");
        GIO.g_free(nFontName);
        GIO.g_object_unref(gsettings);
        return fontName;
    }

    public static String getDefaultMonospaceFontName() {
        Gio GIO = Gio.INSTANCE;
        if (GIO == null) return null;
        long gsettings = GIO.g_settings_new("org.gnome.desktop.interface");
        if (gsettings == NULL) return null;
        long nFontName = GIO.g_settings_get_string(gsettings, "monospace-font-name");
        String fontName = Pointer.wrap(Runtime.getRuntime(GIO), nFontName).getString(0).replace(", ", "");
        GIO.g_free(nFontName);
        GIO.g_object_unref(gsettings);
        return fontName;
    }

}
