package io.notcute.app.awt;

import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static io.notcute.app.Clipboard.ContentType.*;

final class Util {

    private Util() {
        throw new UnsupportedOperationException();
    }

    public static void putPlainTextToClipboard(Clipboard clipboard, CharSequence plainText) {
        clipboard.setContents(new StringSelection(plainText.toString()), null);
    }

    public static CharSequence getPlainTextFromClipboard(Clipboard clipboard) throws IOException, UnsupportedFlavorException {
        Transferable transferable = clipboard.getContents(null);
        if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return (String) transferable.getTransferData(DataFlavor.stringFlavor);
        }
        else return null;
    }

    public static void putHTMLTextToClipboard(Clipboard clipboard, String HTMLText) {
        clipboard.setContents(new StringSelection(HTMLText), null);
    }

    public static String getHTMLTextFromClipboard(Clipboard clipboard) throws IOException, UnsupportedFlavorException {
        Transferable transferable = clipboard.getContents(null);
        if (transferable.isDataFlavorSupported(DataFlavor.selectionHtmlFlavor)) {
            return (String) transferable.getTransferData(DataFlavor.selectionHtmlFlavor);
        }
        else if (transferable.isDataFlavorSupported(DataFlavor.fragmentHtmlFlavor)) {
            return (String) transferable.getTransferData(DataFlavor.fragmentHtmlFlavor);
        }
        else if (transferable.isDataFlavorSupported(DataFlavor.allHtmlFlavor)) {
            return (String) transferable.getTransferData(DataFlavor.allHtmlFlavor);
        }
        else return null;
    }

    public static void putURIsToClipboard(Clipboard clipboard, URI[] uris) {
        clipboard.setContents(new URIListSelection(Arrays.asList(uris)), null);
    }

    public static URI[] getURIsFromClipboard(Clipboard clipboard) throws IOException, UnsupportedFlavorException {
        Transferable transferable = clipboard.getContents(null);
        if (transferable.isDataFlavorSupported(URIListSelection.uriListFlavor)) {
            return ((List<URI>) transferable.getTransferData(URIListSelection.uriListFlavor)).toArray(new URI[0]);
        }
        else if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            return files2URIs(((List<File>)transferable.getTransferData(DataFlavor.javaFileListFlavor)).toArray(new File[0]));
        }
        else return null;
    }

    public static void putContentsToClipboard(Clipboard clipboard, Transferable contents) {
        clipboard.setContents(contents, null);
    }

    public static Object getContentsFromClipboard(Clipboard clipboard, DataFlavor dataFlavor) {
        Transferable transferable = clipboard.getContents(null);
        if (transferable.isDataFlavorSupported(dataFlavor)) {
            return clipboard.getContents(dataFlavor);
        }
        else return null;
    }

    public static DataFlavor getClipboardContentDataFlavor(Clipboard clipboard) {
        Transferable transferable = clipboard.getContents(null);
        if (transferable == null) return null;
        if (transferable.isDataFlavorSupported(URIListSelection.uriListFlavor)) {
            return URIListSelection.uriListFlavor;
        }
        else if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            return DataFlavor.javaFileListFlavor;
        }
        else if (transferable.isDataFlavorSupported(DataFlavor.selectionHtmlFlavor)) {
            return DataFlavor.selectionHtmlFlavor;
        }
        else if (transferable.isDataFlavorSupported(DataFlavor.fragmentHtmlFlavor)) {
            return DataFlavor.fragmentHtmlFlavor;
        }
        else if (transferable.isDataFlavorSupported(DataFlavor.allHtmlFlavor)) {
            return DataFlavor.allHtmlFlavor;
        }
        else if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return DataFlavor.stringFlavor;
        }
        else return null;
    }

    public static int toNotcuteContentType(DataFlavor dataFlavor) {
        if (dataFlavor == null) return -1;
        else if (dataFlavor.equals(DataFlavor.stringFlavor)) {
            return PLAIN_TEXT;
        }
        if (dataFlavor.equals(DataFlavor.allHtmlFlavor) || dataFlavor.equals(DataFlavor.fragmentHtmlFlavor) ||
                dataFlavor.equals(DataFlavor.selectionHtmlFlavor)) {
            return HTML_TEXT;
        }
        else if (dataFlavor.equals(URIListSelection.uriListFlavor) || dataFlavor.equals(DataFlavor.javaFileListFlavor)) {
            return URI_LIST;
        }
        else return -1;
    }

    private static URI[] files2URIs(final File[] files) {
        final URI[] uris = new URI[files.length];
        for (int i = 0; i < uris.length; i ++) {
            uris[i] = files[i].toURI();
        }
        return uris;
    }

}
