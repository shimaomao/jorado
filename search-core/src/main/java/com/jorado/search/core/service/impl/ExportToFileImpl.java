package com.jorado.search.core.service.impl;

import com.jorado.search.core.service.BaseExport;
import com.jorado.search.core.service.Exporter;
import com.jorado.logger.util.IOUtils;
import com.jorado.core.Result;
import org.springframework.context.annotation.Description;

/**
 * 导出数据到文件
 */
@Description("导出数据到文件")
public abstract class ExportToFileImpl<T> extends BaseExport<T> {

    /**
     * 目标文件路径
     */
    protected String distFile;

    /**
     * 如果目标文件已经存在是否删除
     */
    private boolean deleteIfExists;

    /**
     * 构造函数
     *
     * @param exporter 数据导出器
     * @param distFile 数据输出目标文件
     */
    protected ExportToFileImpl(Exporter exporter, String distFile) {
        this(exporter, distFile, true);
    }

    /**
     * 构造函数
     *
     * @param exporter       数据导出器
     * @param distFile       数据输出目标文件
     * @param deleteIfExists 目标文件如果存在是否删除
     */
    protected ExportToFileImpl(Exporter exporter, String distFile, boolean deleteIfExists) {
        super(exporter);
        this.distFile = distFile;
        this.deleteIfExists = deleteIfExists;
    }

    @Override
    protected Result before() {
        if (this.deleteIfExists) {
            IOUtils.delete(this.distFile);
        }
        return Result.OK;
    }

    @Override
    protected Result after(Result result) {
        return Result.OK;
    }

    @Override
    protected Result filter(T data) {
        return Result.OK;
    }

    /**
     * 获取目标文件
     *
     * @return
     */
    public String getDistFile() {
        return distFile;
    }
}
