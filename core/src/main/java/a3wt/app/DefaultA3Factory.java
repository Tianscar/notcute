package a3wt.app;

import a3wt.util.A3Callable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static a3wt.util.A3Preconditions.checkArgNotNull;

public class DefaultA3Factory implements A3Factory {

    protected final Map<Identifier, A3Callable<?>> mappings = new ConcurrentHashMap<>();

    @Override
    public Map<Identifier, A3Callable<?>> getMappings() {
        return mappings;
    }

    @Override
    public void addMapping(final Identifier identifier, final A3Callable<?> mapping) {
        mappings.put(identifier, mapping);
    }

    @Override
    public Object create(final Identifier identifier) {
        checkArgNotNull(identifier, "identifier");
        final A3Callable<?> callable = mappings.get(identifier);
        return callable == null ? null : callable.call();
    }

}
