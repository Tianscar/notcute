package a3wt.util;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static a3wt.util.A3Preconditions.checkArgElementsNotNull;
import static a3wt.util.A3Preconditions.checkArgNotNull;

public class A3Collections {

    private A3Collections() {}

    public static <E extends A3MutableCopyable<E>> Collection<E> copyElementsTo(final Collection<E> src, final Collection<E> dst) {
        checkArgNotNull(src, "src");
        checkArgNotNull(dst, "dst");
        for (final A3MutableCopyable<E> e : src) {
            dst.add(e.copy());
        }
        return dst;
    }
    
    public static<E> Collection<E> checkNullCollection(final Collection<E> collection) {
        if (collection instanceof List) return checkNullList((List<E>) collection);
        else if (collection instanceof Set) return checkNullSet((Set<E>) collection);
        else return new CheckNullCollection<>(collection);
    }

    public static<E> List<E> checkNullList(final List<E> list) {
        return new CheckNullList<>(list);
    }

    public static<E> Set<E> checkNullSet(final Set<E> set) {
        return new CheckNullSet<>(set);
    }

    static final class CheckNullCollection<E> extends AbstractA3FilterCollection<E> {

        public CheckNullCollection(final Collection<E> collection) {
            super(collection);
        }

        @Override
        public boolean add(E e) {
            checkArgNotNull(e, "e");
            return super.add(e);
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            checkArgElementsNotNull(c, "c");
            return super.addAll(c);
        }

    }

    static final class CheckNullList<E> extends AbstractA3FilterList<E> {

        public CheckNullList(final List<E> instance) {
            super(instance);
        }

        @Override
        public boolean add(E e) {
            checkArgNotNull(e, "e");
            return super.add(e);
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            checkArgElementsNotNull(c, "c");
            return super.addAll(c);
        }

        @Override
        public void add(int index, E element) {
            checkArgNotNull(element, "element");
            super.add(index, element);
        }

        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            checkArgElementsNotNull(c, "c");
            return super.addAll(index, c);
        }

    }

    static final class CheckNullSet<E> extends AbstractA3FilterSet<E> {

        public CheckNullSet(Set<E> set) {
            super(set);
        }

        @Override
        public boolean add(E e) {
            checkArgNotNull(e, "e");
            return super.add(e);
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            checkArgElementsNotNull(c, "c");
            return super.addAll(c);
        }

    }
    
}
