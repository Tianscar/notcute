package a3wt.app;

public interface A3Logger {

    class Priority {
        private Priority() {}
        /**
         * Priority constant for the println method; use Log.v.
         */
        public static final int VERBOSE = 2;

        /**
         * Priority constant for the println method; use Log.d.
         */
        public static final int DEBUG = 3;

        /**
         * Priority constant for the println method; use Log.i.
         */
        public static final int INFO = 4;

        /**
         * Priority constant for the println method; use Log.w.
         */
        public static final int WARN = 5;

        /**
         * Priority constant for the println method; use Log.e.
         */
        public static final int ERROR = 6;

        /**
         * Priority constant for the println method.
         */
        public static final int ASSERT = 7;
    }

    /**
     * Send a {@link Priority#VERBOSE} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    int verbose(final String tag, final String msg);

    /**
     * Send a {@link Priority#VERBOSE} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    int verbose(final String tag, final String msg, final Throwable tr);

    /*
     * Send a {@link Priority#VERBOSE} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param tr An exception to log
     */
    int verbose(final String tag, final Throwable tr);

    /**
     * Send a {@link Priority#DEBUG} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    int debug(final String tag, final String msg);

    /**
     * Send a {@link Priority#DEBUG} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    int debug(final String tag, final String msg, final Throwable tr);

    /*
     * Send a {@link Priority#DEBUG} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param tr An exception to log
     */
    int debug(final String tag, final Throwable tr);

    /**
     * Send an {@link Priority#INFO} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    int info(final String tag, final String msg);

    /**
     * Send a {@link Priority#INFO} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    int info(final String tag, final String msg, final Throwable tr);

    /*
     * Send a {@link Priority#INFO} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param tr An exception to log
     */
    int info(final String tag, final Throwable tr);

    /**
     * Send a {@link Priority#WARN} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    int warn(final String tag, final String msg);

    /**
     * Send a {@link Priority#WARN} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    int warn(final String tag, final String msg, final Throwable tr);

    /*
     * Send a {@link Priority#WARN} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param tr An exception to log
     */
    int warn(final String tag, final Throwable tr);

    /**
     * Send an {@link Priority#ERROR} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    int error(final String tag, final String msg);

    /**
     * Send a {@link Priority#ERROR} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    int error(final String tag, final String msg, final Throwable tr);

    /*
     * Send a {@link Priority#ERROR} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param tr An exception to log
     */
    int error(final String tag, final Throwable tr);

    /**
     * Handy function to get a loggable stack trace from a Throwable
     * @param tr An exception to log
     */
    String getStackTraceString(final Throwable tr);

    /**
     * Low-level logging call.
     * @param priority The priority/type of this log message
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @return The number of bytes written.
     */
    int println(final int priority, final String tag, final String msg);

}
