package io.notcute.g2d.awt;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class MFBIIORegistry {

    private static final Map<Class<? extends MFBIIOServiceProvider>, MFBIIOServiceProvider> providers = new ConcurrentHashMap<>();

    private MFBIIORegistry() {
        throw new UnsupportedOperationException();
    }

    static {
        registerServiceProvider(new GIFMFBIIOSpi());
        registerServiceProvider(new TIFFMFBIIOSpi());
    }

    public static boolean registerServiceProvider(MFBIIOServiceProvider provider) {
        if (!providers.containsKey(provider.getClass())) {
            providers.put(provider.getClass(), provider);
            return true;
        }
        return false;
    }

    public static void registerServiceProviders(Iterator<MFBIIOServiceProvider> providers) {
        while (providers.hasNext()) {
            MFBIIOServiceProvider provider = providers.next();
            registerServiceProvider(provider);
        }
    }

    public static boolean deregisterServiceProvider(Class<? extends MFBIIOServiceProvider> clazz) {
        return providers.remove(clazz) != null;
    }

    public static void deregisterServiceProviders(Iterator<Class<? extends MFBIIOServiceProvider>> clazz) {
        while (clazz.hasNext()) {
            Class<? extends MFBIIOServiceProvider> provider = clazz.next();
            deregisterServiceProvider(provider);
        }
    }

    public static Collection<MFBIIOServiceProvider> getServiceProviders() {
        return providers.values();
    }

    public static MFBIIOServiceProvider getReader(String mimeType) {
        for (MFBIIOServiceProvider provider : getServiceProviders()) {
            for (String mReaderFormat : provider.getReaderMIMETypes()) {
                if (mReaderFormat.equalsIgnoreCase(mimeType)) return provider;
            }
        }
        return null;
    }

    public static MFBIIOServiceProvider getWriter(String mimeType) {
        for (MFBIIOServiceProvider provider : getServiceProviders()) {
            for (String mWriterFormat : provider.getWriterMIMETypes()) {
                if (mWriterFormat.equalsIgnoreCase(mimeType)) return provider;
            }
        }
        return null;
    }

    public static boolean contains(MFBIIOServiceProvider provider) {
        return providers.containsValue(provider);
    }

    public static boolean contains(Class<? extends MFBIIOServiceProvider> clazz) {
        return providers.containsKey(clazz);
    }

    public static void clear() {
        providers.clear();
    }

}
