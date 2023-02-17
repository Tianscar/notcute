package io.notcute.util;

import io.notcute.util.collections.ConcurrentHashSet;

import java.util.Set;

public abstract class AbstractExpandable<T extends Expandable.Expansion> implements Expandable<T> {

    private final Set<T> expansions = new ConcurrentHashSet<>();

    @Override
    public Set<T> getExpansions() {
        return expansions;
    }

}
