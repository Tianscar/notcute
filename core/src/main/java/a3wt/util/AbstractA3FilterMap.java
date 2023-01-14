package a3wt.util;

import java.util.Map;

import static a3wt.util.A3Preconditions.checkArgNotNull;

public abstract class AbstractA3FilterMap<K, V> implements A3FilterMap<K, V> {
    
    protected final Map<K, V> instance;

    public AbstractA3FilterMap(final Map<K, V> map) {
        checkArgNotNull(map);
        instance = map;
    }

    @Override
    public Map<K, V> filterInstance() {
        return instance;
    }
    
}
