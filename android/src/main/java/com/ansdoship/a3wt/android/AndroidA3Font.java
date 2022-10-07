package com.ansdoship.a3wt.android;

import android.graphics.Typeface;
import com.ansdoship.a3wt.graphics.A3Font;

import static com.ansdoship.a3wt.android.A3AndroidUtils.typefaceStyle2FontStyle;
import static com.ansdoship.a3wt.util.A3Preconditions.checkArgNotNull;

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
        return typefaceStyle2FontStyle(typeface.getStyle());
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
