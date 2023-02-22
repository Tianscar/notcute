package io.notcute.internal.awt;

import io.notcute.app.FileChooser;
import io.notcute.app.awt.AWTPlatform;
import io.notcute.app.awt.URIListSelection;
import io.notcute.g2d.awt.AWTImage;
import io.notcute.g2d.geom.Rectangle;
import io.notcute.input.Input;
import io.notcute.util.FileUtils;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static io.notcute.app.Clipboard.ContentType.*;

public final class AWTUIUtils {

    private AWTUIUtils() {
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
            return FileUtils.files2URIs(((List<File>)transferable.getTransferData(DataFlavor.javaFileListFlavor)).toArray(new File[0]));
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

    public static int toAWTFileDialogMode(int mode) {
        switch (mode) {
            case FileChooser.Mode.LOAD:
                return FileDialog.LOAD;
            case FileChooser.Mode.SAVE:
                return FileDialog.SAVE;
            default:
                throw new IllegalArgumentException("Invalid mode: " + mode);
        }
    }

    public static int toCursorFactoryCursorType(int type) {
        return type;
    }

    public static int toNotcuteButton(int button) {
        switch (button) {
            case MouseEvent.BUTTON1:
                return Input.Button.LEFT;
            case MouseEvent.BUTTON2:
                return Input.Button.MIDDLE;
            case MouseEvent.BUTTON3:
                return Input.Button.RIGHT;
            default:
                throw new IllegalArgumentException("Invalid button: " + button);
        }
    }

    public static int toNotcuteScrollType(int scrollType) {
        switch (scrollType) {
            case MouseWheelEvent.WHEEL_UNIT_SCROLL:
                return Input.ScrollType.UNIT;
            case MouseWheelEvent.WHEEL_BLOCK_SCROLL:
                return Input.ScrollType.BLOCK;
            default:
                throw new IllegalArgumentException("Invalid scrollType: " + scrollType);
        }
    }

    public static int toNotcuteKeyLocation(int keyLocation) {
        switch (keyLocation) {
            case KeyEvent.KEY_LOCATION_STANDARD:
                return Input.KeyLocation.STANDARD;
            case KeyEvent.KEY_LOCATION_LEFT:
                return Input.KeyLocation.LEFT;
            case KeyEvent.KEY_LOCATION_RIGHT:
                return Input.KeyLocation.RIGHT;
            case KeyEvent.KEY_LOCATION_NUMPAD:
                return Input.KeyLocation.NUMPAD;
            default:
                throw new IllegalArgumentException("Invalid keyLocation: " + keyLocation);
        }
    }

    public static List<BufferedImage> toAWTBufferedImages(io.notcute.g2d.Image... images) {
        List<BufferedImage> result = new ArrayList<>(images.length);
        for (io.notcute.g2d.Image image : images) {
            result.add(((AWTImage) image).getBufferedImage());
        }
        return result;
    }

    public static ColorModel getColorModel(java.awt.Image image) {
        Objects.requireNonNull(image);
        if (image instanceof BufferedImage) return ((BufferedImage)image).getColorModel();

        PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (InterruptedException ignored) {
        }
        return pg.getColorModel();
    }

    public static BufferedImage copyBufferedImage(BufferedImage source) {
        Objects.requireNonNull(source);
        ColorModel cm = source.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = source.copyData(source.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public static BufferedImage copyImage(java.awt.Image source) {
        Objects.requireNonNull(source);
        if (source instanceof BufferedImage) return copyBufferedImage((BufferedImage) source);

        source.flush();
        BufferedImage result = new BufferedImage(source.getWidth(null), source.getHeight(null),
                getColorModel(source).hasAlpha() ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = result.createGraphics();
        try {
            g2d.drawImage(source, 0, 0, null);
        }
        finally {
            g2d.dispose();
        }
        return result;
    }

    public static BufferedImage getBufferedImage(java.awt.Image image) {
        if (image instanceof BufferedImage) return (BufferedImage) image;
        else return copyImage(image);
    }

    public static io.notcute.g2d.Image[] toNotcuteImages(List<java.awt.Image> images) {
        io.notcute.g2d.Image[] result = new io.notcute.g2d.Image[images.size()];
        for (int i = 0; i < result.length; i ++) {
            result[i] = new AWTImage(getBufferedImage(images.get(i)));
        }
        return result;
    }

    public static Rectangle toNotcuteRectangle(Insets insets) {
        if (insets == null) return null;
        else return new Rectangle(insets.left, insets.top, insets.right - insets.left, insets.bottom - insets.top);
    }

    public static double getDPIScale(Component component) {
        if (component != null) {
            try {
                if (AWTPlatform.isWindows) return ((Integer) Class.forName("io.notcute.internal.awt.win32.Win32Utils")
                        .getDeclaredMethod("getDpiForComponent", Component.class).invoke(null, component)) /
                        (double) component.getToolkit().getScreenResolution();
                else if (AWTPlatform.isMac) return (Integer) Class.forName("io.notcute.internal.awt.macosx.MacOSXUtils")
                        .getDeclaredMethod("getScaleFactor").invoke(null);
                else return (Double) Class.forName("io.notcute.internal.awt.X11.X11Utils")
                            .getDeclaredMethod("getXFontDPI").invoke(null) /
                            component.getToolkit().getScreenResolution();
            }
            catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException ignored) {
            }
        }
        return 1.0;
    }

    public static double getDPI(Component component) {
        if (component != null) {
             try {
                if (AWTPlatform.isWindows) return ((Integer) Class.forName("io.notcute.internal.awt.win32.Win32Utils")
                        .getDeclaredMethod("getDpiForComponent", Component.class).invoke(null, component));
                else if (AWTPlatform.isMac) return (Integer) Class.forName("io.notcute.internal.awt.macosx.MacOSXUtils")
                        .getDeclaredMethod("getScaleFactor").invoke(null) * component.getToolkit().getScreenResolution();
                else return (Double) Class.forName("io.notcute.internal.awt.X11.X11Utils")
                            .getDeclaredMethod("getXFontDPI").invoke(null);
            }
            catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException ignored) {
            }
        }
        return Toolkit.getDefaultToolkit().getScreenResolution();
    }

}
