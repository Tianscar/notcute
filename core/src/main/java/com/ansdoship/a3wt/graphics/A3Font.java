package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.util.A3Copyable;
import com.ansdoship.a3wt.util.A3Resetable;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public interface A3Font {

    interface Metrics extends A3Copyable<Metrics>, A3Resetable {
        void setBaseline(final float baseline);
        float getBaseline();
        void setAscent(final float ascent);
        float getAscent();
        void setDescent(final float descent);
        float getDescent();
        void setLeading(final float leading);
        float getLeading();
        void setMaxAscent(final float maxAscent);
        float getMaxAscent();
        void setMaxDescent(final float maxDescent);
        float getMaxDescent();
        void set(final float baseline, final float ascent, final float descent, final float leading, final float maxAscent, final float maxDescent);

        default float getHeight() {
            return getMaxDescent() + getMaxAscent();
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
        protected float maxAscent;
        protected float maxDescent;

        public DefaultMetrics() {
            reset();
        }

        public DefaultMetrics(final float baseline, final float ascent,
                              final float descent, final float leading,
                              final float maxAscent, final float maxDescent) {
            this.baseline = baseline;
            this.ascent = ascent;
            this.descent = descent;
            this.leading = leading;
            this.maxAscent = maxAscent;
            this.maxDescent = maxDescent;
        }

        @Override
        public void setBaseline(final float baseline) {
            this.baseline = baseline;
        }

        @Override
        public float getBaseline() {
            return baseline;
        }

        @Override
        public void setAscent(final float ascent) {
            this.ascent = ascent;
        }

        @Override
        public float getAscent() {
            return ascent;
        }

        @Override
        public void setDescent(final float descent) {
            this.descent = descent;
        }

        @Override
        public float getDescent() {
            return descent;
        }

        @Override
        public void setLeading(final float leading) {
            this.leading = leading;
        }

        @Override
        public float getLeading() {
            return leading;
        }

        @Override
        public void setMaxAscent(final float maxAscent) {
            this.maxAscent = maxAscent;
        }

        @Override
        public float getMaxAscent() {
            return maxAscent;
        }

        @Override
        public void setMaxDescent(final float maxDescent) {
            this.maxDescent = maxDescent;
        }

        @Override
        public float getMaxDescent() {
            return maxDescent;
        }

        @Override
        public void set(final float baseline, final float ascent,
                        final float descent, final float leading,
                        final float maxAscent, final float maxDescent) {
            this.baseline = baseline;
            this.ascent = ascent;
            this.descent = descent;
            this.leading = leading;
            this.maxAscent = maxAscent;
            this.maxDescent = maxDescent;
        }

        @Override
        public void reset() {
            baseline = ascent = descent = leading = maxAscent = maxDescent = 0;
        }

        @Override
        public Metrics copy() {
            return new DefaultMetrics(baseline, ascent, descent, leading, maxAscent, maxDescent);
        }

        @Override
        public void to(final Metrics dst) {
            checkArgNotNull(dst, "dst");
            dst.set(baseline, ascent, descent, leading, maxAscent, maxDescent);
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
