package io.notcute.g2d.swt;

import io.notcute.g2d.AffineTransform;
import io.notcute.g2d.geom.PathIterator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Transform;

import java.util.Objects;

import static io.notcute.g2d.Font.Style.*;
import static io.notcute.g2d.Font.Style.BOLD_ITALIC;
import static io.notcute.g2d.geom.PathIterator.SegmentType.*;

final class Util {
    
    private Util() {
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

}
