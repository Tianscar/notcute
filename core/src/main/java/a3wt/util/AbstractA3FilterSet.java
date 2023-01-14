package a3wt.util;

import java.util.Set;

public abstract class AbstractA3FilterSet<E> extends AbstractA3FilterCollection<E> implements A3FilterSet<E> {

    @Override
    public Set<E> filterInstance() {
        return (Set<E>) super.filterInstance();
    }

    public AbstractA3FilterSet(Set<E> set) {
        super(set);
    }

}
