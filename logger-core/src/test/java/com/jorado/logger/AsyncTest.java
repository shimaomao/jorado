package com.jorado.logger;

import com.jorado.logger.util.Stopwatch;
import org.junit.Test;

public class AsyncTest {

    @Test
    public void test() throws Exception {

        doLog();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> doLog());
            thread.start();
        }
        Thread.sleep(1000);
    }

    public void doLog() {
        Stopwatch stopwatch = Stopwatch.begin();
        for (int i = 0; i < 5; i++) {
            EventClient.getDefault().asyncSubmitLog("test");
        }
        stopwatch.stop();
        System.err.println(stopwatch.getDuration());
    }
}
