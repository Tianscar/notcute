package io.notcute.g2d;

import io.notcute.g2d.geom.PathIterator;
import io.notcute.g2d.geom.Rectangle;
import io.notcute.g2d.geom.Shape;
import io.notcute.util.Disposable;
import io.notcute.util.Resetable;
import io.notcute.util.SwapCloneable;

public interface Graphics extends Disposable {

    class Info implements Resetable, SwapCloneable {

        private Shape clip;
        private AffineTransform transform;
        private int color;
        private int style;
        private float strokeWidth;
        private int strokeJoin;
        private int strokeCap;
        private float strokeMiter;
        private Font font;
        private float textSize;
        private boolean antiAlias;
        private boolean filterImage;
        private boolean subpixelText;
        private boolean underlineText;
        private boolean strikeThroughText;
        private boolean dither;

        public Info() {
            reset();
        }

        public Info(Info info) {
            this(info.getClip(), info.getTransform(), info.getColor(), info.getStyle(),
                    info.getStrokeWidth(), info.getStrokeJoin(), info.getStrokeCap(), info.getStrokeMiter(),
                    info.getFont(), info.getTextSize(), info.isAntiAlias(), info.isFilterImage(), info.isSubpixelText(),
                    info.isUnderlineText(), info.isStrikeThroughText(), info.isDither());
        }

        public void setStroke(float width, int join, int cap, int miter) {
            setStrokeWidth(width);
            setStrokeJoin(join);
            setStrokeCap(cap);
            setStrokeMiter(miter);
        }

        public void setTextAttributes(float size, boolean subpixelText,
                                      boolean underlineText, boolean strikeThroughText) {
            setTextSize(size);
            setSubpixelText(subpixelText);
            setUnderlineText(underlineText);
            setStrikeThroughText(strikeThroughText);
        }

        public void setFilter(boolean antiAlias, boolean filterImage) {
            setAntiAlias(antiAlias);
            setFilterImage(filterImage);
        }

        public Info(Shape clip, AffineTransform transform, int color, int style,
                    float strokeWidth, int strokeJoin, int strokeCap, float strokeMiter,
                    Font font, float textSize, boolean antiAlias, boolean filterImage, boolean subpixelText,
                    boolean underlineText, boolean strikeThroughText, boolean dither) {
            setInfo(clip, transform, color, style, strokeWidth, strokeJoin, strokeCap, strokeMiter,
                    font, textSize, antiAlias, filterImage, subpixelText, underlineText, strikeThroughText,
                    dither);
        }

        public void setInfo(Info info) {
            setInfo(info.getClip(), info.getTransform(), info.getColor(), info.getStyle(),
                    info.getStrokeWidth(), info.getStrokeJoin(), info.getStrokeCap(),
                    info.getStrokeMiter(), info.getFont(), info.getTextSize(), info.isAntiAlias(),
                    info.isFilterImage(), info.isSubpixelText(), info.isUnderlineText(), info.isStrikeThroughText(), info.isDither());
        }

        public void setInfo(Shape clip, AffineTransform transform, int color, int style,
                    float strokeWidth, int strokeJoin, int strokeCap, float strokeMiter,
                    Font font, float textSize, boolean antiAlias, boolean filterImage, boolean subpixelText,
                    boolean underlineText, boolean strikeThroughText, boolean dither) {
            setClip(clip);
            setTransform(transform);
            setColor(color);
            setStyle(style);
            setStrokeWidth(strokeWidth);
            setStrokeJoin(strokeJoin);
            setStrokeCap(strokeCap);
            setStrokeMiter(strokeMiter);
            setFont(font);
            setTextSize(textSize);
            setAntiAlias(antiAlias);
            setFilterImage(filterImage);
            setSubpixelText(subpixelText);
            setUnderlineText(underlineText);
            setStrikeThroughText(strikeThroughText);
            setDither(dither);
        }

        public Rectangle getClipBounds() {
            if (clip != null) return clip.getBounds();
            else return null;
        }

        public void getClipBounds(Rectangle bounds) {
            if (clip != null) clip.getBounds(bounds);
        }

        public Shape getClip() {
            return clip;
        }

        public void setClip(Shape clip) {
            this.clip = clip == null ? null : (Shape) clip.clone();
        }

        public AffineTransform getTransform() {
            return transform;
        }

        public void getTransform(AffineTransform transform) {
            if (this.transform != null) this.transform.to(transform);
        }

        public void setTransform(AffineTransform transform) {
            this.transform = transform == null ? null : transform.clone();
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public int getStyle() {
            return style;
        }

        public void setStyle(int style) {
            this.style = style;
        }
        
        public float getStrokeWidth() {
            return strokeWidth;
        }
        
        public void setStrokeWidth(float width) {
            this.strokeWidth = width;
        }

        public int getStrokeJoin() {
            return strokeJoin;
        }

        public void setStrokeJoin(final int join) {
            this.strokeJoin = join;
        }

        public int getStrokeCap() {
            return strokeCap;
        }

        public void setStrokeCap(final int cap) {
            this.strokeCap = cap;
        }

        public float getStrokeMiter() {
            return strokeMiter;
        }

        public void setStrokeMiter(final float miter) {
            this.strokeMiter = miter;
        }

        public Font getFont() {
            return font;
        }

        public void setFont(Font font) {
            this.font = font;
        }

        public float getTextSize() {
            return textSize;
        }

        public void setTextSize(final float size) {
            this.textSize = size;
        }

        public boolean isAntiAlias() {
            return antiAlias;
        }

        public void setAntiAlias(final boolean antiAlias) {
            this.antiAlias = antiAlias;
        }

        public boolean isFilterImage() {
            return filterImage;
        }

        public void setFilterImage(final boolean filterImage) {
            this.filterImage = filterImage;
        }

        public boolean isSubpixelText() {
            return subpixelText;
        }

        public void setSubpixelText(final boolean subpixelText) {
            this.subpixelText = subpixelText;
        }

        public boolean isUnderlineText() {
            return underlineText;
        }

        public void setUnderlineText(final boolean underlineText) {
            this.underlineText = underlineText;
        }

        public boolean isStrikeThroughText() {
            return strikeThroughText;
        }

        public void setStrikeThroughText(final boolean strikeThroughText) {
            this.strikeThroughText = strikeThroughText;
        }

        public boolean isDither() {
            return dither;
        }

        public void setDither(final boolean dither) {
            this.dither = dither;
        }

        @Override
        public void reset() {
            setClip(null);
            setTransform(null);
            setColor(Color.BLACK);
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
        public Info clone() {
            try {
                Info info = (Info) super.clone();
                info.setClip(clip == null ? null : (Shape) clip.clone());
                info.setTransform(transform == null ? null : transform.clone());
                return info;
            }
            catch (CloneNotSupportedException e) {
                return new Info(this);
            }
        }

        @Override
        public void from(Object src) {
            setInfo((Info) src);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Info info = (Info) o;

            if (getColor() != info.getColor()) return false;
            if (getStyle() != info.getStyle()) return false;
            if (Float.compare(info.getStrokeWidth(), getStrokeWidth()) != 0) return false;
            if (getStrokeJoin() != info.getStrokeJoin()) return false;
            if (getStrokeCap() != info.getStrokeCap()) return false;
            if (Float.compare(info.getStrokeMiter(), getStrokeMiter()) != 0) return false;
            if (Float.compare(info.getTextSize(), getTextSize()) != 0) return false;
            if (isAntiAlias() != info.isAntiAlias()) return false;
            if (isFilterImage() != info.isFilterImage()) return false;
            if (isSubpixelText() != info.isSubpixelText()) return false;
            if (isUnderlineText() != info.isUnderlineText()) return false;
            if (isStrikeThroughText() != info.isStrikeThroughText()) return false;
            if (isDither() != info.isDither()) return false;
            if (!getClip().equals(info.getClip())) return false;
            if (!getTransform().equals(info.getTransform())) return false;
            return getFont().equals(info.getFont());
        }

        @Override
        public int hashCode() {
            int result = getClip().hashCode();
            result = 31 * result + getTransform().hashCode();
            result = 31 * result + getColor();
            result = 31 * result + getStyle();
            result = 31 * result + (getStrokeWidth() != +0.0f ? Float.floatToIntBits(getStrokeWidth()) : 0);
            result = 31 * result + getStrokeJoin();
            result = 31 * result + getStrokeCap();
            result = 31 * result + (getStrokeMiter() != +0.0f ? Float.floatToIntBits(getStrokeMiter()) : 0);
            result = 31 * result + getFont().hashCode();
            result = 31 * result + (getTextSize() != +0.0f ? Float.floatToIntBits(getTextSize()) : 0);
            result = 31 * result + (isAntiAlias() ? 1 : 0);
            result = 31 * result + (isFilterImage() ? 1 : 0);
            result = 31 * result + (isSubpixelText() ? 1 : 0);
            result = 31 * result + (isUnderlineText() ? 1 : 0);
            result = 31 * result + (isStrikeThroughText() ? 1 : 0);
            result = 31 * result + (isDither() ? 1 : 0);
            return result;
        }

    }

    class Style {
        private Style() {
            throw new UnsupportedOperationException();
        }
        public static final int STROKE = 0;
        public static final int FILL = 1;
    }

    final class Join {
        private Join() {
            throw new UnsupportedOperationException();
        }
        public static final int MITER = 0;
        public static final int ROUND = 1;
        public static final int BEVEL = 2;
    }

    final class Cap {
        private Cap() {
            throw new UnsupportedOperationException();
        }
        public static final int BUTT = 0;
        public static final int ROUND = 1;
        public static final int SQUARE = 2;
    }

    int getWidth();
    int getHeight();

    void drawColor();

    default void drawImage(Image image, float x, float y) {
        drawImage(image, AffineTransform.getTranslateInstance(x, y));
    }
    default void drawImage(Image image, Point point) {
        drawImage(image, point.getX(), point.getY());
    }
    void drawImage(Image image, AffineTransform transform);

    void drawPoint(float x, float y);
    default void drawPoint(Point point) {
        drawPoint(point.getX(), point.getY());
    }

    void drawPathIterator(PathIterator iterator);
    default void drawShape(Shape shape) {
        drawPathIterator(shape.getPathIterator());
    }
    default void drawShape(Shape shape, AffineTransform transform) {
        drawShape(transform.createTransformedShape(shape));
    }
    
    default void drawText(CharSequence text, float x, float y) {
        drawText(text, 0, text.length(), x, y);
    }
    default void drawText(CharSequence text, int start, int end, float x, float y) {
        drawText(text, start, end, AffineTransform.getTranslateInstance(x, y));
    }
    default void drawText(char[] text, float x, float y) {
        drawText(text, 0, text.length, x, y);
    }
    default void drawText(char[] text, int offset, int length, float x, float y) {
        drawText(text, offset, length, AffineTransform.getTranslateInstance(x, y));
    }
    default void drawText(CharSequence text, AffineTransform transform) {
        drawText(text, 0, text.length(), transform);
    }
    void drawText(CharSequence text, int start, int end, AffineTransform transform);
    default void drawText(char[] text, AffineTransform transform) {
        drawText(text, 0, text.length, transform);
    }
    void drawText(char[] text, int offset, int length, AffineTransform transform);

    default float measureText(CharSequence text) {
        return measureText(text, 0, text.length());
    }
    float measureText(CharSequence text, int start, int end);
    default float measureText(char[] text) {
        return measureText(text, 0, text.length);
    }
    float measureText(char[] text, int offset, int length);

    void getFontMetrics(Font.Metrics metrics);
    default Font.Metrics getFontMetrics() {
        Font.Metrics metrics = new Font.Metrics();
        getFontMetrics(metrics);
        return metrics;
    }

    void getTextBounds(CharSequence text, int start, int end, Rectangle bounds);
    default Rectangle getTextBounds(CharSequence text, int start, int end) {
        Rectangle rectangle = new Rectangle();
        getTextBounds(text, start, end, rectangle);
        return rectangle;
    }
    default void getTextBounds(CharSequence text, Rectangle bounds) {
        getTextBounds(text, 0, text.length(), bounds);
    }
    default Rectangle getTextBounds(CharSequence text) {
        return getTextBounds(text, 0, text.length());
    }

    void getTextBounds(char[] text, int offset, int length, Rectangle bounds);
    default Rectangle getTextBounds(char[] text, int offset, int length) {
        Rectangle rectangle = new Rectangle();
        getTextBounds(text, offset, length, rectangle);
        return rectangle;
    }

    Info getInfo();
    default void setInfo(Info info) {
        info.to(getInfo());
    }

    void apply();
    void save();
    void restore();

}
