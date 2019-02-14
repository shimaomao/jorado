package com.jorado.search.core.config;

import com.jorado.logger.util.JsonUtils;
import com.jorado.zookeeper.LoadConfig;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * solr配置，需重构
 */
public class SolrConfig {

    private static LoadConfig remoteConfig = LoadConfig.newInstance("solr", () -> {
        adjust();
        return null;
    });

    private static volatile SolrConfig config;

    private List<String> host;

    public static SolrConfig getInstance() {
        if (null == config) {
            adjust();
        }
        return config;
    }

    private static void adjust() {
        config = JsonUtils.fromJson(remoteConfig.getBody(), SolrConfig.class);
        solrMap.clear();
        for (Solr solr : solrList) {
            solrMap.put(solr.getName().toLowerCase(), solr);
        }
    }

    private static List<Solr> solrList = new ArrayList<>();

    public List<Solr> getSolrs() {
        return solrList;
    }

    public void setSolrs(List<Solr> solrs) {
        solrList = solrs;
    }

    private static Map<String, Solr> solrMap = new HashMap<>();

    public Solr getSolr(String solrName) {
        Solr solr = solrMap.get(solrName.toLowerCase());
        if (null != solr && CollectionUtils.isEmpty(solr.getHost())) {
            solr.setHost(getHost());
        }
        return solr;
    }

    public Solr getSolr() {
        return getSolr("default");
    }

    public List<String> getHost() {
        return host;
    }

    public void setHost(List<String> host) {
        this.host = host;
    }
}
