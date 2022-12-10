package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.app.A3Assets;
import com.ansdoship.a3wt.graphics.A3GraphicsKit;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.graphics.A3FramedImage;
import com.ansdoship.a3wt.graphics.A3Font;
import com.ansdoship.a3wt.graphics.A3Cursor;
import com.ansdoship.a3wt.graphics.A3Arc;
import com.ansdoship.a3wt.graphics.A3Line;
import com.ansdoship.a3wt.graphics.A3QuadCurve;
import com.ansdoship.a3wt.graphics.A3CubicCurve;
import com.ansdoship.a3wt.graphics.A3Path;
import com.ansdoship.a3wt.graphics.A3Coordinate;
import com.ansdoship.a3wt.graphics.A3Point;
import com.ansdoship.a3wt.graphics.A3Area;
import com.ansdoship.a3wt.graphics.A3Rect;
import com.ansdoship.a3wt.graphics.A3Oval;
import com.ansdoship.a3wt.graphics.A3RoundRect;
import com.ansdoship.a3wt.graphics.A3Dimension;
import com.ansdoship.a3wt.graphics.A3Size;
import com.ansdoship.a3wt.graphics.A3Transform;

import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import static com.ansdoship.a3wt.awt.A3AWTUtils.fontStyle2AWTFontStyle;
import static com.ansdoship.a3wt.awt.A3AWTUtils.getRGB565Image;
import static com.ansdoship.a3wt.awt.A3AWTUtils.getARGB8888Image;
import static com.ansdoship.a3wt.awt.A3AWTUtils.imageType2BufferedImageType;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotEmpty;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class AWTA3GraphicsKit implements A3GraphicsKit {

    protected static BufferedImage getSupportedImage(final BufferedImage source, final String format) {
        checkArgNotNull(source, "source");
        checkArgNotNull(format, "format");
        if (format.equalsIgnoreCase("jpg") || format.equalsIgnoreCase("jpeg")) {
            return getRGB565Image(source);
        }
        else if (format.equalsIgnoreCase("bmp")) {
            return A3AWTUtils.getImage(source, BufferedImage.TYPE_3BYTE_BGR);
        }
        else return getARGB8888Image(source);
    }

    @Override
    public A3Image createImage(final int width, final int height, final int type) {
        return new AWTA3Image(new BufferedImage(width, height, imageType2BufferedImageType(type)));
    }

    public A3Image readImage(final ImageInputStream stream, final int type) {
        try {
            final A3FramedImage framedImage = readFramedImage(stream, type);
            if (framedImage != null) return framedImage;
            final BufferedImage image = ImageIO.read(stream);
            if (image == null) return null;
            else return new AWTA3Image(A3AWTUtils.getImage(image, imageType2BufferedImageType(type)));
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3Image readImage(final File input, final int type) {
        checkArgNotNull(input, "input");
        try {
            return readImage(ImageIO.createImageInputStream(input), type);
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3Image readImage(final InputStream input, final int type) {
        checkArgNotNull(input, "input");
        try {
            return readImage(ImageIO.createImageInputStream(input), type);
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3Image readImage(final URL input, final int type) {
        checkArgNotNull(input, "input");
        try {
            return readImage(ImageIO.createImageInputStream(input), type);
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3Image readImage(final A3Assets assets, final String input, final int type) {
        checkArgNotNull(assets, "assets");
        return readImage(assets.readAsset(input), type);
    }

    public A3FramedImage readFramedImage(final ImageInputStream input, final int type) {
        try {
            return FramedImageIO.read(input, imageType2BufferedImageType(type));
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3FramedImage readFramedImage(final File input, final int type) {
        checkArgNotNull(input, "input");
        try {
            return readFramedImage(ImageIO.createImageInputStream(input), type);
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3FramedImage readFramedImage(final InputStream input, final int type) {
        checkArgNotNull(input, "input");
        try {
            return readFramedImage(ImageIO.createImageInputStream(input), type);
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3FramedImage readFramedImage(final URL input, final int type) {
        checkArgNotNull(input, "input");
        try {
            return readFramedImage(ImageIO.createImageInputStream(input), type);
        } catch (final IOException e) {
            return null;
        }
    }

    @Override
    public A3FramedImage readFramedImage(final A3Assets assets, final String input, final int type) {
        checkArgNotNull(assets, "assets");
        return readFramedImage(assets.readAsset(input), type);
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
        for (final String formatName : ImageIO.getReaderFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        for (final String formatName : FramedImageIO.getReaderFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public String[] getImageWriterFormatNames() {
        final Set<String> formatNames = new HashSet<>();
        for (final String formatName : ImageIO.getWriterFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        for (final String formatName : FramedImageIO.getWriterFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public A3Transform createTransform() {
        return new AWTA3Transform(new AffineTransform());
    }

    @Override
    public A3Coordinate createCoordinate() {
        return new AWTA3Coordinate(new Point());
    }

    @Override
    public A3Dimension createDimension() {
        return new AWTA3Dimension(new Dimension());
    }

    @Override
    public A3Area createArea() {
        return new AWTA3Area(new Rectangle());
    }

    @Override
    public A3Path createPath() {
        return new AWTA3Path(new Path2D.Float());
    }

    @Override
    public A3Arc createArc() {
        return new AWTA3Arc(new Arc2D.Float());
    }

    @Override
    public A3Line createLine() {
        return new AWTA3Line(new Line2D.Float());
    }

    @Override
    public A3QuadCurve createQuadCurve() {
        return new AWTA3QuadCurve(new QuadCurve2D.Float());
    }

    @Override
    public A3CubicCurve createCubicCurve() {
        return new AWTA3CubicCurve(new CubicCurve2D.Float());
    }

    @Override
    public A3Point createPoint() {
        return new AWTA3Point(new Point2D.Float());
    }

    @Override
    public A3Oval createOval() {
        return new AWTA3Oval(new Ellipse2D.Float());
    }

    @Override
    public A3Rect createRect() {
        return new AWTA3Rect(new Rectangle2D.Float());
    }

    @Override
    public A3RoundRect createRoundRect() {
        return new AWTA3RoundRect(new RoundRectangle2D.Float());
    }

    @Override
    public A3Size createSize() {
        return new AWTA3Size(new Dimension2D.Float());
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
