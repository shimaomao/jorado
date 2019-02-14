package com.jorado.search.core.service;

import java.util.List;

/**
 * @param <T>
 * @author len.zhang
 * 数据导出器
 */
public interface Exporter<T> {

    long count();

    List<T> listDatas(int start, int rows);
}
