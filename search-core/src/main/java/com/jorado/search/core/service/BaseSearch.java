package com.jorado.search.core.service;

import com.jorado.search.core.ClientFilter;
import com.jorado.search.core.QueryBuilder;
import com.jorado.search.core.SearchContext;
import com.jorado.search.core.consts.ErrorConsts;
import com.jorado.search.core.exception.*;
import com.jorado.search.core.model.GroupResult;
import com.jorado.search.core.model.SearchResult;
import com.jorado.search.core.model.searchinfo.SearchInfo;
import com.jorado.search.core.solrclient.SolrjOption;
import com.jorado.search.core.solrclient.SolrjProxy;
import com.jorado.search.core.util.MetaUtils;
import com.jorado.logger.EventBuilder;
import com.jorado.logger.EventClient;
import com.jorado.logger.util.Stopwatch;
import com.jorado.logger.util.StringUtils;
import com.jorado.core.Result;
import com.jorado.core.ResultStatus;
import com.jorado.zkconfig.AppSettings;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.*;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author len.zhang
 * 搜索抽象模板类，默认搜索继承此类即可
 * 轻量级搜索可以使用FastSearch {@link FastSearch}
 * 如需要更灵活的搜索，请使用基于调用链的搜索实现{@link com.jorado.search.core.Search}
 */
public abstract class BaseSearch<T> implements SearchService<T> {

    /**
     * 查询模板方法
     *
     * @return
     */
    @Override
    public Result<SearchResult<T>> search(SearchInfo searchInfo) {

        EventBuilder eventBuilder = EventClient.getDefault().createEvent();

        eventBuilder.addTags(MetaUtils.getDescription(this.getClass()));

        Result<SearchResult<T>> result = Result.OK;

        SearchContext searchContext = new SearchContext(searchInfo);

        List<String> debugInfo = new ArrayList<>();

        try {

            if (null == searchInfo) {
                throw new IllegalArgumentException("SearchInfo param is null");
            }

            //搜索前执行操作
            Result beforeResult = before(searchContext);
            if (beforeResult.isFail()) {
                throw new ActionBeforeException(beforeResult.getMessage());
            }

            //客户端过滤器
            ClientFilter filter = new ClientFilter(searchInfo);
            Result filterResult = filter.filter();
            if (filterResult.isFail()) {
                throw new ClientFilterException(filterResult.getMessage());
            }

            //校准查询条件
            Result adjustResult = adjust(searchContext);
            if (adjustResult.isFail()) {
                throw new QueryAdjustException(adjustResult.getMessage());
            }

            SolrQuery query = new QueryBuilder(searchInfo).build();

            SolrjOption solrjOption = assignSolr(searchContext);

            searchContext.setSolrjOption(solrjOption);

            //记录solr查询整体使用时间
            Stopwatch stopwatch = Stopwatch.begin();

            SolrjProxy solrjProxy = new SolrjProxy(solrjOption);

            QueryResponse rsp = solrjProxy.query(query, searchInfo.getStart(), searchInfo.getRows(), searchInfo.isDebug());

            solrjProxy.close();

            stopwatch.stop();

            SolrDocumentList results = rsp.getResults();

            long numFound = 0;

            List<T> docs = new ArrayList<>();

            if (null != results) {
                results.forEach(doc -> docs.add(format(doc)));
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
            debugInfo.add("stime:" + rsp.getQTime());
            debugInfo.add("numFound:" + numFound);

            eventBuilder.addData("solr.option", solrjOption.toString())
                    .addData("solr.numFound", numFound)
                    .addData("solr.qtime", stopwatch.getDuration())
                    .addData("solr.status", rsp.getResponseHeader().get("status"))
                    .addData("solr.stime", rsp.getQTime());

            if (solrjOption.isCloud()) {
                eventBuilder.addData("solr.server", rsp.getResponse().get("server"));
            }

            searchContext.setSearchResult(searchResult);

            //搜索完执行操作
            Result afterResult = after(searchContext);
            if (afterResult.isFail()) {
                throw new ActionAfterException(afterResult.getMessage());
            }

            result = new Result<>(searchResult, ResultStatus.OK, "Search success");

        } catch (ClientFilterException ex) {
            eventBuilder.setException(ex);
            result = new Result<>(ResultStatus.BAD_REQUEST, ex.getMessage());

        } catch (NoAssignSolrException ex) {

            eventBuilder.setException(ex);
            result = new Result<>(ResultStatus.ERROR, ErrorConsts.Search.ASSIGN_SOLR);

        } catch (ActionBeforeException ex) {

            eventBuilder.setException(ex);
            result = new Result<>(ResultStatus.ERROR, ErrorConsts.Search.BEFORE_SEARCH);

        } catch (QueryAdjustException ex) {

            eventBuilder.setException(ex);
            result = new Result<>(ResultStatus.ERROR, ErrorConsts.Search.ADJUST_QUERY);

        } catch (ActionAfterException ex) {

            eventBuilder.setException(ex);
            result = new Result<>(ResultStatus.ERROR, ErrorConsts.Search.AFTER_SEARCH);

        } catch (Exception ex) {
            eventBuilder.setException(ex);
            result = new Result<>(ResultStatus.ERROR, String.format("%s,%s", ErrorConsts.Search.SEARCH_FAIL, ex.getMessage()));

        } finally {

            if (null != searchInfo && (searchInfo.isDebug() || AppSettings.getInstance().debug())) {
                result.setDebugInfo(debugInfo);
                eventBuilder.addData("debug", debugInfo);
            }

            //打印日志
            if (eventBuilder.getTarget().isError() || AppSettings.getInstance().log()) {
                eventBuilder.addData(searchContext.getPlayload()).addData("searchInfo", searchInfo).setMessage(result.getMessage()).asyncSubmit();
            }
        }

        return result;
    }

    /**
     * 搜索之前的操作，可修改条件
     *
     * @return
     */
    protected abstract Result before(SearchContext context);

    /**
     * 调整query
     *
     * @return
     */
    protected abstract Result adjust(SearchContext context);

    /**
     * 指派solr
     *
     * @return
     * @
     */
    protected abstract SolrjOption assignSolr(SearchContext context);

    /**
     * 格式化solr文档
     *
     * @param doc
     * @return
     */
    protected abstract T format(SolrDocument doc);

    /**
     * 搜索完成后的操作，可修改搜索结果
     *
     * @return
     */
    protected abstract Result after(SearchContext context);

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
    private List<GroupResult<T>> buildGroupResult(GroupResponse groupResponse) {

        List<GroupResult<T>> groupResults = new ArrayList<>();

        List<GroupCommand> groupCommands = groupResponse.getValues();

        if (CollectionUtils.isEmpty(groupCommands)) {
            return groupResults;
        }

        for (GroupCommand groupCommand : groupCommands) {
            GroupResult groupResult = new GroupResult();
            groupResult.setName(groupCommand.getName());
            groupResult.setMatches(groupCommand.getMatches());
            groupResult.setNgroups(groupCommand.getNGroups() != null ? groupCommand.getNGroups() : 0);
            Map<String, List<T>> groups = new LinkedHashMap<>();
            groupResult.setGroups(groups);

            List<Group> groupValues = groupCommand.getValues();

            if (CollectionUtils.isEmpty(groupValues)) {
                continue;
            }

            for (Group group : groupValues) {
                List<T> groupDocs = new ArrayList<>();
                //转换成group对象
                for (SolrDocument doc : group.getResult()) {
                    groupDocs.add(format(doc));
                }
                groups.put(group.getGroupValue(), groupDocs);
            }

            groupResults.add(groupResult);
        }

        return groupResults;
    }
}
