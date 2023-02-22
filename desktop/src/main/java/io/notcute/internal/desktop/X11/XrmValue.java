package io.notcute.internal.desktop.X11;

import jnr.ffi.Runtime;
import jnr.ffi.Struct;

public final class XrmValue extends Struct {
    public final Unsigned32 size = new Unsigned32();
    public final Pointer addr = new Pointer();

    public XrmValue(Runtime runtime) {
        super(runtime);
    }
}
