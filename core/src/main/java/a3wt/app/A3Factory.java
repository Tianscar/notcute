package a3wt.app;

import a3wt.util.A3Callable;

import java.util.Map;

import static a3wt.util.A3Preconditions.checkArgNotEmpty;

public interface A3Factory {

    class Identifier {
        protected final String namespace;
        protected final String name;
        public Identifier(final String namespace, final String name) {
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
    void addMapping(final Identifier identifier, final A3Callable<?> mapping);
    default void addMapping(final String namespace, final String name, final A3Callable<?> mapping) {
        addMapping(new Identifier(namespace, name), mapping);
    }
    Object create(final Identifier identifier);
    default Object create(final String namespace, final String name) {
        return create(new Identifier(namespace, name));
    }

}
