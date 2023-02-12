package io.notcute.g2d.awt;

import io.notcute.g2d.AnimatedImage;
import io.notcute.util.MathUtils;

import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class AnimatedImageIO {

    private AnimatedImageIO() {
        throw new UnsupportedOperationException();
    }

    public static AnimatedImage read(File input, int type) throws IOException {
        return read(ImageIO.createImageInputStream(input), type);
    }

    public static AnimatedImage read(URL input, int type) throws IOException {
        return read(ImageIO.createImageInputStream(input), type);
    }

    public static AnimatedImage read(InputStream input, int type) throws IOException {
        return read(ImageIO.createImageInputStream(input), type);
    }

    public static AnimatedImage read(ImageInputStream input, int type) throws IOException {
        if (input == null) throw new IllegalArgumentException("input source cannot be NULL");
        AnimatedImage image = null;
        for (AIIOServiceProvider provider : AIIORegistry.getServiceProviders()) {
            image = provider.read(input);
            if (image != null) {
                AnimatedImage.Frame frame;
                for (io.notcute.g2d.Image.Frame value : image) {
                    frame = value;
                    if (frame.getImage().getType() != type) {
                        frame.setImage(new AWTImage(Util.getImage(((AWTImage) frame.getImage()).getBufferedImage(), type)));
                    }
                }
                break;
            }
        }
        return image;
    }

    public static boolean write(AnimatedImage image, String formatName, float quality, File output) throws IOException {
        if (output == null) throw new IllegalArgumentException("output cannot be NULL");
        try (ImageOutputStream ios = ImageIO.createImageOutputStream(output)) {
            return write(image, formatName, quality, ios);
        }
    }

    public static boolean write(AnimatedImage image, String formatName, float quality, OutputStream output) throws IOException {
        if (output == null) throw new IllegalArgumentException("output cannot be NULL");
        try (ImageOutputStream ios = ImageIO.createImageOutputStream(output)) {
            return write(image, formatName, quality, ios);
        }
    }

    public static boolean write(AnimatedImage image, String formatName, float quality, ImageOutputStream output) throws IOException {
        if (image == null) throw new IllegalArgumentException("image cannot be NULL");
        if (formatName == null) throw new IllegalArgumentException("format name cannot be NULL");
        if (output == null) throw new IllegalArgumentException("output cannot be NULL");
        boolean result = false;
        float quality0 = MathUtils.clamp(quality, 0, 1);
        for (AIIOServiceProvider provider : AIIORegistry.getServiceProviders()) {
            result = provider.write(image, formatName, quality0, output);
            if (result) break;
        }
        return result;
    }

    public static String[] getReaderFormatNames() {
        List<String> formatNames = new ArrayList<>();
        for (AIIOServiceProvider provider : AIIORegistry.getServiceProviders()) {
            formatNames.addAll(Arrays.asList(provider.getReaderFormatNames()));
        }
        return formatNames.toArray(new String[0]);
    }

    public static String[] getWriterFormatNames() {
        List<String> formatNames = new ArrayList<>();
        for (AIIOServiceProvider provider : AIIORegistry.getServiceProviders()) {
            formatNames.addAll(Arrays.asList(provider.getWriterFormatNames()));
        }
        return formatNames.toArray(new String[0]);
    }

}
