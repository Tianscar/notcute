package a3wt.bundle;

import a3wt.util.A3Callable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static a3wt.util.A3Preconditions.checkArgNotNull;

public class DefaultA3BundleKit implements A3BundleKit {

    protected final Map<Class<? extends A3ExtMapBundle.Delegate>, A3Callable<A3ExtMapBundle.Delegate>> extMapBundleDelegateMappings = new ConcurrentHashMap<>();

    @Override
    public Map<Class<? extends A3ExtMapBundle.Delegate>, A3Callable<A3ExtMapBundle.Delegate>> getExtMapBundleDelegateMappings() {
        return extMapBundleDelegateMappings;
    }

    @Override
    public void addExtMapBundleDelegateMapping(Class<? extends A3ExtMapBundle.Delegate> typeClass, A3Callable<A3ExtMapBundle.Delegate> mapping) {
        checkArgNotNull(typeClass, "typeClass");
        checkArgNotNull(mapping, "mapping");
        extMapBundleDelegateMappings.put(typeClass, mapping);
    }

    @Override
    public A3ExtMapBundle.Delegate createDelegate(final Class<? extends A3ExtMapBundle.Delegate> typeClass) {
        checkArgNotNull(typeClass, "typeClass");
        final A3Callable<A3ExtMapBundle.Delegate> callable = extMapBundleDelegateMappings.get(typeClass);
        return callable == null ? null : callable.call();
    }

}
