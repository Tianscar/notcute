package a3wt.app;

import a3wt.util.A3Supplier;
import a3wt.util.A3Maps;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static a3wt.util.A3Preconditions.checkArgNotNull;

public class DefaultA3Factory implements A3Factory {

    protected final Map<Identifier, A3Supplier<?>> mappings = A3Maps.checkNullMap(new ConcurrentHashMap<>());

    @Override
    public Map<Identifier, A3Supplier<?>> getMappings() {
        return mappings;
    }

    @Override
    public A3Supplier<?> putMapping(final Identifier identifier, final A3Supplier<?> mapping) {
        return mappings.put(identifier, mapping);
    }

    @Override
    public A3Supplier<?> getMapping(final Identifier identifier) {
        return mappings.get(identifier);
    }

    @Override
    public A3Supplier<?> removeMapping(final Identifier identifier) {
        return mappings.remove(identifier);
    }

    @Override
    public void clearMappings() {
        mappings.clear();
    }

    @Override
    public Object createObject(final Identifier identifier) {
        checkArgNotNull(identifier, "identifier");
        final A3Supplier<?> supplier = mappings.get(identifier);
        return supplier == null ? null : supplier.get();
    }

}
