package io.notcute.app.android;

import android.content.ClipboardManager;

import java.net.URI;
import java.util.Objects;

public class AndroidClipboard implements io.notcute.app.Clipboard {

    private static volatile AndroidClipboard systemClipboard;
    public static AndroidClipboard getSystemClipboard(ClipboardManager manager) {
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
        return Util.getClipboardContentType(clipboard);
    }

    @Override
    public int getSelectionType() {
        return selectionType;
    }

    @Override
    public void setPlainText(CharSequence text) {
        Util.putPlainTextToClipboard(clipboard, text);
    }

    @Override
    public CharSequence getPlainText() {
        return Util.getPlainTextFromClipboard(clipboard);
    }

    @Override
    public void setHTMLText(String html) {
        Util.putHTMLTextToClipboard(clipboard, html);
    }

    @Override
    public String getHTMLText() {
        return Util.getHTMLTextFromClipboard(clipboard);
    }

    @Override
    public void setURIs(URI[] uris) {
        Util.putURIsToClipboard(clipboard, uris);
    }

    @Override
    public URI[] getURIs() {
        return Util.getURIsFromClipboard(clipboard);
    }

}
