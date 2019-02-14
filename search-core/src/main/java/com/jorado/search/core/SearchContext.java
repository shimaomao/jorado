package com.jorado.search.core;

import com.jorado.search.core.model.SearchResult;
import com.jorado.search.core.model.searchinfo.SearchInfo;

/**
 * @param <T>
 * @author len.zhang
 * 搜索上下文对象
 */
public final class SearchContext<T> extends SearchRequest {

    /**
     * 查询结果
     */
    private SearchResult<T> searchResult;

    public SearchContext(SearchInfo searchInfo) {
        super(searchInfo);
    }

    public SearchResult<T> getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(SearchResult<T> searchResult) {
        this.searchResult = searchResult;
    }
}
