package com.poscustomer.Utils;

import android.content.Context;
import android.os.Process;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Abhishek on 28-03-2017.
 */

public class ExceptionHandler implements
        Thread.UncaughtExceptionHandler {
    private final Context myContext;

    public ExceptionHandler(Context context) {

        myContext = context;
    }

    public static final String CRASH_REPORT = "crashReport";

    @Override
    public void uncaughtException(Thread thread, Throwable exception) {

        final StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));

        System.err.println(stackTrace);// You can use LogCat too


        Process.killProcess(Process.myPid());
        System.exit(10);
    }

}