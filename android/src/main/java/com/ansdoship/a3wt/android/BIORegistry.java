package com.ansdoship.a3wt.android;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.ServiceLoader;
import java.util.Objects;
import java.util.Iterator;

public enum BIORegistry {

    INSTANCE;

    private final Map<Class<? extends BIOServiceProvider>, BIOServiceProvider> providers = new HashMap<>();

    BIORegistry() {
        registerBasicServiceProviders();
        registerServiceProviders(Objects.requireNonNull(Thread.currentThread().getContextClassLoader()), false);
    }

    public void registerBasicServiceProviders() {
        registerServiceProvider(new BasicBIOSpi());
    }

    public void registerServiceProviders(@NonNull ClassLoader classLoader, boolean refresh) {
        ServiceLoader<BIOServiceProvider> serviceLoader = ServiceLoader.load(BIOServiceProvider.class, classLoader);
        if (refresh) {
            serviceLoader.reload();
        }
        for (BIOServiceProvider provider : serviceLoader) {
            registerServiceProvider(provider);
        }
    }

    public boolean registerServiceProvider(@NonNull BIOServiceProvider provider) {
        if (!providers.containsKey(provider.getClass())) {
            providers.put(provider.getClass(), provider);
            return true;
        }
        return false;
    }

    public void registerServiceProviders(@NonNull Iterator<BIOServiceProvider> providers) {
        while (providers.hasNext()) {
            BIOServiceProvider provider = providers.next();
            registerServiceProvider(provider);
        }
    }

    public boolean deregisterServiceProvider(@NonNull Class<? extends BIOServiceProvider> clazz) {
        return providers.remove(clazz) != null;
    }

    public void deregisterServiceProviders(@NonNull Iterator<Class<? extends BIOServiceProvider>> clazz) {
        while (clazz.hasNext()) {
            Class<? extends BIOServiceProvider> provider = clazz.next();
            deregisterServiceProvider(provider);
        }
    }

    public Collection<BIOServiceProvider> getServiceProviders() {
        return providers.values();
    }

    public BIOServiceProvider getReader(@NonNull String readerFormat) {
        for (BIOServiceProvider provider : getServiceProviders()) {
            for (String mReaderFormat : provider.getReaderFormatNames()) {
                if (mReaderFormat.equalsIgnoreCase(readerFormat)) return provider;
            }
        }
        return null;
    }

    public BIOServiceProvider getWriter(@NonNull String writerFormat) {
        for (BIOServiceProvider provider : getServiceProviders()) {
            for (String mWriterFormat : provider.getWriterFormatNames()) {
                if (mWriterFormat.equalsIgnoreCase(writerFormat)) return provider;
            }
        }
        return null;
    }

    public boolean contains(@NonNull BIOServiceProvider provider) {
        return providers.containsValue(provider);
    }

    public boolean contains(@NonNull Class<? extends BIOServiceProvider> clazz) {
        return providers.containsKey(clazz);
    }

    public void clear() {
        providers.clear();
    }

}
