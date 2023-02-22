package io.notcute.internal.awt.X11;

import sun.awt.AWTAccessor;

import java.awt.Cursor;

public final class X11SystemCursor extends Cursor {

    private static final long serialVersionUID = -2724726940047545807L;

    X11SystemCursor(String name, long pData) {
        super(name);
        AWTUIX11Utils.awtLock();
        try {
            AWTAccessor.getCursorAccessor().setPData(this, pData);
        } finally {
            AWTUIX11Utils.awtUnlock();
        }
    }

}
