package io.notcute.g2d.awt;

import io.notcute.internal.awt.AWTG2DUtils;

import java.awt.Font;
import java.util.Objects;

public class AWTFont implements io.notcute.g2d.Font {

    public static final AWTFont DEFAULT_FONT = new AWTFont(AWTG2DUtils.getSystemDefaultFont());

    private final Font font;

    public AWTFont(Font font) {
        this.font = Objects.requireNonNull(font);
    }

    public Font getFont() {
        return font;
    }

    @Override
    public int getStyle() {
        return AWTG2DUtils.toNotcuteFontStyle(font.getStyle());
    }

    @Override
    public boolean isNormal() {
        return font.isPlain();
    }

    @Override
    public boolean isBold() {
        return font.isBold();
    }

    @Override
    public boolean isItalic() {
        return font.isItalic();
    }

    @Override
    public boolean isBoldItalic() {
        return isBold() && isItalic();
    }

}
