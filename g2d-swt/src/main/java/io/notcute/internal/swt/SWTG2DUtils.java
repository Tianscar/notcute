package io.notcute.internal.swt;

import io.notcute.g2d.AffineTransform;
import io.notcute.g2d.Graphics;
import io.notcute.g2d.Image;
import io.notcute.g2d.geom.PathIterator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;

import java.awt.Font;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static io.notcute.g2d.Font.Style.*;
import static io.notcute.g2d.geom.PathIterator.SegmentType.*;

public final class SWTG2DUtils {
    
    private SWTG2DUtils() {
        throw new UnsupportedOperationException();
    }

    public static Transform toSWTTransform(Device device, AffineTransform transform) {
        if (device == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
        if (transform == null) return null;
        float[] matrix = new float[6];
        transform.getMatrix(matrix);
        return new Transform(device, matrix);
    }

    public static AffineTransform toNotcuteAffineTransform(Transform transform) {
        if (transform == null) return null;
        float[] matrix = new float[6];
        transform.getElements(matrix);
        return new AffineTransform(matrix);
    }

    public static Path toSWTPath(Device device, PathIterator iterator) {
        Path path = new Path(device);
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
    
    public static int toNotcuteFontStyle(int style) {
        if (style == 0) return NORMAL;
        boolean bold = false;
        boolean italic = false;
        if ((style & SWT.BOLD) != 0) bold = true;
        if ((style & SWT.ITALIC) != 0) italic = true;
        if (bold && italic) return BOLD_ITALIC;
        else if (bold) return BOLD;
        else if (italic) return ITALIC;
        else throw new IllegalArgumentException("Invalid font style: " + style);
    }

    public static void getPixels(ImageData imageData, int x, int y, int getWidth, int[] pixels, int startIndex, int bytesPerLine) {
        if (imageData == null || pixels == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
        int width = imageData.width;
        int height = imageData.height;
        if (getWidth < 0 || x >= width || y >= height || x < 0 || y < 0) SWT.error(SWT.ERROR_INVALID_ARGUMENT);
        if (getWidth == 0) return;
        int depth = imageData.depth;
        byte[] data = imageData.data;
        int index;
        int i = startIndex;
        int srcX = x, srcY = y;
        switch (depth) {
            case 32:
                index = (y * bytesPerLine) + (x * 4);
                for (int j = 0; j < getWidth; j++) {
                    pixels[i] = ((data[index] & 0xFF) << 24) | ((data[index+1] & 0xFF) << 16)
                            | ((data[index+2] & 0xFF) << 8) | (data[index+3] & 0xFF);
                    i++;
                    srcX++;
                    if (srcX >= width) {
                        srcY++;
                        index = srcY * bytesPerLine;
                        srcX = 0;
                    } else {
                        index += 4;
                    }
                }
                break;
            case 24:
                index = (y * bytesPerLine) + (x * 3);
                for (int j = 0; j < getWidth; j++) {
                    pixels[i] = ((data[index] & 0xFF) << 16) | ((data[index+1] & 0xFF) << 8)
                            | (data[index+2] & 0xFF);
                    i++;
                    srcX++;
                    if (srcX >= width) {
                        srcY++;
                        index = srcY * bytesPerLine;
                        srcX = 0;
                    } else {
                        index += 3;
                    }
                }
                break;
            default:
                SWT.error(SWT.ERROR_UNSUPPORTED_DEPTH);
                break;
        }
    }

    public static void setPixels(ImageData imageData, int x, int y, int putWidth, int[] pixels, int startIndex, int bytesPerLine) {
        if (imageData == null || pixels == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
        int width = imageData.width;
        int height = imageData.height;
        if (putWidth < 0 || x >= width || y >= height || x < 0 || y < 0) SWT.error(SWT.ERROR_INVALID_ARGUMENT);
        if (putWidth == 0) return;
        int depth = imageData.depth;
        byte[] data = imageData.data;
        int index;
        int i = startIndex;
        int pixel;
        int srcX = x, srcY = y;
        switch (depth) {
            case 32:
                index = (y * bytesPerLine) + (x * 4);
                for (int j = 0; j < putWidth; j++) {
                    pixel = pixels[i];
                    data[index] = (byte)((pixel >> 24) & 0xFF);
                    data[index + 1] = (byte)((pixel >> 16) & 0xFF);
                    data[index + 2] = (byte)((pixel >> 8) & 0xFF);
                    data[index + 3] = (byte)(pixel & 0xFF);
                    i++;
                    srcX++;
                    if (srcX >= width) {
                        srcY++;
                        index = srcY * bytesPerLine;
                        srcX = 0;
                    } else {
                        index += 4;
                    }
                }
                break;
            case 24:
                index = (y * bytesPerLine) + (x * 3);
                for (int j = 0; j < putWidth; j++) {
                    pixel = pixels[i];
                    data[index] = (byte)((pixel >> 16) & 0xFF);
                    data[index + 1] = (byte)((pixel >> 8) & 0xFF);
                    data[index + 2] = (byte)(pixel & 0xFF);
                    i++;
                    srcX++;
                    if (srcX >= width) {
                        srcY++;
                        index = srcY * bytesPerLine;
                        srcX = 0;
                    } else {
                        index += 3;
                    }
                }
                break;
            default:
                SWT.error(SWT.ERROR_UNSUPPORTED_DEPTH);
                break;
        }
    }

    public static int toSWTGCFillRule(int windingRule) {
        switch (windingRule) {
            case PathIterator.WindingRule.EVEN_ODD:
                return SWT.FILL_EVEN_ODD;
            case PathIterator.WindingRule.NON_ZERO:
                return SWT.FILL_WINDING;
            default:
                throw new IllegalArgumentException("Invalid winding rule: " + windingRule);
        }
    }

    public static int toSWTLineCap(int strokeCap) {
        switch (strokeCap) {
            case Graphics.Cap.BUTT:
                return SWT.CAP_FLAT;
            case Graphics.Cap.ROUND:
                return SWT.CAP_ROUND;
            case Graphics.Cap.SQUARE:
                return SWT.CAP_SQUARE;
            default:
                throw new IllegalArgumentException("Invalid stroke cap: " + strokeCap);
        }
    }

    public static int toSWTLineJoin(int strokeJoin) {
        switch (strokeJoin) {
            case Graphics.Join.MITER:
                return SWT.JOIN_MITER;
            case Graphics.Join.ROUND:
                return SWT.JOIN_ROUND;
            case Graphics.Join.BEVEL:
                return SWT.JOIN_BEVEL;
            default:
                throw new IllegalArgumentException("Invalid stroke join: " + strokeJoin);
        }
    }

    public static int toSWTImageDepth(int type) {
        switch (type) {
            case Image.Type.ARGB_8888:
                return 32;
            case Image.Type.RGB_565:
                return 24;
            default:
                throw new IllegalArgumentException("Invalid type: " + type);
        }
    }

    public static int toNotcuteImageType(int depth) {
        switch (depth) {
            case 32:
                return Image.Type.ARGB_8888;
            case 24:
                return Image.Type.RGB_565;
            default:
                SWT.error(SWT.ERROR_UNSUPPORTED_DEPTH);
                throw new RuntimeException(); /* Can't reach */
        }
    }

    public static ImageData getImageData(Device device, ImageData imageData, int depth) {
        if (device == null || imageData == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
        switch (depth) {
            case 32:
            case 24:
            case 16:
            case 8:
            case 4:
            case 2:
            case 1:
                break;
            default:
                SWT.error(SWT.ERROR_UNSUPPORTED_DEPTH);
                break;
        }
        if (imageData.depth == depth) return imageData;
        ImageData resultData = new ImageData(imageData.width, imageData.height, depth, imageData.palette);
        org.eclipse.swt.graphics.Image resultImage = new org.eclipse.swt.graphics.Image(device, resultData); // resultImage ALLOC
        GC resultGC = new GC(resultImage); // resultGC ALLOC
        org.eclipse.swt.graphics.Image tmpImage = new org.eclipse.swt.graphics.Image(device, imageData); // tmpImage ALLOC
        resultGC.drawImage(tmpImage, 0, 0);
        resultData = resultImage.getImageData();
        resultImage.dispose(); // resultImage FREE
        resultGC.dispose(); // resultGC FREE
        tmpImage.dispose(); // tmpImage FREE
        resultData.x = imageData.x;
        resultData.y = imageData.y;
        resultData.disposalMethod = imageData.disposalMethod;
        resultData.delayTime = imageData.delayTime;
        resultData.transparentPixel = imageData.transparentPixel;
        return resultData;
    }

    public static java.awt.Font readAWTFont(File input) throws IOException {
        Objects.requireNonNull(input);
        java.awt.Font font = null;
        try {
            font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, input);
        } catch (FontFormatException e) {
            try {
                font = java.awt.Font.createFont(Font.TYPE1_FONT, input);
            } catch (FontFormatException ignored) {
            }
        }
        return font;
    }

    public static int toSWTFontStyle(int style) {
        switch (style) {
            case NORMAL:
                return SWT.NORMAL;
            case BOLD:
                return SWT.BOLD;
            case ITALIC:
                return SWT.ITALIC;
            case BOLD_ITALIC:
                return SWT.BOLD | SWT.ITALIC;
            default:
                throw new IllegalArgumentException("Invalid font style: " + style);
        }
    }

    public static int fromAWTtoSWTFontStyle(int style) {
        int result = SWT.NORMAL;
        if (style == Font.PLAIN) return result;
        if ((style & Font.BOLD) != 0) result |= SWT.BOLD;
        if ((style & Font.ITALIC) != 0) result |= SWT.ITALIC;
        return result;
    }

}
