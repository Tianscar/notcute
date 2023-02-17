package io.notcute.g2d.awt;

import io.notcute.internal.awt.AWTG2DUtils;
import io.notcute.util.MathUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class BufferedImageIO {

    private BufferedImageIO() {
        throw new UnsupportedOperationException();
    }

    public static BufferedImage read(File input, int type) throws IOException {
        return read(ImageIO.createImageInputStream(input), type);
    }

    public static BufferedImage read(URL input, int type) throws IOException {
        return read(ImageIO.createImageInputStream(input), type);
    }

    public static BufferedImage read(InputStream input, int type) throws IOException {
        return read(ImageIO.createImageInputStream(input), type);
    }

    public static BufferedImage read(ImageInputStream input, int type) throws IOException {
        if (input == null) throw new IllegalArgumentException("input source cannot be NULL");
        BufferedImage image = null;
        for (BIIOServiceProvider provider : BIIORegistry.getServiceProviders()) {
            image = provider.read(input);
            if (image != null) {
                if (image.getType() != type) {
                    image = AWTG2DUtils.getImage(image, type);
                }
                break;
            }
        }
        return image;
    }

    public static boolean write(BufferedImage image, String mimeType, float quality, File output) throws IOException {
        if (output == null) throw new IllegalArgumentException("output cannot be NULL");
        try (ImageOutputStream ios = ImageIO.createImageOutputStream(output)) {
            return write(image, mimeType, quality, ios);
        }
    }

    public static boolean write(BufferedImage image, String mimeType, float quality, OutputStream output) throws IOException {
        if (output == null) throw new IllegalArgumentException("output cannot be NULL");
        try (ImageOutputStream ios = ImageIO.createImageOutputStream(output)) {
            return write(image, mimeType, quality, ios);
        }
    }

    public static boolean write(BufferedImage image, String mimeType, float quality, ImageOutputStream output) throws IOException {
        if (image == null) throw new IllegalArgumentException("image cannot be NULL");
        if (mimeType == null) throw new IllegalArgumentException("MIME Type cannot be NULL");
        if (output == null) throw new IllegalArgumentException("output cannot be NULL");
        boolean result = false;
        quality = MathUtils.clamp(quality, 0, 1);
        for (BIIOServiceProvider provider : BIIORegistry.getServiceProviders()) {
            result = provider.write(image, mimeType, quality, output);
            if (result) break;
        }
        return result;
    }

    public static String[] getReaderMIMETypes() {
        Set<String> mimeTypes = new HashSet<>();
        for (BIIOServiceProvider provider : BIIORegistry.getServiceProviders()) {
            mimeTypes.addAll(Arrays.asList(provider.getReaderMIMETypes()));
        }
        return mimeTypes.toArray(new String[0]);
    }

    public static String[] getWriterMIMETypes() {
        Set<String> mimeTypes = new HashSet<>();
        for (BIIOServiceProvider provider : BIIORegistry.getServiceProviders()) {
            mimeTypes.addAll(Arrays.asList(provider.getWriterMIMETypes()));
        }
        return mimeTypes.toArray(new String[0]);
    }

}
