package io.notcute.internal.desktop.X11;

import jnr.ffi.LibraryLoader;
import jnr.ffi.Platform;

public final class GtkUtils {

    private GtkUtils() {
        throw new UnsupportedOperationException();
    }

    static Gtk3 initGtk3() {
        try {
            return (Platform.getNativePlatform().getOS() == Platform.OS.WINDOWS || Platform.getNativePlatform().getOS() == Platform.OS.DARWIN)
                    ? null : LibraryLoader.create(Gtk3.class).load("gtk-3");
        }
        catch (Exception e) {
            return null;
        }
    }

}
