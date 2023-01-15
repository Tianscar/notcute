/*
 * =============================================================================
 * 
 *   Copyright (c) 2010, The JAVATUPLES team (http://www.javatuples.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package a3wt.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * <p>
 * A tuple of three elements.
 * </p> 
 * 
 * @since 1.0
 * 
 * @author Daniel Fern&aacute;ndez
 *
 */
public class A3TripletTuple<A,B,C> extends AbstractA3Tuple {

    private static final long serialVersionUID = -1877265551599483740L;

    private static final int SIZE = 3;

    private final A val0;
    private final B val1;
    private final C val2;
    
    
    
    public static <A,B,C> A3TripletTuple<A,B,C> with(final A value0, final B value1, final C value2) {
        return new A3TripletTuple<A,B,C>(value0,value1,value2);
    }

    
    /**
     * <p>
     * Create tuple from array. Array has to have exactly three elements.
     * </p>
     * 
     * @param <X> the array component type 
     * @param array the array to be converted to a tuple
     * @return the tuple
     */
    public static <X> A3TripletTuple<X,X,X> fromArray(final X[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        if (array.length != 3) {
            throw new IllegalArgumentException("Array must have exactly 3 elements in order to create a Triplet. Size is " + array.length);
        }
        return new A3TripletTuple<X,X,X>(array[0],array[1],array[2]);
    }

    
    /**
     * <p>
     * Create tuple from collection. Collection has to have exactly three elements.
     * </p>
     * 
     * @param <X> the collection component type 
     * @param collection the collection to be converted to a tuple
     * @return the tuple
     */
    public static <X> A3TripletTuple<X,X,X> fromCollection(final Collection<X> collection) {
        return fromIterable(collection);
    }


    
    /**
     * <p>
     * Create tuple from iterable. Iterable has to have exactly three elements.
     * </p>
     * 
     * @param <X> the iterable component type 
     * @param iterable the iterable to be converted to a tuple
     * @return the tuple
     */
    public static <X> A3TripletTuple<X,X,X> fromIterable(final Iterable<X> iterable) {
        return fromIterable(iterable, 0, true);
    }

    
    
    /**
     * <p>
     * Create tuple from iterable, starting from the specified index. Iterable
     * can have more (or less) elements than the tuple to be created.
     * </p>
     * 
     * @param <X> the iterable component type 
     * @param iterable the iterable to be converted to a tuple
     * @return the tuple
     */
    public static <X> A3TripletTuple<X,X,X> fromIterable(final Iterable<X> iterable, int index) {
        return fromIterable(iterable, index, false);
    }

    


    private static <X> A3TripletTuple<X,X,X> fromIterable(final Iterable<X> iterable, int index, final boolean exactSize) {
        
        if (iterable == null) {
            throw new IllegalArgumentException("Iterable cannot be null");
        }

        boolean tooFewElements = false; 
        
        X element0 = null;
        X element1 = null;
        X element2 = null;
        
        final Iterator<X> iter = iterable.iterator();
        
        int i = 0;
        while (i < index) {
            if (iter.hasNext()) {
                iter.next();
            } else {
                tooFewElements = true;
            }
            i++;
        }
        
        if (iter.hasNext()) {
            element0 = iter.next();
        } else {
            tooFewElements = true;
        }
        
        if (iter.hasNext()) {
            element1 = iter.next();
        } else {
            tooFewElements = true;
        }
        
        if (iter.hasNext()) {
            element2 = iter.next();
        } else {
            tooFewElements = true;
        }
        
        if (tooFewElements && exactSize) {
            throw new IllegalArgumentException("Not enough elements for creating a Triplet (3 needed)");
        }
        
        if (iter.hasNext() && exactSize) {
            throw new IllegalArgumentException("Iterable must have exactly 3 available elements in order to create a Triplet.");
        }
        
        return new A3TripletTuple<X,X,X>(element0, element1, element2);
        
    }
    

    
    
    
    
    public A3TripletTuple(
            final A value0,
            final B value1,
            final C value2) {
        super(value0, value1, value2);
        this.val0 = value0;
        this.val1 = value1;
        this.val2 = value2;
    }


    public A getValue0() {
        return this.val0;
    }


    public B getValue1() {
        return this.val1;
    }


    public C getValue2() {
        return this.val2;
    }


    @Override
    public int size() {
        return SIZE;
    }
    
    
    
    
    
    
    
    
    
    public <X0> A3QuartetTuple<X0,A,B,C> addAt0(final X0 value0) {
        return new A3QuartetTuple<X0,A,B,C>(
                value0, this.val0, this.val1, this.val2);
    }
    
    public <X0> A3QuartetTuple<A,X0,B,C> addAt1(final X0 value0) {
        return new A3QuartetTuple<A,X0,B,C>(
                this.val0, value0, this.val1, this.val2);
    }
    
    public <X0> A3QuartetTuple<A,B,X0,C> addAt2(final X0 value0) {
        return new A3QuartetTuple<A,B,X0,C>(
                this.val0, this.val1, value0, this.val2);
    }
    
    public <X0> A3QuartetTuple<A,B,C,X0> addAt3(final X0 value0) {
        return new A3QuartetTuple<A,B,C,X0>(
                this.val0, this.val1, this.val2, value0);
    }

    
    
    
    
    public <X0,X1> A3QuintetTuple<X0,X1,A,B,C> addAt0(final X0 value0, final X1 value1) {
        return new A3QuintetTuple<X0,X1,A,B,C>(
                value0, value1, this.val0, this.val1, this.val2);
    }
    
    public <X0,X1> A3QuintetTuple<A,X0,X1,B,C> addAt1(final X0 value0, final X1 value1) {
        return new A3QuintetTuple<A,X0,X1,B,C>(
                this.val0, value0, value1, this.val1, this.val2);
    }
    
    public <X0,X1> A3QuintetTuple<A,B,X0,X1,C> addAt2(final X0 value0, final X1 value1) {
        return new A3QuintetTuple<A,B,X0,X1,C>(
                this.val0, this.val1, value0, value1, this.val2);
    }
    
    public <X0,X1> A3QuintetTuple<A,B,C,X0,X1> addAt3(final X0 value0, final X1 value1) {
        return new A3QuintetTuple<A,B,C,X0,X1>(
                this.val0, this.val1, this.val2, value0, value1);
    }
    


    
    
    
    
    public <X0,X1,X2> A3SextetTuple<X0,X1,X2,A,B,C> addAt0(final X0 value0, final X1 value1, final X2 value2) {
        return new A3SextetTuple<X0,X1,X2,A,B,C>(
                value0, value1, value2, this.val0, this.val1, this.val2);
    }
    
    public <X0,X1,X2> A3SextetTuple<A,X0,X1,X2,B,C> addAt1(final X0 value0, final X1 value1, final X2 value2) {
        return new A3SextetTuple<A,X0,X1,X2,B,C>(
                this.val0, value0, value1, value2, this.val1, this.val2);
    }
    
    public <X0,X1,X2> A3SextetTuple<A,B,X0,X1,X2,C> addAt2(final X0 value0, final X1 value1, final X2 value2) {
        return new A3SextetTuple<A,B,X0,X1,X2,C>(
                this.val0, this.val1, value0, value1, value2, this.val2);
    }
    
    public <X0,X1,X2> A3SextetTuple<A,B,C,X0,X1,X2> addAt3(final X0 value0, final X1 value1, final X2 value2) {
        return new A3SextetTuple<A,B,C,X0,X1,X2>(
                this.val0, this.val1, this.val2, value0, value1, value2);
    }
    


    
    
    
    
    public <X0,X1,X2,X3> A3SeptetTuple<X0,X1,X2,X3,A,B,C> addAt0(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return new A3SeptetTuple<X0,X1,X2,X3,A,B,C>(
                value0, value1, value2, value3, this.val0, this.val1, this.val2);
    }
    
    public <X0,X1,X2,X3> A3SeptetTuple<A,X0,X1,X2,X3,B,C> addAt1(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return new A3SeptetTuple<A,X0,X1,X2,X3,B,C>(
                this.val0, value0, value1, value2, value3, this.val1, this.val2);
    }
    
    public <X0,X1,X2,X3> A3SeptetTuple<A,B,X0,X1,X2,X3,C> addAt2(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return new A3SeptetTuple<A,B,X0,X1,X2,X3,C>(
                this.val0, this.val1, value0, value1, value2, value3, this.val2);
    }
    
    public <X0,X1,X2,X3> A3SeptetTuple<A,B,C,X0,X1,X2,X3> addAt3(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return new A3SeptetTuple<A,B,C,X0,X1,X2,X3>(
                this.val0, this.val1, this.val2, value0, value1, value2, value3);
    }


    
    
    
    
    public <X0,X1,X2,X3,X4> A3OctetTuple<X0,X1,X2,X3,X4,A,B,C> addAt0(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4) {
        return new A3OctetTuple<X0,X1,X2,X3,X4,A,B,C>(
                value0, value1, value2, value3, value4, this.val0, this.val1, this.val2);
    }
    
    public <X0,X1,X2,X3,X4> A3OctetTuple<A,X0,X1,X2,X3,X4,B,C> addAt1(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4) {
        return new A3OctetTuple<A,X0,X1,X2,X3,X4,B,C>(
                this.val0, value0, value1, value2, value3, value4, this.val1, this.val2);
    }
    
    public <X0,X1,X2,X3,X4> A3OctetTuple<A,B,X0,X1,X2,X3,X4,C> addAt2(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4) {
        return new A3OctetTuple<A,B,X0,X1,X2,X3,X4,C>(
                this.val0, this.val1, value0, value1, value2, value3, value4, this.val2);
    }
    
    public <X0,X1,X2,X3,X4> A3OctetTuple<A,B,C,X0,X1,X2,X3,X4> addAt3(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4) {
        return new A3OctetTuple<A,B,C,X0,X1,X2,X3,X4>(
                this.val0, this.val1, this.val2, value0, value1, value2, value3, value4);
    }


    
    
    
    
    public <X0,X1,X2,X3,X4,X5> A3EnneadTuple<X0,X1,X2,X3,X4,X5,A,B,C> addAt0(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5) {
        return new A3EnneadTuple<X0,X1,X2,X3,X4,X5,A,B,C>(
                value0, value1, value2, value3, value4, value5, this.val0, this.val1, this.val2);
    }
    
    public <X0,X1,X2,X3,X4,X5> A3EnneadTuple<A,X0,X1,X2,X3,X4,X5,B,C> addAt1(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5) {
        return new A3EnneadTuple<A,X0,X1,X2,X3,X4,X5,B,C>(
                this.val0, value0, value1, value2, value3, value4, value5, this.val1, this.val2);
    }
    
    public <X0,X1,X2,X3,X4,X5> A3EnneadTuple<A,B,X0,X1,X2,X3,X4,X5,C> addAt2(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5) {
        return new A3EnneadTuple<A,B,X0,X1,X2,X3,X4,X5,C>(
                this.val0, this.val1, value0, value1, value2, value3, value4, value5, this.val2);
    }
    
    public <X0,X1,X2,X3,X4,X5> A3EnneadTuple<A,B,C,X0,X1,X2,X3,X4,X5> addAt3(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5) {
        return new A3EnneadTuple<A,B,C,X0,X1,X2,X3,X4,X5>(
                this.val0, this.val1, this.val2, value0, value1, value2, value3, value4, value5);
    }


    
    
    
    
    public <X0,X1,X2,X3,X4,X5,X6> A3DecadeTuple<X0,X1,X2,X3,X4,X5,X6,A,B,C> addAt0(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6) {
        return new A3DecadeTuple<X0,X1,X2,X3,X4,X5,X6,A,B,C>(
                value0, value1, value2, value3, value4, value5, value6, this.val0, this.val1, this.val2);
    }
    
    public <X0,X1,X2,X3,X4,X5,X6> A3DecadeTuple<A,X0,X1,X2,X3,X4,X5,X6,B,C> addAt1(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6) {
        return new A3DecadeTuple<A,X0,X1,X2,X3,X4,X5,X6,B,C>(
                this.val0, value0, value1, value2, value3, value4, value5, value6, this.val1, this.val2);
    }
    
    public <X0,X1,X2,X3,X4,X5,X6> A3DecadeTuple<A,B,X0,X1,X2,X3,X4,X5,X6,C> addAt2(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6) {
        return new A3DecadeTuple<A,B,X0,X1,X2,X3,X4,X5,X6,C>(
                this.val0, this.val1, value0, value1, value2, value3, value4, value5, value6, this.val2);
    }
    
    public <X0,X1,X2,X3,X4,X5,X6> A3DecadeTuple<A,B,C,X0,X1,X2,X3,X4,X5,X6> addAt3(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6) {
        return new A3DecadeTuple<A,B,C,X0,X1,X2,X3,X4,X5,X6>(
                this.val0, this.val1, this.val2, value0, value1, value2, value3, value4, value5, value6);
    }

    
    
    
    
    
    
    public <X0> A3QuartetTuple<X0,A,B,C> addAt0(final A3UnitTuple<X0> tuple) {
        return addAt0(tuple.getValue0());
    }
    
    public <X0> A3QuartetTuple<A,X0,B,C> addAt1(final A3UnitTuple<X0> tuple) {
        return addAt1(tuple.getValue0());
    }
    
    public <X0> A3QuartetTuple<A,B,X0,C> addAt2(final A3UnitTuple<X0> tuple) {
        return addAt2(tuple.getValue0());
    }
    
    public <X0> A3QuartetTuple<A,B,C,X0> addAt3(final A3UnitTuple<X0> tuple) {
        return addAt3(tuple.getValue0());
    }
    


    
    
    
    
    public <X0,X1> A3QuintetTuple<X0,X1,A,B,C> addAt0(final A3PairTuple<X0,X1> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1());
    }
    
    public <X0,X1> A3QuintetTuple<A,X0,X1,B,C> addAt1(final A3PairTuple<X0,X1> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1());
    }
    
    public <X0,X1> A3QuintetTuple<A,B,X0,X1,C> addAt2(final A3PairTuple<X0,X1> tuple) {
        return addAt2(tuple.getValue0(),tuple.getValue1());
    }
    
    public <X0,X1> A3QuintetTuple<A,B,C,X0,X1> addAt3(final A3PairTuple<X0,X1> tuple) {
        return addAt3(tuple.getValue0(),tuple.getValue1());
    }

    
    

    
    
    
    
    public <X0,X1,X2> A3SextetTuple<X0,X1,X2,A,B,C> addAt0(final A3TripletTuple<X0,X1,X2> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2());
    }
    
    public <X0,X1,X2> A3SextetTuple<A,X0,X1,X2,B,C> addAt1(final A3TripletTuple<X0,X1,X2> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2());
    }
    
    public <X0,X1,X2> A3SextetTuple<A,B,X0,X1,X2,C> addAt2(final A3TripletTuple<X0,X1,X2> tuple) {
        return addAt2(tuple.getValue0(),tuple.getValue1(),tuple.getValue2());
    }
    
    public <X0,X1,X2> A3SextetTuple<A,B,C,X0,X1,X2> addAt3(final A3TripletTuple<X0,X1,X2> tuple) {
        return addAt3(tuple.getValue0(),tuple.getValue1(),tuple.getValue2());
    }
    
    
    


    
    
    
    
    public <X0,X1,X2,X3> A3SeptetTuple<X0,X1,X2,X3,A,B,C> addAt0(final A3QuartetTuple<X0,X1,X2,X3> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3());
    }
    
    public <X0,X1,X2,X3> A3SeptetTuple<A,X0,X1,X2,X3,B,C> addAt1(final A3QuartetTuple<X0,X1,X2,X3> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3());
    }
    
    public <X0,X1,X2,X3> A3SeptetTuple<A,B,X0,X1,X2,X3,C> addAt2(final A3QuartetTuple<X0,X1,X2,X3> tuple) {
        return addAt2(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3());
    }
    
    public <X0,X1,X2,X3> A3SeptetTuple<A,B,C,X0,X1,X2,X3> addAt3(final A3QuartetTuple<X0,X1,X2,X3> tuple) {
        return addAt3(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3());
    }

    
    

    
    
    
    
    public <X0,X1,X2,X3,X4> A3OctetTuple<X0,X1,X2,X3,X4,A,B,C> addAt0(final A3QuintetTuple<X0,X1,X2,X3,X4> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4());
    }
    
    public <X0,X1,X2,X3,X4> A3OctetTuple<A,X0,X1,X2,X3,X4,B,C> addAt1(final A3QuintetTuple<X0,X1,X2,X3,X4> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4());
    }
    
    public <X0,X1,X2,X3,X4> A3OctetTuple<A,B,X0,X1,X2,X3,X4,C> addAt2(final A3QuintetTuple<X0,X1,X2,X3,X4> tuple) {
        return addAt2(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4());
    }
    
    public <X0,X1,X2,X3,X4> A3OctetTuple<A,B,C,X0,X1,X2,X3,X4> addAt3(final A3QuintetTuple<X0,X1,X2,X3,X4> tuple) {
        return addAt3(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4());
    }

    
    

    
    
    
    
    public <X0,X1,X2,X3,X4,X5> A3EnneadTuple<X0,X1,X2,X3,X4,X5,A,B,C> addAt0(final A3SextetTuple<X0,X1,X2,X3,X4,X5> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5());
    }
    
    public <X0,X1,X2,X3,X4,X5> A3EnneadTuple<A,X0,X1,X2,X3,X4,X5,B,C> addAt1(final A3SextetTuple<X0,X1,X2,X3,X4,X5> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5());
    }
    
    public <X0,X1,X2,X3,X4,X5> A3EnneadTuple<A,B,X0,X1,X2,X3,X4,X5,C> addAt2(final A3SextetTuple<X0,X1,X2,X3,X4,X5> tuple) {
        return addAt2(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5());
    }
    
    public <X0,X1,X2,X3,X4,X5> A3EnneadTuple<A,B,C,X0,X1,X2,X3,X4,X5> addAt3(final A3SextetTuple<X0,X1,X2,X3,X4,X5> tuple) {
        return addAt3(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5());
    }

    
    

    
    
    
    
    public <X0,X1,X2,X3,X4,X5,X6> A3DecadeTuple<X0,X1,X2,X3,X4,X5,X6,A,B,C> addAt0(final A3SeptetTuple<X0,X1,X2,X3,X4,X5,X6> tuple) {
        return addAt0(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5(),tuple.getValue6());
    }
    
    public <X0,X1,X2,X3,X4,X5,X6> A3DecadeTuple<A,X0,X1,X2,X3,X4,X5,X6,B,C> addAt1(final A3SeptetTuple<X0,X1,X2,X3,X4,X5,X6> tuple) {
        return addAt1(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5(),tuple.getValue6());
    }
    
    public <X0,X1,X2,X3,X4,X5,X6> A3DecadeTuple<A,B,X0,X1,X2,X3,X4,X5,X6,C> addAt2(final A3SeptetTuple<X0,X1,X2,X3,X4,X5,X6> tuple) {
        return addAt2(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5(),tuple.getValue6());
    }
    
    public <X0,X1,X2,X3,X4,X5,X6> A3DecadeTuple<A,B,C,X0,X1,X2,X3,X4,X5,X6> addAt3(final A3SeptetTuple<X0,X1,X2,X3,X4,X5,X6> tuple) {
        return addAt3(tuple.getValue0(),tuple.getValue1(),tuple.getValue2(),tuple.getValue3(),tuple.getValue4(),tuple.getValue5(),tuple.getValue6());
    }

    
    
    
    
    
    public <X0> A3QuartetTuple<A,B,C,X0> add(final X0 value0) {
        return addAt3(value0);
    }
    
    
    public <X0> A3QuartetTuple<A,B,C,X0> add(final A3UnitTuple<X0> tuple) {
        return addAt3(tuple);
    }
    
    
    
    
    public <X0,X1> A3QuintetTuple<A,B,C,X0,X1> add(final X0 value0, final X1 value1) {
        return addAt3(value0, value1);
    }
    
    
    public <X0,X1> A3QuintetTuple<A,B,C,X0,X1> add(final A3PairTuple<X0,X1> tuple) {
        return addAt3(tuple);
    }
    
    
    
    
    public <X0,X1,X2> A3SextetTuple<A,B,C,X0,X1,X2> add(final X0 value0, final X1 value1, final X2 value2) {
        return addAt3(value0, value1, value2);
    }
    
    
    public <X0,X1,X2> A3SextetTuple<A,B,C,X0,X1,X2> add(final A3TripletTuple<X0,X1,X2> tuple) {
        return addAt3(tuple);
    }
    
    
    
    
    public <X0,X1,X2,X3> A3SeptetTuple<A,B,C,X0,X1,X2,X3> add(final X0 value0, final X1 value1, final X2 value2, final X3 value3) {
        return addAt3(value0, value1, value2, value3);
    }
    
    
    public <X0,X1,X2,X3> A3SeptetTuple<A,B,C,X0,X1,X2,X3> add(final A3QuartetTuple<X0,X1,X2,X3> tuple) {
        return addAt3(tuple);
    }
    
    
    
    
    
    public <X0,X1,X2,X3,X4> A3OctetTuple<A,B,C,X0,X1,X2,X3,X4> add(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4) {
        return addAt3(value0, value1, value2, value3, value4);
    }
    
    
    public <X0,X1,X2,X3,X4> A3OctetTuple<A,B,C,X0,X1,X2,X3,X4> add(final A3QuintetTuple<X0,X1,X2,X3,X4> tuple) {
        return addAt3(tuple);
    }
    
    
    
    
    
    public <X0,X1,X2,X3,X4,X5> A3EnneadTuple<A,B,C,X0,X1,X2,X3,X4,X5> add(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5) {
        return addAt3(value0, value1, value2, value3, value4, value5);
    }
    
    
    public <X0,X1,X2,X3,X4,X5> A3EnneadTuple<A,B,C,X0,X1,X2,X3,X4,X5> add(final A3SextetTuple<X0,X1,X2,X3,X4,X5> tuple) {
        return addAt3(tuple);
    }
    
    
    
    
    
    public <X0,X1,X2,X3,X4,X5,X6> A3DecadeTuple<A,B,C,X0,X1,X2,X3,X4,X5,X6> add(final X0 value0, final X1 value1, final X2 value2, final X3 value3, final X4 value4, final X5 value5, final X6 value6) {
        return addAt3(value0, value1, value2, value3, value4, value5, value6);
    }
    
    
    public <X0,X1,X2,X3,X4,X5,X6> A3DecadeTuple<A,B,C,X0,X1,X2,X3,X4,X5,X6> add(final A3SeptetTuple<X0,X1,X2,X3,X4,X5,X6> tuple) {
        return addAt3(tuple);
    }

    
    
    
    
    
    
    
    public <X> A3TripletTuple<X,B,C> setAt0(final X value) {
        return new A3TripletTuple<X,B,C>(
                value, this.val1, this.val2);
    }
    
    public <X> A3TripletTuple<A,X,C> setAt1(final X value) {
        return new A3TripletTuple<A,X,C>(
                this.val0, value, this.val2);
    }
    
    public <X> A3TripletTuple<A,B,X> setAt2(final X value) {
        return new A3TripletTuple<A,B,X>(
                this.val0, this.val1, value);
    }
    
    
    
    
    
    
    
    public A3PairTuple<B,C> removeFrom0() {
        return new A3PairTuple<B,C>(
                this.val1, this.val2);
    }
    
    public A3PairTuple<A,C> removeFrom1() {
        return new A3PairTuple<A,C>(
                this.val0, this.val2);
    }
    
    public A3PairTuple<A,B> removeFrom2() {
        return new A3PairTuple<A,B>(
                this.val0, this.val1);
    }
    
    
    
}
