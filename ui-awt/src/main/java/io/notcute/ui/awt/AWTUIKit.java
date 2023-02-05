package io.notcute.ui.awt;

import io.notcute.g2d.awt.AWTImage;
import io.notcute.g2d.Image;
import io.notcute.ui.Cursor;
import io.notcute.ui.UIKit;

public class AWTUIKit implements UIKit {

    @Override
    public Cursor getCursor(int type) {
        return AWTCursor.getSystemCursor(type);
    }

    @Override
    public Cursor getDefaultCursor() {
        return AWTCursor.DEFAULT_CURSOR;
    }

    @Override
    public Cursor createCursor(Image image, int hotSpotX, int hotSpotY) {
        return new AWTCursor((AWTImage) image, hotSpotX, hotSpotY);
    }

}
