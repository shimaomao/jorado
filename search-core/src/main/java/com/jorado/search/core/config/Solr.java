package com.jorado.search.core.config;

import java.util.List;

public class Solr {

    private String name;
    private String collection;
    private int cloud;
    private List<String> host;
    private int clientCached;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public int getCloud() {
        return cloud;
    }

    public boolean isCloud() {
        return getCloud() > 0;
    }

    public void setCloud(int cloud) {
        this.cloud = cloud;
    }

    public List<String> getHost() {
        return host;
    }

    public void setHost(List<String> host) {
        this.host = host;
    }

    public int getClientCached() {
        return clientCached;
    }

    public void setClientCached(int clientCached) {
        this.clientCached = clientCached;
    }

    public boolean isClientCached() {
        return getClientCached() > 0;
    }

    @Override
    public String toString() {
        return "Solr{" +
                "name='" + name + '\'' +
                ", collection='" + collection + '\'' +
                ", cloud=" + cloud +
                ", host=" + host +
                ", clientCached=" + clientCached +
                '}';
    }
}
