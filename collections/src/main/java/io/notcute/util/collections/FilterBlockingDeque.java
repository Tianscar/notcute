package io.notcute.util.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;

public abstract class FilterBlockingDeque<E> implements BlockingDeque<E> {

    protected volatile BlockingDeque<E> blockingDeque;

    public FilterBlockingDeque(BlockingDeque<E> blockingDeque) {
        this.blockingDeque = Objects.requireNonNull(blockingDeque);
    }

    @Override
    public void addFirst(E e) {
        blockingDeque.addFirst(e);
    }

    @Override
    public void addLast(E e) {
        blockingDeque.addLast(e);
    }

    @Override
    public boolean offerFirst(E e) {
        return blockingDeque.offerFirst(e);
    }

    @Override
    public boolean offerLast(E e) {
        return blockingDeque.offerLast(e);
    }

    @Override
    public E removeFirst() {
        return blockingDeque.removeFirst();
    }

    @Override
    public E removeLast() {
        return blockingDeque.removeLast();
    }

    @Override
    public E pollFirst() {
        return blockingDeque.pollFirst();
    }

    @Override
    public E pollLast() {
        return blockingDeque.pollLast();
    }

    @Override
    public E getFirst() {
        return blockingDeque.getFirst();
    }

    @Override
    public E getLast() {
        return blockingDeque.getLast();
    }

    @Override
    public E peekFirst() {
        return blockingDeque.peekFirst();
    }

    @Override
    public E peekLast() {
        return blockingDeque.peekLast();
    }

    @Override
    public void putFirst(E e) throws InterruptedException {
        blockingDeque.putFirst(e);
    }

    @Override
    public void putLast(E e) throws InterruptedException {
        blockingDeque.putLast(e);
    }

    @Override
    public boolean offerFirst(E e, long timeout, TimeUnit unit) throws InterruptedException {
        return blockingDeque.offerFirst(e, timeout, unit);
    }

    @Override
    public boolean offerLast(E e, long timeout, TimeUnit unit) throws InterruptedException {
        return blockingDeque.offerLast(e, timeout, unit);
    }

    @Override
    public E takeFirst() throws InterruptedException {
        return blockingDeque.takeFirst();
    }

    @Override
    public E takeLast() throws InterruptedException {
        return blockingDeque.takeLast();
    }

    @Override
    public E pollFirst(long timeout, TimeUnit unit) throws InterruptedException {
        return blockingDeque.pollFirst(timeout, unit);
    }

    @Override
    public E pollLast(long timeout, TimeUnit unit) throws InterruptedException {
        return blockingDeque.pollLast(timeout, unit);
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return blockingDeque.removeFirstOccurrence(o);
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return blockingDeque.removeLastOccurrence(o);
    }

    @Override
    public boolean add(E e) {
        return blockingDeque.add(e);
    }

    @Override
    public boolean offer(E e) {
        return blockingDeque.offer(e);
    }

    @Override
    public void put(E e) throws InterruptedException {
        blockingDeque.put(e);
    }

    @Override
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        return blockingDeque.offer(e, timeout, unit);
    }

    @Override
    public E remove() {
        return blockingDeque.remove();
    }

    @Override
    public E poll() {
        return blockingDeque.poll();
    }

    @Override
    public E take() throws InterruptedException {
        return blockingDeque.take();
    }

    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        return blockingDeque.poll(timeout, unit);
    }

    @Override
    public int remainingCapacity() {
        return blockingDeque.remainingCapacity();
    }

    @Override
    public E element() {
        return blockingDeque.element();
    }

    @Override
    public E peek() {
        return blockingDeque.peek();
    }

    @Override
    public boolean remove(Object o) {
        return blockingDeque.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return blockingDeque.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return blockingDeque.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return blockingDeque.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return blockingDeque.retainAll(c);
    }

    @Override
    public void clear() {
        blockingDeque.clear();
    }

    @Override
    public boolean contains(Object o) {
        return blockingDeque.contains(o);
    }

    @Override
    public int drainTo(Collection<? super E> c) {
        return blockingDeque.drainTo(c);
    }

    @Override
    public int drainTo(Collection<? super E> c, int maxElements) {
        return blockingDeque.drainTo(c, maxElements);
    }

    @Override
    public int size() {
        return blockingDeque.size();
    }

    @Override
    public boolean isEmpty() {
        return blockingDeque.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return blockingDeque.iterator();
    }

    @Override
    public Object[] toArray() {
        return blockingDeque.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return blockingDeque.toArray(a);
    }

    @Override
    public Iterator<E> descendingIterator() {
        return blockingDeque.descendingIterator();
    }

    @Override
    public void push(E e) {
        blockingDeque.push(e);
    }

    @Override
    public E pop() {
        return blockingDeque.pop();
    }
    
}
