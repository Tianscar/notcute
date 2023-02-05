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

public interface RectangularShape extends Shape {

    float getX();

    float getY();

    float getWidth();

    float getHeight();

    boolean isEmpty();

    void setRect(float x, float y, float w, float h);

    default float getLeft() {
        return getX();
    }

    default float getTop() {
        return getY();
    }

    default float getRight() {
        return getX() + getWidth();
    }

    default float getBottom() {
        return getY() + getHeight();
    }

    default float getCenterX() {
        return getX() + getWidth() / 2.0f;
    }

    default float getCenterY() {
        return getY() + getHeight() / 2.0f;
    }

    default Rectangle getRect() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    default void setRect(Point loc, Dimension size) {
        setRect(loc.getX(), loc.getY(), size.getWidth(), size.getHeight());
    }

    default void setRect(Rectangle r) {
        setRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    default void setRectFromDiagonal(float x1, float y1, float x2, float y2) {
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
        setRect(rx, ry, rw, rh);
    }

    default void setRectFromDiagonal(Point p1, Point p2) {
        setRectFromDiagonal(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    default void setRectFromCenter(float centerX, float centerY, float cornerX, float cornerY) {
        float width = Math.abs(cornerX - centerX);
        float height = Math.abs(cornerY - centerY);
        setRect(centerX - width, centerY - height, width * 2.0f, height * 2.0f);
    }

    default void setRectFromCenter(Point center, Point corner) {
        setRectFromCenter(center.getX(), center.getY(), corner.getX(), corner.getY());
    }

    default boolean contains(Point point) {
        return contains(point.getX(), point.getY());
    }

    default boolean intersects(Rectangle rect) {
        return intersects(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    default boolean contains(Rectangle rect) {
        return contains(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    default PathIterator getPathIterator(AffineTransform t, float flatness) {
        return new FlatteningPathIterator(getPathIterator(t), flatness);
    }

}

