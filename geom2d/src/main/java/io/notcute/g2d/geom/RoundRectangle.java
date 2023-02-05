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

public class RoundRectangle implements RectangularShape, Resetable, SwapCloneable {

    private float x;
    private float y;
    private float width;
    private float height;
    private float arcwidth;
    private float archeight;

    @Override
    public void reset() {
        x = y = width = height = arcwidth = archeight = 0.0f;
    }

    public RoundRectangle() {
    }

    public RoundRectangle(RoundRectangle rr) {
        this(rr.getX(), rr.getY(), rr.getWidth(), rr.getHeight(), rr.getArcWidth(), rr.getArcHeight());
    }

    public RoundRectangle(float x, float y, float width, float height, float arcwidth, float archeight) {
        setRoundRect(x, y, width, height, arcwidth, archeight);
    }

    @Override
    public String toString() {
        return getClass().getName() + '{' +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ", arcwidth=" + arcwidth +
                ", archeight=" + archeight +
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
    public void getBounds(Rectangle rectangle) {
        rectangle.setRect(x, y, width, height);
    }

    /*
     * RoundRectangle2D path iterator 
     */
    static class Iterator implements PathIterator {

        /*
         * Path for round corners generated the same way as Ellipse2D
         */

        /**
         * The coefficient to calculate control points of Bezier curves
         */
        static final double u = 0.5 - 2.0 / 3.0 * (Math.sqrt(2.0) - 1.0);
        static final float uf = (float) u;

        /**
         * The points coordinates calculation table.
         */
        static final float[][] points = {
                { 0.0f,  0.5f, 0.0f,  0.0f }, // MOVETO
                { 1.0f, -0.5f, 0.0f,  0.0f }, // LINETO
                { 1.0f,   -uf, 0.0f,  0.0f,   // CUBICTO
                  1.0f,  0.0f, 0.0f,    uf,
                  1.0f,  0.0f, 0.0f,  0.5f },
                { 1.0f,  0.0f, 1.0f, -0.5f }, // LINETO
                { 1.0f,  0.0f, 1.0f,   -uf,   // CUBICTO
                  1.0f,   -uf, 1.0f,  0.0f,
                  1.0f, -0.5f, 1.0f,  0.0f },
                { 0.0f,  0.5f, 1.0f,  0.0f }, // LINETO
                { 0.0f,    uf, 1.0f,  0.0f,   // CUBICTO
                  0.0f,  0.0f, 1.0f,   -uf,
                  0.0f,  0.0f, 1.0f, -0.5f },
                { 0.0f,  0.0f, 0.0f,  0.5f }, // LINETO
                { 0.0f,  0.0f, 0.0f,    uf,   // CUBICTO
                  0.0f,    uf, 0.0f,  0.0f,
                  0.0f,  0.5f, 0.0f,  0.0f } };

        /**
         * The segment types correspond to points array
         */
        static final int[] types = {
                MOVE_TO,
                LINE_TO,
                CUBIC_TO,
                LINE_TO,
                CUBIC_TO,
                LINE_TO,
                CUBIC_TO,
                LINE_TO,
                CUBIC_TO};

        /**
         * The x coordinate of left-upper corner of the round rectangle bounds
         */
        float x;
        
        /**
         * The y coordinate of left-upper corner of the round rectangle bounds 
         */
        float y;
        
        /**
         * The width of the round rectangle bounds 
         */
        float width;
        
        /**
         * The height of the round rectangle bounds 
         */
        float height;
        
        /**
         * The width of arc corners of the round rectangle 
         */
        float aw;
        
        /**
         * The height of arc corners of the round rectangle 
         */
        float ah;

        /**
         * The path iterator transformation
         */
        AffineTransform t;

        /**
         * The current segmenet index
         */
        int index;

        /**
         * Constructs a new RoundRectangle2D.Iterator for given round rectangle and transformation.
         * @param rr - the source RoundRectangle2D object
         * @param at - the AffineTransform object to apply rectangle path
         */
        Iterator(RoundRectangle rr, AffineTransform at) {
            this.x = rr.getX();
            this.y = rr.getY();
            this.width = rr.getWidth();
            this.height = rr.getHeight();
            this.aw = Math.min(width, rr.getArcWidth());
            this.ah = Math.min(height, rr.getArcHeight());
            this.t = at;
            if (width < 0.0 || height < 0.0 || aw < 0.0 || ah < 0.0) {
                index = points.length;
            }
        }

        public int getWindingRule() {
            return NON_ZERO;
        }

        @Override
        public boolean hasNext() {
            return index <= points.length;
        }

        public void next() {
            index++;
        }

        public int currentSegment(float[] coords) {
            if (!hasNext()) {
                throw new NoSuchElementException("Iterator out of bounds");
            }
            if (index == points.length) {
                return CLOSE;
            }
            int j = 0;
            float[] p = points[index];
            for (int i = 0; i < p.length; i += 4) {
                coords[j++] = x + p[i + 0] * width + p[i + 1] * aw;
                coords[j++] = y + p[i + 2] * height + p[i + 3] * ah;
            }
            if (t != null) {
                t.transform(coords, 0, coords, 0, j / 2);
            }
            return types[index];
        }

    }

    public float getArcWidth() {
        return arcwidth;
    }

    public float getArcHeight() {
        return archeight;
    }

    @Override
    public boolean isEmpty() {
        return width <= 0.0 || height <= 0.0;
    }

    public void setRoundRect(float x, float y, float width, float height, float arcwidth, float archeight) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.arcwidth = arcwidth;
        this.archeight = archeight;
    }

    public void setRoundRect(RoundRectangle rr) {
        this.x = rr.getX();
        this.y = rr.getY();
        this.width = rr.getWidth();
        this.height = rr.getHeight();
        this.arcwidth = rr.getArcWidth();
        this.archeight = rr.getArcHeight();
    }

    @Override
    public void setRect(float x, float y, float width, float height) {
        setRoundRect(x, y, width, height, getArcWidth(), getArcHeight());
    }

    public boolean contains(float px, float py) {
        if (isEmpty()) {
            return false;
        }

        float rx1 = getX();
        float ry1 = getY();
        float rx2 = rx1 + getWidth();
        float ry2 = ry1 + getHeight();

        if (px < rx1 || px >= rx2 || py < ry1 || py >= ry2) {
            return false;
        }

        float aw = getArcWidth() / 2.0f;
        float ah = getArcHeight() / 2.0f;

        float cx, cy;

        if (px < rx1 + aw) {
            cx = rx1 + aw;
        } else
            if (px > rx2 - aw) {
                cx = rx2 - aw;
            } else {
                return true;
            }

        if (py < ry1 + ah) {
            cy = ry1 + ah;
        } else
            if (py > ry2 - ah) {
                cy = ry2 - ah;
            } else {
                return true;
            }

        px = (px - cx) / aw;
        py = (py - cy) / ah;
        return px * px + py * py <= 1.0;
    }

    public boolean intersects(float rx, float ry, float rw, float rh) {
        if (isEmpty() || rw <= 0.0 || rh <= 0.0) {
            return false;
        }

        float x1 = getX();
        float y1 = getY();
        float x2 = x1 + getWidth();
        float y2 = y1 + getHeight();

        float rx1 = rx;
        float ry1 = ry;
        float rx2 = rx + rw;
        float ry2 = ry + rh;

        if (rx2 < x1 || x2 < rx1 || ry2 < y1 || y2 < ry1) {
            return false;
        }

        float cx = (x1 + x2) / 2.0f;
        float cy = (y1 + y2) / 2.0f;

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
    public RoundRectangle clone() {
        try {
            return (RoundRectangle) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new RoundRectangle(this);
        }
    }

    @Override
    public void from(Object src) {
        setRoundRect((RoundRectangle) src);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoundRectangle that = (RoundRectangle) o;

        if (Float.compare(that.getX(), getX()) != 0) return false;
        if (Float.compare(that.getY(), getY()) != 0) return false;
        if (Float.compare(that.getWidth(), getWidth()) != 0) return false;
        if (Float.compare(that.getHeight(), getHeight()) != 0) return false;
        if (Float.compare(that.arcwidth, arcwidth) != 0) return false;
        return Float.compare(that.archeight, archeight) == 0;
    }

    @Override
    public int hashCode() {
        int result = (getX() != +0.0f ? Float.floatToIntBits(getX()) : 0);
        result = 31 * result + (getY() != +0.0f ? Float.floatToIntBits(getY()) : 0);
        result = 31 * result + (getWidth() != +0.0f ? Float.floatToIntBits(getWidth()) : 0);
        result = 31 * result + (getHeight() != +0.0f ? Float.floatToIntBits(getHeight()) : 0);
        result = 31 * result + (arcwidth != +0.0f ? Float.floatToIntBits(arcwidth) : 0);
        result = 31 * result + (archeight != +0.0f ? Float.floatToIntBits(archeight) : 0);
        return result;
    }

}

