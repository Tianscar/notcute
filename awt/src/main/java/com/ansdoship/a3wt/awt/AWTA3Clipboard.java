package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.app.A3Clipboard;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.net.URI;

import static com.ansdoship.a3wt.app.A3Clipboard.SelectionType.CLIPBOARD;
import static com.ansdoship.a3wt.app.A3Clipboard.SelectionType.SELECTION;
import static com.ansdoship.a3wt.awt.A3AWTUtils.getPlainTextFromClipboard;
import static com.ansdoship.a3wt.awt.A3AWTUtils.putPlainTextToClipboard;
import static com.ansdoship.a3wt.awt.A3AWTUtils.getHTMLTextFromClipboard;
import static com.ansdoship.a3wt.awt.A3AWTUtils.putHTMLTextToClipboard;
import static com.ansdoship.a3wt.awt.A3AWTUtils.getURIsFromClipboard;
import static com.ansdoship.a3wt.awt.A3AWTUtils.putURIsToClipboard;
import static com.ansdoship.a3wt.awt.A3AWTUtils.getClipboardContentDataFlavor;
import static com.ansdoship.a3wt.awt.A3AWTUtils.dataFlavor2ContentType;
import static com.ansdoship.a3wt.awt.A3AWTUtils.putContentsToClipboard;
import static com.ansdoship.a3wt.awt.A3AWTUtils.getContentsFromClipboard;

public class AWTA3Clipboard implements A3Clipboard {
    
    private final int selectionType;

    public AWTA3Clipboard(final int selectionType) {
        this.selectionType = selectionType;
    }

    @Override
    public int getContentType() {
        return dataFlavor2ContentType(getDataFlavor());
    }

    @Override
    public int getSelectionType() {
        return selectionType;
    }
    
    private Clipboard getClipboard() {
        switch (selectionType) {
            case CLIPBOARD:
                return getClipboard();
            case SELECTION:
                return Toolkit.getDefaultToolkit().getSystemSelection();
            default:
                return null;
        }
    }

    public DataFlavor getDataFlavor() {
        return getClipboardContentDataFlavor(getClipboard());
    }

    public void setContents(final Transferable contents) {
        putContentsToClipboard(getClipboard(), contents);
    }

    public Object getContents(final DataFlavor dataFlavor) {
        return getContentsFromClipboard(getClipboard(), dataFlavor);
    }

    @Override
    public void setPlainText(final CharSequence text) {
        putPlainTextToClipboard(getClipboard(), text);
    }

    @Override
    public CharSequence getPlainText() {
        try {
            return getPlainTextFromClipboard(getClipboard());
        } catch (IOException | UnsupportedFlavorException ignored) {
            return null;
        }
    }

    @Override
    public void setHTMLText(final String html) {
        putHTMLTextToClipboard(getClipboard(), html);
    }

    @Override
    public String getHTMLText() {
        try {
            return getHTMLTextFromClipboard(getClipboard());
        } catch (IOException | UnsupportedFlavorException e) {
            return null;
        }
    }

    @Override
    public void setURIs(final URI[] uris) {
        putURIsToClipboard(getClipboard(), uris);
    }

    @Override
    public URI[] getURIs() {
        try {
            return getURIsFromClipboard(getClipboard());
        } catch (IOException | UnsupportedFlavorException e) {
            return null;
        }
    }

}
