package a3wt.bundle;

import a3wt.util.A3Callable;

import java.util.Map;

public interface A3BundleKit {

    default A3MapBundle createMapBundle() {
        return new DefaultA3MapBundle();
    }

    default A3MapBundle createMapBundle(final boolean concurrent) {
        return concurrent ? new DefaultConcurrentA3MapBundle() : new DefaultA3MapBundle();
    }

    default A3MapBundle createConcurrentMapBundle() {
        return new DefaultConcurrentA3MapBundle();
    }

    default A3SecMapBundle createSecMapBundle() {
        return new DefaultA3SecMapBundle();
    }

    default A3SecMapBundle createSecMapBundle(final boolean concurrent) {
        return concurrent ? new DefaultConcurrentA3SecMapBundle() : new DefaultA3SecMapBundle();
    }

    default A3SecMapBundle createConcurrentSecMapBundle() {
        return new DefaultConcurrentA3SecMapBundle();
    }

    Map<Class<? extends A3ExtMapBundle.Delegate>, A3Callable<A3ExtMapBundle.Delegate>> getExtMapBundleDelegateMappings();
    void addExtMapBundleDelegateMapping(final Class<? extends A3ExtMapBundle.Delegate> typeClass, final A3Callable<A3ExtMapBundle.Delegate> mapping);

    default A3ExtMapBundle createExtMapBundle() {
        return new DefaultA3ExtMapBundle(this);
    }

    default A3ExtMapBundle createExtMapBundle(final boolean concurrent) {
        return concurrent ? new DefaultConcurrentA3ExtMapBundle(this) : new DefaultA3ExtMapBundle(this);
    }

    default A3ExtMapBundle createConcurrentExtMapBundle() {
        return new DefaultConcurrentA3ExtMapBundle(this);
    }

    A3ExtMapBundle.Delegate createDelegate(final Class<? extends A3ExtMapBundle.Delegate> typeClass);

}
