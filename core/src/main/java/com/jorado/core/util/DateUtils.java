package com.jorado.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public final class DateUtils {
    private DateUtils() {
    }

    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_FORMAT_MILLIS = "yyyy-MM-dd HH:mm:ss:SSS";
    private static final ThreadLocal<SimpleDateFormat> DEFAULT_LOCAL = ThreadLocal.withInitial(() -> new SimpleDateFormat(DEFAULT_FORMAT));
    private static final ThreadLocal<Map<String, SimpleDateFormat>> LOCAL = new ThreadLocal<>();

    public static DateFormat getDateFormat(String format, TimeZone timeZone) {
        if (StringUtils.isEmpty(format) || DEFAULT_FORMAT.equals(format)) {
            if (timeZone == null) {
                return DEFAULT_LOCAL.get();
            } else {
                format = DEFAULT_FORMAT;
            }
        }
        Map<String, SimpleDateFormat> formatters = LOCAL.get();
        if (formatters == null) {
            formatters = new HashMap<>();
            LOCAL.set(formatters);
        }
        String key = format;
        if (timeZone != null) {
            key += timeZone.getID();
        }
        SimpleDateFormat formatter = formatters.get(key);
        if (formatter == null) {
            formatter = new SimpleDateFormat(format);
            if (timeZone != null) {
                formatter.setTimeZone(timeZone);
            }
            formatters.put(key, formatter);
        }
        return formatter;
    }

    public static String format(Date value) {
        return format(value, DEFAULT_FORMAT, null);
    }

    public static String format(Date value, String format) {
        return format(value, format, null);
    }

    public static String format(Date value, String format, TimeZone timeZone) {
        if (value == null) {
            return null;
        }
        return getDateFormat(format, timeZone).format(value);
    }

    public static String format(Object object) {
        return format(object, null);
    }

    public static String format(Object object, String format) {
        return format(object, format, 0);
    }

    public static String format(Object object, String format, int addYears) {
        return format(object, format, addYears, 0, 0, 0, 0);
    }

    public static String format(Object object, String format, int addYears, int addMonths) {
        return format(object, format, addYears, addMonths, 0, 0, 0);
    }

    public static String format(Object object, String format, int addYears, int addMonths, int addDays) {
        return format(object, format, addYears, addMonths, addDays, 0, 0);
    }

    public static String format(Object object, String format, int addYears, int addMonths, int addDays, int addHours) {
        return format(object, format, addYears, addMonths, addDays, addHours, 0);
    }

    public static String format(Object object, String format, int addYears, int addMonths, int addDays, int addHours, int addMinutes) {

        return format(object, format, null, addYears, addMonths, addDays, addHours, addMinutes);
    }

    public static String format(Object object, String format, TimeZone timeZone, int addYears, int addMonths, int addDays, int addHours, int addMinutes) {

        if (object == null)
            return "";

        DateFormat dateFormat = getDateFormat(format, timeZone);
        try {
            if (addYears != 0 || addMonths != 0 || addDays != 0 || addHours != 0 || addMinutes != 0) {
                Date date = dateFormat.parse(dateFormat.format(object));

                date = dateAdd(date, addYears, addMonths, addDays, addHours, addMinutes);
                return dateFormat.format(date);
            }
            return dateFormat.format(object);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateFormat.format(Calendar.getInstance().getTime());
    }

    public static Date parse(String value) {
        return parse(value, DEFAULT_FORMAT);
    }

    public static Date parse(String value, String format) {
        return parse(value, format, null);
    }

    public static Date parse(String value, String format, TimeZone timeZone) {
        try {
            return getDateFormat(format, timeZone).parse(value);
        } catch (ParseException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public static Date parse(String value, String format, TimeZone timeZone, Date defaultValue) {
        try {
            return getDateFormat(format, timeZone).parse(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static String timeNow() {
        return format(Calendar.getInstance().getTime());
    }

    public static String timeNowMillis() {
        return format(Calendar.getInstance().getTime(), DEFAULT_FORMAT_MILLIS);
    }

    public static Date dateAdd(Date date, int addYears, int addMonths, int addDays, int addHours, int addMinutes) {
        if (date == null)
            return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (addYears != 0)
            calendar.add(Calendar.YEAR, addYears);
        if (addMonths != 0)
            calendar.add(Calendar.MONTH, addMonths);
        if (addDays != 0)
            calendar.add(Calendar.DATE, addDays);
        if (addHours != 0)
            calendar.add(Calendar.HOUR, addHours);
        if (addMinutes != 0)
            calendar.add(Calendar.MINUTE, addMinutes);

        return calendar.getTime();
    }

    public static String toTimestamp(Date value) {
        long ts = value.getTime();
        return String.valueOf(ts / 1000);
    }

    public static String toTimestamp(String value) {
        long ts = parse(value).getTime();
        return String.valueOf(ts / 1000);
    }

    public static String toTimestamp(String value, String format) {
        long ts = parse(value, format).getTime();
        return String.valueOf(ts / 1000);
    }

    public static String toTimestamp(String value, String format, TimeZone timeZone) {
        long ts = parse(value, format, timeZone).getTime();
        return String.valueOf(ts / 1000);
    }

    public static String toTimestamp(String value, String format, TimeZone timeZone, Date defaultValue) {
        long ts = parse(value, format, timeZone, defaultValue).getTime();
        return String.valueOf(ts / 1000);
    }

    public static String reFormat(String value, String newFormat) {
        return format(parse(value), newFormat);
    }

    public static String reFormat(String value, String format, String newFormat) {
        return format(parse(value, format), newFormat);
    }

    public static String reFormat(String value, String format, String newFormat, TimeZone timeZone) {
        return format(parse(value, format, timeZone), newFormat);
    }

    public static String reFormat(String value, String format, String newFormat, TimeZone timeZone, Date defaultValue) {
        return format(parse(value, format, timeZone, defaultValue), newFormat);
    }

    public static void main(String[] args) {
        String input = "2018-12-07 10:54:29";
        String stamp = toTimestamp(input);
        System.out.println(stamp);

        //1812071114
        String reFormat = reFormat(input, "yyMMddHHmm");
        System.out.println(reFormat);
    }
}