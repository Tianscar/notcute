package io.notcute.internal;

import io.notcute.util.MIMETypes;

public final class BasicMIMETypesExpansion implements MIMETypes.Expansion {

    @Override
    public String[] getMIMETypes(String extension) {
        if ("*".equals(extension)) return new String[] { "*/*" };
        else return new String[0];
    }

    @Override
    public String[] getExtensions(String mimeType) {
        if ("*/*".equals(mimeType)) return new String[] { "*" };
        else return new String[0];
    }

}
