package io.notcute.ui.android;

import android.content.Context;
import android.os.Build;
import android.view.PointerIcon;
import io.notcute.g2d.Image;
import io.notcute.g2d.android.AndroidImage;
import io.notcute.internal.android.AndroidUIUtils;
import io.notcute.ui.Cursor;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class AndroidCursor implements Cursor {

    private static final Map<Integer, AndroidCursor> systemCursors = new ConcurrentHashMap<>(2);
    public static AndroidCursor getDefaultCursor(Context context) {
        return getSystemCursor(context, Type.DEFAULT);
    }
    public static AndroidCursor getSystemCursor(Context context, int type) {
        Objects.requireNonNull(context);
        if (!systemCursors.containsKey(type)) systemCursors.put(type, new AndroidCursor(context, type));
        return systemCursors.get(type);
    }

    private final Object pointerIcon;
    private final int type;
    private final AndroidImage image;

    private final int hotSpotX;
    private final int hotSpotY;

    private AndroidCursor(Context context, int type) {
        image = null;
        this.type = type;
        this.hotSpotX = this.hotSpotY = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            pointerIcon = PointerIcon.getSystemIcon(context, AndroidUIUtils.toAndroidPointerType(type));
        }
        else {
            pointerIcon = null;
        }
    }

    public AndroidCursor(AndroidImage image, int hotSpotX, int hotSpotY) {
        Objects.requireNonNull(image);
        type = -1;
        this.image = image;
        this.hotSpotX = hotSpotX;
        this.hotSpotY = hotSpotY;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            pointerIcon = PointerIcon.create(image.getBitmap(), hotSpotX, hotSpotY);
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
    public Image getImage() {
        return image;
    }

    @Override
    public int getHotSpotX() {
        return hotSpotX;
    }

    @Override
    public int getHotSpotY() {
        return hotSpotY;
    }

}
