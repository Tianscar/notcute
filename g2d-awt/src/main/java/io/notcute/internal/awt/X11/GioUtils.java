package io.notcute.internal.awt.X11;

import io.notcute.g2d.awt.AWTFont;
import io.notcute.internal.awt.AWTG2DUtils;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;

import java.awt.Font;

public final class GioUtils {

    private static final long NULL = 0L;

    private GioUtils() {
        throw new UnsupportedOperationException();
    }

    public static int getBaselineFontSize() {
        Gio GIO = Gio.INSTANCE;
        if (GIO == null) return AWTG2DUtils.getAWTDefaultFont().getSize();
        long gsettings = GIO.g_settings_new("org.gnome.desktop.interface");
        if (gsettings == NULL) return AWTFont.DEFAULT_FONT.getFont().getSize();
        long gvariant = GIO.g_settings_get_default_value(gsettings, "font-name");
        if (gvariant == NULL) {
            GIO.g_object_unref(gsettings);
            return AWTG2DUtils.getAWTDefaultFont().getSize();
        }
        String fontName = GIO.g_variant_get_string(gvariant, null).replace(", ", "");
        //GIO.g_free(gvariant);
        GIO.g_object_unref(gsettings);
        return Font.decode(fontName).getSize();
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

    public static int getDefaultFontSize() {
        return Font.decode(getDefaultFontName()).getSize();
    }

    public static double getFontScaleFactor() {
        return (double) getDefaultFontSize() / getBaselineFontSize();
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
