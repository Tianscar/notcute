package io.notcute.g2d.awt;

import io.notcute.app.Assets;
import io.notcute.g2d.Font;
import io.notcute.g2d.GraphicsKit;
import io.notcute.g2d.Image;
import io.notcute.g2d.MultiFrameImage;
import io.notcute.internal.awt.AWTG2DUtils;
import io.notcute.util.AbstractExpandable;
import io.notcute.util.Expandable;
import io.notcute.util.MathUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AWTGraphicsKit extends AbstractExpandable<AWTGraphicsKit.Expansion> implements GraphicsKit {

    protected static BufferedImage getSupportedBufferedImage(BufferedImage source, String mimeType) {
        if (mimeType.equals("image/jpeg")) {
            return AWTG2DUtils.getImage(source, BufferedImage.TYPE_USHORT_565_RGB);
        }
        else if (mimeType.equals("image/bmp")) {
            return AWTG2DUtils.getImage(source, BufferedImage.TYPE_3BYTE_BGR);
        }
        else return AWTG2DUtils.getImage(source, BufferedImage.TYPE_INT_ARGB);
    }

    public interface Expansion extends Expandable.Expansion {

        BufferedImage readBufferedImage(ImageInputStream stream) throws IOException;
        boolean writeBufferedImage(BufferedImage im, String mimeType, float quality, ImageOutputStream output) throws IOException;

        MultiFrameImage readMultiFrameImage(ImageInputStream stream) throws IOException;
        boolean writeMultiFrameImage(MultiFrameImage im, String mimeType, float quality, ImageOutputStream output) throws IOException;

        String[] getBufferedImageReaderMIMETypes();
        String[] getBufferedImageWriterMIMETypes();
        String[] getMultiFrameImageReaderMIMETypes();
        String[] getMultiFrameImageWriterMIMETypes();

    }

    private BufferedImage readBufferedImage(ImageInputStream input, int type) throws IOException {
        if (input == null) throw new IllegalArgumentException("input source cannot be NULL");
        type = AWTG2DUtils.toAWTBufferedImageType(type);
        BufferedImage image = null;
        for (Expansion expansion : getExpansions()) {
            image = expansion.readBufferedImage(input);
            if (image != null) {
                if (image.getType() != type) {
                    image = AWTG2DUtils.getImage(image, type);
                }
                break;
            }
        }
        return image;
    }

    private boolean writeBufferedImage(BufferedImage image, String mimeType, float quality, ImageOutputStream output) throws IOException {
        if (image == null) throw new IllegalArgumentException("image cannot be NULL");
        if (mimeType == null) throw new IllegalArgumentException("MIME Type cannot be NULL");
        if (output == null) throw new IllegalArgumentException("output cannot be NULL");
        boolean result = false;
        quality = MathUtils.clamp(quality, 0, 1);
        for (Expansion expansion : getExpansions()) {
            result = expansion.writeBufferedImage(image, mimeType, quality, output);
            if (result) break;
        }
        return result;
    }

    private Image readImage(ImageInputStream input, int type) {
        try {
            MultiFrameImage framedImage = readMultiFrameImage(input, type);
            if (framedImage != null) return framedImage;
            BufferedImage image = readBufferedImage(input, AWTG2DUtils.toAWTBufferedImageType(type));
            if (image == null) return null;
            else return new AWTImage(image);
        } catch (IOException e) {
            return null;
        }
    }

    private MultiFrameImage readMultiFrameImage(ImageInputStream input, int type) throws IOException {
        if (input == null) throw new IllegalArgumentException("input source cannot be NULL");
        type = AWTG2DUtils.toAWTBufferedImageType(type);
        MultiFrameImage image = null;
        for (Expansion expansion : getExpansions()) {
            image = expansion.readMultiFrameImage(input);
            if (image != null) {
                MultiFrameImage.Frame frame;
                for (io.notcute.g2d.Image.Frame value : image) {
                    frame = value;
                    if (frame.getImage().getType() != type) {
                        frame.setImage(new AWTImage(AWTG2DUtils.getImage(((AWTImage) frame.getImage()).getBufferedImage(), type)));
                    }
                }
                break;
            }
        }
        return image;
    }

    private boolean writeMultiFrameImage(MultiFrameImage image, String mimeType, float quality, ImageOutputStream output) throws IOException {
        if (image == null) throw new IllegalArgumentException("image cannot be NULL");
        if (mimeType == null) throw new IllegalArgumentException("MIME Type cannot be NULL");
        if (output == null) throw new IllegalArgumentException("output cannot be NULL");
        boolean result = false;
        quality = MathUtils.clamp(quality, 0, 1);
        for (Expansion expansion : getExpansions()) {
            result = expansion.writeMultiFrameImage(image, mimeType, quality, output);
            if (result) break;
        }
        return result;
    }

    @Override
    public Image createImage(int width, int height, int type) {
        return new AWTImage(new BufferedImage(width, height, AWTG2DUtils.toAWTBufferedImageType(type)));
    }

    @Override
    public Image readImage(File input, int type) {
        try {
            return readImage(ImageIO.createImageInputStream(input), type);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Image readImage(InputStream input, int type) {
        try {
            return readImage(ImageIO.createImageInputStream(input), type);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Image readImage(URL input, int type) {
        try {
            return readImage(ImageIO.createImageInputStream(input), type);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Image readImage(Assets assets, String input, int type) {
        return readImage(assets.readAsset(input), type);
    }

    @Override
    public MultiFrameImage readMultiFrameImage(File input, int type) {
        try {
            return readMultiFrameImage(ImageIO.createImageInputStream(input), type);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public MultiFrameImage readMultiFrameImage(InputStream input, int type) {
        try {
            return readMultiFrameImage(ImageIO.createImageInputStream(input), type);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public MultiFrameImage readMultiFrameImage(URL input, int type) {
        try {
            return readMultiFrameImage(ImageIO.createImageInputStream(input), type);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public MultiFrameImage readMultiFrameImage(Assets assets, String input, int type) {
        return readMultiFrameImage(assets.readAsset(input), type);
    }

    public boolean writeImage(Image image, String mimeType, int quality, ImageOutputStream output) {
        try {
            if (image instanceof MultiFrameImage) return writeMultiFrameImage((MultiFrameImage) image, mimeType, quality, output);
            return writeBufferedImage(getSupportedBufferedImage(((AWTImage) image).getBufferedImage(), mimeType), mimeType, quality / 100f, output);
        } catch (IOException e) {
            return false;
        }
    }

    public boolean writeMultiFrameImage(MultiFrameImage image, String mimeType, int quality, ImageOutputStream output) {
        try {
            return writeMultiFrameImage(image, mimeType, quality / 100f, output);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeImage(Image image, String mimeType, int quality, File output) {
        try (ImageOutputStream stream = ImageIO.createImageOutputStream(output)) {
            return writeImage(image, mimeType, quality, stream);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeImage(Image image, String mimeType, int quality, OutputStream output) {
        try (ImageOutputStream stream = ImageIO.createImageOutputStream(output)) {
            return writeImage(image, mimeType, quality, stream);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeMultiFrameImage(MultiFrameImage image, String mimeType, int quality, File output) {
        try (ImageOutputStream stream = ImageIO.createImageOutputStream(output)) {
            return writeMultiFrameImage(image, mimeType, quality, stream);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeMultiFrameImage(MultiFrameImage image, String mimeType, int quality, OutputStream output) {
        try (ImageOutputStream stream = ImageIO.createImageOutputStream(output)) {
            return writeMultiFrameImage(image, mimeType, quality, stream);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String[] getImageReaderMIMETypes() {
        Set<String> mimeTypes = new HashSet<>();
        for (Expansion expansion : getExpansions()) {
            mimeTypes.addAll(Arrays.asList(expansion.getBufferedImageReaderMIMETypes()));
        }
        return mimeTypes.toArray(new String[0]);
    }

    @Override
    public String[] getImageWriterMIMETypes() {
        Set<String> mimeTypes = new HashSet<>();
        for (Expansion expansion : getExpansions()) {
            mimeTypes.addAll(Arrays.asList(expansion.getBufferedImageWriterMIMETypes()));
        }
        return mimeTypes.toArray(new String[0]);
    }

    @Override
    public String[] getMultiFrameImageReaderMIMETypes() {
        Set<String> mimeTypes = new HashSet<>();
        for (Expansion expansion : getExpansions()) {
            mimeTypes.addAll(Arrays.asList(expansion.getMultiFrameImageReaderMIMETypes()));
        }
        return mimeTypes.toArray(new String[0]);
    }

    @Override
    public String[] getMultiFrameImageWriterMIMETypes() {
        Set<String> mimeTypes = new HashSet<>();
        for (Expansion expansion : getExpansions()) {
            mimeTypes.addAll(Arrays.asList(expansion.getMultiFrameImageWriterMIMETypes()));
        }
        return mimeTypes.toArray(new String[0]);
    }

    @Override
    public Font getFont(String familyName, int style) {
        java.awt.Font font = java.awt.Font.decode(familyName);
        if (!familyName.equals(java.awt.Font.DIALOG) && font.getFamily().equals(java.awt.Font.DIALOG)) return null;
        return new AWTFont(font.deriveFont(AWTG2DUtils.toAWTFontStyle(style)));
    }
    
    @Override
    public Font getDefaultFont() {
        return AWTFont.DEFAULT_FONT;
    }

    @Override
    public Font readFont(File input) {
        try {
            java.awt.Font font = AWTG2DUtils.readFont(input);
            return font == null ? null : new AWTFont(font);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Font readFont(Assets assets, String input) {
        try {
            java.awt.Font font = AWTG2DUtils.readFont(assets.readAsset(input));
            return font == null ? null : new AWTFont(font);
        } catch (IOException e) {
            return null;
        }
    }

}
