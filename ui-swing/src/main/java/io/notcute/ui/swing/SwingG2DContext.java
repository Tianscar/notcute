package io.notcute.ui.swing;

import io.notcute.ui.awt.AWTG2DContext;

import javax.swing.JComponent;
import java.awt.Component;

public interface SwingG2DContext extends AWTG2DContext {

    JComponent getJComponent();

    @Override
    default Component getComponent() {
        return getJComponent();
    }

}
