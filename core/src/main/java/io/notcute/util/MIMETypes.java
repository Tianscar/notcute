package io.notcute.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MIMETypes extends AbstractExpandable<MIMETypes.Expansion> {

    public interface Expansion extends Expandable.Expansion {
        String[] getMIMETypes(String extension);
        String[] getExtensions(String mimeType);
    }

    public String[] getMIMETypes(String extension) {
        if (extension == null) return new String[0];
        Set<String> mimeTypes = new HashSet<>();
        String[] tmp;
        for (Expansion expansion : getExpansions()) {
            if ((tmp = expansion.getMIMETypes(extension)) != null) mimeTypes.addAll(Arrays.asList(tmp));
        }
        return mimeTypes.toArray(new String[0]);
    }

    public String[] getExtensions(String mimeType) {
        if (mimeType == null) return new String[0];
        Set<String> extensions = new HashSet<>();
        String[] tmp;
        for (Expansion expansion : getExpansions()) {
            if ((tmp = expansion.getExtensions(mimeType)) != null) extensions.addAll(Arrays.asList(tmp));
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
