package com.jorado.logger.concurrent.threadlocal;

import com.jorado.logger.concurrent.ExecutorInterceptor;
import com.jorado.logger.concurrent.NullExecutorInterceptor;

import java.util.concurrent.*;

public class InternalThreadPoolExecutor extends ThreadPoolExecutor {

    private ExecutorInterceptor executorInterceptor;

    public InternalThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        this(new NullExecutorInterceptor(), corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public InternalThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        this(new NullExecutorInterceptor(), corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public InternalThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        this(new NullExecutorInterceptor(), corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public InternalThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        this(new NullExecutorInterceptor(), corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    public InternalThreadPoolExecutor(ExecutorInterceptor executorInterceptor, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.executorInterceptor = executorInterceptor;
    }

    public InternalThreadPoolExecutor(ExecutorInterceptor executorInterceptor, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        this.executorInterceptor = executorInterceptor;
    }

    public InternalThreadPoolExecutor(ExecutorInterceptor executorInterceptor, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        this.executorInterceptor = executorInterceptor;
    }

    public InternalThreadPoolExecutor(ExecutorInterceptor executorInterceptor, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        this.executorInterceptor = executorInterceptor;
    }

    @Override
    public void execute(final Runnable command) {
        Runnable task = () -> {
            try {
                executorInterceptor.before(command);
                command.run();
            } catch (Exception ex) {
                executorInterceptor.error(command, ex);
            } finally {
                executorInterceptor.after(command);
            }
        };
        super.execute(task);
    }
}
