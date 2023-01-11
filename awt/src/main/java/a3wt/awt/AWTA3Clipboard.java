package a3wt.awt;

import a3wt.app.A3Clipboard;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.net.URI;

import static a3wt.app.A3Clipboard.SelectionType.CLIPBOARD;
import static a3wt.app.A3Clipboard.SelectionType.SELECTION;

public class AWTA3Clipboard implements A3Clipboard {
    
    protected final int selectionType;
    protected final Clipboard clipboard;

    public AWTA3Clipboard(final int selectionType) {
        this.selectionType = selectionType;
        switch (selectionType) {
            case CLIPBOARD:
                clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                break;
            case SELECTION:
                clipboard = Toolkit.getDefaultToolkit().getSystemSelection();
                break;
            default:
                throw new IllegalArgumentException("Illegal selectionType: " + selectionType);
        }
    }

    public AWTA3Clipboard(final String name) {
        this.selectionType = SelectionType.APPLICATION;
        clipboard = new Clipboard(name);
    }

    @Override
    public int getContentType() {
        return A3AWTUtils.dataFlavor2ContentType(getDataFlavor());
    }

    @Override
    public int getSelectionType() {
        return selectionType;
    }
    
    private Clipboard getClipboard() {
        return clipboard;
    }

    public DataFlavor getDataFlavor() {
        return A3AWTUtils.getClipboardContentDataFlavor(getClipboard());
    }

    public void setContents(final Transferable contents) {
        A3AWTUtils.putContentsToClipboard(getClipboard(), contents);
    }

    public Object getContents(final DataFlavor dataFlavor) {
        return A3AWTUtils.getContentsFromClipboard(getClipboard(), dataFlavor);
    }

    @Override
    public void setPlainText(final CharSequence text) {
        A3AWTUtils.putPlainTextToClipboard(getClipboard(), text);
    }

    @Override
    public CharSequence getPlainText() {
        try {
            return A3AWTUtils.getPlainTextFromClipboard(getClipboard());
        } catch (IOException | UnsupportedFlavorException ignored) {
            return null;
        }
    }

    @Override
    public void setHTMLText(final String html) {
        A3AWTUtils.putHTMLTextToClipboard(getClipboard(), html);
    }

    @Override
    public String getHTMLText() {
        try {
            return A3AWTUtils.getHTMLTextFromClipboard(getClipboard());
        } catch (IOException | UnsupportedFlavorException e) {
            return null;
        }
    }

    @Override
    public void setURIs(final URI[] uris) {
        A3AWTUtils.putURIsToClipboard(getClipboard(), uris);
    }

    @Override
    public URI[] getURIs() {
        try {
            return A3AWTUtils.getURIsFromClipboard(getClipboard());
        } catch (IOException | UnsupportedFlavorException e) {
            return null;
        }
    }

}
