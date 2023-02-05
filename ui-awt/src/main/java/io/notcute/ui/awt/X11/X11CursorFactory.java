package io.notcute.ui.awt.X11;

import sun.awt.X11.XToolkit;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.Point;
import java.awt.HeadlessException;
import java.awt.GraphicsEnvironment;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class X11CursorFactory {

    private static final long NULL = 0L;

    private static void checkHeadless() throws HeadlessException {
        if (GraphicsEnvironment.isHeadless()) throw new HeadlessException();
    }

    public static Cursor createXCustomCursor(final Image cursor, final Point hotSpot, final String name) throws IndexOutOfBoundsException, HeadlessException {
        checkHeadless();
        if (JNRFFI.Xcursor.INSTANCE.XcursorSupportsARGB(XToolkit.getDisplay())) return new X11CustomCursor(cursor, hotSpot, name);
        else return Toolkit.getDefaultToolkit().createCustomCursor(cursor, hotSpot, name);
    }

    private static final Map<String, Cursor> systemCursors = new ConcurrentHashMap<>(1);

    public static Cursor getXSystemCursor(final int type) throws IllegalArgumentException, HeadlessException {
        checkHeadless();
        final String name = getXSystemCursorName(type);
        final long pData = JNRFFI.Xlib.INSTANCE.XCreateFontCursor(XToolkit.getDisplay(), type);
        if (pData == NULL) return Cursor.getDefaultCursor();
        if (!systemCursors.containsKey(name)) systemCursors.put(name, new X11SystemCursor(name, pData));
        return systemCursors.get(name);
    }

    private static String getXSystemCursorName(final int type) {
        switch (type) {
            case 0: return "X_cursor";
            case 2: return "arrow";
            case 4: return "based_arrow_down";
            case 6: return "based_arrow_up";
            case 8: return "boat";
            case 10: return "bogosity";
            case 12: return "bottom_left_corner";
            case 14: return "bottom_right_corner";
            case 16: return "bottom_side";
            case 18: return "bottom_tee";
            case 20: return "box_spiral";
            case 22: return "center_ptr";
            case 24: return "circle";
            case 26: return "clock";
            case 28: return "coffee_mug";
            case 30: return "cross";
            case 32: return "cross_reverse";
            case 34: return "crosshair";
            case 36: return "diamond_cross";
            case 38: return "dot";
            case 40: return "dotbox";
            case 42: return "double_arrow";
            case 44: return "draft_large";
            case 46: return "draft_small";
            case 48: return "draped_box";
            case 50: return "exchange";
            case 52: return "fleur";
            case 54: return "gobbler";
            case 56: return "gumby";
            case 58: return "hand1";
            case 60: return "hand2";
            case 62: return "heart";
            case 64: return "icon";
            case 66: return "iron_cross";
            case 68: return "left_ptr";
            case 70: return "left_side";
            case 72: return "left_tee";
            case 74: return "leftbutton";
            case 76: return "ll_angle";
            case 78: return "lr_angle";
            case 80: return "man";
            case 82: return "middlebutton";
            case 84: return "mouse";
            case 86: return "pencil";
            case 88: return "pirate";
            case 90: return "plus";
            case 92: return "question_arrow";
            case 94: return "right_ptr";
            case 96: return "right_side";
            case 98: return "right_tee";
            case 100: return "rightbutton";
            case 102: return "rtl_logo";
            case 104: return "sailboat";
            case 106: return "sb_down_arrow";
            case 108: return "sb_h_double_arrow";
            case 110: return "sb_left_arrow";
            case 112: return "sb_right_arrow";
            case 114: return "sb_up_arrow";
            case 116: return "sb_v_double_arrow";
            case 118: return "shuttle";
            case 120: return "sizing";
            case 122: return "spider";
            case 124: return "spraycan";
            case 126: return "star";
            case 128: return "target";
            case 130: return "tcross";
            case 132: return "top_left_arrow";
            case 134: return "top_left_corner";
            case 136: return "top_right_corner";
            case 138: return "top_side";
            case 140: return "top_tee";
            case 142: return "trek";
            case 144: return "ul_angle";
            case 146: return "umbrella";
            case 148: return "ur_angle";
            case 150: return "watch";
            case 152: return "xterm";
            default: throw new IllegalArgumentException("Invalid type: " + type);
        }
    }

    public static Cursor getXSystemCursor(final String name) throws HeadlessException {
        checkHeadless();
        final long pData = JNRFFI.Xcursor.INSTANCE.XcursorLibraryLoadCursor(XToolkit.getDisplay(), name);
        if (pData == NULL) return Cursor.getDefaultCursor();
        else {
            if (!systemCursors.containsKey(name)) systemCursors.put(name, new X11SystemCursor(name, pData));
            return systemCursors.get(name);
        }
    }

}
