package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.app.A3Assets;
import com.ansdoship.a3wt.graphics.A3Font;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.graphics.A3GraphicsKit;
import com.ansdoship.a3wt.graphics.A3Path;

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
import static com.ansdoship.a3wt.util.A3Asserts.checkArgNotEmpty;
import static com.ansdoship.a3wt.util.A3Asserts.checkArgNotNull;

public class AWTA3GraphicsKit implements A3GraphicsKit {

    @Override
    public A3Image createImage(final int width, final int height) {
        return new AWTA3Image(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
    }

    @Override
    public A3Image readImage(final File input) {
        try {
            return new AWTA3Image(ImageIO.read(input));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public A3Image readImage(final InputStream input) {
        checkArgNotNull(input, "input");
        try {
            return new AWTA3Image(ImageIO.read(input));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public A3Image readImage(final URL input) {
        checkArgNotNull(input, "input");
        try {
            return new AWTA3Image(ImageIO.read(input));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public A3Image readImage(final A3Assets assets, final String input) {
        checkArgNotNull(assets, "assets");
        return readImage(assets.readAsset(input));
    }

    @Override
    public boolean writeImage(final A3Image image, final String formatName, final File output) {
        checkArgNotNull(image, "image");
        try {
            return ImageIO.write(((AWTA3Image)image).getBufferedImage(), formatName, output);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean writeImage(final A3Image image, final String formatName, final OutputStream output) {
        checkArgNotNull(image, "image");
        try {
            return ImageIO.write(((AWTA3Image)image).getBufferedImage(), formatName, output);
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

}
