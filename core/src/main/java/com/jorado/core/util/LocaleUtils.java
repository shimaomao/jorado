package com.jorado.core.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class LocaleUtils {

    private static final Pattern UNDER_LINE_PATTERN = Pattern.compile("_");

    private static final int LOCALE_CACHE_SIZE = 10000;

    private static final Map<String, Locale> LOCALE_CACHE = Collections.synchronizedMap(new LinkedHashMap<String, Locale>() {
        private static final long serialVersionUID = 1377741378297004026L;

        @Override
        protected boolean removeEldestEntry(Map.Entry<String, Locale> eldest) {
            return size() > LOCALE_CACHE_SIZE;
        }
    });

    public static String appendLocale(String name, Locale locale) {
        if (locale == null) {
            return name;
        }
        int i = name.lastIndexOf('.');
        return i < 0 ? name + "_" + locale.toString() :
                name.substring(0, i) + "_" + locale.toString() + name.substring(i);
    }

    public static Locale getParentLocale(Locale locale) {
        if (locale == null) {
            return null;
        }
        String name = locale.toString();
        int i = name.lastIndexOf('_');
        if (i > 0) {
            return getLocale(name.substring(0, i));
        }
        return null;
    }

    public static Locale getLocale(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        Locale locale = LOCALE_CACHE.get(name);
        if (locale == null) {
            String[] parts = UNDER_LINE_PATTERN.split(name);
            if (parts.length > 2) {
                locale = new Locale(parts[0], parts[1], parts[2]);
                LOCALE_CACHE.put(name, locale);
            } else if (parts.length > 1) {
                locale = new Locale(parts[0], parts[1]);
                LOCALE_CACHE.put(name, locale);
            } else if (parts.length > 0) {
                locale = new Locale(parts[0]);
                LOCALE_CACHE.put(name, locale);
            }
        }
        return locale;
    }

}