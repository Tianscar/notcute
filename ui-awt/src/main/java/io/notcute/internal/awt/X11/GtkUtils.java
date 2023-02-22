package io.notcute.internal.awt.X11;

import io.notcute.g2d.awt.AWTFont;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;

import java.awt.Toolkit;
import java.awt.Font;
import java.lang.reflect.InvocationTargetException;

public final class GtkUtils {

    private static final long NULL = 0L;

    private GtkUtils() {
        throw new UnsupportedOperationException();
    }

    public static boolean loadGtk() {
        try {
            return (boolean) Class.forName("sun.awt.UNIXToolkit").getDeclaredMethod("loadGTK").invoke(Toolkit.getDefaultToolkit());
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException ignored) {
            return false;
        }
    }

    public static Gtk getGtk() {
        if (loadGtk()) return Gtk3.INSTANCE == null ? Gtk2.INSTANCE : Gtk3.INSTANCE;
        else return null;
    }

    public static int getGtkMajorVersion() {
        if (!loadGtk()) return 0;
        try {
            Enum<?> versionInfo = (Enum<?>) Class.forName("sun.awt.UNIXToolkit")
                    .getDeclaredMethod("getGtkVersion")
                    .invoke(null);
            return (Integer) (versionInfo.getClass().getDeclaredMethod("getNumber").invoke(versionInfo));
        }
        catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            return 0;
        }
    }

}
