package a3wt.util;

import java.util.Map;

import static a3wt.util.A3Preconditions.checkArgElementsNotNull;
import static a3wt.util.A3Preconditions.checkArgNotNull;

public class A3Maps {

    private A3Maps() {}

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

    public static<K, V> Map<K, V> checkNullMap(final Map<K, V> map) {
        return new CheckNullMap<>(map);
    }

    public static<K, V> Map<K, V> checkKeyNullMap(final Map<K, V> map) {
        return new CheckKeyNullMap<>(map);
    }

    public static<K, V> Map<K, V> checkValueNullMap(final Map<K, V> map) {
        return new CheckValueNullMap<>(map);
    }

    static final class CheckKeyNullMap<K, V> extends AbstractA3FilterMap<K, V> {

        public CheckKeyNullMap(final Map<K, V> map) {
            super(map);
        }

        @Override
        public V put(K key, V value) {
            checkArgNotNull(key, "key");
            return super.put(key, value);
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> m) {
            checkArgElementsNotNull(m.keySet());
            super.putAll(m);
        }

    }

    static final class CheckNullMap<K, V> extends AbstractA3FilterMap<K, V> {

        public CheckNullMap(Map<K, V> map) {
            super(map);
        }

        @Override
        public V put(K key, V value) {
            checkArgNotNull(key, "key");
            checkArgNotNull(value, "value");
            return super.put(key, value);
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> m) {
            checkArgElementsNotNull(m.keySet());
            checkArgElementsNotNull(m.values());
            super.putAll(m);
        }

        @Override
        public V get(final Object key) {
            checkArgNotNull(key, "key");
            return super.get(key);
        }

    }

    static final class CheckValueNullMap<K, V> extends AbstractA3FilterMap<K, V> {

        public CheckValueNullMap(Map<K, V> map) {
            super(map);
        }

        @Override
        public V put(K key, V value) {
            checkArgNotNull(value, "value");
            return super.put(key, value);
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> m) {
            checkArgElementsNotNull(m.values());
            super.putAll(m);
        }

    }

}
