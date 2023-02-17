package io.notcute.g2d.android;

import android.graphics.Typeface;
import io.notcute.g2d.Font;
import io.notcute.internal.android.AndroidG2DUtils;

import java.util.Objects;

public class AndroidFont implements Font {

    public static final AndroidFont DEFAULT_FONT = new AndroidFont(Typeface.DEFAULT);

    private final Typeface typeface;

    public AndroidFont(Typeface typeface) {
        this.typeface = Objects.requireNonNull(typeface);
    }

    public Typeface getTypeface() {
        return typeface;
    }

    @Override
    public int getStyle() {
        return AndroidG2DUtils.toNotcuteFontStyle(typeface.getStyle());
    }

    @Override
    public boolean isNormal() {
        return typeface.getStyle() == Typeface.NORMAL;
    }

    @Override
    public boolean isBold() {
        return typeface.isBold();
    }

    @Override
    public boolean isItalic() {
        return typeface.isItalic();
    }

    @Override
    public boolean isBoldItalic() {
        return typeface.getStyle() == Typeface.BOLD_ITALIC;
    }

}
