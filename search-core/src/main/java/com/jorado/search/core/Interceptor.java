package com.jorado.search.core;

import com.jorado.search.core.model.SearchResult;
import com.jorado.core.Result;

import java.util.Map;

/**
 * @author len.zhang
 * 搜索拦截器
 */
public interface Interceptor {

    Result<SearchResult<Map<String, Object>>> intercept(Chain chain);

    interface Chain {

        SearchRequest request();

        Result<SearchResult<Map<String, Object>>> process(SearchRequest request);

    }
}
