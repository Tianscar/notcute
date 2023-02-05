package io.notcute.util.collections;

import java.util.*;

public abstract class FilterNavigableMap<K, V> implements NavigableMap<K, V> {

    protected volatile NavigableMap<K, V> navigableMap;

    public FilterNavigableMap(NavigableMap<K, V> navigableMap) {
        this.navigableMap = Objects.requireNonNull(navigableMap);
    }
    
    @Override
    public Entry<K, V> lowerEntry(K key) {
        return navigableMap.lowerEntry(key);
    }

    @Override
    public K lowerKey(K key) {
        return navigableMap.lowerKey(key);
    }

    @Override
    public Entry<K, V> floorEntry(K key) {
        return navigableMap.floorEntry(key);
    }

    @Override
    public K floorKey(K key) {
        return navigableMap.floorKey(key);
    }

    @Override
    public Entry<K, V> ceilingEntry(K key) {
        return navigableMap.ceilingEntry(key);
    }

    @Override
    public K ceilingKey(K key) {
        return navigableMap.ceilingKey(key);
    }

    @Override
    public Entry<K, V> higherEntry(K key) {
        return navigableMap.higherEntry(key);
    }

    @Override
    public K higherKey(K key) {
        return navigableMap.higherKey(key);
    }

    @Override
    public Entry<K, V> firstEntry() {
        return navigableMap.firstEntry();
    }

    @Override
    public Entry<K, V> lastEntry() {
        return navigableMap.lastEntry();
    }

    @Override
    public Entry<K, V> pollFirstEntry() {
        return navigableMap.pollFirstEntry();
    }

    @Override
    public Entry<K, V> pollLastEntry() {
        return navigableMap.pollLastEntry();
    }

    @Override
    public NavigableMap<K, V> descendingMap() {
        return navigableMap.descendingMap();
    }

    @Override
    public NavigableSet<K> navigableKeySet() {
        return navigableMap.navigableKeySet();
    }

    @Override
    public NavigableSet<K> descendingKeySet() {
        return navigableMap.descendingKeySet();
    }

    @Override
    public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
        return navigableMap.subMap(fromKey, fromInclusive, toKey, toInclusive);
    }

    @Override
    public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
        return navigableMap.headMap(toKey, inclusive);
    }

    @Override
    public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
        return navigableMap.tailMap(fromKey, inclusive);
    }

    @Override
    public Comparator<? super K> comparator() {
        return navigableMap.comparator();
    }

    @Override
    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        return navigableMap.subMap(fromKey, toKey);
    }

    @Override
    public SortedMap<K, V> headMap(K toKey) {
        return navigableMap.headMap(toKey);
    }

    @Override
    public SortedMap<K, V> tailMap(K fromKey) {
        return navigableMap.tailMap(fromKey);
    }

    @Override
    public K firstKey() {
        return navigableMap.firstKey();
    }

    @Override
    public K lastKey() {
        return navigableMap.lastKey();
    }

    @Override
    public int size() {
        return navigableMap.size();
    }

    @Override
    public boolean isEmpty() {
        return navigableMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return navigableMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return navigableMap.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return navigableMap.get(key);
    }

    @Override
    public V put(K key, V value) {
        return navigableMap.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return navigableMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        navigableMap.putAll(m);
    }

    @Override
    public void clear() {
        navigableMap.clear();
    }

    @Override
    public Set<K> keySet() {
        return navigableMap.keySet();
    }

    @Override
    public Collection<V> values() {
        return navigableMap.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return navigableMap.entrySet();
    }

}
