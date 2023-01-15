package a3wt.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface A3FilterMap<K, V> extends A3Map<K, V> {

    Map<K, V> filterInstance();
    
    @Override
    default int size() {
        return filterInstance().size();
    }

    @Override
    default boolean isEmpty() {
        return filterInstance().isEmpty();
    }

    @Override
    default boolean containsKey(Object key) {
        return filterInstance().containsKey(key);
    }

    @Override
    default boolean containsValue(Object value) {
        return filterInstance().containsValue(value);
    }

    @Override
    default V get(Object key) {
        return filterInstance().get(key);
    }

    @Override
    default V put(K key, V value) {
        return filterInstance().put(key, value);
    }

    @Override
    default V remove(Object key) {
        return filterInstance().remove(key);
    }

    @Override
    default void putAll(Map<? extends K, ? extends V> m) {
        filterInstance().putAll(m);
    }

    @Override
    default void clear() {
        filterInstance().clear();
    }

    @Override
    default Set<K> keySet() {
        return filterInstance().keySet();
    }

    @Override
    default Collection<V> values() {
        return filterInstance().values();
    }

    @Override
    default Set<Entry<K, V>> entrySet() {
        return filterInstance().entrySet();
    }
    
}
