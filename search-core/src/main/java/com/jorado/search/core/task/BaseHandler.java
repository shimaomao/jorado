package com.jorado.search.core.task;

import com.jorado.search.core.util.MetaUtils;
import com.jorado.logger.EventClient;
import com.jorado.logger.util.Stopwatch;
import com.jorado.core.Result;
import com.jorado.core.ResultStatus;

/**
 * 任务执行模板类
 */
public abstract class BaseHandler implements Handler {

    /**
     * 接口实现模板方法
     *
     * @param args
     * @return
     */
    @Override
    public Result handle(Object... args) {

        String taskName = MetaUtils.getDescription(this.getClass());

        String traceId = EventClient.getDefault().submitLog(String.format("[%s]启动", taskName));

        Stopwatch stopwatch = Stopwatch.begin();

        try {

            exec(args);

            return Result.OK;

        } catch (Exception ex) {
            EventClient.getDefault().createException(String.format("[%s]异常", taskName), ex).setReferenceId(traceId).submit();
            return new Result(ResultStatus.ERROR, ex.getMessage());

        } finally {
            stopwatch.stop();
            EventClient.getDefault().createLog(String.format("[%s]结束,总耗时:[%s]", taskName, stopwatch.toString())).setReferenceId(traceId).submit();
        }
    }

    /**
     * 具体执行操作
     *
     * @param args
     */
    protected abstract void exec(Object... args);
}
