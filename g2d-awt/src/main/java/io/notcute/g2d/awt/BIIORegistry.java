package io.notcute.g2d.awt;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class BIIORegistry {

    private static final Map<Class<? extends BIIOServiceProvider>, BIIOServiceProvider> providers = new ConcurrentHashMap<>();

    private BIIORegistry() {
        throw new UnsupportedOperationException();
    }

    static {
        registerServiceProvider(new BMPBIIOSpi());
        registerServiceProvider(new JPEGBIIOSpi());
        registerServiceProvider(new PNGBIIOSpi());
    }

    public static boolean registerServiceProvider(BIIOServiceProvider provider) {
        if (!providers.containsKey(provider.getClass())) {
            providers.put(provider.getClass(), provider);
            return true;
        }
        return false;
    }

    public static void registerServiceProviders(Iterator<BIIOServiceProvider> providers) {
        while (providers.hasNext()) {
            BIIOServiceProvider provider = providers.next();
            registerServiceProvider(provider);
        }
    }

    public static boolean deregisterServiceProvider(Class<? extends BIIOServiceProvider> clazz) {
        return providers.remove(clazz) != null;
    }

    public static void deregisterServiceProviders(Iterator<Class<? extends BIIOServiceProvider>> clazz) {
        while (clazz.hasNext()) {
            Class<? extends BIIOServiceProvider> provider = clazz.next();
            deregisterServiceProvider(provider);
        }
    }

    public static Collection<BIIOServiceProvider> getServiceProviders() {
        return providers.values();
    }

    public static BIIOServiceProvider getReader(String mimeType) {
        for (BIIOServiceProvider provider : getServiceProviders()) {
            for (String mReaderFormat : provider.getReaderMIMETypes()) {
                if (mReaderFormat.equalsIgnoreCase(mimeType)) return provider;
            }
        }
        return null;
    }

    public static BIIOServiceProvider getWriter(String mimeType) {
        for (BIIOServiceProvider provider : getServiceProviders()) {
            for (String mWriterFormat : provider.getWriterMIMETypes()) {
                if (mWriterFormat.equalsIgnoreCase(mimeType)) return provider;
            }
        }
        return null;
    }

    public static boolean contains(BIIOServiceProvider provider) {
        return providers.containsValue(provider);
    }

    public static boolean contains(Class<? extends BIIOServiceProvider> clazz) {
        return providers.containsKey(clazz);
    }

    public static void clear() {
        providers.clear();
    }

}
