package com.ansdoship.a3wt.android;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.ServiceLoader;
import java.util.Objects;
import java.util.Iterator;

public final class BIORegistry {

    private static final Map<Class<? extends BIOServiceProvider>, BIOServiceProvider> providers = new HashMap<>();

    private BIORegistry(){}

    static {
        registerBasicServiceProviders();
        registerServiceProviders(Objects.requireNonNull(Thread.currentThread().getContextClassLoader()), false);
    }

    public static void registerBasicServiceProviders() {
        registerServiceProvider(new BasicBIOSpi());
    }

    public static void registerServiceProviders(@NonNull ClassLoader classLoader, boolean refresh) {
        ServiceLoader<BIOServiceProvider> serviceLoader = ServiceLoader.load(BIOServiceProvider.class, classLoader);
        if (refresh) {
            serviceLoader.reload();
        }
        for (BIOServiceProvider provider : serviceLoader) {
            registerServiceProvider(provider);
        }
    }

    public static boolean registerServiceProvider(@NonNull BIOServiceProvider provider) {
        if (!providers.containsKey(provider.getClass())) {
            providers.put(provider.getClass(), provider);
            return true;
        }
        return false;
    }

    public static void registerServiceProviders(@NonNull Iterator<BIOServiceProvider> providers) {
        while (providers.hasNext()) {
            BIOServiceProvider provider = providers.next();
            registerServiceProvider(provider);
        }
    }

    public static boolean deregisterServiceProvider(@NonNull Class<? extends BIOServiceProvider> clazz) {
        return providers.remove(clazz) != null;
    }

    public static void deregisterServiceProviders(@NonNull Iterator<Class<? extends BIOServiceProvider>> clazz) {
        while (clazz.hasNext()) {
            Class<? extends BIOServiceProvider> provider = clazz.next();
            deregisterServiceProvider(provider);
        }
    }

    public static Collection<BIOServiceProvider> getServiceProviders() {
        return providers.values();
    }

    public static BIOServiceProvider getReader(@NonNull String readerFormat) {
        for (BIOServiceProvider provider : getServiceProviders()) {
            for (String mReaderFormat : provider.getReaderFormatNames()) {
                if (mReaderFormat.equalsIgnoreCase(readerFormat)) return provider;
            }
        }
        return null;
    }

    public static BIOServiceProvider getWriter(@NonNull String writerFormat) {
        for (BIOServiceProvider provider : getServiceProviders()) {
            for (String mWriterFormat : provider.getWriterFormatNames()) {
                if (mWriterFormat.equalsIgnoreCase(writerFormat)) return provider;
            }
        }
        return null;
    }

    public static boolean contains(@NonNull BIOServiceProvider provider) {
        return providers.containsValue(provider);
    }

    public static boolean contains(@NonNull Class<? extends BIOServiceProvider> clazz) {
        return providers.containsKey(clazz);
    }

    public static void clear() {
        providers.clear();
    }

}
