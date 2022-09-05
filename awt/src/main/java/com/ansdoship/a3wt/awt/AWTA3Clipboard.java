package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.app.A3Clipboard;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;

import static com.ansdoship.a3wt.awt.A3AWTUtils.getPlainTextFromClipboard;
import static com.ansdoship.a3wt.awt.A3AWTUtils.putPlainTextToClipboard;
import static com.ansdoship.a3wt.awt.A3AWTUtils.getHTMLTextFromClipboard;
import static com.ansdoship.a3wt.awt.A3AWTUtils.putHTMLTextToClipboard;
import static com.ansdoship.a3wt.awt.A3AWTUtils.getFilesFromClipboard;
import static com.ansdoship.a3wt.awt.A3AWTUtils.putFilesToClipboard;
import static com.ansdoship.a3wt.awt.A3AWTUtils.getClipboardContentDataFlavor;
import static com.ansdoship.a3wt.awt.A3AWTUtils.dataFlavor2ContentType;
import static com.ansdoship.a3wt.awt.A3AWTUtils.putContentsToClipboard;
import static com.ansdoship.a3wt.awt.A3AWTUtils.getContentsFromClipboard;

public class AWTA3Clipboard implements A3Clipboard {

    @Override
    public int getContentType() {
        return dataFlavor2ContentType(getDataFlavor());
    }

    public DataFlavor getDataFlavor() {
        return getClipboardContentDataFlavor(Toolkit.getDefaultToolkit().getSystemClipboard());
    }

    public void setContents(final Transferable contents) {
        putContentsToClipboard(Toolkit.getDefaultToolkit().getSystemClipboard(), contents);
    }

    public Object getContents(final DataFlavor dataFlavor) {
        return getContentsFromClipboard(Toolkit.getDefaultToolkit().getSystemClipboard(), dataFlavor);
    }

    @Override
    public void setPlainText(final CharSequence text) {
        putPlainTextToClipboard(Toolkit.getDefaultToolkit().getSystemClipboard(), text);
    }

    @Override
    public CharSequence getPlainText() {
        try {
            return getPlainTextFromClipboard(Toolkit.getDefaultToolkit().getSystemClipboard());
        } catch (IOException | UnsupportedFlavorException ignored) {
            return null;
        }
    }

    @Override
    public void setHTMLText(final String html) {
        putHTMLTextToClipboard(Toolkit.getDefaultToolkit().getSystemClipboard(), html);
    }

    @Override
    public String getHTMLText() {
        try {
            return getHTMLTextFromClipboard(Toolkit.getDefaultToolkit().getSystemClipboard());
        } catch (IOException | UnsupportedFlavorException e) {
            return null;
        }
    }

    @Override
    public void setFiles(final File[] files) {
        putFilesToClipboard(Toolkit.getDefaultToolkit().getSystemClipboard(), files);
    }

    @Override
    public File[] getFiles() {
        try {
            return getFilesFromClipboard(Toolkit.getDefaultToolkit().getSystemClipboard());
        } catch (IOException | UnsupportedFlavorException e) {
            return null;
        }
    }

}
