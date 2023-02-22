package io.notcute.internal.desktop.X11;

import jnr.ffi.annotations.In;
import jnr.ffi.annotations.Out;
import jnr.ffi.byref.PointerByReference;

public interface Xresource {

    void XrmInitialize();
    long XrmGetStringDatabase(@In long data);
    int XrmGetResource(
            @In long database,
            @In String str_name,
            @In String str_class,
            @Out PointerByReference str_type_return,
            @Out XrmValue value_return);

}
