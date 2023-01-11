package a3wt.bundle;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultConcurrentA3SecMapBundle extends AbstractDefaultA3SecMapBundle {

    public DefaultConcurrentA3SecMapBundle() {
        super(new ConcurrentHashMap<>());
    }

    @Override
    public boolean isConcurrent() {
        return true;
    }

    @Override
    protected A3MapBundle createMapBundle() {
        return new DefaultConcurrentA3MapBundle();
    }

    @Override
    public A3MapBundle put(String key, A3MapBundle value) {
        if (!value.isConcurrent()) throw new IllegalArgumentException("value is not concurrent!");
        return super.put(key, value);
    }

    @Override
    public void putAll(final Map<? extends String, ? extends A3MapBundle> m) {
        for (final A3MapBundle value : m.values()) {
            if (!value.isConcurrent()) throw new IllegalArgumentException("value is not concurrent!");
        }
        super.putAll(m);
    }

}
