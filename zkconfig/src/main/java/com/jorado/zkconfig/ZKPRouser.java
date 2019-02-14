package com.jorado.zkconfig;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 14-10-21.
 */
public class ZKPRouser {

    static class RouseTF implements ThreadFactory {

        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "RouseScheduledExecutorThread");
            t.setDaemon(true);
            return t;
        }

    }

    final public static ScheduledExecutorService ScheduledService =
            Executors.newSingleThreadScheduledExecutor(new RouseTF());

    public static void submit(Runnable cmd, long periodMilliSeconds) {
        ScheduledService.scheduleAtFixedRate(cmd, 10l, periodMilliSeconds, TimeUnit.MILLISECONDS);
    }

    private static boolean active = false;

    private static Map<String, Boolean> reloadAware = new HashMap<>();

    public static void reload(String appPath) {
        reloadAware.put(appPath, true);
        if (!active) {
            active = true;
            submit(() -> {
                for (String key : reloadAware.keySet()) {
                    if (reloadAware.get(key)) {
                        ConfigFactory.init(key);
                    }
                }
                reloadAware.clear();
            }, 10 * 1000);
        }
    }


}
