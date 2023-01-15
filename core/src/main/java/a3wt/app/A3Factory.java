package a3wt.app;

import a3wt.util.A3Supplier;

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

    Map<Identifier, A3Supplier<?>> getMappings();
    A3Supplier<?> putMapping(final Identifier identifier, final A3Supplier<?> mapping);
    default A3Supplier<?> putMapping(final String namespace, final String name, final A3Supplier<?> mapping) {
        return putMapping(new DefaultIdentifier(namespace, name), mapping);
    }
    A3Supplier<?> removeMapping(final Identifier identifier);
    default A3Supplier<?> removeMapping(final String namespace, final String name) {
        return removeMapping(new DefaultIdentifier(namespace, name));
    }
    void clearMappings();
    A3Supplier<?> getMapping(final Identifier identifier);
    default A3Supplier<?> getMapping(final String namespace, final String name) {
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
