package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.util.A3Copyable;
import com.ansdoship.a3wt.util.A3Disposable;

import static com.ansdoship.a3wt.util.A3Colors.BLACK;

public interface A3Graphics extends A3Disposable {

    interface Data extends A3Copyable<Data> {
        A3Path getClip();
        void setClip(A3Path path);

        int getColor();
        void setColor(int color);

        int getStyle();
        void setStyle(int style);

        float getStrokeWidth();
        void setStrokeWidth(float width);

        int getStrokeJoin();
        void setStrokeJoin(int join);

        int getStrokeCap();
        void setStrokeCap(int cap);

        float getStrokeMiter();
        void setStrokeMiter(float miter);

        void setFont(A3Font font);
        A3Font getFont();
        void setTextSize(float size);
        float getTextSize();

        void setAntiAlias(boolean antiAlias);
        boolean isAntiAlias();
        void setFilterImage(boolean filterImage);
        boolean isFilterImage();
        void setSubpixelText(boolean subpixelText);
        boolean isSubpixelText();
        void setUnderlineText(boolean underlineText);
        boolean isUnderlineText();
        void setStrikeThroughText(boolean strikeThroughText);
        boolean isStrikeThroughText();
        void setDither(boolean dither);
        boolean isDither();

        void reset();
    }

    class DefaultData implements Data {

        protected A3Path clip;
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
        public A3Path getClip() {
            return clip;
        }

        @Override
        public void setClip(A3Path clip) {
            this.clip = clip;
        }

        @Override
        public int getColor() {
            return color;
        }

        @Override
        public void setColor(int color) {
            this.color = color;
        }

        @Override
        public int getStyle() {
            return style;
        }

        @Override
        public void setStyle(int style) {
            this.style = style;
        }

        @Override
        public float getStrokeWidth() {
            return strokeWidth;
        }

        @Override
        public void setStrokeWidth(float width) {
            this.strokeWidth = width;
        }

        @Override
        public int getStrokeJoin() {
            return strokeJoin;
        }

        @Override
        public void setStrokeJoin(int join) {
            this.strokeJoin = join;
        }

        @Override
        public int getStrokeCap() {
            return strokeCap;
        }

        @Override
        public void setStrokeCap(int cap) {
            this.strokeCap = cap;
        }

        @Override
        public float getStrokeMiter() {
            return strokeMiter;
        }

        @Override
        public void setStrokeMiter(float miter) {
            this.strokeMiter = miter;
        }

        @Override
        public A3Font getFont() {
            return font;
        }

        @Override
        public void setFont(A3Font font) {
            this.font = font;
        }

        @Override
        public float getTextSize() {
            return textSize;
        }

        @Override
        public void setTextSize(float size) {
            this.textSize = size;
        }

        @Override
        public boolean isAntiAlias() {
            return antiAlias;
        }

        @Override
        public void setAntiAlias(boolean antiAlias) {
            this.antiAlias = antiAlias;
        }

        @Override
        public boolean isFilterImage() {
            return filterImage;
        }

        @Override
        public void setFilterImage(boolean filterImage) {
            this.filterImage = filterImage;
        }

        @Override
        public boolean isSubpixelText() {
            return subpixelText;
        }

        @Override
        public void setSubpixelText(boolean subpixelText) {
            this.subpixelText = subpixelText;
        }

        @Override
        public boolean isUnderlineText() {
            return underlineText;
        }

        @Override
        public void setUnderlineText(boolean underlineText) {
            this.underlineText = underlineText;
        }

        @Override
        public boolean isStrikeThroughText() {
            return strikeThroughText;
        }

        @Override
        public void setStrikeThroughText(boolean strikeThroughText) {
            this.strikeThroughText = strikeThroughText;
        }

        @Override
        public boolean isDither() {
            return dither;
        }

        @Override
        public void setDither(boolean dither) {
            this.dither = dither;
        }

        @Override
        public void reset() {
            setClip(null);
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
            DefaultData copy = new DefaultData();
            copy.setClip(getClip());
            copy.setColor(getColor());
            copy.setStyle(getStyle());
            copy.setStrokeWidth(getStrokeWidth());
            copy.setStrokeJoin(getStrokeJoin());
            copy.setStrokeCap(getStrokeCap());
            copy.setStrokeMiter(getStrokeMiter());
            copy.setFont(getFont());
            copy.setTextSize(getTextSize());
            copy.setAntiAlias(isAntiAlias());
            copy.setFilterImage(isFilterImage());
            copy.setSubpixelText(isSubpixelText());
            copy.setUnderlineText(isUnderlineText());
            copy.setStrikeThroughText(isStrikeThroughText());
            copy.setDither(isDither());
            return copy;
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
    void drawPath(A3Path path);
    void drawImage(A3Image image, int x, int y);
    void drawPoint(float x, float y);
    void drawArc(float left, float top, float right, float bottom, float startAngle, float sweepAngle, boolean useCenter);
    void drawLine(float startX, float startY, float stopX, float stopY);
    void drawOval(float left, float top, float right, float bottom);
    void drawRect(float left, float top, float right, float bottom);
    void drawRoundRect(float left, float top, float right, float bottom, float rx, float ry);
    void drawText(CharSequence text, float x, float y);
    void drawText(char[] text, int offset, int length, float x, float y);

    A3Path getClip();
    void setClip(A3Path path);
    void setClip(float left, float top, float right, float bottom);

    int getColor();
    void setColor(int color);

    int getStyle();
    void setStyle(int style);

    float getStrokeWidth();
    void setStrokeWidth(float strokeWidth);

    int getStrokeJoin();
    void setStrokeJoin(int join);

    int getStrokeCap();
    void setStrokeCap(int cap);

    float getStrokeMiter();
    void setStrokeMiter(float miter);

    void setFont(A3Font font);
    A3Font getFont();
    void setTextSize(float size);
    float getTextSize();

    void setAntiAlias(boolean antiAlias);
    boolean isAntiAlias();
    void setFilterImage(boolean filterImage);
    boolean isFilterImage();
    void setSubpixelText(boolean subpixelText);
    boolean isSubpixelText();
    void setUnderlineText(boolean underlineText);
    boolean isUnderlineText();
    void setStrikeThroughText(boolean strikeThroughText);
    boolean isStrikeThroughText();
    void setDither(boolean dither);
    boolean isDither();

    void reset();

    void save();
    void restore();
    void apply();

    Data getData();
    void setData(Data data);

}
