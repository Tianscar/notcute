package io.notcute.internal.android;

import android.content.*;
import android.net.Uri;
import android.os.Build;
import android.view.MotionEvent;
import android.view.PointerIcon;
import io.notcute.app.android.Clipboard;
import io.notcute.input.Input;
import io.notcute.ui.Cursor;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class AndroidUIUtils {

    private AndroidUIUtils() {
        throw new UnsupportedOperationException();
    }

    public static int getClipboardContentType(Clipboard clipboard) {
        if (!clipboard.hasPrimaryClip()) return -1;
        final ClipData clipData = clipboard.getPrimaryClip();
        final ClipDescription description = clipData.getDescription();
        if (description.hasMimeType(ClipDescription.MIMETYPE_TEXT_HTML)) return io.notcute.app.Clipboard.ContentType.HTML_TEXT;
        else if (description.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) return io.notcute.app.Clipboard.ContentType.PLAIN_TEXT;
        else if (description.hasMimeType(ClipDescription.MIMETYPE_TEXT_URILIST)) return io.notcute.app.Clipboard.ContentType.URI_LIST;
        else return -1;
    }

    public static void putPlainTextToClipboard(Clipboard clipboard, CharSequence plainText) {
        Objects.requireNonNull(plainText);
        clipboard.setPrimaryClip(ClipData.newPlainText(null, plainText));
    }

    public static CharSequence getPlainTextFromClipboard(Clipboard clipboard) {
        if (!clipboard.hasPrimaryClip()) return null;
        ClipData clipData = clipboard.getPrimaryClip();
        if (!clipData.getDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) return null;
        StringBuilder builder = new StringBuilder();
        CharSequence text;
        int itemCount = clipData.getItemCount();
        for (int i = 0; i < itemCount; i ++) {
            text = clipData.getItemAt(i).getText();
            if (text != null) {
                builder.append(text);
                if (i + 1 < itemCount) builder.append('\n');
            }
        }
        return builder;
    }
    public static void putHTMLTextToClipboard(Clipboard clipboard, String HTMLText) {
        Objects.requireNonNull(HTMLText);
        clipboard.setPrimaryClip(ClipData.newHtmlText(null, HTMLText, HTMLText));
    }

    public static String getHTMLTextFromClipboard(Clipboard clipboard) {
        if (!clipboard.hasPrimaryClip()) return null;
        ClipData clipData = clipboard.getPrimaryClip();
        if (!clipData.getDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_HTML)) return null;
        StringBuilder builder = new StringBuilder();
        String text;
        int itemCount = clipData.getItemCount();
        for (int i = 0; i < itemCount; i ++) {
            text = clipData.getItemAt(i).getHtmlText();
            if (text != null) {
                builder.append(text);
                if (i + 1 < itemCount) builder.append('\n');
            }
        }
        return builder.toString();
    }

    public static void putURIsToClipboard(Clipboard clipboard, URI[] uris) {
        Objects.requireNonNull(clipboard);
        if (uris.length > 0) {
            ClipData clipData = ClipData.newRawUri(null, Uri.parse(Uri.decode(uris[0].toString())));
            for (int i = 1; i < uris.length; i ++) {
                clipData.addItem(new ClipData.Item(Uri.parse(uris[i].toString())));
            }
            clipboard.setPrimaryClip(clipData);
        }
        else clipboard.setPrimaryClip(ClipData.newPlainText(null, ""));
    }

    public static URI[] getURIsFromClipboard(Clipboard clipboard) {
        if (!clipboard.hasPrimaryClip()) return null;
        ClipData clipData = clipboard.getPrimaryClip();
        if (!clipData.getDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_URILIST)) return null;
        List<URI> uris = new ArrayList<>();
        Uri uri;
        for (int i = 0; i < clipData.getItemCount(); i ++) {
            uri = clipData.getItemAt(i).getUri();
            if (uri != null) uris.add(URI.create(uri.toString()));
        }
        return uris.toArray(new URI[0]);
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
