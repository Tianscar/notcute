package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.app.A3Clipboard;
import com.ansdoship.a3wt.graphics.A3Font;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.input.A3InputListener;

import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.awt.Window;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.Image;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static com.ansdoship.a3wt.util.A3Asserts.checkArgNotNull;
import static com.ansdoship.a3wt.util.A3Asserts.checkArgNotEmpty;

public class A3AWTUtils {

    private A3AWTUtils(){}

    public static BufferedImage copyBufferedImage(final BufferedImage source) {
        checkArgNotNull(source, "source");
        final ColorModel cm = source.getColorModel();
        final boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        final WritableRaster raster = source.copyData(source.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public static BufferedImage copyImage(final Image source) {
        checkArgNotNull(source, "source");
        if (source instanceof BufferedImage) return copyBufferedImage((BufferedImage) source);

        source.flush();
        final BufferedImage result = new BufferedImage(source.getWidth(null), source.getHeight(null),
                getColorModel(source).hasAlpha() ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
        final Graphics2D g = result.createGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return result;
    }

    public static ColorModel getColorModel(final Image image) {
        checkArgNotNull(image, "image");
        if (image instanceof BufferedImage) return ((BufferedImage)image).getColorModel();

        final PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (final InterruptedException ignored) {
        }
        return pg.getColorModel();
    }

    public static String currentFormattedTime(final String pattern) {
        checkArgNotEmpty(pattern);
        final LocalDateTime nowDateTime = LocalDateTime.now();
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTimeFormatter.format(nowDateTime);
    }

    public static int strokeJoin2BasicStrokeJoin(final int join) {
        switch (join) {
            case A3Graphics.Join.MITER: default:
                return BasicStroke.JOIN_MITER;
            case A3Graphics.Join.ROUND:
                return BasicStroke.JOIN_ROUND;
            case A3Graphics.Join.BEVEL:
                return BasicStroke.JOIN_BEVEL;
        }
    }

    public static int strokeCap2BasicStrokeCap(final int cap) {
        switch (cap) {
            case A3Graphics.Cap.BUTT: default:
                return BasicStroke.CAP_BUTT;
            case A3Graphics.Cap.ROUND:
                return BasicStroke.CAP_ROUND;
            case A3Graphics.Cap.SQUARE:
                return BasicStroke.CAP_SQUARE;
        }
    }

    public static int basicStrokeJoin2StrokeJoin(final int join) {
        switch (join) {
            case BasicStroke.JOIN_MITER: default:
                return A3Graphics.Join.MITER;
            case BasicStroke.JOIN_ROUND:
                return A3Graphics.Join.ROUND;
            case BasicStroke.JOIN_BEVEL:
                return A3Graphics.Join.BEVEL;
        }
    }

    public static int basicStrokeCap2StrokeCap(final int cap) {
        switch (cap) {
            case BasicStroke.CAP_BUTT: default:
                return A3Graphics.Cap.BUTT;
            case BasicStroke.CAP_ROUND:
                return A3Graphics.Cap.ROUND;
            case BasicStroke.CAP_SQUARE:
                return A3Graphics.Cap.SQUARE;
        }
    }

    public static int AWTFontStyle2FontStyle(final int style) {
        if (style == 0) return Font.PLAIN;
        boolean bold = false;
        boolean italic = false;
        if ((style & Font.BOLD) != 0) bold = true;
        if ((style & Font.ITALIC) != 0) italic = true;
        if (bold && italic) return A3Font.Style.BOLD_ITALIC;
        else if (bold) return A3Font.Style.BOLD;
        else if (italic) return A3Font.Style.ITALIC;
        else return Font.PLAIN;
    }

    public static int fontStyle2AWTFontStyle(final int style) {
        switch (style) {
            case A3Font.Style.NORMAL: default:
                return Font.PLAIN;
            case A3Font.Style.BOLD:
                return Font.BOLD;
            case A3Font.Style.ITALIC:
                return Font.ITALIC;
            case A3Font.Style.BOLD_ITALIC:
                return Font.BOLD | Font.ITALIC;
        }
    }

    public static Font readFont(final File input) throws IOException {
        checkArgNotNull(input, "input");
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, input);
        } catch (FontFormatException e) {
            try {
                font = Font.createFont(Font.TYPE1_FONT, input);
            } catch (FontFormatException ignored) {
            }
        }
        return font;
    }

    public static Font readFont(final InputStream input) throws IOException {
        checkArgNotNull(input, "input");
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, input);
        } catch (FontFormatException e) {
            try {
                font = Font.createFont(Font.TYPE1_FONT, input);
            } catch (FontFormatException ignored) {
            }
        }
        return font;
    }

    public static int mouseEventButton2Button(final int button) {
        switch (button) {
            case MouseEvent.BUTTON1: default:
                return A3InputListener.Button.LEFT;
            case MouseEvent.BUTTON2:
                return A3InputListener.Button.MIDDLE;
            case MouseEvent.BUTTON3:
                return A3InputListener.Button.RIGHT;
        }
    }

    public static void commonMousePressed(final List<A3InputListener> listeners, final MouseEvent e) {
        checkArgNotNull(listeners, "listeners");
        checkArgNotNull(e, "event");
        boolean result;
        final int x = e.getX();
        final int y = e.getY();
        final int pointer = e.getClickCount() - 1;
        final int button = mouseEventButton2Button(e.getButton());
        for (A3InputListener listener : listeners) {
            result = listener.pointerDown(x, y, pointer, button);
            if (result) break;
        }
    }

    public static void commonMouseReleased(final List<A3InputListener> listeners, final MouseEvent e) {
        checkArgNotNull(listeners, "listeners");
        checkArgNotNull(e, "event");
        boolean result;
        final int x = e.getX();
        final int y = e.getY();
        final int pointer = e.getClickCount() - 1;
        final int button = mouseEventButton2Button(e.getButton());
        for (A3InputListener listener : listeners) {
            result = listener.pointerUp(x, y, pointer, button);
            if (result) break;
        }
    }

    public static void commonMouseDragged(final List<A3InputListener> listeners, final MouseEvent e) {
        checkArgNotNull(listeners, "listeners");
        checkArgNotNull(e, "event");
        boolean result;
        final int x = e.getX();
        final int y = e.getY();
        for (A3InputListener listener : listeners) {
            result = listener.pointerDragged(x, y, 0);
            if (result) break;
        }
    }

    public static void commonMouseMoved(final List<A3InputListener> listeners, final MouseEvent e) {
        checkArgNotNull(listeners, "listeners");
        checkArgNotNull(e, "event");
        boolean result;
        final int x = e.getX();
        final int y = e.getY();
        for (A3InputListener listener : listeners) {
            result = listener.mouseMoved(x, y);
            if (result) break;
        }
    }

    public static void commonMouseEntered(final List<A3InputListener> listeners, final MouseEvent e) {
        checkArgNotNull(listeners, "listeners");
        checkArgNotNull(e, "event");
        boolean result;
        final int x = e.getX();
        final int y = e.getY();
        for (A3InputListener listener : listeners) {
            result = listener.mouseEntered(x, y);
            if (result) break;
        }
    }

    public static void commonMouseExited(final List<A3InputListener> listeners, final MouseEvent e) {
        checkArgNotNull(listeners, "listeners");
        checkArgNotNull(e, "event");
        boolean result;
        final int x = e.getX();
        final int y = e.getY();
        for (A3InputListener listener : listeners) {
            result = listener.mouseExited(x, y);
            if (result) break;
        }
    }

    public static int mouseWheelScrollType2ScrollType(final int scrollType) {
        switch (scrollType) {
            case MouseWheelEvent.WHEEL_UNIT_SCROLL:
                return A3InputListener.ScrollType.UNIT;
            case MouseWheelEvent.WHEEL_BLOCK_SCROLL:
                return A3InputListener.ScrollType.BLOCK;
        }
        return -1;
    }

    public static void commonMouseWheelMoved(final List<A3InputListener> listeners, final MouseWheelEvent e) {
        checkArgNotNull(listeners, "listeners");
        checkArgNotNull(e, "event");
        boolean result;
        final int scrollType = mouseWheelScrollType2ScrollType(e.getScrollType());
        float amount = 0;
        if (scrollType == A3InputListener.ScrollType.UNIT) {
            amount = (float) (Math.abs(e.getUnitsToScroll()) * e.getPreciseWheelRotation());
        }
        for (A3InputListener listener : listeners) {
            result = listener.mouseWheelScrolled(amount, scrollType);
            if (result) break;
        }
    }

    public static int getScreenWidth() {
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }

    public static int getScreenHeight() {
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    public static int getPPI() {
        return Toolkit.getDefaultToolkit().getScreenResolution();
    }

    public static float getDensity() {
        return getPPI() / (float) AWTA3Platform.BASELINE_PPI;
    }

    public static float getScaledDensity(final float scale) {
        return getDensity() * scale;
    }

    public static void setFullscreenWindow(final GraphicsDevice device, final Window window) {
        checkArgNotNull(device, "device");
        device.setFullScreenWindow(window);
    }

    public static void setFullscreenWindow(final Window window) {
        setFullscreenWindow(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(), window);
    }

    public static Window getFullscreenWindow(final GraphicsDevice device) {
        checkArgNotNull(device, "device");
        return device.getFullScreenWindow();
    }

    public static Window getFullscreenWindow() {
        return getFullscreenWindow(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
    }

    public static void putPlainTextToClipboard(final Clipboard clipboard, final CharSequence plainText) {
        checkArgNotNull(clipboard, "clipboard");
        checkArgNotNull(plainText, "plainText");
        final String plainTextString = plainText.toString();
        checkArgNotEmpty(plainTextString, "plainText");
        clipboard.setContents(new StringSelection(plainTextString), null);
    }

    public static CharSequence getPlainTextFromClipboard(final Clipboard clipboard) throws IOException, UnsupportedFlavorException {
        checkArgNotNull(clipboard, "clipboard");
        final Transferable transferable = clipboard.getContents(null);
        if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return (String) transferable.getTransferData(DataFlavor.stringFlavor);
        }
        else return null;
    }

    public static void putHTMLTextToClipboard(final Clipboard clipboard, final String HTMLText) {
        checkArgNotNull(clipboard, "clipboard");
        checkArgNotNull(HTMLText, "HTMLText");
        clipboard.setContents(new StringSelection(HTMLText), null);
    }

    public static String getHTMLTextFromClipboard(final Clipboard clipboard) throws IOException, UnsupportedFlavorException {
        checkArgNotNull(clipboard, "clipboard");
        final Transferable transferable = clipboard.getContents(null);
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

    public static void putFilesToClipboard(final Clipboard clipboard, final File[] files) {
        checkArgNotNull(clipboard, "clipboard");
        checkArgNotNull(files, "files");
        clipboard.setContents(new FileListSelection(Arrays.asList(files)), null);
    }

    public static File[] getFilesFromClipboard(final Clipboard clipboard) throws IOException, UnsupportedFlavorException {
        checkArgNotNull(clipboard, "clipboard");
        final Transferable transferable = clipboard.getContents(null);
        if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            return ((List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor)).toArray(new File[0]);
        }
        else return null;
    }

    public static void putContentsToClipboard(final Clipboard clipboard, final Transferable contents) {
        checkArgNotNull(clipboard, "clipboard");
        checkArgNotNull(contents, "contents");
        clipboard.setContents(contents, null);
    }

    public static Object getContentsFromClipboard(final Clipboard clipboard, final DataFlavor dataFlavor) {
        checkArgNotNull(clipboard, "clipboard");
        checkArgNotNull(dataFlavor, "dataFlavor");
        final Transferable transferable = clipboard.getContents(null);
        if (transferable.isDataFlavorSupported(dataFlavor)) {
            return clipboard.getContents(dataFlavor);
        }
        else return null;
    }

    public static DataFlavor getClipboardContentDataFlavor(final Clipboard clipboard) {
        checkArgNotNull(clipboard, "clipboard");
        final Transferable transferable = clipboard.getContents(null);
        if (transferable == null) return null;
        if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
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

    public static int dataFlavor2ContentType(final DataFlavor dataFlavor) {
        if (dataFlavor == null) return -1;
        else if (dataFlavor.equals(DataFlavor.stringFlavor)) {
            return A3Clipboard.ContentType.PLAIN_TEXT;
        }
        if (dataFlavor.equals(DataFlavor.allHtmlFlavor) || dataFlavor.equals(DataFlavor.fragmentHtmlFlavor) ||
        dataFlavor.equals(DataFlavor.selectionHtmlFlavor)) {
            return A3Clipboard.ContentType.HTML_TEXT;
        }
        else if (dataFlavor.equals(DataFlavor.javaFileListFlavor)) {
            return A3Clipboard.ContentType.FILE_LIST;
        }
        else return -1;
    }

    public static DataFlavor contentType2dataFlavor(final int contentType) {
        switch (contentType) {
            case A3Clipboard.ContentType.PLAIN_TEXT:
                return DataFlavor.stringFlavor;
            case A3Clipboard.ContentType.HTML_TEXT:
                return DataFlavor.allHtmlFlavor;
            case A3Clipboard.ContentType.FILE_LIST:
                return DataFlavor.javaFileListFlavor;
            default:
                return null;
        }
    }

    public static int AWTKeyLocation2KeyLocation(final int keyLocation) {
        switch (keyLocation) {
            case KeyEvent.KEY_LOCATION_STANDARD:
                return A3InputListener.KeyLocation.STANDARD;
            case KeyEvent.KEY_LOCATION_LEFT:
                return A3InputListener.KeyLocation.LEFT;
            case KeyEvent.KEY_LOCATION_RIGHT:
                return A3InputListener.KeyLocation.RIGHT;
            case KeyEvent.KEY_LOCATION_NUMPAD:
                return A3InputListener.KeyLocation.NUMPAD;
            default:
                return -1;
        }
    }

    public static int keyLocation2AWTKeyLocation(final int keyLocation) {
        switch (keyLocation) {
            case A3InputListener.KeyLocation.STANDARD:
                return KeyEvent.KEY_LOCATION_STANDARD;
            case A3InputListener.KeyLocation.LEFT:
                return KeyEvent.KEY_LOCATION_LEFT;
            case A3InputListener.KeyLocation.RIGHT:
                return KeyEvent.KEY_LOCATION_RIGHT;
            case A3InputListener.KeyLocation.NUMPAD:
                return KeyEvent.KEY_LOCATION_NUMPAD;
            default:
                return -1;
        }
    }

    public static void commonKeyTyped(final List<A3InputListener> listeners, final KeyEvent e) {
        checkArgNotNull(listeners, "listeners");
        checkArgNotNull(e, "event");
        boolean result;
        for (A3InputListener listener : listeners) {
            result = listener.keyTyped(e.getKeyChar());
            if (result) break;
        }
    }

    public static void commonKeyPressed(final List<A3InputListener> listeners, final KeyEvent e) {
        checkArgNotNull(listeners, "listeners");
        checkArgNotNull(e, "event");
        boolean result;
        for (A3InputListener listener : listeners) {
            result = listener.keyDown(e.getExtendedKeyCode(), AWTKeyLocation2KeyLocation(e.getKeyLocation()));
            if (result) break;
        }
    }

    public static void commonKeyReleased(final List<A3InputListener> listeners, final KeyEvent e) {
        checkArgNotNull(listeners, "listeners");
        checkArgNotNull(e, "event");
        boolean result;
        for (A3InputListener listener : listeners) {
            result = listener.keyUp(e.getExtendedKeyCode(), AWTKeyLocation2KeyLocation(e.getKeyLocation()));
            if (result) break;
        }
    }

}
