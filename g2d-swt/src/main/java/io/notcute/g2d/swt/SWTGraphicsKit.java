package io.notcute.g2d.swt;

import io.notcute.app.Assets;
import io.notcute.g2d.AnimatedImage;
import io.notcute.g2d.Font;
import io.notcute.g2d.GraphicsKit;
import io.notcute.g2d.Image;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class SWTGraphicsKit implements GraphicsKit {

    private final Device device;
    public SWTGraphicsKit(Device device) {
        if (device == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
        this.device = device;
    }

    @Override
    public Image createImage(int width, int height, int type) {
        return new SWTImage(new ImageData(width, height, Util.toSWTImageDepth(type), new PaletteData()));
    }

    @Override
    public Image readImage(File input, int type) {
        try {
            return SWTImageIO.read(device, input, type);
        } catch (IOException e) {
            return null;
        }
        catch (SWTException e) {
            if (e.code == SWT.ERROR_IO) return null;
            else throw e;
        }
    }

    @Override
    public Image readImage(InputStream input, int type) {
        try {
            return SWTImageIO.read(device, input, type);
        } catch (IOException e) {
            return null;
        }
        catch (SWTException e) {
            if (e.code == SWT.ERROR_IO) return null;
            else throw e;
        }
    }

    @Override
    public Image readImage(URL input, int type) {
        try {
            return SWTImageIO.read(device, input, type);
        } catch (IOException e) {
            return null;
        }
        catch (SWTException e) {
            if (e.code == SWT.ERROR_IO) return null;
            else throw e;
        }
    }

    @Override
    public Image readImage(Assets assets, String input, int type) {
        return readImage(assets.readAsset(input), type);
    }

    @Override
    public AnimatedImage readAnimatedImage(File input, int type) {
        Image image = readImage(input, type);
        if (image instanceof AnimatedImage) return (AnimatedImage) image;
        else return null;
    }

    @Override
    public AnimatedImage readAnimatedImage(InputStream input, int type) {
        Image image = readImage(input, type);
        if (image instanceof AnimatedImage) return (AnimatedImage) image;
        else return null;
    }

    @Override
    public AnimatedImage readAnimatedImage(URL input, int type) {
        Image image = readImage(input, type);
        if (image instanceof AnimatedImage) return (AnimatedImage) image;
        else return null;
    }

    @Override
    public AnimatedImage readAnimatedImage(Assets assets, String input, int type) {
        Image image = readImage(assets, input, type);
        if (image instanceof AnimatedImage) return (AnimatedImage) image;
        else return null;
    }

    @Override
    public boolean writeImage(Image image, String formatName, int quality, File output) {
        try {
            return SWTImageIO.write(image, formatName, quality, output);
        } catch (IOException e) {
            return false;
        }
        catch (SWTException e) {
            if (e.code == SWT.ERROR_IO) return false;
            else throw e;
        }
    }

    @Override
    public boolean writeImage(Image image, String formatName, int quality, OutputStream output) {
        try {
            return SWTImageIO.write(image, formatName, quality, output);
        } catch (IOException e) {
            return false;
        }
        catch (SWTException e) {
            if (e.code == SWT.ERROR_IO) return false;
            else throw e;
        }
    }

    @Override
    public boolean writeAnimatedImage(AnimatedImage image, String formatName, int quality, File output) {
        return writeImage(image, formatName, quality, output);
    }

    @Override
    public boolean writeAnimatedImage(AnimatedImage image, String formatName, int quality, OutputStream output) {
        return writeImage(image, formatName, quality, output);
    }

    @Override
    public String[] getImageReaderFormatNames() {
        Set<String> formatNames = new HashSet<>();
        for (String formatName : SWTImageIO.getReaderFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public String[] getImageWriterFormatNames() {
        Set<String> formatNames = new HashSet<>();
        for (String formatName : SWTImageIO.getWriterFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public String[] getAnimatedImageReaderFormatNames() {
        Set<String> formatNames = new HashSet<>();
        for (String formatName : SWTImageIO.getAnimatedReaderFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public String[] getAnimatedImageWriterFormatNames() {
        Set<String> formatNames = new HashSet<>();
        for (String formatName : SWTImageIO.getAnimatedWriterFormatNames()) {
            formatNames.add(formatName.toLowerCase());
        }
        return formatNames.toArray(new String[0]);
    }

    @Override
    public Font getFont(String familyName, int style) {
        return new SWTFont(device, new FontData(familyName, 12, Util.toSWTFontStyle(style)));
    }

    @Override
    public Font getDefaultFont() {
        return SWTFont.getDefaultFont(device);
    }

    @Override
    public Font readFont(File input) {
        try {
            java.awt.Font font = Util.readFont(input);
            if (font != null) {
                if (!device.loadFont(input.getAbsolutePath())) {
                    return new SWTFont(device, new FontData(font.getFontName(), 12, Util.fromAWTtoSWTFontStyle(font.getStyle())));
                }
            }
        } catch (IOException ignored) {
        }
        return null;
    }

    @Override
    public Font readFont(Assets assets, String input) {
        throw new UnsupportedOperationException();
    }

}
