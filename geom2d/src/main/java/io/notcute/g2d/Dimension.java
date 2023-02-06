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

import io.notcute.util.Resetable;
import io.notcute.util.SwapCloneable;

public class Dimension implements Resetable, SwapCloneable {

    private float width;
    private float height;

    @Override
    public void reset() {
        width = height = 0.0f;
    }

    public Dimension() {
    }

    public Dimension(Dimension dimension) {
        this(dimension.getWidth(), dimension.getHeight());
    }

    public Dimension(float width, float height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return getClass().getName() + '{' +
                "width=" + width +
                ", height=" + height +
                '}';
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void setSize(Dimension dimension) {
        setSize(dimension.getWidth(), dimension.getHeight());
    }

    @Override
    public Dimension clone() {
        try {
            return (Dimension) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new Dimension(this);
        }
    }

    @Override
    public void from(Object src) {
        setSize((Dimension) src);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dimension dimension = (Dimension) o;

        if (Float.compare(dimension.getWidth(), getWidth()) != 0) return false;
        return Float.compare(dimension.getHeight(), getHeight()) == 0;
    }

    @Override
    public int hashCode() {
        int result = (getWidth() != +0.0f ? Float.floatToIntBits(getWidth()) : 0);
        result = 31 * result + (getHeight() != +0.0f ? Float.floatToIntBits(getHeight()) : 0);
        return result;
    }

}

