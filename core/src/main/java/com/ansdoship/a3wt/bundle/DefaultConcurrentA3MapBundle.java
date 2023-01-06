package com.ansdoship.a3wt.bundle;

import java.util.concurrent.ConcurrentHashMap;

public class DefaultConcurrentA3MapBundle extends AbstractDefaultA3MapBundle {

    public DefaultConcurrentA3MapBundle() {
        super(new ConcurrentHashMap<>());
    }

    @Override
    public boolean isConcurrent() {
        return true;
    }

}
