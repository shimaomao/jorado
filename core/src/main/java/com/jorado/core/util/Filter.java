package com.jorado.core.util;

/**
 * 数据过滤器
 *
 * @param <T>
 */
@FunctionalInterface
public interface Filter<T> {
    T filter(T in);
}
