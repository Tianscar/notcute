package io.notcute.g2d.swt;

import io.notcute.g2d.MultiFrameImage;
import io.notcute.g2d.Image;
import io.notcute.util.MathUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class BasicSIIOSpi implements SIIOServiceProvider {

    private static final String[] READER_MIME_TYPES = new String[] { "image/bmp", "image/jpeg", "image/png", "image/x-png"};
    private static final String[] WRITER_MIME_TYPES = new String[] { "image/bmp", "image/jpeg", "image/png", "image/x-png"};
    private static final String[] MULTI_FRAME_READER_MIME_TYPES = new String[] { "image/gif", "image/tiff", "image/x-icon",
            "image/ico", "image/vnd.microsoft.icon" };
    
    @Override
    public Image read(Device device, InputStream stream) throws IOException, SWTException {
        ImageLoader loader = new ImageLoader();
        ImageData[] data = loader.load(stream);
        if (data.length == 0) return null;
        else if (data.length == 1) {
            return new SWTImage(data[0]);
        }
        else {
            ImageData prevData = data[0];
            org.eclipse.swt.graphics.Image prev = null;
            Image.Frame[] frames = new Image.Frame[data.length];
            frames[0] = new Image.Frame(new SWTImage((ImageData) prevData.clone()), prevData.x, prevData.y, MathUtils.clamp(prevData.delayTime * 10L));
            ImageData currData, tmpData;
            org.eclipse.swt.graphics.Image curr, tmp;
            GC currGC;
            for (int i = 1; i < frames.length; i++) {
                currData = data[i];
                switch (currData.disposalMethod) {
                    case SWT.DM_FILL_BACKGROUND:
                        if (loader.backgroundPixel != -1) {
                            curr = new org.eclipse.swt.graphics.Image(device, currData.width, currData.height); // curr ALLOC
                            currGC = new GC(curr); // currGC ALLOC
                            currGC.setBackground(new Color(currData.palette.getRGB(loader.backgroundPixel)));
                            currGC.fillRectangle(prevData.x - currData.x, prevData.y - currData.y, prevData.width, prevData.height);
                            currGC.drawImage((tmp = new org.eclipse.swt.graphics.Image(device, currData)), 0, 0); // tmp ALLOC
                            tmp.dispose(); // tmp FREE
                            currGC.dispose(); // currGC FREE
                            if (prev != null) prev.dispose(); // prev FREE
                            prev = curr; // curr to prev
                            tmpData = curr.getImageData();
                            tmpData.x = currData.x;
                            tmpData.y = currData.y;
                            tmpData.delayTime = currData.delayTime;
                            currData = tmpData;
                            frames[i] = new Image.Frame(new SWTImage(currData), currData.x, currData.y, MathUtils.clamp(currData.delayTime * 10L));
                        }
                        break;
                    case SWT.DM_FILL_PREVIOUS:
                        curr = new org.eclipse.swt.graphics.Image(device, currData.width, currData.height); // curr ALLOC
                        currGC = new GC(curr); // currGC ALLOC
                        if (prev != null) currGC.drawImage(prev, prevData.x - currData.x, prevData.y - currData.y);
                        currGC.drawImage((tmp = new org.eclipse.swt.graphics.Image(device, currData)), 0, 0); // tmp ALLOC
                        tmp.dispose(); // tmp FREE
                        currGC.dispose(); // currGC FREE
                        tmpData = curr.getImageData();
                        tmpData.x = currData.x;
                        tmpData.y = currData.y;
                        tmpData.delayTime = currData.delayTime;
                        currData = tmpData;
                        curr.dispose(); // curr FREE
                        frames[i] = new Image.Frame(new SWTImage(currData), currData.x, currData.y, MathUtils.clamp(currData.delayTime * 10L));
                        break;
                    case SWT.DM_FILL_NONE:
                        curr = new org.eclipse.swt.graphics.Image(device, currData.width, currData.height); // curr ALLOC
                        currGC = new GC(curr); // currGC ALLOC
                        if (prev != null) currGC.drawImage(prev, prevData.x - currData.x, prevData.y - currData.y);
                        currGC.drawImage((tmp = new org.eclipse.swt.graphics.Image(device, currData)), 0, 0); // tmp ALLOC
                        tmp.dispose(); // tmp FREE
                        currGC.dispose(); // currGC FREE
                        if (prev != null) prev.dispose(); // prev FREE
                        prev = curr; // curr to prev
                        tmpData = curr.getImageData();
                        tmpData.x = currData.x;
                        tmpData.y = currData.y;
                        tmpData.delayTime = currData.delayTime;
                        currData = tmpData;
                        frames[i] = new Image.Frame(new SWTImage(currData), currData.x, currData.y, MathUtils.clamp(currData.delayTime * 10L));
                        break;
                    case SWT.DM_UNSPECIFIED:
                    default:
                        frames[i] = new Image.Frame(new SWTImage(currData), currData.x, currData.y, MathUtils.clamp(currData.delayTime * 10L));
                        break;
                }
            }
            if (prev != null) prev.dispose(); // prev FREE
            MultiFrameImage image = new MultiFrameImage(frames);
            image.setLooping(loader.repeatCount - 1);
            return image;
        }
    }

    @Override
    public boolean write(Image im, String mimeType, int quality, OutputStream output) throws IOException, SWTException {
        if (im instanceof MultiFrameImage) return false;
        else {
            if (mimeType.equalsIgnoreCase("png")) {
                ImageLoader loader = new ImageLoader();
                loader.data = new ImageData[] {((SWTImage) im).getImageData()};
                loader.compression = 3;
                loader.save(output, SWT.IMAGE_PNG);
                output.flush();
                return true;
            }
            else if (mimeType.equalsIgnoreCase("jpg") || mimeType.equalsIgnoreCase("jpeg")) {
                ImageLoader loader = new ImageLoader();
                loader.data = new ImageData[] {((SWTImage) im).getImageData()};
                loader.compression = MathUtils.clamp(quality, 0, 100);
                loader.save(output, SWT.IMAGE_JPEG);
                output.flush();
                return true;
            }
            else if (mimeType.equalsIgnoreCase("bmp")) {
                ImageLoader loader = new ImageLoader();
                loader.data = new ImageData[] {((SWTImage) im).getImageData()};
                loader.save(output, SWT.IMAGE_BMP);
                output.flush();
                return true;
            }
            else return false;
        }
    }

    @Override
    public String[] getReaderMIMETypes() {
        return READER_MIME_TYPES.clone();
    }

    @Override
    public String[] getWriterMIMETypes() {
        return WRITER_MIME_TYPES.clone();
    }

    @Override
    public String[] getMultiFrameImageReaderMIMETypes() {
        return MULTI_FRAME_READER_MIME_TYPES.clone();
    }

    @Override
    public String[] getMultiFrameImageWriterMIMETypes() {
        return new String[0];
    }

}
