package com.jorado.search.core.interceptors;

import com.jorado.search.core.Interceptor;
import com.jorado.search.core.SearchRequest;
import com.jorado.search.core.config.Solr;
import com.jorado.search.core.config.SolrConfig;
import com.jorado.search.core.exception.NoAssignSolrException;
import com.jorado.search.core.model.SearchResult;
import com.jorado.search.core.solrclient.SolrjClient;
import com.jorado.search.core.solrclient.SolrjClientPool;
import com.jorado.search.core.solrclient.SolrjOption;
import com.jorado.core.Result;
import org.springframework.context.annotation.Description;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Description("连接拦截器")
public class ConnectInterceptor implements Interceptor {

    /**
     * solr client连接池
     */
    private static SolrjClientPool solrjClientPool = new SolrjClientPool();

    @Override
    public Result<SearchResult<Map<String, Object>>> intercept(Chain chain) {

        SearchRequest request = chain.request();

        request.setSolrjOption(assignSolr());

        return chain.process(request);
    }


    private SolrjOption assignSolr() {

        Solr solr = SolrConfig.getInstance().getSolr();

        if (null == solr || CollectionUtils.isEmpty(solr.getHost())) {
            throw new NoAssignSolrException();
        }

        SolrjClient solrjClient = solrjClientPool.getClient(solr.isClientCached(), solr.getHost(), solr.isCloud());

        return new SolrjOption(solr.getCollection(), solrjClient);
    }
}
