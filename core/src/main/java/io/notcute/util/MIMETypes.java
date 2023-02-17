package io.notcute.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MIMETypes extends AbstractExpandable<MIMETypes.Expansion> {

    public interface Expansion extends Expandable.Expansion {
        String[] getMIMETypes(String extension);
        String[] getExtensions(String mimeType);
    }

    public String[] getMIMETypes(String extension) {
        if (extension == null) return new String[0];
        Set<String> mimeTypes = new HashSet<>();
        for (Expansion expansion : getExpansions()) {
            mimeTypes.addAll(Arrays.asList(expansion.getMIMETypes(extension)));
        }
        return mimeTypes.toArray(new String[0]);
    }

    public String[] getExtensions(String mimeType) {
        if (mimeType == null) return new String[0];
        Set<String> extensions = new HashSet<>();
        for (Expansion expansion : getExpansions()) {
            extensions.addAll(Arrays.asList(expansion.getExtensions(mimeType)));
        }
        return extensions.toArray(new String[0]);
    }

    public String[] getMIMETypes(String... extensions) {
        Set<String> mimeTypes = new HashSet<>();
        for (String extension : extensions) {
            mimeTypes.addAll(Arrays.asList(getMIMETypes(extension)));
        }
        return mimeTypes.toArray(new String[0]);
    }

    public String[] getExtensions(String... mimeTypes) {
        Set<String> extensions = new HashSet<>();
        for (String mimeType : mimeTypes) {
            extensions.addAll(Arrays.asList(getMIMETypes(mimeType)));
        }
        return extensions.toArray(new String[0]);
    }
    
}
