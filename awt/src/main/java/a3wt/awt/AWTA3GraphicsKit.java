package a3wt.awt;

import a3wt.app.A3Assets;
import a3wt.graphics.A3GraphicsKit;
import a3wt.graphics.A3Image;
import a3wt.graphics.A3FramedImage;
import a3wt.graphics.A3Font;
import a3wt.graphics.A3Cursor;
import a3wt.graphics.A3Arc;
import a3wt.graphics.A3Line;
import a3wt.graphics.A3QuadCurve;
import a3wt.graphics.A3CubicCurve;
import a3wt.graphics.A3Path;
import a3wt.graphics.A3Coordinate;
import a3wt.graphics.A3Point;
import a3wt.graphics.A3Area;
import a3wt.graphics.A3Rect;
import a3wt.graphics.A3Oval;
import a3wt.graphics.A3RoundRect;
import a3wt.graphics.A3Dimension;
import a3wt.graphics.A3Size;
import a3wt.graphics.A3Transform;

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

import static a3wt.util.A3Preconditions.checkArgNotEmpty;
import static a3wt.util.A3Preconditions.checkArgNotNull;
import static a3wt.util.A3Preconditions.checkArgArrayLengthMin;

public class AWTA3GraphicsKit implements A3GraphicsKit {

    protected static BufferedImage getSupportedImage(final BufferedImage source, final String format) {
        checkArgNotNull(source, "source");
        checkArgNotNull(format, "format");
        if (format.equalsIgnoreCase("jpg") || format.equalsIgnoreCase("jpeg")) {
            return A3AWTUtils.getRGB565Image(source);
        }
        else if (format.equalsIgnoreCase("bmp")) {
            return A3AWTUtils.getImage(source, BufferedImage.TYPE_3BYTE_BGR);
        }
        else return A3AWTUtils.getARGB8888Image(source);
    }

    @Override
    public A3Image createImage(final int width, final int height, final int type) {
        return new AWTA3Image(new BufferedImage(width, height, A3AWTUtils.imageType2BufferedImageType(type)));
    }

    public A3Image readImage(final ImageInputStream stream, final int type) {
        try {
            final A3FramedImage framedImage = readFramedImage(stream, type);
            if (framedImage != null) return framedImage;
            final BufferedImage image = ImageIO.read(stream);
            if (image == null) return null;
            else return new AWTA3Image(A3AWTUtils.getImage(image, A3AWTUtils.imageType2BufferedImageType(type)));
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
            return FramedImageIO.read(input, A3AWTUtils.imageType2BufferedImageType(type));
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
    public A3Transform createTransform(final float[] matrixValues) {
        checkArgArrayLengthMin(matrixValues, AWTA3Transform.MATRIX_VALUES_LENGTH, true);
        return new AWTA3Transform(new AffineTransform(matrixValues[0], matrixValues[3], matrixValues[1], matrixValues[4], matrixValues[2], matrixValues[5]));
    }

    @Override
    public A3Transform createTransform(final float sx, final float kx, final float dx, final float ky, final float sy, final float dy) {
        return new AWTA3Transform(new AffineTransform(sx, ky, kx, sy, dx, dy));
    }

    @Override
    public A3Transform createTransform(final A3Point scale, final A3Point skew, final A3Point translate) {
        checkArgNotNull(scale, "scale");
        checkArgNotNull(skew, "skew");
        checkArgNotNull(translate, "translate");
        return new AWTA3Transform(new AffineTransform(scale.getX(), skew.getY(), skew.getX(), scale.getY(), translate.getX(), translate.getY()));
    }

    @Override
    public A3Coordinate createCoordinate() {
        return new AWTA3Coordinate(new Point());
    }

    @Override
    public A3Coordinate createCoordinate(final int x, final int y) {
        return new AWTA3Coordinate(new Point(x, y));
    }

    @Override
    public A3Dimension createDimension() {
        return new AWTA3Dimension(new Dimension());
    }

    @Override
    public A3Dimension createDimension(final int width, final int height) {
        return new AWTA3Dimension(new Dimension(width, height));
    }

    @Override
    public A3Area createArea() {
        return new AWTA3Area(new Rectangle());
    }

    @Override
    public A3Area createArea(final int x, final int y, final int width, final int height) {
        return new AWTA3Area(new Rectangle(x, y, width, height));
    }

    @Override
    public A3Area createArea(final A3Coordinate pos, final A3Dimension size) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        return new AWTA3Area(new Rectangle(((AWTA3Coordinate)pos).point, ((AWTA3Dimension)size).dimension));
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
    public A3Arc createArc(final boolean useCenter) {
        return new AWTA3Arc(new Arc2D.Float(useCenter ? Arc2D.PIE : Arc2D.OPEN));
    }

    @Override
    public A3Arc createArc(final float x, final float y, final float width, final float height,
                           final float startAngle, final float sweepAngle, final boolean useCenter) {
        return new AWTA3Arc(new Arc2D.Float(x, y, width, height, startAngle, sweepAngle, useCenter ? Arc2D.PIE : Arc2D.OPEN));
    }

    @Override
    public A3Arc createArc(final A3Point pos, final A3Size size, final float startAngle, final float sweepAngle, final boolean useCenter) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        return new AWTA3Arc(new Arc2D.Float(pos.getX(), pos.getY(), size.getWidth(), size.getHeight(),
                startAngle, sweepAngle, useCenter ? Arc2D.PIE : Arc2D.OPEN));
    }

    @Override
    public A3Arc createArc(final A3Rect rect, final float startAngle, final float sweepAngle, final boolean useCenter) {
        checkArgNotNull(rect, "rect");
        return new AWTA3Arc(new Arc2D.Float(((AWTA3Rect)rect).rectangle2D, startAngle, sweepAngle, useCenter ? Arc2D.PIE : Arc2D.OPEN));
    }

    @Override
    public A3Line createLine() {
        return new AWTA3Line(new Line2D.Float());
    }

    @Override
    public A3Line createLine(final float startX, final float startY, final float endX, final float endY) {
        return new AWTA3Line(new Line2D.Float(startX, startY, endX, endY));
    }

    @Override
    public A3Line createLine(final A3Point startPos, final A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(endPos, "endPos");
        return new AWTA3Line(new Line2D.Float(((AWTA3Point)startPos).point2D, ((AWTA3Point)endPos).point2D));
    }

    @Override
    public A3QuadCurve createQuadCurve() {
        return new AWTA3QuadCurve(new QuadCurve2D.Float());
    }

    @Override
    public A3QuadCurve createQuadCurve(final float startX, final float startY, final float ctrlX, final float ctrlY, final float endX, final float endY) {
        return new AWTA3QuadCurve(new QuadCurve2D.Float(startX, startY, ctrlX, ctrlY, endX, endY));
    }

    @Override
    public A3QuadCurve createQuadCurve(final A3Point startPos, final A3Point ctrlPos, final A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(ctrlPos, "ctrlPos");
        checkArgNotNull(endPos, "endPos");
        return new AWTA3QuadCurve(new QuadCurve2D.Float(startPos.getX(), startPos.getY(), ctrlPos.getX(), ctrlPos.getY(), endPos.getX(), endPos.getY()));
    }

    @Override
    public A3CubicCurve createCubicCurve() {
        return new AWTA3CubicCurve(new CubicCurve2D.Float());
    }

    @Override
    public A3CubicCurve createCubicCurve(final float startX, final float startY, final float ctrlX1, final float ctrlY1,
                                         final float ctrlX2, final float ctrlY2, final float endX, final float endY) {
        return new AWTA3CubicCurve(new CubicCurve2D.Float(startX, startY, ctrlX1, ctrlY1, ctrlX2, ctrlY2, endX, endY));
    }

    @Override
    public A3CubicCurve createCubicCurve(final A3Point startPos, final A3Point ctrlPos1, final A3Point ctrlPos2, final A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(ctrlPos1, "ctrlPos1");
        checkArgNotNull(ctrlPos2, "ctrlPos2");
        checkArgNotNull(endPos, "endPos");
        return new AWTA3CubicCurve(new CubicCurve2D.Float(startPos.getX(), startPos.getY(), ctrlPos1.getX(), ctrlPos1.getY(),
                ctrlPos2.getX(), ctrlPos2.getY(), endPos.getX(), endPos.getY()));
    }

    @Override
    public A3Point createPoint() {
        return new AWTA3Point(new Point2D.Float());
    }

    @Override
    public A3Point createPoint(final float x, final float y) {
        return new AWTA3Point(new Point2D.Float(x, y));
    }

    @Override
    public A3Oval createOval() {
        return new AWTA3Oval(new Ellipse2D.Float());
    }

    @Override
    public A3Oval createOval(final float x, final float y, final float width, final float height) {
        return new AWTA3Oval(new Ellipse2D.Float(x, y, width, height));
    }

    @Override
    public A3Oval createOval(final A3Point pos, final A3Size size) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        return new AWTA3Oval(new Ellipse2D.Float(pos.getX(), pos.getY(), size.getWidth(), size.getHeight()));
    }

    @Override
    public A3Oval createOval(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        return new AWTA3Oval(new Ellipse2D.Float(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight()));
    }

    @Override
    public A3Rect createRect() {
        return new AWTA3Rect(new Rectangle2D.Float());
    }

    @Override
    public A3Rect createRect(final float x, final float y, final float width, final float height) {
        return new AWTA3Rect(new Rectangle2D.Float(x, y, width, height));
    }

    @Override
    public A3Rect createRect(final A3Point pos, final A3Size size) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        return new AWTA3Rect(new Rectangle2D.Float(pos.getX(), pos.getY(), size.getWidth(), size.getHeight()));
    }

    @Override
    public A3RoundRect createRoundRect() {
        return new AWTA3RoundRect(new RoundRectangle2D.Float());
    }

    @Override
    public A3RoundRect createRoundRect(final float x, final float y, final float width, final float height, final float rx, final float ry) {
        return new AWTA3RoundRect(new RoundRectangle2D.Float(x, y, width, height, rx, ry));
    }

    @Override
    public A3RoundRect createRoundRect(final A3Point pos, final A3Size size, final A3Size corner) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        checkArgNotNull(corner, "corner");
        return new AWTA3RoundRect(new RoundRectangle2D.Float(pos.getX(), pos.getY(), size.getWidth(), size.getHeight(), corner.getWidth(), corner.getHeight()));
    }

    @Override
    public A3RoundRect createRoundRect(final A3Rect rect, final A3Size corner) {
        checkArgNotNull(rect, "rect");
        checkArgNotNull(corner, "corner");
        return new AWTA3RoundRect(new RoundRectangle2D.Float(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), corner.getWidth(), corner.getHeight()));
    }

    @Override
    public A3Size createSize() {
        return new AWTA3Size(new Dimension2D.Float());
    }

    @Override
    public A3Size createSize(final float width, final float height) {
        return new AWTA3Size(new Dimension2D.Float(width, height));
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
        return new AWTA3Font(font.deriveFont(A3AWTUtils.fontStyle2awtFontStyle(style)));
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
    public A3Cursor createCursor(final A3Image image, final int hotSpotX, final int hotSpotY) {
        if (image instanceof A3FramedImage) return createFramedCursor((A3FramedImage) image, hotSpotX, hotSpotY);
        else return new AWTA3Cursor((AWTA3Image) image, new Point(hotSpotX, hotSpotY));
    }

    @Override
    public A3Cursor createCursor(final A3Image image, final A3Coordinate hotSpot) {
        if (image instanceof A3FramedImage) return createFramedCursor((A3FramedImage) image, hotSpot);
        else return new AWTA3Cursor((AWTA3Image) image, ((AWTA3Coordinate)hotSpot).point);
    }

    @Override
    public A3Cursor createFramedCursor(final A3FramedImage image, final int hotSpotX, final int hotSpotY) {
        final A3Cursor.Frame[] frames = new A3Cursor.Frame[image.size()];
        for (int i = 0; i < frames.length; i ++) {
            frames[i] = new A3Cursor.DefaultFrame(new AWTA3Cursor((AWTA3Image) image.get(i).getImage(), new Point(hotSpotX, hotSpotY)),
                    image.get(i).getDuration());
        }
        return createFramedCursor(frames);
    }

    @Override
    public A3Cursor createFramedCursor(final A3FramedImage image, final A3Coordinate hotSpot) {
        final A3Cursor.Frame[] frames = new A3Cursor.Frame[image.size()];
        for (int i = 0; i < frames.length; i ++) {
            frames[i] = new A3Cursor.DefaultFrame(new AWTA3Cursor((AWTA3Image) image.get(i).getImage(), ((AWTA3Coordinate)hotSpot).point),
                    image.get(i).getDuration());
        }
        return createFramedCursor(frames);
    }

    @Override
    public A3Cursor getDefaultCursor() {
        return AWTA3Cursor.getDefaultCursor();
    }

}
