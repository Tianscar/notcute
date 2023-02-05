package io.notcute.util.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public abstract class FilterBlockingQueue<E> implements BlockingQueue<E> {
    
    protected volatile BlockingQueue<E> blockingQueue;
    
    public FilterBlockingQueue(BlockingQueue<E> blockingQueue) {
        this.blockingQueue = Objects.requireNonNull(blockingQueue);
    }
    
    @Override
    public boolean add(E e) {
        return blockingQueue.add(e);
    }

    @Override
    public boolean offer(E e) {
        return blockingQueue.offer(e);
    }

    @Override
    public E remove() {
        return blockingQueue.remove();
    }

    @Override
    public E poll() {
        return blockingQueue.poll();
    }

    @Override
    public E element() {
        return blockingQueue.element();
    }

    @Override
    public E peek() {
        return blockingQueue.peek();
    }

    @Override
    public void put(E e) throws InterruptedException {
        blockingQueue.put(e);
    }

    @Override
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        return blockingQueue.offer(e, timeout, unit);
    }

    @Override
    public E take() throws InterruptedException {
        return blockingQueue.take();
    }

    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        return blockingQueue.poll(timeout, unit);
    }

    @Override
    public int remainingCapacity() {
        return blockingQueue.remainingCapacity();
    }

    @Override
    public boolean remove(Object o) {
        return blockingQueue.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return blockingQueue.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return blockingQueue.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return blockingQueue.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return blockingQueue.retainAll(c);
    }

    @Override
    public void clear() {
        blockingQueue.clear();
    }

    @Override
    public int size() {
        return blockingQueue.size();
    }

    @Override
    public boolean isEmpty() {
        return blockingQueue.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return blockingQueue.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return blockingQueue.iterator();
    }

    @Override
    public Object[] toArray() {
        return blockingQueue.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return blockingQueue.toArray(a);
    }

    @Override
    public int drainTo(Collection<? super E> c) {
        return blockingQueue.drainTo(c);
    }

    @Override
    public int drainTo(Collection<? super E> c, int maxElements) {
        return blockingQueue.drainTo(c, maxElements);
    }
    
}
