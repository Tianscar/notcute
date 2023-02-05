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

import java.util.NoSuchElementException;

import static io.notcute.g2d.geom.PathIterator.SegmentType.*;

public class FlatteningPathIterator implements PathIterator {

    /**
     * The default points buffer size
     */
    private static final int BUFFER_SIZE = 16;
    
    /**
     * The default curve subdivision limit
     */
    private static final int BUFFER_LIMIT = 16;

    /**
     * The points buffer capacity
     */
    private static final int BUFFER_CAPACITY = 16;
    
    /**
     * The type of current segment to be flat
     */
    int bufType;
    
    /**
     * The curve subdivision limit
     */
    int bufLimit;
    
    /**
     * The current points buffer size
     */
    int bufSize;
    
    /**
     * The inner cursor position in points buffer 
     */
    int bufIndex;
    
    /**
     * The current subdivision count
     */
    int bufSubdiv;

    /**
     * The points buffer 
     */
    float buf[];
    
    /**
     * The indicator of empty points buffer
     */
    boolean bufEmpty = true;
    
    /**
     * The source PathIterator
     */
    PathIterator p;
    
    /**
     * The flatness of new path 
     */
    float flatness;
    
    /**
     * The square of flatness
     */
    float flatness2;
    
    /**
     * The x coordinate of previous path segment
     */
    float px;

    /**
     * The y coordinate of previous path segment
     */
    float py;
    
    /**
     * The tamporary buffer for getting points from PathIterator
     */
    float coords[] = new float[6];

    public FlatteningPathIterator(PathIterator path, float flatness) {
        this(path, flatness, BUFFER_LIMIT);
    }

    public FlatteningPathIterator(PathIterator path, float flatness, int limit) {
        if (flatness < 0.0) {
            throw new IllegalArgumentException("Flatness is less then zero");
        }
        if (limit < 0) {
            throw new IllegalArgumentException("Limit is less then zero");
        }
        if (path == null) {
            throw new NullPointerException("Path is null");
        }
        this.p = path;
        this.flatness = flatness;
        this.flatness2 = flatness * flatness;
        this.bufLimit = limit;
        this.bufSize = Math.min(bufLimit, BUFFER_SIZE);
        this.buf = new float[bufSize];
        this.bufIndex = bufSize;
    }

    public float getFlatness() {
        return flatness;
    }

    public int getRecursionLimit() {
        return bufLimit;
    }

    public int getWindingRule() {
        return p.getWindingRule();
    }

    @Override
    public boolean hasNext() {
        return !bufEmpty || p.hasNext();
    }

    /**
     * Calculates flat path points for current segment of the source shape.
     * 
     * Line segment is flat by itself. Flatness of quad and cubic curves evaluated by getFlatnessSq() method.  
     * Curves subdivided until current flatness is bigger than user defined and subdivision limit isn't exhausted.
     * Single source segment translated to series of buffer points. The less flatness the bigger serries.
     * Every currentSegment() call extract one point from the buffer. When series completed evaluate() takes next source shape segment.        
     */
    void evaluate() {
        if (bufEmpty) {
            bufType = p.currentSegment(coords);
        }

        switch (bufType) {
        case MOVE_TO:
        case LINE_TO:
            px = coords[0];
            py = coords[1];
            break;
        case QUAD_TO:
            if (bufEmpty) {
                bufIndex -= 6;
                buf[bufIndex + 0] = px;
                buf[bufIndex + 1] = py;
                System.arraycopy(coords, 0, buf, bufIndex + 2, 4);
                bufSubdiv = 0;
            }

            while (bufSubdiv < bufLimit) {
                if (QuadCurve.getFlatnessSq(buf, bufIndex) < flatness2) {
                    break;
                }

                // Realloc buffer
                if (bufIndex <= 4) {
                    float tmp[] = new float[bufSize + BUFFER_CAPACITY];
                    System.arraycopy(
                            buf, bufIndex,
                            tmp, bufIndex + BUFFER_CAPACITY,
                            bufSize - bufIndex);
                    buf = tmp;
                    bufSize += BUFFER_CAPACITY;
                    bufIndex += BUFFER_CAPACITY;
                }

                QuadCurve.subdivide(buf, bufIndex, buf, bufIndex - 4, buf, bufIndex);

                bufIndex -= 4;
                bufSubdiv++;
            }

            bufIndex += 4;
            px = buf[bufIndex];
            py = buf[bufIndex + 1];

            bufEmpty = (bufIndex == bufSize - 2);
            if (bufEmpty) {
                bufIndex = bufSize;
                bufType = LINE_TO;
            } 
            break;
        case CUBIC_TO:
            if (bufEmpty) {
                bufIndex -= 8;
                buf[bufIndex + 0] = px;
                buf[bufIndex + 1] = py;
                System.arraycopy(coords, 0, buf, bufIndex + 2, 6);
                bufSubdiv = 0;
            }

            while (bufSubdiv < bufLimit) {
                if (CubicCurve.getFlatnessSq(buf, bufIndex) < flatness2) {
                    break;
                }

                // Realloc buffer
                if (bufIndex <= 6) {
                    float tmp[] = new float[bufSize + BUFFER_CAPACITY];
                    System.arraycopy(
                            buf, bufIndex,
                            tmp, bufIndex + BUFFER_CAPACITY,
                            bufSize - bufIndex);
                    buf = tmp;
                    bufSize += BUFFER_CAPACITY;
                    bufIndex += BUFFER_CAPACITY;
                }

                CubicCurve.subdivide(buf, bufIndex, buf, bufIndex - 6, buf, bufIndex);

                bufIndex -= 6;
                bufSubdiv++;
            }

            bufIndex += 6;
            px = buf[bufIndex];
            py = buf[bufIndex + 1];

            bufEmpty = (bufIndex == bufSize - 2);
            if (bufEmpty) {
                bufIndex = bufSize;
                bufType = LINE_TO;
            } 
            break;
        }

    }

    public void next() {
        if (bufEmpty) {
            p.next();
        }
    }

    public int currentSegment(float[] coords) {
        if (!hasNext()) {
            throw new NoSuchElementException("Iterator out of bounds");
        }
        evaluate();
        int type = bufType;
        if (type != CLOSE) {
            coords[0] = px;
            coords[1] = py;
            if (type != MOVE_TO) {
                type = LINE_TO;
            }
        }
        return type;
    }
}

