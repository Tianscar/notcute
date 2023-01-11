package a3wt.android;

import android.graphics.Typeface;
import a3wt.graphics.A3Font;

import static a3wt.util.A3Preconditions.checkArgNotNull;

public class AndroidA3Font implements A3Font {

    protected final Typeface typeface;

    public AndroidA3Font(final Typeface typeface) {
        checkArgNotNull(typeface, "typeface");
        this.typeface = typeface;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    @Override
    public int getStyle() {
        return A3AndroidUtils.typefaceStyle2FontStyle(typeface.getStyle());
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
