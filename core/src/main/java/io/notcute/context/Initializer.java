package io.notcute.context;

import java.util.ServiceLoader;

public abstract class Initializer {

    private static volatile boolean allInitialized = false;
    public static void runInitializers() {
        if (allInitialized) return;
        allInitialized = true;
        ServiceLoader<Initializer> serviceLoader = ServiceLoader.load(Initializer.class, Initializer.class.getClassLoader());
        for (Initializer initializer : serviceLoader) {
            initializer.initialize();
        }
    }

    public abstract void initialize();

}
