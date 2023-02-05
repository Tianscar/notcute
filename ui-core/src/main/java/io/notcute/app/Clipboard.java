package io.notcute.app;

import java.net.URI;

public interface Clipboard {

    final class ContentType {
        private ContentType() {
            throw new UnsupportedOperationException();
        }
        public static final int PLAIN_TEXT = 0;
        public static final int HTML_TEXT = 1;
        public static final int URI_LIST = 2;
    }

    final class SelectionType {
        private SelectionType() {
            throw new UnsupportedOperationException();
        }
        public static final int CLIPBOARD = 0;
        public static final int SELECTION = 1;
        public static final int APPLICATION = 2;
    }

    int getContentType();
    int getSelectionType();

    void setPlainText(CharSequence text);
    CharSequence getPlainText();

    void setHTMLText(String html);
    String getHTMLText();

    void setURIs(URI[] uris);
    URI[] getURIs();

}
