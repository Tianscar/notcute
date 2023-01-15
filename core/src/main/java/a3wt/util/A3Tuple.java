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

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 * Abstract base class for all tuple classes.
 * </p> 
 * 
 * @since 1.0
 * 
 * @author Daniel Fern&aacute;ndez
 *
 */
public interface A3Tuple extends Comparable<A3Tuple>, Iterable<Object> {

    /**
     * <p>
     * Return the size of the tuple.
     * </p>
     * 
     * @return the size of the tuple.
     */
    int size();

    /**
     * <p>
     * Get the value at a specific position in the tuple. This method
     * has to return object, so using it you will lose the type-safety you 
     * get with the <tt>getValueX()</tt> methods.
     * </p>
     * 
     * @param pos the position of the value to be retrieved.
     * @return the value
     */
    Object getValue(final int pos);

    boolean contains(final Object value);

    boolean containsAll(final Collection<?> collection);
    
    boolean containsAll(final Object... values);
    
    int indexOf(final Object value);
    
    int lastIndexOf(final Object value);
    
    List<Object> toList();

    Object[] toArray();
    
}
