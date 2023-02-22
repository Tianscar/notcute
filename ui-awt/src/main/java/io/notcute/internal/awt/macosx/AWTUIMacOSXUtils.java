package io.notcute.internal.awt.macosx;

import java.awt.GraphicsEnvironment;
import java.lang.reflect.InvocationTargetException;

public final class AWTUIMacOSXUtils {

    private AWTUIMacOSXUtils() {
        throw new UnsupportedOperationException();
    }

    public static int getScaleFactor() {
        try {
            Object device = Class.forName("sun.awt.CGraphicsDevice").cast(GraphicsEnvironment
                    .getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice());
            return (Integer) device.getClass().getDeclaredMethod("getScaleFactor").invoke(device);
        }
        catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return 1;
        }
    }

}
