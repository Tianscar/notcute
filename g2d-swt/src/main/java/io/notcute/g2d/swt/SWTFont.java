package io.notcute.g2d.swt;

import io.notcute.internal.swt.SWTG2DUtils;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;

public class SWTFont implements io.notcute.g2d.Font {

    private static volatile SWTFont defaultFont = null;
    public static SWTFont getDefaultFont(Device device) {
        if (defaultFont == null) defaultFont = new SWTFont(device.getSystemFont());
        return defaultFont;
    }

    private final Font font;
    private final FontData fontData;

    private SWTFont(Font font) {
        this.font = font;
        this.fontData = font.getFontData()[0];
    }
    public SWTFont(Device device, FontData fontData) {
        this.font = new Font(device, fontData);
        this.fontData = fontData;
    }

    public Font getFont() {
        return font;
    }

    public FontData getFontData() {
        return fontData;
    }

    @Override
    public int getStyle() {
        return SWTG2DUtils.toNotcuteFontStyle(fontData.getStyle());
    }

    @Override
    public boolean isNormal() {
        return getStyle() == Style.NORMAL;
    }

    @Override
    public boolean isBold() {
        return getStyle() == Style.BOLD;
    }

    @Override
    public boolean isItalic() {
        return getStyle() == Style.ITALIC;
    }

    @Override
    public boolean isBoldItalic() {
        return getStyle() == Style.BOLD_ITALIC;
    }

}
