package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Font;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.graphics.A3Path;
import com.ansdoship.a3wt.util.Segment;

import java.awt.RenderingHints;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Point2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;

import static com.ansdoship.a3wt.awt.A3AWTUtils.strokeCap2BasicStrokeCap;
import static com.ansdoship.a3wt.awt.A3AWTUtils.strokeJoin2BasicStrokeJoin;

public class AWTA3Graphics implements A3Graphics {

    protected volatile Graphics2D graphics2D;
    protected volatile boolean disposed = false;
    protected volatile int width, height;
    protected volatile Data data;
    protected volatile Data cacheData;
    protected final RenderingHints hints = new RenderingHints(null);
    protected static final RenderingHints DEFAULT_HINTS = new RenderingHints(null);
    static {
        DEFAULT_HINTS.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        DEFAULT_HINTS.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        DEFAULT_HINTS.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        DEFAULT_HINTS.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public AWTA3Graphics(BufferedImage bufferedImage) {
        this(bufferedImage.createGraphics(), bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    public AWTA3Graphics(Graphics2D graphics2D, int width, int height) {
        this.graphics2D = graphics2D;
        this.width = width;
        this.height = height;
        reset();
    }

    public Graphics2D getGraphics2D() {
        return graphics2D;
    }

    public void drawShape(Shape shape) {
        checkDisposed("Can't call drawShape() on a disposed A3Graphics");
        mDrawShape(shape);
    }

    private void mDrawShape(Shape shape) {
        switch (data.getStyle()) {
            case Style.STROKE: default:
                graphics2D.draw(shape);
                break;
            case Style.FILL:
                graphics2D.fill(shape);
                break;
        }
    }

    @Override
    public void drawPath(A3Path path) {
        checkDisposed("Can't call drawPath() on a disposed A3Graphics");
        mDrawShape(((AWTA3Path)path).getPath2D());
    }

    @Override
    public void drawImage(A3Image image, int x, int y) {
        checkDisposed("Can't call drawImage() on a disposed A3Graphics");
        graphics2D.drawImage(((AWTA3Image)image).getBufferedImage(), x, y, image.getWidth(), image.getHeight(), null);
    }

    @Override
    public void drawPoint(float x, float y) {
        checkDisposed("Can't call drawPoint() on a disposed A3Graphics");
        Point2D point2D = new Point2D.Float(x, y);
        mDrawShape(new Line2D.Float(point2D, point2D));
    }

    @Override
    public void drawArc(float left, float top, float right, float bottom, float startAngle, float sweepAngle, boolean useCenter) {
        checkDisposed("Can't call drawArc() on a disposed A3Graphics");
        mDrawShape(new Arc2D.Float(new Rectangle2D.Float(left, top, right - left, bottom - top), startAngle, sweepAngle,
                useCenter ? Arc2D.PIE : Arc2D.OPEN));
    }

    @Override
    public void drawLine(float startX, float startY, float stopX, float stopY) {
        checkDisposed("Can't call drawLine() on a disposed A3Graphics");
        mDrawShape(new Line2D.Float(startX, startY, stopX, stopY));
    }

    @Override
    public void drawOval(float left, float top, float right, float bottom) {
        checkDisposed("Can't call drawOval() on a disposed A3Graphics");
        mDrawShape(new Ellipse2D.Float(left, top, right - left, bottom - top));
    }

    @Override
    public void drawRect(float left, float top, float right, float bottom) {
        checkDisposed("Can't call drawRect() on a disposed A3Graphics");
        mDrawShape(new Rectangle2D.Float(left, top, right - left, bottom - top));
    }

    @Override
    public void drawRoundRect(float left, float top, float right, float bottom, float rx, float ry) {
        checkDisposed("Can't call drawRoundRect() on a disposed A3Graphics");
        mDrawShape(new RoundRectangle2D.Float(left, top, right - left, bottom - top, rx, ry));
    }

    @Override
    public void drawText(CharSequence text, float x, float y) {
        checkDisposed("Can't call drawText() on a disposed A3Graphics");
        AttributedCharSequence attributedCharSequence = new AttributedCharSequence(text);
        attributedCharSequence.addAttribute(TextAttribute.SIZE, data.getTextSize());
        attributedCharSequence.addAttribute(TextAttribute.FONT, getFont());
        if (data.isUnderlineText()) attributedCharSequence.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        if (data.isStrikeThroughText()) attributedCharSequence.addAttribute(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        graphics2D.drawString(attributedCharSequence.getIterator(), x, y);
    }

    @Override
    public void drawText(char[] text, int offset, int length, float x, float y) {
        checkDisposed("Can't call drawText() on a disposed A3Graphics");
        AttributedCharSequence attributedCharSequence = new AttributedCharSequence(new Segment(text, offset, length));
        attributedCharSequence.addAttribute(TextAttribute.SIZE, data.getTextSize());
        attributedCharSequence.addAttribute(TextAttribute.FONT, getFont());
        if (data.isUnderlineText()) attributedCharSequence.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        if (data.isStrikeThroughText()) attributedCharSequence.addAttribute(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        graphics2D.drawString(attributedCharSequence.getIterator(), x, y);
    }

    @Override
    public int getColor() {
        checkDisposed("Can't call getColor() on a disposed A3Graphics");
        return graphics2D.getColor().getRGB();
    }

    @Override
    public void setColor(int color) {
        checkDisposed("Can't call setColor() on a disposed A3Graphics");
        data.setColor(color);
        graphics2D.setColor(new Color(color));
    }

    @Override
    public int getStyle() {
        checkDisposed("Can't call getStyle() on a disposed A3Graphics");
        return data.getStyle();
    }

    @Override
    public void setStyle(int style) {
        checkDisposed("Can't call setStyle() on a disposed A3Graphics");
        data.setStyle(style);
    }

    @Override
    public float getStrokeWidth() {
        checkDisposed("Can't call getStrokeWidth() on a disposed A3Graphics");
        return data.getStrokeWidth();
    }

    @Override
    public void setStrokeWidth(float width) {
        checkDisposed("Can't call setStrokeWidth() on a disposed A3Graphics");
        data.setStrokeWidth(width);
        graphics2D.setStroke(new BasicStroke(width, strokeCap2BasicStrokeCap(data.getStrokeCap()),
                strokeJoin2BasicStrokeJoin(data.getStrokeJoin()), data.getStrokeMiter()));
    }

    @Override
    public int getStrokeJoin() {
        checkDisposed("Can't call getStrokeJoin() on a disposed A3Graphics");
        return data.getStrokeJoin();
    }

    @Override
    public void setStrokeJoin(int join) {
        checkDisposed("Can't call setStrokeJoin() on a disposed A3Graphics");
        data.setStrokeJoin(join);
        graphics2D.setStroke(new BasicStroke(data.getStrokeWidth(), strokeCap2BasicStrokeCap(data.getStrokeCap()),
                strokeJoin2BasicStrokeJoin(join), data.getStrokeMiter()));
    }

    @Override
    public int getStrokeCap() {
        checkDisposed("Can't call getStrokeCap() on a disposed A3Graphics");
        return data.getStrokeCap();
    }

    @Override
    public void setStrokeCap(int cap) {
        checkDisposed("Can't call setStrokeCap() on a disposed A3Graphics");
        data.setStrokeCap(cap);
        graphics2D.setStroke(new BasicStroke(data.getStrokeWidth(), strokeCap2BasicStrokeCap(cap),
                strokeJoin2BasicStrokeJoin(data.getStrokeJoin()), data.getStrokeMiter()));
    }

    @Override
    public float getStrokeMiter() {
        checkDisposed("Can't call getStrokeMiter() on a disposed A3Graphics");
        return data.getStrokeMiter();
    }

    @Override
    public void setStrokeMiter(float miter) {
        checkDisposed("Can't call setStrokeMiter() on a disposed A3Graphics");
        data.setStrokeMiter(miter);
        graphics2D.setStroke(new BasicStroke(data.getStrokeWidth(), strokeCap2BasicStrokeCap(data.getStrokeCap()),
                strokeJoin2BasicStrokeJoin(data.getStrokeJoin()), miter));
    }

    @Override
    public A3Font getFont() {
        checkDisposed("Can't call getFont() on a disposed A3Graphics");
        return graphics2D.getFont() == null ? null : new AWTA3Font(graphics2D.getFont());
    }

    @Override
    public void setFont(A3Font font) {
        checkDisposed("Can't call setFont() on a disposed A3Graphics");
        data.setFont(font);
        Font baseFont = font == null ? graphics2D.getFont() : ((AWTA3Font)font).getFont();
        graphics2D.setFont(baseFont.deriveFont(baseFont.getStyle(), data.getTextSize()));
    }

    @Override
    public float getTextSize() {
        checkDisposed("Can't call getTextSize() on a disposed A3Graphics");
        return data.getTextSize();
    }

    @Override
    public void setTextSize(float size) {
        checkDisposed("Can't call setTextSize() on a disposed A3Graphics");
        if (data.getTextSize() == size) return;
        data.setTextSize(size);
        Font baseFont = data.getFont() == null ? graphics2D.getFont() : ((AWTA3Font)data.getFont()).getFont();
        if (baseFont != null) graphics2D.setFont(baseFont.deriveFont(size));
    }

    @Override
    public boolean isAntiAlias() {
        checkDisposed("Can't call isAntiAlias() on a disposed A3Graphics");
        return hints.get(RenderingHints.KEY_ANTIALIASING).equals(RenderingHints.VALUE_ANTIALIAS_ON) &&
                !hints.get(RenderingHints.KEY_TEXT_ANTIALIASING).equals(RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
    }

    @Override
    public void setAntiAlias(boolean antiAlias) {
        checkDisposed("Can't call setAntiAlias() on a disposed A3Graphics");
        hints.put(RenderingHints.KEY_ANTIALIASING,
                antiAlias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        hints.put(RenderingHints.KEY_TEXT_ANTIALIASING,
                antiAlias ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        if (graphics2D != null) graphics2D.setRenderingHints(hints);
    }

    @Override
    public boolean isFilterImage() {
        checkDisposed("Can't call isFilterImage() on a disposed A3Graphics");
        return !hints.get(RenderingHints.KEY_INTERPOLATION).equals(RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
    }

    @Override
    public void setFilterImage(boolean filterImage) {
        checkDisposed("Can't call setFilterImage() on a disposed A3Graphics");
        hints.put(RenderingHints.KEY_INTERPOLATION,
                filterImage ? RenderingHints.VALUE_INTERPOLATION_BILINEAR : RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        if (graphics2D != null) graphics2D.setRenderingHints(hints);
    }

    @Override
    public boolean isSubpixelText() {
        checkDisposed("Can't call isSubpixelText() on a disposed A3Graphics");
        return hints.get(RenderingHints.KEY_FRACTIONALMETRICS).equals(RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    }

    @Override
    public void setSubpixelText(boolean subpixelText) {
        checkDisposed("Can't call setSubpixelText() on a disposed A3Graphics");
        hints.put(RenderingHints.KEY_FRACTIONALMETRICS,
                subpixelText ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        if (graphics2D != null) graphics2D.setRenderingHints(hints);
    }

    @Override
    public boolean isUnderlineText() {
        checkDisposed("Can't call isUnderlineText() on a disposed A3Graphics");
        return data.isUnderlineText();
    }

    @Override
    public void setUnderlineText(boolean underlineText) {
        checkDisposed("Can't call setUnderlineText() on a disposed A3Graphics");
        data.setUnderlineText(underlineText);
    }

    @Override
    public boolean isStrikeThroughText() {
        checkDisposed("Can't call isStrikeThroughText() on a disposed A3Graphics");
        return data.isStrikeThroughText();
    }

    @Override
    public void setStrikeThroughText(boolean strikeThroughText) {
        checkDisposed("Can't call setStrikeThroughText() on a disposed A3Graphics");
        data.setStrikeThroughText(strikeThroughText);
    }

    @Override
    public boolean isDither() {
        checkDisposed("Can't call isDither() on a disposed A3Graphics");
        return hints.get(RenderingHints.KEY_DITHERING).equals(RenderingHints.VALUE_DITHER_ENABLE);
    }

    @Override
    public void setDither(boolean dither) {
        checkDisposed("Can't call setDither() on a disposed A3Graphics");
        hints.put(RenderingHints.KEY_DITHERING,
                dither ? RenderingHints.VALUE_DITHER_ENABLE : RenderingHints.VALUE_DITHER_DISABLE);
        if (graphics2D != null) graphics2D.setRenderingHints(hints);
    }

    @Override
    public void reset() {
        checkDisposed("Can't call reset() on a disposed A3Graphics");
        if (data == null) data = new DefaultData();
        else data.reset();
        apply();
    }

    @Override
    public void save() {
        checkDisposed("Can't call save() on a disposed A3Graphics");
        cacheData = data.copy();
    }

    @Override
    public void restore() {
        checkDisposed("Can't call restore() on a disposed A3Graphics");
        if (cacheData == null) return;
        data = cacheData;
        cacheData = null;
        apply();
    }

    @Override
    public void apply() {
        checkDisposed("Can't call apply() on a disposed A3Graphics");
        if (graphics2D == null) return;
        hints.clear();
        hints.putAll(DEFAULT_HINTS);
        graphics2D.setColor(new Color(data.getColor()));
        graphics2D.setStroke(new BasicStroke(data.getStrokeWidth(), strokeCap2BasicStrokeCap(data.getStrokeCap()),
                strokeJoin2BasicStrokeJoin(data.getStrokeJoin()), data.getStrokeMiter()));
        Font baseFont = data.getFont() == null ? graphics2D.getFont() : ((AWTA3Font)data.getFont()).getFont();
        graphics2D.setFont(baseFont.deriveFont(data.getTextSize()));
        hints.put(RenderingHints.KEY_ANTIALIASING,
                data.isAntiAlias() ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        hints.put(RenderingHints.KEY_TEXT_ANTIALIASING,
                data.isAntiAlias() ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        hints.put(RenderingHints.KEY_INTERPOLATION,
                data.isFilterImage() ? RenderingHints.VALUE_INTERPOLATION_BILINEAR : RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        hints.put(RenderingHints.KEY_FRACTIONALMETRICS,
                data.isSubpixelText() ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        hints.put(RenderingHints.KEY_DITHERING,
                data.isDither() ? RenderingHints.VALUE_DITHER_ENABLE : RenderingHints.VALUE_DITHER_DISABLE);
        graphics2D.setRenderingHints(hints);
    }

    @Override
    public Data getData() {
        checkDisposed("Can't call getData() on a disposed A3Graphics");
        if (data == null) data = new DefaultData();
        data.setColor(getColor());
        data.setStyle(getStyle());
        data.setStrokeWidth(getStrokeWidth());
        data.setStrokeJoin(getStrokeJoin());
        data.setStrokeCap(getStrokeCap());
        data.setStrokeMiter(getStrokeMiter());
        data.setFont(getFont());
        data.setTextSize(getTextSize());
        data.setAntiAlias(isAntiAlias());
        data.setFilterImage(isFilterImage());
        data.setSubpixelText(isSubpixelText());
        data.setUnderlineText(isUnderlineText());
        data.setStrikeThroughText(isStrikeThroughText());
        data.setDither(isDither());
        return data;
    }

    @Override
    public void setData(Data data) {
        checkDisposed("Can't call setData() on a disposed A3Graphics");
        this.data = data;
        apply();
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public synchronized void dispose() {
        if (isDisposed()) return;
        disposed = true;
        data = null;
        graphics2D.dispose();
        graphics2D = null;
    }

}
