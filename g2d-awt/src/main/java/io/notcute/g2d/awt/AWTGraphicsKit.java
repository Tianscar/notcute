package io.notcute.g2d.awt;

import io.notcute.app.Assets;
import io.notcute.g2d.MultiFrameImage;
import io.notcute.g2d.Font;
import io.notcute.g2d.GraphicsKit;
import io.notcute.g2d.Image;
import io.notcute.internal.awt.AWTG2DUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class AWTGraphicsKit implements GraphicsKit {

    protected static BufferedImage getSupportedImage(BufferedImage source, String format) {
        if (format.equals("image/jpeg")) {
            return AWTG2DUtils.getImage(source, BufferedImage.TYPE_USHORT_565_RGB);
        }
        else if (format.equals("image/bmp")) {
            return AWTG2DUtils.getImage(source, BufferedImage.TYPE_3BYTE_BGR);
        }
        else return AWTG2DUtils.getImage(source, BufferedImage.TYPE_INT_ARGB);
    }

    public Image readImage(ImageInputStream input, int type) {
        try {
            MultiFrameImage framedImage = readMultiFrameImage(input, type);
            if (framedImage != null) return framedImage;
            BufferedImage image = BufferedImageIO.read(input, AWTG2DUtils.toAWTBufferedImageType(type));
            if (image == null) return null;
            else return new AWTImage(image);
        } catch (IOException e) {
            return null;
        }
    }

    public MultiFrameImage readMultiFrameImage(ImageInputStream input, int type) {
        try {
            return MultiFrameBufferedImageIO.read(input, AWTG2DUtils.toAWTBufferedImageType(type));
        } catch (IOException e) {
            return null;
        }
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
            return BufferedImageIO.write(getSupportedImage(((AWTImage) image).getBufferedImage(), mimeType), mimeType, quality / 100f, output);
        } catch (IOException e) {
            return false;
        }
    }

    public boolean writeMultiFrameImage(MultiFrameImage image, String mimeType, int quality, ImageOutputStream output) {
        try {
            return MultiFrameBufferedImageIO.write(image, mimeType, quality / 100f, output);
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
        return BufferedImageIO.getReaderMIMETypes();
    }

    @Override
    public String[] getImageWriterMIMETypes() {
        return BufferedImageIO.getWriterMIMETypes();
    }

    @Override
    public String[] getMultiFrameImageReaderMIMETypes() {
        return MultiFrameBufferedImageIO.getReaderMIMETypes();
    }

    @Override
    public String[] getMultiFrameImageWriterMIMETypes() {
        return MultiFrameBufferedImageIO.getWriterMIMETypes();
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
