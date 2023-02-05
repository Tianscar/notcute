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

import static io.notcute.g2d.geom.PathIterator.SegmentType.*;
import static io.notcute.g2d.geom.PathIterator.WindingRule.NON_ZERO;
import static io.notcute.g2d.geom.Rectangle.OutCode.*;

public class Rectangle implements RectangularShape, Resetable, SwapCloneable {

    public static final class OutCode {
        private OutCode() {
            throw new UnsupportedOperationException();
        }
        public static final int LEFT   = 1;
        public static final int TOP    = 2;
        public static final int RIGHT  = 4;
        public static final int BOTTOM = 8;
    }

    private float x;
    private float y;
    private float width;
    private float height;

    @Override
    public void reset() {
        x = y = width = height = 0;
    }

    public Rectangle() {
    }

    public Rectangle(Rectangle r) {
        this(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    public Rectangle(float x, float y, float width, float height) {
        setRect(x, y, width, height);
    }

    @Override
    public String toString() {
        return getClass().getName() + '{' +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
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

    @Override
    public boolean isEmpty() {
        return width <= 0.0 || height <= 0.0;
    }

    /*
     * Rectangle2D path iterator
     */
    static class Iterator implements PathIterator {

        /**
         * The x coordinate of left-upper rectangle corner
         */
        float x;

        /**
         * The y coordinate of left-upper rectangle corner
         */
        float y;


        /**
         * The width of rectangle
         */
        float width;

        /**
         * The height of rectangle
         */
        float height;

        /**
         * The path iterator transformation
         */
        AffineTransform t;

        /**
         * The current segmenet index
         */
        int index;

        /**
         * Constructs a new Rectangle2D.Iterator for given rectangle and transformation
         * @param r - the source Rectangle2D object
         * @param at - the AffineTransform object to apply rectangle path
         */
        Iterator(Rectangle r, AffineTransform at) {
            this.x = r.getX();
            this.y = r.getY();
            this.width = r.getWidth();
            this.height = r.getHeight();
            this.t = at;
            if (width < 0.0 || height < 0.0) {
                index = 6;
            }
        }

        public int getWindingRule() {
            return NON_ZERO;
        }

        @Override
        public boolean hasNext() {
            return index <= 5;
        }

        public void next() {
            index++;
        }

        public int currentSegment(float[] coords) {
            if (!hasNext()) {
                throw new NoSuchElementException("Iterator out of bounds");
            }
            if (index == 5) {
                return CLOSE;
            }
            int type;
            if (index == 0) {
                type = MOVE_TO;
                coords[0] = x;
                coords[1] = y;
            } else {
                type = LINE_TO;
                switch(index) {
                case 1:
                    coords[0] = x + width;
                    coords[1] = y;
                    break;
                case 2:
                    coords[0] = x + width;
                    coords[1] = y + height;
                    break;
                case 3:
                    coords[0] = x;
                    coords[1] = y + height;
                    break;
                case 4:
                    coords[0] = x;
                    coords[1] = y;
                    break;
                }
            }
            if (t != null) {
                t.transform(coords, 0, coords, 0, 1);
            }
            return type;
        }

    }

    public int outcode(float px, float py) {
        int code = 0;

        if (width <= 0.0) {
            code |= LEFT | RIGHT;
        } else
        if (px < x) {
            code |= LEFT;
        } else
        if (px > x + width) {
            code |= RIGHT;
        }

        if (height <= 0.0) {
            code |= TOP | BOTTOM;
        } else
        if (py < y) {
            code |= TOP;
        } else
        if (py > y + height) {
            code |= BOTTOM;
        }

        return code;
    }

    @Override
    public void getBounds(Rectangle rectangle) {
        rectangle.setRect(x, y, width, height);
    }

    public Rectangle createIntersection(Rectangle r) {
        Rectangle dst = new Rectangle();
        Rectangle.intersect(this, r, dst);
        return dst;
    }

    public Rectangle createUnion(Rectangle r) {
        Rectangle dest = new Rectangle();
        Rectangle.union(this, r, dest);
        return dest;
    }

    @Override
    public void setRect(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean intersectsLine(float x1, float y1, float x2, float y2) {
        float rx1 = getX();
        float ry1 = getY();
        float rx2 = rx1 + getWidth();
        float ry2 = ry1 + getHeight();
        return
            (rx1 <= x1 && x1 <= rx2 && ry1 <= y1 && y1 <= ry2) ||
            (rx1 <= x2 && x2 <= rx2 && ry1 <= y2 && y2 <= ry2) ||
            Line.linesIntersect(rx1, ry1, rx2, ry2, x1, y1, x2, y2) ||
            Line.linesIntersect(rx2, ry1, rx1, ry2, x1, y1, x2, y2);
    }

    public boolean intersectsLine(Line l) {
        return intersectsLine(l.getX1(), l.getY1(), l.getX2(), l.getY2());
    }

    public int outcode(Point p) {
        return outcode(p.getX(), p.getY());
    }

    public boolean contains(float x, float y) {
        if (isEmpty()) {
            return false;
        }

        float x1 = getX();
        float y1 = getY();
        float x2 = x1 + getWidth();
        float y2 = y1 + getHeight();

        return
            x1 <= x && x < x2 &&
            y1 <= y && y < y2;
    }

    public boolean intersects(float x, float y, float width, float height) {
        if (isEmpty() || width <= 0.0 || height <= 0.0) {
            return false;
        }

        float x1 = getX();
        float y1 = getY();
        float x2 = x1 + getWidth();
        float y2 = y1 + getHeight();

        return
            x + width > x1 && x < x2 &&
            y + height > y1 && y < y2;
    }

    public boolean contains(float x, float y, float width, float height) {
        if (isEmpty() || width <= 0.0 || height <= 0.0) {
            return false;
        }

        float x1 = getX();
        float y1 = getY();
        float x2 = x1 + getWidth();
        float y2 = y1 + getHeight();

        return
            x1 <= x && x + width <= x2 &&
            y1 <= y && y + height <= y2;
    }

    public static void intersect(Rectangle src1, Rectangle src2, Rectangle dst) {
        float x1 = Math.max(src1.getLeft(), src2.getLeft());
        float y1 = Math.max(src1.getTop(), src2.getTop());
        float x2 = Math.min(src1.getRight(), src2.getRight());
        float y2 = Math.min(src1.getBottom(), src2.getBottom());
        dst.setRect(x1, y1, x2 - x1, y2 - y1);
    }

    public static void union(Rectangle src1, Rectangle src2, Rectangle dst) {
        float x1 = Math.min(src1.getLeft(), src2.getLeft());
        float y1 = Math.min(src1.getTop(), src2.getTop());
        float x2 = Math.max(src1.getRight(), src2.getRight());
        float y2 = Math.max(src1.getBottom(), src2.getBottom());
        dst.setRect(x1, y1, x2 - x1, y2 - y1);
    }

    public void add(float x, float y) {
        float x1 = Math.min(getLeft(), x);
        float y1 = Math.min(getTop(), y);
        float x2 = Math.max(getRight(), x);
        float y2 = Math.max(getBottom(), y);
        setRect(x1, y1, x2 - x1, y2 - y1);
    }

    public void add(Point p) {
        add(p.getX(), p.getY());
    }

    public void add(Rectangle r) {
        union(this, r, this);
    }

    public PathIterator getPathIterator(AffineTransform t) {
        return new Iterator(this, t);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform t, float flatness) {
        return new Iterator(this, t);
    }

    @Override
    public Rectangle clone() {
        try {
            return (Rectangle) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new Rectangle(this);
        }
    }

    @Override
    public void from(Object src) {
        setRect((Rectangle) src);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rectangle rectangle = (Rectangle) o;

        if (Float.compare(rectangle.getX(), getX()) != 0) return false;
        if (Float.compare(rectangle.getY(), getY()) != 0) return false;
        if (Float.compare(rectangle.getWidth(), getWidth()) != 0) return false;
        return Float.compare(rectangle.getHeight(), getHeight()) == 0;
    }

    @Override
    public int hashCode() {
        int result = (getX() != +0.0f ? Float.floatToIntBits(getX()) : 0);
        result = 31 * result + (getY() != +0.0f ? Float.floatToIntBits(getY()) : 0);
        result = 31 * result + (getWidth() != +0.0f ? Float.floatToIntBits(getWidth()) : 0);
        result = 31 * result + (getHeight() != +0.0f ? Float.floatToIntBits(getHeight()) : 0);
        return result;
    }

}

