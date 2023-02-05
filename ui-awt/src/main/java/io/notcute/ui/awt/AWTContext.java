package io.notcute.ui.awt;

import io.notcute.app.Platform;
import io.notcute.app.awt.AWTPlatform;
import io.notcute.context.Context;
import io.notcute.app.javase.JavaSEContext;
import io.notcute.util.signalslot.Dispatcher;

public class AWTContext extends JavaSEContext {

    protected static class Holder extends JavaSEContext.Holder {
        public Holder(AWTContext context) {
            super(context);
        }
        @Override
        public Dispatcher getDispatcher() {
            return AWTDispatcher.INSTANCE;
        }
        private static volatile AWTPlatform platform = null;
        @Override
        public Platform getPlatform() {
            if (platform == null) platform = new AWTPlatform();
            return platform;
        }
    }

    private final AWTContext.Holder holder;
    public AWTContext() {
        holder = new AWTContext.Holder(this);
    }

    @Override
    public Context.Holder getContextHolder() {
        return holder;
    }

}
