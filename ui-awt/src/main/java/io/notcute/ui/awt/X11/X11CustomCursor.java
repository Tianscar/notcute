package io.notcute.ui.awt.X11;

import jnr.ffi.Memory;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import sun.awt.AWTAccessor;
import sun.awt.CustomCursor;
import sun.awt.X11.XToolkit;

import java.awt.Image;
import java.awt.Point;

final class X11CustomCursor extends CustomCursor {

    private static final long serialVersionUID = 4990969689338375446L;

    public X11CustomCursor(Image cursor, Point hotSpot, String name) throws IndexOutOfBoundsException {
        super(cursor, hotSpot, name);
    }

    @Override
    protected void createNativeCursor(Image im, int[] pixels, int width, int height, int xHotSpot, int yHotSpot) {
        XToolkit.awtLock();
        try {
            JNRFFI.Xcursor XCURSOR = JNRFFI.Xcursor.INSTANCE;
            Pointer nativePixels = Memory.allocateDirect(Runtime.getSystemRuntime(), pixels.length * 4L);
            nativePixels.put(0, pixels, 0, pixels.length);
            JNRFFI.XcursorImage xCursorImage = XCURSOR.XcursorImageCreate(width, height);
            xCursorImage.xhot.set(xHotSpot);
            xCursorImage.yhot.set(yHotSpot);
            xCursorImage.pixels.set(nativePixels);
            long pData = XCURSOR.XcursorImageLoadCursor(XToolkit.getDisplay(), xCursorImage);
            XCURSOR.XcursorImageDestroy(xCursorImage);
            AWTAccessor.getCursorAccessor().setPData(this, pData);
        } finally {
            XToolkit.awtUnlock();
        }
    }

}
