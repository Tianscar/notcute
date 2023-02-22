package io.notcute.internal.desktop.X11;

import jnr.ffi.Runtime;
import jnr.ffi.Struct;

public final class GSList extends Struct {

    public final Pointer data = new Pointer();
    public final Pointer next = new Pointer();

    public GSList(Runtime runtime) {
        super(runtime);
    }

}
