package com.jorado.search.core.interceptors;

import com.jorado.search.core.Interceptor;
import com.jorado.search.core.SearchRequest;
import com.jorado.search.core.model.SearchResult;
import com.jorado.core.Result;
import org.springframework.context.annotation.Description;

import java.util.Map;

@Description("缓存拦截器")
public class CacheInterceptor implements Interceptor {

    @Override
    public Result<SearchResult<Map<String, Object>>> intercept(Chain chain) {

        SearchRequest request = chain.request();

        //TODO:配置缓存逻辑
        return chain.process(request);

    }

}
