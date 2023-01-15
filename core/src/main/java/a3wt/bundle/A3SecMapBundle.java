package a3wt.bundle;

import a3wt.util.A3Map;

public interface A3SecMapBundle extends A3KeyValueBundle<A3SecMapBundle>, A3Map<String, A3MapBundle> {

    String getKey();
    void setKey(final String key);

    A3MapBundle get(final String key, final A3MapBundle defValue);

    A3MapBundle get();

}
