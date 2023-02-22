package io.notcute.internal.desktop.X11;

import jnr.ffi.LibraryLoader;
import jnr.ffi.Platform;
import jnr.ffi.annotations.In;

public interface Xlib extends Xresource {

    int True = 1;
    int False = 0;

    Xlib INSTANCE = (Platform.getNativePlatform().getOS() == Platform.OS.WINDOWS || Platform.getNativePlatform().getOS() == Platform.OS.DARWIN)
            ? null : LibraryLoader.create(Xlib.class).load("X11");

    long XCreateFontCursor(@In long display, @In int shape);

    long XResourceManagerString(@In long display);

    long XOpenDisplay(@In String display_name);

}
