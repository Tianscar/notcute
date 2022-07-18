package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.graphics.A3Images;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class AWTA3Images implements A3Images {

    @Override
    public A3Image read(File input) throws IOException {
        return new AWTA3Image(ImageIO.read(input));
    }

    @Override
    public A3Image read(InputStream input) throws IOException {
        return new AWTA3Image(ImageIO.read(input));
    }

    @Override
    public A3Image read(URL input) throws IOException {
        return new AWTA3Image(ImageIO.read(input));
    }

    @Override
    public boolean write(A3Image image, String formatName, File output) throws IOException {
        return ImageIO.write(((AWTA3Image)image).getBufferedImage(), formatName, output);
    }

    @Override
    public boolean write(A3Image image, String formatName, OutputStream output) throws IOException {
        return ImageIO.write(((AWTA3Image)image).getBufferedImage(), formatName, output);
    }

    @Override
    public String[] getReaderFormatNames() {
        return ImageIO.getReaderFormatNames();
    }

    @Override
    public String[] getWriterFormatNames() {
        return ImageIO.getWriterFormatNames();
    }

    @Override
    public A3Image copy(A3Image source) {
        BufferedImage bufferedImage = ((AWTA3Image)source).getBufferedImage();
        ColorModel cm = bufferedImage.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bufferedImage.copyData(bufferedImage.getRaster().createCompatibleWritableRaster());
        return new AWTA3Image(new BufferedImage(cm, raster, isAlphaPremultiplied, null));
    }

}
