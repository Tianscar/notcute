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
  @author Alexey A. Petrenko
 */
package io.notcute.g2d.geom;

import io.notcute.g2d.AffineTransform;
import io.notcute.g2d.Point;
import io.notcute.util.Cloneable;

/**
 * Shape
 *
 */
public interface Shape extends Cloneable {

    boolean contains(float x, float y);
    boolean contains(float x, float y, float w, float h);

    default boolean contains(Point point) {
        return contains(point.getX(), point.getY());
    }
    default boolean contains(Rectangle rectangle) {
        return contains(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }

    void getBounds(Rectangle rectangle);
    default Rectangle getBounds() {
        Rectangle rectangle = new Rectangle();
        getBounds(rectangle);
        return rectangle;
    }

    default PathIterator getPathIterator() {
        return getPathIterator(null);
    }
    PathIterator getPathIterator(final AffineTransform at);
    default PathIterator getPathIterator(final AffineTransform at, final float flatness) {
        return new FlatteningPathIterator(getPathIterator(at), flatness);
    }

    boolean intersects(float x, float y, float w, float h);
    default boolean intersects(Rectangle r) {
        return intersects(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

}
