package com.jorado.logger.data;

import com.jorado.logger.data.collection.DataMap;

import java.util.List;

public class ContextData extends DataMap {

    public ContextData() {
    }

    public ContextData(List<Entry<String, Object>> values) {
        super(values);
    }

    public void setException(Throwable ex) {

        put(KnownKeys.EXCEPTION, ex);
    }

    public boolean hasException() {
        return containsKey(KnownKeys.EXCEPTION);
    }

    public Exception getException() {
        if (!hasException())
            return null;

        return (Exception) get(KnownKeys.EXCEPTION);
    }

    public void markAsUnhandledError() {
        put(KnownKeys.IS_UNHANDLED_ERROR, true);
    }

    public static class KnownKeys {
        private KnownKeys() {
        }

        public static final String IS_UNHANDLED_ERROR = "IS_UNHANDLED_ERROR";
        public static final String EXCEPTION = "EXCEPTION";
    }
}
