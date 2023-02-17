package io.notcute.g2d.awt;

import javax.imageio.*;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.EOFException;
import java.io.IOException;
import java.util.Iterator;

public final class BMPBIIOSpi implements BIIOServiceProvider {

    private static final String[] READER_MIME_TYPES = new String[] { "image/bmp" };
    private static final String[] WRITER_MIME_TYPES = new String[] { "image/bmp" };

    @Override
    public BufferedImage read(ImageInputStream stream) throws IOException {
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
    public boolean write(BufferedImage im, String mimeType, float quality, ImageOutputStream output) throws IOException {
        ImageWriter writer = getBMPImageWriter();
        if (writer == null) return false;
        writer.setOutput(output);
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        writer.write(null, new IIOImage(im, null, null), param);
        output.flush();
        writer.dispose();
        return true;
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
    public String[] getReaderMIMETypes() {
        return READER_MIME_TYPES.clone();
    }

    @Override
    public String[] getWriterMIMETypes() {
        return WRITER_MIME_TYPES.clone();
    }

}
