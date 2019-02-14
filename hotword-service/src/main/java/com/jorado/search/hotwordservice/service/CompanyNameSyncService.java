package com.jorado.search.hotwordservice.service;

import com.jorado.search.core.service.BaseExport;
import com.jorado.search.core.service.ExportService;
import com.jorado.search.core.service.impl.SolrExporter;
import com.jorado.search.core.solrclient.SolrjOption;
import com.jorado.search.hotword.service.exportimpl.CompanyWordExporter;
import com.jorado.search.hotword.service.exportimpl.ExportHotWordImpl;
import com.jorado.logger.EventClient;
import com.jorado.logger.util.StringUtils;
import com.jorado.core.Result;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrDocument;
import org.springframework.context.annotation.Description;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author len.zhang
 */
@Description("在线公司名称热词同步")
public final class CompanyNameSyncService extends BaseExport<SolrDocument> {

    private Map<String, Integer> checkRepeatDict = new HashMap<>();

    private List<String> companyList = new ArrayList<>();

    public CompanyNameSyncService(SolrjOption positionSolr) {
        super(new SolrExporter(positionSolr, getSolrQuery()));
    }

    @Override
    protected void export(List<SolrDocument> dataList) {

        for (SolrDocument doc : dataList) {
            String name = doc.get("COMPANY_NAME").toString();
            String[] split_name = StringUtils.splitString(name, "-", false);
            name = split_name[0];
            String shortName = "";
            if (doc.containsKey("COMPANY_NAME_SHORT")) {
                shortName = doc.get("COMPANY_NAME_SHORT").toString();
            }
            String data = String.format("%s,%s,,", name, shortName);
            companyList.add(data);
        }
    }

    @Override
    protected Result before() {
        return Result.OK;
    }

    @Override
    protected Result after(Result result) {
        EventClient.getDefault().submitLog(String.format("待导出在线公司热词个数:[%d]", companyList.size()));
        if (!companyList.isEmpty()) {
            EventClient.getDefault().submitLog("在线公司热词导出开始...");
            CompanyWordExporter wordExporter = new CompanyWordExporter(companyList, false);
            ExportService exportService = new ExportHotWordImpl(wordExporter);
            exportService.dataExport();
            EventClient.getDefault().submitLog("在线公司热词导出完毕");
        }
        return Result.OK;
    }

    @Override
    protected Result filter(SolrDocument data) {
        if (data.isEmpty()) {
            return Result.ERROR;
        }
        String name = data.get("COMPANY_NAME").toString();
        String[] splitName = StringUtils.splitString(name, "-", false);
        name = splitName[0];
        if (data.containsKey("COMPANY_NAME_SHORT")) {
            name += data.get("COMPANY_NAME_SHORT");
        }
        if (checkRepeatDict.containsKey(name)) {
            return Result.ERROR;
        } else {
            checkRepeatDict.put(name, 1);
            return Result.OK;
        }
    }

    private static SolrQuery getSolrQuery() {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("*:*");
        solrQuery.setFields("COMPANY_NAME", "COMPANY_NAME_SHORT");
        solrQuery.setSort("DATE_CREATED", SolrQuery.ORDER.asc);
        return solrQuery;
    }
}
