package a3wt.bundle;

import a3wt.util.A3Supplier;
import a3wt.util.A3Maps;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultA3BundleKit implements A3BundleKit {

    protected final Map<Class<? extends A3ExtMapBundle.Bundleable>, A3Supplier<? extends A3ExtMapBundle.Bundleable>> extMapBundleableMappings =
            A3Maps.checkNullMap(new ConcurrentHashMap<>());

    @Override
    public Map<Class<? extends A3ExtMapBundle.Bundleable>, A3Supplier<? extends A3ExtMapBundle.Bundleable>> getExtMapBundleableMappings() {
        return extMapBundleableMappings;
    }

    @Override
    public A3Supplier<? extends A3ExtMapBundle.Bundleable> putExtMapBundleableMapping(final Class<? extends A3ExtMapBundle.Bundleable> typeClass,
                                                                                        final A3Supplier<? extends A3ExtMapBundle.Bundleable> mapping) {
        return extMapBundleableMappings.put(typeClass, mapping);
    }

    @Override
    public A3Supplier<? extends A3ExtMapBundle.Bundleable> removeExtMapBundleableMapping(final Class<? extends A3ExtMapBundle.Bundleable> typeClass) {
        return extMapBundleableMappings.remove(typeClass);
    }

    @Override
    public A3Supplier<? extends A3ExtMapBundle.Bundleable> getExtMapBundleableMapping(final Class<? extends A3ExtMapBundle.Bundleable> typeClass) {
        return extMapBundleableMappings.get(typeClass);
    }

    @Override
    public void clearExtMapBundleMappings() {
        extMapBundleableMappings.clear();
    }

    @Override
    public A3ExtMapBundle.Bundleable createExtMapBundleable(final Class<? extends A3ExtMapBundle.Bundleable> typeClass) {
        final A3Supplier<? extends A3ExtMapBundle.Bundleable> supplier = extMapBundleableMappings.get(typeClass);
        return supplier == null ? null : supplier.get();
    }

}
