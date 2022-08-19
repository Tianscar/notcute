package com.ansdoship.a3wt.graphics;

public interface A3Font {

    class Style {
        private Style(){}
        public static final int NORMAL = 0;
        public static final int BOLD = 1;
        public static final int ITALIC = 2;
        public static final int BOLD_ITALIC = 3;
    }

    int getStyle();

    boolean isNormal();
    boolean isBold();
    boolean isItalic();
    boolean isBoldItalic();

}
