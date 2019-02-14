package com.jorado.search.core.service.impl;

import com.jorado.search.core.service.Exporter;
import com.jorado.search.core.solrclient.SolrjOption;
import com.jorado.search.core.solrclient.SolrjProxy;
import com.jorado.logger.EventClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.springframework.context.annotation.Description;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用Solr数据导出器
 */
@Description("通用Solr数据导出器")
public class SolrExporter implements Exporter<SolrDocument> {

    /**
     * 来源solr代理
     */
    private SolrjProxy sourceSolrProxy;

    /**
     * solr查询对象
     */
    private SolrQuery solrQuery;

    /**
     * 构造函数
     *
     * @param sourceSolr 来源solr
     */
    public SolrExporter(SolrjOption sourceSolr) {
        this(sourceSolr, null);
    }

    /**
     * 构造函数
     *
     * @param sourceSolr 来源solr
     */
    public SolrExporter(SolrjOption sourceSolr, SolrQuery solrQuery) {

        this.solrQuery = solrQuery;

        if (null == this.solrQuery) {
            this.solrQuery = new SolrQuery();
            this.solrQuery.setQuery("*:*");
        }
        this.solrQuery.setRequestHandler("/query");
        this.sourceSolrProxy = new SolrjProxy(sourceSolr);
    }

    @Override
    public long count() {
        long result = 0;
        try {
            QueryResponse rsp = this.sourceSolrProxy.query(this.solrQuery, 0, 0, false);
            if (null == rsp.getResults()) {
                return result;
            }
            return rsp.getResults().getNumFound();
        } catch (Exception ex) {
            EventClient.getDefault().submitException("Get solr data count error", ex);
            return result;
        }
    }

    @Override
    public List<SolrDocument> listDatas(int start, int rows) {

        List<SolrDocument> result = new ArrayList<>();
        try {

            QueryResponse rsp = this.sourceSolrProxy.query(this.solrQuery, start, rows, false, 1000 * 20);

            if (null == rsp.getResults()) {
                return result;
            }

            return rsp.getResults();

        } catch (Exception ex) {
            EventClient.getDefault().submitException("Get solr data error", ex);
            return result;
        }
    }

    public SolrjProxy getSourceSolrProxy() {
        return sourceSolrProxy;
    }

    public SolrExporter setSourceSolrProxy(SolrjProxy sourceSolrProxy) {
        this.sourceSolrProxy = sourceSolrProxy;
        return this;
    }

    public SolrQuery getSolrQuery() {
        return solrQuery;
    }

    public SolrExporter setSolrQuery(SolrQuery solrQuery) {
        this.solrQuery = solrQuery;
        return this;
    }
}
