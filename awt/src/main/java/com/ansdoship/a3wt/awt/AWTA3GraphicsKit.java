package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.app.A3Assets;
import com.ansdoship.a3wt.graphics.A3GraphicsKit;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.graphics.A3FramedImage;
import com.ansdoship.a3wt.graphics.A3Font;
import com.ansdoship.a3wt.graphics.A3Cursor;
import com.ansdoship.a3wt.graphics.A3Path;

import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.Font;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import static com.ansdoship.a3wt.awt.A3AWTUtils.fontStyle2AWTFontStyle;
import static com.ansdoship.a3wt.awt.A3AWTUtils.getRGBImage;
import static com.ansdoship.a3wt.awt.A3AWTUtils.getBGRImage;
import static com.ansdoship.a3wt.awt.A3AWTUtils.getARGBImage;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotEmpty;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class AWTA3GraphicsKit implements A3GraphicsKit {

    protected static BufferedImage getSupportedImage(final BufferedImage source, final String format) {
        checkArgNotNull(source, "source");
        checkArgNotNull(format, "format");
        if (format.equalsIgnoreCase("jpg") || format.equalsIgnoreCase("jpeg")) {
            return getRGBImage(source);
        }
        else if (format.equalsIgnoreCase("bmp")) {
            return getBGRImage(source);
        }
        else return source;
    }

    @Override
    public A3Image createImage(final int width, final int height) {
        return new AWTA3Image(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
    }

    public A3Image readImage(final ImageInputStream stream) {
        try {
            final A3FramedImage framedImage = readFramedImage(stream);
            if (framedImage != null) return framedImage;
            final BufferedImage image = ImageIO.read(stream);
            if (image == null) return null;
            else return new AWTA3Image(getARGBImage(image));
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3Image readImage(final File input) {
        checkArgNotNull(input, "input");
        try {
            return readImage(ImageIO.createImageInputStream(input));
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3Image readImage(final InputStream input) {
        checkArgNotNull(input, "input");
        try {
            return readImage(ImageIO.createImageInputStream(input));
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3Image readImage(final URL input) {
        checkArgNotNull(input, "input");
        try {
            return readImage(ImageIO.createImageInputStream(input));
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3Image readImage(final A3Assets assets, final String input) {
        checkArgNotNull(assets, "assets");
        return readImage(assets.readAsset(input));
    }

    public A3FramedImage readFramedImage(final ImageInputStream input) {
        try {
            return FramedImageIO.read(input);
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3FramedImage readFramedImage(final File input) {
        checkArgNotNull(input, "input");
        try {
            return readFramedImage(ImageIO.createImageInputStream(input));
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3FramedImage readFramedImage(final InputStream input) {
        checkArgNotNull(input, "input");
        try {
            return readFramedImage(ImageIO.createImageInputStream(input));
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3FramedImage readFramedImage(final URL input) {
        checkArgNotNull(input, "input");
        try {
            return readFramedImage(ImageIO.createImageInputStream(input));
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3FramedImage readFramedImage(final A3Assets assets, final String input) {
        checkArgNotNull(assets, "assets");
        return readFramedImage(assets.readAsset(input));
    }

    public boolean writeImage(final A3Image image, final String formatName, final int quality, final ImageOutputStream stream) {
        try {
            if (image instanceof A3FramedImage) return writeFramedImage((A3FramedImage) image, formatName, quality, stream);
            return ImageIO.write(getSupportedImage(((AWTA3Image)image).getBufferedImage(), formatName), formatName, quality / 100f, stream);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeImage(final A3Image image, final String formatName, final int quality, final File output) {
        checkArgNotNull(image, "image");
        try (final ImageOutputStream stream = ImageIO.createImageOutputStream(output)) {
            return writeImage(image, formatName, quality, stream);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeImage(final A3Image image, final String formatName, final int quality, final OutputStream output) {
        checkArgNotNull(image, "image");
        try (final ImageOutputStream stream = ImageIO.createImageOutputStream(output)) {
            return writeImage(image, formatName, quality, stream);
        } catch (IOException e) {
            return false;
        }
    }

    public boolean writeFramedImage(final A3FramedImage image, final String formatName, final int quality, final ImageOutputStream output) {
        try {
            return FramedImageIO.write(image, formatName, quality / 100f, output);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeFramedImage(final A3FramedImage image, final String formatName, final int quality, final File output) {
        checkArgNotNull(image, "image");
        try (final ImageOutputStream stream = ImageIO.createImageOutputStream(output)) {
            return writeFramedImage(image, formatName, quality, stream);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeFramedImage(final A3FramedImage image, final String formatName, final int quality, final OutputStream output) {
        checkArgNotNull(image, "image");
        try (final ImageOutputStream stream = ImageIO.createImageOutputStream(output)) {
            return writeFramedImage(image, formatName, quality, stream);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String[] getImageReaderFormatNames() {
        final Set<String> formatNames = new HashSet<>();
        for (String formatName : ImageIO.getReaderFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public String[] getImageWriterFormatNames() {
        final Set<String> formatNames = new HashSet<>();
        for (String formatName : ImageIO.getWriterFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public A3Path createPath() {
        return new AWTA3Path(new Path2D.Float());
    }

    @Override
    public A3Font readFont(final File input) {
        try {
            final Font font = A3AWTUtils.readFont(input);
            return font == null ? null : new AWTA3Font(font);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public A3Font readFont(final A3Assets assets, final String input) {
        checkArgNotNull(assets, "assets");
        try {
            final Font font = A3AWTUtils.readFont(assets.readAsset(input));
            return font == null ? null : new AWTA3Font(font);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public A3Font readFont(final String familyName, final int style) {
        checkArgNotEmpty(familyName, "familyName");
        final Font font = Font.decode(familyName);
        if (!familyName.equals(Font.DIALOG) && font.getFamily().equals(Font.DIALOG)) return null;
        return new AWTA3Font(font.deriveFont(fontStyle2AWTFontStyle(style)));
    }

    protected static final AWTA3Font DEFAULT_FONT = new AWTA3Font(A3AWTUtils.getDefaultFont());

    @Override
    public A3Font getDefaultFont() {
        return DEFAULT_FONT;
    }

    @Override
    public A3Cursor createCursor(final int type) {
        return new AWTA3Cursor(type);
    }

    @Override
    public A3Cursor createCursor(final A3Image image) {
        if (image instanceof A3FramedImage) return new AWTA3Cursor((AWTA3Image) ((A3FramedImage)image).get());
        return new AWTA3Cursor((AWTA3Image) image);
    }

    @Override
    public A3Cursor getDefaultCursor() {
        return AWTA3Cursor.getDefaultCursor();
    }

}
