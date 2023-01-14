package a3wt.bundle;

import a3wt.util.A3Callable;
import a3wt.util.A3Maps;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultA3BundleKit implements A3BundleKit {

    protected final Map<Class<? extends A3ExtMapBundle.Delegate>, A3Callable<? extends A3ExtMapBundle.Delegate>> extMapBundleDelegateMappings =
            A3Maps.checkNullMap(new ConcurrentHashMap<>());

    @Override
    public Map<Class<? extends A3ExtMapBundle.Delegate>, A3Callable<? extends A3ExtMapBundle.Delegate>> getExtMapBundleDelegateMappings() {
        return extMapBundleDelegateMappings;
    }

    @Override
    public A3Callable<? extends A3ExtMapBundle.Delegate> putExtMapBundleDelegateMapping(final Class<? extends A3ExtMapBundle.Delegate> typeClass,
                                                                                        final A3Callable<? extends A3ExtMapBundle.Delegate> mapping) {
        return extMapBundleDelegateMappings.put(typeClass, mapping);
    }

    @Override
    public A3Callable<? extends A3ExtMapBundle.Delegate> removeExtMapBundleDelegateMapping(final Class<? extends A3ExtMapBundle.Delegate> typeClass) {
        return extMapBundleDelegateMappings.remove(typeClass);
    }

    @Override
    public A3Callable<? extends A3ExtMapBundle.Delegate> getExtMapBundleDelegateMapping(final Class<? extends A3ExtMapBundle.Delegate> typeClass) {
        return extMapBundleDelegateMappings.get(typeClass);
    }

    @Override
    public void clearExtMapBundleMappings() {
        extMapBundleDelegateMappings.clear();
    }

    @Override
    public A3ExtMapBundle.Delegate createExtMapBundleDelegate(final Class<? extends A3ExtMapBundle.Delegate> typeClass) {
        final A3Callable<? extends A3ExtMapBundle.Delegate> callable = extMapBundleDelegateMappings.get(typeClass);
        return callable == null ? null : callable.call();
    }

}
