package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Font;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.graphics.A3GraphicsKit;
import com.ansdoship.a3wt.graphics.A3Path;

import java.awt.Font;
import java.awt.geom.Path2D;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class AWTA3GraphicsKit implements A3GraphicsKit {

    @Override
    public A3Image readImage(File input) throws IOException {
        return new AWTA3Image(ImageIO.read(input));
    }

    @Override
    public A3Image readImage(InputStream input) throws IOException {
        return new AWTA3Image(ImageIO.read(input));
    }

    @Override
    public A3Image readImage(URL input) throws IOException {
        return new AWTA3Image(ImageIO.read(input));
    }

    @Override
    public boolean writeImage(A3Image image, String formatName, File output) throws IOException {
        return ImageIO.write(((AWTA3Image)image).getBufferedImage(), formatName, output);
    }

    @Override
    public boolean writeImage(A3Image image, String formatName, OutputStream output) throws IOException {
        return ImageIO.write(((AWTA3Image)image).getBufferedImage(), formatName, output);
    }

    @Override
    public String[] getImageReaderFormatNames() {
        Set<String> formatNames = new HashSet<>();
        for (String formatName : ImageIO.getReaderFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public String[] getImageWriterFormatNames() {
        Set<String> formatNames = new HashSet<>();
        for (String formatName : ImageIO.getWriterFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public A3Path createPath() {
        return new AWTA3Path(new Path2D.Float());
    }

    @Override
    public A3Font readFont(File input) throws IOException {
        Font font = A3AWTUtils.readFont(input);
        return font == null ? null : new AWTA3Font(font);
    }

    @Override
    public A3Font readFont(InputStream input) throws IOException {
        Font font = A3AWTUtils.readFont(input);
        return font == null ? null : new AWTA3Font(font);
    }

    @Override
    public A3Font readFont(URL input) throws IOException {
        return readFont(input.openStream());
    }

}
