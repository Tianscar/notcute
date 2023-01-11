package a3wt.android;

import android.content.Context;
import android.os.Build;
import android.view.PointerIcon;
import a3wt.graphics.A3Cursor;
import a3wt.graphics.A3Image;

import static a3wt.util.A3Preconditions.checkArgNotNull;

public class AndroidA3Cursor implements A3Cursor {

    protected final Object pointerIcon;
    protected final int type;
    protected final AndroidA3Image image;

    public AndroidA3Cursor(final Context context, final int type) {
        checkArgNotNull(context, "context");
        image = null;
        this.type = type;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            pointerIcon = PointerIcon.getSystemIcon(context, A3AndroidUtils.cursorType2PointerIconType(type));
        }
        else {
            pointerIcon = null;
        }
    }

    public AndroidA3Cursor(final AndroidA3Image image) {
        checkArgNotNull(image, "image");
        type = -1;
        this.image = image;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            pointerIcon = PointerIcon.create(image.bitmap, image.hotSpotX, image.hotSpotY);
        }
        else pointerIcon = null;
    }

    public Object getPointerIcon() {
        return pointerIcon;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public A3Image getImage() {
        return image;
    }

}
