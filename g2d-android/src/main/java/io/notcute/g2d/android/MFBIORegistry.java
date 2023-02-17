package io.notcute.g2d.android;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class MFBIORegistry {

    private static final Map<Class<? extends MFBIOServiceProvider>, MFBIOServiceProvider> providers = new ConcurrentHashMap<>();

    private MFBIORegistry() {
        throw new UnsupportedOperationException();
    }

    static {

    }

    public static boolean registerServiceProvider(MFBIOServiceProvider provider) {
        if (!providers.containsKey(provider.getClass())) {
            providers.put(provider.getClass(), provider);
            return true;
        }
        return false;
    }

    public static void registerServiceProviders(Iterator<MFBIOServiceProvider> providers) {
        while (providers.hasNext()) {
            MFBIOServiceProvider provider = providers.next();
            registerServiceProvider(provider);
        }
    }

    public static boolean deregisterServiceProvider(Class<? extends MFBIOServiceProvider> clazz) {
        return providers.remove(clazz) != null;
    }

    public static void deregisterServiceProviders(Iterator<Class<? extends MFBIOServiceProvider>> clazz) {
        while (clazz.hasNext()) {
            Class<? extends MFBIOServiceProvider> provider = clazz.next();
            deregisterServiceProvider(provider);
        }
    }

    public static Collection<MFBIOServiceProvider> getServiceProviders() {
        return providers.values();
    }

    public static MFBIOServiceProvider getReader(String readerFormat) {
        for (MFBIOServiceProvider provider : getServiceProviders()) {
            for (String mReaderFormat : provider.getReaderMIMETypes()) {
                if (mReaderFormat.equalsIgnoreCase(readerFormat)) return provider;
            }
        }
        return null;
    }

    public static MFBIOServiceProvider getWriter(String writerFormat) {
        for (MFBIOServiceProvider provider : getServiceProviders()) {
            for (String mWriterFormat : provider.getWriterMIMETypes()) {
                if (mWriterFormat.equalsIgnoreCase(writerFormat)) return provider;
            }
        }
        return null;
    }

    public static boolean contains(MFBIOServiceProvider provider) {
        return providers.containsValue(provider);
    }

    public static boolean contains(Class<? extends MFBIOServiceProvider> clazz) {
        return providers.containsKey(clazz);
    }

    public static void clear() {
        providers.clear();
    }

}
