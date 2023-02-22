package io.notcute.internal.awt.X11;

import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;

public final class AWTUIGtkUtils {

    private AWTUIGtkUtils() {
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
