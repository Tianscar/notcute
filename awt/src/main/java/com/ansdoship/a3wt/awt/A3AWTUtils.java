package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.app.A3Clipboard;
import com.ansdoship.a3wt.graphics.A3Cursor;
import com.ansdoship.a3wt.graphics.A3Font;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.input.A3InputListener;
import com.ansdoship.a3wt.util.A3Math;

import java.awt.Desktop;
import java.awt.Image;
import java.awt.DisplayMode;
import java.awt.Toolkit;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Window;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ansdoship.a3wt.util.A3Arrays.copy;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotEmpty;
import static com.ansdoship.a3wt.util.A3Files.files2URIs;

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
        final Graphics2D g2d = result.createGraphics();
        try {
            g2d.drawImage(source, 0, 0, null);
        }
        finally {
            g2d.dispose();
        }
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
            case A3Graphics.Join.MITER:
                return BasicStroke.JOIN_MITER;
            case A3Graphics.Join.ROUND:
                return BasicStroke.JOIN_ROUND;
            case A3Graphics.Join.BEVEL:
                return BasicStroke.JOIN_BEVEL;
            default:
                return -1;
        }
    }

    public static int strokeCap2BasicStrokeCap(final int cap) {
        switch (cap) {
            case A3Graphics.Cap.BUTT:
                return BasicStroke.CAP_BUTT;
            case A3Graphics.Cap.ROUND:
                return BasicStroke.CAP_ROUND;
            case A3Graphics.Cap.SQUARE:
                return BasicStroke.CAP_SQUARE;
            default:
                return -1;
        }
    }

    public static int basicStrokeJoin2StrokeJoin(final int join) {
        switch (join) {
            case BasicStroke.JOIN_MITER:
                return A3Graphics.Join.MITER;
            case BasicStroke.JOIN_ROUND:
                return A3Graphics.Join.ROUND;
            case BasicStroke.JOIN_BEVEL:
                return A3Graphics.Join.BEVEL;
            default:
                return -1;
        }
    }

    public static int basicStrokeCap2StrokeCap(final int cap) {
        switch (cap) {
            case BasicStroke.CAP_BUTT:
                return A3Graphics.Cap.BUTT;
            case BasicStroke.CAP_ROUND:
                return A3Graphics.Cap.ROUND;
            case BasicStroke.CAP_SQUARE:
                return A3Graphics.Cap.SQUARE;
            default:
                return -1;
        }
    }

    public static int awtFontStyle2FontStyle(final int style) {
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

    public static int fontStyle2awtFontStyle(final int style) {
        switch (style) {
            case A3Font.Style.NORMAL:
                return Font.PLAIN;
            case A3Font.Style.BOLD:
                return Font.BOLD;
            case A3Font.Style.ITALIC:
                return Font.ITALIC;
            case A3Font.Style.BOLD_ITALIC:
                return Font.BOLD | Font.ITALIC;
            default:
                return -1;
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
            case MouseEvent.BUTTON1:
                return A3InputListener.Button.LEFT;
            case MouseEvent.BUTTON2:
                return A3InputListener.Button.MIDDLE;
            case MouseEvent.BUTTON3:
                return A3InputListener.Button.RIGHT;
            default:
                return -1;
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
        amount = -amount;
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

    public static void putURIsToClipboard(final Clipboard clipboard, final URI[] uris) {
        checkArgNotNull(clipboard, "clipboard");
        checkArgNotNull(uris, "uris");
        clipboard.setContents(new URIListSelection(Arrays.asList(uris)), null);
    }

    public static URI[] getURIsFromClipboard(final Clipboard clipboard) throws IOException, UnsupportedFlavorException {
        checkArgNotNull(clipboard, "clipboard");
        final Transferable transferable = clipboard.getContents(null);
        if (transferable.isDataFlavorSupported(URIListSelection.uriListFlavor)) {
            return ((List<URI>) transferable.getTransferData(URIListSelection.uriListFlavor)).toArray(new URI[0]);
        }
        else if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            return files2URIs(((List<File>)transferable.getTransferData(DataFlavor.javaFileListFlavor)).toArray(new File[0]));
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

    public static int dataFlavor2ContentType(final DataFlavor dataFlavor) {
        if (dataFlavor == null) return -1;
        else if (dataFlavor.equals(DataFlavor.stringFlavor)) {
            return A3Clipboard.ContentType.PLAIN_TEXT;
        }
        if (dataFlavor.equals(DataFlavor.allHtmlFlavor) || dataFlavor.equals(DataFlavor.fragmentHtmlFlavor) ||
        dataFlavor.equals(DataFlavor.selectionHtmlFlavor)) {
            return A3Clipboard.ContentType.HTML_TEXT;
        }
        else if (dataFlavor.equals(URIListSelection.uriListFlavor) || dataFlavor.equals(DataFlavor.javaFileListFlavor)) {
            return A3Clipboard.ContentType.URI_LIST;
        }
        else return -1;
    }

    public static DataFlavor contentType2dataFlavor(final int contentType) {
        switch (contentType) {
            case A3Clipboard.ContentType.PLAIN_TEXT:
                return DataFlavor.stringFlavor;
            case A3Clipboard.ContentType.HTML_TEXT:
                return DataFlavor.allHtmlFlavor;
            case A3Clipboard.ContentType.URI_LIST:
                return URIListSelection.uriListFlavor;
            default:
                return null;
        }
    }

    public static int awtKeyLocation2KeyLocation(final int keyLocation) {
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

    public static int keyLocation2awtKeyLocation(final int keyLocation) {
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
            result = listener.keyDown(e.getExtendedKeyCode(), awtKeyLocation2KeyLocation(e.getKeyLocation()));
            if (result) break;
        }
    }

    public static void commonKeyReleased(final List<A3InputListener> listeners, final KeyEvent e) {
        checkArgNotNull(listeners, "listeners");
        checkArgNotNull(e, "event");
        boolean result;
        for (A3InputListener listener : listeners) {
            result = listener.keyUp(e.getExtendedKeyCode(), awtKeyLocation2KeyLocation(e.getKeyLocation()));
            if (result) break;
        }
    }

    private static final Cursor GONE_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(
            Toolkit.getDefaultToolkit().getImage(A3AWTUtils.class.getResource("")),
            new Point(0, 0), "A3WT " + AWTA3Platform.BACKEND_NAME + " Gone Cursor");
    private static final Cursor DEFAULT_CURSOR = Cursor.getDefaultCursor();

    public static Cursor getGoneCursor() {
        return GONE_CURSOR;
    }

    public static Cursor getDefaultCursor() {
        return DEFAULT_CURSOR;
    }

    public static Cursor createCustomCursor(final Image image, final int hotSpotX, final int hotSpotY, final String name) {
        checkArgNotNull(image, "image");
        checkArgNotNull(name, "name");
        final Point hotSpot = new Point(hotSpotX, hotSpotY);
        return AWTA3Platform.isX11() ? new com.ansdoship.a3wt.awt.x11.ColorfulXCursor(image, hotSpot, name) :
                Toolkit.getDefaultToolkit().createCustomCursor(image, hotSpot, name);
    }

    public static int awtCursorType2CursorType(final int type) {
        switch (type) {
            case Cursor.DEFAULT_CURSOR:
                return A3Cursor.Type.ARROW;
            case Cursor.CROSSHAIR_CURSOR:
                return A3Cursor.Type.CROSSHAIR;
            case Cursor.TEXT_CURSOR:
                return A3Cursor.Type.IBEAM;
            case Cursor.WAIT_CURSOR:
                return A3Cursor.Type.WAIT;
            case Cursor.SW_RESIZE_CURSOR:
                return A3Cursor.Type.RESIZE_SW;
            case Cursor.SE_RESIZE_CURSOR:
                return A3Cursor.Type.RESIZE_SE;
            case Cursor.NW_RESIZE_CURSOR:
                return A3Cursor.Type.RESIZE_NW;
            case Cursor.NE_RESIZE_CURSOR:
                return A3Cursor.Type.RESIZE_NE;
            case Cursor.N_RESIZE_CURSOR:
                return A3Cursor.Type.RESIZE_N;
            case Cursor.S_RESIZE_CURSOR:
                return A3Cursor.Type.RESIZE_S;
            case Cursor.W_RESIZE_CURSOR:
                return A3Cursor.Type.RESIZE_W;
            case Cursor.E_RESIZE_CURSOR:
                return A3Cursor.Type.RESIZE_E;
            case Cursor.HAND_CURSOR:
                return A3Cursor.Type.HAND;
            case Cursor.MOVE_CURSOR:
                return A3Cursor.Type.MOVE;
            default:
                return -1;
        }
    }

    public static int cursorType2awtCursorType(final int type) {
        switch (type) {
            case A3Cursor.Type.ARROW:
                return Cursor.DEFAULT_CURSOR;
            case A3Cursor.Type.CROSSHAIR:
                return Cursor.CROSSHAIR_CURSOR;
            case A3Cursor.Type.IBEAM:
                return Cursor.TEXT_CURSOR;
            case A3Cursor.Type.WAIT:
                return Cursor.WAIT_CURSOR;
            case A3Cursor.Type.RESIZE_SW:
                return Cursor.SW_RESIZE_CURSOR;
            case A3Cursor.Type.RESIZE_SE:
                return Cursor.SE_RESIZE_CURSOR;
            case A3Cursor.Type.RESIZE_NW:
                return Cursor.NW_RESIZE_CURSOR;
            case A3Cursor.Type.RESIZE_NE:
                return Cursor.NE_RESIZE_CURSOR;
            case A3Cursor.Type.RESIZE_N:
                return Cursor.N_RESIZE_CURSOR;
            case A3Cursor.Type.RESIZE_S:
                return Cursor.S_RESIZE_CURSOR;
            case A3Cursor.Type.RESIZE_W:
                return Cursor.W_RESIZE_CURSOR;
            case A3Cursor.Type.RESIZE_E:
                return Cursor.E_RESIZE_CURSOR;
            case A3Cursor.Type.HAND:
                return Cursor.HAND_CURSOR;
            case A3Cursor.Type.MOVE:
                return Cursor.MOVE_CURSOR;
            default:
                return -1;
        }
    }

    public static BufferedImage getAlignedImage(final BufferedImage source, final int alignX, final int alignY, final int width, final int height) {
        checkArgNotNull(source, "source");
        if (source.getWidth() == width && source.getHeight() == height) return source;
        final BufferedImage result = new BufferedImage(width, height, source.getType());
        final Graphics2D g2d = result.createGraphics();
        try {
            g2d.drawImage(source, alignX, alignY, null);
        }
        finally {
            g2d.dispose();
        }
        return result;
    }

    public static BufferedImage getImage(final BufferedImage source, final int type) {
        checkArgNotNull(source, "source");
        if (source.getType() == type) return source;
        final BufferedImage result = new BufferedImage(source.getWidth(), source.getHeight(), type);
        final Graphics2D g2d = result.createGraphics();
        try {
            g2d.drawImage(source, 0, 0, null);
        }
        finally {
            g2d.dispose();
        }
        return result;
    }

    public static BufferedImage getARGB8888Image(final BufferedImage source) {
        return getImage(source, BufferedImage.TYPE_INT_ARGB);
    }

    public static BufferedImage getRGB565Image(final BufferedImage source) {
        return getImage(source, BufferedImage.TYPE_USHORT_565_RGB);
    }

    public static List<BufferedImage> A3Images2BufferedImages(final List<A3Image> images) {
        checkArgNotEmpty(images, "images");
        final List<BufferedImage> result = new ArrayList<>(images.size());
        for (final A3Image image : images) {
            result.add(((AWTA3Image)image).bufferedImage);
        }
        return result;
    }

    public static List<A3Image> awtImages2A3Images(final List<Image> images) {
        checkArgNotEmpty(images, "images");
        final List<A3Image> result = new ArrayList<>(images.size());
        for (final Image image : images) {
            result.add(new AWTA3Image(getBufferedImage(image)));
        }
        return result;
    }

    public static BufferedImage getBufferedImage(final Image image) {
        checkArgNotNull(image, "image");
        if (image instanceof BufferedImage) return (BufferedImage) image;
        else return copyImage(image);
    }

    public static int[][] getScreenSizes(final GraphicsDevice device) {
        checkArgNotNull(device, "device");
        final DisplayMode[] modes = device.getDisplayModes();
        final int[][] result = new int[2][modes.length];
        for (int i = 0; i < modes.length; i ++) {
            result[0][i] = modes[i].getWidth();
            result[1][i] = modes[i].getHeight();
        }
        return result;
    }

    public static int[] getMinScreenSize(final GraphicsDevice device) {
        final int[][] screenSizes = getScreenSizes(device);
        return new int[] { A3Math.min(screenSizes[0]), A3Math.min(screenSizes[1]) };
    }

    public static int[] getMaxScreenSize(final GraphicsDevice device) {
        final int[][] screenSizes = getScreenSizes(device);
        return new int[] { A3Math.max(screenSizes[0]), A3Math.max(screenSizes[1]) };
    }

    private static final int[][] SCREEN_SIZES = getScreenSizes(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
    private static final int[] MIN_SCREEN_SIZE = getMinScreenSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
    private static final int[] MAX_SCREEN_SIZE = getMaxScreenSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());

    public static int[][] getScreenSizes() {
        return copy(SCREEN_SIZES);
    }

    public static int[] getMinScreenSize() {
        return copy(MIN_SCREEN_SIZE);
    }

    public static int getMinScreenWidth() {
        return MIN_SCREEN_SIZE[0];
    }

    public static int getMinScreenHeight() {
        return MIN_SCREEN_SIZE[1];
    }

    public static int[] getMaxScreenSize() {
        return copy(MAX_SCREEN_SIZE);
    }

    public static int getMaxScreenWidth() {
        return MAX_SCREEN_SIZE[0];
    }

    public static int getMaxScreenHeight() {
        return MAX_SCREEN_SIZE[1];
    }

    private static final Font DEFAULT_FONT = Font.decode(null);

    public static Font getDefaultFont() {
        return DEFAULT_FONT;
    }

    public static int bufferedImageType2ImageType(final int type) {
        switch (type) {
            case BufferedImage.TYPE_INT_ARGB:
                return A3Image.Type.ARGB_8888;
            case BufferedImage.TYPE_USHORT_565_RGB:
                return A3Image.Type.RGB_565;
            default:
                return -1;
        }
    }

    public static int imageType2BufferedImageType(final int type) {
        switch (type) {
            case A3Image.Type.ARGB_8888:
                return BufferedImage.TYPE_INT_ARGB;
            case A3Image.Type.RGB_565:
                return BufferedImage.TYPE_USHORT_565_RGB;
            default:
                return -1;
        }
    }

    public static Rectangle2D.Float floatRectangle2D(final Rectangle2D rectangle2D) {
        checkArgNotNull(rectangle2D, "rectangle2D");
        if (rectangle2D instanceof Rectangle2D.Float) return (Rectangle2D.Float) rectangle2D;
        else return new Rectangle2D.Float((float) rectangle2D.getX(), (float) rectangle2D.getY(),
                (float) rectangle2D.getWidth(), (float) rectangle2D.getHeight());
    }

    public static Path2D.Float floatPath2D(final Path2D path2D) {
        checkArgNotNull(path2D, "path2D");
        if (path2D instanceof Path2D.Float) return (Path2D.Float) path2D;
        else return new Path2D.Float(path2D);
    }

    public static boolean browse(final URI uri) {
        checkArgNotNull(uri, "uri");
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(uri);
                return true;
            }
            else if (AWTA3Platform.isWindows()) {
                new ProcessBuilder().command("cmd", "/c", "start", uri.toString()).start();
                return true;
            }
            else if (AWTA3Platform.isMac()) {
                new ProcessBuilder().command("open", uri.toString()).start();
                return true;
            }
            else {
                new ProcessBuilder().command("xdg-open", uri.toString()).start();
                return true;
            }
        }
        catch (final IOException e) {
            return false;
        }
    }

    public static boolean open(final File file) {
        checkArgNotNull(file, "file");
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                Desktop.getDesktop().open(file);
                return true;
            }
            else if (AWTA3Platform.isWindows()) {
                new ProcessBuilder().command("cmd", "/c", "start", file.getAbsolutePath()).start();
                return true;
            }
            else if (AWTA3Platform.isMac()) {
                new ProcessBuilder().command("open", file.getAbsolutePath()).start();
                return true;
            }
            else {
                new ProcessBuilder().command("xdg-open", file.getAbsolutePath()).start();
                return true;
            }
        }
        catch (final IOException e) {
            return false;
        }
    }

}
