package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Graphics;

import java.awt.Image;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public static String currentFormattedTime(String pattern) {
        LocalDateTime nowDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTimeFormatter.format(nowDateTime);
    }

    public static int strokeJoin2BasicStrokeJoin(int join) {
        switch (join) {
            case A3Graphics.Join.MITER: default:
                return BasicStroke.JOIN_MITER;
            case A3Graphics.Join.ROUND:
                return BasicStroke.JOIN_ROUND;
            case A3Graphics.Join.BEVEL:
                return BasicStroke.JOIN_BEVEL;
        }
    }

    public static int strokeCap2BasicStrokeCap(int cap) {
        switch (cap) {
            case A3Graphics.Cap.BUTT: default:
                return BasicStroke.CAP_BUTT;
            case A3Graphics.Cap.ROUND:
                return BasicStroke.CAP_ROUND;
            case A3Graphics.Cap.SQUARE:
                return BasicStroke.CAP_SQUARE;
        }
    }

    public static int basicStrokeJoin2StrokeJoin(int join) {
        switch (join) {
            case BasicStroke.JOIN_MITER: default:
                return A3Graphics.Join.MITER;
            case BasicStroke.JOIN_ROUND:
                return A3Graphics.Join.ROUND;
            case BasicStroke.JOIN_BEVEL:
                return A3Graphics.Join.BEVEL;
        }
    }

    public static int basicStrokeCap2StrokeCap(int cap) {
        switch (cap) {
            case BasicStroke.CAP_BUTT: default:
                return A3Graphics.Cap.BUTT;
            case BasicStroke.CAP_ROUND:
                return A3Graphics.Cap.ROUND;
            case BasicStroke.CAP_SQUARE:
                return A3Graphics.Cap.SQUARE;
        }
    }

}
