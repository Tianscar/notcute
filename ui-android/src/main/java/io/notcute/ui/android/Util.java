package io.notcute.ui.android;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.view.MotionEvent;
import android.view.PointerIcon;
import io.notcute.input.Input;
import io.notcute.ui.Cursor;

final class Util {

    private Util() {
        throw new UnsupportedOperationException();
    }
    
    public static int toAndroidPointerType(int type) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            switch (type) {
                case Cursor.Type.NONE:
                    return PointerIcon.TYPE_NULL;
                case Cursor.Type.ARROW:
                    return PointerIcon.TYPE_ARROW;
                case Cursor.Type.CROSSHAIR:
                    return PointerIcon.TYPE_CROSSHAIR;
                case Cursor.Type.IBEAM:
                    return PointerIcon.TYPE_TEXT;
                case Cursor.Type.WAIT:
                case Cursor.Type.PROGRESS:
                    return PointerIcon.TYPE_WAIT;
                case Cursor.Type.HAND:
                    return PointerIcon.TYPE_HAND;
                case Cursor.Type.MOVE:
                    return PointerIcon.TYPE_ALL_SCROLL;
                case Cursor.Type.RESIZE_NS:
                case Cursor.Type.RESIZE_N:
                case Cursor.Type.RESIZE_S:
                    return PointerIcon.TYPE_VERTICAL_DOUBLE_ARROW;
                case Cursor.Type.RESIZE_WE:
                case Cursor.Type.RESIZE_W:
                case Cursor.Type.RESIZE_E:
                    return PointerIcon.TYPE_HORIZONTAL_DOUBLE_ARROW;
                case Cursor.Type.RESIZE_NWSE:
                case Cursor.Type.RESIZE_NW:
                case Cursor.Type.RESIZE_SE:
                    return PointerIcon.TYPE_TOP_LEFT_DIAGONAL_DOUBLE_ARROW;
                case Cursor.Type.RESIZE_NESW:
                case Cursor.Type.RESIZE_NE:
                case Cursor.Type.RESIZE_SW:
                    return PointerIcon.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW;
                case Cursor.Type.CELL:
                    return PointerIcon.TYPE_CELL;
                case Cursor.Type.HELP:
                    return PointerIcon.TYPE_HELP;
                case Cursor.Type.ZOOM_IN:
                    return PointerIcon.TYPE_ZOOM_IN;
                case Cursor.Type.ZOOM_OUT:
                    return PointerIcon.TYPE_ZOOM_OUT;
                case Cursor.Type.NO:
                case Cursor.Type.NO_DROP:
                    return PointerIcon.TYPE_NO_DROP;
                case Cursor.Type.GRAB:
                    return PointerIcon.TYPE_GRAB;
                case Cursor.Type.GRABBING:
                case Cursor.Type.MOVE_DROP:
                    return PointerIcon.TYPE_GRABBING;
                case Cursor.Type.COPY_DROP:
                    return PointerIcon.TYPE_COPY;
                case Cursor.Type.LINK_DROP:
                    return PointerIcon.TYPE_ALIAS;
                case Cursor.Type.UP_ARROW:
                    return PointerIcon.TYPE_ARROW; // FIXME RESOURCES
                case Cursor.Type.VERTICAL_IBEAM:
                    return PointerIcon.TYPE_VERTICAL_TEXT;
                case Cursor.Type.CONTEXT_MENU:
                    return PointerIcon.TYPE_CONTEXT_MENU;
                default:
                    throw new IllegalArgumentException("Invalid cursor type: " + type);
            }
        }
        else {
            throw new UnsupportedOperationException();
        }
    }
    
    public static boolean open(Context context, Uri uri) {
        if (context == null || uri == null) return false;
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(intent);
            return true;
        }
        catch (ActivityNotFoundException e) {
            return false;
        }
    }

    public static int toNotcuteButton(int buttonState) {
        if (buttonState == 0 || (buttonState & MotionEvent.BUTTON_PRIMARY) != 0) return Input.Button.LEFT;
        else if ((buttonState & MotionEvent.BUTTON_SECONDARY) != 0) return Input.Button.RIGHT;
        else if ((buttonState & MotionEvent.BUTTON_TERTIARY) != 0) return Input.Button.MIDDLE;
        else throw new IllegalArgumentException("Invalid button state: " + buttonState);
    }
    
}
