package a3wt.bundle;

import a3wt.util.A3Supplier;

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

    default A3ExtMapBundle createExtMapBundle() {
        return new DefaultA3ExtMapBundle(this);
    }

    default A3ExtMapBundle createExtMapBundle(final boolean concurrent) {
        return concurrent ? new DefaultConcurrentA3ExtMapBundle(this) : new DefaultA3ExtMapBundle(this);
    }

    default A3ExtMapBundle createConcurrentExtMapBundle() {
        return new DefaultConcurrentA3ExtMapBundle(this);
    }

    Map<Class<? extends A3ExtMapBundle.Bundleable>, A3Supplier<? extends A3ExtMapBundle.Bundleable>> getExtMapBundleableMappings();
    A3Supplier<? extends A3ExtMapBundle.Bundleable> putExtMapBundleableMapping(final Class<? extends A3ExtMapBundle.Bundleable> typeClass, final A3Supplier<? extends A3ExtMapBundle.Bundleable> mapping);
    A3Supplier<? extends A3ExtMapBundle.Bundleable> removeExtMapBundleableMapping(final Class<? extends A3ExtMapBundle.Bundleable> typeClass);
    A3Supplier<? extends A3ExtMapBundle.Bundleable> getExtMapBundleableMapping(final Class<? extends A3ExtMapBundle.Bundleable> typeClass);
    void clearExtMapBundleMappings();

    A3ExtMapBundle.Bundleable createExtMapBundleable(final Class<? extends A3ExtMapBundle.Bundleable> typeClass);

}
