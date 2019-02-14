package com.jorado.search.core.config;

import com.jorado.logger.util.JsonUtils;
import com.jorado.zookeeper.LoadConfig;

import java.util.HashMap;

/**
 * 应用配置
 */
public class AppSettings extends HashMap {

    private static LoadConfig remoteConfig = LoadConfig.newInstance("conf", () -> {
        adjust();
        return null;
    });

    private static volatile AppSettings config;

    public static AppSettings getInstance() {
        if (null == config) {
            adjust();
        }
        return config;
    }

    private static void adjust() {
        config = JsonUtils.fromJson(remoteConfig.getBody(), AppSettings.class);
    }

    public String get(String key, String defaultValue) {
        Object value = get(key);
        return null == value ? defaultValue : value.toString();
    }

    public boolean outputLog() {
        String o = get("outputLog", "0");
        return "1".equals(o);
    }

    public boolean debugEnabled() {
        String debugEnabled = get("debugEnabled", "1");
        return debugEnabled.equals("1");
    }
}
