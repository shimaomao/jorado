package com.jorado.search.hotwordservice.service;

import com.jorado.search.core.config.Solr;
import com.jorado.search.core.config.SolrConfig;
import com.jorado.search.core.consts.ErrorConsts;
import com.jorado.search.core.exception.NoAssignSolrException;
import com.jorado.search.core.model.SearchResult;
import com.jorado.search.core.model.searchinfo.SearchInfo;
import com.jorado.search.core.service.BaseExport;
import com.jorado.search.core.service.FastSearch;
import com.jorado.search.core.service.SearchService;
import com.jorado.search.core.service.impl.SolrExporter;
import com.jorado.search.core.solrclient.SolrjClient;
import com.jorado.search.core.solrclient.SolrjClientPool;
import com.jorado.search.core.solrclient.SolrjOption;
import com.jorado.search.core.util.SearchBuilder;
import com.jorado.search.core.util.condition.Condition;
import com.jorado.search.core.util.condition.solr.QueryCondition;
import com.jorado.logger.EventClient;
import com.jorado.core.Result;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.context.annotation.Description;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author len.zhang
 */
@Description("热词职位匹配数更新")
public final class MatchRowsUpdateService extends BaseExport<SolrDocument> {

    private final SolrjOption positionSolr;
    private final SolrExporter exporter;

    public MatchRowsUpdateService(SolrjOption hotwordSolr, SolrQuery solrQuery) {
        this.exporter = new SolrExporter(hotwordSolr, solrQuery);
        this.positionSolr = assignPositionSolr();
        this.setExporter(this.exporter);
    }

    @Override
    protected void export(List<SolrDocument> list) {

        try {

            Map<String, Integer> matchRows = getMatchRows(list);

            if (null == matchRows) {
                return;
            }

            List<SolrInputDocument> docs = new ArrayList<>();
            for (SolrDocument t : list) {
                String id = t.getFieldValue("HOT_ID").toString();
                if (!matchRows.containsKey(id)) {
                    continue;
                }
                int rows = matchRows.get(id);
                SolrInputDocument doc = new SolrInputDocument();
                doc.addField("HOT_ID", id);
                Map<String, Integer> operation = new HashMap<>();
                operation.put("set", rows);
                doc.addField("HOT_MATCH_ROWS", operation);
                docs.add(doc);
            }
            if (!docs.isEmpty()) {
                this.exporter.getSourceSolrProxy().add(docs);
                this.exporter.getSourceSolrProxy().commit();
            }
        } catch (Exception ex) {
            EventClient.getDefault().submitException(ErrorConsts.Export.EXPORT_FAIL, ex);
        }
    }


    @Override
    protected Result before() {
        return Result.OK;
    }

    @Override
    protected Result after(Result result) {
        return Result.OK;
    }

    @Override
    protected Result filter(SolrDocument data) {
        return Result.OK;
    }

    /**
     * 使用facet批量获取减少io操作
     *
     * @param list
     * @return
     */
    private Map<String, Integer> getMatchRows(List<SolrDocument> list) {

        SearchBuilder searchBuilder = new SearchBuilder(0, 0);

        Map<String, String> facetMap = new HashMap<>();

        for (SolrDocument t : list) {

            String word = t.getFieldValue("HOT_WORD").toString().trim();
            String id = t.getFieldValue("HOT_ID").toString();

            Condition query1 = new QueryCondition("JOB_TITLE", word);
            Condition query2 = new QueryCondition("COMPANY_NAME", word);
            Condition query3 = new QueryCondition("JOB_DESC", word);
            Condition query = query1.or(query2).or(query3);

            searchBuilder.addFacetQuery(query);

            facetMap.put(query.toQueryString(), id);
        }

        SearchInfo searchInfo = searchBuilder.build();
        SearchService searcher = new FastSearch(this.positionSolr);
        Result<SearchResult<Map<String, Object>>> searchResult = searcher.search(searchInfo);
        if (searchResult.isOk()) {
            Map<String, Integer> facetQuery = searchResult.getData().getFacet().getFacet_queries();
            if (null == facetQuery) {
                return null;
            }
            Map<String, Integer> matchRows = new HashMap<>();
            for (Map.Entry<String, Integer> entry : facetQuery.entrySet()) {
                matchRows.put(facetMap.get(entry.getKey()), entry.getValue());
            }
            return matchRows;

        } else {
            return null;
        }
    }

    private SolrjOption assignPositionSolr() {

        Solr solr = SolrConfig.getInstance().getSolr("position");

        if (null == solr || CollectionUtils.isEmpty(solr.getHost())) {
            throw new NoAssignSolrException();
        }

        SolrjClient solrjClient = SolrjClientPool.newClient(solr.getHost(), solr.isCloud());

        return new SolrjOption(solr.getCollection(), solrjClient);
    }
}
