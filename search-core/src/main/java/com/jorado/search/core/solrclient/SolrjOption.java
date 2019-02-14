package com.jorado.search.core.solrclient;

import java.io.Serializable;
import java.util.List;

public final class SolrjOption implements Serializable {

    private String collection;

    private SolrjClient solrjClient;

    public SolrjOption(String collection, List<String> host) {
        this.collection = collection;
        this.solrjClient = new SolrjClient(host);
    }

    public SolrjOption(String collection, String host) {
        this.collection = collection;
        this.solrjClient = new SolrjClient(host);
    }

    public SolrjOption(String collection, SolrjClient solrjClient) {
        this.collection = collection;
        this.solrjClient = solrjClient;
    }

    public String getCollection() {
        return collection;
    }

    public SolrjClient getSolrjClient() {
        return solrjClient;
    }

    public boolean isCloud() {
        return solrjClient.isCloud();
    }

    @Override
    public String toString() {
        return "{" +
                "collection='" + collection + '\'' +
                ", solrjClient=" + solrjClient +
                '}';
    }
}
