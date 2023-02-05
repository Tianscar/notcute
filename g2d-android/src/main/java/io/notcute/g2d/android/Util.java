package io.notcute.g2d.android;

import android.content.res.AssetManager;
import android.graphics.*;
import io.notcute.g2d.AffineTransform;
import io.notcute.g2d.Font;
import io.notcute.g2d.Graphics;
import io.notcute.g2d.Image;
import io.notcute.g2d.geom.PathIterator;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static io.notcute.g2d.geom.PathIterator.SegmentType.*;

final class Util {

    private Util() {
        throw new UnsupportedOperationException();
    }

    public static Matrix toAndroidMatrix(AffineTransform transform) {
        if (transform == null) return null;
        float[] values = new float[6];
        transform.getMatrix(values);
        Matrix matrix = new Matrix();
        matrix.setValues(values);
        return matrix;
    }

    public static Path toAndroidPath(PathIterator iterator) {
        Path path = new Path();
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
                    path.cubicTo(tmp[0], tmp[1], tmp[2], tmp[3], tmp[4], tmp[5]);
                    break;
                case CLOSE:
                    path.close();
                    break;
            }
            iterator.next();
        }
        return path;
    }

    public static char[] getChars(CharSequence text, int offset, int length) {
        if (text == null) return null;
        char[] chars = new char[length];
        for (int i = 0; i < length; i ++) {
            chars[i] = text.charAt(offset + i);
        }
        return chars;
    }

    public static int toNotcuteFontStyle(int style) {
        switch (style) {
            case Typeface.NORMAL:
                return Font.Style.NORMAL;
            case Typeface.BOLD:
                return Font.Style.BOLD;
            case Typeface.ITALIC:
                return Font.Style.ITALIC;
            case Typeface.BOLD_ITALIC:
                return Font.Style.BOLD_ITALIC;
            default:
                throw new IllegalArgumentException("Invalid font style: " + style);
        }
    }

    public static int toNotcuteImageType(Bitmap.Config config) {
        switch (config) {
            case ARGB_8888:
                return Image.Type.ARGB_8888;
            case RGB_565:
                return Image.Type.RGB_565;
            default:
                throw new IllegalArgumentException("Invalid Bitmap.Config: " + config);
        }
    }

    public static Bitmap.Config toAndroidBitmapConfig(int type) {
        switch (type) {
            case Image.Type.ARGB_8888:
                return Bitmap.Config.ARGB_8888;
            case Image.Type.RGB_565:
                return Bitmap.Config.RGB_565;
            default:
                throw new IllegalArgumentException("Invalid image type: " + type);
        }
    }

    public static Paint.Style toAndroidPaintStyle(int style) {
        switch (style) {
            case Graphics.Style.STROKE:
                return Paint.Style.STROKE;
            case Graphics.Style.FILL:
                return Paint.Style.FILL;
            default:
                throw new IllegalArgumentException("Invalid graphics style: " + style);
        }
    }

    public static Paint.Join toAndroidPaintJoin(int join) {
        switch (join) {
            case Graphics.Join.MITER:
                return Paint.Join.MITER;
            case Graphics.Join.ROUND:
                return Paint.Join.ROUND;
            case Graphics.Join.BEVEL:
                return Paint.Join.BEVEL;
            default:
                throw new IllegalArgumentException("Invalid graphics join: " + join);
        }
    }

    public static Paint.Cap toAndroidPaintCap(int cap) {
        switch (cap) {
            case Graphics.Cap.BUTT:
                return Paint.Cap.BUTT;
            case Graphics.Cap.ROUND:
                return Paint.Cap.ROUND;
            case Graphics.Cap.SQUARE:
                return Paint.Cap.SQUARE;
            default:
                throw new IllegalArgumentException("Invalid graphics cap: " + cap);
        }
    }

    public static Bitmap copyBitmap(Bitmap source) {
        return source.copy(source.getConfig(), source.isMutable());
    }

    public static Bitmap copyBitmap(Bitmap source, Bitmap.Config config) {
        return source.copy(config, source.isMutable());
    }

    public static boolean containsIgnoreCase(String[] array, String string) {
        for (String str : Objects.requireNonNull(array)) {
            if (str.equalsIgnoreCase(string)) return true;
        }
        return false;
    }

    public static void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception ignored) {
        }
    }

    public static Typeface readTypeface(AssetManager assets, String asset) throws IOException {
        Objects.requireNonNull(assets);
        Objects.requireNonNull(asset);
        Typeface typeface;
        try {
            typeface = Typeface.createFromAsset(assets, asset);
        }
        catch (RuntimeException e) {
            throw new IOException(e);
        }
        return typeface;
    }

    public static Typeface readTypeface(File input) throws IOException {
        Objects.requireNonNull(input);
        Typeface typeface;
        try {
            typeface = Typeface.createFromFile(input);
        }
        catch (RuntimeException e) {
            throw new IOException(e);
        }
        return typeface;
    }

    public static int toAndroidTypefaceStyle(int style) {
        switch (style) {
            case Font.Style.NORMAL:
                return Typeface.NORMAL;
            case Font.Style.BOLD:
                return Typeface.BOLD;
            case Font.Style.ITALIC:
                return Typeface.ITALIC;
            case Font.Style.BOLD_ITALIC:
                return Typeface.BOLD_ITALIC;
            default:
                throw new IllegalArgumentException("Invalid font style: " + style);
        }
    }

    public static String removeStartSeparator(String text) {
        return text.charAt(0) == '/' ? text.substring(1) : text;
    }

}
