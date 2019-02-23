package com.jorado.core.cache;

import com.jorado.core.logger.Logger;
import com.jorado.core.logger.LoggerFactory;
import com.jorado.core.util.EnumTimeUnit;
import com.jorado.core.util.TimeUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 内存缓存
 *
 * @param <T>
 */
public class MemoryCache<T> implements ICache<T> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String, CacheItem<T>> cache;

    private final static int INIT_SIZE = 300000;

    @Override
    public String toString() {
        return "MemoryCache{" +
                "cache=" + cache +
                '}';
    }

    public MemoryCache() {
        cache = new ConcurrentHashMap<>(INIT_SIZE);
        logger.info(String.format("init cache[ConcurrentHashMap] INIT_SIZE:%d", INIT_SIZE));
    }

    @Override
    public void add(String key, T value, EnumTimeUnit timeUnit) {

        CacheItem<T> item = new CacheItem<T>(value, timeUnit.addNextTime().getTime());
        this.add(key, item);
    }

    @Override
    public void add(String key, CacheItem<T> cacheItem) {
        this.cache.put(key, cacheItem);

    }

    @Override
    public T get(String key, IEntityFactory<T> dataBuilder, EnumTimeUnit timeUnit) {
        CacheItem<T> item = getCacheItem(key, dataBuilder, timeUnit);
        return item.getValue();
    }

    @Override
    public void remove(String key) {
        if (cache.containsKey(key)) {
            cache.remove(key);
        }
    }

    @Override
    public int size() {
        return cache.size();
    }


    @Override
    public CacheItem<T> getCacheItem(String key, IEntityFactory<T> dataBuilder, EnumTimeUnit timeUnit) {
        CacheItem<T> item = cache.get(key);
        if (item == null || TimeUtils.compare(item.getExpiry()) == TimeUtils.EnumCompare.GT) {
            item = cache.get(key); // 二次查询缓存
            if (item == null || TimeUtils.compare(item.getExpiry()) == TimeUtils.EnumCompare.GT) { // 仍旧无结果
                T value;
                try {
                    value = dataBuilder.get(key);
                    if (value != null) {
                        item = new CacheItem<T>(value, timeUnit.addNextTime().getTime());
                        add(key, item);
                    } else {
                        item = new CacheItem<T>(null, timeUnit.addNextTime().getTime());
                    }
                } catch (ExpiryException e) {
                    if (item != null) {
                        item.setExpiry(timeUnit.addNextTime().getTime());
                    } else {
                        return null;
                    }
                }
            }
        }
        return item;
    }

    @Override
    public void clear() {
        synchronized (cache) {
            cache.clear();
        }
    }
}
