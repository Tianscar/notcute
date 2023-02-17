package io.notcute.g2d.awt;

import io.notcute.g2d.MultiFrameImage;
import io.notcute.g2d.Image;
import io.notcute.internal.awt.AWTG2DUtils;
import io.notcute.util.MathUtils;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public final class GIFMFBIIOSpi implements MFBIIOServiceProvider {

    private static final String NATIVE_FORMAT_NAME = "javax_imageio_gif_image_1.0";

    private static final String[] READER_MIME_TYPES = new String[] { "image/gif" };
    private static final String[] WRITER_MIME_TYPES = new String[] { "image/gif" };

    @Override
    public MultiFrameImage read(ImageInputStream stream) throws IOException {
        if (!isGIF(stream)) return null;
        ImageReader reader = getGIFImageReader();
        if (reader == null) return null;
        reader.setInput(stream);
        Image.Frame[] frames = new Image.Frame[reader.getNumImages(true)];
        IIOMetadataNode node;
        int hotSpotX = 0, hotSpotY = 0, loops = 0;
        long duration = 0;
        for (int i = 0; i < frames.length; i ++) {
            node = (IIOMetadataNode) reader.getImageMetadata(i).getAsTree(NATIVE_FORMAT_NAME).getFirstChild();
            String name;
            while (node != null) {
                name = node.getNodeName();
                switch (name) {
                    case "ImageDescriptor":
                        hotSpotX = Integer.parseInt(node.getAttribute("imageLeftPosition"));
                        hotSpotY = Integer.parseInt(node.getAttribute("imageTopPosition"));
                        break;
                    case "GraphicControlExtension":
                        duration = Integer.parseInt(node.getAttribute("delayTime")) * 10L;
                        break;
                    case "ApplicationExtensions":
                        IIOMetadataNode appExtNode = (IIOMetadataNode) node.getFirstChild();
                        while (appExtNode != null) {
                            if (appExtNode.getNodeName().equals("ApplicationExtension") &&
                                    appExtNode.getAttribute("applicationID").equals("NETSCAPE") &&
                                    appExtNode.getAttribute("authenticationCode").equals("2.0")) {
                                Object userObject = appExtNode.getUserObject();
                                if (userObject instanceof byte[]) {
                                    loops = ByteBuffer.wrap((byte[]) userObject).getShort() - 1;
                                }
                            }
                            appExtNode = (IIOMetadataNode) appExtNode.getNextSibling();
                        }
                        break;
                }
                node = (IIOMetadataNode) node.getNextSibling();
            }
            frames[i] = new Image.Frame(new AWTImage(reader.read(i)), hotSpotX, hotSpotY, duration);
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

    private static ImageReader getGIFImageReader() {
        Iterator<ImageReader> it = ImageIO.getImageReadersByMIMEType("image/gif");
        if (it.hasNext()) return it.next();
        return null;
    }

    private static boolean isGIF(ImageInputStream stream) throws IOException {
        StringBuilder id = new StringBuilder();
        stream.mark();
        try {
            for (int i = 0; i < 6; i ++) {
                id.append((char) stream.read());
            }
        }
        catch (EOFException e) {
            return false;
        }
        finally {
            stream.reset();
        }
        return id.toString().startsWith("GIF");
    }

    @Override
    public boolean write(MultiFrameImage im, String mimeType, float quality, ImageOutputStream output) throws IOException {
        ImageWriter writer = getGIFImageWriter();
        if (writer == null) return false;
        writer.setOutput(output);
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionType("LZW");
        param.setCompressionQuality(quality);
        writer.prepareWriteSequence(null);
        ImageTypeSpecifier specifier = ImageTypeSpecifier.createFromBufferedImageType(AWTG2DUtils.toAWTBufferedImageType(im.getGeneralType()));
        IIOMetadata metadata = writer.getDefaultImageMetadata(specifier, param);
        IIOMetadataNode metadataTree = (IIOMetadataNode) metadata.getAsTree(NATIVE_FORMAT_NAME);
        mergeMetadata(metadataTree, im);
        for (MultiFrameImage.Frame frame : im) {
            mergeMetadata(metadataTree, frame);
            metadata.mergeTree(NATIVE_FORMAT_NAME, metadataTree);
            writer.writeToSequence(new IIOImage(((AWTImage)frame.getImage()).getBufferedImage(), null, metadata), param);
        }
        writer.endWriteSequence();
        output.flush();
        writer.dispose();
        return true;
    }

    private static void mergeMetadata(IIOMetadataNode root, MultiFrameImage image) {
        IIOMetadataNode node = new IIOMetadataNode("ApplicationExtensions");
        IIOMetadataNode appExtNode = new IIOMetadataNode("ApplicationExtension");
        appExtNode.setAttribute("applicationID", "NETSCAPE");
        appExtNode.setAttribute("authenticationCode", "2.0");
        appExtNode.setUserObject(Integer.toString(image.getLooping() + 1).getBytes(StandardCharsets.UTF_8));
        node.appendChild(appExtNode);
        root.appendChild(node);
    }

    private static void mergeMetadata(IIOMetadataNode root, Image.Frame frame) {
        Image image = frame.getImage();
        IIOMetadataNode node = (IIOMetadataNode) root.getFirstChild();
        String name;
        while (node != null) {
            name = node.getNodeName();
            if (name.equals("ImageDescriptor")) {
                node.setAttribute("imageLeftPosition",
                        Integer.toString(frame.getHotSpotX()));
                node.setAttribute("imageTopPosition",
                        Integer.toString(frame.getHotSpotY()));
                node.setAttribute("imageWidth", Integer.toString(image.getWidth()));
                node.setAttribute("imageHeight", Integer.toString(image.getHeight()));
            }
            else if (name.equals("GraphicControlExtension")) {
                node.setAttribute("disposalMethod","restoreToBackgroundColor");
                node.setAttribute("delayTime",
                        Integer.toString(MathUtils.clamp(frame.getDuration() / 10L)));
            }
            node = (IIOMetadataNode) node.getNextSibling();
        }
    }

    private static ImageWriter getGIFImageWriter() {
        Iterator<ImageWriter> it = ImageIO.getImageWritersByMIMEType("image/gif");
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
