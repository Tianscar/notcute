package io.notcute.util.collections;

import java.util.*;

public abstract class FilterSortedMap<K, V> implements SortedMap<K, V> {

    protected volatile SortedMap<K, V> sortedMap;

    public FilterSortedMap(SortedMap<K, V> sortedMap) {
        this.sortedMap = Objects.requireNonNull(sortedMap);
    }

    @Override
    public Comparator<? super K> comparator() {
        return sortedMap.comparator();
    }

    @Override
    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        return sortedMap.subMap(fromKey, toKey);
    }

    @Override
    public SortedMap<K, V> headMap(K toKey) {
        return sortedMap.headMap(toKey);
    }

    @Override
    public SortedMap<K, V> tailMap(K fromKey) {
        return sortedMap.tailMap(fromKey);
    }

    @Override
    public K firstKey() {
        return sortedMap.firstKey();
    }

    @Override
    public K lastKey() {
        return sortedMap.lastKey();
    }

    @Override
    public int size() {
        return sortedMap.size();
    }

    @Override
    public boolean isEmpty() {
        return sortedMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return sortedMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return sortedMap.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return sortedMap.get(key);
    }

    @Override
    public V put(K key, V value) {
        return sortedMap.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return sortedMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        sortedMap.putAll(m);
    }

    @Override
    public void clear() {
        sortedMap.clear();
    }

    @Override
    public Set<K> keySet() {
        return sortedMap.keySet();
    }

    @Override
    public Collection<V> values() {
        return sortedMap.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return sortedMap.entrySet();
    }

}
