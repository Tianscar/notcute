package io.notcute.internal.awt;

import io.notcute.g2d.MultiFrameImage;
import io.notcute.g2d.awt.AWTGraphicsKit;

import javax.imageio.*;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.EOFException;
import java.io.IOException;
import java.util.Iterator;

public final class BMPAWTGraphicsKitExpansion implements AWTGraphicsKit.Expansion {

    private static final String[] READER_MIME_TYPES = new String[] { "image/bmp" };
    private static final String[] WRITER_MIME_TYPES = new String[] { "image/bmp" };

    @Override
    public BufferedImage readBufferedImage(ImageInputStream stream) throws IOException {
        if (!isBMP(stream)) return null;
        ImageReader reader = getBMPImageReader();
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

    private static boolean isBMP(ImageInputStream stream) throws IOException {
        byte[] b = new byte[8];
        stream.mark();
        try {
            stream.readFully(b);
        }
        catch (EOFException e) {
            return false;
        }
        finally {
            stream.reset();
        }
        return b[0] == 0x42 && b[1] == 0x4d;
    }

    @Override
    public boolean writeBufferedImage(BufferedImage im, String mimeType, float quality, ImageOutputStream output) throws IOException {
        ImageWriter writer = getBMPImageWriter();
        if (writer == null) return false;
        writer.setOutput(output);
        ImageWriteParam param = writer.getDefaultWriteParam();
        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionType("BI_RGB");
            param.setCompressionQuality(quality);
        }
        writer.write(null, new IIOImage(im, null, null), param);
        writer.dispose();
        output.flush();
        return true;
    }

    @Override
    public MultiFrameImage readMultiFrameImage(ImageInputStream stream) throws IOException {
        return null;
    }

    @Override
    public boolean writeMultiFrameImage(MultiFrameImage im, String mimeType, float quality, ImageOutputStream output) throws IOException {
        return false;
    }

    private static ImageReader getBMPImageReader() {
        Iterator<ImageReader> it = ImageIO.getImageReadersByMIMEType("image/bmp");
        if (it.hasNext()) return it.next();
        else return null;
    }

    private static ImageWriter getBMPImageWriter() {
        Iterator<ImageWriter> it = ImageIO.getImageWritersByMIMEType("image/bmp");
        if (it.hasNext()) return it.next();
        else return null;
    }

    @Override
    public String[] getBufferedImageReaderMIMETypes() {
        return READER_MIME_TYPES.clone();
    }

    @Override
    public String[] getBufferedImageWriterMIMETypes() {
        return WRITER_MIME_TYPES.clone();
    }

    @Override
    public String[] getMultiFrameImageReaderMIMETypes() {
        return new String[0];
    }

    @Override
    public String[] getMultiFrameImageWriterMIMETypes() {
        return new String[0];
    }

}
