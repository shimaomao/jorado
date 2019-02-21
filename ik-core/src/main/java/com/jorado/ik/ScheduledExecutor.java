package com.jorado.ik;

import com.jorado.ik.util.ThreadUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutor {

    public final static ScheduledExecutorService ScheduledService = Executors.newSingleThreadScheduledExecutor(ThreadUtils.threadFactory("ResourceWatcherThread", true));

    public static void submit(Runnable command, long periodMilliSenconds) {
        ScheduledService.scheduleAtFixedRate(command, 10l, periodMilliSenconds, TimeUnit.MILLISECONDS);
    }
}
