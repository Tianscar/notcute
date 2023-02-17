package io.notcute.g2d.awt;

import javax.imageio.*;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.EOFException;
import java.io.IOException;
import java.util.Iterator;

public final class JPEGBIIOSpi implements BIIOServiceProvider {

    private static final int JPEG_SOI = 0xD8;

    private static final String[] READER_MIME_TYPES = new String[] { "image/jpeg" };
    private static final String[] WRITER_MIME_TYPES = new String[] { "image/jpeg" };

    @Override
    public BufferedImage read(ImageInputStream stream) throws IOException {
        if (!isJPEG(stream)) return null;
        ImageReader reader = getJPEGImageReader();
        if (reader == null) return null;
        reader.setInput(stream, false, true);
        BufferedImage result = reader.read(0);
        reader.dispose();
        try {
            stream.close();
        }
        catch (IOException ignored) {
        }
        return result;
    }

    private static boolean isJPEG(ImageInputStream stream) throws IOException {
        int b0, b1;
        stream.mark();
        try {
            b0 = stream.read();
            b1 = stream.read();
        }
        catch (EOFException e) {
            return false;
        }
        finally {
            stream.reset();
        }
        return b0 == 0xFF && b1 == JPEG_SOI;
    }

    @Override
    public boolean write(BufferedImage im, String mimeType, float quality, ImageOutputStream output) throws IOException {
        ImageWriter writer = getJPEGImageWriter();
        if (writer == null) return false;
        writer.setOutput(output);
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionType("JPEG");
        param.setCompressionQuality(quality);
        writer.write(null, new IIOImage(im, null, null), param);
        output.flush();
        writer.dispose();
        return true;
    }

    private static ImageReader getJPEGImageReader() {
        Iterator<ImageReader> it = ImageIO.getImageReadersByMIMEType("image/jpeg");
        if (it.hasNext()) return it.next();
        else return null;
    }

    private static ImageWriter getJPEGImageWriter() {
        Iterator<ImageWriter> it = ImageIO.getImageWritersByMIMEType("image/jpeg");
        if (it.hasNext()) return it.next();
        else return null;
    }

    @Override
    public String[] getReaderMIMETypes() {
        return READER_MIME_TYPES.clone();
    }

    @Override
    public String[] getWriterMIMETypes() {
        return WRITER_MIME_TYPES.clone();
    }

}
