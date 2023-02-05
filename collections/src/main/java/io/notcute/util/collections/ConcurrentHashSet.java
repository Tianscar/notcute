package io.notcute.util.collections;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashSet<E> extends AbstractSet<E> {

    private static final Object DUMMY = new Object();

    private final ConcurrentHashMap<E, Object> hashMap;

    public ConcurrentHashSet() {
        hashMap = new ConcurrentHashMap<>();
    }

    public ConcurrentHashSet(int initialCapacity) {
        hashMap = new ConcurrentHashMap<>(initialCapacity);
    }

    public ConcurrentHashSet(int initialCapacity, float loadFactor) {
        hashMap = new ConcurrentHashMap<>(initialCapacity, loadFactor);
    }

    public ConcurrentHashSet(int initialCapacity, float loadFactor, int concurrencyLevel) {
        hashMap = new ConcurrentHashMap<>(initialCapacity, loadFactor, concurrencyLevel);
    }

    @Override
    public int size() {
        return hashMap.size();
    }

    @Override
    public boolean isEmpty() {
        return hashMap.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return hashMap.keySet().iterator();
    }

    @Override
    public boolean add(final E o) {
        return hashMap.put(o, DUMMY) == null;
    }

    @Override
    public boolean remove(final Object o) {
        return hashMap.remove(o) == DUMMY;
    }

    @Override
    public boolean contains(final Object o) {
        return hashMap.containsKey(o);
    }

    @Override
    public void clear() {
        hashMap.clear();
    }

    @Override
    public Object[] toArray() {
        return hashMap.keySet().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return hashMap.keySet().toArray(a);
    }

}
