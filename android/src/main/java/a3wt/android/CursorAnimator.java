package a3wt.android;

import a3wt.graphics.A3Cursor;
import a3wt.graphics.A3FramedCursor;
import android.os.Build;
import android.view.PointerIcon;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import static a3wt.util.A3Preconditions.checkArgNotNull;

final class CursorAnimator {

    private final View view;
    private final A3FramedCursor framedCursor;
    private volatile Timer timer;
    private volatile boolean stopped;

    public CursorAnimator(final View view, final A3FramedCursor framedCursor) {
        checkArgNotNull(view, "view");
        checkArgNotNull(framedCursor, "framedCursor");
        this.view = view;
        stopped = false;
        timer = null;
        this.framedCursor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ? framedCursor.copy() : null;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void start() {
        stopped = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (timer == null) timer = new Timer(getClass().getName() + " Timer: " + this);
            restart();
        }
    }

    public void stop() {
        stopped = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }
    }

    private void restart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            long delay = 0;
            for (final A3Cursor.Frame frame : framedCursor) {
                PointerIcon pointerIcon = (PointerIcon) ((AndroidA3Cursor)frame.getCursor()).pointerIcon;
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (!stopped) view.setPointerIcon(pointerIcon);
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

}
