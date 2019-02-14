package com.jorado.search.core.service.impl;

import com.jorado.search.core.consts.ErrorConsts;
import com.jorado.search.core.service.BaseExport;
import com.jorado.search.core.solrclient.SolrjOption;
import com.jorado.search.core.solrclient.SolrjProxy;
import com.jorado.logger.EventClient;
import com.jorado.core.Result;
import com.jorado.core.ResultStatus;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.context.annotation.Description;

import java.util.ArrayList;
import java.util.List;

/**
 * 导出solr数据到另外一个solr
 */
@Description("导出solr数据到另外一个solr")
public class ExportToSolrImpl extends BaseExport<SolrDocument> {

    /**
     * 目标solr
     */
    private List<SolrjOption> targetSolr;

    /**
     * 目标solr 代理
     */
    private List<SolrjProxy> targetSolrProxy;

    /**
     * 复制完成后是否自动优化
     */
    private boolean autoOptimize;

    /**
     * 复制前是否删除目标数据
     */
    private boolean clearTarget;

    /**
     * 数据导出器
     */
    private SolrExporter exporter;

    /**
     * 构造函数
     *
     * @param sourceSolr 来源solr
     * @param targetSolr 目标solr
     */
    public ExportToSolrImpl(SolrjOption sourceSolr, List<SolrjOption> targetSolr, SolrQuery solrQuery) {
        this(sourceSolr, targetSolr, solrQuery, true, false);
    }

    /**
     * 构造函数
     *
     * @param sourceSolr 来源solr
     * @param targetSolr 目标solr
     */
    public ExportToSolrImpl(SolrjOption sourceSolr, List<SolrjOption> targetSolr, SolrQuery solrQuery, boolean clearTarget) {
        this(sourceSolr, targetSolr, solrQuery, clearTarget, false);
    }

    /**
     * 构造函数
     *
     * @param sourceSolr   来源solr
     * @param targetSolr   目标solr
     * @param clearTarget  是否清空目标solr数据
     * @param autoOptimize 操作完成是否自动优化目标solr
     */
    public ExportToSolrImpl(SolrjOption sourceSolr, List<SolrjOption> targetSolr, SolrQuery solrQuery, boolean clearTarget, boolean autoOptimize) {
        this.exporter = new SolrExporter(sourceSolr, solrQuery);
        this.clearTarget = clearTarget;
        this.autoOptimize = autoOptimize;
        this.targetSolr = targetSolr;
        this.setExporter(exporter);
    }

    /**
     * 处理前需要干点啥
     */
    @Override
    protected Result before() {

        this.targetSolrProxy = new ArrayList<>();

        for (SolrjOption option : targetSolr) {
            targetSolrProxy.add(new SolrjProxy(option));
        }

        try {

            if (this.clearTarget) {
                for (SolrjProxy solr : this.targetSolrProxy) {
                    solr.deleteByQuery(this.exporter.getSolrQuery().getQuery());
                    solr.commit();
                }
            }
        } catch (Exception ex) {
            EventClient.getDefault().asyncSubmitException(ErrorConsts.Export.BEFORE_EXPORT, ex);
            return new Result(ResultStatus.ERROR, ex.getMessage());
        }
        return Result.OK;
    }

    @Override
    protected void export(List<SolrDocument> dataList) {

        try {
            List<SolrInputDocument> docs = new ArrayList<>();
            for (SolrDocument t : dataList) {
                SolrInputDocument doc = new SolrInputDocument();
                for (String f : t.getFieldNames()) {
                    if (!"_version_".equals(f) && !"score".equals(f)) {
                        doc.addField(f, t.getFieldValue(f));
                    }
                }
                docs.add(doc);
            }
            if (!docs.isEmpty()) {
                for (SolrjProxy solr : this.targetSolrProxy) {
                    solr.add(docs);
                    solr.commit();
                }
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
        try {
            if (this.autoOptimize) {
                for (SolrjProxy solr : this.targetSolrProxy) {
                    solr.optimize();
                }
            }
        } catch (Exception ex) {
            EventClient.getDefault().asyncSubmitException(ErrorConsts.Export.AFTER_EXPORT, ex);
            return new Result(ResultStatus.ERROR, ex.getMessage());
        }
        return Result.OK;
    }

    @Override
    protected Result filter(SolrDocument data) {
        return Result.OK;
    }
}
