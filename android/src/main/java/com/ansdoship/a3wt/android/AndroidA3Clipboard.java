package com.ansdoship.a3wt.android;

import android.content.ClipboardManager;
import android.net.Uri;
import com.ansdoship.a3wt.app.A3Clipboard;

import java.net.URI;

import static com.ansdoship.a3wt.android.A3AndroidUtils.getClipboardContentType;
import static com.ansdoship.a3wt.android.A3AndroidUtils.putPlainTextToClipboard;
import static com.ansdoship.a3wt.android.A3AndroidUtils.getPlainTextFromClipboard;
import static com.ansdoship.a3wt.android.A3AndroidUtils.putHTMLTextToClipboard;
import static com.ansdoship.a3wt.android.A3AndroidUtils.getHTMLTextFromClipboard;
import static com.ansdoship.a3wt.android.A3AndroidUtils.putURIsToClipboard;
import static com.ansdoship.a3wt.android.A3AndroidUtils.getURIsFromClipboard;
import static com.ansdoship.a3wt.android.A3AndroidUtils.putUrisToClipboard;
import static com.ansdoship.a3wt.android.A3AndroidUtils.getUrisFromClipboard;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class AndroidA3Clipboard implements A3Clipboard {

    protected final Clipboard clipboard;
    protected final int selectionType;

    public AndroidA3Clipboard(final ClipboardManager manager) {
        checkArgNotNull(manager);
        selectionType = SelectionType.CLIPBOARD;
        this.clipboard = new Clipboard(manager);
    }

    public AndroidA3Clipboard(final int selectionType) {
        if (selectionType == SelectionType.CLIPBOARD) throw new IllegalArgumentException("Invalid selectionType: " + selectionType);
        this.selectionType = selectionType;
        clipboard = new Clipboard();
    }

    @Override
    public int getContentType() {
        return getClipboardContentType(clipboard);
    }

    @Override
    public int getSelectionType() {
        return selectionType;
    }

    @Override
    public void setPlainText(final CharSequence text) {
        putPlainTextToClipboard(clipboard, text);
    }

    @Override
    public CharSequence getPlainText() {
        return getPlainTextFromClipboard(clipboard);
    }

    @Override
    public void setHTMLText(final String html) {
        putHTMLTextToClipboard(clipboard, html);
    }

    @Override
    public String getHTMLText() {
        return getHTMLTextFromClipboard(clipboard);
    }

    @Override
    public void setURIs(final URI[] uris) {
        putURIsToClipboard(clipboard, uris);
    }

    public void setUris(final Uri[] uris) {
        putUrisToClipboard(clipboard, uris);
    }

    @Override
    public URI[] getURIs() {
        return getURIsFromClipboard(clipboard);
    }

    public Uri[] getUris() {
        return getUrisFromClipboard(clipboard);
    }

}
