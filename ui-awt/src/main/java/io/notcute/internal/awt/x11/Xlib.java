package io.notcute.internal.awt.x11;

import jnr.ffi.LibraryLoader;
import jnr.ffi.Platform;
import jnr.ffi.annotations.In;
import jnr.ffi.annotations.Out;
import jnr.ffi.byref.PointerByReference;

public interface Xlib {

    int True = 1;
    int False = 0;

    Xlib INSTANCE = (Platform.getNativePlatform().getOS() == Platform.OS.WINDOWS || Platform.getNativePlatform().getOS() == Platform.OS.DARWIN)
            ? null : LibraryLoader.create(Xlib.class).load("X11");

    long XCreateFontCursor(@In long display, @In int shape);
    void XrmInitialize();
    long XResourceManagerString(@In long display);
    long XrmGetStringDatabase(@In long data);
    int XrmGetResource(
            @In long database,
            @In String str_name,
            @In String str_class,
            @Out PointerByReference str_type_return,
            @Out XrmValue value_return);

}
