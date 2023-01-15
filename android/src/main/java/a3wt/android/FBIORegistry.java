package a3wt.android;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class FBIORegistry {

    private static final Map<Class<? extends FBIOServiceProvider>, FBIOServiceProvider> providers = new ConcurrentHashMap<>();

    private FBIORegistry(){}

    static {
        registerBasicServiceProviders();
    }

    public static void registerBasicServiceProviders() {
        registerServiceProvider(new GifFBIOSpi());
    }

    public static boolean registerServiceProvider(FBIOServiceProvider provider) {
        if (!providers.containsKey(provider.getClass())) {
            providers.put(provider.getClass(), provider);
            return true;
        }
        return false;
    }

    public static void registerServiceProviders(Iterator<FBIOServiceProvider> providers) {
        while (providers.hasNext()) {
            FBIOServiceProvider provider = providers.next();
            registerServiceProvider(provider);
        }
    }

    public static boolean deregisterServiceProvider(Class<? extends FBIOServiceProvider> clazz) {
        return providers.remove(clazz) != null;
    }

    public static void deregisterServiceProviders(Iterator<Class<? extends FBIOServiceProvider>> clazz) {
        while (clazz.hasNext()) {
            Class<? extends FBIOServiceProvider> provider = clazz.next();
            deregisterServiceProvider(provider);
        }
    }

    public static Collection<FBIOServiceProvider> getServiceProviders() {
        return providers.values();
    }

    public static FBIOServiceProvider getReader(String readerFormat) {
        for (FBIOServiceProvider provider : getServiceProviders()) {
            for (String mReaderFormat : provider.getReaderFormatNames()) {
                if (mReaderFormat.equalsIgnoreCase(readerFormat)) return provider;
            }
        }
        return null;
    }

    public static FBIOServiceProvider getWriter(String writerFormat) {
        for (FBIOServiceProvider provider : getServiceProviders()) {
            for (String mWriterFormat : provider.getWriterFormatNames()) {
                if (mWriterFormat.equalsIgnoreCase(writerFormat)) return provider;
            }
        }
        return null;
    }

    public static boolean contains(FBIOServiceProvider provider) {
        return providers.containsValue(provider);
    }

    public static boolean contains(Class<? extends FBIOServiceProvider> clazz) {
        return providers.containsKey(clazz);
    }

    public static void clear() {
        providers.clear();
    }

}
