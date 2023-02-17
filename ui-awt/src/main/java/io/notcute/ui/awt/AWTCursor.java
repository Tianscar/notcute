package io.notcute.ui.awt;

import io.notcute.g2d.awt.AWTImage;
import io.notcute.g2d.Image;
import io.notcute.internal.awt.CursorFactory;
import io.notcute.internal.awt.AWTUIUtils;

import java.awt.Cursor;
import java.awt.Point;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class AWTCursor implements io.notcute.ui.Cursor {

    private final Cursor cursor;
    private final int type;
    private final AWTImage image;
    private final int hotSpotX;
    private final int hotSpotY;

    public static final AWTCursor DEFAULT_CURSOR = new AWTCursor(Type.DEFAULT);

    private static final Map<Integer, AWTCursor> systemCursors = new ConcurrentHashMap<>(2);
    static {
        systemCursors.put(Type.DEFAULT, DEFAULT_CURSOR);
    }
    public static AWTCursor getSystemCursor(int type) {
        if (!systemCursors.containsKey(type)) systemCursors.put(type, new AWTCursor(type));
        return systemCursors.get(type);
    }

    private AWTCursor(int type) {
        image = null;
        hotSpotX = 0;
        hotSpotY = 0;
        this.type = type;
        cursor = CursorFactory.getPredefinedCursor(AWTUIUtils.toCursorFactoryCursorType(type));
    }

    public AWTCursor(AWTImage image, int hotSpotX, int hotSpotY) {
        this.image = Objects.requireNonNull(image);
        this.hotSpotX = hotSpotX;
        this.hotSpotY = hotSpotY;
        type = -1;
        cursor = CursorFactory.createCustomCursor(image.getBufferedImage(), new Point(hotSpotX, hotSpotY), this.toString());
    }

    public Cursor getCursor() {
        return cursor;
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
