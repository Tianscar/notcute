package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.util.A3Copyable;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public interface A3Font {

    interface Metrics extends A3Copyable<Metrics> {
        void setBaseline(final float baseline);
        float getBaseline();
        void setAscent(final float ascent);
        float getAscent();
        void setDescent(final float descent);
        float getDescent();
        void setLeading(final float leading);
        float getLeading();
        void setTop(final float top);
        float getTop();
        void setBottom(final float bottom);
        float getBottom();

        void reset();

        default float getHeight() {
            return getBottom() + getTop();
        }
        default float getLineHeight() {
            return getHeight() + getLeading();
        }
    }

    class DefaultMetrics implements Metrics {

        protected float baseline;
        protected float ascent;
        protected float descent;
        protected float leading;
        protected float top;
        protected float bottom;

        public DefaultMetrics() {
            reset();
        }

        public DefaultMetrics(final float baseline, final float ascent,
                              final float descent, final float leading,
                              final float top, final float bottom) {
            this.baseline = baseline;
            this.ascent = ascent;
            this.descent = descent;
            this.leading = leading;
            this.top = top;
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
        public void setTop(float top) {
            this.top = top;
        }

        @Override
        public float getTop() {
            return top;
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
        public void reset() {
            baseline = ascent = descent = leading = top = bottom = 0;
        }

        @Override
        public Metrics copy() {
            return new DefaultMetrics(baseline, ascent, descent, leading, top, bottom);
        }

        @Override
        public void to(final Metrics dst) {
            checkArgNotNull(dst, "dst");
            dst.setBaseline(baseline);
            dst.setAscent(ascent);
            dst.setDescent(descent);
            dst.setLeading(leading);
            dst.setTop(top);
            dst.setBottom(bottom);
        }

        @Override
        public void from(final Metrics src) {
            checkArgNotNull(src, "src");
            src.to(this);
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
