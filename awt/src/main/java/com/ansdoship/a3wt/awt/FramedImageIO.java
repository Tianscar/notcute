package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3FramedImage;

import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ansdoship.a3wt.util.A3Math.clamp;

public final class FramedImageIO {

    private FramedImageIO(){}

    public static A3FramedImage read(final File input, final int type) throws IOException {
        return read(ImageIO.createImageInputStream(input), type);
    }

    public static A3FramedImage read(final URL input, final int type) throws IOException {
        return read(ImageIO.createImageInputStream(input), type);
    }

    public static A3FramedImage read(final InputStream input, final int type) throws IOException {
        return read(ImageIO.createImageInputStream(input), type);
    }

    public static A3FramedImage read(final ImageInputStream input, final int type) throws IOException {
        if (input == null) throw new IllegalArgumentException("input source cannot be NULL");
        A3FramedImage image = null;
        for (final FIIOServiceProvider provider : FIIORegistry.getServiceProviders()) {
            image = provider.read(input);
            if (image != null) {
                image.setTypeAll(type);
                break;
            }
        }
        return image;
    }

    public static boolean write(final A3FramedImage image, final String formatName, final float quality, final File output) throws IOException {
        if (output == null) throw new IllegalArgumentException("output cannot be NULL");
        try (final ImageOutputStream ios = ImageIO.createImageOutputStream(output)) {
            return write(image, formatName, quality, ios);
        }
    }

    public static boolean write(final A3FramedImage image, final String formatName, final float quality, final OutputStream output) throws IOException {
        if (output == null) throw new IllegalArgumentException("output cannot be NULL");
        try (final ImageOutputStream ios = ImageIO.createImageOutputStream(output)) {
            return write(image, formatName, quality, ios);
        }
    }

    public static boolean write(final A3FramedImage image, final String formatName, final float quality, final ImageOutputStream output) throws IOException {
        if (image == null) throw new IllegalArgumentException("image cannot be NULL");
        if (formatName == null) throw new IllegalArgumentException("format name cannot be NULL");
        if (output == null) throw new IllegalArgumentException("output cannot be NULL");
        boolean result = false;
        final float quality0 = clamp(quality, 0, 1);
        for (final FIIOServiceProvider provider : FIIORegistry.getServiceProviders()) {
            result = provider.write(image, formatName, quality0, output);
            if (result) break;
        }
        return result;
    }

    public static String[] getReaderFormatNames() {
        final List<String> formatNames = new ArrayList<>();
        for (final FIIOServiceProvider provider : FIIORegistry.getServiceProviders()) {
            formatNames.addAll(Arrays.asList(provider.getReaderFormatNames()));
        }
        return formatNames.toArray(new String[0]);
    }

    public static String[] getWriterFormatNames() {
        final List<String> formatNames = new ArrayList<>();
        for (final FIIOServiceProvider provider : FIIORegistry.getServiceProviders()) {
            formatNames.addAll(Arrays.asList(provider.getWriterFormatNames()));
        }
        return formatNames.toArray(new String[0]);
    }

}
