package com.ansdoship.a3wt.bundle;

import java.util.concurrent.ConcurrentHashMap;

public class DefaultConcurrentA3ExtMapBundle extends AbstractDefaultA3ExtMapBundle {

    protected DefaultConcurrentA3ExtMapBundle() {
        super(new ConcurrentHashMap<>());
    }

    @Override
    public boolean isConcurrent() {
        return false;
    }

}
