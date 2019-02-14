package com.jorado.core.util;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;


public class NumberUtils {

    private static final String DEFAULT_FORMAT = "###,##0.###";

    private static final ThreadLocal<DecimalFormat> DEFAULT_LOCAL = new ThreadLocal<DecimalFormat>() {
        @Override
        protected DecimalFormat initialValue() {
            return new DecimalFormat(DEFAULT_FORMAT);
        }
    };

    private static final ThreadLocal<Map<String, DecimalFormat>> LOCAL = new ThreadLocal<Map<String, DecimalFormat>>();

    public static DecimalFormat getDecimalFormat(String format) {
        if (StringUtils.isEmpty(format) || DEFAULT_FORMAT.equals(format)) {
            return DEFAULT_LOCAL.get();
        }
        Map<String, DecimalFormat> formatters = LOCAL.get();
        if (formatters == null) {
            formatters = new HashMap<String, DecimalFormat>();
            LOCAL.set(formatters);
        }
        DecimalFormat formatter = formatters.get(format);
        if (formatter == null) {
            formatter = new DecimalFormat(format);
            formatters.put(format, formatter);
        }
        return formatter;
    }

    public static String format(Number value, String format) {
        return getDecimalFormat(format).format(value);
    }

}