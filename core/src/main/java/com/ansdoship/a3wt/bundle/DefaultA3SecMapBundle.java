package com.ansdoship.a3wt.bundle;

import java.util.HashMap;
import java.util.Map;

public class DefaultA3SecMapBundle extends AbstractDefaultA3SecMapBundle {

    public DefaultA3SecMapBundle() {
        super(new HashMap<>());
    }

    @Override
    public boolean isConcurrent() {
        return false;
    }

    @Override
    protected A3MapBundle createMapBundle() {
        return new DefaultA3MapBundle();
    }

    @Override
    public A3MapBundle put(String key, A3MapBundle value) {
        if (value.isConcurrent()) throw new IllegalArgumentException("value is concurrent!");
        return super.put(key, value);
    }

    @Override
    public void putAll(final Map<? extends String, ? extends A3MapBundle> m) {
        for (final A3MapBundle value : m.values()) {
            if (value.isConcurrent()) throw new IllegalArgumentException("value is concurrent!");
        }
        super.putAll(m);
    }

}
