package io.notcute.internal.awt.X11;

import io.notcute.internal.awt.AWTG2DUtils;
import io.notcute.internal.desktop.X11.Gio;
import io.notcute.internal.desktop.X11.GioUtils;

import java.awt.Font;

public final class AWTG2DGioUtils {

    private static final long NULL = 0L;

    private AWTG2DGioUtils() {
        throw new UnsupportedOperationException();
    }

    public static int getBaselineFontSize() {
        Gio GIO = Gio.INSTANCE;
        if (GIO == null) return AWTG2DUtils.getAWTDefaultFont().getSize();
        long gsettings = GIO.g_settings_new("org.gnome.desktop.interface");
        if (gsettings == NULL) return AWTG2DUtils.getAWTDefaultFont().getSize();
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

    public static int getDefaultFontSize() {
        return Font.decode(GioUtils.getDefaultFontName()).getSize();
    }

    public static double getFontScaleFactor() {
        return (double) getDefaultFontSize() / getBaselineFontSize();
    }

}
