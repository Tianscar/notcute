package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3FramedImage;
import com.ansdoship.a3wt.util.A3Math;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class FramedImageIO {

    private FramedImageIO(){}

    public static A3FramedImage read(final File input) throws IOException {
        if (input == null) throw new IllegalArgumentException("input == null!");
        return read(Files.newInputStream(input.toPath()));
    }

    public static A3FramedImage read(final URL input) throws IOException {
        if (input == null) throw new IllegalArgumentException("input == null!");
        return read(input.openStream());
    }

    public static A3FramedImage read(InputStream stream) throws IOException {
        if (!stream.markSupported()) stream = new BufferedInputStream(stream);
        A3FramedImage image = null;
        for (final FIIOServiceProvider provider : FIIORegistry.getServiceProviders()) {
            if (provider.canRead(stream)) image = provider.read(stream);
            if (image != null) break;
        }
        return image;
    }

    public static boolean write(final A3FramedImage image, final String formatName, final int quality, final File output) throws IOException {
        if (output == null) {
            throw new IllegalArgumentException("output cannot be NULL");
        }
        return write(image, formatName, quality, Files.newOutputStream(output.toPath()));
    }

    public static boolean write(final A3FramedImage image, final String formatName, final int quality, final OutputStream output) throws IOException {
        if (image == null) {
            throw new IllegalArgumentException("image cannot be NULL");
        }
        if (formatName == null) {
            throw new IllegalArgumentException("format name cannot be NULL");
        }
        if (output == null) {
            throw new IllegalArgumentException("output cannot be NULL");
        }
        boolean result = false;
        for (final FIIOServiceProvider provider : FIIORegistry.getServiceProviders()) {
            if (provider.canWrite(formatName)) result = provider.write(image, formatName, A3Math.clamp(quality, 0, 1) / 100f, output);
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
