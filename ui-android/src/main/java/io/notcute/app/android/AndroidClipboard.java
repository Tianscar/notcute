package io.notcute.app.android;

import android.content.ClipboardManager;
import io.notcute.internal.android.AndroidUIUtils;

import java.net.URI;
import java.util.Objects;

public class AndroidClipboard implements io.notcute.app.Clipboard {

    private static volatile AndroidClipboard systemClipboard;
    public synchronized static AndroidClipboard getSystemClipboard(ClipboardManager manager) {
        if (systemClipboard == null) systemClipboard = new AndroidClipboard(manager);
        return systemClipboard;
    }

    public static final AndroidClipboard SELECTION = new AndroidClipboard(SelectionType.SELECTION, "SELECTION");

    private final String name;
    private final Clipboard clipboard;
    private final int selectionType;

    private AndroidClipboard(ClipboardManager manager) {
        Objects.requireNonNull(manager);
        selectionType = SelectionType.CLIPBOARD;
        name = "CLIPBOARD";
        this.clipboard = new Clipboard(manager);
    }

    public AndroidClipboard(String name) {
        this(SelectionType.APPLICATION, name);
    }

    private AndroidClipboard(int selectionType, String name) {
        if (selectionType == SelectionType.CLIPBOARD) throw new IllegalArgumentException("Invalid selectionType: " + selectionType);
        this.selectionType = selectionType;
        this.name = name;
        clipboard = new Clipboard();
    }

    public Clipboard getClipboard() {
        return clipboard;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getContentType() {
        return AndroidUIUtils.getClipboardContentType(clipboard);
    }

    @Override
    public int getSelectionType() {
        return selectionType;
    }

    @Override
    public void setPlainText(CharSequence text) {
        AndroidUIUtils.putPlainTextToClipboard(clipboard, text);
    }

    @Override
    public CharSequence getPlainText() {
        return AndroidUIUtils.getPlainTextFromClipboard(clipboard);
    }

    @Override
    public void setHTMLText(String html) {
        AndroidUIUtils.putHTMLTextToClipboard(clipboard, html);
    }

    @Override
    public String getHTMLText() {
        return AndroidUIUtils.getHTMLTextFromClipboard(clipboard);
    }

    @Override
    public void setURIs(URI[] uris) {
        AndroidUIUtils.putURIsToClipboard(clipboard, uris);
    }

    @Override
    public URI[] getURIs() {
        return AndroidUIUtils.getURIsFromClipboard(clipboard);
    }

}
