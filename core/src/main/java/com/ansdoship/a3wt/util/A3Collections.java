package com.ansdoship.a3wt.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.ansdoship.a3wt.util.A3Asserts.checkArgNotNull;

public class A3Collections {

    private A3Collections(){}

    public static <E> Collection<E> copy(final Collection<E> collection) {
        checkArgNotNull(collection, "collection");
        final List<E> result = new ArrayList<>(collection.size());
        result.addAll(collection);
        return result;
    }

    public static <E extends A3Copyable<?>> Collection<E> deepCopy(final Collection<E> collection) {
        checkArgNotNull(collection, "collection");
        final List<E> result = new ArrayList<>(collection.size());
        for (final A3Copyable<?> e : collection) {
            result.add((E) e.copy());
        }
        return result;
    }

    public static <E extends A3Copyable<E>> Collection<E> deepCopyStrict(final Collection<E> collection) {
        checkArgNotNull(collection, "collection");
        final List<E> result = new ArrayList<>(collection.size());
        for (final E e : collection) {
            result.add(e.copy());
        }
        return result;
    }

}
