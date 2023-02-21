package io.notcute.internal.awt.X11;

import jnr.ffi.Runtime;
import jnr.ffi.Struct;

public final class XcursorImage extends Struct {

    public final Unsigned32 version = new Unsigned32();    /* version of the image data */
    public final Unsigned32 size = new Unsigned32();       /* nominal size for matching */
    public final Unsigned32 width = new Unsigned32();      /* actual width */
    public final Unsigned32 height = new Unsigned32();     /* actual height */
    public final Unsigned32 xhot = new Unsigned32();       /* hot spot x (must be inside image) */
    public final Unsigned32 yhot = new Unsigned32();       /* hot spot y (must be inside image) */
    public final Unsigned32 delay = new Unsigned32();      /* animation delay to next frame (ms) */
    public final Pointer pixels = new Pointer();           /* pointer to pixels */

    public XcursorImage(Runtime runtime) {
        super(runtime);
    }

}
