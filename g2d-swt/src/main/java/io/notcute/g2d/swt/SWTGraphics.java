package io.notcute.g2d.swt;

import io.notcute.g2d.AffineTransform;
import io.notcute.g2d.Font;
import io.notcute.g2d.Graphics;
import io.notcute.g2d.Image;
import io.notcute.g2d.geom.Line;
import io.notcute.g2d.geom.PathIterator;
import io.notcute.g2d.geom.Rectangle;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Transform;

import java.util.Objects;

public class SWTGraphics implements Graphics {

    private final GC gc;
    private final int width, height;
    public SWTGraphics(GC gc, int width, int height) {
        this.gc = Objects.requireNonNull(gc);
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean isDisposed() {
        return gc.isDisposed();
    }

    @Override
    public void dispose() {
        gc.dispose();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void drawColor() {
        gc.fillRectangle(0, 0, width, height);
    }

    @Override
    public void drawImage(Image image, AffineTransform transform) {
        Device device = gc.getDevice();
        Transform originalTransform = new Transform(device);
        gc.getTransform(originalTransform);
        AffineTransform at = Util.toNotcuteAffineTransform(originalTransform);
        at.concatenate(transform);
        Transform tmpTransform = Util.toSWTTransform(device, at);
        gc.setTransform(tmpTransform);
        org.eclipse.swt.graphics.Image tmpImage = new org.eclipse.swt.graphics.Image(device, ((SWTImage) image).getImageData());
        gc.drawImage(tmpImage, 0, 0);
        gc.setTransform(originalTransform);
        tmpImage.dispose();
        if (tmpTransform != null) tmpTransform.dispose();
        originalTransform.dispose();
    }

    @Override
    public void drawPoint(float x, float y) {
        drawPathIterator(new Line(x, y, x, y).getPathIterator());
    }

    @Override
    public void drawPathIterator(PathIterator iterator) {
        Path path = Util.toSWTPath(gc.getDevice(), iterator);
        gc.drawPath(path);
        path.dispose();
    }

    @Override
    public void drawText(CharSequence text, int start, int end, AffineTransform transform) {
        Device device = gc.getDevice();
        Transform originalTransform = new Transform(device);
        gc.getTransform(originalTransform);
        AffineTransform at = Util.toNotcuteAffineTransform(originalTransform);
        at.concatenate(transform);
        Transform tmpTransform = Util.toSWTTransform(device, at);
        gc.setTransform(tmpTransform);
        Path path = new Path(device);
        path.addString(text.subSequence(start, end).toString(), 0, 0, gc.getFont());
        gc.drawPath(path);
        gc.setTransform(originalTransform);
        path.dispose();
        if (tmpTransform != null) tmpTransform.dispose();
        originalTransform.dispose();
    }

    @Override
    public void drawText(char[] text, int offset, int length, AffineTransform transform) {
        drawText(new String(text, offset, length), 0, length - offset, transform);
    }

    @Override
    public float measureText(CharSequence text, int start, int end) {
        return 0;
    }

    @Override
    public float measureText(char[] text, int offset, int length) {
        return 0;
    }

    @Override
    public void getFontMetrics(Font.Metrics metrics) {

    }

    @Override
    public void getTextBounds(CharSequence text, int start, int end, Rectangle bounds) {

    }

    @Override
    public void getTextBounds(char[] text, int offset, int length, Rectangle bounds) {

    }

    @Override
    public Info getInfo() {
        return null;
    }

    @Override
    public void save() {

    }

    @Override
    public void restore() {

    }

}
