package io.notcute.g2d.android;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ABIORegistry {

    private static final Map<Class<? extends ABIOServiceProvider>, ABIOServiceProvider> providers = new ConcurrentHashMap<>();

    private ABIORegistry() {
        throw new UnsupportedOperationException();
    }

    static {

    }

    public static boolean registerServiceProvider(ABIOServiceProvider provider) {
        if (!providers.containsKey(provider.getClass())) {
            providers.put(provider.getClass(), provider);
            return true;
        }
        return false;
    }

    public static void registerServiceProviders(Iterator<ABIOServiceProvider> providers) {
        while (providers.hasNext()) {
            ABIOServiceProvider provider = providers.next();
            registerServiceProvider(provider);
        }
    }

    public static boolean deregisterServiceProvider(Class<? extends ABIOServiceProvider> clazz) {
        return providers.remove(clazz) != null;
    }

    public static void deregisterServiceProviders(Iterator<Class<? extends ABIOServiceProvider>> clazz) {
        while (clazz.hasNext()) {
            Class<? extends ABIOServiceProvider> provider = clazz.next();
            deregisterServiceProvider(provider);
        }
    }

    public static Collection<ABIOServiceProvider> getServiceProviders() {
        return providers.values();
    }

    public static ABIOServiceProvider getReader(String readerFormat) {
        for (ABIOServiceProvider provider : getServiceProviders()) {
            for (String mReaderFormat : provider.getReaderFormatNames()) {
                if (mReaderFormat.equalsIgnoreCase(readerFormat)) return provider;
            }
        }
        return null;
    }

    public static ABIOServiceProvider getWriter(String writerFormat) {
        for (ABIOServiceProvider provider : getServiceProviders()) {
            for (String mWriterFormat : provider.getWriterFormatNames()) {
                if (mWriterFormat.equalsIgnoreCase(writerFormat)) return provider;
            }
        }
        return null;
    }

    public static boolean contains(ABIOServiceProvider provider) {
        return providers.containsValue(provider);
    }

    public static boolean contains(Class<? extends ABIOServiceProvider> clazz) {
        return providers.containsKey(clazz);
    }

    public static void clear() {
        providers.clear();
    }

}
