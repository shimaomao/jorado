package com.jorado.logger.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 简单的内存缓存实现，实现group概念，<br/>
 * 一个group里面是个有序的集合，<br/>
 * 集合支持key-value expire弥补redis list的不足
 */
public class GroupCacheFactory {

    // 数据容器
    private Map<String, Group> container;

    public GroupCacheFactory() {
        container = new LinkedHashMap<>();
    }

    /**
     * 如果组存在就返回，不存在就创建，保证不为null
     *
     * @param key
     * @return
     */
    public Group group(String key, int capacity) {
        return container.computeIfAbsent(key, n -> new Group(capacity));
    }

    /**
     * 如果组存在就返回，不存在就创建，默认容量1000
     *
     * @param key
     * @return
     */
    public Group group(String key) {

        return this.group(key, 1000);
    }
}
