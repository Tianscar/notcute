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
package io.notcute.g2d.geom;

import io.notcute.g2d.AffineTransform;
import io.notcute.g2d.Point;
import io.notcute.util.ArrayUtils;
import io.notcute.util.Resetable;
import io.notcute.util.SwapCloneable;

import java.util.NoSuchElementException;
import java.util.Objects;

import static io.notcute.g2d.geom.PathIterator.SegmentType.*;
import static io.notcute.g2d.geom.PathIterator.WindingRule.EVEN_ODD;

public class Polygon implements Shape, Resetable, SwapCloneable {

	/**
     * the coordinates array of the shape vertices
     */
	private float[] coords = new float[20];
	
	/**
	 * the coordinates quantity
	 */
	private int coordsSize = 0;
	
	/**
	 * the rules array for the drawing of the shape edges
	 */
	private int[] rules = new int[10];
	
	/**
	 * the rules quantity
	 */
	private int rulesSize = 0;
	
	/**
	 * offsets[i] - index in array of coords and i - index in array of rules
	 */
	private int[] offsets = new int[10];
	
	/**
	 * the quantity of MOVETO rule occurences
	 */
	private int moveToCount = 0;
	
	/**
	 * true if the shape is polygon
	 */
	private boolean isPolygonal = true;

	public Polygon() {
	}

	public Polygon(Shape s) {
		if (s instanceof Polygon) {
			copy((Polygon) s, this);
			return;
		}
		float[] segmentCoords = new float[6];
		float lastMoveX = 0.0f;
		float lastMoveY = 0.0f;
		int rulesIndex = 0;
		int coordsIndex = 0;
		
		for (PathIterator pi = s.getPathIterator(null);
			 pi.hasNext(); pi.next()) {
			coords = adjustSize(coords, coordsIndex + 6);
			rules = adjustSize(rules, rulesIndex + 1);
			offsets = adjustSize(offsets, rulesIndex + 1);
			rules[rulesIndex] = pi.currentSegment(segmentCoords);
			offsets[rulesIndex] = coordsIndex;
			
			switch (rules[rulesIndex]) {
                case MOVE_TO:
                    coords[coordsIndex++] = segmentCoords[0];
                    coords[coordsIndex++] = segmentCoords[1];
                    lastMoveX = segmentCoords[0];
                    lastMoveY = segmentCoords[1];
                    ++moveToCount;
                    break;
                case LINE_TO:
                    if ((segmentCoords[0] != lastMoveX) || 
                    		(segmentCoords[1] != lastMoveY)) {
                        coords[coordsIndex++] = segmentCoords[0];
                        coords[coordsIndex++] = segmentCoords[1];
                    } else {
                        --rulesIndex;
                    }
                    break;
                case QUAD_TO:
                    System.arraycopy(segmentCoords, 0, coords, coordsIndex, 4);
                    coordsIndex += 4;
                    isPolygonal = false;
                    break;
                case CUBIC_TO:
                    System.arraycopy(segmentCoords, 0, coords, coordsIndex, 6);
                    coordsIndex += 6;
                    isPolygonal = false;
                    break;
                case CLOSE:
                    break;
            }
            ++rulesIndex;
		}
		
		if ((rulesIndex != 0) && 
				(rules[rulesIndex - 1] != CLOSE)) {
			rules[rulesIndex] = CLOSE;
			offsets[rulesIndex] = coordsSize;
		}
		
		rulesSize = rulesIndex;
		coordsSize = coordsIndex;
	}

	public boolean contains(float x, float y) {
        return !isEmpty() &&
                   containsExact(x, y) > 0;
    }

	public boolean contains(float x, float y, float width, float height) {
		int crossCount = Crossing.intersectPath(getPathIterator(null), x, y,
				width, height);
		return crossCount != Crossing.CROSSING &&
			       Crossing.isInsideEvenOdd(crossCount);
	}

	public boolean contains(Point p) {
		return contains(p.getX(), p.getY());
	}

	public boolean contains(Rectangle r) {
		return contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Polygon polygon = (Polygon) o;
		
		Polygon clone = clone();
		clone.subtract(polygon);
		return clone.isEmpty();
	}

	public boolean intersects(float x, float y, float width, float height) {
		if ((width <= 0.0) || (height <= 0.0)) {
			return false;
		} else if (!getBounds().intersects(x, y, width, height)) {
			return false;
		}
		
		int crossCount = Crossing.intersectShape(this, x, y, width, height);
		return Crossing.isInsideEvenOdd(crossCount);
	}

	public boolean intersects(Rectangle r) {
		return intersects(r.getX(), r.getY(), r.getWidth(), r.getHeight());
	}

	@Override
	public void getBounds(Rectangle rectangle) {
		Objects.requireNonNull(rectangle);
		float maxX = coords[0];
		float maxY = coords[1];
		float minX = coords[0];
		float minY = coords[1];

		for (int i = 0; i < coordsSize;) {
			minX = Math.min(minX, coords[i]);
			maxX = Math.max(maxX, coords[i++]);
			minY = Math.min(minY, coords[i]);
			maxY = Math.max(maxY, coords[i++]);
		}
		
		rectangle.setRect(minX, minY, maxX - minX, maxY - minY);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform t) {
		return new Iterator(this, t);
	}

	@Override
	public PathIterator getPathIterator(AffineTransform t, float flatness) {
		return new FlatteningPathIterator(getPathIterator(t), flatness);
	}
	
	public boolean isEmpty() {
		return (rulesSize == 0) && (coordsSize == 0);
	}

	public boolean isPolygonal() {
		return isPolygonal;
	}

	public boolean isRectangular() {
        return (isPolygonal) && (rulesSize <= 5) && (coordsSize <= 8) &&
               (coords[1] == coords[3]) && (coords[7] == coords[5]) &&
               (coords[0] == coords[6]) && (coords[2] == coords[4]);
    }

	public boolean isSingular() {
		return (moveToCount <= 1);
	}

	@Override
	public void reset() {
		coordsSize = 0;
		rulesSize = 0;
	}

	public void transform(AffineTransform t) {
		copy(new Polygon(t.createTransformedShape(this)), this);
	}

	public Polygon createTransformedArea(AffineTransform t) {
		return new Polygon(t.createTransformedShape(this));
	}

	@Override
	public Polygon clone() {
		try {
			Polygon clone = (Polygon) super.clone();
			copy(this, clone);
			return clone;
		}
		catch (CloneNotSupportedException e) {
			return new Polygon(this);
		}
	}

	@Override
	public void from(Object src) {
		copy((Polygon) src, this);
	}

	public void add(Polygon polygon) {
    	if (polygon == null || polygon.isEmpty()) {
    	    return;
    	} else if (isEmpty()) {
    	    copy(polygon, this);
    		return;
    	}

		if (isPolygonal() && polygon.isPolygonal()) {
			addPolygon(polygon);
		} else {
			addCurvePolygon(polygon);
		}
		
		if (getAreaBoundsSquare() < GeometryUtil.EPSILON) {
		    reset();
		}
	}
	   
	public void intersect(Polygon polygon) {
		if (polygon == null) {
		    return;
		} else if (isEmpty() || polygon.isEmpty()) {
		    reset();
			return;		
		}
		
		if (isPolygonal() && polygon.isPolygonal()) {
			intersectPolygon(polygon);
		} else {
			intersectCurvePolygon(polygon);
		}
		
		if (getAreaBoundsSquare() < GeometryUtil.EPSILON) {
		    reset();
		}
	}
	
	public void subtract(Polygon polygon) {
		if (polygon == null || isEmpty() || polygon.isEmpty()) {
		    return;
		}

		if (isPolygonal() && polygon.isPolygonal()) {
			subtractPolygon(polygon);
		} else {
			subtractCurvePolygon(polygon);
		}
		
		if (getAreaBoundsSquare() < GeometryUtil.EPSILON) {
		    reset();
		}
	}
	
 	public void exclusiveOr(Polygon polygon) {
		Polygon a = clone();
		a.intersect(polygon);
		add(polygon);
		subtract(a);
	}

	private void addCurvePolygon(Polygon polygon) {
		CurveCrossingHelper crossHelper = new CurveCrossingHelper(
	            new float[][] { coords, polygon.coords },
		        new int[] { coordsSize, polygon.coordsSize },
		        new int[][] { rules, polygon.rules },
				new int[] { rulesSize, polygon.rulesSize },
				new int[][] { offsets, polygon.offsets });
		IntersectPoint[] intersectPoints = crossHelper.findCrossing();

		if (intersectPoints.length == 0) {
			if (polygon.contains(getBounds())) {
				copy(polygon, this);
			} else if (!contains(polygon.getBounds())) {
				coords = adjustSize(coords, coordsSize + polygon.coordsSize);
				System.arraycopy(polygon.coords, 0, coords, coordsSize,
								 polygon.coordsSize);
				coordsSize += polygon.coordsSize;
				rules = adjustSize(rules, rulesSize + polygon.rulesSize);
				System.arraycopy(polygon.rules, 0, rules, rulesSize,
								 polygon.rulesSize);
				rulesSize += polygon.rulesSize;
				offsets = adjustSize(offsets, rulesSize + polygon.rulesSize);
				System.arraycopy(polygon.offsets, 0, offsets, rulesSize,
								 polygon.rulesSize);
			}
			
			return;
		}

        float[] resultCoords = new float[coordsSize + polygon.coordsSize +
                                                       intersectPoints.length];
        int[] resultRules = new int[rulesSize + polygon.rulesSize +
                                                       intersectPoints.length];
        int[] resultOffsets = new int[rulesSize + polygon.rulesSize +
                                                       intersectPoints.length];
        int resultCoordPos = 0;
        int resultRulesPos = 0;
        boolean isCurrentArea = true;

        IntersectPoint point = intersectPoints[0];
        resultRules[resultRulesPos] = MOVE_TO;
        resultOffsets[resultRulesPos++] = resultCoordPos;
        
        do {
        	resultCoords[resultCoordPos++] = point.getX();
            resultCoords[resultCoordPos++] = point.getY();
            int curIndex = point.getEndIndex(true);
            
            if (curIndex < 0) {
            	isCurrentArea = !isCurrentArea;
            } else if (polygon.containsExact(coords[2 * curIndex],
            		                      coords[2 * curIndex + 1]) > 0) { 
            	isCurrentArea = false;
            } else {
            	isCurrentArea = true;
            }

            IntersectPoint nextPoint = getNextIntersectPoint(intersectPoints, 
                                                             point, 
                                                             isCurrentArea);
            float[] coords = (isCurrentArea) ? this.coords : polygon.coords;
            int[] offsets = (isCurrentArea) ? this.offsets : polygon.offsets;
            int[] rules = (isCurrentArea) ? this.rules : polygon.rules;
            int offset = point.getRuleIndex(isCurrentArea);
            boolean isCopyUntilZero = false;
            
            if ((point.getRuleIndex(isCurrentArea) > 
                    nextPoint.getRuleIndex(isCurrentArea))) {
            	int rulesSize = (isCurrentArea) ? this.rulesSize : 
            		                              polygon.rulesSize;
            	resultCoordPos = includeCoordsAndRules(offset + 1, rulesSize,
            			                               rules, offsets, 
            			                               resultRules, 
            			                               resultOffsets, 
            			                               resultCoords, coords, 
            			                               resultRulesPos, 
            			                               resultCoordPos,
            			                               point, isCurrentArea, 
            			                               false, 0);
            	resultRulesPos += rulesSize - offset - 1; 
            	offset = 1;
            	isCopyUntilZero = true;
            }
            
            int length = nextPoint.getRuleIndex(isCurrentArea) - offset + 1;
            
            if (isCopyUntilZero) {
            	offset = 0;
            }
            
           	resultCoordPos = includeCoordsAndRules(offset, length, rules, 
           			                               offsets, resultRules, 
           			                               resultOffsets, resultCoords,
           			                               coords, resultRulesPos, 
           			                               resultCoordPos, point, 
           			                               isCurrentArea, true, 0);
            resultRulesPos += length - offset; 
            point = nextPoint;
        } while (point != intersectPoints[0]);
        
        resultRules[resultRulesPos++] = CLOSE;
        resultOffsets[resultRulesPos - 1] = resultCoordPos;
		this.coords = resultCoords;
		this.rules = resultRules;
		this.offsets = resultOffsets;
		this.coordsSize = resultCoordPos;
		this.rulesSize = resultRulesPos;
	}

    private void addPolygon(Polygon polygon) {
		CrossingHelper crossHelper = new CrossingHelper(new float[][] {coords,
				                                        polygon.coords },
				                                        new int[] {coordsSize, 
				                                        polygon.coordsSize });
		IntersectPoint[] intersectPoints = crossHelper.findCrossing();

		if (intersectPoints.length == 0) {
			if (polygon.contains(getBounds())) {
				copy(polygon, this);
			} else if (!contains(polygon.getBounds())) {
				coords = adjustSize(coords, coordsSize + polygon.coordsSize);
				System.arraycopy(polygon.coords, 0, coords, coordsSize,
								 polygon.coordsSize);
				coordsSize += polygon.coordsSize;
				rules = adjustSize(rules, rulesSize + polygon.rulesSize);
				System.arraycopy(polygon.rules, 0, rules, rulesSize,
								 polygon.rulesSize);
				rulesSize += polygon.rulesSize;
				offsets = adjustSize(offsets, rulesSize + polygon.rulesSize);
				System.arraycopy(polygon.offsets, 0, offsets, rulesSize,
								 polygon.rulesSize);
			}
			return;
		}

        float[] resultCoords = new float[coordsSize + polygon.coordsSize +
                                                       intersectPoints.length];
        int[] resultRules = new int[rulesSize + polygon.rulesSize +
                                                       intersectPoints.length];
        int[] resultOffsets = new int[rulesSize + polygon.rulesSize +
                                                       intersectPoints.length];
        int resultCoordPos = 0;
        int resultRulesPos = 0;
        boolean isCurrentArea = true;

        IntersectPoint point = intersectPoints[0];
        resultRules[resultRulesPos] = MOVE_TO;
        resultOffsets[resultRulesPos++] = resultCoordPos;
        
        do {
        	resultCoords[resultCoordPos++] = point.getX();
            resultCoords[resultCoordPos++] = point.getY();
            resultRules[resultRulesPos] = LINE_TO;
            resultOffsets[resultRulesPos++] = resultCoordPos - 2;
            int curIndex = point.getEndIndex(true);
            if (curIndex < 0) {
            	isCurrentArea = !isCurrentArea;
            } else if (polygon.containsExact(coords[2 * curIndex],
            		                      coords[2 * curIndex + 1]) > 0) { 
            	isCurrentArea = false;
            } else {
            	isCurrentArea = true;
            }

            IntersectPoint nextPoint = getNextIntersectPoint(intersectPoints, 
            		                                         point, 
            		                                         isCurrentArea);
            float[] coords = (isCurrentArea) ? this.coords : polygon.coords;
            int offset = 2 * point.getEndIndex(isCurrentArea);
 
            if ((offset >= 0) && 
            	    (nextPoint.getBegIndex(isCurrentArea) < 
            		    point.getEndIndex(isCurrentArea))) {
                int coordSize = (isCurrentArea) ? this.coordsSize : 
                	                              polygon.coordsSize;
                int length = coordSize - offset;
                System.arraycopy(coords, offset, 
                		         resultCoords, resultCoordPos, length);
                
                for (int i = 0; i < length / 2; i++) {
                	resultRules[resultRulesPos] = LINE_TO;
                	resultOffsets[resultRulesPos++] = resultCoordPos;
                	resultCoordPos += 2;
                }
                
                offset = 0;
            }
            
            if (offset >= 0) {
                int length = 2 * nextPoint.getBegIndex(isCurrentArea) - offset + 2;
                System.arraycopy(coords, offset, 
            		             resultCoords, resultCoordPos, length);
            
                for (int i = 0; i < length / 2; i++) {
            	    resultRules[resultRulesPos] = LINE_TO;
            	    resultOffsets[resultRulesPos++] = resultCoordPos;
            	    resultCoordPos += 2;
                }
            }

            point = nextPoint;
        } while (point != intersectPoints[0]);
        
        resultRules[resultRulesPos - 1] = CLOSE;
        resultOffsets[resultRulesPos - 1] = resultCoordPos;
		coords = resultCoords;
		rules = resultRules;
		offsets = resultOffsets;
		coordsSize = resultCoordPos;
		rulesSize = resultRulesPos;
	}
    
 	private void intersectCurvePolygon(Polygon polygon) {
		CurveCrossingHelper crossHelper = new CurveCrossingHelper(
				new float[][] {coords, polygon.coords },
				new int[] { coordsSize, polygon.coordsSize },
				new int[][] { rules, polygon.rules },
				new int[] { rulesSize, polygon.rulesSize },
				new int[][] { offsets, polygon.offsets });
		IntersectPoint[] intersectPoints = crossHelper.findCrossing();

		if (intersectPoints.length == 0) {
			if (contains(polygon.getBounds())) {
				copy(polygon, this);
			} else if (!polygon.contains(getBounds())) {
				reset();
			}
			return;
		}

        float[] resultCoords = new float[coordsSize + polygon.coordsSize +
                                                       intersectPoints.length];
        int[] resultRules = new int[rulesSize + polygon.rulesSize +
                                                       intersectPoints.length];
        int[] resultOffsets = new int[rulesSize + polygon.rulesSize +
                                                       intersectPoints.length];
        int resultCoordPos = 0;
        int resultRulesPos = 0;
        boolean isCurrentArea = true;

        IntersectPoint point = intersectPoints[0];
        IntersectPoint nextPoint = intersectPoints[0];
        resultRules[resultRulesPos] = MOVE_TO;
        resultOffsets[resultRulesPos++] = resultCoordPos;
        
        do {
        	resultCoords[resultCoordPos++] = point.getX();
            resultCoords[resultCoordPos++] = point.getY();
 
            int curIndex = point.getEndIndex(true);
            if ((curIndex < 0) || (polygon.containsExact(
            		coords[2 * curIndex], coords[2 * curIndex + 1]) == 0)) {
            	isCurrentArea = !isCurrentArea;
            } else if (polygon.containsExact(coords[2 * curIndex],
            		                      coords[2 * curIndex + 1]) > 0) { 
            	isCurrentArea = true;
            } else {
            	isCurrentArea = false;
            }
            
            nextPoint = getNextIntersectPoint(intersectPoints, point, isCurrentArea);
            float[] coords = (isCurrentArea) ? this.coords : polygon.coords;
            int[] offsets = (isCurrentArea) ? this.offsets : polygon.offsets;
            int[] rules = (isCurrentArea) ? this.rules : polygon.rules;
            int offset = point.getRuleIndex(isCurrentArea);
            boolean isCopyUntilZero = false;
            
            if (point.getRuleIndex(isCurrentArea) > 
                    nextPoint.getRuleIndex(isCurrentArea)) {
            	int rulesSize = (isCurrentArea) ? this.rulesSize : 
            		                              polygon.rulesSize;
            	resultCoordPos = includeCoordsAndRules(offset + 1, rulesSize, 
            			                               rules, offsets, 
            			                               resultRules, 
            			                               resultOffsets, 
            			                               resultCoords, coords, 
            			                               resultRulesPos, 
            			                               resultCoordPos, point, 
            			                               isCurrentArea, false, 
            			                               1);
            	resultRulesPos += rulesSize - offset - 1; 
            	offset = 1;
            	isCopyUntilZero = true;
            }
            
            int length = nextPoint.getRuleIndex(isCurrentArea) - offset + 1;
            
            if (isCopyUntilZero) {
            	offset = 0;
            	isCopyUntilZero = false;
            }
            if ((length == offset) && 
            	(nextPoint.getRule(isCurrentArea) != LINE_TO) &&
                (nextPoint.getRule(isCurrentArea) != CLOSE) &&
            	(point.getRule(isCurrentArea) != LINE_TO) &&
            	(point.getRule(isCurrentArea) != CLOSE)) {
            	
            	isCopyUntilZero = true;
            	length++;
            }
            
           	resultCoordPos = includeCoordsAndRules(offset, length, rules, 
           			                               offsets, resultRules, 
           			                               resultOffsets, resultCoords, 
           			                               coords, resultRulesPos, 
           			                               resultCoordPos, nextPoint, 
           			                               isCurrentArea, true, 1);
            resultRulesPos = ((length <= offset) || (isCopyUntilZero)) ? 
            		resultRulesPos + 1 : resultRulesPos + length; 

            point = nextPoint;
        } while (point != intersectPoints[0]);
        
        if (resultRules[resultRulesPos - 1] == LINE_TO) {
        	resultRules[resultRulesPos - 1] = CLOSE;
        } else {
        	resultCoords[resultCoordPos++] = nextPoint.getX();
            resultCoords[resultCoordPos++] = nextPoint.getY();
        	resultRules[resultRulesPos++] = CLOSE;
        }
        
        resultOffsets[resultRulesPos - 1] = resultCoordPos;
		coords = resultCoords;
		rules = resultRules;
		offsets = resultOffsets;
		coordsSize = resultCoordPos;
		rulesSize = resultRulesPos;
	}

	private void intersectPolygon(Polygon polygon) {
		CrossingHelper crossHelper = new CrossingHelper(new float[][] {coords, 
				                                        polygon.coords },
				                                        new int[] { coordsSize, 
				                                        polygon.coordsSize });
		IntersectPoint[] intersectPoints = crossHelper.findCrossing();

		if (intersectPoints.length == 0) {
			if (contains(polygon.getBounds())) {
				copy(polygon, this);
			} else if (!polygon.contains(getBounds())) {
				reset();
			}
			return;
		}

        float[] resultCoords = new float[coordsSize + polygon.coordsSize +
                                                        intersectPoints.length];
        int[] resultRules = new int[rulesSize + polygon.rulesSize +
                                                        intersectPoints.length];
        int[] resultOffsets = new int[rulesSize + polygon.rulesSize +
                                                        intersectPoints.length];
        int resultCoordPos = 0;
        int resultRulesPos = 0;
        boolean isCurrentArea = true;

        IntersectPoint point = intersectPoints[0];
        resultRules[resultRulesPos] = MOVE_TO;
        resultOffsets[resultRulesPos++] = resultCoordPos; 
        
        do {
        	resultCoords[resultCoordPos++] = point.getX();
            resultCoords[resultCoordPos++] = point.getY();
            resultRules[resultRulesPos] = LINE_TO;
            resultOffsets[resultRulesPos++] = resultCoordPos - 2;
            int curIndex = point.getEndIndex(true);

            if ((curIndex < 0) || 
            	(polygon.containsExact(coords[2 * curIndex],
            		                coords[2 * curIndex + 1]) == 0)) {
            	isCurrentArea = !isCurrentArea;
            } else if (polygon.containsExact(coords[2 * curIndex],
            		                      coords[2 * curIndex + 1]) > 0) { 
            	isCurrentArea = true;
            } else {
            	isCurrentArea = false;
            }

            IntersectPoint nextPoint = getNextIntersectPoint(intersectPoints, 
            		                                         point, 
            		                                         isCurrentArea);
            float[] coords = (isCurrentArea) ? this.coords : polygon.coords;
            int offset = 2 * point.getEndIndex(isCurrentArea);
            if ((offset >= 0) && 
            		(nextPoint.getBegIndex(isCurrentArea) < 
            		    point.getEndIndex(isCurrentArea))) {
                int coordSize = (isCurrentArea) ? this.coordsSize : 
                	                              polygon.coordsSize;
                int length = coordSize - offset;
                System.arraycopy(coords, offset, 
                		         resultCoords, resultCoordPos, length);
                
                for (int i = 0; i < length / 2; i++) {
                	resultRules[resultRulesPos] = LINE_TO;
                	resultOffsets[resultRulesPos++] = resultCoordPos;
                	resultCoordPos += 2;
                }
                
                offset = 0;
            }
            
            if (offset >= 0) {
            	int length = 2 * nextPoint.getBegIndex(isCurrentArea) - 
            	                 offset + 2;
            	System.arraycopy(coords, offset, 
            			         resultCoords, resultCoordPos, length);
            	
            	for (int i = 0; i < length / 2; i++) {
            		resultRules[resultRulesPos] = LINE_TO;
            		resultOffsets[resultRulesPos++] = resultCoordPos;
            		resultCoordPos += 2;
            	}
            }

            point = nextPoint;
        } while (point != intersectPoints[0]);
        
        resultRules[resultRulesPos - 1] = CLOSE;
        resultOffsets[resultRulesPos - 1] = resultCoordPos;
		coords = resultCoords;
		rules = resultRules;
		offsets = resultOffsets;
		coordsSize = resultCoordPos;
		rulesSize = resultRulesPos;
	}

	private void subtractCurvePolygon(Polygon polygon) {
		CurveCrossingHelper crossHelper = new CurveCrossingHelper(
				new float[][] { coords, polygon.coords },
				new int[] { coordsSize, polygon.coordsSize },
				new int[][] { rules, polygon.rules },
				new int[] { rulesSize, polygon.rulesSize },
				new int[][] { offsets, polygon.offsets });
		IntersectPoint[] intersectPoints = crossHelper.findCrossing();

		if (intersectPoints.length == 0 && contains(polygon.getBounds())) {
			copy(polygon, this);
			return;
		}

        float[] resultCoords = new float[coordsSize + polygon.coordsSize +
                                                       intersectPoints.length];
        int[] resultRules = new int[rulesSize + polygon.rulesSize +
                                                       intersectPoints.length];
        int[] resultOffsets = new int[rulesSize + polygon.rulesSize +
                                                       intersectPoints.length];
        int resultCoordPos = 0;
        int resultRulesPos = 0;
        boolean isCurrentArea = true;

        IntersectPoint point = intersectPoints[0];
        resultRules[resultRulesPos] = MOVE_TO;
        resultOffsets[resultRulesPos++] = resultCoordPos;
        
        do {
        	resultCoords[resultCoordPos++] = point.getX();
            resultCoords[resultCoordPos++] = point.getY();
            int curIndex = offsets[point.getRuleIndex(true)] % coordsSize;
            
            if (polygon.containsExact(coords[curIndex],
            		               coords[curIndex + 1]) == 0) {
            	isCurrentArea = !isCurrentArea;
            } else if (polygon.containsExact(coords[curIndex],
            		                      coords[curIndex + 1]) > 0) { 
            	isCurrentArea = false;
            } else {
            	isCurrentArea = true;
            }
  
            IntersectPoint nextPoint = (isCurrentArea) ? 
            		getNextIntersectPoint(intersectPoints, point, 
            				              isCurrentArea):
            		getPrevIntersectPoint(intersectPoints, point, 
            				              isCurrentArea);
            float[] coords = (isCurrentArea) ? this.coords : polygon.coords;
            int[] offsets = (isCurrentArea) ? this.offsets : polygon.offsets;
            int[] rules = (isCurrentArea) ? this.rules : polygon.rules;
            int offset = (isCurrentArea) ? point.getRuleIndex(isCurrentArea) :
            	                         nextPoint.getRuleIndex(isCurrentArea);
            boolean isCopyUntilZero = false;
         
            if (((isCurrentArea) && 
            	 (point.getRuleIndex(isCurrentArea) > 
            	  nextPoint.getRuleIndex(isCurrentArea))) ||
            	((!isCurrentArea) && 
            	 (nextPoint.getRuleIndex(isCurrentArea) > 
            	  nextPoint.getRuleIndex(isCurrentArea)))) {
            	
            	int rulesSize = (isCurrentArea) ? this.rulesSize : 
            		                              polygon.rulesSize;
            	resultCoordPos = includeCoordsAndRules(offset + 1, rulesSize, 
            			                               rules, offsets, 
            			                               resultRules, 
            			                               resultOffsets, 
            			                               resultCoords, coords, 
            			                               resultRulesPos, 
            			                               resultCoordPos, point, 
            			                               isCurrentArea, false, 
            			                               2);
            	resultRulesPos += rulesSize - offset - 1; 
            	offset = 1;
            	isCopyUntilZero = true;
            }
            
            int length = nextPoint.getRuleIndex(isCurrentArea) - offset + 1;
            
            if (isCopyUntilZero) {
            	offset = 0;
            	isCopyUntilZero = false;
            }
            
           	resultCoordPos = includeCoordsAndRules(offset, length, rules, 
           			                               offsets, resultRules, 
           			                               resultOffsets, resultCoords, 
           			                               coords, resultRulesPos, 
           			                               resultCoordPos, point, 
           			                               isCurrentArea, true, 2);
           	
           	if ((length == offset) && 
           		((rules[offset] == QUAD_TO) ||
           		 (rules[offset] == CUBIC_TO))) {
           		
           		resultRulesPos++;
    		} else {
           	    resultRulesPos = (length < offset || isCopyUntilZero) ? 
           	    		resultRulesPos + 1 : resultRulesPos + length - offset;
    		}

            point = nextPoint;
        } while (point != intersectPoints[0]);
        
        resultRules[resultRulesPos++] = CLOSE;
        resultOffsets[resultRulesPos - 1] = resultCoordPos;
		coords = resultCoords;
		rules = resultRules;
		offsets = resultOffsets;
		coordsSize = resultCoordPos;
		rulesSize = resultRulesPos;
	}

	private void subtractPolygon(Polygon polygon) {
		CrossingHelper crossHelper = new CrossingHelper(new float[][] {coords, 
				                                        polygon.coords },
				                                        new int[] { coordsSize, 
				                                        polygon.coordsSize });
		IntersectPoint[] intersectPoints = crossHelper.findCrossing();

		if (intersectPoints.length == 0) {
		    if (contains(polygon.getBounds())) {
		        copy(polygon, this);
		        return;
			} 
		    return;
		}

        float[] resultCoords = new float[2 * (coordsSize + polygon.coordsSize +
                                                       intersectPoints.length)];
        int[] resultRules = new int[2 * (rulesSize + polygon.rulesSize +
                                                       intersectPoints.length)];
        int[] resultOffsets = new int[2 * (rulesSize + polygon.rulesSize +
                                                       intersectPoints.length)];
        int resultCoordPos = 0;
        int resultRulesPos = 0;
        boolean isCurrentArea = true;
        int countPoints = 0;
        boolean curArea = false;
        boolean addArea = false;

        IntersectPoint point = intersectPoints[0];
        resultRules[resultRulesPos] = MOVE_TO;
        resultOffsets[resultRulesPos++] = resultCoordPos;
        
        do {
        	resultCoords[resultCoordPos++] = point.getX();
            resultCoords[resultCoordPos++] = point.getY();
            resultRules[resultRulesPos] = LINE_TO;
            resultOffsets[resultRulesPos++] = resultCoordPos - 2;
            int curIndex = point.getEndIndex(true);
            
            if ((curIndex < 0) || 
            		(polygon.isVertex(coords[2 * curIndex], coords[2 * curIndex + 1]) &&
            		     crossHelper.containsPoint(new float[] {coords[2 * curIndex], 
            				                       coords[2 * curIndex + 1]}) && 
            		(coords[2 * curIndex] != point.getX() || 
            			 coords[2 * curIndex + 1] != point.getY()))) {
            	isCurrentArea = !isCurrentArea;
            } else if (polygon.containsExact(coords[2 * curIndex],
            		                      coords[2 * curIndex + 1]) > 0) { 
            	isCurrentArea = false;
            } else {
            	isCurrentArea = true;
            }
            
            if (countPoints >= intersectPoints.length) {
                isCurrentArea = !isCurrentArea;
            }
            	 
            if (isCurrentArea) {
                curArea = true;
            } else {
                addArea = true;
            }

            IntersectPoint nextPoint = (isCurrentArea) ? 
            		getNextIntersectPoint(intersectPoints, point, isCurrentArea):
            		getPrevIntersectPoint(intersectPoints, point, isCurrentArea);
            float[] coords = (isCurrentArea) ? this.coords : polygon.coords;
            
            int offset = (isCurrentArea) ? 2 * point.getEndIndex(isCurrentArea): 
            							 2 * nextPoint.getEndIndex(isCurrentArea);
            
            if ((offset > 0) && 
            	(((isCurrentArea) && 
            	  (nextPoint.getBegIndex(isCurrentArea) < 
            			  point.getEndIndex(isCurrentArea))) ||
            	  ((!isCurrentArea) && 
            	  (nextPoint.getEndIndex(isCurrentArea) < 
            			  nextPoint.getBegIndex(isCurrentArea))))) {
            	
                int coordSize = (isCurrentArea) ? this.coordsSize : 
                	                              polygon.coordsSize;
                int length = coordSize - offset; 
                
                if (isCurrentArea) {
                	System.arraycopy(coords, offset, 
                			         resultCoords, resultCoordPos, length);
                } else {
                	float[] temp = new float[length];
                	System.arraycopy(coords, offset, temp, 0, length);
                	reverseCopy(temp);
                	System.arraycopy(temp, 0, 
                			         resultCoords, resultCoordPos, length);
                }
                
                for (int i = 0; i < length / 2; i++) {
                	resultRules[resultRulesPos] = LINE_TO;
                	resultOffsets[resultRulesPos++] = resultCoordPos;
                	resultCoordPos += 2;
                }
                
                offset = 0;
            }
            
            if (offset >= 0) {
            	int length = (isCurrentArea) ? 
            			         2 * nextPoint.getBegIndex(isCurrentArea) - offset + 2:
            	                 2 * point.getBegIndex(isCurrentArea) - offset + 2;
            			         
            	if (isCurrentArea) {
            		System.arraycopy(coords, offset, 
            				         resultCoords, resultCoordPos, length);
            	} else {
            		float[] temp = new float[length];
            		System.arraycopy(coords, offset, temp, 0, length);
            		reverseCopy(temp);
            		System.arraycopy(temp, 0, 
            				         resultCoords, resultCoordPos, length);
            	}
            	
            	for (int i = 0; i < length / 2; i++) {
            		resultRules[resultRulesPos] = LINE_TO;
            		resultOffsets[resultRulesPos++] = resultCoordPos;
            		resultCoordPos += 2;
            	}
            }

            point = nextPoint;
            countPoints++;
        } while (point != intersectPoints[0] || !(curArea && addArea));
        
        resultRules[resultRulesPos - 1] = CLOSE;
        resultOffsets[resultRulesPos - 1] = resultCoordPos;
	    coords = resultCoords;
	    rules = resultRules;
	    offsets = resultOffsets;
	    coordsSize = resultCoordPos;
	    rulesSize = resultRulesPos;
	}
	
	private IntersectPoint getNextIntersectPoint(IntersectPoint[] iPoints,
			                                        IntersectPoint isectPoint, 
			                                        boolean isCurrentArea) {
	    int endIndex = isectPoint.getEndIndex(isCurrentArea);
		if (endIndex < 0) {
			return iPoints[Math.abs(endIndex) - 1];
		}

		IntersectPoint firstIsectPoint = null;
		IntersectPoint nextIsectPoint = null;
		for (IntersectPoint point : iPoints) {
			int begIndex = point.getBegIndex(isCurrentArea);
			
			if (begIndex >= 0) {
				if (firstIsectPoint == null) {
					firstIsectPoint = point;
				} else if (begIndex < firstIsectPoint
						.getBegIndex(isCurrentArea)) {
					firstIsectPoint = point;
				}
			}

			if (endIndex <= begIndex) {
				if (nextIsectPoint == null) {
					nextIsectPoint = point;
				} else if (begIndex < 
						       nextIsectPoint.getBegIndex(isCurrentArea)) {
					nextIsectPoint = point;
				}
			}
		}

		return (nextIsectPoint != null) ? nextIsectPoint : firstIsectPoint;
	}

	private IntersectPoint getPrevIntersectPoint(IntersectPoint[] iPoints,
			                                     IntersectPoint isectPoint, 
			                                     boolean isCurrentArea) {

		int begIndex = isectPoint.getBegIndex(isCurrentArea);
		
		if (begIndex < 0) {
			return iPoints[Math.abs(begIndex) - 1];
		}

		IntersectPoint firstIsectPoint = null;
		IntersectPoint predIsectPoint = null;
		for (IntersectPoint point : iPoints) {
			int endIndex = point.getEndIndex(isCurrentArea);
			
			if (endIndex >= 0) {
				if (firstIsectPoint == null) {
					firstIsectPoint = point;
				} else if (endIndex < firstIsectPoint
						.getEndIndex(isCurrentArea)) {
					firstIsectPoint = point;
				}
			}

			if (endIndex <= begIndex) {
				if (predIsectPoint == null) {
					predIsectPoint = point;
				} else if (endIndex > 
				               predIsectPoint.getEndIndex(isCurrentArea)) {
					predIsectPoint = point;
				}
			}
		}

		return (predIsectPoint != null) ? predIsectPoint : firstIsectPoint;
	}

	
	private int includeCoordsAndRules(int offset, int length, int[] rules,
			                          int[] offsets, int[] resultRules, 
			                          int[] resultOffsets, float[] resultCoords,
			                          float[] coords, int resultRulesPos,
			                          int resultCoordPos, IntersectPoint point, 
			                          boolean isCurrentArea, boolean way, 
			                          int operation) {

		float[] temp = new float[8 * length];
		int coordsCount = 0;
		boolean isMoveIndex = true;
		boolean isMoveLength = true;
		boolean additional = false;

		if (length <= offset) {
			for (int i = resultRulesPos; i < resultRulesPos + 1; i++) {
				resultRules[i] = LINE_TO;
			}
		} else {
			int j = resultRulesPos;
			for (int i = offset; i < length; i++) {
				resultRules[j++] = LINE_TO;
			}
		}

		if ((length == offset) &&
			((rules[offset] == QUAD_TO) ||
			 (rules[offset] == CUBIC_TO))) {
			length++;
			additional = true;
		}
		for (int i = offset; i < length; i++) {
			int index = offsets[i];
			
			if (!isMoveIndex) {
				index -= 2;
			}
			
			if (!isMoveLength) {
				length++;
				isMoveLength = true;
			}
			
			switch (rules[i]) {
			    case MOVE_TO:
			    	isMoveIndex = false;
			    	isMoveLength = false;
				    break;
			    case LINE_TO:
			    case CLOSE:
				    resultRules[resultRulesPos] = LINE_TO;
				    resultOffsets[resultRulesPos++] = resultCoordPos + 2;
				    boolean isLeft = CrossingHelper.compare(coords[index],
						    coords[index + 1], point.getX(), point.getY()) > 0;
						    
				    if (way || !isLeft) {
					    temp[coordsCount++] = coords[index];
					    temp[coordsCount++] = coords[index + 1];
				    }
				    break;
			    case QUAD_TO:
				    resultRules[resultRulesPos] = QUAD_TO;
				    resultOffsets[resultRulesPos++] = resultCoordPos + 4;
				    float[] coefs = new float[] { coords[index - 2],
						    coords[index - 1], coords[index], coords[index + 1],
						    coords[index + 2], coords[index + 3] };
				    isLeft = CrossingHelper.compare(coords[index - 2],
						    coords[index - 1], point.getX(), point.getY()) > 0;
						    
				    if ((!additional) && (operation == 0 || operation == 2)) {
					    isLeft = !isLeft;
					    way = false;
				    }
				    GeometryUtil
						.subQuad(coefs, point.getParam(isCurrentArea), isLeft);
				    
				    if (way || isLeft) {
					    temp[coordsCount++] = coefs[2];
					    temp[coordsCount++] = coefs[3];
				    } else {
					    System.arraycopy(coefs, 2, temp, coordsCount, 4);
					    coordsCount += 4;
				    }
				    break;
			    case CUBIC_TO:
				    resultRules[resultRulesPos] = CUBIC_TO;
				    resultOffsets[resultRulesPos++] = resultCoordPos + 6;
				    coefs = new float[] {coords[index - 2], coords[index - 1],
						                  coords[index], coords[index + 1], 
						                  coords[index + 2], coords[index + 3], 
						                  coords[index + 4], coords[index + 5] };
				    isLeft = CrossingHelper.compare(coords[index - 2],
						    coords[index - 1], point.getX(), point.getY()) > 0;
				    GeometryUtil.subCubic(coefs, point.getParam(isCurrentArea),
						                  !isLeft);
				    
				    if (isLeft) {
					    System.arraycopy(coefs, 2, temp, coordsCount, 6);
					    coordsCount += 6;
				    } else {
					    System.arraycopy(coefs, 2, temp, coordsCount, 4);
					    coordsCount += 4;
				    }
				    break;
		    }
		}

        if (operation == 2 && !isCurrentArea && coordsCount > 2) {
			reverseCopy(temp);
			System.arraycopy(temp, 0, resultCoords, resultCoordPos, coordsCount);
		} else {
			System.arraycopy(temp, 0, resultCoords, resultCoordPos, coordsCount);
		}
        
		return (resultCoordPos + coordsCount);
	}
	
	// the method check up the array size and necessarily increases it. 
	private static float[] adjustSize(float[] array, int newSize) {
		if (newSize <= array.length) {
			return array;
		}
		float[] newArray = new float[2 * newSize];
		System.arraycopy(array, 0, newArray, 0, array.length);
		return newArray;
	}

	private static int[] adjustSize(int[] array, int newSize) {
		if (newSize <= array.length) {
			return array;
		}
		int[] newArray = new int[2 * newSize];
		System.arraycopy(array, 0, newArray, 0, array.length);
		return newArray;
	}

	public void setPolygon(Polygon polygon) {
		copy(polygon, this);
	}

	private static void copy(Polygon src, Polygon dst) {
		dst.coordsSize = src.coordsSize;
		dst.coords = src.coords.clone();
		dst.rulesSize = src.rulesSize;
		dst.rules = src.rules.clone();
		dst.moveToCount = src.moveToCount;
		dst.offsets = src.offsets.clone();
	}

    private int containsExact(float x, float y) {
        PathIterator pi = getPathIterator(null);
        int crossCount = Crossing.crossPath(pi, x, y);
        
        if (Crossing.isInsideEvenOdd(crossCount)) {
            return 1;
        }

        float[] segmentCoords = new float[6];
        float[] resultPoints = new float[6];
        int rule;
        float curX = -1;
        float curY = -1;
        float moveX = -1;
        float moveY = -1;
        
        for (pi = getPathIterator(null); pi.hasNext(); pi.next()) {
            rule = pi.currentSegment(segmentCoords);
            switch (rule) {
                case MOVE_TO:
                    moveX = curX = segmentCoords[0];
                    moveY = curY = segmentCoords[1];
                    break;
                case LINE_TO:
                    if (GeometryUtil.intersectLines(curX, curY, 
                    		segmentCoords[0], segmentCoords[1], x, y, x, y, 
                    		resultPoints) != 0) {
                        return 0;
                    }
                    curX = segmentCoords[0];
                    curY = segmentCoords[1];
                    break;
                case QUAD_TO:
                    if (GeometryUtil.intersectLineAndQuad(x, y, x, y, 
                    		curX, curY, segmentCoords[0], segmentCoords[1], 
                    		segmentCoords[2], segmentCoords[3], 
                    		resultPoints) > 0) {
                        return 0;
                    }
                    curX = segmentCoords[2];
                    curY = segmentCoords[3];
                    break;
                case CUBIC_TO:
                    if (GeometryUtil.intersectLineAndCubic(x, y, x, y, 
                    		curX, curY, segmentCoords[0], segmentCoords[1], 
                    		segmentCoords[2], segmentCoords[3], segmentCoords[4], 
                    		segmentCoords[5], resultPoints) > 0) {
                        return 0;
                    }
                    curX = segmentCoords[4];
                    curY = segmentCoords[5];
                    break;
                case CLOSE:
                    if (GeometryUtil.intersectLines(curX, curY, moveX, moveY,
                    		x, y, x, y, resultPoints) != 0) {
                        return 0;
                    }
                    curX = moveX;
                    curY = moveY;
                    break;
            }
        }
        return -1;
    }

    private void reverseCopy(float[] coords) {
    	float[] temp = new float[coords.length];
    	System.arraycopy(coords, 0, temp, 0, coords.length);
    	
    	for (int i = 0; i < coords.length;) {
    		coords[i] = temp[coords.length - i - 2];
    		coords[i + 1] = temp[coords.length - i - 1]; 
    		i = i + 2;
    	}
    }
    
    private float getAreaBoundsSquare() {
        Rectangle bounds = getBounds();
        return bounds.getHeight() * bounds.getWidth();
    }

    private boolean isVertex(float x, float y) {
        for (int i = 0; i < coordsSize;) {
    	    if (x == coords[i++] && y == coords[i++]) {
    		    return true;
    		}
    	}
    	return false;
    }

    // the internal class implements PathIterator
	static class Iterator implements PathIterator {

		AffineTransform transform;
		Polygon polygon;
		int curRuleIndex = 0;
		int curCoordIndex = 0;

		Iterator(Polygon polygon) {
			this(polygon, null);
		}

		Iterator(Polygon polygon, AffineTransform t) {
			this.polygon = polygon;
			this.transform = t;
		}

		public int getWindingRule() {
			return EVEN_ODD;
		}

		@Override
		public boolean hasNext() {
			return curRuleIndex < polygon.rulesSize;
		}

		public void next() {
			switch (polygon.rules[curRuleIndex]) {
			case MOVE_TO:
			case LINE_TO:
				curCoordIndex += 2;
				break;
			case QUAD_TO:
				curCoordIndex += 4;
				break;
			case CUBIC_TO:
				curCoordIndex += 6;
				break;
			}
			curRuleIndex++;
		}

		public int currentSegment(float[] c) {
			if (!hasNext()) {
				throw new NoSuchElementException("Iterator out of bounds");
			}
			
			int count = 0;
			
			switch (polygon.rules[curRuleIndex]) {
				case CUBIC_TO:
					c[4] = polygon.coords[curCoordIndex + 4];
					c[5] = polygon.coords[curCoordIndex + 5];
					count = 1;
				case QUAD_TO:
					c[2] = polygon.coords[curCoordIndex + 2];
					c[3] = polygon.coords[curCoordIndex + 3];
					count += 1;
				case MOVE_TO:
				case LINE_TO:
					c[0] = polygon.coords[curCoordIndex];
					c[1] = polygon.coords[curCoordIndex + 1];
					count += 1;
			}
			
			if(transform != null) {
	            transform.transform(c, 0, c, 0, count);
			}
			
			return polygon.rules[curRuleIndex];
		}

	}

	@Override
	public int hashCode() {
		int result = ArrayUtils.hashCode(coords, 0, coordsSize);
		result = 31 * result + coordsSize;
		result = 31 * result + ArrayUtils.hashCode(rules, 0, rulesSize);
		result = 31 * result + rulesSize;
		result = 31 * result + ArrayUtils.hashCode(offsets, 0, moveToCount);
		result = 31 * result + moveToCount;
		result = 31 * result + (isPolygonal() ? 1 : 0);
		return result;
	}

}