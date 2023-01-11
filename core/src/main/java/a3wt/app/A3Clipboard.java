package a3wt.app;

import java.net.URI;

public interface A3Clipboard {

    class ContentType {
        private ContentType(){}
        public static final int PLAIN_TEXT = 0;
        public static final int HTML_TEXT = 1;
        public static final int URI_LIST = 2;
    }

    class SelectionType {
        private SelectionType(){}
        public static final int CLIPBOARD = 0;
        public static final int SELECTION = 1;
        public static final int APPLICATION = 2;
    }

    int getContentType();
    int getSelectionType();

    void setPlainText(final CharSequence text);
    CharSequence getPlainText();

    void setHTMLText(final String html);
    String getHTMLText();

    void setURIs(final URI[] uris);
    URI[] getURIs();

}
