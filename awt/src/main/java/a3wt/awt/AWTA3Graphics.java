package a3wt.awt;

import a3wt.graphics.A3Path;
import a3wt.graphics.A3Rect;
import a3wt.graphics.A3Size;
import a3wt.graphics.A3Point;
import a3wt.graphics.A3Graphics;
import a3wt.graphics.A3Oval;
import a3wt.graphics.A3Arc;
import a3wt.graphics.A3Image;
import a3wt.graphics.A3Transform;
import a3wt.graphics.A3Font;
import a3wt.graphics.A3CubicCurve;
import a3wt.graphics.A3QuadCurve;
import a3wt.graphics.A3Line;
import a3wt.graphics.A3RoundRect;
import a3wt.util.A3CharSegment;

import java.awt.font.LineMetrics;
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
import java.awt.geom.QuadCurve2D;
import java.awt.geom.CubicCurve2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.HashMap;

import static a3wt.awt.A3AWTUtils.strokeCap2BasicStrokeCap;
import static a3wt.awt.A3AWTUtils.strokeJoin2BasicStrokeJoin;
import static a3wt.awt.A3AWTUtils.floatRectangle2D;
import static a3wt.awt.A3AWTUtils.floatPath2D;
import static a3wt.util.A3Preconditions.checkArgNotNull;
import static a3wt.util.A3Preconditions.checkArgNotEmpty;
import static a3wt.util.A3Preconditions.checkArgArrayLengthMin;

public class AWTA3Graphics implements A3Graphics {

    protected final AffineTransform mImageTransform = new AffineTransform();
    protected final AWTA3Transform mTransform = new AWTA3Transform(new AffineTransform());
    protected final Line2D.Float mPointLine = new Line2D.Float();
    protected final Arc2D.Float mArc2D = new Arc2D.Float();
    protected final Line2D.Float mLine2D = new Line2D.Float();
    protected final QuadCurve2D.Float mQuadCurve2D = new QuadCurve2D.Float();
    protected final CubicCurve2D.Float mCubicCurve2D = new CubicCurve2D.Float();
    protected final Ellipse2D.Float mEllipse2D = new Ellipse2D.Float();
    protected final Rectangle2D.Float mRectangle2D = new Rectangle2D.Float();
    protected final RoundRectangle2D.Float mRoundRectangle2D = new RoundRectangle2D.Float();

    protected final AWTA3Rect mClipRect = new AWTA3Rect(new Rectangle2D.Float());

    protected volatile Graphics2D graphics2D;
    protected volatile boolean disposed = false;
    protected volatile int width;
    protected volatile int height;
    protected final Data data = new DefaultData();
    protected final Data cacheData = new DefaultData();
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

    public void setGraphics2D(final Graphics2D graphics2D, final int width, final int height) {
        checkDisposed("Can't call setGraphics2D() on a disposed AWTA3Graphics");
        this.graphics2D = graphics2D;
        this.width = width;
        this.height = height;
        apply();
    }

    @Override
    public void drawColor() {
        checkDisposed("Can't call drawColor() on a disposed A3Graphics");
        switch (data.getStyle()) {
            case Style.STROKE:
                graphics2D.drawRect(0, 0, width, height);
                break;
            case Style.FILL:
                graphics2D.fillRect(0, 0, width, height);
                break;
        }
    }

    public void drawShape(final Shape shape) {
        checkDisposed("Can't call drawShape() on a disposed AWTA3Graphics");
        mDrawShape(shape);
    }

    private void mDrawShape(final Shape shape) {
        switch (data.getStyle()) {
            case Style.STROKE:
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
    public void drawImage(final A3Image image, final float x, final float y) {
        checkArgNotNull(image, "image");
        checkDisposed("Can't call drawImage() on a disposed A3Graphics");
        mImageTransform.setToTranslation(x, y);
        graphics2D.drawImage(((AWTA3Image)image).getBufferedImage(), mImageTransform, null);
    }

    @Override
    public void drawImage(final A3Image image, final A3Point point) {
        checkArgNotNull(point, "point");
        drawImage(image, point.getX(), point.getY());
    }

    @Override
    public void drawImage(final A3Image image, final A3Transform transform) {
        checkArgNotNull(image, "image");
        checkArgNotNull(transform, "transform");
        checkDisposed("Can't call drawImage() on a disposed A3Graphics");
        graphics2D.drawImage(((AWTA3Image)image).getBufferedImage(), ((AWTA3Transform)transform).affineTransform, null);
    }

    @Override
    public void drawImage(final A3Image image, final float[] matrixValues) {
        checkArgNotNull(image, "image");
        checkArgArrayLengthMin(matrixValues, AWTA3Transform.MATRIX_VALUES_LENGTH, true);
        checkDisposed("Can't call drawImage() on a disposed A3Graphics");
        mImageTransform.setTransform(matrixValues[0], matrixValues[1], matrixValues[2], matrixValues[3], matrixValues[4], matrixValues[5]);
        graphics2D.drawImage(((AWTA3Image)image).getBufferedImage(), mImageTransform, null);
    }

    @Override
    public void drawImage(final A3Image image, final float sx, final float kx, final float dx, final float ky, final float sy, final float dy) {
        checkArgNotNull(image, "image");
        checkDisposed("Can't call drawImage() on a disposed A3Graphics");
        mImageTransform.setTransform(sx, kx, dx, ky, sy, dy);
        graphics2D.drawImage(((AWTA3Image)image).getBufferedImage(), mImageTransform, null);
    }

    @Override
    public void drawPoint(final float x, final float y) {
        checkDisposed("Can't call drawPoint() on a disposed A3Graphics");
        mPointLine.setLine(x, y, x, y);
        mDrawShape(mPointLine);
    }

    @Override
    public void drawPoint(final A3Point point) {
        checkArgNotNull(point, "point");
        checkDisposed("Can't call drawPoint() on a disposed A3Graphics");
        mPointLine.setLine(((AWTA3Point)point).point2D, ((AWTA3Point)point).point2D);
        mDrawShape(mPointLine);
    }

    @Override
    public void drawArc(final float x, final float y, final float width, final float height,
                        final float startAngle, final float sweepAngle, final boolean useCenter) {
        checkDisposed("Can't call drawArc() on a disposed A3Graphics");
        mArc2D.setArc(x, y, width, height, startAngle, sweepAngle, useCenter ? Arc2D.PIE : Arc2D.OPEN);
        mDrawShape(mArc2D);
    }

    @Override
    public void drawArc(final A3Point pos, final A3Size size, final float startAngle, final float sweepAngle, final boolean useCenter) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        checkDisposed("Can't call drawArc() on a disposed A3Graphics");
        mArc2D.setArc(((AWTA3Point)pos).point2D, ((AWTA3Size)size).dimension2D, startAngle, sweepAngle, useCenter ? Arc2D.PIE : Arc2D.OPEN);
        mDrawShape(mArc2D);
    }

    @Override
    public void drawArc(final A3Rect rect, final float startAngle, final float sweepAngle, final boolean useCenter) {
        checkArgNotNull(rect, "rect");
        checkDisposed("Can't call drawArc() on a disposed A3Graphics");
        mArc2D.setArc(((AWTA3Rect)rect).rectangle2D, startAngle, sweepAngle, useCenter ? Arc2D.PIE : Arc2D.OPEN);
        mDrawShape(mArc2D);
    }

    @Override
    public void drawArc(final A3Arc arc) {
        checkArgNotNull(arc, "arc");
        checkDisposed("Can't call drawArc() on a disposed A3Graphics");
        mDrawShape(((AWTA3Arc)arc).arc2D);
    }

    @Override
    public void drawLine(final float startX, final float startY, final float endX, final float endY) {
        checkDisposed("Can't call drawLine() on a disposed A3Graphics");
        mLine2D.setLine(startX, startY, endX, endY);
        mDrawShape(mLine2D);
    }

    @Override
    public void drawLine(final A3Point startPos, final A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(endPos, "endPos");
        checkDisposed("Can't call drawLine() on a disposed A3Graphics");
        mLine2D.setLine(((AWTA3Point)startPos).point2D, ((AWTA3Point)endPos).point2D);
        mDrawShape(mLine2D);
    }

    @Override
    public void drawLine(final A3Line line) {
        checkArgNotNull(line, "line");
        checkDisposed("Can't call drawLine() on a disposed A3Graphics");
        mDrawShape(((AWTA3Line)line).line2D);
    }

    @Override
    public void drawQuadCurve(final float startX, final float startY, final float ctrlX, final float ctrlY, final float endX, final float endY) {
        checkDisposed("Can't call drawQuadCurve() on a disposed A3Graphics");
        mQuadCurve2D.setCurve(startX, startY, ctrlX, ctrlY, endX, endY);
        mDrawShape(mQuadCurve2D);
    }

    @Override
    public void drawQuadCurve(final A3Point startPos, final A3Point ctrlPos, final A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(ctrlPos, "ctrlPos");
        checkArgNotNull(endPos, "endPos");
        checkDisposed("Can't call drawQuadCurve() on a disposed A3Graphics");
        mQuadCurve2D.setCurve(((AWTA3Point)startPos).point2D, ((AWTA3Point)ctrlPos).point2D, ((AWTA3Point)endPos).point2D);
        mDrawShape(mQuadCurve2D);
    }

    @Override
    public void drawQuadCurve(final A3QuadCurve quadCurve) {
        checkArgNotNull(quadCurve, "quadCurve");
        checkDisposed("Can't call drawQuadCurve() on a disposed A3Graphics");
        mDrawShape(((AWTA3QuadCurve)quadCurve).quadCurve2D);
    }

    @Override
    public void drawCubicCurve(final float startX, final float startY,
                               final float ctrlX1, final float ctrlY1,
                               final float ctrlX2, final float ctrlY2,
                               final float endX, final float endY) {
        checkDisposed("Can't call drawCubicCurve() on a disposed A3Graphics");
        mCubicCurve2D.setCurve(startX, startY, ctrlX1, ctrlY1, ctrlX2, ctrlY2, endX, endY);
        mDrawShape(mQuadCurve2D);
    }

    @Override
    public void drawCubicCurve(final A3Point startPos, final A3Point ctrlPos1, final A3Point ctrlPos2, final A3Point endPos) {
        checkArgNotNull(startPos, "startPos");
        checkArgNotNull(ctrlPos1, "ctrlPos1");
        checkArgNotNull(ctrlPos2, "ctrlPos2");
        checkArgNotNull(endPos, "endPos");
        checkDisposed("Can't call drawCubicCurve() on a disposed A3Graphics");
        mCubicCurve2D.setCurve(((AWTA3Point)startPos).point2D, ((AWTA3Point)ctrlPos1).point2D, ((AWTA3Point)ctrlPos2).point2D, ((AWTA3Point)endPos).point2D);
        mDrawShape(mCubicCurve2D);
    }

    @Override
    public void drawCubicCurve(final A3CubicCurve cubicCurve) {
        checkArgNotNull(cubicCurve, "cubicCurve");
        checkDisposed("Can't call drawCubicCurve() on a disposed A3Graphics");
        mDrawShape(((AWTA3CubicCurve)cubicCurve).cubicCurve2D);
    }

    @Override
    public void drawOval(final float x, final float y, final float width, final float height) {
        checkDisposed("Can't call drawOval() on a disposed A3Graphics");
        mEllipse2D.setFrame(x, y, width, height);
        mDrawShape(mEllipse2D);
    }

    @Override
    public void drawOval(final A3Point pos, final A3Size size) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        checkDisposed("Can't call drawOval() on a disposed A3Graphics");
        mEllipse2D.setFrame(((AWTA3Point)pos).point2D, ((AWTA3Size)size).dimension2D);
        mDrawShape(mEllipse2D);
    }

    @Override
    public void drawOval(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        checkDisposed("Can't call drawOval() on a disposed A3Graphics");
        mEllipse2D.setFrame(((AWTA3Rect)rect).rectangle2D);
        mDrawShape(mEllipse2D);
    }

    @Override
    public void drawOval(final A3Oval oval) {
        checkArgNotNull(oval, "oval");
        checkDisposed("Can't call drawOval() on a disposed A3Graphics");
        mDrawShape(((AWTA3Oval)oval).ellipse2D);
    }

    @Override
    public void drawRect(final float x, final float y, final float width, final float height) {
        checkDisposed("Can't call drawRect() on a disposed A3Graphics");
        mRectangle2D.setRect(x, y, width, height);
        mDrawShape(mRectangle2D);
    }

    @Override
    public void drawRect(final A3Point pos, final A3Size size) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        checkDisposed("Can't call drawRect() on a disposed A3Graphics");
        mRectangle2D.setRect(pos.getX(), pos.getY(), size.getWidth(), size.getHeight());
        mDrawShape(mRectangle2D);
    }

    @Override
    public void drawRect(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        checkDisposed("Can't call drawRect() on a disposed A3Graphics");
        mDrawShape(((AWTA3Rect)rect).rectangle2D);
    }

    @Override
    public void drawRoundRect(final float x, final float y, final float width, final float height, final float rx, final float ry) {
        checkDisposed("Can't call drawRoundRect() on a disposed A3Graphics");
        mRoundRectangle2D.setRoundRect(x, y, width, height, rx, ry);
        mDrawShape(mRoundRectangle2D);
    }

    @Override
    public void drawRoundRect(final A3Point pos, final A3Size size, final A3Size corner) {
        checkArgNotNull(pos, "pos");
        checkArgNotNull(size, "size");
        checkArgNotNull(corner, "corner");
        checkDisposed("Can't call drawRoundRect() on a disposed A3Graphics");
        mRoundRectangle2D.setRoundRect(pos.getX(), pos.getY(), size.getWidth(), size.getHeight(), corner.getWidth(), corner.getHeight());
        mDrawShape(mRoundRectangle2D);
    }

    @Override
    public void drawRoundRect(final A3Rect rect, final A3Size corner) {
        checkArgNotNull(rect, "size");
        checkArgNotNull(corner, "corner");
        checkDisposed("Can't call drawRoundRect() on a disposed A3Graphics");
        mRoundRectangle2D.setRoundRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), corner.getWidth(), corner.getHeight());
        mDrawShape(mRoundRectangle2D);
    }

    @Override
    public void drawRoundRect(final A3RoundRect roundRect) {
        checkArgNotNull(roundRect, "roundRect");
        checkDisposed("Can't call drawRoundRect() on a disposed A3Graphics");
        mDrawShape(((AWTA3RoundRect)roundRect).roundRectangle2D);
    }

    @Override
    public void drawText(final CharSequence text, final int start, final int end, final float x, final float y) {
        checkArgNotEmpty(text, "text");
        checkDisposed("Can't call drawText() on a disposed A3Graphics");
        final Runnable[] cleanup = new Runnable[1];
        final AttributedCharacterIterator iterator = mGetAttributedCharacterIterator(text, start, end, cleanup);
        mDrawText(iterator, x, y);
        if (cleanup[0] != null) cleanup[0].run();
    }

    private AttributedCharacterIterator mGetAttributedCharacterIterator(final String text, final int start, final int end) {
        checkArgNotNull(text, "text");
        final Font font = getFont() == null ? null : ((AWTA3Font)getFont()).getFont();
        final HashMap<AttributedCharacterIterator.Attribute, Object> attributes = new HashMap<>();
        if (font != null) attributes.put(TextAttribute.FONT, font);
        attributes.put(TextAttribute.SIZE, data.getTextSize());
        if (data.isUnderlineText()) attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        if (data.isStrikeThroughText()) attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        final AttributedString attributedString = new AttributedString(text);
        attributedString.addAttributes(attributes, start, end);
        return attributedString.getIterator();
    }
    
    private AttributedCharacterIterator mGetAttributedCharacterIterator(final A3CharSegment text, final int start, final int end) {
        checkArgNotNull(text, "text");
        final Font font = getFont() == null ? null : ((AWTA3Font)getFont()).getFont();
        final HashMap<AttributedCharacterIterator.Attribute, Object> attributes = new HashMap<>();
        if (font != null) attributes.put(TextAttribute.FONT, font);
        attributes.put(TextAttribute.SIZE, data.getTextSize());
        if (data.isUnderlineText()) attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        if (data.isStrikeThroughText()) attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
        final AttributedA3CharSegment attributedSegment = new AttributedA3CharSegment(text);
        attributedSegment.addAttributes(attributes, start, end);
        return attributedSegment.getIterator();
    }

    private AttributedCharacterIterator mGetAttributedCharacterIterator(final CharSequence text, final int start, final int end, final Runnable[] cleanup) {
        if (text instanceof String) return mGetAttributedCharacterIterator((String) text, start, end);
        else if (text instanceof A3CharSegment) return mGetAttributedCharacterIterator((A3CharSegment) text, start, end);
        else {
            final Font font = getFont() == null ? null : ((AWTA3Font)getFont()).getFont();
            final HashMap<AttributedCharacterIterator.Attribute, Object> attributes = new HashMap<>();
            if (font != null) attributes.put(TextAttribute.FONT, font);
            attributes.put(TextAttribute.SIZE, data.getTextSize());
            if (data.isUnderlineText()) attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            if (data.isStrikeThroughText()) attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
            final AttributedCharSequence attributedCharSequence = new AttributedCharSequence(text);
            attributedCharSequence.addAttributes(attributes, start, end);
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
        checkArgNotEmpty(text, "text");
        checkDisposed("Can't call drawText() on a disposed A3Graphics");
        mDrawText(mGetAttributedCharacterIterator(text, offset, length), x, y);
    }

    @Override
    public float measureText(final CharSequence text, final int start, final int end) {
        checkArgNotEmpty(text);
        checkDisposed("Can't call measureText() on a disposed A3Graphics");
        return (float) graphics2D.getFont().getStringBounds(new CharSequenceCharacterIterator(text),
                start, end, graphics2D.getFontRenderContext()).getWidth();
    }

    @Override
    public float measureText(final char[] text, final int offset, final int length) {
        checkArgNotEmpty(text);
        checkDisposed("Can't call measureText() on a disposed A3Graphics");
        return (float) graphics2D.getFont().getStringBounds(text, offset, length, graphics2D.getFontRenderContext()).getWidth();
    }

    @Override
    public A3Font.Metrics getFontMetrics() {
        checkDisposed("Can't call getFontMetrics() on a disposed A3Graphics");
        final LineMetrics lineMetrics = graphics2D.getFontMetrics().getLineMetrics("", graphics2D);
        return new A3Font.DefaultMetrics(0, lineMetrics.getAscent(), lineMetrics.getDescent(), lineMetrics.getLeading(),
                lineMetrics.getAscent(), lineMetrics.getDescent());
    }

    @Override
    public void getFontMetrics(final A3Font.Metrics metrics) {
        checkDisposed("Can't call getFontMetrics() on a disposed A3Graphics");
        checkArgNotNull(metrics, "metrics");
        final LineMetrics lineMetrics = graphics2D.getFontMetrics().getLineMetrics("", graphics2D);
        metrics.set(0, lineMetrics.getAscent(), lineMetrics.getDescent(), lineMetrics.getLeading(),
                lineMetrics.getAscent(), lineMetrics.getDescent());
    }

    @Override
    public A3Rect getTextBounds(final CharSequence text, final int start, final int end) {
        checkArgNotEmpty(text);
        checkDisposed("Can't call getTextBounds() on a disposed A3Graphics");
        final Rectangle2D stringBounds;
        if (text instanceof String) {
            stringBounds = graphics2D.getFont().getStringBounds((String) text, start, end, graphics2D.getFontRenderContext());
        }
        else if (text instanceof A3CharSegment) {
            final int arrayOffset = ((A3CharSegment) text).arrayOffset();
            stringBounds = graphics2D.getFont().getStringBounds(((A3CharSegment) text).array(), arrayOffset + start, arrayOffset + end,
                    graphics2D.getFontRenderContext());
        }
        else {
            stringBounds = graphics2D.getFont().getStringBounds(new CharSequenceCharacterIterator(text), start, end,
                    graphics2D.getFontRenderContext());
        }
        return new AWTA3Rect(floatRectangle2D(stringBounds));
    }

    @Override
    public void getTextBounds(final CharSequence text, final int start, final int end, final A3Rect bounds) {
        checkArgNotNull(bounds, "bounds");
        checkDisposed("Can't call getTextBounds() on a disposed A3Graphics");
        getTextBounds(text, start, end).to(bounds);
    }

    @Override
    public A3Rect getTextBounds(final char[] text, final int offset, final int length) {
        checkArgNotEmpty(text);
        checkDisposed("Can't call getTextBounds() on a disposed A3Graphics");
        return new AWTA3Rect(floatRectangle2D(graphics2D.getFont().getStringBounds(text, offset, offset + length, graphics2D.getFontRenderContext())));
    }

    @Override
    public void getTextBounds(final char[] text, final int offset, final int length, final A3Rect bounds) {
        checkArgNotNull(bounds, "bounds");
        checkDisposed("Can't call getTextBounds() on a disposed A3Graphics");
        getTextBounds(text, offset, length).to(bounds);
    }

    @Override
    public A3Rect getClipBounds() {
        checkDisposed("Can't call getClipBounds() on a disposed A3Graphics");
        final Rectangle2D clipBounds = graphics2D.getClipBounds();
        if (clipBounds == null) return null;
        else return new AWTA3Rect(floatRectangle2D(clipBounds));
    }

    @Override
    public void getClipBounds(final A3Rect bounds) {
        checkArgNotNull(bounds, "bounds");
        final A3Rect clipBounds = getClipBounds();
        if (clipBounds != null) clipBounds.to(bounds);
    }

    @Override
    public A3Path getClipPath() {
        checkDisposed("Can't call getClipPath() on a disposed A3Graphics");
        final Shape clip = graphics2D.getClip();
        if (clip instanceof Path2D) return new AWTA3Path(floatPath2D((Path2D) clip));
        else return null;
    }

    @Override
    public void getClipPath(final A3Path path) {
        checkArgNotNull(path, "path");
        final A3Path clipPath = getClipPath();
        if (clipPath != null) clipPath.to(path);
    }

    @Override
    public A3Graphics setClipPath(final A3Path clip) {
        checkDisposed("Can't call setClipPath() on a disposed A3Graphics");
        data.setClipPath(clip);
        graphics2D.setClip(clip == null ? null : ((AWTA3Path)clip).path2D);
        return this;
    }

    @Override
    public A3Rect getClipRect() {
        checkDisposed("Can't call getClipRect() on a disposed A3Graphics");
        final Shape clip = graphics2D.getClip();
        if (clip instanceof Rectangle2D) return new AWTA3Rect(floatRectangle2D((Rectangle2D) clip));
        else return null;
    }

    @Override
    public void getClipRect(final A3Rect rect) {
        checkArgNotNull(rect, "rect");
        final A3Rect clipRect = getClipRect();
        if (clipRect != null) clipRect.to(rect);
    }

    @Override
    public A3Graphics setClipRect(final float x, final float y, final float width, final float height) {
        checkDisposed("Can't call setClipRect() on a disposed A3Graphics");
        mClipRect.set(x, y, width, height);
        data.setClipRect(mClipRect);
        graphics2D.setClip(mClipRect.rectangle2D);
        return this;
    }

    @Override
    public A3Graphics setClipRect(final A3Point pos, final A3Size size) {
        checkDisposed("Can't call setClipRect() on a disposed A3Graphics");
        mClipRect.set(pos, size);
        data.setClipRect(mClipRect);
        graphics2D.setClip(mClipRect.rectangle2D);
        return this;
    }

    @Override
    public A3Graphics setClipRect(final A3Rect rect) {
        checkDisposed("Can't call setClipRect() on a disposed A3Graphics");
        checkArgNotNull(rect, "rect");
        data.setClipRect(rect);
        graphics2D.setClip(((AWTA3Rect)rect).rectangle2D);
        return this;
    }

    @Override
    public A3Transform getTransform() {
        checkDisposed("Can't call getTransform() on a disposed A3Graphics");
        return new AWTA3Transform(graphics2D.getTransform());
    }

    @Override
    public void getTransform(final A3Transform transform) {
        checkArgNotNull(transform, "transform");
        checkDisposed("Can't call getTransform() on a disposed A3Graphics");
        getTransform().to(transform);
    }

    @Override
    public A3Graphics setTransform(final A3Transform transform) {
        checkDisposed("Can't call setTransform() on a disposed A3Graphics");
        if (transform == null) graphics2D.setTransform(AWTA3Transform.DEFAULT_TRANSFORM);
        else graphics2D.setTransform(((AWTA3Transform)transform).affineTransform);
        data.setTransform(transform);
        return this;
    }

    @Override
    public A3Graphics setTransform(final float[] matrixValues) {
        checkDisposed("Can't call setTransform() on a disposed A3Graphics");
        if (matrixValues == null) {
            graphics2D.setTransform(AWTA3Transform.DEFAULT_TRANSFORM);
            data.setTransform(null);
        }
        else {
            mTransform.setMatrixValues(matrixValues);
            graphics2D.setTransform(mTransform.affineTransform);
            data.setTransform(mTransform);
        }
        return this;
    }

    @Override
    public A3Graphics setTransform(final float sx, final float kx, final float dx, final float ky, final float sy, final float dy) {
        checkDisposed("Can't call setTransform() on a disposed A3Graphics");
        mTransform.set(sx, kx, dx, ky, sy, dy);
        graphics2D.setTransform(mTransform.affineTransform);
        data.setTransform(mTransform);
        return this;
    }

    @Override
    public A3Graphics setTransform(final A3Point scale, final A3Point skew, final A3Point translate) {
        checkDisposed("Can't call setTransform() on a disposed A3Graphics");
        mTransform.set(scale, skew, translate);
        graphics2D.setTransform(mTransform.affineTransform);
        data.setTransform(mTransform);
        return this;
    }

    @Override
    public int getColor() {
        checkDisposed("Can't call getColor() on a disposed A3Graphics");
        return graphics2D.getColor().getRGB();
    }

    @Override
    public A3Graphics setColor(final int color) {
        checkDisposed("Can't call setColor() on a disposed A3Graphics");
        data.setColor(color);
        graphics2D.setColor(new Color(color, true));
        return this;
    }

    @Override
    public int getStyle() {
        checkDisposed("Can't call getStyle() on a disposed A3Graphics");
        return data.getStyle();
    }

    @Override
    public A3Graphics setStyle(final int style) {
        checkDisposed("Can't call setStyle() on a disposed A3Graphics");
        data.setStyle(style);
        return this;
    }

    @Override
    public float getStrokeWidth() {
        checkDisposed("Can't call getStrokeWidth() on a disposed A3Graphics");
        return data.getStrokeWidth();
    }

    @Override
    public A3Graphics setStrokeWidth(final float width) {
        checkDisposed("Can't call setStrokeWidth() on a disposed A3Graphics");
        data.setStrokeWidth(width);
        graphics2D.setStroke(new BasicStroke(width, strokeCap2BasicStrokeCap(data.getStrokeCap()),
                strokeJoin2BasicStrokeJoin(data.getStrokeJoin()), data.getStrokeMiter()));
        return this;
    }

    @Override
    public int getStrokeJoin() {
        checkDisposed("Can't call getStrokeJoin() on a disposed A3Graphics");
        return data.getStrokeJoin();
    }

    @Override
    public A3Graphics setStrokeJoin(final int join) {
        checkDisposed("Can't call setStrokeJoin() on a disposed A3Graphics");
        data.setStrokeJoin(join);
        graphics2D.setStroke(new BasicStroke(data.getStrokeWidth(), strokeCap2BasicStrokeCap(data.getStrokeCap()),
                strokeJoin2BasicStrokeJoin(join), data.getStrokeMiter()));
        return this;
    }

    @Override
    public int getStrokeCap() {
        checkDisposed("Can't call getStrokeCap() on a disposed A3Graphics");
        return data.getStrokeCap();
    }

    @Override
    public A3Graphics setStrokeCap(final int cap) {
        checkDisposed("Can't call setStrokeCap() on a disposed A3Graphics");
        data.setStrokeCap(cap);
        graphics2D.setStroke(new BasicStroke(data.getStrokeWidth(), strokeCap2BasicStrokeCap(cap),
                strokeJoin2BasicStrokeJoin(data.getStrokeJoin()), data.getStrokeMiter()));
        return this;
    }

    @Override
    public float getStrokeMiter() {
        checkDisposed("Can't call getStrokeMiter() on a disposed A3Graphics");
        return data.getStrokeMiter();
    }

    @Override
    public A3Graphics setStrokeMiter(final float miter) {
        checkDisposed("Can't call setStrokeMiter() on a disposed A3Graphics");
        data.setStrokeMiter(miter);
        graphics2D.setStroke(new BasicStroke(data.getStrokeWidth(), strokeCap2BasicStrokeCap(data.getStrokeCap()),
                strokeJoin2BasicStrokeJoin(data.getStrokeJoin()), miter));
        return this;
    }

    @Override
    public A3Font getFont() {
        checkDisposed("Can't call getFont() on a disposed A3Graphics");
        return graphics2D.getFont() == null ? null : new AWTA3Font(graphics2D.getFont());
    }

    @Override
    public A3Graphics setFont(final A3Font font) {
        checkDisposed("Can't call setFont() on a disposed A3Graphics");
        if (font == null) {
            data.setFont(null);
        }
        else {
            AWTA3Font font0 = new AWTA3Font(((AWTA3Font)font).getFont().deriveFont(data.getTextSize()));
            data.setFont(font0);
            graphics2D.setFont(font0.getFont());
        }
        return this;
    }

    @Override
    public float getTextSize() {
        checkDisposed("Can't call getTextSize() on a disposed A3Graphics");
        return graphics2D.getFont().getSize2D();
    }

    @Override
    public A3Graphics setTextSize(final float size) {
        checkDisposed("Can't call setTextSize() on a disposed A3Graphics");
        if (data.getTextSize() == size) return this;
        data.setTextSize(size);
        A3Font a3Font = data.getFont();
        Font font = a3Font == null ? (graphics2D.getFont() == null ? null : graphics2D.getFont()) : ((AWTA3Font)a3Font).getFont();
        if (font != null) {
            font = font.deriveFont(size);
            data.setFont(new AWTA3Font(font));
            graphics2D.setFont(font);
        }
        return this;
    }

    @Override
    public boolean isAntiAlias() {
        checkDisposed("Can't call isAntiAlias() on a disposed A3Graphics");
        return hints.get(RenderingHints.KEY_ANTIALIASING).equals(RenderingHints.VALUE_ANTIALIAS_ON) &&
                !hints.get(RenderingHints.KEY_TEXT_ANTIALIASING).equals(RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
    }

    @Override
    public A3Graphics setAntiAlias(final boolean antiAlias) {
        checkDisposed("Can't call setAntiAlias() on a disposed A3Graphics");
        hints.put(RenderingHints.KEY_ANTIALIASING,
                antiAlias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        hints.put(RenderingHints.KEY_TEXT_ANTIALIASING,
                antiAlias ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        if (graphics2D != null) graphics2D.setRenderingHints(hints);
        return this;
    }

    @Override
    public boolean isFilterImage() {
        checkDisposed("Can't call isFilterImage() on a disposed A3Graphics");
        return !hints.get(RenderingHints.KEY_INTERPOLATION).equals(RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
    }

    @Override
    public A3Graphics setFilterImage(final boolean filterImage) {
        checkDisposed("Can't call setFilterImage() on a disposed A3Graphics");
        hints.put(RenderingHints.KEY_INTERPOLATION,
                filterImage ? RenderingHints.VALUE_INTERPOLATION_BILINEAR : RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        if (graphics2D != null) graphics2D.setRenderingHints(hints);
        return this;
    }

    @Override
    public boolean isSubpixelText() {
        checkDisposed("Can't call isSubpixelText() on a disposed A3Graphics");
        return hints.get(RenderingHints.KEY_FRACTIONALMETRICS).equals(RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    }

    @Override
    public A3Graphics setSubpixelText(final boolean subpixelText) {
        checkDisposed("Can't call setSubpixelText() on a disposed A3Graphics");
        hints.put(RenderingHints.KEY_FRACTIONALMETRICS,
                subpixelText ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        if (graphics2D != null) graphics2D.setRenderingHints(hints);
        return this;
    }

    @Override
    public boolean isUnderlineText() {
        checkDisposed("Can't call isUnderlineText() on a disposed A3Graphics");
        return data.isUnderlineText();
    }

    @Override
    public A3Graphics setUnderlineText(final boolean underlineText) {
        checkDisposed("Can't call setUnderlineText() on a disposed A3Graphics");
        data.setUnderlineText(underlineText);
        return this;
    }

    @Override
    public boolean isStrikeThroughText() {
        checkDisposed("Can't call isStrikeThroughText() on a disposed A3Graphics");
        return data.isStrikeThroughText();
    }

    @Override
    public A3Graphics setStrikeThroughText(final boolean strikeThroughText) {
        checkDisposed("Can't call setStrikeThroughText() on a disposed A3Graphics");
        data.setStrikeThroughText(strikeThroughText);
        return this;
    }

    @Override
    public boolean isDither() {
        checkDisposed("Can't call isDither() on a disposed A3Graphics");
        return hints.get(RenderingHints.KEY_DITHERING).equals(RenderingHints.VALUE_DITHER_ENABLE);
    }

    @Override
    public A3Graphics setDither(final boolean dither) {
        checkDisposed("Can't call setDither() on a disposed A3Graphics");
        hints.put(RenderingHints.KEY_DITHERING,
                dither ? RenderingHints.VALUE_DITHER_ENABLE : RenderingHints.VALUE_DITHER_DISABLE);
        if (graphics2D != null) graphics2D.setRenderingHints(hints);
        return this;
    }

    @Override
    public A3Graphics reset() {
        checkDisposed("Can't call reset() on a disposed A3Graphics");
        data.reset();
        apply();
        return this;
    }

    @Override
    public void save() {
        checkDisposed("Can't call save() on a disposed A3Graphics");
        data.to(cacheData);
    }

    @Override
    public void restore() {
        checkDisposed("Can't call restore() on a disposed A3Graphics");
        data.from(cacheData);
        apply();
    }

    @Override
    public void apply() {
        checkDisposed("Can't call apply() on a disposed A3Graphics");
        if (graphics2D == null) return;
        hints.clear();
        hints.putAll(DEFAULT_HINTS);
        final Shape clip = data.getClipPath() == null ?
                (data.getClipRect() == null ? null : ((AWTA3Rect)data.getClipRect()).rectangle2D) :
                ((AWTA3Path)data.getClipPath()).path2D;
        if (clip != null) graphics2D.setClip(clip);
        final A3Transform transform = data.getTransform();
        if (transform == null) graphics2D.getTransform().setToIdentity();
        else graphics2D.getTransform().setTransform(((AWTA3Transform)data.getTransform()).affineTransform);
        graphics2D.setColor(new Color(data.getColor(), true));
        graphics2D.setStroke(new BasicStroke(data.getStrokeWidth(), strokeCap2BasicStrokeCap(data.getStrokeCap()),
                strokeJoin2BasicStrokeJoin(data.getStrokeJoin()), data.getStrokeMiter()));
        final Font font = data.getFont() == null ? (graphics2D.getFont() == null ? null : graphics2D.getFont()) : ((AWTA3Font)data.getFont()).getFont();
        if (font != null) {
            graphics2D.setFont(font.deriveFont(data.getTextSize()));
            data.setFont(new AWTA3Font(graphics2D.getFont()));
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
        data.setClipRect(getClipRect());
        data.setClipPath(getClipPath());
        data.setTransform(getTransform());
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
    public A3Graphics setData(final Data data) {
        checkDisposed("Can't call setData() on a disposed A3Graphics");
        checkArgNotNull(data, "data");
        data.to(this.data);
        apply();
        return this;
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
        graphics2D = null;
    }

}
