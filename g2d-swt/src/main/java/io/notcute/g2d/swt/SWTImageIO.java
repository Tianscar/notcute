package io.notcute.g2d.swt;

import io.notcute.g2d.AnimatedImage;
import io.notcute.g2d.Image;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Device;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class SWTImageIO {

    private SWTImageIO() {
        throw new UnsupportedOperationException();
    }

    public static Image read(Device device, File input, int type) throws IOException, SWTException {
        return read(device, Files.newInputStream(input.toPath()), type);
    }

    public static Image read(Device device, URL input, int type) throws IOException, SWTException {
        return read(device, input.openStream(), type);
    }

    public static Image read(Device device, InputStream input, int type) throws IOException, SWTException {
        if (device == null) throw new IllegalArgumentException("device cannot be NULL");
        if (input == null) throw new IllegalArgumentException("input source cannot be NULL");
        Image image = null;
        for (SIIOServiceProvider provider : SIIORegistry.getServiceProviders()) {
            image = provider.read(device, input);
            if (image != null) {
                if (image instanceof AnimatedImage) {
                    AnimatedImage im = (AnimatedImage) image;
                    AnimatedImage.Frame frame;
                    for (io.notcute.g2d.Image.Frame value : im) {
                        frame = value;
                        if (frame.getImage().getType() != type) {
                            frame.setImage(new SWTImage(Util.getImageData(device, ((SWTImage) frame.getImage()).getImageData(), Util.toSWTImageDepth(type))));
                        }
                    }
                }
                else {
                    image = new SWTImage(Util.getImageData(device, ((SWTImage) image).getImageData(), Util.toSWTImageDepth(type)));
                }
                break;
            }
        }
        return image;
    }

    public static boolean write(Image image, String formatName, int quality, File output) throws IOException, SWTException {
        if (output == null) throw new IllegalArgumentException("output cannot be NULL");
        try (BufferedOutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(output.toPath()))) {
            return write(image, formatName, quality, outputStream);
        }
    }

    public static boolean write(Image image, String formatName, int quality, OutputStream output) throws IOException, SWTException {
        if (image == null) throw new IllegalArgumentException("image cannot be NULL");
        if (formatName == null) throw new IllegalArgumentException("format name cannot be NULL");
        if (output == null) throw new IllegalArgumentException("output cannot be NULL");
        boolean result = false;
        for (SIIOServiceProvider provider : SIIORegistry.getServiceProviders()) {
            result = provider.write(image, formatName, quality, output);
            if (result) break;
        }
        return result;
    }

    public static String[] getReaderFormatNames() {
        List<String> formatNames = new ArrayList<>();
        for (SIIOServiceProvider provider : SIIORegistry.getServiceProviders()) {
            formatNames.addAll(Arrays.asList(provider.getReaderFormatNames()));
        }
        return formatNames.toArray(new String[0]);
    }

    public static String[] getWriterFormatNames() {
        List<String> formatNames = new ArrayList<>();
        for (SIIOServiceProvider provider : SIIORegistry.getServiceProviders()) {
            formatNames.addAll(Arrays.asList(provider.getWriterFormatNames()));
        }
        return formatNames.toArray(new String[0]);
    }

    public static String[] getAnimatedReaderFormatNames() {
        List<String> formatNames = new ArrayList<>();
        for (SIIOServiceProvider provider : SIIORegistry.getServiceProviders()) {
            formatNames.addAll(Arrays.asList(provider.getAnimatedImageReaderFormatNames()));
        }
        return formatNames.toArray(new String[0]);
    }

    public static String[] getAnimatedWriterFormatNames() {
        List<String> formatNames = new ArrayList<>();
        for (SIIOServiceProvider provider : SIIORegistry.getServiceProviders()) {
            formatNames.addAll(Arrays.asList(provider.getAnimatedImageWriterFormatNames()));
        }
        return formatNames.toArray(new String[0]);
    }

}
