package io.notcute.g2d.awt;

import io.notcute.g2d.AffineTransform;
import io.notcute.g2d.Font;
import io.notcute.g2d.Graphics;
import io.notcute.g2d.Image;
import io.notcute.g2d.geom.PathIterator;
import io.notcute.g2d.geom.Rectangle;
import io.notcute.g2d.geom.Shape;
import io.notcute.internal.awt.AWTG2DUtils;
import io.notcute.util.AlreadyDisposedException;

import java.awt.*;
import java.awt.font.LineMetrics;
import java.awt.font.TextAttribute;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.HashMap;
import java.util.Objects;

import static io.notcute.g2d.Graphics.Style.FILL;
import static io.notcute.g2d.Graphics.Style.STROKE;

public class AWTGraphics implements Graphics {

    private final Graphics2D graphics2D;
    private final int width, height;

    private final Info info;
    private final Info cacheInfo;

    private volatile boolean disposed = false;

    private static final RenderingHints DEFAULT_HINTS = new RenderingHints(null);
    static {
        DEFAULT_HINTS.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        DEFAULT_HINTS.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        DEFAULT_HINTS.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        DEFAULT_HINTS.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    }

    public AWTGraphics(BufferedImage bufferedImage) {
        this(bufferedImage.createGraphics(), bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    public AWTGraphics(Graphics2D graphics2D, int width, int height) {
        this.graphics2D = Objects.requireNonNull(graphics2D);
        this.width = width;
        this.height = height;
        info = new Info();
        cacheInfo = new Info();
        apply();
    }

    public Graphics2D getGraphics2D() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return graphics2D;
    }

    @Override
    public void apply() {
        if (isDisposed()) throw new AlreadyDisposedException();
        AffineTransform transform = info.getTransform();
        if (transform == null) graphics2D.getTransform().setToIdentity();
        else graphics2D.setTransform(AWTG2DUtils.toAWTTransform(transform));
        Shape clip = info.getClip();
        graphics2D.setClip(clip == null ? null : AWTG2DUtils.toAWTPath2D(clip.getPathIterator()));
        graphics2D.setColor(new Color(info.getColor(), true));
        graphics2D.setStroke(
                new BasicStroke(
                        info.getStrokeWidth(),
                        AWTG2DUtils.toAWTStrokeCap(info.getStrokeCap()),
                        AWTG2DUtils.toAWTStrokeJoin(info.getStrokeJoin()),
                        info.getStrokeMiter()));
        java.awt.Font font = info.getFont() == null ? (graphics2D.getFont() == null ? null : graphics2D.getFont()) : ((AWTFont)info.getFont()).getFont();
        if (font != null) {
            graphics2D.setFont(font.deriveFont(info.getTextSize()));
            info.setFont(new AWTFont(graphics2D.getFont()));
        }
        RenderingHints hints = new RenderingHints(null);
        hints.putAll(DEFAULT_HINTS);
        hints.put(RenderingHints.KEY_ANTIALIASING,
                info.isAntiAlias() ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        hints.put(RenderingHints.KEY_TEXT_ANTIALIASING,
                info.isAntiAlias() ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        hints.put(RenderingHints.KEY_INTERPOLATION,
                info.isFilterImage() ? RenderingHints.VALUE_INTERPOLATION_BILINEAR : RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        hints.put(RenderingHints.KEY_FRACTIONALMETRICS,
                info.isSubpixelText() ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        hints.put(RenderingHints.KEY_DITHERING,
                info.isDither() ? RenderingHints.VALUE_DITHER_ENABLE : RenderingHints.VALUE_DITHER_DISABLE);
        graphics2D.setRenderingHints(hints);
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
        graphics2D.fillRect(0, 0, width, height);
    }

    @Override
    public void drawImage(Image image, AffineTransform transform) {
        if (isDisposed()) throw new AlreadyDisposedException();
        graphics2D.drawImage(((AWTImage)image).getBufferedImage(), AWTG2DUtils.toAWTTransform(transform), null);
    }

    @Override
    public void drawPoint(float x, float y) {
        if (isDisposed()) throw new AlreadyDisposedException();
        graphics2D.draw(new Line2D.Float(x, y, x, y));
    }

    private void draw(java.awt.Shape shape) {
        switch (info.getStyle()) {
            case STROKE:
                graphics2D.draw(shape);
                break;
            case FILL:
                graphics2D.fill(shape);
                break;
        }
    }

    @Override
    public void drawPathIterator(PathIterator iterator) {
        if (isDisposed()) throw new AlreadyDisposedException();
        draw(AWTG2DUtils.toAWTPath2D(iterator));
    }

    private AttributedCharacterIterator getAttributedCharacterIterator(String text, int start, int end, AffineTransform transform) {
        java.awt.Font font = info.getFont() == null ? null : ((AWTFont)info.getFont()).getFont().deriveFont(AWTG2DUtils.toAWTTransform(transform));
        HashMap<AttributedCharacterIterator.Attribute, Object> attributes = new HashMap<>();
        if (font != null) attributes.put(TextAttribute.FONT, font);
        attributes.put(TextAttribute.SIZE, info.getTextSize());
        if (info.isUnderlineText()) attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        if (info.isStrikeThroughText()) attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        AttributedString attributedString = new AttributedString(text);
        attributedString.addAttributes(attributes, start, end);
        return attributedString.getIterator();
    }

    @Override
    public void drawText(CharSequence text, int start, int end, AffineTransform transform) {
        if (isDisposed()) throw new AlreadyDisposedException();
        graphics2D.drawString(getAttributedCharacterIterator(text.toString(), start, end, transform), 0, 0);
    }

    @Override
    public void drawText(char[] text, int offset, int length, AffineTransform transform) {
        if (isDisposed()) throw new AlreadyDisposedException();
        graphics2D.drawString(getAttributedCharacterIterator(new String(text, offset, length), 0, length, transform), 0, 0);
    }

    @Override
    public float measureText(CharSequence text, int start, int end) {
        if (isDisposed()) throw new AlreadyDisposedException();
        return (float) graphics2D.getFont().getStringBounds(text.toString(), start, end, graphics2D.getFontRenderContext()).getWidth();
    }

    @Override
    public float measureText(char[] text, int offset, int length) {
        if (isDisposed()) throw new AlreadyDisposedException();
        return (float) graphics2D.getFont().getStringBounds(text, offset, offset + length, graphics2D.getFontRenderContext()).getWidth();
    }

    @Override
    public void getFontMetrics(Font.Metrics metrics) {
        Objects.requireNonNull(metrics);
        if (isDisposed()) throw new AlreadyDisposedException();
        LineMetrics lineMetrics = graphics2D.getFontMetrics().getLineMetrics("", graphics2D);
        metrics.setMetrics(0, lineMetrics.getAscent(),
                lineMetrics.getDescent(), lineMetrics.getLeading(),
                lineMetrics.getAscent(), lineMetrics.getDescent());
    }

    @Override
    public void getTextBounds(CharSequence text, int start, int end, Rectangle bounds) {
        Objects.requireNonNull(bounds);
        if (isDisposed()) throw new AlreadyDisposedException();
        Rectangle2D.Float stringBounds = AWTG2DUtils.floatRectangle2D(graphics2D.getFont().getStringBounds(text.toString(), start, end,
                    graphics2D.getFontRenderContext()));
        bounds.setRect(stringBounds.x, stringBounds.y, stringBounds.width, stringBounds.height);
    }

    @Override
    public void getTextBounds(char[] text, int offset, int length, Rectangle bounds) {
        Objects.requireNonNull(bounds);
        if (isDisposed()) throw new AlreadyDisposedException();
        Rectangle2D.Float stringBounds = AWTG2DUtils.floatRectangle2D(graphics2D.getFont().getStringBounds(text, offset, offset + length,
                graphics2D.getFontRenderContext()));
        bounds.setRect(stringBounds.x, stringBounds.y, stringBounds.width, stringBounds.height);
    }

    @Override
    public Info getInfo() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return info;
    }

    Info getCacheInfo() {
        if (isDisposed()) throw new AlreadyDisposedException();
        return cacheInfo;
    }

    void setCacheInfo(Info info) {
        if (isDisposed()) throw new AlreadyDisposedException();
        info.to(cacheInfo);
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

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public void dispose() {
        if (isDisposed()) return;
        disposed = true;
        graphics2D.dispose();
    }

}
