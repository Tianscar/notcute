package com.ansdoship.a3wt.awt;

import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;

public class A3AWTUtils {

    private A3AWTUtils(){}

    public static BufferedImage copyBufferedImage(BufferedImage source) {
        ColorModel cm = source.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = source.copyData(source.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public static BufferedImage copyImage(Image source) {
        if (source instanceof BufferedImage) return copyBufferedImage((BufferedImage) source);

        source.flush();
        BufferedImage result = new BufferedImage(source.getWidth(null), source.getHeight(null),
                getColorModel(source).hasAlpha() ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
        Graphics2D g = result.createGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return result;
    }

    public static ColorModel getColorModel(Image image) {
        if (image instanceof BufferedImage) return ((BufferedImage)image).getColorModel();

        PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (InterruptedException ignored) {
        }
        return pg.getColorModel();
    }

}
