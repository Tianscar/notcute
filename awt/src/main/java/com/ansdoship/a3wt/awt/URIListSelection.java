package com.ansdoship.a3wt.awt;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.datatransfer.Clipboard;
import java.io.IOException;
import java.net.URI;
import java.util.List;

public class URIListSelection implements Transferable, ClipboardOwner {

    public static final DataFlavor uriListFlavor = new DataFlavor("text/uri-list; class=java.lang.String", null);

    private static final DataFlavor[] supportedFlavors = { uriListFlavor };

    private final List<URI> uriList;

    public URIListSelection(List<URI> data) {
        uriList = data;
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (flavor.equals(uriListFlavor)) {
            return uriList2String(uriList);
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }

    private String uriList2String(List<URI> uriList) {
        StringBuilder builder = new StringBuilder();
        int size = uriList.size();
        for (int i = 0; i < size; i ++) {
            builder.append(uriList.get(i));
            if (i + 1 < size) builder.append('\n');
        }
        return builder.toString();
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(uriListFlavor);
    }

    public DataFlavor[] getTransferDataFlavors() {
        return supportedFlavors.clone();
    }

    public void lostOwnership(Clipboard clipboard, Transferable contents) {
    }

}
