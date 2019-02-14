package com.jorado.core.cache;


import com.jorado.core.util.EnumTimeUnit;

/**
 * 缓存接口
 * @param <T>
 */
public interface ICache<T> {

    public T get(String key, IEntityFactory<T> dataBuilder, EnumTimeUnit timeUnit);

    public CacheItem<T> getCacheItem(String key, IEntityFactory<T> dataBuilder, EnumTimeUnit timeUnit);

    public void add(String key, T value, EnumTimeUnit timeUnit);

    public void add(String key, CacheItem<T> cacheItem);

    public void remove(String key);

    public int size();

    public void clear();
}
