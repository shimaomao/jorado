package com.jorado.search.hotword.service.exportimpl;

import com.jorado.search.core.config.Solr;
import com.jorado.search.core.config.SolrConfig;
import com.jorado.search.core.consts.ErrorConsts;
import com.jorado.search.core.exception.NoAssignSolrException;
import com.jorado.search.core.service.BaseExport;
import com.jorado.search.core.solrclient.SolrjClient;
import com.jorado.search.core.solrclient.SolrjClientPool;
import com.jorado.search.core.solrclient.SolrjOption;
import com.jorado.search.core.solrclient.SolrjProxy;
import com.jorado.search.hotword.model.Word;
import com.jorado.logger.EventClient;
import com.jorado.core.Result;
import com.jorado.core.ResultStatus;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.context.annotation.Description;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Description("导出热词数据到solr")
public class ExportHotWordImpl extends BaseExport<Word> {

    /**
     * 目标solr 代理
     */
    private List<SolrjProxy> targetSolrProxy;

    /**
     * 热词数据导出器
     */
    private BaseWordExporter exporter;

    public ExportHotWordImpl(BaseWordExporter exporter) {
        this.exporter = exporter;
        this.targetSolrProxy = new ArrayList<>();
        this.setExporter(exporter).setPollSize(Integer.MAX_VALUE);
    }

    /**
     * 处理前需要干点啥
     */
    @Override
    protected Result before() {

        for (SolrjOption option : assignSolr()) {
            targetSolrProxy.add(new SolrjProxy(option));
        }

        try {

            for (SolrjProxy solr : this.targetSolrProxy) {
                solr.deleteByQuery(String.format("HOT_FLAG:%d", this.exporter.getWordType().getValue()));
                solr.commit();
            }

        } catch (Exception ex) {
            EventClient.getDefault().asyncSubmitException(ErrorConsts.Export.BEFORE_EXPORT, ex);
            return new Result(ResultStatus.ERROR, ex.getMessage());
        }
        return Result.OK;
    }

    @Override
    protected void export(List<Word> dataList) {

        try {

            if (dataList.isEmpty()) {
                return;
            }

            List<SolrInputDocument> docs = new ArrayList<>();
            for (Word word : dataList) {
                SolrInputDocument doc = new SolrInputDocument();
                doc.addField("HOT_ID", word.getId());
                doc.addField("HOT_WORD", word.getKeyWord());
                doc.addField("HOT_PINYIN", word.getPinYin());
                doc.addField("HOT_PY", word.getPy());
                if (null != word.getSimilar()) {
                    for (String w : word.getSimilar()) {
                        doc.addField("HOT_SAME", w);
                    }
                }
                if (null != word.getNext()) {
                    for (String w : word.getNext()) {
                        doc.addField("HOT_NEXT", w);
                    }
                }
                doc.addField("HOT_CLICK", word.getClick());
                doc.addField("HOT_MATCH_ROWS", word.getRows());
                doc.addField("HOT_FLAG", word.getFlag());

                docs.add(doc);
            }

            for (SolrjProxy solr : this.targetSolrProxy) {
                solr.add(docs);
                solr.commit();
            }

        } catch (Exception ex) {
            EventClient.getDefault().asyncSubmitException(ErrorConsts.Export.EXPORT_FAIL, ex);
        }
    }

    /**
     * 处理完需要干点啥
     */
    @Override
    protected Result after(Result result) {
        return Result.OK;
    }

    @Override
    protected Result filter(Word data) {
        return Result.OK;
    }

    private List<SolrjOption> assignSolr() {

        Solr solr = SolrConfig.getInstance().getSolr();

        if (null == solr || CollectionUtils.isEmpty(solr.getHost())) {
            throw new NoAssignSolrException();
        }

        SolrjClient solrjClient = SolrjClientPool.newClient(solr.getHost(), solr.isCloud());

        List<SolrjOption> solrs = new ArrayList<>();

        solrs.add(new SolrjOption(solr.getCollection(), solrjClient));

        return solrs;
    }
}
