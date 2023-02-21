package io.notcute.internal.awt.x11;

import jnr.ffi.LibraryLoader;
import jnr.ffi.Platform;
import jnr.ffi.annotations.In;

public interface Xcursor {

    Xcursor INSTANCE = (Platform.getNativePlatform().getOS() == Platform.OS.WINDOWS || Platform.getNativePlatform().getOS() == Platform.OS.DARWIN)
            ? null : LibraryLoader.create(Xcursor.class).load("Xcursor");

    XcursorImage XcursorImageCreate(@In int width, @In int height);

    void XcursorImageDestroy(@In XcursorImage image);

    long XcursorImageLoadCursor(@In long dpy, @In XcursorImage image);

    long XcursorLibraryLoadCursor(@In long dpy, @In String name);

    boolean XcursorSupportsARGB(@In long dpy);

}
