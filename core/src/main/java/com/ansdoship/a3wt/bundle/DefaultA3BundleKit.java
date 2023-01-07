package com.ansdoship.a3wt.bundle;

import com.ansdoship.a3wt.util.A3Callable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

public class DefaultA3BundleKit implements A3BundleKit {

    protected final Map<Class<? extends A3ExtMapBundle.Delegate>, A3Callable<A3ExtMapBundle.Delegate>> extMapBundleDelegateMappings = new ConcurrentHashMap<>();

    public Map<Class<? extends A3ExtMapBundle.Delegate>, A3Callable<A3ExtMapBundle.Delegate>> getExtMapBundleDelegateMappings() {
        return extMapBundleDelegateMappings;
    }

    @Override
    public A3ExtMapBundle.Delegate createDelegate(final Class<? extends A3ExtMapBundle.Delegate> typeClass) {
        checkArgNotNull(typeClass, "typeClass");
        final A3Callable<A3ExtMapBundle.Delegate> callable = extMapBundleDelegateMappings.get(typeClass);
        return callable == null ? null : callable.call();
    }

}
