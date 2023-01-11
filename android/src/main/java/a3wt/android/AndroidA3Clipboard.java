package a3wt.android;

import android.content.ClipboardManager;
import android.net.Uri;
import a3wt.app.A3Clipboard;

import java.net.URI;

import static a3wt.android.A3AndroidUtils.getClipboardContentType;
import static a3wt.android.A3AndroidUtils.putPlainTextToClipboard;
import static a3wt.android.A3AndroidUtils.getPlainTextFromClipboard;
import static a3wt.android.A3AndroidUtils.putHTMLTextToClipboard;
import static a3wt.android.A3AndroidUtils.getHTMLTextFromClipboard;
import static a3wt.android.A3AndroidUtils.putURIsToClipboard;
import static a3wt.android.A3AndroidUtils.getURIsFromClipboard;
import static a3wt.android.A3AndroidUtils.putUrisToClipboard;
import static a3wt.android.A3AndroidUtils.getUrisFromClipboard;
import static a3wt.util.A3Preconditions.checkArgNotNull;

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
        return A3AndroidUtils.getClipboardContentType(clipboard);
    }

    @Override
    public int getSelectionType() {
        return selectionType;
    }

    @Override
    public void setPlainText(final CharSequence text) {
        A3AndroidUtils.putPlainTextToClipboard(clipboard, text);
    }

    @Override
    public CharSequence getPlainText() {
        return A3AndroidUtils.getPlainTextFromClipboard(clipboard);
    }

    @Override
    public void setHTMLText(final String html) {
        A3AndroidUtils.putHTMLTextToClipboard(clipboard, html);
    }

    @Override
    public String getHTMLText() {
        return A3AndroidUtils.getHTMLTextFromClipboard(clipboard);
    }

    @Override
    public void setURIs(final URI[] uris) {
        A3AndroidUtils.putURIsToClipboard(clipboard, uris);
    }

    public void setUris(final Uri[] uris) {
        A3AndroidUtils.putUrisToClipboard(clipboard, uris);
    }

    @Override
    public URI[] getURIs() {
        return A3AndroidUtils.getURIsFromClipboard(clipboard);
    }

    public Uri[] getUris() {
        return A3AndroidUtils.getUrisFromClipboard(clipboard);
    }

}
