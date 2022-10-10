package com.ansdoship.a3wt.android;

import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.Typeface;
import com.ansdoship.a3wt.app.A3Assets;
import com.ansdoship.a3wt.graphics.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import static com.ansdoship.a3wt.android.A3AndroidUtils.fontStyle2TypefaceStyle;
import static com.ansdoship.a3wt.android.A3AndroidUtils.readTypeface;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotEmpty;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;
import static com.ansdoship.a3wt.util.A3Files.removeStartSeparator;

public class AndroidA3GraphicsKit implements A3GraphicsKit {

    @Override
    public A3Image createImage(final int width, final int height) {
        return new AndroidA3Image(Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888));
    }

    @Override
    public A3Image readImage(final File input) {
        checkArgNotNull(input, "input");
        try {
            return new AndroidA3Image(BitmapIO.read(input));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public A3Image readImage(final InputStream input) {
        checkArgNotNull(input, "input");
        try {
            return new AndroidA3Image(BitmapIO.read(input));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public A3Image readImage(final URL input) {
        checkArgNotNull(input, "input");
        try {
            return new AndroidA3Image(BitmapIO.read(input));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public A3Image readImage(final A3Assets assets, final String input) {
        checkArgNotNull(assets, "assets");
        checkArgNotEmpty(input, "input");
        try {
            return new AndroidA3Image(BitmapIO.read(((AndroidA3Assets)assets).getAssets(), removeStartSeparator(input)));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public A3FramedImage readFramedImage(File input) {
        return null;
    }

    @Override
    public A3FramedImage readFramedImage(InputStream input) {
        return null;
    }

    @Override
    public A3FramedImage readFramedImage(URL input) {
        return null;
    }

    @Override
    public A3FramedImage readFramedImage(A3Assets assets, String input) {
        return null;
    }

    @Override
    public boolean writeImage(final A3Image image, final String formatName, final int quality, final File output) {
        checkArgNotNull(image, "image");
        try {
            return BitmapIO.write(output, ((AndroidA3Image)image).getBitmap(), formatName, quality);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeImage(final A3Image image, final String formatName, final int quality, final OutputStream output) {
        checkArgNotNull(image, "image");
        try {
            return BitmapIO.write(output, ((AndroidA3Image)image).getBitmap(), formatName, quality);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeFramedImage(A3FramedImage image, String formatName, int quality, File output) {
        return false;
    }

    @Override
    public boolean writeFramedImage(A3FramedImage image, String formatName, int quality, OutputStream output) {
        return false;
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
    public A3Font readFont(final File input) {
        checkArgNotNull(input, "input");
        try {
            final Typeface typeface = readTypeface(input);
            return typeface == null ? null : new AndroidA3Font(typeface);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public A3Font readFont(final A3Assets assets, final String input) {
        checkArgNotNull(assets, "assets");
        checkArgNotEmpty(input, "input");
        try {
            final Typeface typeface = readTypeface(((AndroidA3Assets)assets).getAssets(), removeStartSeparator(input));
            return typeface == null ? null : new AndroidA3Font(typeface);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public A3Font readFont(final String familyName, final int style) {
        checkArgNotEmpty(familyName, "familyName");
        return new AndroidA3Font(Typeface.create(familyName, fontStyle2TypefaceStyle(style)));
    }

    protected static final AndroidA3Font DEFAULT_FONT = new AndroidA3Font(Typeface.DEFAULT);

    @Override
    public A3Font getDefaultFont() {
        return DEFAULT_FONT;
    }

    @Override
    public A3Cursor createCursor(int type) {
        return null;
    }

    @Override
    public A3Cursor createCursor(A3Image image) {
        return null;
    }

}
