package io.notcute.g2d.swt;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class SIIORegistry {

    private static final Map<Class<? extends SIIOServiceProvider>, SIIOServiceProvider> providers = new ConcurrentHashMap<>();

    private SIIORegistry() {
        throw new UnsupportedOperationException();
    }

    static {
        registerServiceProvider(new BasicSIIOSpi());
    }

    public static boolean registerServiceProvider(SIIOServiceProvider provider) {
        if (!providers.containsKey(provider.getClass())) {
            providers.put(provider.getClass(), provider);
            return true;
        }
        return false;
    }

    public static void registerServiceProviders(Iterator<SIIOServiceProvider> providers) {
        while (providers.hasNext()) {
            SIIOServiceProvider provider = providers.next();
            registerServiceProvider(provider);
        }
    }

    public static boolean deregisterServiceProvider(Class<? extends SIIOServiceProvider> clazz) {
        return providers.remove(clazz) != null;
    }

    public static void deregisterServiceProviders(Iterator<Class<? extends SIIOServiceProvider>> clazz) {
        while (clazz.hasNext()) {
            Class<? extends SIIOServiceProvider> provider = clazz.next();
            deregisterServiceProvider(provider);
        }
    }

    public static Collection<SIIOServiceProvider> getServiceProviders() {
        return providers.values();
    }

    public static SIIOServiceProvider getReader(String readerFormat) {
        for (SIIOServiceProvider provider : getServiceProviders()) {
            for (String mReaderFormat : provider.getReaderMIMETypes()) {
                if (mReaderFormat.equalsIgnoreCase(readerFormat)) return provider;
            }
        }
        return null;
    }

    public static SIIOServiceProvider getWriter(String writerFormat) {
        for (SIIOServiceProvider provider : getServiceProviders()) {
            for (String mWriterFormat : provider.getWriterMIMETypes()) {
                if (mWriterFormat.equalsIgnoreCase(writerFormat)) return provider;
            }
        }
        return null;
    }

    public static boolean contains(SIIOServiceProvider provider) {
        return providers.containsValue(provider);
    }

    public static boolean contains(Class<? extends SIIOServiceProvider> clazz) {
        return providers.containsKey(clazz);
    }

    public static void clear() {
        providers.clear();
    }

}
