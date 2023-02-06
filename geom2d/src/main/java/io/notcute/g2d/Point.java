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

import io.notcute.util.MathUtils;
import io.notcute.util.Resetable;
import io.notcute.util.SwapCloneable;

public class Point implements Resetable, SwapCloneable {

    private float x;
    private float y;

    @Override
    public void reset() {
        x = y = 0.0f;
    }

    public Point() {
    }

    public Point(Point point) {
        this(point.getX(), point.getY());
    }

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return getClass().getName() + '{' +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setLocation(Point point) {
        setLocation(point.getX(), point.getY());
    }

    public static float distanceSq(float x1, float y1, float x2, float y2) {
        x2 -= x1;
        y2 -= y1;
        return x2 * x2 + y2 * y2;
    }

    public float distanceSq(float px, float py) {
        return Point.distanceSq(getX(), getY(), px, py);
    }

    public float distanceSq(Point p) {
        return Point.distanceSq(getX(), getY(), p.getX(), p.getY());
    }

    public static float distance(float x1, float y1, float x2, float y2) {
        return MathUtils.sqrt(distanceSq(x1, y1, x2, y2));
    }

    public float distance(float px, float py) {
        return MathUtils.sqrt(distanceSq(px, py));
    }

    public float distance(Point p) {
        return MathUtils.sqrt(distanceSq(p));
    }

    @Override
    public Point clone() {
        try {
            return (Point) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new Point(this);
        }
    }

    @Override
    public void from(Object src) {
        setLocation((Point) src);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (Float.compare(point.getX(), getX()) != 0) return false;
        return Float.compare(point.getY(), getY()) == 0;
    }

    @Override
    public int hashCode() {
        int result = (getX() != +0.0f ? Float.floatToIntBits(getX()) : 0);
        result = 31 * result + (getY() != +0.0f ? Float.floatToIntBits(getY()) : 0);
        return result;
    }

}

