package com.ansdoship.a3wt.android;

import android.graphics.Path;
import android.graphics.Typeface;
import com.ansdoship.a3wt.graphics.A3Font;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.graphics.A3GraphicsKit;
import com.ansdoship.a3wt.graphics.A3Path;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import static com.ansdoship.a3wt.android.A3AndroidUtils.readTypeface;

public class AndroidA3GraphicsKit implements A3GraphicsKit {

    @Override
    public A3Image readImage(File input) throws IOException {
        return new AndroidA3Image(BitmapIO.read(input));
    }

    @Override
    public A3Image readImage(InputStream input) throws IOException {
        return new AndroidA3Image(BitmapIO.read(input));
    }

    @Override
    public A3Image readImage(URL input) throws IOException {
        return new AndroidA3Image(BitmapIO.read(input));
    }

    @Override
    public boolean writeImage(A3Image image, String formatName, File output) throws IOException {
        return BitmapIO.write(output, ((AndroidA3Image)image).getBitmap(), formatName, 100);
    }

    @Override
    public boolean writeImage(A3Image image, String formatName, OutputStream output) throws IOException {
        return BitmapIO.write(output, ((AndroidA3Image)image).getBitmap(), formatName, 100);
    }

    @Override
    public String[] getImageReaderFormatNames() {
        Set<String> formatNames = new HashSet<>();
        for (String formatName : BitmapIO.getReaderFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public String[] getImageWriterFormatNames() {
        Set<String> formatNames = new HashSet<>();
        for (String formatName : BitmapIO.getWriterFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public A3Path createPath() {
        return new AndroidA3Path(new Path());
    }

    @Override
    public A3Font readFont(File input) throws IOException {
        Typeface typeface = readTypeface(input);
        return typeface == null ? null : new AndroidA3Font(typeface);
    }

    @Override
    public A3Font readFont(InputStream input) throws IOException {
        Typeface typeface = readTypeface(input);
        return typeface == null ? null : new AndroidA3Font(typeface);
    }

    @Override
    public A3Font readFont(URL input) throws IOException {
        return readFont(input.openStream());
    }

}
