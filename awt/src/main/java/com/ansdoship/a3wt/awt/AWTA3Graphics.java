package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Font;
import com.ansdoship.a3wt.graphics.A3Graphics;
import com.ansdoship.a3wt.graphics.A3Image;
import com.ansdoship.a3wt.graphics.A3Path;
import com.ansdoship.a3wt.util.A3CharSegment;

import java.awt.RenderingHints;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextLayout;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Point2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.HashMap;

import static com.ansdoship.a3wt.awt.A3AWTUtils.strokeCap2BasicStrokeCap;
import static com.ansdoship.a3wt.awt.A3AWTUtils.strokeJoin2BasicStrokeJoin;
import static com.ansdoship.a3wt.util.A3Asserts.checkArgNotNull;

public class AWTA3Graphics implements A3Graphics {

    protected volatile Graphics2D graphics2D;
    protected volatile boolean disposed = false;
    protected volatile int width;
    protected volatile int height;
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

    public AWTA3Graphics(final BufferedImage bufferedImage) {
        this(bufferedImage.createGraphics(), bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    public AWTA3Graphics(final Graphics2D graphics2D, final int width, final int height) {
        this.graphics2D = graphics2D;
        this.width = width;
        this.height = height;
        reset();
    }

    public Graphics2D getGraphics2D() {
        return graphics2D;
    }

    @Override
    public void drawColor() {
        checkDisposed("Can't call clearColor() on a disposed A3Graphics");
        graphics2D.fillRect(0, 0, width, height);
    }

    public void drawShape(final Shape shape) {
        checkDisposed("Can't call drawShape() on a disposed A3Graphics");
        mDrawShape(shape);
    }

    private void mDrawShape(final Shape shape) {
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
    public void drawPath(final A3Path path) {
        checkArgNotNull(path, "path");
        checkDisposed("Can't call drawPath() on a disposed A3Graphics");
        mDrawShape(((AWTA3Path)path).getPath2D());
    }

    @Override
    public void drawImage(final A3Image image, final int x, final int y) {
        checkArgNotNull(image, "image");
        checkDisposed("Can't call drawImage() on a disposed A3Graphics");
        graphics2D.drawImage(((AWTA3Image)image).getBufferedImage(),
                x - image.getHotSpotX(), y - image.getHotSpotY(),
                image.getWidth(), image.getHeight(), null);
    }

    @Override
    public void drawPoint(final float x, final float y) {
        checkDisposed("Can't call drawPoint() on a disposed A3Graphics");
        Point2D point2D = new Point2D.Float(x, y);
        mDrawShape(new Line2D.Float(point2D, point2D));
    }

    @Override
    public void drawArc(final float left, final float top, final float right, final float bottom,
                        final float startAngle, final float sweepAngle, final boolean useCenter) {
        checkDisposed("Can't call drawArc() on a disposed A3Graphics");
        mDrawShape(new Arc2D.Float(new Rectangle2D.Float(left, top, right - left, bottom - top), startAngle, sweepAngle,
                useCenter ? Arc2D.PIE : Arc2D.OPEN));
    }

    @Override
    public void drawLine(final float startX, final float startY, final float stopX, final float stopY) {
        checkDisposed("Can't call drawLine() on a disposed A3Graphics");
        mDrawShape(new Line2D.Float(startX, startY, stopX, stopY));
    }

    @Override
    public void drawOval(final float left, final float top, final float right, final float bottom) {
        checkDisposed("Can't call drawOval() on a disposed A3Graphics");
        mDrawShape(new Ellipse2D.Float(left, top, right - left, bottom - top));
    }

    @Override
    public void drawRect(final float left, final float top, final float right, final float bottom) {
        checkDisposed("Can't call drawRect() on a disposed A3Graphics");
        mDrawShape(new Rectangle2D.Float(left, top, right - left, bottom - top));
    }

    @Override
    public void drawRoundRect(final float left, final float top, final float right, final float bottom, final float rx, final float ry) {
        checkDisposed("Can't call drawRoundRect() on a disposed A3Graphics");
        mDrawShape(new RoundRectangle2D.Float(left, top, right - left, bottom - top, rx, ry));
    }

    @Override
    public void drawText(final CharSequence text, final float x, final float y) {
        checkArgNotNull(text, "text");
        checkDisposed("Can't call drawText() on a disposed A3Graphics");
        final Runnable[] cleanup = new Runnable[1];
        final AttributedCharacterIterator iterator = mGetAttributedCharacterIterator(text, cleanup);
        final Runnable cleanupRunnable = cleanup[0];
        try {
            mDrawText(iterator, x, y);
        }
        finally {
            if (cleanupRunnable != null) cleanupRunnable.run();
        }
    }

    private AttributedCharacterIterator mGetAttributedCharacterIterator(final CharSequence text, final Runnable[] cleanup) {
        final A3Font a3Font = getFont();
        final Font font = a3Font == null ? null : ((AWTA3Font)getFont()).getFont();
        final HashMap<AttributedCharacterIterator.Attribute, Object> attributes = new HashMap<>();
        if (font != null) attributes.put(TextAttribute.FONT, font);
        attributes.put(TextAttribute.SIZE, data.getTextSize());
        if (data.isUnderlineText()) attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        if (data.isStrikeThroughText()) attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        if (text instanceof String) {
            final AttributedString attributedString = new AttributedString((String) text);
            attributedString.addAttributes(attributes, 0, text.length());
            cleanup[0] = null;
            return attributedString.getIterator();
        }
        else if (text instanceof A3CharSegment) {
            final A3CharSegment segment = (A3CharSegment) text;
            final AttributedA3CharSegment attributedSegment = new AttributedA3CharSegment(segment);
            attributedSegment.addAttributes(attributes, 0, segment.length());
            cleanup[0] = null;
            return attributedSegment.getIterator();
        }
        else {
            final AttributedCharSequence attributedCharSequence = new AttributedCharSequence(text);
            attributedCharSequence.addAttributes(attributes, 0, text.length());
            if (attributedCharSequence.isNewArraySegment()) {
                cleanup[0] = new Runnable() {
                    @Override
                    public void run() {
                        attributedCharSequence.getNewArraySegment().fillZero();
                    }
                };
            }
            return attributedCharSequence.getIterator();
        }
    }

    private AttributedCharacterIterator mGetAttributedCharacterIterator(final char[] text, final int offset, final int length) {
        final AttributedA3CharSegment attributedSegment = new AttributedA3CharSegment(new A3CharSegment(text, offset, length));
        final A3Font a3Font = getFont();
        final Font font = a3Font == null ? null : ((AWTA3Font)getFont()).getFont();
        if (font != null) attributedSegment.addAttribute(TextAttribute.FONT, font);
        attributedSegment.addAttribute(TextAttribute.SIZE, data.getTextSize());
        if (data.isUnderlineText()) attributedSegment.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        if (data.isStrikeThroughText()) attributedSegment.addAttribute(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        return attributedSegment.getIterator();
    }

    private void mDrawText(final AttributedCharacterIterator iterator, final float x, final float y) {
        final TextLayout textLayout = new TextLayout(iterator, graphics2D.getFontRenderContext());
        if (getStyle() == Style.STROKE) graphics2D.draw(textLayout.getOutline(AffineTransform.getTranslateInstance(x, y)));
        else textLayout.draw(graphics2D, x, y);
    }

    @Override
    public void drawText(final char[] text, final int offset, final int length, final float x, final float y) {
        checkArgNotNull(text, "text");
        checkDisposed("Can't call drawText() on a disposed A3Graphics");
        mDrawText(mGetAttributedCharacterIterator(text, offset, length), x, y);
    }

    @Override
    public A3Font.Metrics getTextLayout(final CharSequence text) {
        checkArgNotNull(text, "text");
        checkDisposed("Can't call getTextLayout() on a disposed A3Graphics");
        final Runnable[] cleanup = new Runnable[1];
        final AttributedCharacterIterator iterator = mGetAttributedCharacterIterator(text, cleanup);
        final Runnable cleanupRunnable = cleanup[0];
        try {
            final TextLayout textLayout = new TextLayout(iterator, graphics2D.getFontRenderContext());
            final Rectangle2D bounds = textLayout.getBounds();
            final double left = bounds.getX();
            final double top = bounds.getY();
            final double right = left + bounds.getWidth();
            final double bottom = top + bounds.getHeight();
            return new A3Font.DefaultMetrics(textLayout.getBaseline(), textLayout.getAscent(), textLayout.getDescent(),
                    textLayout.getLeading(), (float) left, (float) top, (float) right, (float) bottom);
        }
        finally {
            if (cleanupRunnable != null) cleanupRunnable.run();
        }
    }

    @Override
    public A3Font.Metrics getTextLayout(final char[] text, final int offset, final int length) {
        checkArgNotNull(text, "text");
        checkDisposed("Can't call getTextLayout() on a disposed A3Graphics");
        final AttributedCharacterIterator iterator = mGetAttributedCharacterIterator(text, offset, length);
        final TextLayout textLayout = new TextLayout(iterator, graphics2D.getFontRenderContext());
        final Rectangle2D bounds = textLayout.getBounds();
        final double left = bounds.getX();
        final double top = bounds.getY();
        final double right = left + bounds.getWidth();
        final double bottom = top + bounds.getHeight();
        return new A3Font.DefaultMetrics(textLayout.getBaseline(), textLayout.getAscent(), textLayout.getDescent(),
                textLayout.getLeading(), (float) left, (float) top, (float) right, (float) bottom);
    }

    @Override
    public A3Path getClip() {
        checkDisposed("Can't call getClip() on a disposed A3Graphics");
        return data.getClip();
    }

    @Override
    public void setClip(final A3Path clip) {
        checkDisposed("Can't call setClip() on a disposed A3Graphics");
        data.setClip(clip);
        graphics2D.setClip(clip == null ? null : ((AWTA3Path)clip).path2D);
    }

    @Override
    public void setClip(float left, float top, float right, float bottom) {
        checkDisposed("Can't call setClip() on a disposed A3Graphics");
        Path2D path2D = new Path2D.Float();
        path2D.append(new Rectangle2D.Float(left, top, right - left, bottom - top), false);
        AWTA3Path clip = new AWTA3Path(path2D);
        data.setClip(clip);
        graphics2D.setClip(path2D);
    }

    @Override
    public int getColor() {
        checkDisposed("Can't call getColor() on a disposed A3Graphics");
        return graphics2D.getColor().getRGB();
    }

    @Override
    public void setColor(final int color) {
        checkDisposed("Can't call setColor() on a disposed A3Graphics");
        data.setColor(color);
        graphics2D.setColor(new Color(color, true));
    }

    @Override
    public int getStyle() {
        checkDisposed("Can't call getStyle() on a disposed A3Graphics");
        return data.getStyle();
    }

    @Override
    public void setStyle(final int style) {
        checkDisposed("Can't call setStyle() on a disposed A3Graphics");
        data.setStyle(style);
    }

    @Override
    public float getStrokeWidth() {
        checkDisposed("Can't call getStrokeWidth() on a disposed A3Graphics");
        return data.getStrokeWidth();
    }

    @Override
    public void setStrokeWidth(final float width) {
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
    public void setStrokeJoin(final int join) {
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
    public void setStrokeCap(final int cap) {
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
    public void setStrokeMiter(final float miter) {
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
    public void setFont(final A3Font font) {
        checkDisposed("Can't call setFont() on a disposed A3Graphics");
        if (font == null) {
            data.setFont(null);
        }
        else {
            AWTA3Font font0 = new AWTA3Font(((AWTA3Font)font).getFont().deriveFont(data.getTextSize()));
            data.setFont(font0);
            graphics2D.setFont(font0.getFont());
        }
    }

    @Override
    public float getTextSize() {
        checkDisposed("Can't call getTextSize() on a disposed A3Graphics");
        return data.getTextSize();
    }

    @Override
    public void setTextSize(final float size) {
        checkDisposed("Can't call setTextSize() on a disposed A3Graphics");
        if (data.getTextSize() == size) return;
        data.setTextSize(size);
        A3Font a3Font = data.getFont();
        Font font = a3Font == null ? (graphics2D.getFont() == null ? null : graphics2D.getFont()) : ((AWTA3Font)a3Font).getFont();
        if (font != null) {
            font = font.deriveFont(size);
            data.setFont(new AWTA3Font(font));
            graphics2D.setFont(font);
        }
    }

    @Override
    public boolean isAntiAlias() {
        checkDisposed("Can't call isAntiAlias() on a disposed A3Graphics");
        return hints.get(RenderingHints.KEY_ANTIALIASING).equals(RenderingHints.VALUE_ANTIALIAS_ON) &&
                !hints.get(RenderingHints.KEY_TEXT_ANTIALIASING).equals(RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
    }

    @Override
    public void setAntiAlias(final boolean antiAlias) {
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
    public void setFilterImage(final boolean filterImage) {
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
    public void setSubpixelText(final boolean subpixelText) {
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
    public void setUnderlineText(final boolean underlineText) {
        checkDisposed("Can't call setUnderlineText() on a disposed A3Graphics");
        data.setUnderlineText(underlineText);
    }

    @Override
    public boolean isStrikeThroughText() {
        checkDisposed("Can't call isStrikeThroughText() on a disposed A3Graphics");
        return data.isStrikeThroughText();
    }

    @Override
    public void setStrikeThroughText(final boolean strikeThroughText) {
        checkDisposed("Can't call setStrikeThroughText() on a disposed A3Graphics");
        data.setStrikeThroughText(strikeThroughText);
    }

    @Override
    public boolean isDither() {
        checkDisposed("Can't call isDither() on a disposed A3Graphics");
        return hints.get(RenderingHints.KEY_DITHERING).equals(RenderingHints.VALUE_DITHER_ENABLE);
    }

    @Override
    public void setDither(final boolean dither) {
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
        if (!data.equals(cacheData)) cacheData = data.copy();
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
        graphics2D.setColor(new Color(data.getColor(), true));
        graphics2D.setStroke(new BasicStroke(data.getStrokeWidth(), strokeCap2BasicStrokeCap(data.getStrokeCap()),
                strokeJoin2BasicStrokeJoin(data.getStrokeJoin()), data.getStrokeMiter()));
        A3Font a3Font = data.getFont();
        Font font = a3Font == null ? (graphics2D.getFont() == null ? null : graphics2D.getFont()) : ((AWTA3Font)a3Font).getFont();
        if (font != null) {
            font = font.deriveFont(data.getTextSize());
            data.setFont(new AWTA3Font(font));
            graphics2D.setFont(font);
        }
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
    public void setData(final Data data) {
        checkDisposed("Can't call setData() on a disposed A3Graphics");
        this.data = data;
        apply();
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public void dispose() {
        if (isDisposed()) return;
        disposed = true;
        data = null;
        cacheData = null;
        graphics2D.dispose();
        graphics2D = null;
    }

}
