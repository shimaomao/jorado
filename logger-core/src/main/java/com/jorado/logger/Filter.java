package com.jorado.logger;

/**
 * 数据过滤器
 *
 * @param <T>
 */
@FunctionalInterface
public interface Filter<T> {
    T filter(T in);
}
