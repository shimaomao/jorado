package com.jorado.search.core.service;

import com.jorado.search.core.QueryBuilder;
import com.jorado.search.core.model.GroupResult;
import com.jorado.search.core.model.SearchResult;
import com.jorado.search.core.model.searchinfo.SearchInfo;
import com.jorado.search.core.solrclient.SolrjClient;
import com.jorado.search.core.solrclient.SolrjOption;
import com.jorado.search.core.solrclient.SolrjProxy;
import com.jorado.logger.EventClient;
import com.jorado.logger.util.StringUtils;
import com.jorado.core.Result;
import com.jorado.core.ResultStatus;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.*;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author len.zhang
 * 轻量级搜索实现
 * 如需更灵活的搜索，请使用基于调用链的搜索实现{@link com.jorado.search.core.Search}
 */
@Service
public final class FastSearch implements SearchService {

    /**
     * solr集合
     */
    private String collection;

    /**
     * solr链接
     */
    private SolrjClient solrjClient;

    public FastSearch() {

    }

    public FastSearch(String collection, List<String> host) {
        this(collection, new SolrjClient(host));
    }

    public FastSearch(String collection, String host) {
        this(collection, new SolrjClient(host));
    }

    public FastSearch(SolrjOption solrjOption) {
        this(solrjOption.getCollection(), solrjOption.getSolrjClient());
    }

    public FastSearch(String collection, SolrjClient solrjClient) {
        this.collection = collection;
        this.solrjClient = solrjClient;
    }

    /**
     * 查询
     *
     * @return
     */
    @Override
    public Result<SearchResult<Map<String, Object>>> search(SearchInfo searchInfo) {

        Result<SearchResult<Map<String, Object>>> result = Result.OK;

        List<String> debugInfo = new ArrayList<>();

        try {

            if (StringUtils.isNullOrWhiteSpace(collection)) {
                throw new IllegalArgumentException("Solr Collection is null");
            }

            if (null == this.solrjClient) {
                throw new IllegalArgumentException("Solr client is null");
            }

            if (null == searchInfo) {
                throw new IllegalArgumentException("SearchInfo param is null");
            }

            SolrQuery query = new QueryBuilder(searchInfo).build();

            SolrjProxy solrjProxy = new SolrjProxy(this.collection, this.solrjClient);

            QueryResponse rsp = solrjProxy.query(query, searchInfo.getStart(), searchInfo.getRows(), searchInfo.isDebug());

            solrjProxy.close();

            SolrDocumentList results = rsp.getResults();

            long numFound = 0;

            List<Map<String, Object>> docs = new ArrayList<>();

            if (null != results) {
                docs.addAll(results);
                numFound = results.getNumFound();
            }

            SearchResult searchResult = new SearchResult(searchInfo.getStart(), searchInfo.getRows(), numFound, docs);

            //设置facetquery数据
            Map<String, Integer> facetQuerys = rsp.getFacetQuery();
            if (!CollectionUtils.isEmpty(facetQuerys)) {
                searchResult.getFacet().setFacet_queries(facetQuerys);
            }

            List<FacetField> facetFields = rsp.getFacetFields();
            if (!CollectionUtils.isEmpty(facetFields)) {
                searchResult.getFacet().setFacet_fields(buildFacetFieldsResult(facetFields));
            }

            GroupResponse groupResponse = rsp.getGroupResponse();
            if (null != groupResponse) {
                searchResult.setGroup(buildGroupResult(groupResponse));
            }

            debugInfo.add("query:" + StringUtils.urlDecode(query.toString()));
            debugInfo.add("stime:" + rsp.getQTime() + " ms");
            debugInfo.add("numFound:" + numFound);

            result = new Result<>(searchResult, ResultStatus.OK);

        } catch (Exception ex) {
            EventClient.getDefault().asyncSubmitException("Search fail", ex);
            result = new Result<>(ResultStatus.ERROR, ex.getMessage());

        } finally {
            if (null != searchInfo && searchInfo.isDebug()) {
                result.setDebugInfo(debugInfo);
            }
        }

        return result;
    }

    /**
     * 组装facetfields结果
     *
     * @param facetFields
     * @return
     */
    private Map<String, Map<String, Long>> buildFacetFieldsResult(List<FacetField> facetFields) {

        Map<String, Map<String, Long>> facetFieldsResult = new LinkedHashMap<>();

        if (CollectionUtils.isEmpty(facetFields)) {
            return facetFieldsResult;
        }

        for (FacetField field : facetFields) {
            Map<String, Long> fieldsCount = new LinkedHashMap<>();

            List<FacetField.Count> facetCount = field.getValues();

            if (CollectionUtils.isEmpty(facetCount)) continue;

            for (FacetField.Count count : facetCount) {
                fieldsCount.put(count.getName(), count.getCount());
            }

            facetFieldsResult.put(field.getName(), fieldsCount);
        }

        return facetFieldsResult;
    }

    /**
     * 组装group结果
     *
     * @param groupResponse
     * @return
     */
    private List<GroupResult<Map<String, Object>>> buildGroupResult(GroupResponse groupResponse) {

        List<GroupResult<Map<String, Object>>> groupResults = new ArrayList<>();

        List<GroupCommand> groupCommands = groupResponse.getValues();

        if (CollectionUtils.isEmpty(groupCommands)) {
            return groupResults;
        }

        for (GroupCommand groupCommand : groupCommands) {
            GroupResult groupResult = new GroupResult();
            groupResult.setName(groupCommand.getName());
            groupResult.setMatches(groupCommand.getMatches());
            groupResult.setNgroups(groupCommand.getNGroups() != null ? groupCommand.getNGroups() : 0);
            Map<String, List<Map<String, Object>>> groups = new LinkedHashMap<>();
            groupResult.setGroups(groups);

            List<Group> groupValues = groupCommand.getValues();

            if (CollectionUtils.isEmpty(groupValues)) {
                continue;
            }

            for (Group group : groupValues) {
                List<Map<String, Object>> groupDocs = new ArrayList<>();
                groupDocs.addAll(group.getResult());
                groups.put(group.getGroupValue(), groupDocs);
            }

            groupResults.add(groupResult);
        }

        return groupResults;
    }

    public String getCollection() {
        return collection;
    }

    public SolrjClient getSolrjClient() {
        return solrjClient;
    }

    public FastSearch setCollection(String collection) {
        this.collection = collection;
        return this;
    }

    public FastSearch setSolrjClient(SolrjClient solrjClient) {
        this.solrjClient = solrjClient;
        return this;
    }

}
