package io.notcute.g2d.android;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class BIORegistry {

    private static final Map<Class<? extends BIOServiceProvider>, BIOServiceProvider> providers = new ConcurrentHashMap<>();

    private BIORegistry() {
        throw new UnsupportedOperationException();
    }

    static {
        registerServiceProvider(new BasicBIOSpi());
    }

    public static boolean registerServiceProvider(BIOServiceProvider provider) {
        if (!providers.containsKey(provider.getClass())) {
            providers.put(provider.getClass(), provider);
            return true;
        }
        return false;
    }

    public static void registerServiceProviders(Iterator<BIOServiceProvider> providers) {
        while (providers.hasNext()) {
            BIOServiceProvider provider = providers.next();
            registerServiceProvider(provider);
        }
    }

    public static boolean deregisterServiceProvider(Class<? extends BIOServiceProvider> clazz) {
        return providers.remove(clazz) != null;
    }

    public static void deregisterServiceProviders(Iterator<Class<? extends BIOServiceProvider>> clazz) {
        while (clazz.hasNext()) {
            Class<? extends BIOServiceProvider> provider = clazz.next();
            deregisterServiceProvider(provider);
        }
    }

    public static Collection<BIOServiceProvider> getServiceProviders() {
        return providers.values();
    }

    public static BIOServiceProvider getReader(String readerFormat) {
        for (BIOServiceProvider provider : getServiceProviders()) {
            for (String mReaderFormat : provider.getReaderFormatNames()) {
                if (mReaderFormat.equalsIgnoreCase(readerFormat)) return provider;
            }
        }
        return null;
    }

    public static BIOServiceProvider getWriter(String writerFormat) {
        for (BIOServiceProvider provider : getServiceProviders()) {
            for (String mWriterFormat : provider.getWriterFormatNames()) {
                if (mWriterFormat.equalsIgnoreCase(writerFormat)) return provider;
            }
        }
        return null;
    }

    public static boolean contains(BIOServiceProvider provider) {
        return providers.containsValue(provider);
    }

    public static boolean contains(Class<? extends BIOServiceProvider> clazz) {
        return providers.containsKey(clazz);
    }

    public static void clear() {
        providers.clear();
    }

}
