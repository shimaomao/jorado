package com.jorado.search.core.interceptors;

import com.jorado.search.core.ClientFilter;
import com.jorado.search.core.Interceptor;
import com.jorado.search.core.SearchRequest;
import com.jorado.search.core.exception.ClientFilterException;
import com.jorado.search.core.model.SearchResult;
import com.jorado.core.Result;
import org.springframework.context.annotation.Description;

import java.util.Map;

@Description("客户端验证拦截器")
public class ClientInterceptor implements Interceptor {

    @Override
    public Result<SearchResult<Map<String, Object>>> intercept(Chain chain) {

        SearchRequest request = chain.request();

        ClientFilter clientFilter = new ClientFilter(request.getSearchInfo());

        Result filterResult = clientFilter.filter();

        if (filterResult.isFail()) {
            throw new ClientFilterException();
        }

        return chain.process(request);
    }
}
