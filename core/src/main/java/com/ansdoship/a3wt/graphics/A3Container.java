package com.ansdoship.a3wt.graphics;

import com.ansdoship.a3wt.input.A3ContainerListener;

import java.util.List;

public interface A3Container extends A3Canvas {

    List<A3ContainerListener> getA3ContainerListeners();
    void addA3ContainerListener(A3ContainerListener listener);

}
