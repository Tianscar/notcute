package io.notcute.g2d.swt;

import io.notcute.g2d.AffineTransform;
import io.notcute.g2d.Font;
import io.notcute.g2d.Graphics;
import io.notcute.g2d.Image;
import io.notcute.g2d.geom.Line;
import io.notcute.g2d.geom.PathIterator;
import io.notcute.g2d.geom.Rectangle;
import io.notcute.internal.swt.SWTG2DUtils;
import io.notcute.util.AlreadyDisposedException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;

import static io.notcute.g2d.Color.intRed;
import static io.notcute.g2d.Color.intGreen;
import static io.notcute.g2d.Color.intBlue;
import static io.notcute.g2d.Color.intAlpha;

import java.util.Objects;

public class SWTGraphics implements Graphics {

    private final Info info;
    private final Info cacheInfo;

    private final GC gc;
    private final int width, height;
    public SWTGraphics(GC gc, int width, int height) {
        this.gc = Objects.requireNonNull(gc);
        gc.setAdvanced(true);
        this.width = width;
        this.height = height;
        info = new Info();
        cacheInfo = new Info();
        apply();
    }

    private volatile boolean filterImage;
    private volatile boolean subpixelText;
    private volatile boolean dither;
    private volatile Transform transform = null;
    private volatile Path clip = null;
    private volatile org.eclipse.swt.graphics.Font font = null;

    @Override
    public boolean isDisposed() {
        return gc.isDisposed();
    }

    @Override
    public void dispose() {
        gc.dispose();
        if (transform != null) transform.dispose();
        if (clip != null) clip.dispose();
        if (font != null) font.dispose();
    }

    @Override
    public int getWidth() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return width;
    }

    @Override
    public int getHeight() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return height;
    }

    @Override
    public void drawColor() {
        if (isDisposed()) throw new AlreadyDisposedException();
        gc.fillRectangle(0, 0, width, height);
    }

    @Override
    public void drawImage(Image image, AffineTransform transform) {
        if (isDisposed()) throw new AlreadyDisposedException();
        gc.setInterpolation(filterImage ? (dither ? SWT.HIGH : SWT.LOW) : SWT.NONE);
        Device device = gc.getDevice();
        Transform originalTransform = new Transform(device);
        gc.getTransform(originalTransform);
        AffineTransform at = SWTG2DUtils.toNotcuteAffineTransform(originalTransform);
        at.concatenate(transform);
        Transform tmpTransform = SWTG2DUtils.toSWTTransform(device, at);
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
        if (isDisposed()) throw new AlreadyDisposedException();
        drawPathIterator(new Line(x, y, x, y).getPathIterator());
    }

    @Override
    public void drawPathIterator(PathIterator iterator) {
        if (isDisposed()) throw new AlreadyDisposedException();
        Device device = gc.getDevice();
        Path tmpPath = SWTG2DUtils.toSWTPath(device, iterator);
        int original = gc.getFillRule();
        gc.setFillRule(SWTG2DUtils.toSWTGCFillRule(iterator.getWindingRule()));
        gc.drawPath(tmpPath);
        gc.setFillRule(original);
        tmpPath.dispose();
    }

    @Override
    public void drawText(CharSequence text, int start, int end, AffineTransform transform) {
        if (isDisposed()) throw new AlreadyDisposedException();
        gc.setInterpolation(subpixelText ? (dither ? SWT.HIGH : SWT.LOW) : SWT.NONE);
        Device device = gc.getDevice();
        Transform originalTransform = new Transform(device);
        gc.getTransform(originalTransform);
        AffineTransform at = SWTG2DUtils.toNotcuteAffineTransform(originalTransform);
        at.concatenate(transform);
        Transform tmpTransform = SWTG2DUtils.toSWTTransform(device, at);
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
        if (isDisposed()) throw new AlreadyDisposedException();
        drawText(new String(text, offset, length), 0, length - offset, transform);
    }

    @Override
    public float measureText(CharSequence text, int start, int end) {
        if (isDisposed()) throw new AlreadyDisposedException();
        return gc.stringExtent(text.subSequence(start, end).toString()).x;
    }

    @Override
    public float measureText(char[] text, int offset, int length) {
        if (isDisposed()) throw new AlreadyDisposedException();
        return measureText(new String(text, offset, length));
    }

    @Override
    public void getFontMetrics(Font.Metrics metrics) {
        if (isDisposed()) throw new AlreadyDisposedException();
        FontMetrics fontMetrics = gc.getFontMetrics();
        metrics.setMetrics(0, fontMetrics.getAscent(), fontMetrics.getDescent(), fontMetrics.getLeading(),
                fontMetrics.getAscent(), fontMetrics.getDescent());
    }

    @Override
    public void getTextBounds(CharSequence text, int start, int end, Rectangle bounds) {
        if (isDisposed()) throw new AlreadyDisposedException();
        Point point = gc.stringExtent(text.subSequence(start, end).toString());
        bounds.setRect(0, 0, point.x, point.y);
    }

    @Override
    public void getTextBounds(char[] text, int offset, int length, Rectangle bounds) {
        if (isDisposed()) throw new AlreadyDisposedException();
        getTextBounds(new String(text, offset, length), bounds);
    }

    @Override
    public Info getInfo() {
        return info;
    }

    @Override
    public void apply() {
        Device device = gc.getDevice();
        if (transform != null) transform.dispose();
        transform = info.getTransform() == null ? null : SWTG2DUtils.toSWTTransform(device, info.getTransform());
        gc.setTransform(transform);
        if (clip != null) clip.dispose();
        clip = info.getClip() == null ? null : SWTG2DUtils.toSWTPath(device, info.getClip().getPathIterator());
        gc.setClipping(clip);
        if (font != null) font.dispose();
        SWTFont swtFont = (SWTFont) info.getFont();
        font = swtFont == null ? null : new org.eclipse.swt.graphics.Font(device, swtFont.getFontData());
        gc.setFont(font);

        setColor(info.getColor());
        gc.setLineAttributes(new LineAttributes(info.getStrokeWidth(),
                SWTG2DUtils.toSWTLineCap(info.getStrokeCap()), SWTG2DUtils.toSWTLineJoin(info.getStrokeJoin()),
                SWT.LINE_SOLID, null, 0, info.getStrokeMiter()));
        gc.setAntialias(info.isAntiAlias() ? SWT.ON : SWT.OFF);
        gc.setTextAntialias(info.isAntiAlias() ? SWT.ON : SWT.OFF);

        applyInterpolation();
    }

    private void applyInterpolation() {
        filterImage = info.isFilterImage();
        subpixelText = info.isSubpixelText();
        dither = info.isDither();
    }

    private void setColor(int color) {
        Color swtColor = new Color(intRed(color), intGreen(color), intBlue(color), intAlpha(color));
        gc.setBackground(swtColor);
        gc.setForeground(swtColor);
    }

    @Override
    public void save() {
        if (isDisposed()) throw new AlreadyDisposedException();
        info.to(cacheInfo);
    }

    @Override
    public void restore() {
        if (isDisposed()) throw new AlreadyDisposedException();
        info.from(cacheInfo);
    }

}
