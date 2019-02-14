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
import com.jorado.search.hotwordservice.service.CompanyNameSyncService;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author len.zhang
 */
@Service
@Description("在线公司名称热词同步")
public final class CompanyNameSyncHandler extends BaseHandler {

    @Override
    protected void exec(Object... args) {

        if (!RemoteSettings.onlineCompanySyncEnabled()) {
            return;
        }

        SolrjOption solrjOption = assignPositionSolr();

        ExportService importService = new CompanyNameSyncService(solrjOption);
        importService.dataExport();
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
