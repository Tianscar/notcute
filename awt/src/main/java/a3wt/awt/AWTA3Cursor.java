package a3wt.awt;

import a3wt.graphics.A3Cursor;
import a3wt.graphics.A3Image;
import a3wt.graphics.A3Point;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.geom.Point2D;

import static a3wt.awt.A3AWTUtils.createCustomCursor;
import static a3wt.awt.A3AWTUtils.cursorType2awtCursorType;
import static a3wt.util.A3Preconditions.checkArgNotNull;

public class AWTA3Cursor implements A3Cursor {

    protected final Cursor cursor;
    protected final int type;
    protected final AWTA3Image image;
    protected final float hotSpotX;
    protected final float hotSpotY;

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
        hotSpotX = 0;
        hotSpotY = 0;
        this.type = type;
        if (type == Type.GONE) {
            cursor = A3AWTUtils.getGoneCursor();
        }
        else {
            final int type0 = cursorType2awtCursorType(type);
            cursor = Cursor.getPredefinedCursor(type0 == -1 ? Cursor.DEFAULT_CURSOR : type0);
        }
    }

    public AWTA3Cursor(final AWTA3Image image, final Point2D.Float hotSpot) {
        checkArgNotNull(image, "image");
        type = -1;
        this.image = image;
        hotSpotX = hotSpot.x;
        hotSpotY = hotSpot.y;
        cursor = createCustomCursor(image.bufferedImage, new Point((int) hotSpotX, (int) hotSpotY), "A3WT/" + AWTA3Platform.BACKEND_NAME + " Custom Cursor");
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

    @Override
    public float getHotSpotX() {
        return hotSpotX;
    }

    @Override
    public float getHotSpotY() {
        return hotSpotY;
    }

    @Override
    public A3Point getHotSpot() {
        return new AWTA3Point(new Point2D.Float(hotSpotX, hotSpotY));
    }

}
