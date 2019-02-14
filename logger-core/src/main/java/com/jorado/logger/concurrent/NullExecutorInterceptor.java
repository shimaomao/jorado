package com.jorado.logger.concurrent;

public class NullExecutorInterceptor implements ExecutorInterceptor {

    @Override
    public void before(Runnable command) {

    }

    @Override
    public void after(Runnable command) {

    }

    @Override
    public void error(Runnable command, Throwable throwable) {

    }
}
