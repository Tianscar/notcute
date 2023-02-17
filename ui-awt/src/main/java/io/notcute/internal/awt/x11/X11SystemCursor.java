package io.notcute.internal.awt.x11;

import sun.awt.AWTAccessor;
import sun.awt.X11.XToolkit;

import java.awt.Cursor;

final class X11SystemCursor extends Cursor {

    private static final long serialVersionUID = -2724726940047545807L;

    X11SystemCursor(String name, long pData) {
        super(name);
        XToolkit.awtLock();
        try {
            AWTAccessor.getCursorAccessor().setPData(this, pData);
        } finally {
            XToolkit.awtUnlock();
        }
    }

}
