package com.jorado.search.core;

import com.jorado.search.core.model.searchinfo.SearchInfo;
import com.jorado.search.core.solrclient.SolrjOption;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author len.zhang
 * 搜索请求，调用链使用
 */
public class SearchRequest {

    /**
     * 是否取消/终止
     */
    private boolean isCanceled;

    /**
     * 查询对象
     */
    private SearchInfo searchInfo;

    /**
     * Solr查询选项
     */
    private SolrjOption solrjOption;

    /**
     * 附加数据
     */
    private Map<String, Object> playload;

    public SearchRequest(SearchInfo searchInfo) {
        this.searchInfo = searchInfo;
        this.playload = new LinkedHashMap<>();
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public SearchRequest setCanceled(boolean canceled) {
        isCanceled = canceled;
        return this;
    }

    public SearchInfo getSearchInfo() {
        return searchInfo;
    }

    public SearchRequest setSearchInfo(SearchInfo searchInfo) {
        this.searchInfo = searchInfo;
        return this;
    }

    public SolrjOption getSolrjOption() {
        return solrjOption;
    }

    public void setSolrjOption(SolrjOption solrjOption) {
        this.solrjOption = solrjOption;
    }

    public Map<String, Object> getPlayload() {
        return playload;
    }

    public SearchRequest addData(String key, Object object) {
        this.playload.put(key, object);
        return this;
    }
}
