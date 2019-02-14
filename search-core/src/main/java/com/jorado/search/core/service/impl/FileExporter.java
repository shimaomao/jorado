package com.jorado.search.core.service.impl;

import com.jorado.search.core.service.Exporter;
import com.jorado.logger.util.IOUtils;

import java.util.List;

/**
 * 文本导出器
 */
public class FileExporter implements Exporter<String> {

    private String sourceFile;
    private List<String> data;
    private boolean removeDuplicate;

    public FileExporter(String sourceFile) {
        this(sourceFile, false);
    }

    public FileExporter(String sourceFile, boolean removeDuplicate) {
        this.sourceFile = sourceFile;
        this.removeDuplicate = removeDuplicate;
    }

    @Override
    public long count() {
        if (null == data)
            this.initData();
//        try {
//            IOUtils.writeLines(sourceFile + "_0", this.data);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        return data.size();
    }

    @Override
    public List<String> listDatas(int start, int rows) {
        if (null == data)
            this.initData();
        return data;
    }

    private void initData() {
        data = IOUtils.readLines(this.sourceFile, removeDuplicate);
    }
}
