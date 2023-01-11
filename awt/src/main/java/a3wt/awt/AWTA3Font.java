package a3wt.awt;

import a3wt.graphics.A3Font;

import java.awt.Font;

import static a3wt.util.A3Preconditions.checkArgNotNull;

public class AWTA3Font implements A3Font {

    protected final Font font;

    public AWTA3Font(final Font font) {
        checkArgNotNull(font, "font");
        this.font = font;
    }

    public Font getFont() {
        return font;
    }

    @Override
    public int getStyle() {
        return A3AWTUtils.awtFontStyle2FontStyle(font.getStyle());
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
