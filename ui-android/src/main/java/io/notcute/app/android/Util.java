package io.notcute.app.android;

import android.content.ClipData;
import android.content.ClipDescription;
import android.net.Uri;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

final class Util {

    private Util() {
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
    
}
