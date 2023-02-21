package io.notcute.internal.awt.x11;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public final class GtkUtils {

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

    public static int getGtkMajorVersion() {
        try {
            Enum<?> versionInfo = (Enum<?>) Class.forName("sun.awt.UNIXToolkit")
                    .getDeclaredMethod("getGtkVersion")
                    .invoke(null);
            return (Integer) (versionInfo.getClass().getDeclaredMethod("getNumber").invoke(versionInfo));
        }
        catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            return -1;
        }
    }

}
