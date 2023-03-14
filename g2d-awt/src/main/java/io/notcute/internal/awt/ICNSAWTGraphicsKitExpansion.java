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

public final class ICNSAWTGraphicsKitExpansion implements AWTGraphicsKit.Expansion {

    private static final int ICNS_MAGIC = ('i' << 24) + ('c' << 16) + ('n' << 8) + 's';

    private static final String[] READER_MIME_TYPES = new String[] { "image/x-apple-icons" };
    private static final String[] WRITER_MIME_TYPES = new String[] { "image/x-apple-icons" };

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
        if (!isICNS(stream)) return null;
        ImageReader reader = getICNSImageReader();
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

    private static ImageReader getICNSImageReader() {
        Iterator<ImageReader> it = ImageIO.getImageReadersByMIMEType("image/x-apple-icons");
        if (it.hasNext()) return it.next();
        return null;
    }

    private static boolean isICNS(ImageInputStream stream) throws IOException {
        stream.mark();
        try {
            return stream.readInt() == ICNS_MAGIC;
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
        ImageWriter writer = getICNSImageWriter();
        if (writer == null) return false;
        writer.setOutput(output);
        ImageWriteParam param = writer.getDefaultWriteParam();
        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);
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

    private static ImageWriter getICNSImageWriter() {
        Iterator<ImageWriter> it = ImageIO.getImageWritersByMIMEType("image/x-apple-icons");
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
