package a3wt.awt;

import a3wt.graphics.A3FramedImage;
import a3wt.graphics.DefaultA3FramedImage;
import com.twelvemonkeys.imageio.metadata.tiff.TIFF;

import javax.imageio.IIOImage;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.Iterator;

import static a3wt.util.A3Arrays.copy;

public final class TiffFIIOSpi implements FIIOServiceProvider {

    private static final String[] READER_FORMAT_NAMES = new String[]{"tif", "tiff", "bigtiff"};
    private static final String[] WRITER_FORMAT_NAMES = new String[]{"tif", "tiff", "bigtiff"};

    @Override
    public A3FramedImage read(final ImageInputStream stream) throws IOException {
        stream.mark();
        try {
            if (!(isTiff(stream, TIFF.TIFF_MAGIC) || isTiff(stream, TIFF.BIGTIFF_MAGIC))) return null;
        }
        finally {
            stream.reset();
        }
        final ImageReader reader = getTiffImageReader();
        if (reader == null) return null;
        reader.setInput(stream);
        final A3FramedImage.Frame[] frames = new A3FramedImage.Frame[reader.getNumImages(true)];
        for (int i = 0; i < frames.length; i ++) {
            frames[i] = new A3FramedImage.DefaultFrame(new AWTA3Image(reader.read(i)), 0);
        }
        try {
            stream.close();
        }
        catch (final IOException ignored) {
        }
        reader.dispose();
        return new DefaultA3FramedImage(frames);
    }

    private static boolean isTiff(final ImageInputStream stream, final int versionMagic) throws IOException {
        try {
            final byte[] magic = new byte[4];
            stream.readFully(magic, 0, magic.length);
            return magic[0] == 'I' && magic[1] == 'I' && magic[2] == (versionMagic & 0xFF) && magic[3] == (versionMagic >>> 8)
                    || magic[0] == 'M' && magic[1] == 'M' && magic[2] == (versionMagic >>> 8) && magic[3] == (versionMagic & 0xFF);
        }
        catch (final EOFException e) {
            return false;
        }
    }

    @Override
    public boolean write(final A3FramedImage im, final String formatName, final float quality, final ImageOutputStream output) throws IOException {
        final ImageWriter writer = getTiffImageWriter(formatName);
        if (writer == null) return false;
        writer.setOutput(output);
        final ImageWriteParam params = writer.getDefaultWriteParam();
        params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        params.setCompressionType("LZW");
        params.setCompressionQuality(quality);
        writer.prepareWriteSequence(null);
        for (final A3FramedImage.Frame frame : im) {
            writer.writeToSequence(new IIOImage(((AWTA3Image)frame.getImage()).bufferedImage, null, null), params);
        }
        writer.endWriteSequence();
        output.flush();
        writer.dispose();
        return true;
    }

    private static ImageReader getTiffImageReader() {
        Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("tif");
        if (it.hasNext()) return it.next();
        it = ImageIO.getImageReadersByFormatName("bigtiff");
        if (it.hasNext()) return it.next();
        return null;
    }

    private static ImageWriter getTiffImageWriter(final String formatName) {
        final Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName(formatName);
        if (it.hasNext()) return it.next();
        return null;
    }

    @Override
    public String[] getReaderFormatNames() {
        return copy(READER_FORMAT_NAMES);
    }

    @Override
    public String[] getWriterFormatNames() {
        return copy(WRITER_FORMAT_NAMES);
    }

}
