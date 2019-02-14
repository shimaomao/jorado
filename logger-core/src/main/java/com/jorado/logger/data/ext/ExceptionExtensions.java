package com.jorado.logger.data.ext;

import com.jorado.logger.data.Error;
import com.jorado.logger.data.SimpleError;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class ExceptionExtensions {
    private ExceptionExtensions() {
    }

    public static Error toError(Throwable ex) {
        Error error = new Error();
        error.setMessage(ex.getMessage());

        return error;
    }

    public static SimpleError toSimpleError(Throwable ex) {
        SimpleError error = new SimpleError();
        error.setMessage(ex.getMessage());
        error.setType(ex.getClass().getName());
        error.setStackTrace(getErrorDetail(ex));
        return error;
    }

    public static String getErrorDetail(Throwable ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        ex.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }

    public static String toString(StackTraceElement[] stackTrace) {
        StringBuilder buf = new StringBuilder();
        for (StackTraceElement item : stackTrace) {
            buf.append(item.toString());
            buf.append("\n");
        }
        return buf.toString();
    }
}
