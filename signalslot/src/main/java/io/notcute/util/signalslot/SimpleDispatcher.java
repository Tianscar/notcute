package io.notcute.util.signalslot;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A simple {@link Dispatcher} implementation with a worker thread.
 * {@link #start()} and {@link #stop()} are convenience methods to
 * start (and stop) an arbitrary thread which executes {@link #run()} in its
 * context.
 *
 * @see Dispatcher
 */
public class SimpleDispatcher extends Dispatcher {

    /**
     * The default {@link Dispatcher} of {@link Connection.Type#QUEUED} connected slots.
     */
    private static final SimpleDispatcher DISPATCHER = new SimpleDispatcher("Default-Dispatcher");

    static {
        DISPATCHER.start();
    }

    public static Dispatcher getDefaultDispatcher() {
        return DISPATCHER;
    }

    /**
     * The thread used in {@link #start()} and {@link #stop}.
     */
    private Thread workerThread = null;

    /**
     * The worker thread name.
     */
    private final String workerThreadName;

    /**
     * This ID is used to generate thread names.
     */
    private final static AtomicInteger nextSerialNumber = new AtomicInteger(0);
    private static int serialNumber() {
        return nextSerialNumber.getAndIncrement();
    }

    protected boolean isDispatchThread() {
        return Thread.currentThread() == workerThread;
    }

    @Override
    protected void switchContext() {
        dispatch();
    }

    /**
     * Creates a new dispatcher. The associated thread specified to run as a daemon.
     */
    public SimpleDispatcher() {
        this("SimpleDispatcher-" + serialNumber());
    }

    /**
     * Creates a new dispatcher whose associated thread has the specified name.
     * The associated thread specified to run as a daemon.
     *
     * @param name the name of the associated thread
     * @throws NullPointerException if {@code name} is null
     */
    public SimpleDispatcher(final String name) throws NullPointerException {
        workerThreadName = Objects.requireNonNull(name);
    }

    /**
     * Creates a new {@link Thread} which runs {@link #run()}. Does nothing if
     * there already is a running thread.
     */
    public final synchronized void start() {
        if (workerThread == null) {
            workerThread = new Thread(this::run, workerThreadName);
            workerThread.setDaemon(true);
            workerThread.start();
        }
    }

    /**
     * Stops the current {@link Thread} created by {@link #start()}. Does
     * nothing if there is no running thread.
     */
    public final synchronized void stop() {
        if (workerThread != null) {
            workerThread.interrupt();
            workerThread = null;
        }
    }

}
