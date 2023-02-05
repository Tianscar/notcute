/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
/*
  @author Denis M. Kishenko
 */
package io.notcute.g2d;

import io.notcute.g2d.geom.NoninvertibleTransformException;
import io.notcute.g2d.geom.Polyline;
import io.notcute.g2d.geom.Shape;
import io.notcute.util.MathUtils;
import io.notcute.util.Resetable;
import io.notcute.util.SwapCloneable;

import static io.notcute.g2d.AffineTransform.Type.*;

public class AffineTransform implements Resetable, SwapCloneable {

    public static final class Type {
        private Type() {
            throw new UnsupportedOperationException();
        }
        public static final int IDENTITY = 0;
        public static final int TRANSLATION = 1;
        public static final int UNIFORM_SCALE = 2;
        public static final int GENERAL_SCALE = 4;
        public static final int QUADRANT_ROTATION = 8;
        public static final int GENERAL_ROTATION = 16;
        public static final int GENERAL_TRANSFORM = 32;
        public static final int FLIP = 64;
        public static final int MASK_SCALE = UNIFORM_SCALE | GENERAL_SCALE;
        public static final int MASK_ROTATION = QUADRANT_ROTATION | GENERAL_ROTATION;
        
        /**
         * The <code>UNKNOWN</code> is an initial type value
         */
        static final int UNKNOWN = -1;
    }
    
    /**
     * The min value equivalent to zero. If absolute value less then ZERO it considered as zero.  
     */
    private static final double ZERO = 1E-10;

    /**
     * The values of transformation matrix
     */
    private float m00;
    private float m10;
    private float m01;
    private float m11;
    private float m02;
    private float m12;

    /**
     * The transformation <code>type</code>
     */
    private transient int type;

    @Override
    public void reset() {
        type = IDENTITY;
        m00 = m11 = 1.0f;
        m10 = m01 = m02 = m12 = 0.0f;
    }

    public AffineTransform() {
        type = IDENTITY;
        m00 = m11 = 1.0f;
        m10 = m01 = m02 = m12 = 0.0f;
    }

    public AffineTransform(AffineTransform t) {
        this.type = t.type;
        this.m00 = t.m00;
        this.m10 = t.m10;
        this.m01 = t.m01;
        this.m11 = t.m11;
        this.m02 = t.m02;
        this.m12 = t.m12;
    }

    public AffineTransform(float m00, float m10, float m01, float m11, float m02, float m12) {
        this.type = UNKNOWN;
        this.m00 = m00;
        this.m10 = m10;
        this.m01 = m01;
        this.m11 = m11;
        this.m02 = m02;
        this.m12 = m12;
    }

    public AffineTransform(float[] matrix) {
        this.type = UNKNOWN;
        m00 = matrix[0];
        m10 = matrix[1];
        m01 = matrix[2];
        m11 = matrix[3];
        if (matrix.length > 4) {
            m02 = matrix[4];
            m12 = matrix[5];
        }
    }

    /*
     * Method returns type of affine transformation.
     *
     * Transform matrix is
     *   m00 m01 m02
     *   m10 m11 m12
     *
     * According analytic geometry new basis vectors are (m00, m01) and (m10, m11),
     * translation vector is (m02, m12). Original basis vectors are (1, 0) and (0, 1).
     * Type transformations classification:
     *   IDENTITY - new basis equals original one and zero translation
     *   TRANSLATION - translation vector isn't zero
     *   UNIFORM_SCALE - vectors length of new basis equals
     *   GENERAL_SCALE - vectors length of new basis doesn't equal
     *   FLIP - new basis vector orientation differ from original one
     *   QUADRANT_ROTATION - new basis is rotated by 90, 180, 270, or 360 degrees
     *   GENERAL_ROTATION - new basis is rotated by arbitrary angle
     *   GENERAL_TRANSFORM - transformation can't be inversed
     */
    public int getType() {
        if (type != UNKNOWN) {
            return type;
        }

        int type = 0;

        if (m00 * m01 + m10 * m11 != 0.0) {
            type |= GENERAL_TRANSFORM;
            return type;
        }

        if (m02 != 0.0 || m12 != 0.0) {
            type |= TRANSLATION;
        } else
            if (m00 == 1.0 && m11 == 1.0 && m01 == 0.0 && m10 == 0.0) {
                type = IDENTITY;
                return type;
            }

        if (m00 * m11 - m01 * m10 < 0.0) {
            type |= FLIP;
        }

        float dx = m00 * m00 + m10 * m10;
        float dy = m01 * m01 + m11 * m11;
        if (dx != dy) {
            type |= GENERAL_SCALE;
        } else
            if (dx != 1.0) {
                type |= UNIFORM_SCALE;
            }

        if ((m00 == 0.0 && m11 == 0.0) ||
            (m10 == 0.0 && m01 == 0.0 && (m00 < 0.0 || m11 < 0.0)))
        {
            type |= QUADRANT_ROTATION;
        } else
            if (m01 != 0.0 || m10 != 0.0) {
                type |= GENERAL_ROTATION;
            }

        return type;
    }

    public float getScaleX() {
        return m00;
    }

    public float getScaleY() {
        return m11;
    }

    public float getShearX() {
        return m01;
    }

    public float getShearY() {
        return m10;
    }

    public float getTranslateX() {
        return m02;
    }

    public float getTranslateY() {
        return m12;
    }

    public boolean isIdentity() {
        return getType() == IDENTITY;
    }

    public void getMatrix(float[] matrix) {
        matrix[0] = m00;
        matrix[1] = m10;
        matrix[2] = m01;
        matrix[3] = m11;
        if (matrix.length > 4) {
            matrix[4] = m02;
            matrix[5] = m12;
        }
    }

    public float getDeterminant() {
        return m00 * m11 - m01 * m10;
    }

    public void setTransform(float m00, float m10, float m01, float m11, float m02, float m12) {
        this.type = UNKNOWN;
        this.m00 = m00;
        this.m10 = m10;
        this.m01 = m01;
        this.m11 = m11;
        this.m02 = m02;
        this.m12 = m12;
    }

    public void setTransform(AffineTransform transform) {
        type = transform.type;
        setTransform(transform.m00, transform.m10, transform.m01, transform.m11, transform.m02, transform.m12);
    }

    public void setMatrix(float[] matrix) {
        if (matrix.length < 4) throw new IllegalArgumentException("Matrix must have at least 4 elements");
        m00 = matrix[0];
        m10 = matrix[1];
        m01 = matrix[2];
        m11 = matrix[3];
        if (matrix.length > 4) {
            m02 = matrix[4];
            m12 = matrix[5];
        }
    }

    public void setToIdentity() {
        type = IDENTITY;
        m00 = m11 = 1.0f;
        m10 = m01 = m02 = m12 = 0.0f;
    }

    public void setToTranslation(float mx, float my) {
        m00 = m11 = 1.0f;
        m01 = m10 = 0.0f;
        m02 = mx;
        m12 = my;
        if (mx == 0.0 && my == 0.0) {
            type = IDENTITY;
        } else {
            type = TRANSLATION;
        }
    }

    public void setToScale(float scx, float scy) {
        m00 = scx;
        m11 = scy;
        m10 = m01 = m02 = m12 = 0.0f;
        if (scx != 1.0 || scy != 1.0) {
            type = UNKNOWN;
        } else {
            type = IDENTITY;
        }
    }

    public void setToShear(float shx, float shy) {
        m00 = m11 = 1.0f;
        m02 = m12 = 0.0f;
        m01 = shx;
        m10 = shy;
        if (shx != 0.0 || shy != 0.0) {
            type = UNKNOWN;
        } else {
            type = IDENTITY;
        }
    }

    public void setToRotation(float angle) {
        float sin = MathUtils.sin(angle);
        float cos = MathUtils.cos(angle);
        if (Math.abs(cos) < ZERO) {
            cos = 0.0f;
            sin = sin > 0.0f ? 1.0f : -1.0f;
        } else
            if (Math.abs(sin) < ZERO) {
                sin = 0.0f;
                cos = cos > 0.0f ? 1.0f : -1.0f;
            }
        m00 = m11 = cos;
        m01 = -sin;
        m10 = sin;
        m02 = m12 = 0.0f;
        type = UNKNOWN;
    }

    public void setToRotation(float angle, float px, float py) {
        setToRotation(angle);
        m02 = px * (1.0f - m00) + py * m10;
        m12 = py * (1.0f - m00) - px * m10;
        type = UNKNOWN;
    }

    public static AffineTransform getTranslateInstance(float mx, float my) {
        AffineTransform t = new AffineTransform();
        t.setToTranslation(mx, my);
        return t;
    }

    public static AffineTransform getScaleInstance(float scx, float scY) {
        AffineTransform t = new AffineTransform();
        t.setToScale(scx, scY);
        return t;
    }

    public static AffineTransform getShearInstance(float shx, float shy) {
        AffineTransform m = new AffineTransform();
        m.setToShear(shx, shy);
        return m;
    }

    public static AffineTransform getRotateInstance(float angle) {
        AffineTransform t = new AffineTransform();
        t.setToRotation(angle);
        return t;
    }

    public static AffineTransform getRotateInstance(float angle, float x, float y) {
        AffineTransform t = new AffineTransform();
        t.setToRotation(angle, x, y);
        return t;
    }

    public void translate(float mx, float my) {
        concatenate(AffineTransform.getTranslateInstance(mx, my));
    }

    public void scale(float scx, float scy) {
        concatenate(AffineTransform.getScaleInstance(scx, scy));
    }

    public void shear(float shx, float shy) {
        concatenate(AffineTransform.getShearInstance(shx, shy));
    }

    public void rotate(float angle) {
        concatenate(AffineTransform.getRotateInstance(angle));
    }

    public void rotate(float angle, float px, float py) {
        concatenate(AffineTransform.getRotateInstance(angle, px, py));
    }

    /**
     * Multiply matrix of two AffineTransform objects
     * @param t1 - the AffineTransform object is a multiplicand
     * @param t2 - the AffineTransform object is a multiplier
     * @return an AffineTransform object that is a result of t1 multiplied by matrix t2.
     */
    AffineTransform multiply(AffineTransform t1, AffineTransform t2) {
        return new AffineTransform(
                t1.m00 * t2.m00 + t1.m10 * t2.m01,          // m00
                t1.m00 * t2.m10 + t1.m10 * t2.m11,          // m01
                t1.m01 * t2.m00 + t1.m11 * t2.m01,          // m10
                t1.m01 * t2.m10 + t1.m11 * t2.m11,          // m11
                t1.m02 * t2.m00 + t1.m12 * t2.m01 + t2.m02, // m02
                t1.m02 * t2.m10 + t1.m12 * t2.m11 + t2.m12);// m12
    }

    public void concatenate(AffineTransform t) {
        from(multiply(t, this));
    }

    public void preConcatenate(AffineTransform t) {
        from(multiply(this, t));
    }

    public AffineTransform createInverse() throws NoninvertibleTransformException {
        float det = getDeterminant();
        if (Math.abs(det) < ZERO) {
            throw new NoninvertibleTransformException("Determinant is zero");
        }
        return new AffineTransform(
                 m11 / det, // m00
                -m10 / det, // m10
                -m01 / det, // m01
                 m00 / det, // m11
                (m01 * m12 - m11 * m02) / det, // m02
                (m10 * m02 - m00 * m12) / det  // m12
        );
    }

    public Point transform(Point src, Point dst) {
        if (dst == null) dst = new Point();

        float x = src.getX();
        float y = src.getY();

        dst.setLocation(x * m00 + y * m01 + m02, x * m10 + y * m11 + m12);
        return dst;
    }

    public void transform(Point[] src, int srcOff, Point[] dst, int dstOff, int length) {
        while (--length >= 0) {
            Point srcPoint = src[srcOff++];
            float x = srcPoint.getX();
            float y = srcPoint.getY();
            Point dstPoint = dst[dstOff];
            if (dstPoint == null) dstPoint = new Point();
            dstPoint.setLocation(x * m00 + y * m01 + m02, x * m10 + y * m11 + m12);
            dst[dstOff++] = dstPoint;
        }
    }

     public void transform(float[] src, int srcOff, float[] dst, int dstOff, int length) {
        int step = 2;
        if (src == dst && srcOff < dstOff && dstOff < srcOff + length * 2) {
            srcOff = srcOff + length * 2 - 2;
            dstOff = dstOff + length * 2 - 2;
            step = -2;
        }
        while (--length >= 0) {
            float x = src[srcOff + 0];
            float y = src[srcOff + 1];
            dst[dstOff + 0] = x * m00 + y * m01 + m02;
            dst[dstOff + 1] = x * m10 + y * m11 + m12;
            srcOff += step;
            dstOff += step;
        }
    }

    public Point deltaTransform(Point src, Point dst) {
        if (dst == null) dst = new Point();

        float x = src.getX();
        float y = src.getY();

        dst.setLocation(x * m00 + y * m01, x * m10 + y * m11);
        return dst;
    }

    public void deltaTransform(float[] src, int srcOff, float[] dst, int dstOff, int length) {
        while (--length >= 0) {
            float x = src[srcOff++];
            float y = src[srcOff++];
            dst[dstOff++] = x * m00 + y * m01;
            dst[dstOff++] = x * m10 + y * m11;
        }
    }

    public Point inverseTransform(Point src, Point dst) throws NoninvertibleTransformException {
        float det = getDeterminant();
        if (Math.abs(det) < ZERO) {
            throw new NoninvertibleTransformException("Determinant is zero");
        }

        if (dst == null) dst = new Point();

        float x = src.getX() - m02;
        float y = src.getY() - m12;

        dst.setLocation((x * m11 - y * m01) / det, (y * m00 - x * m10) / det);
        return dst;
    }

    public void inverseTransform(float[] src, int srcOff, float[] dst, int dstOff, int length)
        throws NoninvertibleTransformException
    {
        float det = getDeterminant();
        if (Math.abs(det) < ZERO) {
            throw new NoninvertibleTransformException("Determinant is zero");
        }

        while (--length >= 0) {
            float x = src[srcOff++] - m02;
            float y = src[srcOff++] - m12;
            dst[dstOff++] = (x * m11 - y * m01) / det;
            dst[dstOff++] = (y * m00 - x * m10) / det;
        }
    }

    public Shape createTransformedShape(Shape src) {
        if (src == null) {
            return null;
        }
        return new Polyline(src, this);
    }

    @Override
    public String toString() {
        return
            getClass().getName() +
            "[[" + m00 + ", " + m01 + ", " + m02 + "], ["
                + m10 + ", " + m11 + ", " + m12 + "]]";
    }

    @Override
    public void from(Object src) {
        setTransform((AffineTransform) src);
    }

    @Override
    public AffineTransform clone() {
        try {
            return (AffineTransform) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new AffineTransform(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AffineTransform that = (AffineTransform) o;

        if (Float.compare(that.m00, m00) != 0) return false;
        if (Float.compare(that.m10, m10) != 0) return false;
        if (Float.compare(that.m01, m01) != 0) return false;
        if (Float.compare(that.m11, m11) != 0) return false;
        if (Float.compare(that.m02, m02) != 0) return false;
        if (Float.compare(that.m12, m12) != 0) return false;
        return getType() == that.getType();
    }

    @Override
    public int hashCode() {
        int result = (m00 != +0.0f ? Float.floatToIntBits(m00) : 0);
        result = 31 * result + (m10 != +0.0f ? Float.floatToIntBits(m10) : 0);
        result = 31 * result + (m01 != +0.0f ? Float.floatToIntBits(m01) : 0);
        result = 31 * result + (m11 != +0.0f ? Float.floatToIntBits(m11) : 0);
        result = 31 * result + (m02 != +0.0f ? Float.floatToIntBits(m02) : 0);
        result = 31 * result + (m12 != +0.0f ? Float.floatToIntBits(m12) : 0);
        result = 31 * result + getType();
        return result;
    }

}

