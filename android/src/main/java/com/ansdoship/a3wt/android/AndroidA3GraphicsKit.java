package com.ansdoship.a3wt.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.Typeface;
import com.ansdoship.a3wt.app.A3Assets;
import com.ansdoship.a3wt.graphics.A3FramedImage;
import com.ansdoship.a3wt.graphics.A3GraphicsKit;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.graphics.A3Font;
import com.ansdoship.a3wt.graphics.A3Path;
import com.ansdoship.a3wt.graphics.A3Cursor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static com.ansdoship.a3wt.android.A3AndroidUtils.fontStyle2TypefaceStyle;
import static com.ansdoship.a3wt.android.A3AndroidUtils.readTypeface;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotEmpty;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;
import static com.ansdoship.a3wt.util.A3Files.removeStartSeparator;

public class AndroidA3GraphicsKit implements A3GraphicsKit {

    protected final AtomicReference<Context> contextRef = new AtomicReference<>();

    public void attach(final Context context) {
        checkArgNotNull(context, "context");
        contextRef.compareAndSet(null, context);
    }

    public void detach() {
        contextRef.set(null);
    }

    @Override
    public A3Image createImage(final int width, final int height) {
        return new AndroidA3Image(Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888));
    }

    @Override
    public A3Image readImage(final File input) {
        checkArgNotNull(input, "input");
        try {
            final A3FramedImage framedImage = readFramedImage(input);
            if (framedImage != null) return framedImage;
            final Bitmap bitmap = BitmapIO.read(input);
            if (bitmap == null) return null;
            else return new AndroidA3Image(bitmap);
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3Image readImage(final InputStream input) {
        checkArgNotNull(input, "input");
        try {
            final A3FramedImage framedImage = readFramedImage(input);
            if (framedImage != null) return framedImage;
            final Bitmap bitmap = BitmapIO.read(input);
            if (bitmap == null) return null;
            else return new AndroidA3Image(bitmap);
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3Image readImage(final URL input) {
        checkArgNotNull(input, "input");
        try {
            final A3FramedImage framedImage = readFramedImage(input);
            if (framedImage != null) return framedImage;
            final Bitmap bitmap = BitmapIO.read(input);
            if (bitmap == null) return null;
            else return new AndroidA3Image(bitmap);
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3Image readImage(final A3Assets assets, final String input) {
        checkArgNotNull(assets, "assets");
        checkArgNotEmpty(input, "input");
        try {
            final A3FramedImage framedImage = readFramedImage(assets, removeStartSeparator(input));
            if (framedImage != null) return framedImage;
            final Bitmap bitmap = BitmapIO.read(((AndroidA3Assets)assets).assets, removeStartSeparator(input));
            if (bitmap == null) return null;
            else return new AndroidA3Image(bitmap);
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3FramedImage readFramedImage(final File input) {
        checkArgNotNull(input, "input");
        try {
            return FramedBitmapIO.read(input);
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3FramedImage readFramedImage(final InputStream input) {
        checkArgNotNull(input, "input");
        try {
            return FramedBitmapIO.read(input);
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3FramedImage readFramedImage(final URL input) {
        checkArgNotNull(input, "input");
        try {
            return FramedBitmapIO.read(input);
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3FramedImage readFramedImage(final A3Assets assets, String input) {
        checkArgNotNull(assets, "assets");
        checkArgNotNull(input, "input");
        try {
            return FramedBitmapIO.read(((AndroidA3Assets)assets).assets, removeStartSeparator(input));
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public boolean writeImage(final A3Image image, final String formatName, final int quality, final File output) {
        checkArgNotNull(image, "image");
        try {
            if (image instanceof A3FramedImage) return writeFramedImage((A3FramedImage) image, formatName, quality, output);
            return BitmapIO.write(output, ((AndroidA3Image)image).getBitmap(), formatName, quality);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeImage(final A3Image image, final String formatName, final int quality, final OutputStream output) {
        checkArgNotNull(image, "image");
        try {
            if (image instanceof A3FramedImage) return writeFramedImage((A3FramedImage) image, formatName, quality, output);
            return BitmapIO.write(output, ((AndroidA3Image)image).getBitmap(), formatName, quality);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeFramedImage(final A3FramedImage image, final String formatName, final int quality, final File output) {
        checkArgNotNull(image, "image");
        try {
            return FramedBitmapIO.write(output, image, formatName, quality);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeFramedImage(final A3FramedImage image, final String formatName, final int quality, final OutputStream output) {
        checkArgNotNull(image, "image");
        try {
            return FramedBitmapIO.write(output, image, formatName, quality);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String[] getImageReaderFormatNames() {
        final Set<String> formatNames = new HashSet<>();
        for (final String formatName : BitmapIO.getReaderFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        for (final String formatName : FramedBitmapIO.getReaderFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public String[] getImageWriterFormatNames() {
        final Set<String> formatNames = new HashSet<>();
        for (final String formatName : BitmapIO.getWriterFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        for (final String formatName : FramedBitmapIO.getWriterFormatNames()) {
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
    public A3Cursor createCursor(final int type) {
        return new AndroidA3Cursor(contextRef.get(), type);
    }

    @Override
    public A3Cursor createCursor(final A3Image image) {
        return new AndroidA3Cursor((AndroidA3Image) image);
    }

    @Override
    public A3Cursor getDefaultCursor() {
        return new AndroidA3Cursor(contextRef.get(), A3Cursor.Type.DEFAULT);
    }

}
