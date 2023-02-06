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

import static io.notcute.g2d.geom.PathIterator.SegmentType.LINE_TO;
import static io.notcute.g2d.geom.PathIterator.SegmentType.MOVE_TO;
import static io.notcute.g2d.geom.PathIterator.WindingRule.NON_ZERO;

public class Line implements Shape, Resetable, SwapCloneable {

    private float x1;
    private float y1;
    private float x2;
    private float y2;

    @Override
    public void reset() {
        x1 = y1 = x2 = y2 = 0.0f;
    }

    public Line() {
    }

    public Line(Line l) {
        this(l.getX1(), l.getY1(), l.getX2(), l.getY2());
    }

    public Line(float x1, float y1, float x2, float y2) {
        setLine(x1, y1, x2, y2);
    }

    public Line(Point p1, Point p2) {
        setLine(p1, p2);
    }

    @Override
    public String toString() {
        return getClass().getName() + '{' +
                "x1=" + x1 +
                ", y1=" + y1 +
                ", x2=" + x2 +
                ", y2=" + y2 +
                '}';
    }

    @Override
    public void getBounds(Rectangle rectangle) {
        Objects.requireNonNull(rectangle);
        float rx, ry, rw, rh;
        if (x1 < x2) {
            rx = x1;
            rw = x2 - x1;
        } else {
            rx = x2;
            rw = x1 - x2;
        }
        if (y1 < y2) {
            ry = y1;
            rh = y2 - y1;
        } else {
            ry = y2;
            rh = y1 - y2;
        }
        rectangle.setRect(rx, ry, rw, rh);
    }

    /*
     * Line2D path iterator 
     */
    static class Iterator implements PathIterator {

        /**
         * The x coordinate of the start line point
         */
        float x1;
        
        /**
         * The y coordinate of the start line point
         */
        float y1;
        
        /**
         * The x coordinate of the end line point
         */
        float x2;
        
        /**
         * The y coordinate of the end line point
         */
        float y2;

        /**
         * The path iterator transformation
         */
        AffineTransform t;

        /**
         * The current segmenet index
         */
        int index;

        /**
         * Constructs a new Line2D.Iterator for given line and transformation
         * @param l - the source Line2D object
         * @param at - the AffineTransform object to apply rectangle path
         */
        Iterator(Line l, AffineTransform at) {
            this.x1 = l.getX1();
            this.y1 = l.getY1();
            this.x2 = l.getX2();
            this.y2 = l.getY2();
            this.t = at;
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
            if (index == 0) {
                type = MOVE_TO;
                coords[0] = x1;
                coords[1] = y1;
            } else {
                type = LINE_TO;
                coords[0] = x2;
                coords[1] = y2;
            }
            if (t != null) {
                t.transform(coords, 0, coords, 0, 1);
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

    public Point getP2() {
        return new Point(x2, y2);
    }

    public void setLine(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void setLine(Point p1, Point p2) {
        setLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    public void setLine(Line line) {
        setLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
    }

    public static int relativeCCW(float x1, float y1, float x2, float y2, float px, float py) {
        /*
         * A = (x2-x1, y2-y1) P = (px-x1, py-y1)
         */
        x2 -= x1;
        y2 -= y1;
        px -= x1;
        py -= y1;
        float t = px * y2 - py * x2; // PxA
        if (t == 0.0) {
            t = px * x2 + py * y2; // P*A
            if (t > 0.0) {
                px -= x2; // B-A
                py -= y2;
                t = px * x2 + py * y2; // (P-A)*A
                if (t < 0.0) {
                    t = 0.0f;
                }
            }
        }

        return t < 0.0 ? -1 : (t > 0.0 ? 1 : 0);
    }

    public int relativeCCW(float px, float py) {
        return relativeCCW(getX1(), getY1(), getX2(), getY2(), px, py);
    }

    public int relativeCCW(Point p) {
        return relativeCCW(getX1(), getY1(), getX2(), getY2(), p.getX(), p.getY());
    }

    public static boolean linesIntersect(float x1, float y1, float x2,
            float y2, float x3, float y3, float x4, float y4)
    {
        /*
         * A = (x2-x1, y2-y1) B = (x3-x1, y3-y1) C = (x4-x1, y4-y1) D = (x4-x3,
         * y4-y3) = C-B E = (x1-x3, y1-y3) = -B F = (x2-x3, y2-y3) = A-B
         *
         * Result is ((AxB) * (AxC) <=0) and ((DxE) * (DxF) <= 0)
         *
         * DxE = (C-B)x(-B) = BxB-CxB = BxC DxF = (C-B)x(A-B) = CxA-CxB-BxA+BxB =
         * AxB+BxC-AxC
         */

        x2 -= x1; // A
        y2 -= y1;
        x3 -= x1; // B
        y3 -= y1;
        x4 -= x1; // C
        y4 -= y1;

        float AvB = x2 * y3 - x3 * y2;
        float AvC = x2 * y4 - x4 * y2;

        // Online
        if (AvB == 0.0 && AvC == 0.0) {
            if (x2 != 0.0) {
                return
                    (x4 * x3 <= 0.0) ||
                    ((x3 * x2 >= 0.0) &&
                     (x2 > 0.0 ? x3 <= x2 || x4 <= x2 : x3 >= x2 || x4 >= x2));
            }
            if (y2 != 0.0) {
                return
                    (y4 * y3 <= 0.0) ||
                    ((y3 * y2 >= 0.0) &&
                     (y2 > 0.0 ? y3 <= y2 || y4 <= y2 : y3 >= y2 || y4 >= y2));
            }
            return false;
        }

        float BvC = x3 * y4 - x4 * y3;

        return (AvB * AvC <= 0.0) && (BvC * (AvB + BvC - AvC) <= 0.0);
    }

    public boolean intersectsLine(float x1, float y1, float x2, float y2) {
        return linesIntersect(x1, y1, x2, y2, getX1(), getY1(), getX2(), getY2());
    }

    public boolean intersectsLine(Line l) {
        return linesIntersect(l.getX1(), l.getY1(), l.getX2(), l.getY2(), getX1(), getY1(), getX2(), getY2());
    }

    public static float ptSegDistSq(float x1, float y1, float x2, float y2, float px, float py) {
        /*
         * A = (x2 - x1, y2 - y1) P = (px - x1, py - y1)
         */
        x2 -= x1; // A = (x2, y2)
        y2 -= y1;
        px -= x1; // P = (px, py)
        py -= y1;
        float dist;
        if (px * x2 + py * y2 <= 0.0) { // P*A
            dist = px * px + py * py;
        } else {
            px = x2 - px; // P = A - P = (x2 - px, y2 - py)
            py = y2 - py;
            if (px * x2 + py * y2 <= 0.0) { // P*A
                dist = px * px + py * py;
            } else {
                dist = px * y2 - py * x2;
                dist = dist * dist / (x2 * x2 + y2 * y2); // pxA/|A|
            }
        }
        if (dist < 0) {
            dist = 0;
        }
        return dist;
    }

    public static float ptSegDist(float x1, float y1, float x2, float y2, float px, float py) {
        return MathUtils.sqrt(ptSegDistSq(x1, y1, x2, y2, px, py));
    }

    public float ptSegDistSq(float px, float py) {
        return ptSegDistSq(getX1(), getY1(), getX2(), getY2(), px, py);
    }

    public float ptSegDistSq(Point p) {
        return ptSegDistSq(getX1(), getY1(), getX2(), getY2(), p.getX(), p.getY());
    }

    public float ptSegDist(float px, float py) {
        return ptSegDist(getX1(), getY1(), getX2(), getY2(), px, py);
    }

    public float ptSegDist(Point p) {
        return ptSegDist(getX1(), getY1(), getX2(), getY2(), p.getX(), p.getY());
    }

    public static float ptLineDistSq(float x1, float y1, float x2, float y2, float px, float py) {
        x2 -= x1;
        y2 -= y1;
        px -= x1;
        py -= y1;
        float s = px * y2 - py * x2;
        return s * s / (x2 * x2 + y2 * y2);
    }

    public static float ptLineDist(float x1, float y1, float x2, float y2, float px, float py) {
        return MathUtils.sqrt(ptLineDistSq(x1, y1, x2, y2, px, py));
    }

    public float ptLineDistSq(float px, float py) {
        return ptLineDistSq(getX1(), getY1(), getX2(), getY2(), px, py);
    }

    public float ptLineDistSq(Point p) {
        return ptLineDistSq(getX1(), getY1(), getX2(), getY2(), p.getX(), p.getY());
    }

    public float ptLineDist(float px, float py) {
        return ptLineDist(getX1(), getY1(), getX2(), getY2(), px, py);
    }

    public float ptLineDist(Point p) {
        return ptLineDist(getX1(), getY1(), getX2(), getY2(), p.getX(), p.getY());
    }

    public boolean contains(float px, float py) {
        return false;
    }

    public boolean contains(Point p) {
        return false;
    }

    public boolean contains(Rectangle r) {
        return false;
    }

    public boolean contains(float rx, float ry, float rw, float rh) {
        return false;
    }

    public boolean intersects(float rx, float ry, float rw, float rh) {
        return intersects(new Rectangle(rx, ry, rw, rh));
    }

    public boolean intersects(Rectangle r) {
        return r.intersectsLine(getX1(), getY1(), getX2(), getY2());
    }

    public PathIterator getPathIterator(AffineTransform at) {
        return new Iterator(this, at);
    }

    public PathIterator getPathIterator(AffineTransform at, float flatness) {
        return new Iterator(this, at);
    }

    @Override
    public Line clone() {
        try {
            return (Line) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new Line(this);
        }
    }

    @Override
    public void from(Object src) {
        setLine((Line) src);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Line line = (Line) o;

        if (Float.compare(line.getX1(), getX1()) != 0) return false;
        if (Float.compare(line.getY1(), getY1()) != 0) return false;
        if (Float.compare(line.getX2(), getX2()) != 0) return false;
        return Float.compare(line.getY2(), getY2()) == 0;
    }

    @Override
    public int hashCode() {
        int result = (getX1() != +0.0f ? Float.floatToIntBits(getX1()) : 0);
        result = 31 * result + (getY1() != +0.0f ? Float.floatToIntBits(getY1()) : 0);
        result = 31 * result + (getX2() != +0.0f ? Float.floatToIntBits(getX2()) : 0);
        result = 31 * result + (getY2() != +0.0f ? Float.floatToIntBits(getY2()) : 0);
        return result;
    }

}