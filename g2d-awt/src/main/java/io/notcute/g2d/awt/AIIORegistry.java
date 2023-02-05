package io.notcute.g2d.awt;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class AIIORegistry {

    private static final Map<Class<? extends AIIOServiceProvider>, AIIOServiceProvider> providers = new ConcurrentHashMap<>();

    private AIIORegistry() {
        throw new UnsupportedOperationException();
    }

    static {
        registerServiceProvider(new GIFAIIOSpi());
        registerServiceProvider(new TIFFAIIOSpi());
    }

    public static boolean registerServiceProvider(AIIOServiceProvider provider) {
        if (!providers.containsKey(provider.getClass())) {
            providers.put(provider.getClass(), provider);
            return true;
        }
        return false;
    }

    public static void registerServiceProviders(Iterator<AIIOServiceProvider> providers) {
        while (providers.hasNext()) {
            AIIOServiceProvider provider = providers.next();
            registerServiceProvider(provider);
        }
    }

    public static boolean deregisterServiceProvider(Class<? extends AIIOServiceProvider> clazz) {
        return providers.remove(clazz) != null;
    }

    public static void deregisterServiceProviders(Iterator<Class<? extends AIIOServiceProvider>> clazz) {
        while (clazz.hasNext()) {
            Class<? extends AIIOServiceProvider> provider = clazz.next();
            deregisterServiceProvider(provider);
        }
    }

    public static Collection<AIIOServiceProvider> getServiceProviders() {
        return providers.values();
    }

    public static AIIOServiceProvider getReader(String readerFormat) {
        for (AIIOServiceProvider provider : getServiceProviders()) {
            for (String mReaderFormat : provider.getReaderFormatNames()) {
                if (mReaderFormat.equalsIgnoreCase(readerFormat)) return provider;
            }
        }
        return null;
    }

    public static AIIOServiceProvider getWriter(String writerFormat) {
        for (AIIOServiceProvider provider : getServiceProviders()) {
            for (String mWriterFormat : provider.getWriterFormatNames()) {
                if (mWriterFormat.equalsIgnoreCase(writerFormat)) return provider;
            }
        }
        return null;
    }

    public static boolean contains(AIIOServiceProvider provider) {
        return providers.containsValue(provider);
    }

    public static boolean contains(Class<? extends AIIOServiceProvider> clazz) {
        return providers.containsKey(clazz);
    }

    public static void clear() {
        providers.clear();
    }

}
