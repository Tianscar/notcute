package io.notcute.ui;

import io.notcute.g2d.Graphics;

public interface Paintable {

    void paint(G2DContext context, Graphics graphics, boolean snapshot);

}
