package com.jorado.search.core.service.impl;

import com.jorado.search.core.solrclient.SolrjOption;
import com.jorado.logger.EventClient;
import com.jorado.logger.util.IOUtils;
import com.jorado.logger.util.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrDocument;
import org.springframework.context.annotation.Description;

import java.util.ArrayList;
import java.util.List;

/**
 * 导出solr数据到csv文件
 */
@Description("导出solr数据到csv文件")
public class ExportToCsvImpl extends ExportToFileImpl<SolrDocument> {

    /**
     * 字段分隔符
     */
    private static final String SEPARATE = ",";

    /**
     * 是否已经输出了标题
     */
    private boolean titled;

    /**
     * 构造函数
     *
     * @param source   数据来源solr
     * @param distFile 数据输出目标文件
     */
    protected ExportToCsvImpl(SolrjOption source, String distFile) {
        this(source, null, distFile, true);
    }

    /**
     * 构造函数
     *
     * @param source         数据来源solr
     * @param distFile       数据输出目标文件
     * @param deleteIfExists 目标文件如果存在是否删除
     */
    protected ExportToCsvImpl(SolrjOption source, String distFile, boolean deleteIfExists) {
        this(source, null, distFile, deleteIfExists);
    }

    /**
     * 构造函数
     *
     * @param source   数据来源solr
     * @param sq       查询条件
     * @param distFile 数据输出目标文件
     */
    protected ExportToCsvImpl(SolrjOption source, SolrQuery sq, String distFile) {
        this(source, sq, distFile, true);
    }

    /**
     * 构造函数
     *
     * @param source         数据来源solr
     * @param sq             查询条件
     * @param distFile       数据输出目标文件
     * @param deleteIfExists 目标文件如果存在是否删除
     */
    protected ExportToCsvImpl(SolrjOption source, SolrQuery sq, String distFile, boolean deleteIfExists) {
        super(new SolrExporter(source, sq), distFile, deleteIfExists);
    }

    @Override
    protected void export(List<SolrDocument> dataList) {
        List<String> lines = new ArrayList<>();
        if (!titled) {
            for (SolrDocument doc : dataList) {
                if (titled) {
                    break;
                }
                List<String> fields = new ArrayList<>();
                for (String f : doc.getFieldNames()) {
                    if (!"_version_".equals(f) && !"score".equals(f)) {
                        fields.add(f);
                    }
                }
                lines.add(StringUtils.joinString(fields, "", "", SEPARATE));
                try {
                    //写入title
                    IOUtils.appendLines(this.distFile, lines);
                } catch (Exception ex) {
                    EventClient.getDefault().asyncSubmitException("Export data to csv file error", ex);
                } finally {
                    titled = true;
                }
            }
        }

        lines = new ArrayList<>();
        for (SolrDocument doc : dataList) {
            List<String> fields = new ArrayList<>();
            for (String f : doc.getFieldNames()) {
                if (!"_version_".equals(f) && !"score".equals(f)) {
                    fields.add(doc.getFieldValue(f).toString());
                }
            }
            lines.add(StringUtils.joinString(fields, "", "", SEPARATE));
        }
        try {
            //写入文本
            IOUtils.appendLines(this.distFile, lines);
        } catch (Exception ex) {
            EventClient.getDefault().asyncSubmitException("Export data to csv file error", ex);
        }
    }
}
