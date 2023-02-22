package io.notcute.internal.desktop.X11;

import jnr.ffi.Runtime;
import jnr.ffi.byref.PointerByReference;

public final class X11Utils {

    private static final long NULL = 0L;

    // adapted from https://github.com/glfw/glfw/issues/1019#issuecomment-302772498
    public static double getXFontDPI(long display, double defaultDPI) {
        Xlib XLIB = Xlib.INSTANCE;
        long resourceString = XLIB.XResourceManagerString(display);
        long db;
        XrmValue value = new XrmValue(Runtime.getRuntime(XLIB));
        PointerByReference type = new PointerByReference();
        XLIB.XrmInitialize(); /* Need to initialize the DB before calling Xrm* functions */
        db = XLIB.XrmGetStringDatabase(resourceString);
        if (resourceString != NULL) {
            if (XLIB.XrmGetResource(db, "Xft.dpi", "String", type, value) == Xlib.True) {
                if (value.addr.get().address() != NULL) {
                    return Double.parseDouble(value.addr.get().getString(0));
                }
            }
        }
        return defaultDPI;
    }

}
