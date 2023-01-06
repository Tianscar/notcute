package com.ansdoship.a3wt.bundle;

import java.util.HashMap;

public class DefaultA3ExtMapBundle extends AbstractDefaultA3ExtMapBundle {

    protected DefaultA3ExtMapBundle() {
        super(new HashMap<>());
    }

    @Override
    public boolean isConcurrent() {
        return false;
    }

}
