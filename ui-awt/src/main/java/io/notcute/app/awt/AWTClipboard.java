package io.notcute.app.awt;

import io.notcute.internal.awt.AWTUIUtils;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.net.URI;

public class AWTClipboard implements io.notcute.app.Clipboard {
    
    private final int selectionType;
    private final Clipboard clipboard;

    public static final AWTClipboard CLIPBOARD = new AWTClipboard(SelectionType.CLIPBOARD);
    public static final AWTClipboard SELECTION = new AWTClipboard(SelectionType.SELECTION);

    private AWTClipboard(int selectionType) {
        this.selectionType = selectionType;
        switch (selectionType) {
            case SelectionType.CLIPBOARD:
                clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                break;
            case SelectionType.SELECTION:
                clipboard = Toolkit.getDefaultToolkit().getSystemSelection();
                break;
            default:
                throw new IllegalArgumentException("Invalid selectionType: " + selectionType);
        }
    }

    public AWTClipboard(String name) {
        this.selectionType = SelectionType.APPLICATION;
        clipboard = new Clipboard(name);
    }

    public Clipboard getClipboard() {
        return clipboard;
    }

    @Override
    public int getContentType() {
        return AWTUIUtils.toNotcuteContentType(getDataFlavor());
    }

    @Override
    public int getSelectionType() {
        return selectionType;
    }

    public DataFlavor getDataFlavor() {
        return AWTUIUtils.getClipboardContentDataFlavor(getClipboard());
    }

    public void setContents(Transferable contents) {
        AWTUIUtils.putContentsToClipboard(getClipboard(), contents);
    }

    public Object getContents(DataFlavor dataFlavor) {
        return AWTUIUtils.getContentsFromClipboard(getClipboard(), dataFlavor);
    }

    @Override
    public void setPlainText(CharSequence text) {
        AWTUIUtils.putPlainTextToClipboard(getClipboard(), text);
    }

    @Override
    public CharSequence getPlainText() {
        try {
            return AWTUIUtils.getPlainTextFromClipboard(getClipboard());
        } catch (IOException | UnsupportedFlavorException ignored) {
            return null;
        }
    }

    @Override
    public void setHTMLText(String html) {
        AWTUIUtils.putHTMLTextToClipboard(getClipboard(), html);
    }

    @Override
    public String getHTMLText() {
        try {
            return AWTUIUtils.getHTMLTextFromClipboard(getClipboard());
        } catch (IOException | UnsupportedFlavorException e) {
            return null;
        }
    }

    @Override
    public void setURIs(URI[] uris) {
        AWTUIUtils.putURIsToClipboard(getClipboard(), uris);
    }

    @Override
    public URI[] getURIs() {
        try {
            return AWTUIUtils.getURIsFromClipboard(getClipboard());
        } catch (IOException | UnsupportedFlavorException e) {
            return null;
        }
    }

}
