package com.ansdoship.a3wt.awt;

/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.ansdoship.a3wt.util.A3Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.net.UnknownHostException;

public class AWTA3Logger implements A3Logger {

    public int verbose(String tag, String msg) {
        return println(VERBOSE, tag, msg);
    }

    public int verbose(String tag, String msg, Throwable tr) {
        return println(VERBOSE, tag, msg + '\n' + getStackTraceString(tr));
    }

    public int debug(String tag, String msg) {
        return println(DEBUG, tag, msg);
    }

    public int debug(String tag, String msg, Throwable tr) {
        return println(DEBUG, tag, msg + '\n' + getStackTraceString(tr));
    }

    public int info(String tag, String msg) {
        return println(INFO, tag, msg);
    }

    public int info(String tag, String msg, Throwable tr) {
        return println(INFO, tag, msg + '\n' + getStackTraceString(tr));
    }

    public int warn(String tag, String msg) {
        return println(WARN, tag, msg);
    }

    public int warn(String tag, String msg, Throwable tr) {
        return println(WARN, tag, msg + '\n' + getStackTraceString(tr));
    }

    public int warn(String tag, Throwable tr) {
        return println(WARN, tag, getStackTraceString(tr));
    }

    public int error(String tag, String msg) {
        return println(ERROR, tag, msg);
    }

    public int error(String tag, String msg, Throwable tr) {
        return println(ERROR, tag, msg + '\n' + getStackTraceString(tr));
    }

    public String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }

        // This is to reduce the amount of log spew that apps do in the non-error
        // condition of the network being unavailable.
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    public int println(int priority, String tag, String msg) {
        String log = tag + ": " + msg;
        boolean error = false;
        switch (priority) {
            case VERBOSE:
                log = "V/" + log;
                break;
            case DEBUG:
                log = "D/" + log;
                break;
            case INFO:
                log = "I/" + log;
                break;
            case WARN:
                log = "W/" + log;
                break;
            case ERROR:
                log = "E/" + log;
                error = true;
                break;
            case ASSERT:
                log = "A/" + log;
                error = true;
                break;
        }
        log = A3AWTUtils.currentFormattedTime("yyyy-MM-dd HH:mm:ss:SSS") + " " +
                ManagementFactory.getRuntimeMXBean().getName() + " " +
                log;
        if (error) System.err.println(log);
        else System.out.println(log);
        return log.length();
    }
    
}

