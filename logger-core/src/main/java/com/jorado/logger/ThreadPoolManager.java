package com.jorado.logger;

import com.jorado.logger.concurrent.threadlocal.InternalThreadPoolExecutor;
import com.jorado.logger.util.ThreadUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public final class ThreadPoolManager {

    private ThreadPoolManager() {
    }

    static {
        eventExecutor = new InternalThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), ThreadUtils.threadFactory("EventClient thread pool", false));
    }

    private static ExecutorService eventExecutor;

    public static void addJobToEventExecutor(Runnable command) {

        eventExecutor.execute(command);
    }
}
