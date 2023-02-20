package io.notcute.internal.awt;

import io.notcute.g2d.AffineTransform;
import io.notcute.g2d.Graphics;
import io.notcute.g2d.geom.Rectangle;
import io.notcute.util.ArrayUtils;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static io.notcute.g2d.Font.Style.*;
import static io.notcute.g2d.Graphics.Cap.BUTT;
import static io.notcute.g2d.Graphics.Cap.SQUARE;
import static io.notcute.g2d.Graphics.Join.BEVEL;
import static io.notcute.g2d.Graphics.Join.MITER;
import static io.notcute.g2d.Image.Type.ARGB_8888;
import static io.notcute.g2d.Image.Type.RGB_565;
import static io.notcute.g2d.geom.PathIterator.SegmentType.*;
import static io.notcute.g2d.geom.PathIterator.WindingRule.EVEN_ODD;
import static io.notcute.g2d.geom.PathIterator.WindingRule.NON_ZERO;

public final class AWTG2DUtils {

    private AWTG2DUtils() {
        throw new UnsupportedOperationException();
    }

    public static int toAWTWindingRule(int rule) {
        switch (rule) {
            case EVEN_ODD:
                return PathIterator.WIND_EVEN_ODD;
            case NON_ZERO:
                return PathIterator.WIND_NON_ZERO;
            default:
                throw new IllegalArgumentException("Invalid winding rule: " + rule);
        }
    }

    public static java.awt.Rectangle toAWTRectangle(Rectangle rectangle) {
        return new java.awt.Rectangle((int) Math.floor(rectangle.getX()), (int) Math.floor(rectangle.getY()),
                (int) Math.ceil(rectangle.getWidth()), (int) Math.ceil(rectangle.getHeight()));
    }

    public static Rectangle toNotcuteRectangle(Rectangle2D.Float rectangle2D) {
        return new Rectangle(rectangle2D.x, rectangle2D.y, rectangle2D.width, rectangle2D.height);
    }

    public static Rectangle toNotcuteRectangle(java.awt.Rectangle rectangle) {
        return new Rectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public static Rectangle toNotcuteRectangle(Rectangle2D rectangle2D) {
        return new Rectangle((float) rectangle2D.getX(), (float) rectangle2D.getY(), (float) rectangle2D.getWidth(), (float) rectangle2D.getHeight());
    }

    public static Rectangle2D toAWTRectangle2D(Rectangle rectangle) {
        return new Rectangle2D.Float(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }

    public static java.awt.geom.AffineTransform toAWTTransform(AffineTransform transform) {
        if (transform == null) return null;
        float[] matrix = new float[6];
        transform.getMatrix(matrix);
        return new java.awt.geom.AffineTransform(matrix);
    }

    public static AffineTransform toNotcuteTransform(java.awt.geom.AffineTransform transform) {
        if (transform == null) return null;
        double[] matrix = new double[6];
        transform.getMatrix(matrix);
        return new AffineTransform(ArrayUtils.copyOf(matrix));
    }

    public static int toAWTStrokeCap(int cap) {
        switch (cap) {
            case BUTT:
                return BasicStroke.CAP_BUTT;
            case Graphics.Cap.ROUND:
                return BasicStroke.CAP_ROUND;
            case SQUARE:
                return BasicStroke.CAP_SQUARE;
            default:
                throw new IllegalArgumentException("Invalid stroke cap");
        }
    }

    public static int toAWTStrokeJoin(int join) {
        switch (join) {
            case MITER:
                return BasicStroke.JOIN_MITER;
            case Graphics.Join.ROUND:
                return BasicStroke.CAP_ROUND;
            case BEVEL:
                return BasicStroke.JOIN_BEVEL;
            default:
                throw new IllegalArgumentException("Invalid stroke join");
        }
    }

    public static int toNotcuteFontStyle(int style) {
        if (style == Font.PLAIN) return NORMAL;
        boolean bold = false;
        boolean italic = false;
        if ((style & Font.BOLD) != 0) bold = true;
        if ((style & Font.ITALIC) != 0) italic = true;
        if (bold && italic) return BOLD_ITALIC;
        else if (bold) return BOLD;
        else if (italic) return ITALIC;
        else throw new IllegalArgumentException("Invalid font style: " + style);
    }

    public static int toAWTFontStyle(int style) {
        switch (style) {
            case NORMAL:
                return Font.PLAIN;
            case BOLD:
                return Font.BOLD;
            case ITALIC:
                return Font.ITALIC;
            case BOLD_ITALIC:
                return Font.BOLD | Font.ITALIC;
            default:
                throw new IllegalArgumentException("Invalid font style: " + style);
        }
    }

    public static BufferedImage copyBufferedImage(BufferedImage source) {
        Objects.requireNonNull(source);
        ColorModel cm = source.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = source.copyData(source.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public static int toNotcuteImageType(final int type) {
        switch (type) {
            case BufferedImage.TYPE_INT_ARGB:
                return ARGB_8888;
            case BufferedImage.TYPE_USHORT_565_RGB:
                return RGB_565;
            default:
                throw new IllegalArgumentException("Invalid type: " + type);
        }
    }

    public static int toAWTBufferedImageType(final int type) {
        switch (type) {
            case ARGB_8888:
                return BufferedImage.TYPE_INT_ARGB;
            case RGB_565:
                return BufferedImage.TYPE_USHORT_565_RGB;
            default:
                throw new IllegalArgumentException("Invalid type: " + type);
        }
    }

    public static BufferedImage getImage(final BufferedImage source, final int type) {
        if (source.getType() == type) return source;
        BufferedImage result = new BufferedImage(source.getWidth(), source.getHeight(), type);
        Graphics2D g2d = result.createGraphics();
        try {
            g2d.drawImage(source, 0, 0, null);
        }
        finally {
            g2d.dispose();
        }
        return result;
    }

    public static Font readFont(File input) throws IOException {
        Objects.requireNonNull(input);
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, input);
        } catch (FontFormatException e) {
            try {
                font = Font.createFont(Font.TYPE1_FONT, input);
            } catch (FontFormatException ignored) {
            }
        }
        return font;
    }

    public static Font readFont(InputStream input) throws IOException {
        Objects.requireNonNull(input);
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, input);
        } catch (FontFormatException e) {
            try {
                font = Font.createFont(Font.TYPE1_FONT, input);
            } catch (FontFormatException ignored) {
            }
        }
        return font;
    }
    
    public static Path2D toAWTPath2D(io.notcute.g2d.geom.PathIterator iterator) {
        Path2D.Float path = new Path2D.Float();
        path.setWindingRule(toAWTWindingRule(iterator.getWindingRule()));
        int type;
        float[] tmp = new float[6];
        while (iterator.hasNext()) {
            type = iterator.currentSegment(tmp);
            switch (type) {
                case MOVE_TO:
                    path.moveTo(tmp[0], tmp[1]);
                    break;
                case LINE_TO:
                    path.lineTo(tmp[0], tmp[1]);
                    break;
                case QUAD_TO:
                    path.quadTo(tmp[0], tmp[1], tmp[2], tmp[3]);
                    break;
                case CUBIC_TO:
                    path.curveTo(tmp[0], tmp[1], tmp[2], tmp[3], tmp[4], tmp[5]);
                    break;
                case CLOSE:
                    path.closePath();
                    break;
            }
            iterator.next();
        }
        return path;
    }

    public static Rectangle2D.Float floatRectangle2D(Rectangle2D rectangle2D) {
        if (rectangle2D == null) return null;
        if (rectangle2D instanceof Rectangle2D.Float) return (Rectangle2D.Float) rectangle2D;
        Rectangle2D.Float result = new Rectangle2D.Float();
        result.setRect(rectangle2D);
        return result;
    }

}
