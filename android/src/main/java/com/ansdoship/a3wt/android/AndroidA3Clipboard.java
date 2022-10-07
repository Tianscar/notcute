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

    protected final ClipboardManager manager;

    public AndroidA3Clipboard(final ClipboardManager manager) {
        checkArgNotNull(manager);
        this.manager = manager;
    }

    @Override
    public int getContentType() {
        return getClipboardContentType(manager);
    }

    @Override
    public int getSelectionType() {
        return 0;
    }

    @Override
    public void setPlainText(final CharSequence text) {
        putPlainTextToClipboard(manager, text);
    }

    @Override
    public CharSequence getPlainText() {
        return getPlainTextFromClipboard(manager);
    }

    @Override
    public void setHTMLText(final String html) {
        putHTMLTextToClipboard(manager, html);
    }

    @Override
    public String getHTMLText() {
        return getHTMLTextFromClipboard(manager);
    }

    @Override
    public void setURIs(final URI[] uris) {
        putURIsToClipboard(manager, uris);
    }

    public void setUris(final Uri[] uris) {
        putUrisToClipboard(manager, uris);
    }

    @Override
    public URI[] getURIs() {
        return getURIsFromClipboard(manager);
    }

    public Uri[] getUris() {
        return getUrisFromClipboard(manager);
    }

}
