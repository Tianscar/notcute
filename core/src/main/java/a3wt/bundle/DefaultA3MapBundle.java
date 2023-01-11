package a3wt.bundle;

import java.util.HashMap;

public class DefaultA3MapBundle extends AbstractDefaultA3MapBundle {

    public DefaultA3MapBundle() {
        super(new HashMap<>());
    }

    @Override
    public boolean isConcurrent() {
        return false;
    }

}
