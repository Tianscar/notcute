package a3wt.android;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static a3wt.util.A3Preconditions.checkArgNotNull;

public class Clipboard {

    protected final ClipboardManager manager;
    protected final AtomicReference<ClipData> clipRef;
    protected final List<ClipboardManager.OnPrimaryClipChangedListener> listeners;

    public Clipboard(final ClipboardManager manager) {
        checkArgNotNull(manager, "manager");
        this.manager = manager;
        clipRef = null;
        listeners = null;
    }

    public Clipboard() {
        manager = null;
        clipRef = new AtomicReference<>();
        listeners = new CopyOnWriteArrayList<>();
    }

    public boolean hasClipboardManager() {
        return manager != null;
    }

    public ClipboardManager getClipboardManager() {
        return manager;
    }

    /**
     * Sets the current primary clip on the clipboard.  This is the clip that
     * is involved in normal cut and paste operations.
     *
     * @param clip The clipped data item to set.
     * @see #getPrimaryClip()
     */
    public void setPrimaryClip(final ClipData clip) {
        checkArgNotNull(clip, "clip");
        if (manager == null) {
            clipRef.compareAndSet(clipRef.get(), clip);
        }
        else {
            manager.setPrimaryClip(clip);
        }
    }

    /**
     * Returns the current primary clip on the clipboard.
     *
     * <em>If the application is not the default IME or does not have input focus this return
     * {@code null}.</em>
     *
     * @see #setPrimaryClip(ClipData)
     */
    public ClipData getPrimaryClip() {
        if (manager == null) {
            return clipRef.get();
        }
        else {
            return manager.getPrimaryClip();
        }
    }

    /**
     * Returns a description of the current primary clip on the clipboard
     * but not a copy of its data.
     *
     * <em>If the application is not the default IME or does not have input focus this return
     * {@code null}.</em>
     *
     * @see #setPrimaryClip(ClipData)
     */
    public ClipDescription getPrimaryClipDescription() {
        if (manager == null) {
            return clipRef.get().getDescription();
        }
        else {
            return manager.getPrimaryClipDescription();
        }
    }

    /**
     * Returns true if there is currently a primary clip on the clipboard.
     *
     * <em>If the application is not the default IME or the does not have input focus this will
     * return {@code false}.</em>
     */
    public boolean hasPrimaryClip() {
        if (manager == null) {
            return clipRef.get() != null;
        }
        else {
            return manager.hasPrimaryClip();
        }
    }

    public void addPrimaryClipChangedListener(final ClipboardManager.OnPrimaryClipChangedListener listener) {
        checkArgNotNull(listener, "listener");
        if (manager == null) {
            listeners.add(listener);
        }
        else {
            manager.addPrimaryClipChangedListener(listener);
        }
    }

    public void removePrimaryClipChangedListener(final ClipboardManager.OnPrimaryClipChangedListener listener) {
        checkArgNotNull(listener, "listener");
        if (manager == null) {
            listeners.remove(listener);
        }
        else  {
            manager.removePrimaryClipChangedListener(listener);
        }
    }

}
