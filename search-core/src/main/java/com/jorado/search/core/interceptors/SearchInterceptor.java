package com.jorado.search.core.interceptors;

import com.jorado.search.core.Interceptor;
import com.jorado.search.core.SearchRequest;
import com.jorado.search.core.model.SearchResult;
import com.jorado.search.core.service.FastSearch;
import com.jorado.search.core.service.SearchService;
import com.jorado.logger.util.Stopwatch;
import com.jorado.core.Result;
import org.springframework.context.annotation.Description;

import java.util.Map;

@Description("搜索拦截器")
public class SearchInterceptor implements Interceptor {

    @Override
    public Result<SearchResult<Map<String, Object>>> intercept(Chain chain) {

        SearchRequest request = chain.request();

        //记录solr查询整体使用时间
        Stopwatch stopwatch = Stopwatch.begin();

        SearchService searchService = new FastSearch(request.getSolrjOption());

        Result<SearchResult<Map<String, Object>>> result = searchService.search(request.getSearchInfo());

        stopwatch.stop();

        request.addData("querytime", stopwatch.getDuration());

        return result;
    }

}
