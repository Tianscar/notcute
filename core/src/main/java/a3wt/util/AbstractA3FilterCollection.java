package a3wt.util;

import java.util.Collection;

import static a3wt.util.A3Preconditions.checkArgNotNull;

public abstract class AbstractA3FilterCollection<E> implements A3FilterCollection<E> {

    protected final Collection<E> instance;

    public AbstractA3FilterCollection(final Collection<E> collection) {
        checkArgNotNull(collection);
        instance = collection;
    }

    @Override
    public Collection<E> filterInstance() {
        return instance;
    }

}
