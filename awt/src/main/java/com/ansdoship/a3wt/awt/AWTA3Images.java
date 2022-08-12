package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.graphics.A3Images;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class AWTA3Images implements A3Images {

    @Override
    public A3Image read(File input) throws IOException {
        return new AWTA3Image(ImageIO.read(input));
    }

    @Override
    public A3Image read(InputStream input) throws IOException {
        return new AWTA3Image(ImageIO.read(input));
    }

    @Override
    public A3Image read(URL input) throws IOException {
        return new AWTA3Image(ImageIO.read(input));
    }

    @Override
    public boolean write(A3Image image, String formatName, File output) throws IOException {
        return ImageIO.write(((AWTA3Image)image).getBufferedImage(), formatName, output);
    }

    @Override
    public boolean write(A3Image image, String formatName, OutputStream output) throws IOException {
        return ImageIO.write(((AWTA3Image)image).getBufferedImage(), formatName, output);
    }

    @Override
    public String[] getReaderFormatNames() {
        Set<String> formatNames = new HashSet<>();
        for (String formatName : ImageIO.getReaderFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public String[] getWriterFormatNames() {
        Set<String> formatNames = new HashSet<>();
        for (String formatName : ImageIO.getWriterFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

}
