package io.notcute.util.collections;

import java.util.*;
import java.util.concurrent.ConcurrentNavigableMap;

public abstract class FilterConcurrentNavigableMap<K, V> implements ConcurrentNavigableMap<K, V> {

    protected volatile ConcurrentNavigableMap<K, V> concurrentNavigableMap;

    public FilterConcurrentNavigableMap(ConcurrentNavigableMap<K, V> concurrentNavigableMap) {
        this.concurrentNavigableMap = Objects.requireNonNull(concurrentNavigableMap);
    }
    
    @Override
    public Entry<K, V> lowerEntry(K key) {
        return concurrentNavigableMap.lowerEntry(key);
    }

    @Override
    public K lowerKey(K key) {
        return concurrentNavigableMap.lowerKey(key);
    }

    @Override
    public Entry<K, V> floorEntry(K key) {
        return concurrentNavigableMap.floorEntry(key);
    }

    @Override
    public K floorKey(K key) {
        return concurrentNavigableMap.floorKey(key);
    }

    @Override
    public Entry<K, V> ceilingEntry(K key) {
        return concurrentNavigableMap.ceilingEntry(key);
    }

    @Override
    public K ceilingKey(K key) {
        return concurrentNavigableMap.ceilingKey(key);
    }

    @Override
    public Entry<K, V> higherEntry(K key) {
        return concurrentNavigableMap.higherEntry(key);
    }

    @Override
    public K higherKey(K key) {
        return concurrentNavigableMap.higherKey(key);
    }

    @Override
    public Entry<K, V> firstEntry() {
        return concurrentNavigableMap.firstEntry();
    }

    @Override
    public Entry<K, V> lastEntry() {
        return concurrentNavigableMap.lastEntry();
    }

    @Override
    public Entry<K, V> pollFirstEntry() {
        return concurrentNavigableMap.pollFirstEntry();
    }

    @Override
    public Entry<K, V> pollLastEntry() {
        return concurrentNavigableMap.pollLastEntry();
    }

    @Override
    public ConcurrentNavigableMap<K, V> descendingMap() {
        return concurrentNavigableMap.descendingMap();
    }

    @Override
    public NavigableSet<K> navigableKeySet() {
        return concurrentNavigableMap.navigableKeySet();
    }

    @Override
    public NavigableSet<K> descendingKeySet() {
        return concurrentNavigableMap.descendingKeySet();
    }

    @Override
    public ConcurrentNavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
        return concurrentNavigableMap.subMap(fromKey, fromInclusive, toKey, toInclusive);
    }

    @Override
    public ConcurrentNavigableMap<K, V> headMap(K toKey, boolean inclusive) {
        return concurrentNavigableMap.headMap(toKey, inclusive);
    }

    @Override
    public ConcurrentNavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
        return concurrentNavigableMap.tailMap(fromKey, inclusive);
    }

    @Override
    public Comparator<? super K> comparator() {
        return concurrentNavigableMap.comparator();
    }

    @Override
    public ConcurrentNavigableMap<K, V> subMap(K fromKey, K toKey) {
        return concurrentNavigableMap.subMap(fromKey, toKey);
    }

    @Override
    public ConcurrentNavigableMap<K, V> headMap(K toKey) {
        return concurrentNavigableMap.headMap(toKey);
    }

    @Override
    public ConcurrentNavigableMap<K, V> tailMap(K fromKey) {
        return concurrentNavigableMap.tailMap(fromKey);
    }

    @Override
    public K firstKey() {
        return concurrentNavigableMap.firstKey();
    }

    @Override
    public K lastKey() {
        return concurrentNavigableMap.lastKey();
    }

    @Override
    public int size() {
        return concurrentNavigableMap.size();
    }

    @Override
    public boolean isEmpty() {
        return concurrentNavigableMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return concurrentNavigableMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return concurrentNavigableMap.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return concurrentNavigableMap.get(key);
    }

    @Override
    public V put(K key, V value) {
        return concurrentNavigableMap.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return concurrentNavigableMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        concurrentNavigableMap.putAll(m);
    }

    @Override
    public void clear() {
        concurrentNavigableMap.clear();
    }

    @Override
    public NavigableSet<K> keySet() {
        return concurrentNavigableMap.keySet();
    }

    @Override
    public Collection<V> values() {
        return concurrentNavigableMap.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return concurrentNavigableMap.entrySet();
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return concurrentNavigableMap.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return concurrentNavigableMap.remove(key, value);
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return concurrentNavigableMap.replace(key, oldValue, newValue);
    }

    @Override
    public V replace(K key, V value) {
        return concurrentNavigableMap.replace(key, value);
    }

}
