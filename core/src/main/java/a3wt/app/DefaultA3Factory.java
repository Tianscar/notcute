package a3wt.app;

import a3wt.util.A3Callable;
import a3wt.util.A3Maps;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static a3wt.util.A3Preconditions.checkArgNotNull;

public class DefaultA3Factory implements A3Factory {

    protected final Map<Identifier, A3Callable<?>> mappings = A3Maps.checkNullMap(new ConcurrentHashMap<>());

    @Override
    public Map<Identifier, A3Callable<?>> getMappings() {
        return mappings;
    }

    @Override
    public A3Callable<?> putMapping(final Identifier identifier, final A3Callable<?> mapping) {
        return mappings.put(identifier, mapping);
    }

    @Override
    public A3Callable<?> getMapping(final Identifier identifier) {
        return mappings.get(identifier);
    }

    @Override
    public A3Callable<?> removeMapping(final Identifier identifier) {
        return mappings.remove(identifier);
    }

    @Override
    public void clearMappings() {
        mappings.clear();
    }

    @Override
    public Object createObject(final Identifier identifier) {
        checkArgNotNull(identifier, "identifier");
        final A3Callable<?> callable = mappings.get(identifier);
        return callable == null ? null : callable.call();
    }

}
