package io.notcute.internal.awt.X11;

import com.kenai.jffi.MemoryIO;
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
            Xcursor XCURSOR = Xcursor.INSTANCE;
            long pNativePixels = MemoryIO.getInstance().allocateMemory(pixels.length * 4L, false);
            Pointer nativePixels = Pointer.wrap(Runtime.getRuntime(XCURSOR), pNativePixels);
            nativePixels.put(0, pixels, 0, pixels.length);
            XcursorImage xCursorImage = XCURSOR.XcursorImageCreate(width, height);
            xCursorImage.xhot.set(xHotSpot);
            xCursorImage.yhot.set(yHotSpot);
            xCursorImage.pixels.set(nativePixels);
            long pData = XCURSOR.XcursorImageLoadCursor(XToolkit.getDisplay(), xCursorImage);
            MemoryIO.getInstance().freeMemory(pNativePixels);
            XCURSOR.XcursorImageDestroy(xCursorImage);
            AWTAccessor.getCursorAccessor().setPData(this, pData);
        } finally {
            XToolkit.awtUnlock();
        }
    }

}
