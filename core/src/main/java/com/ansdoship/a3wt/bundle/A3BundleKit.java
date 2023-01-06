package com.ansdoship.a3wt.bundle;

public interface A3BundleKit {

    A3I18NBundle getI18NBundle();

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
        return new DefaultA3ExtMapBundle();
    }

    default A3ExtMapBundle createExtMapBundle(final boolean concurrent) {
        return concurrent ? new DefaultConcurrentA3ExtMapBundle() : new DefaultA3ExtMapBundle();
    }

    default A3ExtMapBundle createConcurrentExtMapBundle() {
        return new DefaultConcurrentA3ExtMapBundle();
    }

}
