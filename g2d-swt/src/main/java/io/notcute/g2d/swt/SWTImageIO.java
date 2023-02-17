package io.notcute.g2d.swt;

import io.notcute.g2d.MultiFrameImage;
import io.notcute.g2d.Image;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Device;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;

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
                if (image instanceof MultiFrameImage) {
                    MultiFrameImage im = (MultiFrameImage) image;
                    MultiFrameImage.Frame frame;
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

    public static boolean write(Image image, String mimeType, int quality, File output) throws IOException, SWTException {
        if (output == null) throw new IllegalArgumentException("output cannot be NULL");
        try (BufferedOutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(output.toPath()))) {
            return write(image, mimeType, quality, outputStream);
        }
    }

    public static boolean write(Image image, String mimeType, int quality, OutputStream output) throws IOException, SWTException {
        if (image == null) throw new IllegalArgumentException("image cannot be NULL");
        if (mimeType == null) throw new IllegalArgumentException("MIME Type cannot be NULL");
        if (output == null) throw new IllegalArgumentException("output cannot be NULL");
        boolean result = false;
        for (SIIOServiceProvider provider : SIIORegistry.getServiceProviders()) {
            result = provider.write(image, mimeType, quality, output);
            if (result) break;
        }
        return result;
    }

    public static String[] getReaderMIMETypes() {
        Set<String> mimeTypes = new HashSet<>();
        for (SIIOServiceProvider provider : SIIORegistry.getServiceProviders()) {
            mimeTypes.addAll(Arrays.asList(provider.getReaderMIMETypes()));
        }
        return mimeTypes.toArray(new String[0]);
    }

    public static String[] getWriterMIMETypes() {
        Set<String> mimeTypes = new HashSet<>();
        for (SIIOServiceProvider provider : SIIORegistry.getServiceProviders()) {
            mimeTypes.addAll(Arrays.asList(provider.getWriterMIMETypes()));
        }
        return mimeTypes.toArray(new String[0]);
    }

    public static String[] getMultiFrameReaderMIMETypes() {
        Set<String> mimeTypes = new HashSet<>();
        for (SIIOServiceProvider provider : SIIORegistry.getServiceProviders()) {
            mimeTypes.addAll(Arrays.asList(provider.getMultiFrameImageReaderMIMETypes()));
        }
        return mimeTypes.toArray(new String[0]);
    }

    public static String[] getMultiFrameWriterMIMETypes() {
        Set<String> mimeTypes = new HashSet<>();
        for (SIIOServiceProvider provider : SIIORegistry.getServiceProviders()) {
            mimeTypes.addAll(Arrays.asList(provider.getMultiFrameImageWriterMIMETypes()));
        }
        return mimeTypes.toArray(new String[0]);
    }

}
