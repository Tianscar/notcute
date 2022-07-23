package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Font;
import com.ansdoship.a3wt.graphics.A3Graphics;

import java.awt.Image;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    public static boolean createFileIfNotExist(File file) {
        try {
            if (file.exists()) return true;
            else {
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    if (!parentFile.mkdirs()) return false;
                }
                return file.createNewFile();
            }
        }
        catch (IOException e) {
            return false;
        }
    }

    public static boolean createFileIfNotExistNIO(Path path) {
        try {
            if (!Files.exists(path)) {
                Path parentPath = path.getParent();
                if (!Files.exists(parentPath)) {
                    Files.createDirectories(parentPath);
                }
                Files.createFile(path);
            }
            return true;
        }
        catch (IOException e) {
            return false;
        }
    }

    public static int AWTFontStyle2FontStyle(int style) {
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

    public static int fontStyle2AWTFontStyle(int style) {
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

}
