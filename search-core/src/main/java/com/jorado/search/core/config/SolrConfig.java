package com.jorado.search.core.config;

import com.jorado.zkconfig.ZKPSettings;
import com.jorado.zkconfig.ConfigFactory;
import com.jorado.zkconfig.ZKPConfig;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * solr配置，需重构
 */
public class SolrConfig extends ZKPConfig {

    final static String ZKP_PATH = ZKPSettings.ZOOKEEPER_PATH + "/" + SolrConfig.class.getName();

    static SolrConfig settings;

    public synchronized static SolrConfig getInstance() {
        if (settings == null) {
            settings = ConfigFactory.get(ZKP_PATH);
        }
        return settings;
    }

    @Override
    public void adjust() {
        solrMap.clear();
        for (Solr solr : solrList) {
            solrMap.put(solr.getName().toLowerCase(), solr);
        }
    }

    private List<String> host;

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
