package com.jorado.logger;

import com.jorado.logger.data.collector.Collector;
import com.jorado.logger.data.collector.EnvironmentInfoCollector;
import com.jorado.logger.util.Stopwatch;
import org.junit.Test;

public class PerformanceTest {

    @Test
    public void environmentInfo() {

        Stopwatch stop = Stopwatch.begin();

        for (int i = 0; i < 50000; i++) {
            Collector collector = new EnvironmentInfoCollector();
            collector.getData();
        }
        stop.stop();

        System.out.println(stop.getDuration());
    }

    @Test
    public void environmentInfo1() {

        final String CACHE_KEY = "ENVIRONMENT_INFO";

        final int EXPIRE_TIME = 10;

        Stopwatch stop = Stopwatch.begin();

        for (int i = 0; i < 50000; i++) {
            Object info = CacheManager.getDefault().get(CACHE_KEY);

            if (null == info) {

                Collector collector = new EnvironmentInfoCollector();

                if (null == collector)
                    return;

                info = collector.getData();

                CacheManager.getDefault().set(CACHE_KEY, info, EXPIRE_TIME);
            }


        }
        stop.stop();

        System.out.println(stop.getDuration());
    }

    @Test
    public void environmentInfo2() throws Exception {

        ThreadPoolManager.addJobToEventExecutor(() -> {
            for (int i = 0; i < 10000; i++) {
                environmentInfo1();
                System.out.println("1111111111111111111111111111");
            }
        });

        ThreadPoolManager.addJobToEventExecutor(() -> {
            for (int i = 0; i < 10000; i++) {
                environmentInfo1();
                System.out.println("2222222222222222222222222222");
            }

        });

        Thread.sleep(10 * 1000);
    }
}
