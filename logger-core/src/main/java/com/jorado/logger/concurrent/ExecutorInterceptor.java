package com.jorado.logger.concurrent;

public interface ExecutorInterceptor {

    void before(Runnable command);

    void after(Runnable command);

    void error(Runnable command, Throwable throwable);
}
