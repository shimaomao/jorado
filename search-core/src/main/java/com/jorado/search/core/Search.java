package com.jorado.search.core;

import com.jorado.search.core.config.AppSettings;
import com.jorado.search.core.consts.ErrorConsts;
import com.jorado.search.core.exception.ClientFilterException;
import com.jorado.search.core.exception.NoAssignSolrException;
import com.jorado.search.core.interceptors.*;
import com.jorado.search.core.model.SearchResult;
import com.jorado.search.core.model.searchinfo.SearchInfo;
import com.jorado.search.core.service.FastSearch;
import com.jorado.search.core.service.SearchService;
import com.jorado.logger.EventBuilder;
import com.jorado.logger.EventClient;
import com.jorado.core.Result;
import com.jorado.core.ResultStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author len.zhang
 * 基于调用链拦截器的搜索服务实现
 * 轻量级搜索可以使用FastSearch {@link FastSearch}
 */
@Service
public final class Search implements SearchService {

    private List<Interceptor> interceptors = new ArrayList<>();
    private List<Interceptor> sortInterceptors = new ArrayList<>();
    private Interceptor connectInterceptor;

    @Override
    public Result<SearchResult<Map<String, Object>>> search(SearchInfo searchInfo) {

        EventBuilder eventBuilder = EventClient.getDefault().createEvent();

        Result<SearchResult<Map<String, Object>>> result = Result.OK;

        try {

            if (null == searchInfo) {
                throw new IllegalArgumentException("SearchInfo is null");
            }

            SearchRequest request = new SearchRequest(searchInfo);

            List<Interceptor> innerInterceptors = new ArrayList<>();

            innerInterceptors.add(new ClientInterceptor());
            innerInterceptors.add(new CacheInterceptor());
            innerInterceptors.addAll(this.interceptors);

            innerInterceptors.add(new SortInterceptor());
            innerInterceptors.addAll(this.sortInterceptors);

            innerInterceptors.add(this.connectInterceptor == null ? new ConnectInterceptor() : this.connectInterceptor);

            innerInterceptors.add(new SearchInterceptor());

            Interceptor.Chain chain = new InterceptorChain(innerInterceptors, 0, request);

            result = chain.process(request);

            eventBuilder.addData("searchInfo", request.getSearchInfo()).addData("solr", request.getSolrjOption().toString()).addData(request.getPlayload());

        } catch (ClientFilterException ex) {
            eventBuilder.setException(ex);
            result = new Result<>(ResultStatus.ERROR, ErrorConsts.Search.CLIENT_FILTER);

        } catch (NoAssignSolrException ex) {

            eventBuilder.setException(ex);
            result = new Result<>(ResultStatus.ERROR, ErrorConsts.Search.ASSIGN_SOLR);

        } catch (Exception ex) {
            eventBuilder.setException(ex);
            result = new Result<>(ResultStatus.ERROR, String.format("%s,%s", ErrorConsts.Search.SEARCH_FAIL, ex.getMessage()));

        } finally {
            //打印日志
            if (eventBuilder.getTarget().isError() || AppSettings.getInstance().outputLog()) {
                eventBuilder.addData("debug", result.getDebugInfo()).setMessage(result.getMessage()).asyncSubmit();
            }
        }
        return result;
    }

    /**
     * 添加拦截器
     *
     * @param interceptors
     * @return
     */
    public Search addInterceptor(Interceptor... interceptors) {
        for (Interceptor interceptor : interceptors) {
            this.interceptors.add(interceptor);
        }
        return this;
    }

    /**
     * 链接拦截器只能有一个
     *
     * @param interceptor
     * @return
     */
    public Search setConnectInterceptor(Interceptor interceptor) {
        this.connectInterceptor = interceptor;
        return this;
    }

    /**
     * 添加排序拦截器
     *
     * @param interceptors
     * @return
     */
    public Search addSortInterceptor(Interceptor... interceptors) {
        for (Interceptor interceptor : interceptors) {
            this.sortInterceptors.add(interceptor);
        }
        return this;
    }
}
