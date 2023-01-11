package a3wt.android;

import android.util.Log;
import a3wt.app.A3Logger;

import static a3wt.app.A3Logger.Priority.*;

public class AndroidA3Logger implements A3Logger {

    @Override
    public int verbose(final String tag, final String msg) {
        return Log.v(tag, msg);
    }

    @Override
    public int verbose(final String tag, final String msg, final Throwable tr) {
        return Log.v(tag, msg, tr);
    }

    @Override
    public int verbose(final String tag, final Throwable tr) {
        return println(VERBOSE, tag, getStackTraceString(tr));
    }

    @Override
    public int debug(final String tag, final String msg) {
        return Log.d(tag, msg);
    }

    @Override
    public int debug(final String tag, final String msg, final Throwable tr) {
        return Log.d(tag, msg, tr);
    }

    @Override
    public int debug(final String tag, final Throwable tr) {
        return println(DEBUG, tag, getStackTraceString(tr));
    }

    @Override
    public int info(final String tag, final String msg) {
        return Log.i(tag, msg);
    }

    @Override
    public int info(final String tag, final String msg, final Throwable tr) {
        return Log.i(tag, msg, tr);
    }

    @Override
    public int info(final String tag, final Throwable tr) {
        return println(INFO, tag, getStackTraceString(tr));
    }

    @Override
    public int warn(final String tag, final String msg) {
        return Log.w(tag, msg);
    }

    @Override
    public int warn(final String tag, final String msg, final Throwable tr) {
        return Log.w(tag, msg, tr);
    }

    @Override
    public int warn(final String tag, final Throwable tr) {
        return Log.w(tag, tr);
    }

    @Override
    public int error(final String tag, final String msg) {
        return Log.e(tag, msg);
    }

    @Override
    public int error(final String tag, final String msg, final Throwable tr) {
        return Log.e(tag, msg, tr);
    }

    @Override
    public int error(final String tag, final Throwable tr) {
        return println(WARN, tag, getStackTraceString(tr));
    }

    @Override
    public String getStackTraceString(final Throwable tr) {
        return Log.getStackTraceString(tr);
    }

    @Override
    public int println(final int priority, final String tag, final String msg) {
        return Log.println(priority, tag, msg);
    }

}
