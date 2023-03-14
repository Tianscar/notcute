package io.notcute.internal.awt;

import io.notcute.g2d.Image;
import io.notcute.g2d.MultiFrameImage;
import io.notcute.g2d.awt.AWTGraphicsKit;
import io.notcute.g2d.awt.AWTImage;
import io.notcute.util.MathUtils;

import javax.imageio.*;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.EOFException;
import java.io.IOException;
import java.util.Iterator;

public final class APNGAWTGraphicsKitExpansion implements AWTGraphicsKit.Expansion {

    private static final int acTL_TYPE = 0x6163544C;

    private static final String NATIVE_FORMAT_NAME = "javax_imageio_png_1.0";

    private static final String[] READER_MIME_TYPES = new String[] { "image/apng" };
    private static final String[] WRITER_MIME_TYPES = new String[] { "image/apng" };

    @Override
    public BufferedImage readBufferedImage(ImageInputStream stream) throws IOException {
        return null;
    }

    @Override
    public boolean writeBufferedImage(BufferedImage im, String mimeType, float quality, ImageOutputStream output) throws IOException {
        return false;
    }

    private static int parseAPNGDisposalOp(String disposalOp) {
        if ("background".equals(disposalOp)) return Image.DisposalMode.BACKGROUND;
        else if ("previous".equals(disposalOp)) return Image.DisposalMode.PREVIOUS;
        else return Image.DisposalMode.NONE;
    }

    private static int parseAPNGBlendOp(String blendOp) {
        if ("over".equals(blendOp)) return Image.BlendMode.OVER;
        else return Image.BlendMode.SOURCE;
    }

    @Override
    public MultiFrameImage readMultiFrameImage(ImageInputStream stream) throws IOException {
        if (!isAPNG(stream)) return null;
        ImageReader reader = getAPNGImageReader();
        if (reader == null) return null;
        reader.setInput(stream);
        Image.Frame[] frames = new Image.Frame[reader.getNumImages(true)];
        IIOMetadataNode node;
        int hotSpotX = 0, hotSpotY = 0, loops = 0;
        long duration = 0;
        int disposalMode = Image.DisposalMode.NONE;
        int blendMode = Image.BlendMode.SOURCE;
        for (int i = 0; i < frames.length; i ++) {
            node = (IIOMetadataNode) reader.getImageMetadata(i).getAsTree(NATIVE_FORMAT_NAME).getFirstChild();
            String name;
            while (node != null) {
                name = node.getNodeName();
                switch (name) {
                    case "acTL":
                        if (i == 0) loops = Integer.parseInt(node.getAttribute("num_plays")) - 1;
                        continue;
                    case "fcTL":
                        hotSpotX = Integer.parseInt(node.getAttribute("x_offset"));
                        hotSpotY = Integer.parseInt(node.getAttribute("y_offset"));
                        duration = Integer.parseInt(node.getAttribute("delay_num")) * 1000L /
                                Integer.parseInt(node.getAttribute("delay_den"));
                        disposalMode = parseAPNGDisposalOp(node.getAttribute("dispose_op"));
                        blendMode = parseAPNGBlendOp(node.getAttribute("blend_op"));
                        break;
                }
                node = (IIOMetadataNode) node.getNextSibling();
            }
            frames[i] = new Image.Frame(new AWTImage(reader.read(i)), hotSpotX, hotSpotY, duration, disposalMode, blendMode);
        }
        try {
            stream.close();
        }
        catch (IOException ignored) {
        }
        reader.dispose();
        MultiFrameImage result = new MultiFrameImage(frames);
        result.setLooping(loops);
        return result;
    }

    private static ImageReader getAPNGImageReader() {
        Iterator<ImageReader> it = ImageIO.getImageReadersByMIMEType("image/apng");
        if (it.hasNext()) return it.next();
        return null;
    }

    private static boolean isAPNG(ImageInputStream stream) throws IOException {
        stream.mark();
        try {
            byte[] signature = new byte[8];
            stream.readFully(signature);
            if (signature[0] != (byte)137 ||
                    signature[1] != (byte)80 ||
                    signature[2] != (byte)78 ||
                    signature[3] != (byte)71 ||
                    signature[4] != (byte)13 ||
                    signature[5] != (byte)10 ||
                    signature[6] != (byte)26 ||
                    signature[7] != (byte)10) {
                return false;
            }
            while (true) {
                int chunkLength = stream.readInt();
                if (chunkLength < 0 || chunkLength + 4 < 0) {
                    return false;
                }
                int chunkType = stream.readInt();
                if (chunkType == acTL_TYPE) return true;
                else stream.skipBytes(chunkLength + 4);
            }
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
        ImageWriter writer = getAPNGImageWriter();
        if (writer == null) return false;
        writer.setOutput(output);
        ImageWriteParam param = writer.getDefaultWriteParam();
        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionType("Deflate");
            param.setCompressionQuality(quality);
        }
        writer.prepareWriteSequence(null);
        ImageTypeSpecifier specifier = ImageTypeSpecifier.createFromBufferedImageType(AWTG2DUtils.toAWTBufferedImageType(im.getGeneralType()));
        IIOMetadata metadata = writer.getDefaultImageMetadata(specifier, param);
        merge_acTL(metadata, im);
        for (MultiFrameImage.Frame frame : im) {
            merge_fcTL(metadata, frame);
            writer.writeToSequence(new IIOImage(((AWTImage)frame.getImage()).getBufferedImage(), null, metadata), param);
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

    private static void merge_acTL(IIOMetadata metadata, MultiFrameImage image) throws IIOInvalidTreeException {
        IIOMetadataNode node = new IIOMetadataNode(NATIVE_FORMAT_NAME);
        IIOMetadataNode acTL = new IIOMetadataNode("acTL");
        acTL.setAttribute("num_plays", Integer.toString(image.getLooping() + 1));
        acTL.setAttribute("num_frames", Integer.toString(image.size()));
        node.appendChild(acTL);
        metadata.mergeTree(NATIVE_FORMAT_NAME, node);
    }

    private static void merge_fcTL(IIOMetadata metadata, Image.Frame frame) throws IIOInvalidTreeException {
        Image image = frame.getImage();
        IIOMetadataNode node = new IIOMetadataNode(NATIVE_FORMAT_NAME);
        IIOMetadataNode fcTL = new IIOMetadataNode("fcTL");
        fcTL.setAttribute("sequence_number", "0");
        fcTL.setAttribute("width", Integer.toString(image.getWidth()));
        fcTL.setAttribute("height", Integer.toString(image.getHeight()));
        fcTL.setAttribute("x_offset", Integer.toString(frame.getHotSpotX()));
        fcTL.setAttribute("y_offset", Integer.toString(frame.getHotSpotY()));
        long num = frame.getDuration();
        long den = 1000;
        long gcd = MathUtils.gcd(num, den);
        num = num / gcd;
        den = den / gcd;
        if (num > Integer.MAX_VALUE) throw new IIOInvalidTreeException("frame duration too large: " + frame.getDuration() +
                " (max: " + Integer.MAX_VALUE * 10L + ")", fcTL);
        fcTL.setAttribute("delay_num", Long.toString(num));
        fcTL.setAttribute("delay_den", Long.toString(den));
        fcTL.setAttribute("dispose_op", generateAPNGDisposalOp(frame.getDisposal()));
        fcTL.setAttribute("blend_op", generateAPNGBlendlOp(frame.getBlend()));
        node.appendChild(fcTL);
        metadata.mergeTree(NATIVE_FORMAT_NAME, node);
    }

    private static String generateAPNGDisposalOp(int disposalMode) {
        switch (disposalMode) {
            case Image.DisposalMode.PREVIOUS:
                return "previous";
            case Image.DisposalMode.BACKGROUND:
                return "background";
            default:
                return "none";
        }
    }

    private static String generateAPNGBlendlOp(int blendMode) {
        return blendMode == Image.BlendMode.OVER ? "over" : "source";
    }

    private static ImageWriter getAPNGImageWriter() {
        Iterator<ImageWriter> it = ImageIO.getImageWritersByMIMEType("image/apng");
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
