package com.ansdoship.a3wt.input;

public interface A3ContainerListener {

    void containerOpened();
    void containerClosing();
    void containerClosed();
    void containerIconified();
    void containerDeiconified();
    void containerActivated();
    void containerDeactivated();
    void containerFocusGained();
    void containerFocusLost();

}
