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
import io.notcute.g2d.Dimension;
import io.notcute.g2d.Point;
import io.notcute.util.MathUtils;
import io.notcute.util.Resetable;
import io.notcute.util.SwapCloneable;

import java.util.NoSuchElementException;
import java.util.Objects;

import static io.notcute.g2d.geom.Arc.Type.*;
import static io.notcute.g2d.geom.PathIterator.SegmentType.*;
import static io.notcute.g2d.geom.PathIterator.WindingRule.NON_ZERO;

public class Arc implements RectangularShape, Resetable, SwapCloneable {

    public static final class Type {
        private Type() {
            throw new UnsupportedOperationException();
        }
        public final static int OPEN = 0;
        public final static int CHORD = 1;
        public final static int PIE = 2;
    }

    private float x;
    private float y;
    private float width;
    private float height;
    private float start;
    private float extent;

    @Override
    public void reset() {
        x = y = width = height = start = extent = 0.0f;
        type = OPEN;
    }

    public Arc() {
        type = OPEN;
    }

    public Arc(Arc a) {
        this(a.getX(), a.getY(), a.getWidth(), a.getHeight(), a.getAngleStart(), a.getAngleExtent(), a.getArcType());
    }

    public Arc(int type) {
        setArcType(type);
    }

    public Arc(float x, float y, float width, float height,
               float start, float extent, int type)
    {
        this(type);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.start = start;
        this.extent = extent;
    }

    public Arc(Rectangle bounds, float start, float extent, int type) {
        this(type);
        this.x = bounds.getX();
        this.y = bounds.getY();
        this.width = bounds.getWidth();
        this.height = bounds.getHeight();
        this.start = start;
        this.extent = extent;
    }

    @Override
    public String toString() {
        return getClass().getName() + '{' +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ", start=" + start +
                ", extent=" + extent +
                ", type=" + type +
                '}';
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    /*
     * Arc2D path iterator  
     */
    static class Iterator implements PathIterator {

        /**
         * The x coordinate of left-upper corner of the arc rectangle bounds
         */
        float x;

        /**
         * The y coordinate of left-upper corner of the arc rectangle bounds
         */
        float y;

        /**
         * The width of the arc rectangle bounds
         */
        float width;
        
        /**
         * The height of the arc rectangle bounds
         */
        float height;
        
        /**
         * The start angle of the arc in degrees
         */
        float angle;
        
        /**
         * The angle extent in degrees
         */
        float extent;
         
        /**
         * The closure type of the arc
         */
        int type;
        
        /**
         * The path iterator transformation
         */
        AffineTransform t;
        
        /**
         * The current segmenet index
         */
        int index;
        
        /**
         * The number of arc segments the source arc subdivided to be approximated by Bezier curves.
         * Depends on extent value.  
         */
        int arcCount;
        
        /**
         * The number of line segments. Depends on closure type. 
         */
        int lineCount;
        
        /**
         * The step to calculate next arc subdivision point
         */
        float step;
        
        /**
         * The tempopary value of cosinus of the current angle 
         */
        float cos;

        /**
         * The tempopary value of sinus of the current angle 
         */
        float sin;
        
        /**
         * The coefficient to calculate control points of Bezier curves
         */
        float k;
        
        /**
         * The tempopary value of x coordinate of the Bezier curve control vector
         */
        float kx;

        /**
         * The tempopary value of y coordinate of the Bezier curve control vector
         */
        float ky;
        
        /**
         * The x coordinate of the first path point (MOVE_TO)
         */
        float mx;
        
        /**
         * The y coordinate of the first path point (MOVE_TO)
         */
        float my;

        /**
         * Constructs a new Arc2D.Iterator for given line and transformation
         * @param a - the source Arc2D object
         * @param t - the AffineTransform object to apply rectangle path
         */
        Iterator(Arc a, AffineTransform t) {
            if (width < 0 || height < 0) {
                arcCount = 0;
                lineCount = 0;
                index = 1;
                return;
            }

            this.width = a.getWidth() / 2.0f;
            this.height = a.getHeight() / 2.0f;
            this.x = a.getX() + width;
            this.y = a.getY() + height;
            this.angle = -MathUtils.toRadians(a.getAngleStart());
            this.extent = -a.getAngleExtent();
            this.type = a.getArcType();
            this.t = t;

            if (Math.abs(extent) >= 360.0) {
                arcCount = 4;
                k = 4.0f / 3.0f * (MathUtils.sqrt(2.0f) - 1.0f);
                step = (float) (Math.PI / 2.0);
                if (extent < 0.0) {
                    step = -step;
                    k = -k;
                }
            } else {
                arcCount = (int)Math.rint(Math.abs(extent) / 90.0f);
                step = MathUtils.toRadians(extent / arcCount);
                k = 4.0f / 3.0f * (1.0f - MathUtils.cos(step / 2.0f))
                        / MathUtils.sin(step / 2.0f);
            }

            lineCount = 0;
            if (type == CHORD) {
                lineCount++;
            } else if (type == PIE) {
                lineCount += 2;
            }
        }

        public int getWindingRule() {
            return NON_ZERO;
        }

        @Override
        public boolean hasNext() {
            return index <= arcCount + lineCount;
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
                count = 1;
                cos = MathUtils.cos(angle);
                sin = MathUtils.sin(angle);
                kx = k * width * sin;
                ky = k * height * cos;
                coords[0] = mx = x + cos * width;
                coords[1] = my = y + sin * height;
            } else if (index <= arcCount) {
                type = CUBIC_TO;
                count = 3;
                coords[0] = mx - kx;
                coords[1] = my + ky;
                angle += step;
                cos = MathUtils.cos(angle);
                sin = MathUtils.sin(angle);
                kx = k * width * sin;
                ky = k * height * cos;
                coords[4] = mx = x + cos * width;
                coords[5] = my = y + sin * height;
                coords[2] = mx + kx;
                coords[3] = my - ky;
            } else if (index == arcCount + lineCount) {
                type = CLOSE;
                count = 0;
            } else {
                type = LINE_TO;
                count = 1;
                coords[0] = x;
                coords[1] = y;
            }
            if (t != null) {
                t.transform(coords, 0, coords, 0, count);
            }
            return type;
        }

    }

    /**
     * The closure type of the arc
     */
    private int type;

    public float getAngleStart() {
        return start;
    }

    public float getAngleExtent() {
        return extent;
    }

    @Override
    public boolean isEmpty() {
        return width <= 0.0 || height <= 0.0;
    }

    public void setArc(float x, float y, float width, float height,
                       float start, float extent, int type)
    {
        this.setArcType(type);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.start = start;
        this.extent = extent;
    }

    public void setArc(Arc arc) {
        setArc(arc.getX(), arc.getY(), arc.getWidth(), arc.getHeight(), arc
                .getAngleStart(), arc.getAngleExtent(), arc.getArcType());
    }

    public void setAngleStart(float start) {
        this.start = start;
    }

    public void setAngleExtent(float extent) {
        this.extent = extent;
    }

    public int getArcType() {
        return type;
    }

    public void setArcType(int type) {
        if (type != OPEN && type != CHORD && type != PIE) {
            throw new IllegalArgumentException("Invalid type of Arc: " + type);
        }
        this.type = type;
    }

    public Point getStartPoint() {
        float a = MathUtils.toRadians(getAngleStart());
        return new Point(
                getX() + (1.0f + MathUtils.cos(a)) * getWidth() / 2.0f,
                getY() + (1.0f - MathUtils.sin(a)) * getHeight() / 2.0f);
    }

    public Point getEndPoint() {
        float a = MathUtils.toRadians(getAngleStart() + getAngleExtent());
        return new Point(
                getX() + (1.0f + MathUtils.cos(a)) * getWidth() / 2.0f,
                getY() + (1.0f - MathUtils.sin(a)) * getHeight() / 2.0f);
    }

    public void getBounds(Rectangle rectangle) {
        Objects.requireNonNull(rectangle);
        if (isEmpty()) {
            rectangle.setRect(getX(), getY(), getWidth(), getHeight());
            return;
        }
        float rx1 = getX();
        float ry1 = getY();
        float rx2 = rx1 + getWidth();
        float ry2 = ry1 + getHeight();

        Point p1 = getStartPoint();
        Point p2 = getEndPoint();

        float bx1 = containsAngle(180.0f) ? rx1 : Math.min(p1.getX(), p2.getX());
        float by1 = containsAngle(90.0f)  ? ry1 : Math.min(p1.getY(), p2.getY());
        float bx2 = containsAngle(0.0f)   ? rx2 : Math.max(p1.getX(), p2.getX());
        float by2 = containsAngle(270.0f) ? ry2 : Math.max(p1.getY(), p2.getY());

        if (type == PIE) {
            float cx = getCenterX();
            float cy = getCenterY();
            bx1 = Math.min(bx1, cx);
            by1 = Math.min(by1, cy);
            bx2 = Math.max(bx2, cx);
            by2 = Math.max(by2, cy);
        }
        rectangle.setRect(bx1, by1, bx2 - bx1, by2 - by1);
    }

    @Override
    public void setRect(float x, float y, float width, float height) {
        setArc(x, y, width, height, getAngleStart(), getAngleExtent(), type);
    }

    public void setArc(Point point, Dimension size, float start, float extent, int type) {
        setArc(point.getX(), point.getY(), size.getWidth(), size.getHeight(), start, extent, type);
    }

    public void setArc(Rectangle rect, float start, float extent, int type) {
        setArc(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), start, extent, type);
    }

    public void setArcByCenter(float x, float y, float radius, float start, float extent, int type) {
        setArc(x - radius, y - radius, radius * 2.0f, radius * 2.0f, start, extent, type);
    }

    public void setArcByTangent(Point p1, Point p2, Point p3, float radius) {
        // Used simple geometric calculations of arc center, radius and angles by tangents
        float a1 = -MathUtils.atan2(p1.getY() - p2.getY(), p1.getX() - p2.getX());
        float a2 = -MathUtils.atan2(p3.getY() - p2.getY(), p3.getX() - p2.getX());
        float am = (a1 + a2) / 2.0f;
        float ah = a1 - am;
        float d = radius / Math.abs(MathUtils.sin(ah));
        float x = p2.getX() + d * MathUtils.cos(am);
        float y = p2.getY() - d * MathUtils.sin(am);
        ah = (float) (ah >= 0.0 ? Math.PI * 1.5 - ah : Math.PI * 0.5 - ah);
        a1 = getNormAngle(MathUtils.toDegrees(am - ah));
        a2 = getNormAngle(MathUtils.toDegrees(am + ah));
        float delta = a2 - a1;
        if (delta <= 0.0) {
            delta += 360.0;
        }
        setArcByCenter(x, y, radius, a1, delta, type);
    }

    public void setAngleStart(Point point) {
        float angle = MathUtils.atan2(point.getY() - getCenterY(), point.getX() - getCenterX());
        setAngleStart(getNormAngle(-MathUtils.toDegrees(angle)));
    }

    public void setAngles(float x1, float y1, float x2, float y2) {
        float cx = getCenterX();
        float cy = getCenterY();
        float a1 = getNormAngle(-MathUtils.toDegrees(MathUtils.atan2(y1 - cy, x1 - cx)));
        float a2 = getNormAngle(-MathUtils.toDegrees(MathUtils.atan2(y2 - cy, x2 - cx)));
        a2 -= a1;
        if (a2 <= 0.0) {
            a2 += 360.0;
        }
        setAngleStart(a1);
        setAngleExtent(a2);
    }

    public void setAngles(Point p1, Point p2) {
        setAngles(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    /**
     * Normalizes angle 
     * @param angle - the source angle in degrees
     * @return a normalized angle
     */
    float getNormAngle(float angle) {
        float n = MathUtils.floor(angle / 360.0f);
        return angle - n * 360.0f;
    }

    public boolean containsAngle(float angle) {
        float extent = getAngleExtent();
        if (extent >= 360.0) {
            return true;
        }
        angle = getNormAngle(angle);
        float a1 = getNormAngle(getAngleStart());
        float a2 = a1 + extent;
        if (a2 > 360.0) {
            return angle >= a1 || angle <= a2 - 360.0;
        }
        if (a2 < 0.0) {
            return angle >= a2 + 360.0 || angle <= a1;
        }
        return extent > 0.0 ? a1 <= angle && angle <= a2 : a2 <= angle
                && angle <= a1;
    }

    public boolean contains(float px, float py) {
        // Normalize point
        float nx = (px - getX()) / getWidth() - 0.5f;
        float ny = (py - getY()) / getHeight() - 0.5f;

        if ((nx * nx + ny * ny) > 0.25) {
            return false;
        }

        float extent = getAngleExtent();
        float absExtent = Math.abs(extent);
        if (absExtent >= 360.0) {
            return true;
        }

        boolean containsAngle = containsAngle(MathUtils.toDegrees(-MathUtils
                .atan2(ny, nx)));
        if (type == PIE) {
            return containsAngle;
        }
        if (absExtent <= 180.0 && !containsAngle) {
            return false;
        }

        Line l = new Line(getStartPoint(), getEndPoint());
        int ccw1 = l.relativeCCW(px, py);
        int ccw2 = l.relativeCCW(getCenterX(), getCenterY());
        return ccw1 == 0 || ccw2 == 0
                || ((ccw1 + ccw2) == 0 ^ absExtent > 180.0);
    }

    public boolean contains(float rx, float ry, float rw, float rh) {

        if (!(contains(rx, ry) && contains(rx + rw, ry)
                && contains(rx + rw, ry + rh) && contains(rx, ry + rh))) {
            return false;
        }

        float absExtent = Math.abs(getAngleExtent());
        if (type != PIE || absExtent <= 180.0 || absExtent >= 360.0) {
            return true;
        }

        Rectangle r = new Rectangle(rx, ry, rw, rh);

        float cx = getCenterX();
        float cy = getCenterY();
        if (r.contains(cx, cy)) {
            return false;
        }

        Point p1 = getStartPoint();
        Point p2 = getEndPoint();

        return !r.intersectsLine(cx, cy, p1.getX(), p1.getY())
                && !r.intersectsLine(cx, cy, p2.getX(), p2.getY());
    }

    @Override
    public boolean contains(Rectangle rect) {
        return contains(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    public boolean intersects(float rx, float ry, float rw, float rh) {

        if (isEmpty() || rw <= 0.0 || rh <= 0.0) {
            return false;
        }

        // Check: Does arc contain rectangle's points
        if (contains(rx, ry) || contains(rx + rw, ry) || contains(rx, ry + rh)
                || contains(rx + rw, ry + rh)) {
            return true;
        }

        float cx = getCenterX();
        float cy = getCenterY();
        Point p1 = getStartPoint();
        Point p2 = getEndPoint();
        Rectangle r = new Rectangle(rx, ry, rw, rh);

        // Check: Does rectangle contain arc's points
        if (r.contains(p1) || r.contains(p2) || (type == PIE && r.contains(cx, cy))) {
            return true;
        }

        if (type == PIE) {
            if (r.intersectsLine(p1.getX(), p1.getY(), cx, cy) ||
                r.intersectsLine(p2.getX(), p2.getY(), cx, cy))
            {
                return true;
            }
        } else {
            if (r.intersectsLine(p1.getX(), p1.getY(), p2.getX(), p2.getY())) {
                return true;
            }
        }

        // Nearest rectangle point
        float nx = cx < rx ? rx : Math.min(cx, rx + rw);
        float ny = cy < ry ? ry : Math.min(cy, ry + rh);
        return contains(nx, ny);
    }

    public PathIterator getPathIterator(AffineTransform at) {
        return new Iterator(this, at);
    }

    @Override
    public Arc clone() {
        try {
            return (Arc) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new Arc(this);
        }
    }

    @Override
    public void from(Object src) {
        setArc((Arc) src);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Arc arc = (Arc) o;

        if (Float.compare(arc.getX(), getX()) != 0) return false;
        if (Float.compare(arc.getY(), getY()) != 0) return false;
        if (Float.compare(arc.getWidth(), getWidth()) != 0) return false;
        if (Float.compare(arc.getHeight(), getHeight()) != 0) return false;
        if (Float.compare(arc.start, start) != 0) return false;
        if (Float.compare(arc.extent, extent) != 0) return false;
        return type == arc.type;
    }

    @Override
    public int hashCode() {
        int result = (getX() != +0.0f ? Float.floatToIntBits(getX()) : 0);
        result = 31 * result + (getY() != +0.0f ? Float.floatToIntBits(getY()) : 0);
        result = 31 * result + (getWidth() != +0.0f ? Float.floatToIntBits(getWidth()) : 0);
        result = 31 * result + (getHeight() != +0.0f ? Float.floatToIntBits(getHeight()) : 0);
        result = 31 * result + (start != +0.0f ? Float.floatToIntBits(start) : 0);
        result = 31 * result + (extent != +0.0f ? Float.floatToIntBits(extent) : 0);
        result = 31 * result + type;
        return result;
    }

}

