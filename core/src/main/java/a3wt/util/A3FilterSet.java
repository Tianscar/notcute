package a3wt.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public interface A3FilterSet<E> extends Set<E>, A3FilterCollection<E> {

    @Override
    Set<E> filterInstance();

    @Override
    default int size() {
        return filterInstance().size();
    }

    @Override
    default boolean isEmpty() {
        return filterInstance().isEmpty();
    }

    @Override
    default boolean contains(Object o) {
        return filterInstance().contains(o);
    }

    @Override
    default Iterator<E> iterator() {
        return filterInstance().iterator();
    }

    @Override
    default Object[] toArray() {
        return filterInstance().toArray();
    }

    @Override
    default <T> T[] toArray(T[] a) {
        return filterInstance().toArray(a);
    }

    @Override
    default boolean add(E e) {
        return filterInstance().add(e);
    }

    @Override
    default boolean remove(Object o) {
        return filterInstance().remove(o);
    }

    @Override
    default boolean containsAll(Collection<?> c) {
        return filterInstance().containsAll(c);
    }

    @Override
    default boolean addAll(Collection<? extends E> c) {
        return filterInstance().addAll(c);
    }

    @Override
    default boolean removeAll(Collection<?> c) {
        return filterInstance().removeAll(c);
    }

    @Override
    default boolean retainAll(Collection<?> c) {
        return filterInstance().retainAll(c);
    }

    @Override
    default void clear() {
        filterInstance().clear();
    }

}
