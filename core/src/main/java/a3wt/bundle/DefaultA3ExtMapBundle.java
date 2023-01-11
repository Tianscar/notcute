package a3wt.bundle;

import java.util.HashMap;

public class DefaultA3ExtMapBundle extends AbstractDefaultA3ExtMapBundle {

    public DefaultA3ExtMapBundle(final A3BundleKit bundleKit) {
        super(bundleKit, new HashMap<>());
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
