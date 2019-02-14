package com.jorado.search.core.interceptors;

import com.jorado.search.core.Interceptor;
import com.jorado.search.core.SearchRequest;
import com.jorado.search.core.model.SearchResult;
import com.jorado.core.Result;
import org.springframework.context.annotation.Description;

import java.util.Map;

@Description("排序拦截器")
public class SortInterceptor implements Interceptor {

    @Override
    public Result<SearchResult<Map<String, Object>>> intercept(Chain chain) {

        SearchRequest request = chain.request();

        return chain.process(request);
    }

}
