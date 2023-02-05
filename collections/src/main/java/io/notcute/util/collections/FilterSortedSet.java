package io.notcute.util.collections;

import java.util.*;

public abstract class FilterSortedSet<E> implements SortedSet<E> {

    protected volatile SortedSet<E> sortedSet;

    public FilterSortedSet(SortedSet<E> sortedSet) {
        this.sortedSet = Objects.requireNonNull(sortedSet);
    }
    
    @Override
    public Comparator<? super E> comparator() {
        return sortedSet.comparator();
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
        return sortedSet.subSet(fromElement, toElement);
    }

    @Override
    public SortedSet<E> headSet(E toElement) {
        return sortedSet.headSet(toElement);
    }

    @Override
    public SortedSet<E> tailSet(E fromElement) {
        return sortedSet.tailSet(fromElement);
    }

    @Override
    public E first() {
        return sortedSet.first();
    }

    @Override
    public E last() {
        return sortedSet.last();
    }

    @Override
    public int size() {
        return sortedSet.size();
    }

    @Override
    public boolean isEmpty() {
        return sortedSet.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return sortedSet.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return sortedSet.iterator();
    }

    @Override
    public Object[] toArray() {
        return sortedSet.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return sortedSet.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return sortedSet.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return sortedSet.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return sortedSet.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return sortedSet.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return sortedSet.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return sortedSet.removeAll(c);
    }

    @Override
    public void clear() {
        sortedSet.clear();
    }

}
