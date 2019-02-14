package com.jorado.search.core.service;

import com.jorado.search.core.model.SearchResult;
import com.jorado.search.core.model.searchinfo.SearchInfo;
import com.jorado.core.Result;

/**
 * @param <T>
 * @author len.zhang
 */
public interface SearchService<T> {

    Result<SearchResult<T>> search(SearchInfo searchInfo);
}
