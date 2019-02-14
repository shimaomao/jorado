package com.jorado.search.core.solrclient;

import com.jorado.logger.util.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.FacetParams;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;

/**
 * solr client 包装器
 */
public final class SolrjProxy implements Closeable {

    private String collection;
    private SolrjClient solrjClient;

    public SolrjProxy(SolrjOption option) {
        this.collection = option.getCollection();
        this.solrjClient = option.getSolrjClient();
    }

    public SolrjProxy(String collection, List<String> host) {
        this.collection = collection;
        this.solrjClient = new SolrjClient(host);
    }

    public SolrjProxy(String collection, String host) {
        this.collection = collection;
        this.solrjClient = new SolrjClient(host);
    }

    public SolrjProxy(String collection, SolrjClient solrjClient) {
        this.collection = collection;
        this.solrjClient = solrjClient;
    }

    public QueryResponse query(int start, int rows, boolean debug) throws SolrServerException, IOException {
        return this.query("*:*", start, rows, debug);
    }

    public QueryResponse query(String q, int start, int rows, boolean debug) throws SolrServerException, IOException {
        SolrQuery query = new SolrQuery(q);
        return this.query(query, start, rows, debug);
    }

    public QueryResponse query(String q, int start, int rows) throws SolrServerException, IOException {
        return this.query(q, start, rows, false);
    }

    public QueryResponse query(SolrQuery query, int start, int rows) throws SolrServerException, IOException {
        return this.query(query, start, rows, false);
    }

    public QueryResponse query(SolrQuery query, int start, int rows, boolean debug, int timeout) throws SolrServerException, IOException {
        if (null == query) {
            throw new IllegalArgumentException("SolrQuery is null");
        }
        if (StringUtils.isNullOrWhiteSpace(query.getQuery())) {
            query.setQuery("*:*");
        }
        if (StringUtils.isNullOrWhiteSpace(query.getFields())) {
            query.setFields("*");
        }
        if (StringUtils.isNullOrWhiteSpace(query.get("q.op"))) {
            query.set("q.op", "AND");
        }
        query.setShowDebugInfo(debug);
        query.setStart(start);
        query.setRows(rows);
        query.set("wt", "json");
        query.set("indent", "off");
        query.setTimeAllowed(timeout);
        query.setFacetMissing(false);
        query.setFacetMinCount(1);
        query.setFacetSort(FacetParams.FACET_SORT_COUNT);
        if (this.isCloud()) {
            query.set("shards.tolerant", true);//只从存活的节点上获取数据
        }
        return this.solrjClient.getRealClient().query(this.collection, query, SolrRequest.METHOD.POST);
    }

    public QueryResponse query(SolrQuery query, int start, int rows, boolean debug) throws SolrServerException, IOException {
        return this.query(query, start, rows, debug, 2000);
    }

    public QueryResponse query(String field, String value, int start, int rows, boolean debug) throws SolrServerException, IOException {
        String q = String.format("%s:%s", field, value);
        return this.query(q, start, rows, debug);
    }

    public QueryResponse queryById(String... ids) throws SolrServerException, IOException {
        return this.queryById("ID", Arrays.asList(ids));
    }

    public QueryResponse queryById(String field, String id) throws SolrServerException, IOException {
        return this.query(field, id, 0, 1, false);
    }

    public QueryResponse queryById(String field, List<String> ids) throws SolrServerException, IOException {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (int i = 0; i < ids.size(); i++) {
            index++;
            sb.append(String.format("%s:%s", field, ids.get(i)));
            if (index < ids.size()) {
                sb.append(" OR ");
            }

        }
        return this.query(sb.toString(), 0, ids.size(), false);
    }

    public UpdateResponse add(SolrInputDocument... doc) throws SolrServerException, IOException {
        List<SolrInputDocument> documents = new ArrayList<>();
        for (SolrInputDocument document : doc) {
            documents.add(document);
        }
        return this.solrjClient.getRealClient().add(this.collection, documents);
    }

    public UpdateResponse add(Collection<SolrInputDocument> docs) throws SolrServerException, IOException {
        return this.solrjClient.getRealClient().add(this.collection, docs);
    }

    public UpdateResponse add(Map<String, String>... doc) throws SolrServerException, IOException {
        List<SolrInputDocument> documents = new ArrayList<>();
        for (Map<String, String> map : doc) {
            SolrInputDocument document = new SolrInputDocument();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                document.addField(entry.getKey(), entry.getValue());
            }
            documents.add(document);
        }
        return this.solrjClient.getRealClient().add(documents);
    }

    public UpdateResponse deleteAll() throws SolrServerException, IOException {
        return this.solrjClient.getRealClient().deleteByQuery(this.collection, "*:*");
    }

    public UpdateResponse deleteByQuery(String query) throws SolrServerException, IOException {
        return this.solrjClient.getRealClient().deleteByQuery(this.collection, query);
    }

    public UpdateResponse deleteById(String... ids) throws SolrServerException, IOException {
        return this.deleteById(Arrays.asList(ids));
    }

    public UpdateResponse deleteById(List<String> ids) throws SolrServerException, IOException {
        return this.solrjClient.getRealClient().deleteById(this.collection, ids);
    }

    public UpdateResponse commit() throws SolrServerException, IOException {
        return this.solrjClient.getRealClient().commit(this.collection);
    }

    public UpdateResponse optimize() throws SolrServerException, IOException {
        return this.solrjClient.getRealClient().optimize(this.collection);
    }

    public SolrjProxy setCollection(String collection) {
        this.collection = collection;
        return this;
    }

    public SolrjProxy setSolrjClient(SolrjClient solrjClient) {
        this.solrjClient = solrjClient;
        return this;
    }

    public String getCollection() {
        return this.collection;
    }

    public String getHost() {
        return this.solrjClient.getHost();
    }

    public SolrjClient getSolrjClient() {
        return this.solrjClient;
    }

    public boolean isCloud() {
        return this.solrjClient.isCloud();
    }

    @Override
    public void close() throws IOException {
        this.solrjClient.close();
    }
}
