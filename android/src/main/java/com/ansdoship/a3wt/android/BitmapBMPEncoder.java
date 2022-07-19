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
import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A factory class providing functions to encode Windows Bitmap (*.bmp).
 */
final class BitmapBMPEncoder {

    private BitmapBMPEncoder(){}

    private final static short BF_TYPE = 0x4D42;

    private final static int BITMAP_FILE_HEADER_SIZE = 14;
    private final static int BITMAP_INFO_HEADER_SIZE = 40;

    private final static int BF_OFF_BITS = BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE;

    private final static short BI_BIT_COUNT_24 = 24;
    private final static short BI_BIT_COUNT_32 = 32;

    private static @NonNull byte[] getBitmapFileHeader (int bfSize) {
        byte[] buffer = new byte[BITMAP_FILE_HEADER_SIZE];
        // bfType
        // The file type; must be BM.
        buffer[0] = (byte) (0xFF & BF_TYPE);
        buffer[1] = (byte) (0xFF & (BF_TYPE >> 8));
        // bfSize
        // The size, in bytes, of the bitmap file.
        buffer[2] = (byte) (0xFF & bfSize);
        buffer[3] = (byte) (0xFF & (bfSize >> 8));
        buffer[4] = (byte) (0xFF & (bfSize >> 16));
        buffer[5] = (byte) (0xFF & (bfSize >> 24));
        // bfReserved1
        // Reserved; must be zero.
        buffer[6] = 0x00;
        buffer[7] = 0x00;
        // bfReserved2
        // Reserved; must be zero.
        buffer[8] = 0x00;
        buffer[9] = 0x00;
        // bfOffBits
        // The offset, in bytes, from the beginning of the BITMAP_FILE_HEADER structure to the bitmap bits.
        buffer[10] = (byte) (0xFF & BF_OFF_BITS);
        buffer[11] = (byte) (0xFF & (BF_OFF_BITS >> 8));
        buffer[12] = (byte) (0xFF & (BF_OFF_BITS >> 16));
        buffer[13] = (byte) (0xFF & (BF_OFF_BITS >> 24));
        return buffer;
    }

    private static @NonNull byte[] getBitmapInfoHeader (int biWidth, int biHeight, short biBitCount, int biSizeImage) {
        byte[] buffer = new byte[BITMAP_INFO_HEADER_SIZE];
        // biSize
        // The number of bytes required by the structure.
        buffer[0] = (byte) (0xFF & BITMAP_INFO_HEADER_SIZE);
        buffer[1] = (byte) (0xFF & (BITMAP_INFO_HEADER_SIZE >> 8));
        buffer[2] = (byte) (0xFF & (BITMAP_INFO_HEADER_SIZE >> 16));
        buffer[3] = (byte) (0xFF & (BITMAP_INFO_HEADER_SIZE >> 24));
        // biWidth
        // The width of the bitmap, in pixels.
        buffer[4] = (byte) (0xFF & biWidth);
        buffer[5] = (byte) (0xFF & (biWidth >> 8));
        buffer[6] = (byte) (0xFF & (biWidth >> 16));
        buffer[7] = (byte) (0xFF & (biWidth >> 24));
        // biHeight
        // The height of the bitmap, in pixels. If biHeight is positive,
        // the bitmap is a bottom-up DIB and its origin is the lower-left corner.
        // If biHeight is negative,
        // the bitmap is a top-down DIB and its origin is the upper-left corner.
        // If biHeight is negative, indicating a top-down DIB,
        // biCompression must be either BI_RGB or BI_BIT_FIELDS.
        // Top-down DIBs cannot be compressed.
        buffer[8] = (byte) (0xFF & biHeight);
        buffer[9] = (byte) (0xFF & (biHeight >> 8));
        buffer[10] = (byte) (0xFF & (biHeight >> 16));
        buffer[11] = (byte) (0xFF & (biHeight >> 24));
        // biPlanes
        // The number of planes for the target device. This value must be set to 1.
        buffer[12] = 0x01;
        buffer[13] = 0x00;
        // biBitCount
        // The number of bits-per-pixel.
        // The biBitCount member of the BITMAP_INFO_HEADER structure determines
        // the number of bits that define each pixel and the maximum number of colors in the bitmap.
        buffer[14] = (byte) (0xFF & biBitCount);
        buffer[15] = (byte) (0xFF & (biBitCount >> 8));
        // biCompression
        // The type of compression for a compressed bottom-up bitmap
        // (top-down DIBs cannot be compressed).
        // This member can be one of the following values.
        buffer[16] = 0x00;
        buffer[17] = 0x00;
        buffer[18] = 0x00;
        buffer[19] = 0x00;
        // biSizeImage
        // The size, in bytes, of the image. This may be set to zero for BI_RGB bitmaps.
        // If biCompression is BI_JPEG or BI_PNG,
        // biSizeImage indicates the size of the JPEG or PNG image buffer, respectively.
        buffer[20] = (byte) (0xFF & biSizeImage);
        buffer[21] = (byte) (0xFF & (biSizeImage >> 8));
        buffer[22] = (byte) (0xFF & (biSizeImage >> 16));
        buffer[23] = (byte) (0xFF & (biSizeImage >> 24));
        // biXPelsPerMeter
        // The horizontal resolution, in pixels-per-meter, of the target device for the bitmap.
        // An application can use this value to select a bitmap
        // from a resource group that best matches the characteristics of the current device.
        buffer[24] = (byte) (0xFF & 3780);
        buffer[25] = (byte) (0xFF & (3780 >> 8));
        buffer[26] = (byte) (0xFF & (3780 >> 16));
        buffer[27] = (byte) (0xFF & (3780 >> 24));
        // biYPelsPerMeter
        // The vertical resolution, in pixels-per-meter, of the target device for the bitmap.
        buffer[28] = (byte) (0xFF & 3780);
        buffer[29] = (byte) (0xFF & (3780 >> 8));
        buffer[30] = (byte) (0xFF & (3780 >> 16));
        buffer[31] = (byte) (0xFF & (3780 >> 24));
        // biClrUsed
        // The number of color indexes in the color table that are actually used by the bitmap.
        // If this value is zero, the bitmap uses the maximum number of colors
        // corresponding to the value of the biBitCount member
        // for the compression mode specified by biCompression.
        // If biClrUsed is nonzero and the biBitCount member is less than 16, the biClrUsed member specifies the actual number of colors the graphics engine or device driver accesses. If biBitCount is 16 or greater, the biClrUsed member specifies the size of the color table used to optimize performance of the system color palettes. If biBitCount equals 16 or 32, the optimal color palette starts immediately following the three DWORD masks.
        // When the bitmap array immediately follows the BITMAP_INFO structure, it is a packed bitmap.
        // Packed bitmaps are referenced by a single pointer.
        // Packed bitmaps require that the biClrUsed member must be either zero or the actual size of the color table.
        buffer[32] = 0x00;
        buffer[33] = 0x00;
        buffer[34] = 0x00;
        buffer[35] = 0x00;
        // biClrImportant
        // The number of color indexes that are required for displaying the bitmap.
        // If this value is zero, all colors are required.
        buffer[36] = 0x00;
        buffer[37] = 0x00;
        buffer[38] = 0x00;
        buffer[39] = 0x00;
        return buffer;
    }

    private static @NonNull byte[] getDIBData (@NonNull Bitmap bitmap) {
        int biWidth = bitmap.getWidth();
        int biHeight = bitmap.getHeight();
        byte[] data;
        int[] pixels = new int[biWidth * biHeight];
        bitmap.getPixels(pixels, 0, biWidth, 0, 0, biWidth, biHeight);
        int offset = 0;
        if (bitmap.getConfig() == Bitmap.Config.RGB_565) {
            data = new byte[pixels.length * 3];
            for (int i = pixels.length - 1; i >= 0; i -= biWidth) {
                for (int j = i - biWidth + 1; j <= i; j ++) {
                    data[offset] = (byte) (Color.blue(pixels[j]));
                    data[offset + 1] = (byte) (Color.green(pixels[j]));
                    data[offset + 2] = (byte) (Color.red(pixels[j]));
                    offset += 3;
                }
            }
        }
        else {
            data = new byte[pixels.length * 4];
            for (int i = pixels.length - 1; i >= 0; i -= biWidth) {
                for (int j = i - biWidth + 1; j <= i; j ++) {
                    data[offset] = (byte) (Color.blue(pixels[j]));
                    data[offset + 1] = (byte) (Color.green(pixels[j]));
                    data[offset + 2] = (byte) (Color.red(pixels[j]));
                    data[offset + 3] = (byte) (Color.alpha(pixels[j]));
                    offset += 4;
                }
            }
        }
        return data;
    }

    public static boolean compress(@NonNull Bitmap bitmap, @NonNull OutputStream stream) {
        int biWidth = bitmap.getWidth();
        int biHeight = bitmap.getHeight();
        byte biBitCount;
        int biSizeImage;
        if (bitmap.getConfig() != Bitmap.Config.RGB_565) {
            biBitCount = BI_BIT_COUNT_32;
            biSizeImage = biWidth * biHeight * 4;
        }
        else {
            biBitCount = BI_BIT_COUNT_24;
            biSizeImage = biWidth * biHeight * 3;
        }
        int bfSize = BF_OFF_BITS + biSizeImage;
        try {
            stream.write(getBitmapFileHeader(bfSize));
            stream.write(getBitmapInfoHeader(biWidth, biHeight, biBitCount, biSizeImage));
            stream.write(getDIBData(bitmap));
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
