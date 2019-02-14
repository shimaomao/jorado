package com.jorado.logger;

import com.jorado.logger.cache.Group;
import com.jorado.logger.cache.GroupCacheFactory;

/**
 * 缓存管理器
 */
public final class CacheManager {

    private static final String DEFAULT_GROUP = "event";
    private static final CacheManager cacheManager = new CacheManager();
    private GroupCacheFactory factory = new GroupCacheFactory();
    private Group defaultGroup = factory.group(DEFAULT_GROUP);

    public static CacheManager getDefault() {
        return cacheManager;
    }

    public Object get(String key) {
        return defaultGroup.getValue(key);
    }

    public Object get(String key, Object defaultValue) {

        Object v = defaultGroup.getValue(key);

        return null == v ? defaultValue : v;
    }

    public void set(String key, Object v) {
        defaultGroup.push(key, v, 10);
    }

    public void set(String key, Object v, int second) {
        defaultGroup.push(key, v, second);
    }

    public Object get(String group, String key) {
        return factory.group(group).getValue(key);
    }

    public Object get(String group, String key, Object defaultValue) {

        Object v = factory.group(group).getValue(key);

        return null == v ? defaultValue : v;
    }

    public void set(String group, String key, Object v) {
        factory.group(group).push(key, v, 10);
    }

    public void set(String group, String key, Object v, int second) {
        factory.group(group).push(key, v, second);
    }
}
