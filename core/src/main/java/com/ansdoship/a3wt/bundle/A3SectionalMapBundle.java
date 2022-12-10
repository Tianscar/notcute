package com.ansdoship.a3wt.bundle;

import java.util.Map;

public interface A3SectionalMapBundle extends A3MapBundle, Map<String, A3MapBundle> {

    String getKey();
    void setKey(final String key);

    A3MapBundle get(final String key, final A3MapBundle defValue);

    A3MapBundle get();

}
