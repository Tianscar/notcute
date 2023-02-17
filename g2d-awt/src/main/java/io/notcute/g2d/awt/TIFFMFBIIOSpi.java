package io.notcute.g2d.awt;

import io.notcute.g2d.MultiFrameImage;
import io.notcute.g2d.Image;
import io.notcute.internal.awt.AWTG2DUtils;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.plugins.tiff.BaselineTIFFTagSet;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.Iterator;

import static io.notcute.app.javase.JavaSEPlatform.BASELINE_DPI;

public final class TIFFMFBIIOSpi implements MFBIIOServiceProvider {

    private static final String NATIVE_FORMAT_NAME = "com_sun_media_imageio_plugins_tiff_image_1.0";

    private static final String[] READER_MIME_TYPES = new String[] { "image/tiff" };
    private static final String[] WRITER_MIME_TYPES = new String[] { "image/tiff" };

    @Override
    public MultiFrameImage read(ImageInputStream stream) throws IOException {
        if (!isTIFF(stream)) return null;
        ImageReader reader = getTIFFImageReader();
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
                        case BaselineTIFFTagSet.TAG_X_RESOLUTION:
                            tmp = ((IIOMetadataNode) node.getFirstChild().getFirstChild()).getAttribute("value").split("/");
                            xrf = Long.parseLong(tmp[0]);
                            xrd = Long.parseLong(tmp[1]);
                            break;
                        case BaselineTIFFTagSet.TAG_Y_RESOLUTION:
                            tmp = ((IIOMetadataNode) node.getFirstChild().getFirstChild()).getAttribute("value").split("/");
                            yrf = Long.parseLong(tmp[0]);
                            yrd = Long.parseLong(tmp[1]);
                            break;
                        case BaselineTIFFTagSet.TAG_X_POSITION:
                            tmp = ((IIOMetadataNode) node.getFirstChild().getFirstChild()).getAttribute("value").split("/");
                            xpf = Long.parseLong(tmp[0]);
                            xpd = Long.parseLong(tmp[1]);
                            break;
                        case BaselineTIFFTagSet.TAG_Y_POSITION:
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
        return new MultiFrameImage(frames);
    }

    private static boolean isTIFF(ImageInputStream stream) throws IOException {
        byte[] b = new byte[4];
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
        return ((
                        b[0] == (byte) 0x49 &&
                        b[1] == (byte) 0x49 &&
                        b[2] == (byte) 0x2a &&
                        b[3] == (byte) 0x00
                ) || (
                        b[0] == (byte) 0x4d &&
                        b[1] == (byte) 0x4d &&
                        b[2] == (byte) 0x00 &&
                        b[3] == (byte) 0x2a
                ));
    }

    @Override
    public boolean write(MultiFrameImage im, String mimeType, float quality, ImageOutputStream output) throws IOException {
        ImageWriter writer = getTIFFImageWriter();
        if (writer == null) return false;
        writer.setOutput(output);
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionType(quality < 1.0f ? "Deflate" : "LZW");
        param.setCompressionQuality(quality);
        writer.prepareWriteSequence(null);
        ImageTypeSpecifier specifier = ImageTypeSpecifier.createFromBufferedImageType(AWTG2DUtils.toAWTBufferedImageType(im.getGeneralType()));
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
        ifd.appendChild(generateTIFFRational(BaselineTIFFTagSet.TAG_X_RESOLUTION, BASELINE_DPI, 1));
        ifd.appendChild(generateTIFFRational(BaselineTIFFTagSet.TAG_Y_RESOLUTION, BASELINE_DPI, 1));
        ifd.appendChild(generateTIFFRational(BaselineTIFFTagSet.TAG_X_POSITION, frame.getHotSpotX(), BASELINE_DPI));
        ifd.appendChild(generateTIFFRational(BaselineTIFFTagSet.TAG_Y_POSITION, frame.getHotSpotY(), BASELINE_DPI));
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

    private static ImageReader getTIFFImageReader() {
        Iterator<ImageReader> it = ImageIO.getImageReadersByMIMEType("image/tiff");
        if (it.hasNext()) return it.next();
        else return null;
    }

    private static ImageWriter getTIFFImageWriter() {
        Iterator<ImageWriter> it = ImageIO.getImageWritersByMIMEType("image/tiff");
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
