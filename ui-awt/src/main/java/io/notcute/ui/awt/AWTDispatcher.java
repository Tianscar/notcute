package io.notcute.ui.awt;

import io.notcute.util.signalslot.Dispatcher;

import java.awt.EventQueue;

public class AWTDispatcher extends Dispatcher {

    private AWTDispatcher() {
    }
    public static final AWTDispatcher INSTANCE = new AWTDispatcher();

    @Override
    protected boolean isDispatchThread() {
        return EventQueue.isDispatchThread();
    }

    @Override
    protected void switchContext() {
        if (isDispatchThread()) dispatch();
        else EventQueue.invokeLater(this::dispatch);
    }

}
