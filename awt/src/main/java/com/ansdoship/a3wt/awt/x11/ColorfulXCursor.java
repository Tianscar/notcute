package com.ansdoship.a3wt.awt.x11;

import com.ansdoship.a3wt.util.A3Unsafe;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import sun.awt.AWTAccessor;
import sun.awt.CustomCursor;
import sun.misc.Unsafe;

import java.awt.Point;
import java.awt.Image;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ColorfulXCursor extends CustomCursor {

    public ColorfulXCursor(final Image cursor, final Point hotSpot, final String name) throws IndexOutOfBoundsException {
        super(cursor, hotSpot, name);
    }

    @Override
    protected void createNativeCursor(final Image im, final int[] pixels, final int width, final int height, final int xHotSpot, final int yHotSpot) {
        try {
            final Class<?> xToolkit = Class.forName("sun.awt.X11.XToolkit");
            final Method awtLock = xToolkit.getMethod("awtLock");
            awtLock.invoke(null);
            //XToolkit.awtLock();
            try {
                final Unsafe UNSAFE = A3Unsafe.getUnsafe();
                final long pNativePixels = UNSAFE.allocateMemory(pixels.length * 4L);
                final Pointer nativePixels = Pointer.wrap(Runtime.getSystemRuntime(), pNativePixels);
                nativePixels.put(0, pixels, 0, pixels.length);
                final Xcursor XCURSOR = Xcursor.getXcursor();
                final Xcursor.XCursorImage xCursorImage = XCURSOR.XcursorImageCreate(width, height);
                xCursorImage.setXhot(xHotSpot);
                xCursorImage.setYhot(yHotSpot);
                xCursorImage.setPixels(nativePixels);

                final Method getDisplay = xToolkit.getMethod("getDisplay");
                final long dpy = (long) getDisplay.invoke(null);

                final long pData = XCURSOR.XcursorImageLoadCursor(dpy, xCursorImage);
                //final long pData = XCURSOR.XcursorImageLoadCursor(XToolkit.getDisplay(), xCursorImage);
                XCURSOR.XcursorImageDestroy(xCursorImage);
                UNSAFE.freeMemory(pNativePixels);
                AWTAccessor.getCursorAccessor().setPData(this, pData);
            }
            finally {
                final Method awtUnlock = xToolkit.getMethod("awtUnlock");
                awtUnlock.invoke(null);
                //XToolkit.awtUnlock();
            }

        } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
