package io.notcute.context;

import io.notcute.util.QuietCallable;
import io.notcute.util.collections.FilterMap;

import java.util.concurrent.ConcurrentHashMap;

public class Producer extends FilterMap<Identifier, QuietCallable<Object>> {
    
    public static final Producer GLOBAL = new Producer();

    public Producer() {
        super(new ConcurrentHashMap<>());
    }

    public Object produce(Identifier identifier) {
        QuietCallable<Object> callable = get(identifier);
        if (callable == null) return null;
        else return callable.call();
    }

    public<T> T produce(Identifier identifier, Class<T> clazz) {
        if (clazz == null) return null;
        QuietCallable<Object> callable = get(identifier);
        if (callable == null) return null;
        else {
            Object obj = callable.call();
            if (clazz.isInstance(obj)) return clazz.cast(obj);
            else return null;
        }
    }

}
