package io.notcute.context;

import io.notcute.util.QuietCallable;
import io.notcute.util.collections.FilterMap;

import java.util.concurrent.ConcurrentHashMap;

public class Producer extends FilterMap<Identifier, QuietCallable<Object>> {

    public Producer() {
        super(new ConcurrentHashMap<>(1));
    }

    public Object produce(Identifier identifier) {
        QuietCallable<Object> callable = get(identifier);
        if (callable == null) return null;
        else return callable.call();
    }

}
