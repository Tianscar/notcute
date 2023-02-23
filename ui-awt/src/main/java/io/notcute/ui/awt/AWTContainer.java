package io.notcute.ui.awt;

import io.notcute.ui.Container;

import java.awt.Window;

public interface AWTContainer extends AWTG2DContext, Container {

    Window getWindow();

}
