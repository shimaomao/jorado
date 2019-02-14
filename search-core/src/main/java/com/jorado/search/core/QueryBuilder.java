package com.jorado.search.core;

import com.jorado.search.core.model.searchinfo.SearchInfo;
import com.jorado.logger.util.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.GroupParams;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author len.zhang
 * 搜索条件建造器
 */
public final class QueryBuilder {

    private SearchInfo searchInfo;

    public QueryBuilder(SearchInfo searchInfo) {
        this.searchInfo = searchInfo;

    }

    /**
     * 组装solrquery
     *
     * @return
     */
    public SolrQuery build() {

        SolrQuery query = new SolrQuery();

        query.setFields(searchInfo.getField());

        if (!CollectionUtils.isEmpty(searchInfo.getQuery())) {
            query.setQuery(StringUtils.joinString(searchInfo.getQuery(), " AND "));
        }

        if (!CollectionUtils.isEmpty(searchInfo.getFilterQuery())) {
            for (String field : searchInfo.getFilterQuery()) {
                query.addFilterQuery(field);
            }
        }

        boolean facetEnabled = false;

        if (null != searchInfo.getFacetSearchInfo()) {

            if (!CollectionUtils.isEmpty(searchInfo.getFacetSearchInfo().getQuery())) {
                for (String field : searchInfo.getFacetSearchInfo().getQuery()) {
                    query.addFacetQuery(field);
                }

                facetEnabled = true;
            }
            if (!CollectionUtils.isEmpty(searchInfo.getFacetSearchInfo().getField())) {
                for (String field : searchInfo.getFacetSearchInfo().getField()) {
                    query.addFacetField(field);
                }

                facetEnabled = true;
            }

            if (facetEnabled) {
                int limit = searchInfo.getFacetSearchInfo().getLimit();
                if (limit < -1) {
                    limit = -1;
                }
                query.setFacetLimit(limit);
            }
        }

        query.setFacet(facetEnabled);

        boolean groupEnabled = false;

        if (null != searchInfo.getGroupSearchInfo()) {

            if (!CollectionUtils.isEmpty(searchInfo.getGroupSearchInfo().getQuery())) {
                String[] values = new String[searchInfo.getGroupSearchInfo().getQuery().size()];
                searchInfo.getGroupSearchInfo().getQuery().toArray(values);
                query.setParam(GroupParams.GROUP_QUERY, values);

                groupEnabled = true;
            }
            if (!CollectionUtils.isEmpty(searchInfo.getGroupSearchInfo().getField())) {
                String[] values = new String[searchInfo.getGroupSearchInfo().getField().size()];
                searchInfo.getGroupSearchInfo().getField().toArray(values);
                query.setParam(GroupParams.GROUP_FIELD, values);

                groupEnabled = true;
            }
        }

        if (groupEnabled) {
            query.setParam(GroupParams.GROUP, groupEnabled);
            query.setParam(GroupParams.GROUP_TOTAL_COUNT, true);
            int limit = searchInfo.getGroupSearchInfo().getLimit();
            if (limit < -1) {
                limit = -1;
            }
            query.setParam(GroupParams.GROUP_LIMIT, Integer.toString(limit));
        }


        if (!StringUtils.isNullOrWhiteSpace(searchInfo.getSortMode())) {
            query.set("sort", searchInfo.getSortMode());
        }

        for (Map.Entry<String, String> entry : searchInfo.getParams().entrySet()) {
            query.set(entry.getKey(), entry.getValue());
        }

        return query;
    }
}
