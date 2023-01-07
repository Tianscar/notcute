package com.ansdoship.a3wt.bundle;

import com.ansdoship.a3wt.app.A3Assets;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public interface A3Bundle {

    class Format {
        private Format() {}
        public static final int PROPERTIES = 0;
        public static final int INI = 1;
        public static final int XML = 2;
    }

    boolean save(final File output, final int format);
    boolean save(final OutputStream output, final int format);

    boolean restore(final File input);
    boolean restore(final InputStream input);
    boolean restore(final URL input);
    boolean restore(final A3Assets assets, final String input);

    String[] getBundleReaderFormatNames();
    String[] getBundleWriterFormatNames();

    boolean isConcurrent();

}
