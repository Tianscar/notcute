package io.notcute.util.collections;

import java.util.*;

public abstract class FilterNavigableSet<E> implements NavigableSet<E> {

    protected volatile NavigableSet<E> navigableSet;

    public FilterNavigableSet(NavigableSet<E> navigableSet) {
        this.navigableSet = Objects.requireNonNull(navigableSet);
    }
    
    @Override
    public E lower(E e) {
        return navigableSet.lower(e);
    }

    @Override
    public E floor(E e) {
        return navigableSet.floor(e);
    }

    @Override
    public E ceiling(E e) {
        return navigableSet.ceiling(e);
    }

    @Override
    public E higher(E e) {
        return navigableSet.higher(e);
    }

    @Override
    public E pollFirst() {
        return navigableSet.pollFirst();
    }

    @Override
    public E pollLast() {
        return navigableSet.pollLast();
    }

    @Override
    public int size() {
        return navigableSet.size();
    }

    @Override
    public boolean isEmpty() {
        return navigableSet.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return navigableSet.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return navigableSet.iterator();
    }

    @Override
    public Object[] toArray() {
        return navigableSet.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return navigableSet.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return navigableSet.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return navigableSet.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return navigableSet.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return navigableSet.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return navigableSet.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return navigableSet.retainAll(c);
    }

    @Override
    public void clear() {
        navigableSet.clear();
    }

    @Override
    public NavigableSet<E> descendingSet() {
        return navigableSet.descendingSet();
    }

    @Override
    public Iterator<E> descendingIterator() {
        return navigableSet.descendingIterator();
    }

    @Override
    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        return navigableSet.subSet(fromElement, fromInclusive, toElement, toInclusive);
    }

    @Override
    public NavigableSet<E> headSet(E toElement, boolean inclusive) {
        return navigableSet.headSet(toElement, inclusive);
    }

    @Override
    public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
        return navigableSet.tailSet(fromElement, inclusive);
    }

    @Override
    public Comparator<? super E> comparator() {
        return navigableSet.comparator();
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
        return navigableSet.subSet(fromElement, toElement);
    }

    @Override
    public SortedSet<E> headSet(E toElement) {
        return navigableSet.headSet(toElement);
    }

    @Override
    public SortedSet<E> tailSet(E fromElement) {
        return navigableSet.tailSet(fromElement);
    }

    @Override
    public E first() {
        return navigableSet.first();
    }

    @Override
    public E last() {
        return navigableSet.last();
    }
    
}
