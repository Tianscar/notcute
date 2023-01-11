package a3wt.graphics;

import a3wt.util.A3Copyable;
import a3wt.util.A3Disposable;
import a3wt.util.A3Resetable;

import static a3wt.util.A3Colors.BLACK;
import static a3wt.util.A3Preconditions.checkArgNotNull;

public interface A3Graphics extends A3Disposable, A3Resetable<A3Graphics> {

    interface Data extends A3Copyable<Data>, A3Resetable<Data> {
        A3Rect getClipBounds();
        void getClipBounds(final A3Rect bounds);

        A3Rect getClipRect();
        void getClipRect(final A3Rect rect);
        A3Path getClipPath();
        void getClipPath(final A3Path path);
        Data setClipRect(final A3Rect rect);
        Data setClipPath(final A3Path path);

        A3Transform getTransform();
        void getTransform(final A3Transform transform);
        Data setTransform(final A3Transform transform);

        int getColor();
        Data setColor(final int color);

        int getStyle();
        Data setStyle(final int style);

        float getStrokeWidth();
        Data setStrokeWidth(final float width);

        int getStrokeJoin();
        Data setStrokeJoin(final int join);

        int getStrokeCap();
        Data setStrokeCap(final int cap);

        float getStrokeMiter();
        Data setStrokeMiter(final float miter);

        Data setFont(final A3Font font);
        A3Font getFont();
        Data setTextSize(final float size);
        float getTextSize();

        Data setAntiAlias(final boolean antiAlias);
        boolean isAntiAlias();
        Data setFilterImage(final boolean filterImage);
        boolean isFilterImage();
        Data setSubpixelText(final boolean subpixelText);
        boolean isSubpixelText();
        Data setUnderlineText(final boolean underlineText);
        boolean isUnderlineText();
        Data setStrikeThroughText(final boolean strikeThroughText);
        boolean isStrikeThroughText();
        Data setDither(final boolean dither);
        boolean isDither();
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
        public void getClipBounds(final A3Rect bounds) {
            if (clipRect != null) clipRect.getBounds(bounds);
            else if (clipPath != null) clipPath.getBounds(bounds);
        }

        @Override
        public A3Path getClipPath() {
            return clipPath;
        }

        @Override
        public void getClipPath(final A3Path path) {
            if (clipPath != null) clipPath.to(path);
        }

        @Override
        public Data setClipPath(final A3Path path) {
            this.clipPath = path == null ? null : path.copy();
            this.clipRect = null;
            return this;
        }

        @Override
        public A3Rect getClipRect() {
            return clipRect;
        }

        @Override
        public void getClipRect(final A3Rect rect) {
            if (clipRect != null) clipRect.to(rect);
        }

        @Override
        public Data setClipRect(final A3Rect rect) {
            this.clipRect = rect == null ? null : rect.copy();
            this.clipPath = null;
            return this;
        }

        @Override
        public A3Transform getTransform() {
            return transform;
        }

        @Override
        public void getTransform(final A3Transform transform) {
            if (this.transform != null) this.transform.to(transform);
        }

        @Override
        public Data setTransform(final A3Transform transform) {
            this.transform = transform == null ? null : transform.copy();
            return this;
        }

        @Override
        public int getColor() {
            return color;
        }

        @Override
        public Data setColor(final int color) {
            this.color = color;
            return this;
        }

        @Override
        public int getStyle() {
            return style;
        }

        @Override
        public Data setStyle(final int style) {
            this.style = style;
            return this;
        }

        @Override
        public float getStrokeWidth() {
            return strokeWidth;
        }

        @Override
        public Data setStrokeWidth(final float width) {
            this.strokeWidth = width;
            return this;
        }

        @Override
        public int getStrokeJoin() {
            return strokeJoin;
        }

        @Override
        public Data setStrokeJoin(final int join) {
            this.strokeJoin = join;
            return this;
        }

        @Override
        public int getStrokeCap() {
            return strokeCap;
        }

        @Override
        public Data setStrokeCap(final int cap) {
            this.strokeCap = cap;
            return this;
        }

        @Override
        public float getStrokeMiter() {
            return strokeMiter;
        }

        @Override
        public Data setStrokeMiter(final float miter) {
            this.strokeMiter = miter;
            return this;
        }

        @Override
        public A3Font getFont() {
            return font;
        }

        @Override
        public Data setFont(final A3Font font) {
            this.font = font;
            return this;
        }

        @Override
        public float getTextSize() {
            return textSize;
        }

        @Override
        public Data setTextSize(final float size) {
            this.textSize = size;
            return this;
        }

        @Override
        public boolean isAntiAlias() {
            return antiAlias;
        }

        @Override
        public Data setAntiAlias(final boolean antiAlias) {
            this.antiAlias = antiAlias;
            return this;
        }

        @Override
        public boolean isFilterImage() {
            return filterImage;
        }

        @Override
        public Data setFilterImage(final boolean filterImage) {
            this.filterImage = filterImage;
            return this;
        }

        @Override
        public boolean isSubpixelText() {
            return subpixelText;
        }

        @Override
        public Data setSubpixelText(final boolean subpixelText) {
            this.subpixelText = subpixelText;
            return this;
        }

        @Override
        public boolean isUnderlineText() {
            return underlineText;
        }

        @Override
        public Data setUnderlineText(final boolean underlineText) {
            this.underlineText = underlineText;
            return this;
        }

        @Override
        public boolean isStrikeThroughText() {
            return strikeThroughText;
        }

        @Override
        public Data setStrikeThroughText(final boolean strikeThroughText) {
            this.strikeThroughText = strikeThroughText;
            return this;
        }

        @Override
        public boolean isDither() {
            return dither;
        }

        @Override
        public Data setDither(final boolean dither) {
            this.dither = dither;
            return this;
        }

        @Override
        public Data reset() {
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
            return this;
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
            dst.setClipPath(clipPath == null ? null : clipPath);
            dst.setClipRect(clipRect == null ? null : clipRect);
            dst.setTransform(transform == null ? null : transform);
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
    void drawArc(final A3Point pos, final A3Size size, final float startAngle, final float sweepAngle, final boolean useCenter);
    void drawArc(final A3Rect rect, final float startAngle, final float sweepAngle, final boolean useCenter);
    void drawArc(final A3Arc arc);
    void drawLine(final float startX, final float startY, final float endX, final float endY);
    void drawLine(final A3Point startPos, final A3Point endPos);
    void drawLine(final A3Line line);
    void drawQuadCurve(final float startX, final float startY,
                       final float ctrlX, final float ctrlY,
                       final float endX, final float endY);
    void drawQuadCurve(final A3Point startPos, final A3Point ctrlPos, final A3Point endPos);
    void drawQuadCurve(final A3QuadCurve quadCurve);
    void drawCubicCurve(final float startX, final float startY,
                        final float ctrlX1, final float ctrlY1,
                        final float ctrlX2, final float ctrlY2,
                        final float endX, final float endY);
    void drawCubicCurve(final A3Point startPos, final A3Point ctrlPos1, final A3Point ctrlPos2, final A3Point endPos);
    void drawCubicCurve(final A3CubicCurve cubicCurve);
    void drawOval(final float x, final float y, final float width, final float height);
    void drawOval(final A3Point pos, final A3Size size);
    void drawOval(final A3Rect rect);
    void drawOval(final A3Oval oval);
    void drawRect(final float x, final float y, final float width, final float height);
    void drawRect(final A3Point pos, final A3Size size);
    void drawRect(final A3Rect rect);
    void drawRoundRect(final float x, final float y, final float width, final float height, final float rx, final float ry);
    void drawRoundRect(final A3Point pos, final A3Size size, final A3Size corner);
    void drawRoundRect(final A3Rect rect, final A3Size corner);
    void drawRoundRect(final A3RoundRect roundRect);
    default void drawText(final CharSequence text, final float x, final float y) {
        drawText(text, 0, text.length(), x, y);
    }
    void drawText(final CharSequence text, final int start, final int end, final float x, final float y);
    void drawText(final char[] text, final int offset, final int length, final float x, final float y);

    default float measureText(final CharSequence text) {
        return measureText(text, 0, text.length());
    }
    float measureText(final CharSequence text, final int start, final int end);
    float measureText(final char[] text, final int offset, final int length);
    A3Font.Metrics getFontMetrics();
    void getFontMetrics(final A3Font.Metrics metrics);
    default A3Rect getTextBounds(final CharSequence text) {
        return getTextBounds(text, 0, text.length());
    }
    A3Rect getTextBounds(final CharSequence text, final int start, final int end);
    default void getTextBounds(final CharSequence text, final A3Rect bounds) {
        getTextBounds(text, 0, text.length(), bounds);
    }
    void getTextBounds(final CharSequence text, final int start, final int end, final A3Rect bounds);
    A3Rect getTextBounds(final char[] text, final int offset, final int length);
    void getTextBounds(final char[] text, final int offset, final int length, final A3Rect bounds);

    A3Rect getClipBounds();
    void getClipBounds(final A3Rect bounds);
    A3Rect getClipRect();
    void getClipRect(final A3Rect rect);
    A3Path getClipPath();
    void getClipPath(final A3Path path);

    A3Graphics setClipRect(final float x, final float y, final float width, final float height);
    A3Graphics setClipRect(final A3Point pos, final A3Size size);
    A3Graphics setClipRect(final A3Rect rect);
    A3Graphics setClipPath(final A3Path path);

    A3Transform getTransform();
    void getTransform(final A3Transform transform);
    A3Graphics setTransform(final A3Transform transform);
    A3Graphics setTransform(final float[] matrixValues);
    A3Graphics setTransform(final float sx, final float kx, final float dx,
                                final float ky, final float sy, final float dy);

    int getColor();
    A3Graphics setColor(final int color);

    int getStyle();
    A3Graphics setStyle(final int style);

    float getStrokeWidth();
    A3Graphics setStrokeWidth(final float strokeWidth);

    int getStrokeJoin();
    A3Graphics setStrokeJoin(final int join);

    int getStrokeCap();
    A3Graphics setStrokeCap(final int cap);

    float getStrokeMiter();
    A3Graphics setStrokeMiter(final float miter);

    A3Graphics setFont(final A3Font font);
    A3Font getFont();
    A3Graphics setTextSize(final float size);
    float getTextSize();

    A3Graphics setAntiAlias(final boolean antiAlias);
    boolean isAntiAlias();
    A3Graphics setFilterImage(final boolean filterImage);
    boolean isFilterImage();
    A3Graphics setSubpixelText(final boolean subpixelText);
    boolean isSubpixelText();
    A3Graphics setUnderlineText(final boolean underlineText);
    boolean isUnderlineText();
    A3Graphics setStrikeThroughText(final boolean strikeThroughText);
    boolean isStrikeThroughText();
    A3Graphics setDither(final boolean dither);
    boolean isDither();

    void save();
    void restore();
    void apply();

    Data getData();
    A3Graphics setData(final Data data);

}
