package io.notcute.internal.awt;

import jnr.ffi.Platform;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.Point;
import java.awt.AWTException;
import java.lang.reflect.InvocationTargetException;

import static io.notcute.internal.awt.x11.X11CursorConstants.*;

public final class CursorFactory {

    public static final int ARROW = 0;
    public static final int DEFAULT = ARROW;
    public static final int CROSSHAIR = 1;
    public static final int IBEAM = 2;
    public static final int WAIT = 3;
    public static final int HAND = 4;
    public static final int MOVE = 5;
    public static final int RESIZE_N = 6;
    public static final int RESIZE_S = 7;
    public static final int RESIZE_W = 8;
    public static final int RESIZE_E = 9;
    public static final int RESIZE_SW = 10;
    public static final int RESIZE_SE = 11;
    public static final int RESIZE_NW = 12;
    public static final int RESIZE_NE = 13;
    public static final int RESIZE_NS = 14;
    public static final int RESIZE_WE = 15;
    public static final int RESIZE_NWSE = 16;
    public static final int RESIZE_NESW = 17;
    public static final int CELL = 18;
    public static final int HELP = 19;
    public static final int ZOOM_IN = 20;
    public static final int ZOOM_OUT = 21;
    public static final int NO = 22;
    public static final int GRAB = 23;
    public static final int GRABBING = 24;
    public static final int COPY_DROP = 25;
    public static final int LINK_DROP = 26;
    public static final int MOVE_DROP = 27;
    public static final int NO_DROP = 28;
    public static final int UP_ARROW = 29;
    public static final int VERTICAL_IBEAM = 30;
    public static final int CONTEXT_MENU = 31;
    public static final int PROGRESS = 32;
    public static final int NONE = 100;

    public static Cursor getPredefinedCursor(final int type) {
        switch (type) {
            default:
            case ARROW: return Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
            case CROSSHAIR: return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
            case IBEAM: return Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR);
            case WAIT:
                if (ISMACOS) return Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR); // FIXME
                else return Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
            case HAND: return Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
            case MOVE: return Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
            case RESIZE_N: return Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
            case RESIZE_S: return Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
            case RESIZE_W: return Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
            case RESIZE_E: return Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
            case RESIZE_SW: return Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
            case RESIZE_SE: return Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
            case RESIZE_NW: return Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
            case RESIZE_NE: return Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
            case RESIZE_NS:
                if (ISX11) return getXSystemCursor(XC_sb_v_double_arrow);
                else return Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
            case RESIZE_WE:
                if (ISX11) return getXSystemCursor(XC_sb_h_double_arrow);
                else return Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
            case RESIZE_NWSE:
                if (ISX11) return getXSystemCursor("bd_double_arrow");
                else if (ISWINDOWS) return Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
                else return Cursor.getDefaultCursor(); //FIXME
            case RESIZE_NESW:
                if (ISX11) return getXSystemCursor("fd_double_arrow");
                else if (ISWINDOWS) return Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
                else return Cursor.getDefaultCursor(); //FIXME
            case CELL:
                if (ISX11) return getXSystemCursor("cell");
                else if (ISWINDOWS) return Cursor.getDefaultCursor(); //FIXME
                else return Cursor.getDefaultCursor(); //FIXME
            case HELP:
                if (ISX11) return getXSystemCursor(XC_question_arrow);
                else if (ISWINDOWS) return Cursor.getDefaultCursor(); //FIXME
                else return Cursor.getDefaultCursor(); //FIXME
            case ZOOM_IN:
                if (ISX11) return getXSystemCursor("zoom-in");
                else if (ISWINDOWS) return Cursor.getDefaultCursor(); //FIXME
                else return Cursor.getDefaultCursor(); //FIXME
            case ZOOM_OUT:
                if (ISX11) return getXSystemCursor("zoom-out");
                else if (ISWINDOWS) return Cursor.getDefaultCursor(); //FIXME
                else return Cursor.getDefaultCursor(); //FIXME
            case NO:
                if (ISX11) return getXSystemCursor("crossed_circle");
                else if (ISWINDOWS) return getSystemCustomCursor("Invalid.32x32"); //FIXME
                else return getDesktopPropertyCursor("DnD.Cursor.MoveNoDrop"); //FIXME
            case GRAB:
                if (ISX11) return getXSystemCursor(XC_hand1);
                else if (ISWINDOWS) return Cursor.getDefaultCursor();
                else return Cursor.getDefaultCursor(); //FIXME
            case GRABBING:
                if (ISX11) return getXSystemCursor("grabbing");
                else if (ISWINDOWS) return getSystemCustomCursor("MoveDrop.32x32"); //FIXME
                else return getDesktopPropertyCursor("DnD.Cursor.MoveDrop");
            case COPY_DROP:
                if (ISX11) return getXSystemCursor("dnd-copy");
                else if (ISWINDOWS) return getSystemCustomCursor("CopyDrop.32x32");
                else return getDesktopPropertyCursor("DnD.Cursor.CopyDrop");
            case LINK_DROP:
                if (ISX11) return getXSystemCursor("dnd-link");
                else if (ISWINDOWS) return getSystemCustomCursor("LinkDrop.32x32");
                else return getDesktopPropertyCursor("DnD.Cursor.LinkDrop");
            case MOVE_DROP:
                if (ISX11) return getXSystemCursor("dnd-move");
                else if (ISWINDOWS) return getSystemCustomCursor("MoveDrop.32x32");
                else return getDesktopPropertyCursor("DnD.Cursor.MoveDrop");
            case NO_DROP:
                if (ISX11) return getXSystemCursor("dnd-no-drop");
                else if (ISWINDOWS) return getSystemCustomCursor("Invalid.32x32"); //FIXME
                else return getDesktopPropertyCursor("DnD.Cursor.MoveNoDrop");
            case UP_ARROW:
                if (ISX11) return getXSystemCursor(XC_sb_up_arrow);
                else if (ISWINDOWS) return Cursor.getDefaultCursor(); //FIXME
                else return Cursor.getDefaultCursor(); //FIXME
            case VERTICAL_IBEAM:
                if (ISX11) return getXSystemCursor("vertical-text");
                else if (ISWINDOWS) return Cursor.getDefaultCursor(); //FIXME
                else return Cursor.getDefaultCursor(); //FIXME
            case CONTEXT_MENU:
                if (ISX11) return getXSystemCursor("context-menu");
                else if (ISWINDOWS) return Cursor.getDefaultCursor(); //FIXME
                else return Cursor.getDefaultCursor(); //FIXME
            case PROGRESS:
                if (ISX11) return getXSystemCursor("left_ptr_watch");
                else if (ISWINDOWS) return Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR); //FIXME
                else return Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
            case NONE: return NONE_CURSOR;
        }
    }

    private static Cursor getXSystemCursor(final String name) {
        try {
            return (Cursor) Class.forName("io.notcute.internal.awt.x11.X11CursorFactory")
                    .getDeclaredMethod("getXSystemCursor", String.class).invoke(null, name);
        } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            return Cursor.getDefaultCursor();
        }
    }

    private static Cursor getXSystemCursor(final int type) {
        try {
            return (Cursor) Class.forName("io.notcute.internal.awt.x11.X11CursorFactory")
                    .getDeclaredMethod("getXSystemCursor", int.class).invoke(null, type);
        } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            return Cursor.getDefaultCursor();
        }
    }

    private static Cursor createXCustomCursor(final Image cursor, final Point hotSpot, final String name) {
        try {
            return (Cursor) Class.forName("io.notcute.internal.awt.x11.X11CursorFactory")
                    .getDeclaredMethod("createXCustomCursor", Image.class, Point.class, String.class).invoke(null, cursor, hotSpot, name);
        } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            return Cursor.getDefaultCursor();
        }
    }

    public static Cursor createCustomCursor(final Image cursor, final Point hotSpot, final String name) {
        if (ISX11) return createXCustomCursor(cursor, hotSpot, name);
        else return Toolkit.getDefaultToolkit().createCustomCursor(cursor, hotSpot, name);
    }

    private CursorFactory() {
        throw new UnsupportedOperationException();
    }

    private static Cursor getSystemCustomCursor(final String name) {
        try {
            final Cursor cursor = Cursor.getSystemCustomCursor(name);
            return cursor == null ? Cursor.getDefaultCursor() : cursor;
        }
        catch (final AWTException e) {
            return Cursor.getDefaultCursor();
        }
    }

    private static Cursor getDesktopPropertyCursor(final String name) {
        return (Cursor) Toolkit.getDefaultToolkit().getDesktopProperty(name);
    }

    private static final Cursor NONE_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(
            Toolkit.getDefaultToolkit().getImage(CursorFactory.class.getResource("")),
            new Point(0, 0), "NONE");
    private static final boolean ISX11;
    private static final boolean ISWINDOWS;
    private static final boolean ISMACOS;
    static {
        switch (Platform.getNativePlatform().getOS()) {
            case WINDOWS:
                ISWINDOWS = true;
                ISMACOS = ISX11 = false;
                break;
            case DARWIN:
                ISWINDOWS = ISX11 = false;
                ISMACOS = true;
                break;
            default:
                ISWINDOWS = ISMACOS = false;
                ISX11 = true;
                break;
        }
    }

}
