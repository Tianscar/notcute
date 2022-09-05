package com.ansdoship.a3wt.app;

import java.io.File;

public interface A3Clipboard {

    class ContentType {
        private ContentType(){}
        public static final int PLAIN_TEXT = 0;
        public static final int HTML_TEXT = 1;
        public static final int FILE_LIST = 2;
    }

    int getContentType();

    void setPlainText(CharSequence text);
    CharSequence getPlainText();

    void setHTMLText(String html);
    String getHTMLText();

    void setFiles(File[] files);
    File[] getFiles();

}
