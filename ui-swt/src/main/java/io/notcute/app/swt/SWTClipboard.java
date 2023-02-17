package io.notcute.app.swt;

import org.eclipse.swt.dnd.*;
import org.eclipse.swt.widgets.Display;

import java.net.URI;
import java.util.Objects;

public class SWTClipboard implements io.notcute.app.Clipboard {

    private final Clipboard clipboard;
    private final int selectionType;
    private final int clipboards;

    private SWTClipboard(Display display, int selectionType) {
        Objects.requireNonNull(display);
        this.selectionType = selectionType;
        switch (selectionType) {
            case SelectionType.CLIPBOARD:
            case SelectionType.SELECTION:
                clipboard = new Clipboard(display);
                //clipboards = Util.toSWTSelectionType(selectionType);
                clipboards = -1;
                break;
            default:
                throw new IllegalArgumentException("Invalid selectionType: " + selectionType);
        }
    }

    @Override
    public int getContentType() {
        //return Util.toNotcuteContentType(clipboard.getAvailableTypes());
        return -1;
    }

    @Override
    public int getSelectionType() {
        return selectionType;
    }

    @Override
    public void setPlainText(CharSequence text) {
        clipboard.clearContents(clipboards);
        clipboard.setContents(new Object[] { text.toString() }, new Transfer[] { TextTransfer.getInstance() }, clipboards);
    }

    @Override
    public CharSequence getPlainText() {
        return (String) clipboard.getContents(TextTransfer.getInstance(), clipboards);
    }

    @Override
    public void setHTMLText(String html) {
        clipboard.clearContents(clipboards);
        clipboard.setContents(new Object[] { html }, new Transfer[] { HTMLTransfer.getInstance() }, clipboards);
    }

    @Override
    public String getHTMLText() {
        return (String) clipboard.getContents(HTMLTransfer.getInstance(), clipboards);
    }

    @Override
    public void setURIs(URI[] uris) {
        clipboard.clearContents(clipboards);
        //clipboard.setContents(Util.toStrings(uris), new Transfer[] { FileTransfer.getInstance() }, clipboards);
    }

    @Override
    public URI[] getURIs() {
        //return Util.toURIs(clipboard.getContents(FileTransfer.getInstance(), clipboards));
        return null;
    }

}
