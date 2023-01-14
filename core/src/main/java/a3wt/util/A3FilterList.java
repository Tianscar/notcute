package a3wt.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public interface A3FilterList<E> extends List<E>, A3FilterCollection<E> {

    @Override
    List<E> filterInstance();

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
        return filterInstance().contains(c);
    }

    @Override
    default boolean addAll(Collection<? extends E> c) {
        return filterInstance().addAll(c);
    }

    @Override
    default boolean addAll(int index, Collection<? extends E> c) {
        return filterInstance().addAll(index, c);
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

    @Override
    default E get(int index) {
        return filterInstance().get(index);
    }

    @Override
    default E set(int index, E element) {
        return filterInstance().set(index, element);
    }

    @Override
    default void add(int index, E element) {
        filterInstance().add(index, element);
    }

    @Override
    default E remove(int index) {
        return filterInstance().remove(index);
    }

    @Override
    default int indexOf(Object o) {
        return filterInstance().indexOf(o);
    }

    @Override
    default int lastIndexOf(Object o) {
        return filterInstance().lastIndexOf(o);
    }

    @Override
    default ListIterator<E> listIterator() {
        return filterInstance().listIterator();
    }

    @Override
    default ListIterator<E> listIterator(int index) {
        return filterInstance().listIterator(index);
    }

    @Override
    default List<E> subList(int fromIndex, int toIndex) {
        return filterInstance().subList(fromIndex, toIndex);
    }
    
}
