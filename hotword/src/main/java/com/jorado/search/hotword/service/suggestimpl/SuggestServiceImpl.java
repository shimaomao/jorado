package com.jorado.search.hotword.service.suggestimpl;

import com.jorado.search.core.config.Solr;
import com.jorado.search.core.config.SolrConfig;
import com.jorado.search.core.exception.NoAssignSolrException;
import com.jorado.search.core.model.SearchResult;
import com.jorado.search.core.model.searchinfo.SearchInfo;
import com.jorado.search.core.service.FastSearch;
import com.jorado.search.core.service.SearchService;
import com.jorado.search.core.solrclient.SolrjClient;
import com.jorado.search.core.solrclient.SolrjClientPool;
import com.jorado.search.core.solrclient.SolrjOption;
import com.jorado.search.core.util.SearchBuilder;
import com.jorado.search.core.util.condition.Condition;
import com.jorado.search.core.util.condition.QueryCondition;
import com.jorado.search.hotword.config.RemoteSettings;
import com.jorado.search.hotword.exception.SuggestNotEnabledException;
import com.jorado.search.hotword.model.enums.HotWordType;
import com.jorado.search.hotword.model.suggest.SuggestResult;
import com.jorado.search.hotword.model.suggest.SuggestWord;
import com.jorado.search.hotword.service.SuggestService;
import com.jorado.logger.EventBuilder;
import com.jorado.logger.EventClient;
import com.jorado.logger.util.StringUtils;
import com.jorado.core.Result;
import com.jorado.core.ResultStatus;
import com.jorado.zkconfig.AppSettings;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Map;


/**
 * Created by len.zhang on 2018/3/30.
 * 热词联想服务
 */
@Service
@Scope("prototype")
public final class SuggestServiceImpl implements SuggestService {

    /**
     * solr client连接池
     */
    private static SolrjClientPool solrjClientPool = new SolrjClientPool(5);

    /**
     * 热词联想模板方法
     *
     * @param kw
     * @param rows
     * @return
     */
    @Override
    public Result<SuggestResult> dataSuggest(HotWordType flag, String kw, int rows, boolean debug) {

        EventBuilder eventBuilder = EventClient.getDefault().createEvent();

        eventBuilder.addTags("热词联想");

        Result<SuggestResult> result = Result.OK;

        try {

            if (!RemoteSettings.enabled()) {
                throw new SuggestNotEnabledException();
            }

            if (StringUtils.isNullOrWhiteSpace(kw)) {
                throw new IllegalArgumentException("关键字不能为空");
            }

            SearchBuilder searchBuilder = new SearchBuilder(0, rows, debug);

            Condition query1 = new QueryCondition(String.format("HOT_WORD:%s*", kw));
            Condition query2 = new QueryCondition(String.format("HOT_SAME:%s*^100.0", kw));
            Condition query3 = new QueryCondition(String.format("HOT_PINYIN:%s*^70.0", kw));
            Condition query4 = new QueryCondition(String.format("HOT_PY:%s*^40.0", kw));
            Condition query = query1.or(query2).or(query3).or(query4);

            searchBuilder.addQuery(query)
                    .setField("HOT_WORD,HOT_FLAG")
                    .addFilterQuery(String.format("HOT_MATCH_ROWS:[%d TO *]", RemoteSettings.showMaxRows()));

            if (HotWordType.FULL_INDEX.equals(flag)) {

                searchBuilder.addFilterQuery("HOT_FLAG:[1 TO 2]")
                        .setSortMode("HOT_FLAG asc,score desc,HOT_CLICK desc,HOT_MATCH_ROWS desc");

            } else {

                searchBuilder.addFilterQuery("HOT_FLAG", flag.getValue())
                        .setSortMode("score desc,HOT_CLICK desc,HOT_MATCH_ROWS desc");
            }

            SearchInfo searchInfo = searchBuilder.build();

            SearchService searchService = new FastSearch(assignSolr());

            Result<SearchResult<Map<String, Object>>> searchResult = searchService.search(searchInfo);

            SuggestResult suggestResult = new SuggestResult(kw, rows, flag);

            if (searchResult.isOk()) {

                for (Map<String, Object> document : searchResult.getData().getDocs()) {
                    SuggestWord word = new SuggestWord(document.get("HOT_WORD").toString(), Integer.valueOf(document.get("HOT_FLAG").toString()));
                    suggestResult.getWords().add(word);
                }
                suggestResult.setNumFound(searchResult.getData().getNumFound());
            }

            result = new Result<>(suggestResult, searchResult.getCode(), searchResult.getMessage());

            result.setDebugInfo(searchResult.getDebugInfo());

        } catch (SuggestNotEnabledException ex) {

            eventBuilder.setException(ex);
            result = new Result<>(ResultStatus.FORBIDDEN, ex.getMessage());

        } catch (Exception ex) {

            eventBuilder.setException(ex);
            result = new Result<>(ResultStatus.ERROR, ex.getMessage());

        } finally {
            //打印日志
            if (eventBuilder.getTarget().isError() || AppSettings.getInstance().log()) {
                eventBuilder.addData("type", flag.toString()).addData("kw", kw).addData("rows", rows).setMessage(result.getMessage()).asyncSubmit();
            }
        }
        return result;
    }


    private SolrjOption assignSolr() {

        Solr solr = SolrConfig.getInstance().getSolr();

        if (null == solr || CollectionUtils.isEmpty(solr.getHost())) {
            throw new NoAssignSolrException();
        }

        SolrjClient solrjClient = solrjClientPool.getClient(solr.isClientCached(), solr.getHost(), solr.isCloud());

        return new SolrjOption(solr.getCollection(), solrjClient);
    }

}
