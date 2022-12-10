package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.util.A3Copyable;
import com.ansdoship.a3wt.util.A3Disposable;

import static com.ansdoship.a3wt.util.A3Colors.BLACK;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public interface A3Graphics extends A3Disposable {

    interface Data extends A3Copyable<Data> {
        A3Rect getClipBounds();

        A3Rect getClipRect();
        A3Path getClipPath();
        void setClipRect(final A3Rect rect);
        void setClipPath(final A3Path path);

        A3Transform getTransform();
        void setTransform(final A3Transform transform);

        int getColor();
        void setColor(final int color);

        int getStyle();
        void setStyle(final int style);

        float getStrokeWidth();
        void setStrokeWidth(final float width);

        int getStrokeJoin();
        void setStrokeJoin(final int join);

        int getStrokeCap();
        void setStrokeCap(final int cap);

        float getStrokeMiter();
        void setStrokeMiter(final float miter);

        void setFont(final A3Font font);
        A3Font getFont();
        void setTextSize(final float size);
        float getTextSize();

        void setAntiAlias(final boolean antiAlias);
        boolean isAntiAlias();
        void setFilterImage(final boolean filterImage);
        boolean isFilterImage();
        void setSubpixelText(final boolean subpixelText);
        boolean isSubpixelText();
        void setUnderlineText(final boolean underlineText);
        boolean isUnderlineText();
        void setStrikeThroughText(final boolean strikeThroughText);
        boolean isStrikeThroughText();
        void setDither(final boolean dither);
        boolean isDither();

        void reset();
    }

    class DefaultData implements Data {

        protected A3Path clipPath;
        protected A3Rect clipRect;
        protected A3Transform transform;
        protected int color;
        protected int style;
        protected float strokeWidth;
        protected int strokeJoin;
        protected int strokeCap;
        protected float strokeMiter;
        protected A3Font font;
        protected float textSize;
        protected boolean antiAlias;
        protected boolean filterImage;
        protected boolean subpixelText;
        protected boolean underlineText;
        protected boolean strikeThroughText;
        protected boolean dither;

        public DefaultData() {
            reset();
        }

        @Override
        public A3Rect getClipBounds() {
            if (clipRect != null) return clipRect.getBounds();
            else if (clipPath != null) return clipPath.getBounds();
            else return null;
        }

        @Override
        public A3Path getClipPath() {
            return clipPath;
        }

        @Override
        public void setClipPath(final A3Path clipPath) {
            this.clipPath = clipPath;
            this.clipRect = null;
        }

        @Override
        public A3Rect getClipRect() {
            return clipRect;
        }

        @Override
        public void setClipRect(final A3Rect clipRect) {
            this.clipRect = clipRect;
            this.clipPath = null;
        }

        @Override
        public A3Transform getTransform() {
            return transform;
        }

        @Override
        public void setTransform(final A3Transform transform) {
            this.transform = transform;
        }

        @Override
        public int getColor() {
            return color;
        }

        @Override
        public void setColor(final int color) {
            this.color = color;
        }

        @Override
        public int getStyle() {
            return style;
        }

        @Override
        public void setStyle(final int style) {
            this.style = style;
        }

        @Override
        public float getStrokeWidth() {
            return strokeWidth;
        }

        @Override
        public void setStrokeWidth(final float width) {
            this.strokeWidth = width;
        }

        @Override
        public int getStrokeJoin() {
            return strokeJoin;
        }

        @Override
        public void setStrokeJoin(final int join) {
            this.strokeJoin = join;
        }

        @Override
        public int getStrokeCap() {
            return strokeCap;
        }

        @Override
        public void setStrokeCap(final int cap) {
            this.strokeCap = cap;
        }

        @Override
        public float getStrokeMiter() {
            return strokeMiter;
        }

        @Override
        public void setStrokeMiter(final float miter) {
            this.strokeMiter = miter;
        }

        @Override
        public A3Font getFont() {
            return font;
        }

        @Override
        public void setFont(final A3Font font) {
            this.font = font;
        }

        @Override
        public float getTextSize() {
            return textSize;
        }

        @Override
        public void setTextSize(final float size) {
            this.textSize = size;
        }

        @Override
        public boolean isAntiAlias() {
            return antiAlias;
        }

        @Override
        public void setAntiAlias(final boolean antiAlias) {
            this.antiAlias = antiAlias;
        }

        @Override
        public boolean isFilterImage() {
            return filterImage;
        }

        @Override
        public void setFilterImage(final boolean filterImage) {
            this.filterImage = filterImage;
        }

        @Override
        public boolean isSubpixelText() {
            return subpixelText;
        }

        @Override
        public void setSubpixelText(final boolean subpixelText) {
            this.subpixelText = subpixelText;
        }

        @Override
        public boolean isUnderlineText() {
            return underlineText;
        }

        @Override
        public void setUnderlineText(final boolean underlineText) {
            this.underlineText = underlineText;
        }

        @Override
        public boolean isStrikeThroughText() {
            return strikeThroughText;
        }

        @Override
        public void setStrikeThroughText(final boolean strikeThroughText) {
            this.strikeThroughText = strikeThroughText;
        }

        @Override
        public boolean isDither() {
            return dither;
        }

        @Override
        public void setDither(final boolean dither) {
            this.dither = dither;
        }

        @Override
        public void reset() {
            setClipRect(null);
            setClipPath(null);
            setTransform(null);
            setColor(BLACK);
            setStyle(Style.STROKE);
            setStrokeWidth(1.0f);
            setStrokeJoin(Join.MITER);
            setStrokeCap(Cap.BUTT);
            setStrokeMiter(10.0f);
            setFont(null);
            setTextSize(16.0f);
            setAntiAlias(true);
            setFilterImage(true);
            setSubpixelText(true);
            setUnderlineText(false);
            setStrikeThroughText(false);
            setDither(true);
        }

        @Override
        public Data copy() {
            final DefaultData copy = new DefaultData();
            to(copy);
            return copy;
        }

        @Override
        public void to(final Data dst) {
            checkArgNotNull(dst, "dst");
            dst.setClipPath(clipPath == null ? null : clipPath.copy());
            dst.setClipRect(clipRect == null ? null : clipRect.copy());
            dst.setTransform(transform == null ? null : transform.copy());
            dst.setColor(color);
            dst.setStyle(style);
            dst.setStrokeWidth(strokeWidth);
            dst.setStrokeJoin(strokeJoin);
            dst.setStrokeCap(strokeCap);
            dst.setStrokeMiter(strokeMiter);
            dst.setFont(font);
            dst.setTextSize(textSize);
            dst.setAntiAlias(antiAlias);
            dst.setFilterImage(filterImage);
            dst.setSubpixelText(subpixelText);
            dst.setUnderlineText(underlineText);
            dst.setStrikeThroughText(strikeThroughText);
            dst.setDither(dither);
        }

        @Override
        public void from(final Data src) {
            checkArgNotNull(src, "src");
            src.to(this);
        }

    }

    class Style {
        private Style(){}
        public static final int STROKE = 0;
        public static final int FILL = 1;
    }

    class Join {
        private Join(){}
        public static final int MITER = 0;
        public static final int ROUND = 1;
        public static final int BEVEL = 2;
    }

    class Cap {
        private Cap(){}
        public static final int BUTT = 0;
        public static final int ROUND = 1;
        public static final int SQUARE = 2;
    }

    int getWidth();
    int getHeight();

    void drawColor();
    void drawPath(final A3Path path);
    void drawImage(final A3Image image, final float x, final float y);
    void drawImage(final A3Image image, final A3Point point);
    void drawPoint(final float x, final float y);
    void drawPoint(final A3Point point);
    void drawArc(final float x, final float y, final float width, final float height, final float startAngle, final float sweepAngle, final boolean useCenter);
    void drawArc(final A3Arc arc);
    void drawLine(final float startX, final float startY, final float endX, final float endY);
    void drawLine(final A3Line line);
    void drawQuadCurve(final float startX, final float startY, final float endX, final float endY, final float ctrlX, final float ctrlY);
    void drawQuadCurve(final A3QuadCurve quadCurve);
    void drawCubicCurve(final float startX, final float startY, final float endX, final float endY,
                        final float ctrlX1, final float ctrlY1, final float ctrlX2, final float ctrlY2);
    void drawCubicCurve(final A3CubicCurve cubicCurve);
    void drawOval(final float x, final float y, final float width, final float height);
    void drawOval(final A3Oval oval);
    void drawRect(final float x, final float y, final float width, final float height);
    void drawRect(final A3Rect rect);
    void drawRoundRect(final float x, final float y, final float width, final float height, final float rx, final float ry);
    void drawRoundRect(final A3RoundRect roundRect);
    void drawText(final CharSequence text, final float x, final float y);
    void drawText(final char[] text, final int offset, final int length, final float x, final float y);

    default float measureText(final CharSequence text) {
        return measureText(text, 0, text.length());
    }
    float measureText(final CharSequence text, final int start, final int end);
    float measureText(final char[] text, final int offset, final int length);
    A3Font.Metrics getFontMetrics();
    A3Rect getTextBounds(final CharSequence text);
    A3Rect getTextBounds(final char[] text, final int offset, final int length);
    void getTextBounds(final CharSequence text, final A3Rect bounds);
    void getTextBounds(final char[] text, final int offset, final int length, final A3Rect bounds);

    A3Rect getClipBounds();
    void clipRect(final float x, final float y, final float width, final float height);
    void clipRect(final A3Rect rect);

    A3Transform getTransform();
    void setTransform(final A3Transform transform);

    int getColor();
    void setColor(final int color);

    int getStyle();
    void setStyle(final int style);

    float getStrokeWidth();
    void setStrokeWidth(final float strokeWidth);

    int getStrokeJoin();
    void setStrokeJoin(final int join);

    int getStrokeCap();
    void setStrokeCap(final int cap);

    float getStrokeMiter();
    void setStrokeMiter(final float miter);

    void setFont(final A3Font font);
    A3Font getFont();
    void setTextSize(final float size);
    float getTextSize();

    void setAntiAlias(final boolean antiAlias);
    boolean isAntiAlias();
    void setFilterImage(final boolean filterImage);
    boolean isFilterImage();
    void setSubpixelText(final boolean subpixelText);
    boolean isSubpixelText();
    void setUnderlineText(final boolean underlineText);
    boolean isUnderlineText();
    void setStrikeThroughText(final boolean strikeThroughText);
    boolean isStrikeThroughText();
    void setDither(final boolean dither);
    boolean isDither();

    void reset();

    void save();
    void restore();
    void apply();

    Data getData();
    void setData(final Data data);

}
