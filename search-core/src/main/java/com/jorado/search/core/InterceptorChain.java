package com.jorado.search.core;

import com.jorado.search.core.model.SearchResult;
import com.jorado.core.Result;

import java.util.List;
import java.util.Map;

/**
 * @author len.zhang
 * 搜索调用链
 */
public final class InterceptorChain implements Interceptor.Chain {

    private List<Interceptor> interceptors;
    private int index;
    private SearchRequest request;

    public InterceptorChain(List<Interceptor> interceptors, int index, SearchRequest request) {
        this.interceptors = interceptors;
        this.index = index;
        this.request = request;
    }

    @Override
    public SearchRequest request() {
        return request;
    }

    @Override
    public Result<SearchResult<Map<String, Object>>> process(SearchRequest request) {
        if (index >= interceptors.size()) {
            throw new AssertionError();
        }
        InterceptorChain next = new InterceptorChain(interceptors, index + 1, request);
        Interceptor interceptor = interceptors.get(index);
        return interceptor.intercept(next);
    }
}
