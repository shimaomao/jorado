package com.jorado.search.core.solrclient;

import com.jorado.logger.util.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * solr client 包装器
 */
public final class SolrjClient implements Serializable, Cloneable, Closeable {

    private String host;
    private int soTimeout;
    private int connectionTimeout;
    private int maxConnectionsPerHost;
    private int maxTotalConnections;
    private boolean allowCompression;
    private int maxRetries;
    private SolrClient client;
    private boolean isCloud;
    private boolean isReusable;

    SolrjClient(String host, Builder builder) {
        this.isCloud = host.contains(",");
        this.host = host;
        this.soTimeout = builder.soTimeout;
        this.connectionTimeout = builder.connectionTimeout;
        this.maxConnectionsPerHost = builder.maxConnectionsPerHost;
        this.maxTotalConnections = builder.maxTotalConnections;
        this.allowCompression = builder.allowCompression;
        this.maxRetries = builder.maxRetries;
        this.build();
    }

    public SolrjClient(List<String> host) {
        this(StringUtils.joinString(host, ","));
    }

    public SolrjClient(String host) {
        this.isCloud = host.contains(",");
        this.host = host;
        this.soTimeout = 2000;
        this.connectionTimeout = 2000;
        this.maxConnectionsPerHost = 64;
        this.maxTotalConnections = 256;
        this.build();
    }

    public static SolrjClient newClient(String host) {
        return new Builder().build(host);
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public SolrClient getRealClient() {
        return client;
    }

    public boolean isCloud() {
        return isCloud;
    }

    public String getHost() {
        return host;
    }

    public boolean isReusable() {
        return isReusable;
    }

    public void setReusable() {
        isReusable = true;
    }

    @Override
    public void close() throws IOException {
        if (!isReusable) {
            this.client.close();
        }
    }

    @Override
    public SolrjClient clone() {
        return new Builder(this).build(this.host);
    }

    @Override
    public String toString() {
        return "{" +
                "host='" + host + '\'' +
                ", soTimeout=" + soTimeout +
                ", connectionTimeout=" + connectionTimeout +
                ", maxConnectionsPerHost=" + maxConnectionsPerHost +
                ", maxTotalConnections=" + maxTotalConnections +
                ", allowCompression=" + allowCompression +
                ", maxRetries=" + maxRetries +
                ", isCloud=" + isCloud +
                ", isReusable=" + isReusable +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SolrjClient that = (SolrjClient) o;
        return soTimeout == that.soTimeout &&
                connectionTimeout == that.connectionTimeout &&
                maxConnectionsPerHost == that.maxConnectionsPerHost &&
                maxTotalConnections == that.maxTotalConnections &&
                maxRetries == that.maxRetries &&
                isCloud() == that.isCloud() &&
                isReusable() == that.isReusable() &&
                Objects.equals(getHost(), that.getHost()) &&
                Objects.equals(allowCompression, that.allowCompression);
    }

    @Override
    public int hashCode() {

        return Objects.hash(getHost(), soTimeout, connectionTimeout, maxConnectionsPerHost, maxTotalConnections, allowCompression, maxRetries, isCloud(), isReusable());
    }

    /**
     * 生成真实client
     */
    private void build() {

        //是否为集群
        if (this.isCloud) {
            HttpClient httpClient = HttpClientUtil.createClient(null);
            HttpClientUtil.setSoTimeout(httpClient, this.soTimeout);
            HttpClientUtil.setConnectionTimeout(httpClient, this.connectionTimeout);
            HttpClientUtil.setMaxConnectionsPerHost(httpClient, this.maxConnectionsPerHost);
            HttpClientUtil.setMaxConnections(httpClient, this.maxTotalConnections);
            CloudSolrjClient csCli = new CloudSolrjClient(this.host, httpClient);
            this.client = csCli;
            return;
        }

        HttpSolrClient cli = new HttpSolrClient(String.format("http://%s/solr/", this.host));
        cli.setSoTimeout(this.soTimeout); // socket read timeout
        cli.setConnectionTimeout(this.connectionTimeout);
        cli.setDefaultMaxConnectionsPerHost(this.maxConnectionsPerHost);
        cli.setMaxTotalConnections(this.maxTotalConnections);
        cli.setAllowCompression(this.allowCompression);
        this.client = cli;
    }

    public static class Builder {

        private int soTimeout;
        private int connectionTimeout;
        private int maxConnectionsPerHost;
        private int maxTotalConnections;
        private boolean allowCompression;
        private int maxRetries;

        public Builder() {
            this.soTimeout = 2000;
            this.connectionTimeout = 2000;
            this.maxConnectionsPerHost = 64;
            this.maxTotalConnections = 256;
        }

        public Builder(SolrjClient client) {
            this.soTimeout = client.soTimeout;
            this.connectionTimeout = client.connectionTimeout;
            this.maxConnectionsPerHost = client.maxConnectionsPerHost;
            this.maxTotalConnections = client.maxTotalConnections;
            this.allowCompression = client.allowCompression;
            this.maxRetries = client.maxRetries;
        }

        public Builder setSoTimeout(int soTimeout) {
            this.soTimeout = soTimeout;
            return this;
        }

        public Builder setConnectionTimeout(int connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
            return this;
        }

        public Builder setMaxConnectionsPerHost(int maxConnectionsPerHost) {
            this.maxConnectionsPerHost = maxConnectionsPerHost;
            return this;
        }

        public Builder setMaxTotalConnections(int maxTotalConnections) {
            this.maxTotalConnections = maxTotalConnections;
            return this;
        }

        public Builder setAllowCompression(boolean allowCompression) {
            this.allowCompression = allowCompression;
            return this;
        }

        public Builder setMaxRetries(int maxRetries) {
            this.maxRetries = maxRetries;
            return this;
        }

        public SolrjClient build(String host) {

            if (StringUtils.isNullOrWhiteSpace(host)) {
                throw new IllegalArgumentException("Host param is null");
            }

            return new SolrjClient(host, this);
        }
    }
}
