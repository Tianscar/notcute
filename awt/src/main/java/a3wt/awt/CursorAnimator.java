package a3wt.awt;

import a3wt.graphics.A3Cursor;
import a3wt.graphics.A3FramedCursor;

import java.awt.Component;
import java.awt.Cursor;
import java.util.Timer;
import java.util.TimerTask;

import static a3wt.util.A3Preconditions.checkArgNotNull;

final class CursorAnimator {

    private final Component component;
    private final A3FramedCursor framedCursor;
    private volatile Timer timer;
    private volatile boolean stopped;

    public CursorAnimator(final Component component, final A3FramedCursor framedCursor) {
        checkArgNotNull(component, "component");
        checkArgNotNull(framedCursor, "framedCursor");
        this.component = component;
        stopped = false;
        timer = null;
        this.framedCursor = framedCursor.copy();
    }

    public boolean isStopped() {
        return stopped;
    }

    public void start() {
        stopped = false;
        if (timer == null) timer = new Timer(getClass().getName() + " Timer: " + this);
        restart();
    }

    public void stop() {
        stopped = true;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void restart() {
        long delay = 0;
        for (final A3Cursor.Frame frame : framedCursor) {
            Cursor cursor = ((AWTA3Cursor)frame.getCursor()).cursor;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!stopped) component.setCursor(cursor);
                }
            }, delay);
            delay += frame.getDuration();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.purge();
                restart();
            }
        }, delay);
    }

}
