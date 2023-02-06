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
import io.notcute.util.Resetable;
import io.notcute.util.SwapCloneable;

import java.util.NoSuchElementException;

import static io.notcute.g2d.geom.PathIterator.SegmentType.*;
import static io.notcute.g2d.geom.PathIterator.WindingRule.NON_ZERO;

public class Ellipse implements RectangularShape, Resetable, SwapCloneable {

    private float x;
    private float y;
    private float width;
    private float height;

    @Override
    public void reset() {
        x = y = width = height = 0.0f;
    }

    public Ellipse() {
    }

    public Ellipse(Ellipse e) {
        this(e.getX(), e.getY(), e.getWidth(), e.getHeight());
    }

    public Ellipse(float x, float y, float width, float height) {
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
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public boolean isEmpty() {
        return width <= 0.0 || height <= 0.0;
    }

    @Override
    public void setRect(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setEllipse(Ellipse ellipse) {
        setRect(ellipse.getX(), ellipse.getY(), ellipse.getWidth(), ellipse.getHeight());
    }

    @Override
    public void getBounds(Rectangle rectangle) {
        rectangle.setRect(x, y, width, height);
    }

    /*
     * Ellipse2D path iterator 
     */
    static class Iterator implements PathIterator {

        /*
         * Ellipse is subdivided into four quarters by x and y axis. Each part approximated by
         * cubic Bezier curve. Arc in first quarter is started in (a, 0) and finished in (0, b) points.
         * Control points for cubic curve wiil be (a, 0), (a, m), (n, b) and (0, b) where n and m are
         * calculated based on requirement Bezier curve in point 0.5 should lay on the arc.
         */

        /**
         * The coefficient to calculate control points of Bezier curves
         */
        static final double u = 2.0 / 3.0 * (Math.sqrt(2.0) - 1.0);
        static final float uf = (float) u;

        /**
         * The points coordinates calculation table.
         */
        final float[][] points = {
                {1.0f, 0.5f + uf, 0.5f + uf, 1.0f, 0.5f, 1.0f},
                {0.5f - uf, 1.0f, 0.0f, 0.5f + uf, 0.0f, 0.5f},
                {0.0f, 0.5f - uf, 0.5f - uf, 0.0f, 0.5f, 0.0f},
                {0.5f + uf, 0.0f, 1.0f, 0.5f - uf, 1.0f, 0.5f}
        };

        /**
         * The x coordinate of left-upper corner of the ellipse bounds
         */
        float x;

        /**
         * The y coordinate of left-upper corner of the ellipse bounds
         */
        float y;

        /**
         * The width of the ellipse bounds
         */
        float width;

        /**
         * The height of the ellipse bounds
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
         * Constructs a new Ellipse2D.Iterator for given ellipse and transformation
         *
         * @param e - the source Ellipse2D object
         * @param t - the AffineTransform object to apply rectangle path
         */
        Iterator(Ellipse e, AffineTransform t) {
            this.x = e.getX();
            this.y = e.getY();
            this.width = e.getWidth();
            this.height = e.getHeight();
            this.t = t;
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
            int count;
            if (index == 0) {
                type = MOVE_TO;
                count = 1;
                float[] p = points[3];
                coords[0] = x + p[4] * width;
                coords[1] = y + p[5] * height;
            } else {
                type = CUBIC_TO;
                count = 3;
                float[] p = points[index - 1];
                int j = 0;
                for (int i = 0; i < 3; i++) {
                    coords[j] = x + p[j++] * width;
                    coords[j] = y + p[j++] * height;
                }
            }
            if (t != null) {
                t.transform(coords, 0, coords, 0, count);
            }
            return type;
        }

    }

    public boolean contains(float px, float py) {
        if (isEmpty()) {
            return false;
        }

        float a = (px - getX()) / getWidth() - 0.5f;
        float b = (py - getY()) / getHeight() - 0.5f;

        return a * a + b * b < 0.25f;
    }

    public boolean intersects(float rx, float ry, float rw, float rh) {
        if (isEmpty() || rw <= 0.0 || rh <= 0.0) {
            return false;
        }

        float cx = getX() + getWidth() / 2.0f;
        float cy = getY() + getHeight() / 2.0f;

        float rx1 = rx;
        float ry1 = ry;
        float rx2 = rx + rw;
        float ry2 = ry + rh;

        float nx = cx < rx1 ? rx1 : Math.min(cx, rx2);
        float ny = cy < ry1 ? ry1 : Math.min(cy, ry2);

        return contains(nx, ny);
    }

    public boolean contains(float rx, float ry, float rw, float rh) {
        if (isEmpty() || rw <= 0.0 || rh <= 0.0) {
            return false;
        }

        float rx1 = rx;
        float ry1 = ry;
        float rx2 = rx + rw;
        float ry2 = ry + rh;

        return
            contains(rx1, ry1) &&
            contains(rx2, ry1) &&
            contains(rx2, ry2) &&
            contains(rx1, ry2);
    }

    public PathIterator getPathIterator(AffineTransform at) {
        return new Iterator(this, at);
    }

    @Override
    public Ellipse clone() {
        try {
            return (Ellipse) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new Ellipse(this);
        }
    }

    @Override
    public void from(Object src) {
        setEllipse((Ellipse) src);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ellipse ellipse = (Ellipse) o;

        if (Float.compare(ellipse.getX(), getX()) != 0) return false;
        if (Float.compare(ellipse.getY(), getY()) != 0) return false;
        if (Float.compare(ellipse.getWidth(), getWidth()) != 0) return false;
        return Float.compare(ellipse.getHeight(), getHeight()) == 0;
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

