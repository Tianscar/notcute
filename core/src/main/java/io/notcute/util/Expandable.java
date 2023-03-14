package io.notcute.util;

import java.util.Set;

public interface Expandable<T extends Expandable.Expansion> {

    Set<T> getExpansions();

    interface Expansion {
    }

}
