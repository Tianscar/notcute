package io.notcute.ui.awt;

import io.notcute.g2d.awt.AWTImage;
import io.notcute.g2d.Image;
import io.notcute.input.Input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

final class Util {

    private Util() {
        throw new UnsupportedOperationException();
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

    public static List<BufferedImage> toAWTBufferedImages(Image... images) {
        List<BufferedImage> result = new ArrayList<>(images.length);
        for (Image image : images) {
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

    public static Image[] toNotcuteImages(List<java.awt.Image> images) {
        Image[] result = new Image[images.size()];
        for (int i = 0; i < result.length; i ++) {
            result[i] = new AWTImage(getBufferedImage(images.get(i)));
        }
        return result;
    }

}
