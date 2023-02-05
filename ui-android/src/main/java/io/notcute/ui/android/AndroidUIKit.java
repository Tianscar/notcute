package io.notcute.ui.android;

import android.content.Context;
import io.notcute.g2d.Image;
import io.notcute.g2d.android.AndroidImage;
import io.notcute.ui.Cursor;
import io.notcute.ui.UIKit;

import java.util.Objects;

public class AndroidUIKit implements UIKit {

    private final Context context;
    public AndroidUIKit(Context context) {
        this.context = Objects.requireNonNull(context);
    }

    @Override
    public Cursor getCursor(int type) {
        return AndroidCursor.getSystemCursor(context, type);
    }

    @Override
    public Cursor getDefaultCursor() {
        return AndroidCursor.getDefaultCursor(context);
    }

    @Override
    public Cursor createCursor(Image image, int hotSpotX, int hotSpotY) {
        return new AndroidCursor((AndroidImage) image, hotSpotX, hotSpotY);
    }

}
