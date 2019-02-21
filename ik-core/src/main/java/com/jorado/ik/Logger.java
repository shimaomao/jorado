package com.jorado.ik;

public class Logger {

    private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("campus-lexicon");

    public static void trace(String msg) {
        logger.trace(msg);
    }

    public static void traceFormat(String msg, Object... args) {
        trace(String.format(msg, args));
    }

    public static void trace(Throwable e) {
        logger.trace(e.getMessage(), e);
    }

    public static void trace(String msg, Throwable e) {
        logger.trace(msg, e);
    }

    public static void debug(String msg) {
        logger.debug(msg);
    }

    public static void debugFormat(String msg, Object... args) {
        debug(String.format(msg, args));
    }

    public static void debug(Throwable e) {
        logger.debug(e.getMessage(), e);
    }

    public static void debug(String msg, Throwable e) {
        logger.debug(msg, e);
    }

    public static void info(String msg) {
        logger.info(msg);
    }

    public static void infoFormat(String msg, Object... args) {
        info(String.format(msg, args));
    }

    public static void info(Throwable e) {
        logger.info(e.getMessage(), e);
    }

    public static void info(String msg, Throwable e) {
        logger.info(msg, e);
    }

    public static void warn(String msg) {
        logger.warn(msg);
    }

    public static void warnFormat(String msg, Object... args) {
        warn(String.format(msg, args));
    }

    public static void warn(Throwable e) {
        logger.warn(e.getMessage(), e);
    }

    public static void warn(String msg, Throwable e) {
        logger.warn(msg, e);
    }

    public static void error(String msg) {
        logger.error(msg);
    }

    public static void errorFormat(String msg, Object... args) {
        error(String.format(msg, args));
    }

    public static void error(Throwable e) {
        logger.error(e.getMessage(), e);
    }

    public static void error(String msg, Throwable e) {
        logger.error(msg, e);
    }

    public static boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    public static boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    public static boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    public static boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    public static boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }
}
