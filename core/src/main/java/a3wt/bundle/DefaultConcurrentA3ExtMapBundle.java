package a3wt.bundle;

import java.util.concurrent.ConcurrentHashMap;

public class DefaultConcurrentA3ExtMapBundle extends AbstractDefaultA3ExtMapBundle {

    protected DefaultConcurrentA3ExtMapBundle(final A3BundleKit bundleKit) {
        super(bundleKit, new ConcurrentHashMap<>());
    }

    @Override
    public boolean isConcurrent() {
        return false;
    }

    @Override
    protected AbstractDefaultA3ExtMapBundle createExtMapBundle() {
        return new DefaultA3ExtMapBundle(bundleKit);
    }

}
