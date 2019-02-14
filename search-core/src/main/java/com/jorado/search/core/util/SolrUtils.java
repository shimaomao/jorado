package com.jorado.search.core.util;

import com.jorado.logger.util.ConvertUtils;
import org.apache.solr.common.SolrDocument;

public final class SolrUtils {

    private SolrUtils() {

    }

    public static String getDocValue(SolrDocument doc, String key) {

        return getDocValue(doc, key, "");

    }

    public static <T> T getDocValue(SolrDocument doc, String key, T defaultValue) {

        try {

            if (!doc.containsKey(key)) {
                return defaultValue;
            }

            Object o = doc.get(key);

            if (isDataNull(o)) {
                return defaultValue;
            }

            Class<?> type = defaultValue.getClass();

            if (type.equals(String.class)) {
                return (T) o.toString();
            }
            if (type.equals(Integer.class)) {
                return (T) type.cast(ConvertUtils.toInt(o.toString()));
            }
            if (type.equals(Long.class)) {
                return (T) type.cast(ConvertUtils.toLong(o.toString()));
            }
            if (type.equals(Float.class)) {
                return (T) type.cast(ConvertUtils.toFloat(o.toString()));
            }
            if (type.equals(Double.class)) {
                return (T) type.cast(ConvertUtils.toDouble(o.toString()));
            }
            if (type.equals(Byte.class)) {
                return (T) type.cast(ConvertUtils.toByte(o.toString()));
            }
            if (type.equals(boolean.class)) {
                return (T) type.cast(ConvertUtils.toBoolean(o.toString()));
            }

            return defaultValue;

        } catch (Exception ex) {

            return defaultValue;
        }

    }

    public static boolean isDataNull(Object object) {
        return object == null || object.toString().isEmpty();
    }
}
