package com.jorado.search.hotwordservice.taskhandles;


import com.jorado.search.core.config.Solr;
import com.jorado.search.core.config.SolrConfig;
import com.jorado.search.core.exception.NoAssignSolrException;
import com.jorado.search.core.solrclient.SolrjClient;
import com.jorado.search.core.solrclient.SolrjClientPool;
import com.jorado.search.core.solrclient.SolrjProxy;
import com.jorado.search.core.task.BaseHandler;
import com.jorado.search.hotword.config.RemoteSettings;
import com.jorado.logger.EventClient;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author len.zhang
 */
@Service
@Description("热词Solr优化")
public final class SolrOptimizeHandler extends BaseHandler {

    @Override
    protected void exec(Object... args) {

        if (!RemoteSettings.updateMatchRows()) {
            return;
        }

        Solr solr = SolrConfig.getInstance().getSolr();

        if (null == solr || CollectionUtils.isEmpty(solr.getHost())) {
            throw new NoAssignSolrException();
        }

        try {
            SolrjClient solrjClient = SolrjClientPool.newClient(solr.getHost(), solr.isCloud());
            SolrjProxy solrjProxy = new SolrjProxy(solr.getCollection(), solrjClient);
            solrjProxy.optimize();
        } catch (Exception ex) {
            EventClient.getDefault().submitException("热词solr优化错误", ex);
        }
    }
}
