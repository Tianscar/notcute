package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.util.A3Copyable;

public interface A3Font {

    interface Metrics extends A3Copyable<Metrics> {
        void setBaseline(float baseline);
        float getBaseline();
        void setAscent(float ascent);
        float getAscent();
        void setDescent(float descent);
        float getDescent();
        void setLeading(float leading);
        float getLeading();
        void setLeft(float left);
        float getLeft();
        void setTop(float top);
        float getTop();
        void setRight(float right);
        float getRight();
        void setBottom(float bottom);
        float getBottom();
    }

    class DefaultMetrics implements Metrics {

        protected float baseline;
        protected float ascent;
        protected float descent;
        protected float leading;
        protected float left;
        protected float top;
        protected float right;
        protected float bottom;

        public DefaultMetrics(final float baseline, final float ascent,
                              final float descent, final float leading,
                              final float left, final float top,
                              final float right, final float bottom) {
            this.baseline = baseline;
            this.ascent = ascent;
            this.descent = descent;
            this.leading = leading;
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }

        @Override
        public void setBaseline(float baseline) {
            this.baseline = baseline;
        }

        @Override
        public float getBaseline() {
            return baseline;
        }

        @Override
        public void setAscent(float ascent) {
            this.ascent = ascent;
        }

        @Override
        public float getAscent() {
            return ascent;
        }

        @Override
        public void setDescent(float descent) {
            this.descent = descent;
        }

        @Override
        public float getDescent() {
            return descent;
        }

        @Override
        public void setLeading(float leading) {
            this.leading = leading;
        }

        @Override
        public float getLeading() {
            return leading;
        }

        @Override
        public void setLeft(float left) {
            this.left = left;
        }

        @Override
        public float getLeft() {
            return left;
        }

        @Override
        public void setTop(float top) {
            this.top = top;
        }

        @Override
        public float getTop() {
            return top;
        }

        @Override
        public void setRight(float right) {
            this.right = right;
        }

        @Override
        public float getRight() {
            return right;
        }

        @Override
        public void setBottom(float bottom) {
            this.bottom = bottom;
        }

        @Override
        public float getBottom() {
            return bottom;
        }

        @Override
        public Metrics copy() {
            return new DefaultMetrics(getBaseline(), getAscent(), getDescent(), getLeading(), getLeft(), getTop(), getRight(), getBottom());
        }

    }

    class Style {
        private Style(){}
        public static final int NORMAL = 0;
        public static final int BOLD = 1;
        public static final int ITALIC = 2;
        public static final int BOLD_ITALIC = 3;
    }

    int getStyle();

    boolean isNormal();
    boolean isBold();
    boolean isItalic();
    boolean isBoldItalic();

}
