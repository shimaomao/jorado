package com.jorado.search.core.service;

import com.jorado.search.core.config.AppSettings;
import com.jorado.search.core.consts.ErrorConsts;
import com.jorado.search.core.exception.ActionAfterException;
import com.jorado.search.core.exception.ActionBeforeException;
import com.jorado.search.core.exception.NoDataFoundException;
import com.jorado.search.core.util.MetaUtils;
import com.jorado.logger.EventBuilder;
import com.jorado.logger.EventClient;
import com.jorado.core.Result;
import com.jorado.core.ResultStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据导出操作（数据导入只是数据导出的反操作，也可以通过实现此接口来完成操作）
 */
public abstract class BaseExport<T> implements ExportService {

    /**
     * 最大拉取记录数
     */
    private int pollSize = 5000;

    /**
     * 一次输出最大数量
     */
    private int flushSize = 200;

    /**
     * 记录总数
     */
    private long numFound;

    /**
     * 游标起始位置
     */
    private int start;

    /**
     * 游标结束位置
     */
    private int end;

    /**
     * 数据导出器
     */
    private Exporter<T> exporter;

    /**
     * 无参数构造函数
     */
    protected BaseExport() {
        this(0, Integer.MAX_VALUE);
    }

    /**
     * 带起始位置的构造函数
     */
    protected BaseExport(int start, int end) {
        this.start = start;
        this.end = end;
    }

    /**
     * 构造函数
     *
     * @param exporter 数据导出器
     */
    protected BaseExport(Exporter<T> exporter) {
        this(exporter, 0, Integer.MAX_VALUE);
    }

    /**
     * 构造函数
     *
     * @param exporter 数据导出器
     */
    protected BaseExport(Exporter<T> exporter, int start) {
        this(exporter, start, Integer.MAX_VALUE);
    }

    /**
     * 构造函数
     *
     * @param exporter 数据导出器
     */
    protected BaseExport(Exporter<T> exporter, int start, int end) {
        this.exporter = exporter;
        this.start = start;
        this.end = end;
    }

    /**
     * 数据操作模板方法
     *
     * @return
     */
    @Override
    public Result dataExport() {

        EventBuilder eventBuilder = EventClient.getDefault().createEvent();

        eventBuilder.addTags(MetaUtils.getDescription(this.getClass()));

        Result result = Result.OK;

        try {

            if (null == this.exporter) {
                throw new IllegalArgumentException("Exporter is null");
            }

            eventBuilder.addData("exporter", MetaUtils.getDescription(exporter.getClass()));

            if (this.start < 0) {
                this.start = 0;
            }

            this.numFound = this.exporter.count();

            if (this.numFound < 1) {
                throw new NoDataFoundException();
            }

            Result beforeResult = before();
            if (beforeResult.isFail()) {
                throw new ActionBeforeException(beforeResult.getMessage());
            }

            do {

                List<T> datas = initData();

                List<T> batchList = new ArrayList<>();
                int i = 0;
                for (T data : datas) {
                    i++;
                    Result filterResult = filter(data);
                    if (filterResult.isOk()) {
                        batchList.add(data);
                    }

                    if (flushSize > batchList.size() && i < datas.size()) {
                        continue;
                    }

                    try {
                        export(batchList);
                    } catch (Exception ex) {
                        EventClient.getDefault().asyncSubmitException(ErrorConsts.Export.EXPORT_FAIL, ex);
                    }
                    batchList.clear();
                }

                EventClient.getDefault().asyncSubmitLog(String.format("第[%d]条导出完成...", start));
            }
            while (start < numFound);

            result.setMessage(String.format("导出完毕，共导出记录：[%s] 条", numFound));

            Result afterResult = after(result);
            if (afterResult.isFail()) {
                throw new ActionAfterException(beforeResult.getMessage());
            }

        } catch (ActionBeforeException ex) {

            eventBuilder.setException(ex);
            result = new Result<>(ResultStatus.ERROR, ErrorConsts.Export.BEFORE_EXPORT);

        } catch (ActionAfterException ex) {

            eventBuilder.setException(ex);
            result = new Result<>(ResultStatus.ERROR, ErrorConsts.Export.AFTER_EXPORT);

        } catch (Exception ex) {

            eventBuilder.setException(ex);
            result = new Result<>(ResultStatus.ERROR, String.format("%s,%s", ErrorConsts.Export.EXPORT_FAIL, ex.getMessage()));

        } finally {
            //打印日志
            if (eventBuilder.getTarget().isError() || AppSettings.getInstance().outputLog()) {
                eventBuilder.setMessage(result.getMessage()).addData("numFound", numFound).asyncSubmit();
            }
        }
        return result;
    }

    /**
     * 处理前需要干点啥
     */
    protected abstract Result before();

    /**
     * 具体操作
     *
     * @param dataList
     */
    protected abstract void export(List<T> dataList);

    /**
     * 过滤
     *
     * @param data
     * @return
     */
    protected abstract Result filter(T data);

    /**
     * 处理完需要干点啥
     */
    protected abstract Result after(Result result);

    /**
     * 查询数据源
     *
     * @return
     * @throws IOException
     */
    private List<T> initData() {

        int rows = pollSize;

        try {

            if (start + pollSize > this.end) {
                rows = end - start;
            }

            return this.exporter.listDatas(start, rows);

        } catch (Exception ex) {
            EventClient.getDefault().asyncSubmitException("Init data error", ex);
        } finally {
            start += rows;
            if (start > numFound) {
                start = (int) numFound;
            }
        }
        return new ArrayList<>();
    }

    /**
     * 设置开始读取的位置
     *
     * @param start
     */
    public BaseExport setStart(int start) {
        this.start = start;
        return this;
    }

    /**
     * 设置结束读取的位置
     *
     * @param end
     */
    public BaseExport setEnd(int end) {
        this.end = end;
        return this;
    }

    /**
     * 设置最大拉取记录数
     *
     * @param pollSize
     * @return
     */
    public BaseExport setPollSize(int pollSize) {
        this.pollSize = pollSize;
        return this;
    }

    /**
     * 设置一次输出最大数量
     *
     * @param flushSize
     * @return
     */
    public BaseExport setFlushSize(int flushSize) {
        this.flushSize = flushSize;
        return this;
    }

    /**
     * 设置数据导出器
     *
     * @param exporter
     * @return
     */
    public BaseExport setExporter(Exporter<T> exporter) {
        this.exporter = exporter;
        return this;
    }

    /**
     * 获取记录总数
     *
     * @return
     */
    public long getNumFound() {
        return numFound;
    }
}
