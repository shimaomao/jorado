package com.jorado.logger.concurrent.threadcontext;

import com.jorado.logger.Sortable;
import com.jorado.logger.util.StringUtils;

public interface ThreadContext extends Sortable {

    void setData(String key, String value);

    void removeData(String key);

    void clearData();

    String getData(String key);

    default String getOrSetData(String key, String defaultValue) {
        String value = getData(key);
        if (StringUtils.isNotNullOrWhiteSpace(value)) return value;
        setData(key, defaultValue);
        return defaultValue;
    }
}
