package io.notcute.app.android;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class Clipboard {

    private final ClipboardManager manager;
    private final AtomicReference<ClipData> clipRef;
    private final List<ClipboardManager.OnPrimaryClipChangedListener> listeners;

    public Clipboard(ClipboardManager manager) {
        this.manager = Objects.requireNonNull(manager);
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
    public void setPrimaryClip(ClipData clip) {
        if (manager == null) {
            Objects.requireNonNull(clip);
            if (clipRef.compareAndSet(clipRef.get(), clip)) {
                for (ClipboardManager.OnPrimaryClipChangedListener listener : listeners) {
                    listener.onPrimaryClipChanged();
                }
            }
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

    public void addPrimaryClipChangedListener(ClipboardManager.OnPrimaryClipChangedListener listener) {
        if (manager == null) {
            listeners.add(listener);
        }
        else {
            manager.addPrimaryClipChangedListener(listener);
        }
    }

    public void removePrimaryClipChangedListener(ClipboardManager.OnPrimaryClipChangedListener listener) {
        if (manager == null) {
            listeners.remove(listener);
        }
        else  {
            manager.removePrimaryClipChangedListener(listener);
        }
    }

}
