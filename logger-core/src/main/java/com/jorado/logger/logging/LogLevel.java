package com.jorado.logger.logging;

public class LogLevel implements Comparable {

    public static final LogLevel Other = new LogLevel("Other", -1);
    public static final LogLevel Trace = new LogLevel("Trace", 0);
    public static final LogLevel Debug = new LogLevel("Debug", 1);
    public static final LogLevel Info = new LogLevel("Info", 2);
    public static final LogLevel Warn = new LogLevel("Warn", 3);
    public static final LogLevel Error = new LogLevel("ERROR", 4);
    public static final LogLevel Fatal = new LogLevel("Fatal", 5);
    public static final LogLevel Off = new LogLevel("Off", 6);

    private final int ordinal;
    private final String name;

    private LogLevel(String name, int ordinal) {
        this.name = name;
        this.ordinal = ordinal;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public String getName() {
        return name;
    }

    public static LogLevel fromOrdinal(int ordinal) {
        switch (ordinal) {
            case -1:
                return Other;
            case 0:
                return Trace;
            case 1:
                return Debug;
            case 2:
                return Info;
            case 3:
                return Warn;
            case 4:
                return Error;
            case 5:
                return Fatal;
            case 6:
                return Off;
            default:
                throw new IllegalArgumentException("Invalid ordinal.");
        }
    }

    public static LogLevel fromString(String levelName) {
        levelName = levelName.toLowerCase();
        if (levelName == null)
            throw new IllegalArgumentException("levelName");

        if (levelName.equals("trace")
                || levelName.equals("true")
                || levelName.equals("1")
                || levelName.equals("yes"))
            return LogLevel.Trace;
        if (levelName.equals("debug"))
            return LogLevel.Debug;
        if (levelName.equals("info"))
            return LogLevel.Info;
        if (levelName.equals("warn"))
            return LogLevel.Warn;
        if (levelName.equals("error"))
            return LogLevel.Error;
        if (levelName.equals("fatal"))
            return LogLevel.Fatal;
        if (levelName.equals("off")
                || levelName.equals("false")
                || levelName.equals("0")
                || levelName.equals("no"))
            return LogLevel.Off;

        return LogLevel.Other;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogLevel logLevel = (LogLevel) o;
        return ordinal == logLevel.ordinal;
    }

    public boolean equals(LogLevel other) {
        if (null != other)
            return ordinal == other.ordinal;

        return false;
    }

    @Override
    public int hashCode() {
        return ordinal;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null)
            throw new IllegalArgumentException("o");

        return ordinal - ((LogLevel) o).getOrdinal();
    }
}
