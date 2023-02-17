package io.notcute.util;

import java.util.Map;
import java.util.Objects;

public final class CollectionUtils {

    private CollectionUtils() {
        throw new UnsupportedOperationException();
    }

    public static<K, V> V getOrDefault(Map<K, V> map, K key, V defaultValue) {
        V v;
        return (((v = map.get(key)) != null) || map.containsKey(key))
                ? v
                : defaultValue;
    }

    public static<K, V> V putIfAbsent(Map<K, V> map, K key, V value) {
        V v = map.get(key);
        if (v == null) {
            v = map.put(key, value);
        }

        return v;
    }

    public static<K, V> boolean remove(Map<K, V> map, Object key, Object value) {
        Object curValue = map.get(key);
        if (!Objects.equals(curValue, value) ||
                (curValue == null && !map.containsKey(key))) {
            return false;
        }
        map.remove(key);
        return true;
    }

    public static<K, V> boolean replace(Map<K, V> map, K key, V oldValue, V newValue) {
        Object curValue = map.get(key);
        if (!Objects.equals(curValue, oldValue) ||
                (curValue == null && !map.containsKey(key))) {
            return false;
        }
        map.put(key, newValue);
        return true;
    }

    public static<K, V> V replace(Map<K, V> map, K key, V value) {
        V curValue;
        if (((curValue = map.get(key)) != null) || map.containsKey(key)) {
            curValue = map.put(key, value);
        }
        return curValue;
    }

}
