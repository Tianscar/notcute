package io.notcute.ui;

import io.notcute.g2d.Image;

public interface UIKit {

    Cursor getCursor(int type);
    Cursor getDefaultCursor();

    Cursor createCursor(Image image, int hotSpotX, int hotSpotY);

}
