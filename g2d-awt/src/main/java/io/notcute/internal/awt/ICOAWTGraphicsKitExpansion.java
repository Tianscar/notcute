package io.notcute.internal.awt;

import io.notcute.g2d.Image;
import io.notcute.g2d.MultiFrameImage;
import io.notcute.g2d.awt.AWTGraphicsKit;
import io.notcute.g2d.awt.AWTImage;

import javax.imageio.*;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.EOFException;
import java.io.IOException;
import java.util.Iterator;

public final class ICOAWTGraphicsKitExpansion implements AWTGraphicsKit.Expansion {

    private static final String[] READER_MIME_TYPES = new String[] { "image/vnd.microsoft.icon", "image/x-icon", "image/ico" };
    private static final String[] WRITER_MIME_TYPES = new String[] { "image/vnd.microsoft.icon", "image/x-icon", "image/ico" };

    @Override
    public BufferedImage readBufferedImage(ImageInputStream stream) throws IOException {
        return null;
    }

    @Override
    public boolean writeBufferedImage(BufferedImage im, String mimeType, float quality, ImageOutputStream output) throws IOException {
        return false;
    }

    @Override
    public MultiFrameImage readMultiFrameImage(ImageInputStream stream) throws IOException {
        if (!isICO(stream)) return null;
        ImageReader reader = getICOImageReader();
        if (reader == null) return null;
        reader.setInput(stream);
        Image.Frame[] frames = new Image.Frame[reader.getNumImages(true)];
        for (int i = 0; i < frames.length; i ++) {
            frames[i] = new Image.Frame(new AWTImage(reader.read(i)), 0, 0, 0, Image.DisposalMode.NONE, Image.BlendMode.SOURCE);
        }
        try {
            stream.close();
        }
        catch (IOException ignored) {
        }
        reader.dispose();
        MultiFrameImage result = new MultiFrameImage(frames);
        result.setLooping(0);
        return result;
    }

    private static ImageReader getICOImageReader() {
        Iterator<ImageReader> it = ImageIO.getImageReadersByMIMEType("image/ico");
        if (it.hasNext()) return it.next();
        return null;
    }

    private static boolean isICO(ImageInputStream stream) throws IOException {
        byte[] b = new byte[4];
        stream.mark();
        try {
            stream.readFully(b);
            int count = stream.readByte() + (stream.readByte() << 8);
            return (
                    b[0] == 0x0
                    && b[1] == 0x0
                    && b[2] == 0x1
                    && b[3] == 0x0
                    && count > 0
            );
        }
        catch (EOFException e) {
            return false;
        }
        finally {
            stream.reset();
        }
    }

    @Override
    public boolean writeMultiFrameImage(MultiFrameImage im, String mimeType, float quality, ImageOutputStream output) throws IOException {
        ImageWriter writer = getICOImageWriter();
        if (writer == null) return false;
        writer.setOutput(output);
        ImageWriteParam param = writer.getDefaultWriteParam();
        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionType("BI_RGB");
        }
        writer.prepareWriteSequence(null);
        for (MultiFrameImage.Frame frame : im) {
            writer.writeToSequence(new IIOImage(((AWTImage)frame.getImage()).getBufferedImage(), null, null), param);
        }
        writer.endWriteSequence();
        writer.dispose();
        output.flush();
        return true;
    }

    @Override
    public String[] getBufferedImageReaderMIMETypes() {
        return new String[0];
    }

    @Override
    public String[] getBufferedImageWriterMIMETypes() {
        return new String[0];
    }

    private static ImageWriter getICOImageWriter() {
        Iterator<ImageWriter> it = ImageIO.getImageWritersByMIMEType("image/ico");
        if (it.hasNext()) return it.next();
        else return null;
    }

    @Override
    public String[] getMultiFrameImageReaderMIMETypes() {
        return READER_MIME_TYPES.clone();
    }

    @Override
    public String[] getMultiFrameImageWriterMIMETypes() {
        return WRITER_MIME_TYPES.clone();
    }

}
