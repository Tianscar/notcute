package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Font;

import java.awt.Font;

import static com.ansdoship.a3wt.awt.A3AWTUtils.AWTFontStyle2FontStyle;

public class AWTA3Font implements A3Font {

    protected final Font font;

    public AWTA3Font(Font font) {
        this.font = font;
    }

    public Font getFont() {
        return font;
    }

    @Override
    public int getStyle() {
        return AWTFontStyle2FontStyle(font.getStyle());
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
