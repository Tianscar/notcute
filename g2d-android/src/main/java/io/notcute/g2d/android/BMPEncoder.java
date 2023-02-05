package io.notcute.g2d.android;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.io.IOException;
import java.io.OutputStream;

/**
 * The utility class which provides function to encode {@link Bitmap} to Windows Bitmap (*.bmp).
 */
public final class BMPEncoder {

    private BMPEncoder() {
        throw new UnsupportedOperationException();
    }

    private final static short BF_TYPE = 0x4D42;

    private final static int BITMAP_FILE_HEADER_SIZE = 14;
    private final static int BITMAP_INFO_HEADER_SIZE = 40;

    private final static int BF_OFF_BITS = BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE;

    private final static short BI_BIT_COUNT_24 = 24;
    private final static short BI_BIT_COUNT_32 = 32;

    private static void putBitmapFileHeader(final int bfSize, final OutputStream stream) throws IOException {
        // bfType
        // The file type; must be BM.
        stream.write((byte) (0xFF & BF_TYPE));
        stream.write((byte) (0xFF & (BF_TYPE >> 8)));
        // bfSize
        // The size, in bytes, of the bitmap file.
        stream.write((byte) (0xFF & bfSize));
        stream.write((byte) (0xFF & (bfSize >> 8)));
        stream.write((byte) (0xFF & (bfSize >> 16)));
        stream.write((byte) (0xFF & (bfSize >> 24)));
        // bfReserved1
        // Reserved; must be zero.
        stream.write((byte) 0x00);
        stream.write((byte) 0x00);
        // bfReserved2
        // Reserved; must be zero.
        stream.write((byte) 0x00);
        stream.write((byte) 0x00);
        // bfOffBits
        // The offset, in bytes, from the beginning of the BITMAP_FILE_HEADER structure to the bitmap bits.
        stream.write((byte) (0xFF & BF_OFF_BITS));
        stream.write((byte) (0xFF & (BF_OFF_BITS >> 8)));
        stream.write((byte) (0xFF & (BF_OFF_BITS >> 16)));
        stream.write((byte) (0xFF & (BF_OFF_BITS >> 24)));
    }

    private static void putBitmapInfoHeader(final int biWidth, final int biHeight, final short biBitCount, final int biSizeImage,
                                            final OutputStream stream) throws IOException {
        // biSize
        // The number of bytes required by the structure.
        stream.write((byte) (0xFF & BITMAP_INFO_HEADER_SIZE));
        stream.write((byte) (0xFF & (BITMAP_INFO_HEADER_SIZE >> 8)));
        stream.write((byte) (0xFF & (BITMAP_INFO_HEADER_SIZE >> 16)));
        stream.write((byte) (0xFF & (BITMAP_INFO_HEADER_SIZE >> 24)));
        // biWidth
        // The width of the bitmap, in pixels.
        stream.write((byte) (0xFF & biWidth));
        stream.write((byte) (0xFF & (biWidth >> 8)));
        stream.write((byte) (0xFF & (biWidth >> 16)));
        stream.write((byte) (0xFF & (biWidth >> 24)));
        // biHeight
        // The height of the bitmap, in pixels. If biHeight is positive,
        // the bitmap is a bottom-up DIB and its origin is the lower-left corner.
        // If biHeight is negative,
        // the bitmap is a top-down DIB and its origin is the upper-left corner.
        // If biHeight is negative, indicating a top-down DIB,
        // biCompression must be either BI_RGB or BI_BIT_FIELDS.
        // Top-down DIBs cannot be compressed.
        stream.write((byte) (0xFF & biHeight));
        stream.write((byte) (0xFF & (biHeight >> 8)));
        stream.write((byte) (0xFF & (biHeight >> 16)));
        stream.write((byte) (0xFF & (biHeight >> 24)));
        // biPlanes
        // The number of planes for the target device. This value must be set to 1.
        stream.write((byte) 0x01);
        stream.write((byte) 0x00);
        // biBitCount
        // The number of bits-per-pixel.
        // The biBitCount member of the BITMAP_INFO_HEADER structure determines
        // the number of bits that define each pixel and the maximum number of colors in the bitmap.
        stream.write((byte) (0xFF & biBitCount));
        stream.write((byte) (0xFF & (biBitCount >> 8)));
        // biCompression
        // The type of compression for a compressed bottom-up bitmap
        // (top-down DIBs cannot be compressed).
        // This member can be one of the following values.
        stream.write((byte) 0x00);
        stream.write((byte) 0x00);
        stream.write((byte) 0x00);
        stream.write((byte) 0x00);
        // biSizeImage
        // The size, in bytes, of the image. This may be set to zero for BI_RGB bitmaps.
        // If biCompression is BI_JPEG or BI_PNG,
        // biSizeImage indicates the size of the JPEG or PNG image buffer, respectively.
        stream.write((byte) (0xFF & biSizeImage));
        stream.write((byte) (0xFF & (biSizeImage >> 8)));
        stream.write((byte) (0xFF & (biSizeImage >> 16)));
        stream.write((byte) (0xFF & (biSizeImage >> 24)));
        // biXPelsPerMeter
        // The horizontal resolution, in pixels-per-meter, of the target device for the bitmap.
        // An application can use this value to select a bitmap
        // from a resource group that best matches the characteristics of the current device.
        stream.write((byte) (0xFF & 3780));
        stream.write((byte) (0xFF & (3780 >> 8)));
        stream.write((byte) (0xFF & (3780 >> 16)));
        stream.write((byte) (0xFF & (3780 >> 24)));
        // biYPelsPerMeter
        // The vertical resolution, in pixels-per-meter, of the target device for the bitmap.
        stream.write((byte) (0xFF & 3780));
        stream.write((byte) (0xFF & (3780 >> 8)));
        stream.write((byte) (0xFF & (3780 >> 16)));
        stream.write((byte) (0xFF & (3780 >> 24)));
        // biClrUsed
        // The number of color indexes in the color table that are actually used by the bitmap.
        // If this value is zero, the bitmap uses the maximum number of colors
        // corresponding to the value of the biBitCount member
        // for the compression mode specified by biCompression.
        // If biClrUsed is nonzero and the biBitCount member is less than 16, the biClrUsed member specifies the actual number of colors the graphics engine or device driver accesses. If biBitCount is 16 or greater, the biClrUsed member specifies the size of the color table used to optimize performance of the system color palettes. If biBitCount equals 16 or 32, the optimal color palette starts immediately following the three DWORD masks.
        // When the bitmap array immediately follows the BITMAP_INFO structure, it is a packed bitmap.
        // Packed bitmaps are referenced by a single pointer.
        // Packed bitmaps require that the biClrUsed member must be either zero or the actual size of the color table.
        stream.write((byte) 0x00);
        stream.write((byte) 0x00);
        stream.write((byte) 0x00);
        stream.write((byte) 0x00);
        // biClrImportant
        // The number of color indexes that are required for displaying the bitmap.
        // If this value is zero, all colors are required.
        stream.write((byte) 0x00);
        stream.write((byte) 0x00);
        stream.write((byte) 0x00);
        stream.write((byte) 0x00);
    }

    private static void putDIBData(final Bitmap bitmap, final OutputStream stream) throws IOException {
        final int biWidth = bitmap.getWidth();
        final int biHeight = bitmap.getHeight();
        final int nPixels = biWidth * biHeight;
        final int[] pixels = new int[biWidth * biHeight];
        bitmap.getPixels(pixels, 0, biWidth, 0, 0, biWidth, biHeight);
        final Bitmap.Config config = bitmap.getConfig();
        if (config == Bitmap.Config.RGB_565) {
            for (int i = nPixels - 1; i >= 0; i -= biWidth) {
                for (int j = i - biWidth + 1; j <= i; j ++) {
                    stream.write((byte) (Color.blue(pixels[j])));
                    stream.write((byte) (Color.green(pixels[j])));
                    stream.write((byte) (Color.red(pixels[j])));
                    stream.write((byte) 0x00);
                }
            }
        }
        else if (config == Bitmap.Config.ARGB_8888) {
            for (int i = nPixels - 1; i >= 0; i -= biWidth) {
                for (int j = i - biWidth + 1; j <= i; j ++) {
                    stream.write((byte) (Color.blue(pixels[j])));
                    stream.write((byte) (Color.green(pixels[j])));
                    stream.write((byte) (Color.red(pixels[j])));
                    stream.write((byte) (Color.alpha(pixels[j])));
                }
            }
        }
        else throw new UnsupportedOperationException("Unsupported Bitmap.Config: " + config.toString());
    }

    /**
     * <p>Encode {@link Bitmap} to Windows Bitmap (*.bmp).
     * Returns true if success, false if failed.</p>
     * <p>This method supports the following {@link Bitmap.Config}:</p>
     * {@link Bitmap.Config#RGB_565}<br>
     * {@link Bitmap.Config#ARGB_8888}
     * @throws IllegalArgumentException if bitmap or stream is null
     * @throws UnsupportedOperationException if the bitmap's {@link Bitmap.Config} not supported by this method
     * @param bitmap the bitmap to encode
     * @param stream the target output stream
     * @return true if success, false if failed
     */
    public static boolean compress(final Bitmap bitmap, final OutputStream stream) throws IllegalArgumentException, UnsupportedOperationException {
        if (bitmap == null) throw new IllegalArgumentException("bitmap cannot be null.");
        if (stream == null) throw new IllegalArgumentException("stream cannot be null.");
        final int biWidth = bitmap.getWidth();
        final int biHeight = bitmap.getHeight();
        final byte biBitCount;
        final int biSizeImage = biWidth * biHeight * 4;
        final Bitmap.Config config = bitmap.getConfig();
        if (config == Bitmap.Config.ARGB_8888) {
            biBitCount = BI_BIT_COUNT_32;
        }
        else if (config == Bitmap.Config.RGB_565) {
            biBitCount = BI_BIT_COUNT_24;
        }
        else return false;
        final int bfSize = BF_OFF_BITS + biSizeImage;
        try {
            putBitmapFileHeader(bfSize, stream);
            putBitmapInfoHeader(biWidth, biHeight, biBitCount, biSizeImage, stream);
            putDIBData(bitmap, stream);
            return true;
        }
        catch (final IOException e) {
            return false;
        }
    }

}
