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
package io.notcute.g2d.geom;

import io.notcute.g2d.AffineTransform;
import io.notcute.g2d.Point;
import io.notcute.util.Resetable;
import io.notcute.util.SwapCloneable;

import java.util.NoSuchElementException;
import java.util.Objects;

import static io.notcute.g2d.geom.PathIterator.SegmentType.MOVE_TO;
import static io.notcute.g2d.geom.PathIterator.SegmentType.QUAD_TO;
import static io.notcute.g2d.geom.PathIterator.WindingRule.NON_ZERO;

public class QuadCurve implements Shape, Resetable, SwapCloneable {

    private float x1;
    private float y1;
    private float ctrlx;
    private float ctrly;
    private float x2;
    private float y2;

    @Override
    public void reset() {
        x1 = y1 = ctrlx = ctrly = x2 = y2 = 0.0f;
    }

    public QuadCurve() {
    }

    public QuadCurve(QuadCurve qc) {
        this(qc.getX1(), qc.getY1(), qc.getCtrlX(), qc.getCtrlY(), qc.getX2(), qc.getY2());
    }

    public QuadCurve(float x1, float y1, float ctrlx, float ctrly, float x2, float y2) {
        setCurve(x1, y1, ctrlx, ctrly, x2, y2);
    }

    @Override
    public String toString() {
        return getClass().getName() + '{' +
                "x1=" + x1 +
                ", y1=" + y1 +
                ", ctrlx=" + ctrlx +
                ", ctrly=" + ctrly +
                ", x2=" + x2 +
                ", y2=" + y2 +
                '}';
    }

    @Override
    public void getBounds(Rectangle rectangle) {
        Objects.requireNonNull(rectangle);
        float rx0 = Math.min(Math.min(x1, x2), ctrlx);
        float ry0 = Math.min(Math.min(y1, y2), ctrly);
        float rx1 = Math.max(Math.max(x1, x2), ctrlx);
        float ry1 = Math.max(Math.max(y1, y2), ctrly);
        rectangle.setRect(rx0, ry0, rx1 - rx0, ry1 - ry0);
    }

    /*
     * QuadCurve2D path iterator 
     */
    static class Iterator implements PathIterator {

        /**
         * The source QuadCurve2D object
         */
        QuadCurve c;

        /**
         * The path iterator transformation
         */
        AffineTransform t;

        /**
         * The current segmenet index
         */
        int index;

        /**
         * Constructs a new QuadCurve2D.Iterator for given line and transformation
         * @param q - the source QuadCurve2D object
         * @param t - the AffineTransform object to apply rectangle path
         */
        Iterator(QuadCurve q, AffineTransform t) {
            this.c = q;
            this.t = t;
        }

        public int getWindingRule() {
            return NON_ZERO;
        }

        @Override
        public boolean hasNext() {
            return index <= 1;
        }

        public void next() {
            index++;
        }

        public int currentSegment(float[] coords) {
            if (!hasNext()) {
                throw new NoSuchElementException("Iterator out of bounds");
            }
            int type;
            int count;
            if (index == 0) {
                type = MOVE_TO;
                coords[0] = c.getX1();
                coords[1] = c.getY1();
                count = 1;
            } else {
                type = QUAD_TO;
                coords[0] = c.getCtrlX();
                coords[1] = c.getCtrlY();
                coords[2] = c.getX2();
                coords[3] = c.getY2();
                count = 2;
            }
            if (t != null) {
                t.transform(coords, 0, coords, 0, count);
            }
            return type;
        }

    }

    public float getX1() {
        return x1;
    }

    public float getY1() {
        return y1;
    }

    public float getCtrlX() {
        return ctrlx;
    }

    public float getCtrlY() {
        return ctrly;
    }

    public float getX2() {
        return x2;
    }

    public float getY2() {
        return y2;
    }

    public Point getP1() {
        return new Point(x1, y1);
    }

    public Point getCtrlPt() {
        return new Point(ctrlx, ctrly);
    }

    public Point getP2() {
        return new Point(x2, y2);
    }

    public void setCurve(float x1, float y1, float ctrlx, float ctrly, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.ctrlx = ctrlx;
        this.ctrly = ctrly;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void setCurve(Point p1, Point cp, Point p2) {
        setCurve(p1.getX(), p1.getY(), cp.getX(), cp.getY(), p2.getX(), p2.getY());
    }

    public void setCurve(float[] coords, int offset) {
        setCurve(
                coords[offset + 0], coords[offset + 1],
                coords[offset + 2], coords[offset + 3],
                coords[offset + 4], coords[offset + 5]);
    }

    public void setCurve(Point[] points, int offset) {
        setCurve(
                points[offset + 0].getX(), points[offset + 0].getY(),
                points[offset + 1].getX(), points[offset + 1].getY(),
                points[offset + 2].getX(), points[offset + 2].getY());
    }

    public void setCurve(QuadCurve curve) {
        setCurve(
                curve.getX1(), curve.getY1(),
                curve.getCtrlX(), curve.getCtrlY(),
                curve.getX2(), curve.getY2());
    }

    public float getFlatnessSq() {
        return Line.ptSegDistSq(
                getX1(), getY1(),
                getX2(), getY2(),
                getCtrlX(), getCtrlY());
    }

    public static float getFlatnessSq(float x1, float y1, float ctrlx, float ctrly, float x2, float y2) {
        return Line.ptSegDistSq(x1, y1, x2, y2, ctrlx, ctrly);
    }

    public static float getFlatnessSq(float[] coords, int offset) {
        return Line.ptSegDistSq(
                coords[offset + 0], coords[offset + 1],
                coords[offset + 4], coords[offset + 5],
                coords[offset + 2], coords[offset + 3]);
    }

    public float getFlatness() {
        return Line.ptSegDist(getX1(), getY1(), getX2(), getY2(), getCtrlX(), getCtrlY());
    }

    public static float getFlatness(float x1, float y1, float ctrlx,
            float ctrly, float x2, float y2)
    {
        return Line.ptSegDist(x1, y1, x2, y2, ctrlx, ctrly);
    }

    public static float getFlatness(float[] coords, int offset) {
        return Line.ptSegDist(
                coords[offset + 0], coords[offset + 1],
                coords[offset + 4], coords[offset + 5],
                coords[offset + 2], coords[offset + 3]);
    }

    public void subdivide(QuadCurve left, QuadCurve right) {
        subdivide(this, left, right);
    }

    public static void subdivide(QuadCurve src, QuadCurve left, QuadCurve right) {
        float x1 = src.getX1();
        float y1 = src.getY1();
        float cx = src.getCtrlX();
        float cy = src.getCtrlY();
        float x2 = src.getX2();
        float y2 = src.getY2();
        float cx1 = (x1 + cx) / 2.0f;
        float cy1 = (y1 + cy) / 2.0f;
        float cx2 = (x2 + cx) / 2.0f;
        float cy2 = (y2 + cy) / 2.0f;
        cx = (cx1 + cx2) / 2.0f;
        cy = (cy1 + cy2) / 2.0f;
        if (left != null) {
            left.setCurve(x1, y1, cx1, cy1, cx, cy);
        }
        if (right != null) {
            right.setCurve(cx, cy, cx2, cy2, x2, y2);
        }
    }

    public static void subdivide(float[] src, int srcoff, float[] left,
                                 int leftOff, float[] right, int rightOff)
    {
        float x1 = src[srcoff + 0];
        float y1 = src[srcoff + 1];
        float cx = src[srcoff + 2];
        float cy = src[srcoff + 3];
        float x2 = src[srcoff + 4];
        float y2 = src[srcoff + 5];
        float cx1 = (x1 + cx) / 2.0f;
        float cy1 = (y1 + cy) / 2.0f;
        float cx2 = (x2 + cx) / 2.0f;
        float cy2 = (y2 + cy) / 2.0f;
        cx = (cx1 + cx2) / 2.0f;
        cy = (cy1 + cy2) / 2.0f;
        if (left != null) {
            left[leftOff + 0] = x1;
            left[leftOff + 1] = y1;
            left[leftOff + 2] = cx1;
            left[leftOff + 3] = cy1;
            left[leftOff + 4] = cx;
            left[leftOff + 5] = cy;
        }
        if (right != null) {
            right[rightOff + 0] = cx;
            right[rightOff + 1] = cy;
            right[rightOff + 2] = cx2;
            right[rightOff + 3] = cy2;
            right[rightOff + 4] = x2;
            right[rightOff + 5] = y2;
        }
    }

    public static int solveQuadratic(float[] eqn) {
        return solveQuadratic(eqn, eqn);
    }


    public static int solveQuadratic(float[] eqn, float[] res) {
        return Crossing.solveQuad(eqn, res);
    }

    public boolean contains(float px, float py) {
        return Crossing.isInsideEvenOdd(Crossing.crossShape(this, px, py));
    }

    public boolean contains(float rx, float ry, float rw, float rh) {
        int cross = Crossing.intersectShape(this, rx, ry, rw, rh);
        return cross != Crossing.CROSSING && Crossing.isInsideEvenOdd(cross);
    }

    public boolean intersects(float rx, float ry, float rw, float rh) {
        int cross = Crossing.intersectShape(this, rx, ry, rw, rh);
        return cross == Crossing.CROSSING || Crossing.isInsideEvenOdd(cross);
    }

    public boolean contains(Point p) {
        return contains(p.getX(), p.getY());
    }

    public boolean intersects(Rectangle r) {
        return intersects(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    public boolean contains(Rectangle r) {
        return contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    public PathIterator getPathIterator(AffineTransform t) {
        return new Iterator(this, t);
    }

    public PathIterator getPathIterator(AffineTransform t, float flatness) {
        return new FlatteningPathIterator(getPathIterator(t), flatness);
    }

    @Override
    public QuadCurve clone() {
        try {
            return (QuadCurve) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new QuadCurve(this);
        }
    }

    @Override
    public void from(Object src) {
        setCurve((QuadCurve) src);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuadCurve quadCurve = (QuadCurve) o;

        if (Float.compare(quadCurve.getX1(), getX1()) != 0) return false;
        if (Float.compare(quadCurve.getY1(), getY1()) != 0) return false;
        if (Float.compare(quadCurve.ctrlx, ctrlx) != 0) return false;
        if (Float.compare(quadCurve.ctrly, ctrly) != 0) return false;
        if (Float.compare(quadCurve.getX2(), getX2()) != 0) return false;
        return Float.compare(quadCurve.getY2(), getY2()) == 0;
    }

    @Override
    public int hashCode() {
        int result = (getX1() != +0.0f ? Float.floatToIntBits(getX1()) : 0);
        result = 31 * result + (getY1() != +0.0f ? Float.floatToIntBits(getY1()) : 0);
        result = 31 * result + (ctrlx != +0.0f ? Float.floatToIntBits(ctrlx) : 0);
        result = 31 * result + (ctrly != +0.0f ? Float.floatToIntBits(ctrly) : 0);
        result = 31 * result + (getX2() != +0.0f ? Float.floatToIntBits(getX2()) : 0);
        result = 31 * result + (getY2() != +0.0f ? Float.floatToIntBits(getY2()) : 0);
        return result;
    }

}

