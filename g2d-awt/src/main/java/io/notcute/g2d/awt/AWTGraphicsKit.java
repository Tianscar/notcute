package io.notcute.g2d.awt;

import io.notcute.app.Assets;
import io.notcute.g2d.*;

import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class AWTGraphicsKit implements GraphicsKit {

    protected static BufferedImage getSupportedImage(BufferedImage source, String format) {
        if (format.equalsIgnoreCase("jpg") || format.equalsIgnoreCase("jpeg")) {
            return Util.getImage(source, BufferedImage.TYPE_USHORT_565_RGB);
        }
        else if (format.equalsIgnoreCase("bmp")) {
            return Util.getImage(source, BufferedImage.TYPE_3BYTE_BGR);
        }
        else return Util.getImage(source, BufferedImage.TYPE_INT_ARGB);
    }

    public Image readImage(ImageInputStream input, int type) {
        try {
            AnimatedImage framedImage = readAnimatedImage(input, type);
            if (framedImage != null) return framedImage;
            BufferedImage image = ImageIO.read(input);
            if (image == null) return null;
            else return new AWTImage(Util.getImage(image, Util.toAWTBufferedImageType(type)));
        } catch (IOException e) {
            return null;
        }
    }

    public AnimatedImage readAnimatedImage(ImageInputStream input, int type) {
        try {
            return AnimatedImageIO.read(input, Util.toAWTBufferedImageType(type));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Image createImage(int width, int height, int type) {
        return new AWTImage(new BufferedImage(width, height, Util.toAWTBufferedImageType(type)));
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
    public AnimatedImage readAnimatedImage(File input, int type) {
        try {
            return readAnimatedImage(ImageIO.createImageInputStream(input), type);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public AnimatedImage readAnimatedImage(InputStream input, int type) {
        try {
            return readAnimatedImage(ImageIO.createImageInputStream(input), type);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public AnimatedImage readAnimatedImage(URL input, int type) {
        try {
            return readAnimatedImage(ImageIO.createImageInputStream(input), type);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public AnimatedImage readAnimatedImage(Assets assets, String input, int type) {
        return readAnimatedImage(assets.readAsset(input), type);
    }

    public boolean writeImage(Image image, String formatName, int quality, ImageOutputStream output) {
        try {
            if (image instanceof AnimatedImage) return writeAnimatedImage((AnimatedImage) image, formatName, quality, output);
            return ImageIO.write(getSupportedImage(((AWTImage)image).getBufferedImage(), formatName), formatName, quality / 100f, output);
        } catch (IOException e) {
            return false;
        }
    }

    public boolean writeAnimatedImage(AnimatedImage image, String formatName, int quality, ImageOutputStream output) {
        try {
            return AnimatedImageIO.write(image, formatName, quality / 100f, output);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeImage(Image image, String formatName, int quality, File output) {
        try (ImageOutputStream stream = ImageIO.createImageOutputStream(output)) {
            return writeImage(image, formatName, quality, stream);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeImage(Image image, String formatName, int quality, OutputStream output) {
        try (ImageOutputStream stream = ImageIO.createImageOutputStream(output)) {
            return writeImage(image, formatName, quality, stream);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeAnimatedImage(AnimatedImage image, String formatName, int quality, File output) {
        try (ImageOutputStream stream = ImageIO.createImageOutputStream(output)) {
            return writeAnimatedImage(image, formatName, quality, stream);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeAnimatedImage(AnimatedImage image, String formatName, int quality, OutputStream output) {
        try (ImageOutputStream stream = ImageIO.createImageOutputStream(output)) {
            return writeAnimatedImage(image, formatName, quality, stream);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String[] getImageReaderFormatNames() {
        Set<String> formatNames = new HashSet<>();
        for (String formatName : ImageIO.getReaderFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        for (String formatName : AnimatedImageIO.getReaderFormatNames()) {
            formatNames.remove(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public String[] getImageWriterFormatNames() {
        Set<String> formatNames = new HashSet<>();
        for (String formatName : ImageIO.getWriterFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        for (String formatName : AnimatedImageIO.getWriterFormatNames()) {
            formatNames.remove(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public String[] getAnimatedImageReaderFormatNames() {
        Set<String> formatNames = new HashSet<>();
        for (String formatName : AnimatedImageIO.getReaderFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public String[] getAnimatedImageWriterFormatNames() {
        Set<String> formatNames = new HashSet<>();
        for (String formatName : AnimatedImageIO.getWriterFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public Font getFont(String familyName, int style) {
        java.awt.Font font = java.awt.Font.decode(familyName);
        if (!familyName.equals(java.awt.Font.DIALOG) && font.getFamily().equals(java.awt.Font.DIALOG)) return null;
        return new AWTFont(font.deriveFont(Util.toAWTFontStyle(style)));
    }
    
    @Override
    public Font getDefaultFont() {
        return AWTFont.DEFAULT_FONT;
    }

    @Override
    public Font readFont(File input) {
        try {
            java.awt.Font font = Util.readFont(input);
            return font == null ? null : new AWTFont(font);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Font readFont(Assets assets, String input) {
        try {
            java.awt.Font font = Util.readFont(assets.readAsset(input));
            return font == null ? null : new AWTFont(font);
        } catch (IOException e) {
            return null;
        }
    }

}
