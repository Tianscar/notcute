package io.notcute.internal.awt;

import io.notcute.g2d.MultiFrameImage;
import io.notcute.g2d.awt.AWTGraphicsKit;

import javax.imageio.*;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.Iterator;

public final class TGAAWTGraphicsKitExpansion implements AWTGraphicsKit.Expansion {

    private static final int TGA_COLORMAP_NONE = 0;
    private static final int TGA_COLORMAP_PALETTE = 1;
    private static final int TGA_IMAGETYPE_NONE = 0;
    private static final int TGA_IMAGETYPE_COLORMAPPED = 1;
    private static final int TGA_IMAGETYPE_TRUECOLOR = 2;
    private static final int TGA_IMAGETYPE_MONOCHROME = 3;
    private static final int TGA_IMAGETYPE_COLORMAPPED_RLE = 9;
    private static final int TGA_IMAGETYPE_TRUECOLOR_RLE = 10;
    private static final int TGA_IMAGETYPE_MONOCHROME_RLE = 11;

    private static final String[] READER_MIME_TYPES = new String[] { "image/tga", "image/x-tga", "image/targa", "image/x-targa" };
    private static final String[] WRITER_MIME_TYPES = new String[] { "image/tga", "image/x-tga", "image/targa", "image/x-targa" };

    @Override
    public BufferedImage readBufferedImage(ImageInputStream stream) throws IOException {
        if (!isTGA(stream)) return null;
        ImageReader reader = getTGAImageReader();
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

    private static boolean isTGA(ImageInputStream stream) throws IOException {
        stream.mark();
        ByteOrder originalByteOrder = stream.getByteOrder();

        try {
            stream.setByteOrder(ByteOrder.LITTLE_ENDIAN);

            // NOTE: The original TGA format does not have a magic identifier, so this is guesswork...
            // We'll try to match sane values, and hope no other files contains the same sequence.

            stream.readUnsignedByte();

            int colorMapType = stream.readUnsignedByte();
            switch (colorMapType) {
                case TGA_COLORMAP_NONE:
                case TGA_COLORMAP_PALETTE:
                    break;
                default:
                    return false;
            }

            int imageType = stream.readUnsignedByte();
            switch (imageType) {
                case TGA_IMAGETYPE_NONE:
                case TGA_IMAGETYPE_COLORMAPPED:
                case TGA_IMAGETYPE_TRUECOLOR:
                case TGA_IMAGETYPE_MONOCHROME:
                case TGA_IMAGETYPE_COLORMAPPED_RLE:
                case TGA_IMAGETYPE_TRUECOLOR_RLE:
                case TGA_IMAGETYPE_MONOCHROME_RLE:
                    break;
                default:
                    return false;
            }

            int colorMapStart = stream.readUnsignedShort();
            int colorMapSize = stream.readUnsignedShort();
            int colorMapDepth = stream.readUnsignedByte();

            if (colorMapSize == 0) {
                // No color map, all 3 fields should be 0 (but some files contain bogus colorMapDepth)
                if (colorMapStart != 0 || colorMapDepth != 0
                        && colorMapDepth != 15 && colorMapDepth != 16 && colorMapDepth != 24 && colorMapDepth != 32)  {
                    return false;
                }
            }
            else {
                if (colorMapType == TGA_COLORMAP_NONE) {
                    return false;
                }
                if (colorMapSize < 2) {
                    return false;
                }
                if (colorMapStart >= colorMapSize) {
                    return false;
                }
                if (colorMapDepth != 15 && colorMapDepth != 16 && colorMapDepth != 24 && colorMapDepth != 32) {
                    return false;
                }
            }

            // Skip x, y, w, h as these can be anything
            stream.readShort();
            stream.readShort();
            stream.readShort();
            stream.readShort();

            // Verify sane pixel depth
            int depth = stream.readUnsignedByte();
            switch (depth) {
                case 1:
                case 2:
                case 4:
                case 8:
                case 16:
                case 24:
                case 32:
                    break;
                default:
                    return false;
            }

            // We're pretty sure by now, but there can still be false positives...
            // For 2.0 format, we could skip to end, and read "TRUEVISION-XFILE.\0" but it would be too slow
            // unless we are working with a local file (and the file may still be a valid original TGA without it).
            return true;
        }
        catch (EOFException e) {
            return false;
        }
        finally {
            stream.reset();
            stream.setByteOrder(originalByteOrder);
        }
    }

    @Override
    public boolean writeBufferedImage(BufferedImage im, String mimeType, float quality, ImageOutputStream output) throws IOException {
        ImageWriter writer = getTGAImageWriter();
        if (writer == null) return false;
        writer.setOutput(output);
        ImageWriteParam param = writer.getDefaultWriteParam();
        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionType("RLE");
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

    private static ImageReader getTGAImageReader() {
        Iterator<ImageReader> it = ImageIO.getImageReadersByMIMEType("image/tga");
        if (it.hasNext()) return it.next();
        else return null;
    }

    private static ImageWriter getTGAImageWriter() {
        Iterator<ImageWriter> it = ImageIO.getImageWritersByMIMEType("image/tga");
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
