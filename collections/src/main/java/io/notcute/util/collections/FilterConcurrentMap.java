package io.notcute.util.collections;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public abstract class FilterConcurrentMap<K, V> implements ConcurrentMap<K, V> {

    protected volatile ConcurrentMap<K, V> concurrentMap;

    public FilterConcurrentMap(ConcurrentMap<K, V> concurrentMap) {
        this.concurrentMap = Objects.requireNonNull(concurrentMap);
    }


    @Override
    public int size() {
        return concurrentMap.size();
    }

    @Override
    public boolean isEmpty() {
        return concurrentMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return concurrentMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return concurrentMap.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return concurrentMap.get(key);
    }

    @Override
    public V put(K key, V value) {
        return concurrentMap.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return concurrentMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        concurrentMap.putAll(m);
    }

    @Override
    public void clear() {
        concurrentMap.clear();
    }

    @Override
    public Set<K> keySet() {
        return concurrentMap.keySet();
    }

    @Override
    public Collection<V> values() {
        return concurrentMap.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return concurrentMap.entrySet();
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return concurrentMap.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return concurrentMap.remove(key, value);
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return concurrentMap.replace(key, oldValue, newValue);
    }

    @Override
    public V replace(K key, V value) {
        return concurrentMap.replace(key, value);
    }
    
}
