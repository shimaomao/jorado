package com.jorado.search.hotwordservice.taskhandles;


import com.jorado.search.core.config.Solr;
import com.jorado.search.core.config.SolrConfig;
import com.jorado.search.core.exception.NoAssignSolrException;
import com.jorado.search.core.service.ExportService;
import com.jorado.search.core.solrclient.SolrjClient;
import com.jorado.search.core.solrclient.SolrjClientPool;
import com.jorado.search.core.solrclient.SolrjOption;
import com.jorado.search.core.task.BaseHandler;
import com.jorado.search.hotword.config.RemoteSettings;
import com.jorado.search.hotwordservice.service.MatchRowsUpdateService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author len.zhang
 */
@Service
@Description("热词匹配行数更新")
public final class MatchRowsUpdateHandler extends BaseHandler {

    @Override
    protected void exec(Object... args) {

        if (!RemoteSettings.updateMatchRows()) {
            return;
        }

        SolrQuery sq = new SolrQuery();
        if (args.length > 0) {
            for (Object arg : args) {
                sq.setQuery(String.format("HOT_FLAG:%s", arg));
                break;
            }
        } else {
            sq.setQuery("HOT_FLAG:1 OR HOT_FLAG:2");
        }
        sq.setFields("HOT_ID,HOT_WORD");
        sq.setSort("HOT_FLAG", SolrQuery.ORDER.asc);

        SolrjOption solrjOption = assignHotwordSolr();

        ExportService exportService = new MatchRowsUpdateService(solrjOption, sq);
        exportService.dataExport();
    }


    private SolrjOption assignHotwordSolr() {

        Solr solr = SolrConfig.getInstance().getSolr();

        if (null == solr || CollectionUtils.isEmpty(solr.getHost())) {
            throw new NoAssignSolrException();
        }

        SolrjClient solrjClient = SolrjClientPool.newClient(solr.getHost(), solr.isCloud());

        return new SolrjOption(solr.getCollection(), solrjClient);
    }

}
