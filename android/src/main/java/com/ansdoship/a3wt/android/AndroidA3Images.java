package com.ansdoship.a3wt.android;

import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.graphics.A3Images;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class AndroidA3Images implements A3Images {

    @Override
    public A3Image read(File input) throws IOException {
        return new AndroidA3Image(BitmapIO.read(input));
    }

    @Override
    public A3Image read(InputStream input) throws IOException {
        return new AndroidA3Image(BitmapIO.read(input));
    }

    @Override
    public A3Image read(URL input) throws IOException {
        return new AndroidA3Image(BitmapIO.read(input));
    }

    @Override
    public boolean write(A3Image image, String formatName, File output) throws IOException {
        return BitmapIO.write(output, ((AndroidA3Image)image).getBitmap(), formatName, 100);
    }

    @Override
    public boolean write(A3Image image, String formatName, OutputStream output) throws IOException {
        return BitmapIO.write(output, ((AndroidA3Image)image).getBitmap(), formatName, 100);
    }

    @Override
    public String[] getReaderFormatNames() {
        Set<String> formatNames = new HashSet<>();
        for (String formatName : BitmapIO.getReaderFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public String[] getWriterFormatNames() {
        Set<String> formatNames = new HashSet<>();
        for (String formatName : BitmapIO.getWriterFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

}
