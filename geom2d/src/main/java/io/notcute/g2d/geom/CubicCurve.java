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
import io.notcute.util.MathUtils;
import io.notcute.util.Resetable;
import io.notcute.util.SwapCloneable;

import java.util.NoSuchElementException;
import java.util.Objects;

import static io.notcute.g2d.geom.PathIterator.SegmentType.CUBIC_TO;
import static io.notcute.g2d.geom.PathIterator.SegmentType.MOVE_TO;
import static io.notcute.g2d.geom.PathIterator.WindingRule.NON_ZERO;

public class CubicCurve implements Shape, Resetable, SwapCloneable {

    private float x1;
    private float y1;
    private float ctrlx1;
    private float ctrly1;
    private float ctrlx2;
    private float ctrly2;
    private float x2;
    private float y2;

    @Override
    public void reset() {
        x1 = y1 = ctrlx1 = ctrly1 = ctrlx2 = ctrly2 = x2 = y2 = 0.0f;
    }

    public CubicCurve() {
    }

    public CubicCurve(CubicCurve cc) {
        this(cc.getX1(), cc.getY1(), cc.getCtrlX1(), cc.getCtrlY1(), cc.getCtrlX2(), cc.getCtrlY2(), cc.getX2(), cc.getY2());
    }

    public CubicCurve(float x1, float y1, float ctrlx1, float ctrly1,
                      float ctrlx2, float ctrly2, float x2, float y2) {
        setCurve(x1, y1, ctrlx1, ctrly1, ctrlx2, ctrly2, x2, y2);
    }

    @Override
    public String toString() {
        return getClass().getName() + '{' +
                "x1=" + x1 +
                ", y1=" + y1 +
                ", ctrlx1=" + ctrlx1 +
                ", ctrly1=" + ctrly1 +
                ", ctrlx2=" + ctrlx2 +
                ", ctrly2=" + ctrly2 +
                ", x2=" + x2 +
                ", y2=" + y2 +
                '}';
    }

    @Override
    public void getBounds(Rectangle rectangle) {
        Objects.requireNonNull(rectangle);
        float rx1 = Math.min(Math.min(x1, x2), Math.min(ctrlx1, ctrlx2));
        float ry1 = Math.min(Math.min(y1, y2), Math.min(ctrly1, ctrly2));
        float rx2 = Math.max(Math.max(x1, x2), Math.max(ctrlx1, ctrlx2));
        float ry2 = Math.max(Math.max(y1, y2), Math.max(ctrly1, ctrly2));
        rectangle.setRect(rx1, ry1, rx2 - rx1, ry2 - ry1);
    }

    /*
     * CubicCurve2D path iterator 
     */
    static class Iterator implements PathIterator {

        /**
         * The source CubicCurve2D object
         */
        CubicCurve c;
        
        /**
         * The path iterator transformation
         */
        AffineTransform t;
        
        /**
         * The current segmenet index
         */
        int index;

        /**
         * Constructs a new CubicCurve2D.Iterator for given line and transformation
         * @param c - the source CubicCurve2D object
         * @param t - the AffineTransform object to apply rectangle path
         */
        Iterator(CubicCurve c, AffineTransform t) {
            this.c = c;
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
                type = CUBIC_TO;
                coords[0] = c.getCtrlX1();
                coords[1] = c.getCtrlY1();
                coords[2] = c.getCtrlX2();
                coords[3] = c.getCtrlY2();
                coords[4] = c.getX2();
                coords[5] = c.getY2();
                count = 3;
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

    public void setX1(float x1) {
        this.x1 = x1;
    }

    public float getY1() {
        return y1;
    }

    public void setY1(float y1) {
        this.y1 = y1;
    }

    public float getCtrlX1() {
        return ctrlx1;
    }

    public void setCtrlX1(float ctrlx1) {
        this.ctrlx1 = ctrlx1;
    }

    public float getCtrlY1() {
        return ctrly1;
    }

    public void setCtrlY1(float ctrly1) {
        this.ctrly1 = ctrly1;
    }

    public float getCtrlX2() {
        return ctrlx2;
    }

    public void setCtrlX2(float ctrlx2) {
        this.ctrlx2 = ctrlx2;
    }

    public float getCtrlY2() {
        return ctrly2;
    }

    public void setCtrlY2(float ctrly2) {
        this.ctrly2 = ctrly2;
    }

    public float getX2() {
        return x2;
    }

    public void setX2(float x2) {
        this.x2 = x2;
    }

    public float getY2() {
        return y2;
    }

    public void setY2(float y2) {
        this.y2 = y2;
    }

    public Point getP1() {
        return new Point(x1, y1);
    }

    public Point getCtrlP1() {
        return new Point(ctrlx1, ctrly1);
    }

    public Point getCtrlP2() {
        return new Point(ctrlx2, ctrly2);
    }

    public Point getP2() {
        return new Point(x2, y2);
    }

    public void setCurve(float x1, float y1, float ctrlx1, float ctrly1,
                         float ctrlx2, float ctrly2, float x2, float y2)
    {
        this.x1 = x1;
        this.y1 = y1;
        this.ctrlx1 = ctrlx1;
        this.ctrly1 = ctrly1;
        this.ctrlx2 = ctrlx2;
        this.ctrly2 = ctrly2;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void setCurve(Point p1, Point cp1, Point cp2, Point p2) {
        setCurve(
                p1.getX(), p1.getY(),
                cp1.getX(), cp1.getY(),
                cp2.getX(), cp2.getY(),
                p2.getX(), p2.getY());
    }

    public void setCurve(float[] coords, int offset) {
        setCurve(
                coords[offset + 0], coords[offset + 1],
                coords[offset + 2], coords[offset + 3],
                coords[offset + 4], coords[offset + 5],
                coords[offset + 6], coords[offset + 7]);
    }

    public void setCurve(Point[] points, int offset) {
        setCurve(
                points[offset + 0].getX(), points[offset + 0].getY(),
                points[offset + 1].getX(), points[offset + 1].getY(),
                points[offset + 2].getX(), points[offset + 2].getY(),
                points[offset + 3].getX(), points[offset + 3].getY());
    }

    public void setCurve(CubicCurve curve) {
        setCurve(
                curve.getX1(), curve.getY1(),
                curve.getCtrlX1(), curve.getCtrlY1(),
                curve.getCtrlX2(), curve.getCtrlY2(),
                curve.getX2(), curve.getY2());
    }

    public float getFlatnessSq() {
        return getFlatnessSq(
                getX1(), getY1(),
                getCtrlX1(), getCtrlY1(),
                getCtrlX2(), getCtrlY2(),
                getX2(), getY2());
    }

    public static float getFlatnessSq(float x1, float y1, float ctrlx1, float ctrly1,
            float ctrlx2, float ctrly2, float x2, float y2)
    {
        return Math.max(
                Line.ptSegDistSq(x1, y1, x2, y2, ctrlx1, ctrly1),
                Line.ptSegDistSq(x1, y1, x2, y2, ctrlx2, ctrly2));
    }

    public static float getFlatnessSq(float coords[], int offset) {
        return getFlatnessSq(
                coords[offset + 0], coords[offset + 1],
                coords[offset + 2], coords[offset + 3],
                coords[offset + 4], coords[offset + 5],
                coords[offset + 6], coords[offset + 7]);
    }

    public float getFlatness() {
        return getFlatness(
                getX1(), getY1(),
                getCtrlX1(), getCtrlY1(),
                getCtrlX2(), getCtrlY2(),
                getX2(), getY2());
    }

    public static float getFlatness(float x1, float y1, float ctrlx1, float ctrly1,
            float ctrlx2, float ctrly2, float x2, float y2)
    {
        return MathUtils.sqrt(getFlatnessSq(x1, y1, ctrlx1, ctrly1, ctrlx2, ctrly2, x2, y2));
    }

    public static float getFlatness(float[] coords, int offset) {
        return getFlatness(
                coords[offset + 0], coords[offset + 1],
                coords[offset + 2], coords[offset + 3],
                coords[offset + 4], coords[offset + 5],
                coords[offset + 6], coords[offset + 7]);
    }

    public void subdivide(CubicCurve left, CubicCurve right) {
        subdivide(this, left, right);
    }

    public static void subdivide(CubicCurve src, CubicCurve left, CubicCurve right) {
        float x1 = src.getX1();
        float y1 = src.getY1();
        float cx1 = src.getCtrlX1();
        float cy1 = src.getCtrlY1();
        float cx2 = src.getCtrlX2();
        float cy2 = src.getCtrlY2();
        float x2 = src.getX2();
        float y2 = src.getY2();
        float cx = (cx1 + cx2) / 2.0f;
        float cy = (cy1 + cy2) / 2.0f;
        cx1 = (x1 + cx1) / 2.0f;
        cy1 = (y1 + cy1) / 2.0f;
        cx2 = (x2 + cx2) / 2.0f;
        cy2 = (y2 + cy2) / 2.0f;
        float ax = (cx1 + cx) / 2.0f;
        float ay = (cy1 + cy) / 2.0f;
        float bx = (cx2 + cx) / 2.0f;
        float by = (cy2 + cy) / 2.0f;
        cx = (ax + bx) / 2.0f;
        cy = (ay + by) / 2.0f;
        if (left != null) {
            left.setCurve(x1, y1, cx1, cy1, ax, ay, cx, cy);
        }
        if (right != null) {
            right.setCurve(cx, cy, bx, by, cx2, cy2, x2, y2);
        }
    }

    public static void subdivide(float src[], int srcOff, float left[], int leftOff, float right[], int rightOff) {
        float x1 = src[srcOff + 0];
        float y1 = src[srcOff + 1];
        float cx1 = src[srcOff + 2];
        float cy1 = src[srcOff + 3];
        float cx2 = src[srcOff + 4];
        float cy2 = src[srcOff + 5];
        float x2 = src[srcOff + 6];
        float y2 = src[srcOff + 7];
        float cx = (cx1 + cx2) / 2.0f;
        float cy = (cy1 + cy2) / 2.0f;
        cx1 = (x1 + cx1) / 2.0f;
        cy1 = (y1 + cy1) / 2.0f;
        cx2 = (x2 + cx2) / 2.0f;
        cy2 = (y2 + cy2) / 2.0f;
        float ax = (cx1 + cx) / 2.0f;
        float ay = (cy1 + cy) / 2.0f;
        float bx = (cx2 + cx) / 2.0f;
        float by = (cy2 + cy) / 2.0f;
        cx = (ax + bx) / 2.0f;
        cy = (ay + by) / 2.0f;
        if (left != null) {
            left[leftOff + 0] = x1;
            left[leftOff + 1] = y1;
            left[leftOff + 2] = cx1;
            left[leftOff + 3] = cy1;
            left[leftOff + 4] = ax;
            left[leftOff + 5] = ay;
            left[leftOff + 6] = cx;
            left[leftOff + 7] = cy;
        }
        if (right != null) {
            right[rightOff + 0] = cx;
            right[rightOff + 1] = cy;
            right[rightOff + 2] = bx;
            right[rightOff + 3] = by;
            right[rightOff + 4] = cx2;
            right[rightOff + 5] = cy2;
            right[rightOff + 6] = x2;
            right[rightOff + 7] = y2;
        }
    }

    public static int solveCubic(float eqn[]) {
        return solveCubic(eqn, eqn);
    }

    public static int solveCubic(float eqn[], float res[]) {
        return Crossing.solveCubic(eqn, res);
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

    public PathIterator getPathIterator(AffineTransform at, float flatness) {
        return new FlatteningPathIterator(getPathIterator(at), flatness);
    }

    @Override
    public CubicCurve clone() {
        try {
            return (CubicCurve) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new CubicCurve(this);
        }
    }

    @Override
    public void from(Object src) {
        setCurve((CubicCurve) src);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CubicCurve that = (CubicCurve) o;

        if (Float.compare(that.getX1(), getX1()) != 0) return false;
        if (Float.compare(that.getY1(), getY1()) != 0) return false;
        if (Float.compare(that.ctrlx1, ctrlx1) != 0) return false;
        if (Float.compare(that.ctrly1, ctrly1) != 0) return false;
        if (Float.compare(that.ctrlx2, ctrlx2) != 0) return false;
        if (Float.compare(that.ctrly2, ctrly2) != 0) return false;
        if (Float.compare(that.getX2(), getX2()) != 0) return false;
        return Float.compare(that.getY2(), getY2()) == 0;
    }

    @Override
    public int hashCode() {
        int result = (getX1() != +0.0f ? Float.floatToIntBits(getX1()) : 0);
        result = 31 * result + (getY1() != +0.0f ? Float.floatToIntBits(getY1()) : 0);
        result = 31 * result + (ctrlx1 != +0.0f ? Float.floatToIntBits(ctrlx1) : 0);
        result = 31 * result + (ctrly1 != +0.0f ? Float.floatToIntBits(ctrly1) : 0);
        result = 31 * result + (ctrlx2 != +0.0f ? Float.floatToIntBits(ctrlx2) : 0);
        result = 31 * result + (ctrly2 != +0.0f ? Float.floatToIntBits(ctrly2) : 0);
        result = 31 * result + (getX2() != +0.0f ? Float.floatToIntBits(getX2()) : 0);
        result = 31 * result + (getY2() != +0.0f ? Float.floatToIntBits(getY2()) : 0);
        return result;
    }

}