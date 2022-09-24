package com.ansdoship.a3wt.awt;

import com.ansdoship.a3wt.graphics.A3Cursor;
import com.ansdoship.a3wt.graphics.A3Image;

import java.awt.Cursor;

import static com.ansdoship.a3wt.awt.A3AWTUtils.createCustomCursor;
import static com.ansdoship.a3wt.awt.A3AWTUtils.cursorType2AWTCursorType;
import static com.ansdoship.a3wt.util.A3Asserts.checkArgNotNull;

public class AWTA3Cursor implements A3Cursor {

    protected final Cursor cursor;
    protected final int type;
    protected final AWTA3Image image;

    private static final AWTA3Cursor DEFAULT_CURSOR = new AWTA3Cursor(Type.DEFAULT);
    private static final AWTA3Cursor GONE_CURSOR = new AWTA3Cursor(Type.GONE);

    public static AWTA3Cursor getDefaultCursor() {
        return DEFAULT_CURSOR;
    }

    public static AWTA3Cursor getGoneCursor() {
        return GONE_CURSOR;
    }

    public AWTA3Cursor(final int type) {
        image = null;
        this.type = type;
        if (type == Type.GONE) {
            cursor = A3AWTUtils.getGoneCursor();
        }
        else cursor = Cursor.getPredefinedCursor(cursorType2AWTCursorType(type));
    }

    public AWTA3Cursor(final AWTA3Image image) {
        checkArgNotNull(image, "image");
        type = -1;
        this.image = image;
        cursor = createCustomCursor(image.bufferedImage, image.hotSpotX, image.hotSpotY, "A3WT/" + AWTA3Platform.BACKEND_NAME + " Custom Cursor");
    }

    public Cursor getCursor() {
        return cursor;
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
