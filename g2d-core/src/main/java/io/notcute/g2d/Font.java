package io.notcute.g2d;

import io.notcute.util.Resetable;
import io.notcute.util.SwapCloneable;

public interface Font {

    class Metrics implements Resetable, SwapCloneable {

        private float baseline;
        private float ascent;
        private float descent;
        private float leading;
        private float maxAscent;
        private float maxDescent;

        public Metrics() {
        }

        public Metrics(Metrics metrics) {
            this(metrics.getBaseline(), metrics.getAscent(), metrics.getDescent(),
                    metrics.getLeading(), metrics.getMaxAscent(), metrics.getMaxDescent());
        }

        public Metrics(float baseline, float ascent,
                              float descent, float leading,
                              float maxAscent, float maxDescent) {
            this.baseline = baseline;
            this.ascent = ascent;
            this.descent = descent;
            this.leading = leading;
            this.maxAscent = maxAscent;
            this.maxDescent = maxDescent;
        }

        @Override
        public String toString() {
            return getClass().getName() + "{" +
                    "baseline=" + baseline +
                    ", ascent=" + ascent +
                    ", descent=" + descent +
                    ", leading=" + leading +
                    ", maxAscent=" + maxAscent +
                    ", maxDescent=" + maxDescent +
                    '}';
        }

        public void setBaseline(float baseline) {
            this.baseline = baseline;
        }

        public float getBaseline() {
            return baseline;
        }

        public void setAscent(float ascent) {
            this.ascent = ascent;
        }

        public float getAscent() {
            return ascent;
        }

        public void setDescent(float descent) {
            this.descent = descent;
        }

        public float getDescent() {
            return descent;
        }

        public void setLeading(float leading) {
            this.leading = leading;
        }

        public float getLeading() {
            return leading;
        }

        public void setMaxAscent(float maxAscent) {
            this.maxAscent = maxAscent;
        }

        public float getMaxAscent() {
            return maxAscent;
        }

        public void setMaxDescent(float maxDescent) {
            this.maxDescent = maxDescent;
        }

        public float getMaxDescent() {
            return maxDescent;
        }

        public void setMetrics(Metrics metrics) {
            setMetrics(metrics.getBaseline(), metrics.getAscent(), metrics.getDescent(),
                    metrics.getLeading(), metrics.getMaxAscent(), metrics.getMaxDescent());
        }

        public void setMetrics(float baseline, float ascent,
                        float descent, float leading,
                        float maxAscent, float maxDescent) {
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
        public Metrics clone() {
            try {
                return (Metrics) super.clone();
            }
            catch (CloneNotSupportedException e) {
                return new Metrics(this);
            }
        }

        @Override
        public void from(Object src) {
            setMetrics((Metrics) src);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Metrics metrics = (Metrics) o;

            if (Float.compare(metrics.getBaseline(), getBaseline()) != 0) return false;
            if (Float.compare(metrics.getAscent(), getAscent()) != 0) return false;
            if (Float.compare(metrics.getDescent(), getDescent()) != 0) return false;
            if (Float.compare(metrics.getLeading(), getLeading()) != 0) return false;
            if (Float.compare(metrics.getMaxAscent(), getMaxAscent()) != 0) return false;
            return Float.compare(metrics.getMaxDescent(), getMaxDescent()) == 0;
        }

        @Override
        public int hashCode() {
            int result = (getBaseline() != +0.0f ? Float.floatToIntBits(getBaseline()) : 0);
            result = 31 * result + (getAscent() != +0.0f ? Float.floatToIntBits(getAscent()) : 0);
            result = 31 * result + (getDescent() != +0.0f ? Float.floatToIntBits(getDescent()) : 0);
            result = 31 * result + (getLeading() != +0.0f ? Float.floatToIntBits(getLeading()) : 0);
            result = 31 * result + (getMaxAscent() != +0.0f ? Float.floatToIntBits(getMaxAscent()) : 0);
            result = 31 * result + (getMaxDescent() != +0.0f ? Float.floatToIntBits(getMaxDescent()) : 0);
            return result;
        }

    }

    final class Style {
        private Style() {
            throw new UnsupportedOperationException();
        }
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
