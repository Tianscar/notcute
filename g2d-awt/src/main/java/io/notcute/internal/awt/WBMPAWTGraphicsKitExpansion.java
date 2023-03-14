package io.notcute.internal.awt;

import io.notcute.g2d.MultiFrameImage;
import io.notcute.g2d.awt.AWTGraphicsKit;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.EOFException;
import java.io.IOException;
import java.util.Iterator;

public final class WBMPAWTGraphicsKitExpansion implements AWTGraphicsKit.Expansion {

    private static final int MAX_WBMP_WIDTH = 1024;
    private static final int MAX_WBMP_HEIGHT = 768;

    private static final String[] READER_MIME_TYPES = new String[] { "image/vnd.wap.wbmp" };
    private static final String[] WRITER_MIME_TYPES = new String[] { "image/vnd.wap.wbmp" };

    @Override
    public BufferedImage readBufferedImage(ImageInputStream stream) throws IOException {
        if (!isWBMP(stream)) return null;
        ImageReader reader = getWBMPImageReader();
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

    private static boolean isWBMP(ImageInputStream stream) throws IOException {
        stream.mark();
        try {
            int type = stream.readByte();
            int fixedHeader= stream.readByte();

            if (type != 0 || fixedHeader != 0) return false;

            int width = readVInt(stream);
            int height = readVInt(stream);

            if (width <= 0 || height <= 0) return false;

            long dataLength = stream.length();

            if (dataLength == -1) return (width < MAX_WBMP_WIDTH) && (height < MAX_WBMP_HEIGHT);

            dataLength -= stream.getStreamPosition();

            long scanSize = (width / 8) + ((width % 8) == 0 ? 0 : 1);

            return (dataLength == scanSize * height);
        }
        catch (EOFException e) {
            return false;
        }
        finally {
            stream.reset();
        }
    }

    private static int readVInt(ImageInputStream iis) throws IOException {
        int value = iis.readByte();
        int result = value & 0x7F;
        while ((value & 0x80) == 0x80) {
            result <<= 7;
            value = iis.readByte();
            result |= (value & 0x7F);
        }
        return result;
    }

    @Override
    public boolean writeBufferedImage(BufferedImage im, String mimeType, float quality, ImageOutputStream output) throws IOException {
        ImageWriter writer = getWBMPImageWriter();
        if (writer == null) return false;
        writer.setOutput(output);
        writer.write(null, new IIOImage(im, null, null), null);
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

    private static ImageReader getWBMPImageReader() {
        Iterator<ImageReader> it = ImageIO.getImageReadersByMIMEType("image/vnd.wap.wbmp");
        if (it.hasNext()) return it.next();
        else return null;
    }

    private static ImageWriter getWBMPImageWriter() {
        Iterator<ImageWriter> it = ImageIO.getImageWritersByMIMEType("image/vnd.wap.wbmp");
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
