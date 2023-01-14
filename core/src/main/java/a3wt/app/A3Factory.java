package a3wt.app;

import a3wt.util.A3Callable;

import java.util.Map;

import static a3wt.util.A3Preconditions.checkArgNotEmpty;

public interface A3Factory {

    interface Identifier {
        String getNamespace();
        String getName();
    }

    class DefaultIdentifier implements Identifier {
        protected final String namespace;
        protected final String name;
        public DefaultIdentifier(final String namespace, final String name) {
            checkArgNotEmpty(namespace, "namespace");
            checkArgNotEmpty(name, "name");
            this.namespace = namespace;
            this.name = name;
        }
        public String getNamespace() {
            return namespace;
        }
        public String getName() {
            return name;
        }
    }

    Map<Identifier, A3Callable<?>> getMappings();
    A3Callable<?> putMapping(final Identifier identifier, final A3Callable<?> mapping);
    default A3Callable<?> putMapping(final String namespace, final String name, final A3Callable<?> mapping) {
        return putMapping(new DefaultIdentifier(namespace, name), mapping);
    }
    A3Callable<?> removeMapping(final Identifier identifier);
    default A3Callable<?> removeMapping(final String namespace, final String name) {
        return removeMapping(new DefaultIdentifier(namespace, name));
    }
    void clearMappings();
    A3Callable<?> getMapping(final Identifier identifier);
    default A3Callable<?> getMapping(final String namespace, final String name) {
        return getMapping(new DefaultIdentifier(namespace, name));
    }

    Object createObject(final Identifier identifier);
    default Object createObject(final String namespace, final String name) {
        return createObject(new DefaultIdentifier(namespace, name));
    }
    default Identifier createIdentifier(final String namespace, final String name) {
        return new DefaultIdentifier(namespace, name);
    }

}
