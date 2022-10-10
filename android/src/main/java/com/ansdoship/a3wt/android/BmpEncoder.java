/*
 * MIT License
 *
 * Copyright (c) 2021 Tianscar
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.ansdoship.a3wt.android;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * A factory class providing functions to encode Android Bitmap to Windows Bitmap (*.bmp).
 */
final class BmpEncoder {

    private BmpEncoder(){}

    public final static short BF_TYPE = 0x4D42;

    public final static int BITMAP_FILE_HEADER_SIZE = 14;
    public final static int BITMAP_INFO_HEADER_SIZE = 40;

    public final static int BF_OFF_BITS = BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE;

    public final static short BI_BIT_COUNT_24 = 24;
    public final static short BI_BIT_COUNT_32 = 32;

    public static int putBitmapFileHeader(final int bfSize, final byte[] buffer, final int offset) {
        if (offset < 0 || offset + BITMAP_FILE_HEADER_SIZE > buffer.length) throw new ArrayIndexOutOfBoundsException();
        // bfType
        // The file type; must be BM.
        buffer[offset] = (byte) (0xFF & BF_TYPE);
        buffer[offset + 1] = (byte) (0xFF & (BF_TYPE >> 8));
        // bfSize
        // The size, in bytes, of the bitmap file.
        buffer[offset + 2] = (byte) (0xFF & bfSize);
        buffer[offset + 3] = (byte) (0xFF & (bfSize >> 8));
        buffer[offset + 4] = (byte) (0xFF & (bfSize >> 16));
        buffer[offset + 5] = (byte) (0xFF & (bfSize >> 24));
        // bfReserved1
        // Reserved; must be zero.
        buffer[offset + 6] = (byte) 0x00;
        buffer[offset + 7] = (byte) 0x00;
        // bfReserved2
        // Reserved; must be zero.
        buffer[offset + 8] = (byte) 0x00;
        buffer[offset + 9] = (byte) 0x00;
        // bfOffBits
        // The offset, in bytes, from the beginning of the BITMAP_FILE_HEADER structure to the bitmap bits.
        buffer[offset + 10] = (byte) (0xFF & BF_OFF_BITS);
        buffer[offset + 11] = (byte) (0xFF & (BF_OFF_BITS >> 8));
        buffer[offset + 12] = (byte) (0xFF & (BF_OFF_BITS >> 16));
        buffer[offset + 13] = (byte) (0xFF & (BF_OFF_BITS >> 24));
        return BITMAP_FILE_HEADER_SIZE;
    }

    public static int putBitmapFileHeader(final int bfSize, final ByteBuffer buffer, final int offset) {
        if (offset < 0 || offset + BITMAP_FILE_HEADER_SIZE > buffer.limit()) throw new IndexOutOfBoundsException();
        buffer.position(offset);
        // bfType
        // The file type; must be BM.
        buffer.put((byte) (0xFF & BF_TYPE));
        buffer.put((byte) (0xFF & (BF_TYPE >> 8)));
        // bfSize
        // The size, in bytes, of the bitmap file.
        buffer.put((byte) (0xFF & bfSize));
        buffer.put((byte) (0xFF & (bfSize >> 8)));
        buffer.put((byte) (0xFF & (bfSize >> 16)));
        buffer.put((byte) (0xFF & (bfSize >> 24)));
        // bfReserved1
        // Reserved; must be zero.
        buffer.put((byte) 0x00);
        buffer.put((byte) 0x00);
        // bfReserved2
        // Reserved; must be zero.
        buffer.put((byte) 0x00);
        buffer.put((byte) 0x00);
        // bfOffBits
        // The offset, in bytes, from the beginning of the BITMAP_FILE_HEADER structure to the bitmap bits.
        buffer.put((byte) (0xFF & BF_OFF_BITS));
        buffer.put((byte) (0xFF & (BF_OFF_BITS >> 8)));
        buffer.put((byte) (0xFF & (BF_OFF_BITS >> 16)));
        buffer.put((byte) (0xFF & (BF_OFF_BITS >> 24)));
        return BITMAP_FILE_HEADER_SIZE;
    }

    public static int putBitmapFileHeader(final int bfSize, final OutputStream stream) throws IOException {
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
        return BITMAP_FILE_HEADER_SIZE;
    }

    public static int putBitmapInfoHeader(final int biWidth, final int biHeight, final short biBitCount, final int biSizeImage,
                                             final byte[] buffer, final int offset) {
        if (offset < 0 || offset + BITMAP_INFO_HEADER_SIZE > buffer.length) throw new ArrayIndexOutOfBoundsException();
        // biSize
        // The number of bytes required by the structure.
        buffer[offset] = (byte) (0xFF & BITMAP_INFO_HEADER_SIZE);
        buffer[offset + 1] = (byte) (0xFF & (BITMAP_INFO_HEADER_SIZE >> 8));
        buffer[offset + 2] = (byte) (0xFF & (BITMAP_INFO_HEADER_SIZE >> 16));
        buffer[offset + 3] = (byte) (0xFF & (BITMAP_INFO_HEADER_SIZE >> 24));
        // biWidth
        // The width of the bitmap, in pixels.
        buffer[offset + 4] = (byte) (0xFF & biWidth);
        buffer[offset + 5] = (byte) (0xFF & (biWidth >> 8));
        buffer[offset + 6] = (byte) (0xFF & (biWidth >> 16));
        buffer[offset + 7] = (byte) (0xFF & (biWidth >> 24));
        // biHeight
        // The height of the bitmap, in pixels. If biHeight is positive,
        // the bitmap is a bottom-up DIB and its origin is the lower-left corner.
        // If biHeight is negative,
        // the bitmap is a top-down DIB and its origin is the upper-left corner.
        // If biHeight is negative, indicating a top-down DIB,
        // biCompression must be either BI_RGB or BI_BIT_FIELDS.
        // Top-down DIBs cannot be compressed.
        buffer[offset + 8] = (byte) (0xFF & biHeight);
        buffer[offset + 9] = (byte) (0xFF & (biHeight >> 8));
        buffer[offset + 10] = (byte) (0xFF & (biHeight >> 16));
        buffer[offset + 11] = (byte) (0xFF & (biHeight >> 24));
        // biPlanes
        // The number of planes for the target device. This value must be set to 1.
        buffer[offset + 12] = (byte) 0x01;
        buffer[offset + 13] = (byte) 0x00;
        // biBitCount
        // The number of bits-per-pixel.
        // The biBitCount member of the BITMAP_INFO_HEADER structure determines
        // the number of bits that define each pixel and the maximum number of colors in the bitmap.
        buffer[offset + 14] = (byte) (0xFF & biBitCount);
        buffer[offset + 15] = (byte) (0xFF & (biBitCount >> 8));
        // biCompression
        // The type of compression for a compressed bottom-up bitmap
        // (top-down DIBs cannot be compressed).
        // This member can be one of the following values.
        buffer[offset + 16] = (byte) 0x00;
        buffer[offset + 17] = (byte) 0x00;
        buffer[offset + 18] = (byte) 0x00;
        buffer[offset + 19] = (byte) 0x00;
        // biSizeImage
        // The size, in bytes, of the image. This may be set to zero for BI_RGB bitmaps.
        // If biCompression is BI_JPEG or BI_PNG,
        // biSizeImage indicates the size of the JPEG or PNG image buffer, respectively.
        buffer[offset + 20] = (byte) (0xFF & biSizeImage);
        buffer[offset + 21] = (byte) (0xFF & (biSizeImage >> 8));
        buffer[offset + 22] = (byte) (0xFF & (biSizeImage >> 16));
        buffer[offset + 23] = (byte) (0xFF & (biSizeImage >> 24));
        // biXPelsPerMeter
        // The horizontal resolution, in pixels-per-meter, of the target device for the bitmap.
        // An application can use this value to select a bitmap
        // from a resource group that best matches the characteristics of the current device.
        buffer[offset + 24] = (byte) (0xFF & 3780);
        buffer[offset + 25] = (byte) (0xFF & (3780 >> 8));
        buffer[offset + 26] = (byte) (0xFF & (3780 >> 16));
        buffer[offset + 27] = (byte) (0xFF & (3780 >> 24));
        // biYPelsPerMeter
        // The vertical resolution, in pixels-per-meter, of the target device for the bitmap.
        buffer[offset + 28] = (byte) (0xFF & 3780);
        buffer[offset + 29] = (byte) (0xFF & (3780 >> 8));
        buffer[offset + 30] = (byte) (0xFF & (3780 >> 16));
        buffer[offset + 31] = (byte) (0xFF & (3780 >> 24));
        // biClrUsed
        // The number of color indexes in the color table that are actually used by the bitmap.
        // If this value is zero, the bitmap uses the maximum number of colors
        // corresponding to the value of the biBitCount member
        // for the compression mode specified by biCompression.
        // If biClrUsed is nonzero and the biBitCount member is less than 16, the biClrUsed member specifies the actual number of colors the graphics engine or device driver accesses. If biBitCount is 16 or greater, the biClrUsed member specifies the size of the color table used to optimize performance of the system color palettes. If biBitCount equals 16 or 32, the optimal color palette starts immediately following the three DWORD masks.
        // When the bitmap array immediately follows the BITMAP_INFO structure, it is a packed bitmap.
        // Packed bitmaps are referenced by a single pointer.
        // Packed bitmaps require that the biClrUsed member must be either zero or the actual size of the color table.
        buffer[offset + 32] = (byte) 0x00;
        buffer[offset + 33] = (byte) 0x00;
        buffer[offset + 34] = (byte) 0x00;
        buffer[offset + 35] = (byte) 0x00;
        // biClrImportant
        // The number of color indexes that are required for displaying the bitmap.
        // If this value is zero, all colors are required.
        buffer[offset + 36] = (byte) 0x00;
        buffer[offset + 37] = (byte) 0x00;
        buffer[offset + 38] = (byte) 0x00;
        buffer[offset + 39] = (byte) 0x00;
        return BITMAP_INFO_HEADER_SIZE;
    }

    public static int putBitmapInfoHeader(final int biWidth, final int biHeight, final short biBitCount, final int biSizeImage,
                                             final ByteBuffer buffer, final int offset) {
        if (offset < 0 || offset + BITMAP_INFO_HEADER_SIZE > buffer.limit()) throw new IndexOutOfBoundsException();
        buffer.position(offset);
        // biSize
        // The number of bytes required by the structure.
        buffer.put((byte) (0xFF & BITMAP_INFO_HEADER_SIZE));
        buffer.put((byte) (0xFF & (BITMAP_INFO_HEADER_SIZE >> 8)));
        buffer.put((byte) (0xFF & (BITMAP_INFO_HEADER_SIZE >> 16)));
        buffer.put((byte) (0xFF & (BITMAP_INFO_HEADER_SIZE >> 24)));
        // biWidth
        // The width of the bitmap, in pixels.
        buffer.put((byte) (0xFF & biWidth));
        buffer.put((byte) (0xFF & (biWidth >> 8)));
        buffer.put((byte) (0xFF & (biWidth >> 16)));
        buffer.put((byte) (0xFF & (biWidth >> 24)));
        // biHeight
        // The height of the bitmap, in pixels. If biHeight is positive,
        // the bitmap is a bottom-up DIB and its origin is the lower-left corner.
        // If biHeight is negative,
        // the bitmap is a top-down DIB and its origin is the upper-left corner.
        // If biHeight is negative, indicating a top-down DIB,
        // biCompression must be either BI_RGB or BI_BIT_FIELDS.
        // Top-down DIBs cannot be compressed.
        buffer.put((byte) (0xFF & biHeight));
        buffer.put((byte) (0xFF & (biHeight >> 8)));
        buffer.put((byte) (0xFF & (biHeight >> 16)));
        buffer.put((byte) (0xFF & (biHeight >> 24)));
        // biPlanes
        // The number of planes for the target device. This value must be set to 1.
        buffer.put((byte) 0x01);
        buffer.put((byte) 0x00);
        // biBitCount
        // The number of bits-per-pixel.
        // The biBitCount member of the BITMAP_INFO_HEADER structure determines
        // the number of bits that define each pixel and the maximum number of colors in the bitmap.
        buffer.put((byte) (0xFF & biBitCount));
        buffer.put((byte) (0xFF & (biBitCount >> 8)));
        // biCompression
        // The type of compression for a compressed bottom-up bitmap
        // (top-down DIBs cannot be compressed).
        // This member can be one of the following values.
        buffer.put((byte) 0x00);
        buffer.put((byte) 0x00);
        buffer.put((byte) 0x00);
        buffer.put((byte) 0x00);
        // biSizeImage
        // The size, in bytes, of the image. This may be set to zero for BI_RGB bitmaps.
        // If biCompression is BI_JPEG or BI_PNG,
        // biSizeImage indicates the size of the JPEG or PNG image buffer, respectively.
        buffer.put((byte) (0xFF & biSizeImage));
        buffer.put((byte) (0xFF & (biSizeImage >> 8)));
        buffer.put((byte) (0xFF & (biSizeImage >> 16)));
        buffer.put((byte) (0xFF & (biSizeImage >> 24)));
        // biXPelsPerMeter
        // The horizontal resolution, in pixels-per-meter, of the target device for the bitmap.
        // An application can use this value to select a bitmap
        // from a resource group that best matches the characteristics of the current device.
        buffer.put((byte) (0xFF & 3780));
        buffer.put((byte) (0xFF & (3780 >> 8)));
        buffer.put((byte) (0xFF & (3780 >> 16)));
        buffer.put((byte) (0xFF & (3780 >> 24)));
        // biYPelsPerMeter
        // The vertical resolution, in pixels-per-meter, of the target device for the bitmap.
        buffer.put((byte) (0xFF & 3780));
        buffer.put((byte) (0xFF & (3780 >> 8)));
        buffer.put((byte) (0xFF & (3780 >> 16)));
        buffer.put((byte) (0xFF & (3780 >> 24)));
        // biClrUsed
        // The number of color indexes in the color table that are actually used by the bitmap.
        // If this value is zero, the bitmap uses the maximum number of colors
        // corresponding to the value of the biBitCount member
        // for the compression mode specified by biCompression.
        // If biClrUsed is nonzero and the biBitCount member is less than 16, the biClrUsed member specifies the actual number of colors the graphics engine or device driver accesses. If biBitCount is 16 or greater, the biClrUsed member specifies the size of the color table used to optimize performance of the system color palettes. If biBitCount equals 16 or 32, the optimal color palette starts immediately following the three DWORD masks.
        // When the bitmap array immediately follows the BITMAP_INFO structure, it is a packed bitmap.
        // Packed bitmaps are referenced by a single pointer.
        // Packed bitmaps require that the biClrUsed member must be either zero or the actual size of the color table.
        buffer.put((byte) 0x00);
        buffer.put((byte) 0x00);
        buffer.put((byte) 0x00);
        buffer.put((byte) 0x00);
        // biClrImportant
        // The number of color indexes that are required for displaying the bitmap.
        // If this value is zero, all colors are required.
        buffer.put((byte) 0x00);
        buffer.put((byte) 0x00);
        buffer.put((byte) 0x00);
        buffer.put((byte) 0x00);
        return BITMAP_INFO_HEADER_SIZE;
    }

    public static int putBitmapInfoHeader(final int biWidth, final int biHeight, final short biBitCount, final int biSizeImage,
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
        return BITMAP_INFO_HEADER_SIZE;
    }

    public static int putDIBData(final Bitmap bitmap, final byte[] buffer, int offset) {
        final int biWidth = bitmap.getWidth();
        final int biHeight = bitmap.getHeight();
        final int nPixels = biWidth * biHeight;
        if (offset < 0 || offset + nPixels * 4 > buffer.length) throw new ArrayIndexOutOfBoundsException();
        final int[] pixels = new int[nPixels];
        bitmap.getPixels(pixels, 0, biWidth, 0, 0, biWidth, biHeight);
        final Bitmap.Config config = bitmap.getConfig();
        if (config == Bitmap.Config.RGB_565) {
            for (int i = nPixels - 1; i >= 0; i -= biWidth) {
                for (int j = i - biWidth + 1; j <= i; j ++) {
                    buffer[offset] = (byte) (Color.blue(pixels[j]));
                    buffer[offset + 1] = (byte) (Color.green(pixels[j]));
                    buffer[offset + 2] = (byte) (Color.red(pixels[j]));
                    buffer[offset + 3] = (byte) 0x00;
                    offset += 4;
                }
            }
        }
        else if (config == Bitmap.Config.ARGB_8888) {
            for (int i = nPixels - 1; i >= 0; i -= biWidth) {
                for (int j = i - biWidth + 1; j <= i; j ++) {
                    buffer[offset] = (byte) (Color.blue(pixels[j]));
                    buffer[offset + 1] = (byte) (Color.green(pixels[j]));
                    buffer[offset + 2] = (byte) (Color.red(pixels[j]));
                    buffer[offset + 3] = (byte) (Color.alpha(pixels[j]));
                    offset += 4;
                }
            }
        }
        else throw new UnsupportedOperationException("Unsupported Bitmap.Config: " + config.toString());
        return nPixels;
    }

    public static int putDIBData(final Bitmap bitmap, final ByteBuffer buffer, final int offset) {
        final int biWidth = bitmap.getWidth();
        final int biHeight = bitmap.getHeight();
        final int nPixels = biWidth * biHeight;
        if (offset < 0 || offset + nPixels * 4 > buffer.limit()) throw new ArrayIndexOutOfBoundsException();
        final int[] pixels = new int[biWidth * biHeight];
        bitmap.getPixels(pixels, 0, biWidth, 0, 0, biWidth, biHeight);
        final Bitmap.Config config = bitmap.getConfig();
        buffer.position(offset);
        if (config == Bitmap.Config.RGB_565) {
            for (int i = nPixels - 1; i >= 0; i -= biWidth) {
                for (int j = i - biWidth + 1; j <= i; j ++) {
                    buffer.put((byte) (Color.blue(pixels[j])));
                    buffer.put((byte) (Color.green(pixels[j])));
                    buffer.put((byte) (Color.red(pixels[j])));
                    buffer.put((byte) 0x00);
                }
            }
        }
        else if (config == Bitmap.Config.ARGB_8888) {
            for (int i = nPixels - 1; i >= 0; i -= biWidth) {
                for (int j = i - biWidth + 1; j <= i; j ++) {
                    buffer.put((byte) (Color.blue(pixels[j])));
                    buffer.put((byte) (Color.green(pixels[j])));
                    buffer.put((byte) (Color.red(pixels[j])));
                    buffer.put((byte) (Color.alpha(pixels[j])));
                }
            }
        }
        else throw new UnsupportedOperationException("Unsupported Bitmap.Config: " + config.toString());
        return nPixels;
    }

    public static int putDIBData(final Bitmap bitmap, final OutputStream stream) throws IOException {
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
        return nPixels;
    }

    public static boolean compress(final Bitmap bitmap, final OutputStream stream) {
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
        } catch (IOException e) {
            return false;
        }
    }

}
