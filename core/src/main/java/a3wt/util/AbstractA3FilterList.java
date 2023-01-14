package a3wt.util;

import java.util.List;

public abstract class AbstractA3FilterList<E> extends AbstractA3FilterCollection<E> implements A3FilterList<E> {

    @Override
    public List<E> filterInstance() {
        return (List<E>) super.filterInstance();
    }

    public AbstractA3FilterList(List<E> instance) {
        super(instance);
    }

}
