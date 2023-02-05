package io.notcute.g2d.awt;

import java.awt.*;
import java.util.Objects;

public class AWTFont implements io.notcute.g2d.Font {

    public static final AWTFont DEFAULT_FONT = new AWTFont(Font.decode(null));

    private final Font font;

    public AWTFont(Font font) {
        this.font = Objects.requireNonNull(font);
    }

    public Font getFont() {
        return font;
    }

    @Override
    public int getStyle() {
        return Util.toNotcuteFontStyle(font.getStyle());
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
