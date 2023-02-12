package io.notcute.g2d.awt;

import com.twelvemonkeys.imageio.metadata.tiff.TIFF;
import io.notcute.g2d.AnimatedImage;
import io.notcute.g2d.Image;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.Iterator;

import static io.notcute.app.javase.JavaSEPlatform.BASELINE_DPI;

public final class TIFFAIIOSpi implements AIIOServiceProvider {

    private static final String NATIVE_FORMAT_NAME = "com_sun_media_imageio_plugins_tiff_image_1.0";

    private static final String[] READER_FORMAT_NAMES = new String[] { "tif", "tiff", "bigtiff" };
    private static final String[] WRITER_FORMAT_NAMES = new String[] { "tif", "tiff", "bigtiff" };

    @Override
    public AnimatedImage read(ImageInputStream stream) throws IOException {
        stream.mark();
        try {
            if (!(isTiff(stream, TIFF.TIFF_MAGIC) || isTiff(stream, TIFF.BIGTIFF_MAGIC))) return null;
        }
        finally {
            stream.reset();
        }
        ImageReader reader = getTiffImageReader();
        if (reader == null) return null;
        reader.setInput(stream);
        Image.Frame[] frames = new Image.Frame[reader.getNumImages(true)];
        IIOMetadataNode ifd;
        IIOMetadataNode node;
        String[] tmp;
        long xrf, xrd, xpf, xpd, yrf, yrd, ypf, ypd;
        xrf = xrd = xpf = xpd = yrf = yrd = ypf = ypd = 1;
        for (int i = 0; i < frames.length; i ++) {
            ifd = (IIOMetadataNode) reader.getImageMetadata(i).getAsTree(NATIVE_FORMAT_NAME).getFirstChild();
            node = (IIOMetadataNode) ifd.getFirstChild();
            while (node != null) {
                if ("TIFFField".equals(node.getNodeName())) {
                    switch (Integer.parseInt(node.getAttribute("number"))) {
                        case TIFF.TAG_X_RESOLUTION:
                            tmp = ((IIOMetadataNode) node.getFirstChild().getFirstChild()).getAttribute("value").split("/");
                            xrf = Long.parseLong(tmp[0]);
                            xrd = Long.parseLong(tmp[1]);
                            break;
                        case TIFF.TAG_Y_RESOLUTION:
                            tmp = ((IIOMetadataNode) node.getFirstChild().getFirstChild()).getAttribute("value").split("/");
                            yrf = Long.parseLong(tmp[0]);
                            yrd = Long.parseLong(tmp[1]);
                            break;
                        case TIFF.TAG_X_POSITION:
                            tmp = ((IIOMetadataNode) node.getFirstChild().getFirstChild()).getAttribute("value").split("/");
                            xpf = Long.parseLong(tmp[0]);
                            xpd = Long.parseLong(tmp[1]);
                            break;
                        case TIFF.TAG_Y_POSITION:
                            tmp = ((IIOMetadataNode) node.getFirstChild().getFirstChild()).getAttribute("value").split("/");
                            ypf = Long.parseLong(tmp[0]);
                            ypd = Long.parseLong(tmp[1]);
                            break;
                    }
                }
                node = (IIOMetadataNode) node.getNextSibling();
            }
            frames[i] = new Image.Frame(new AWTImage(reader.read(i)),
                    (int) (xpf * xrf / xpd / xrd), (int) (ypf * yrf / ypd / yrd), 0);
        }
        try {
            stream.close();
        }
        catch (IOException ignored) {
        }
        reader.dispose();
        return new AnimatedImage(frames);
    }

    private static boolean isTiff(ImageInputStream stream, int versionMagic) throws IOException {
        try {
            byte[] magic = new byte[4];
            stream.readFully(magic, 0, magic.length);
            return magic[0] == 'I' && magic[1] == 'I' && magic[2] == (versionMagic & 0xFF) && magic[3] == (versionMagic >>> 8)
                    || magic[0] == 'M' && magic[1] == 'M' && magic[2] == (versionMagic >>> 8) && magic[3] == (versionMagic & 0xFF);
        }
        catch (EOFException e) {
            return false;
        }
    }

    @Override
    public boolean write(AnimatedImage im, String formatName, float quality, ImageOutputStream output) throws IOException {
        ImageWriter writer = getTiffImageWriter(formatName);
        if (writer == null) return false;
        writer.setOutput(output);
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionType(quality < 1.0f ? "Deflate" : "LZW");
        param.setCompressionQuality(quality);
        writer.prepareWriteSequence(null);
        ImageTypeSpecifier specifier = ImageTypeSpecifier.createFromBufferedImageType(Util.toAWTBufferedImageType(im.getGeneralType()));
        IIOMetadata metadata = writer.getDefaultImageMetadata(specifier, param);
        for (Image.Frame frame : im) {
            metadata.mergeTree(NATIVE_FORMAT_NAME, generateMetadata(frame));
            writer.writeToSequence(new IIOImage(((AWTImage)frame.getImage()).getBufferedImage(), null, metadata), param);
        }
        writer.endWriteSequence();
        output.flush();
        writer.dispose();
        return true;
    }

    private static IIOMetadataNode generateMetadata(Image.Frame frame) {
        IIOMetadataNode root = new IIOMetadataNode(NATIVE_FORMAT_NAME);
        IIOMetadataNode ifd = new IIOMetadataNode("TIFFIFD");
        ifd.appendChild(generateTIFFRational(TIFF.TAG_X_RESOLUTION, BASELINE_DPI, 1));
        ifd.appendChild(generateTIFFRational(TIFF.TAG_Y_RESOLUTION, BASELINE_DPI, 1));
        ifd.appendChild(generateTIFFRational(TIFF.TAG_X_POSITION, frame.getHotSpotX(), BASELINE_DPI));
        ifd.appendChild(generateTIFFRational(TIFF.TAG_Y_POSITION, frame.getHotSpotY(), BASELINE_DPI));
        root.appendChild(ifd);
        return root;
    }

    private static IIOMetadataNode generateTIFFRational(int number, long fraction, long denominator) {
        IIOMetadataNode field = new IIOMetadataNode("TIFFField");
        field.setAttribute("number", Integer.toString(number));
        IIOMetadataNode type = new IIOMetadataNode("TIFFRationals");
        IIOMetadataNode value = new IIOMetadataNode("TIFFRational");
        value.setAttribute("value", fraction + "/" + denominator);
        type.appendChild(value);
        field.appendChild(type);
        return field;
    }

    private static ImageReader getTiffImageReader() {
        Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("tif");
        if (it.hasNext()) return it.next();
        it = ImageIO.getImageReadersByFormatName("bigtiff");
        if (it.hasNext()) return it.next();
        return null;
    }

    private static ImageWriter getTiffImageWriter(String formatName) {
        return Util.getImageWriter(WRITER_FORMAT_NAMES, formatName);
    }

    @Override
    public String[] getReaderFormatNames() {
        return READER_FORMAT_NAMES.clone();
    }

    @Override
    public String[] getWriterFormatNames() {
        return WRITER_FORMAT_NAMES.clone();
    }

}
