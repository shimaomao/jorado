package com.jorado.logger.concurrent.threadcontext;

import com.jorado.logger.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class DefaultThreadContext implements ThreadContext {

    private static final ThreadLocal<Map<String, String>> LOCAL = ThreadLocal.withInitial(() -> new HashMap<>());

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public void setData(String key, String value) {
        if (StringUtils.isNotNullOrWhiteSpace(value)) {
            LOCAL.get().put(key, value);
        }
    }

    @Override
    public void removeData(String key) {
        LOCAL.get().remove(key);
    }

    @Override
    public void clearData() {
        LOCAL.get().clear();
    }

    @Override
    public String getData(String key) {
        return LOCAL.get().get(key);
    }
}
