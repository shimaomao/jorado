package com.jorado.dubbo;

import com.jorado.logger.EventClient;
import com.jorado.logger.util.Stopwatch;
import org.junit.Test;

public class DefaultTest {

    @Test
    public void test() throws Exception {
        EventClient.getDefault().submitLog("Startup begin.....");
        // doLog();
        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(() -> doLog());
            thread.start();
        }
        Thread.sleep(50000);
    }

    public void doLog() {
        Stopwatch stopwatch = Stopwatch.begin();
        for (int i = 0; i < 5; i++) {
            EventClient.getDefault().submitLog("test");
        }
        stopwatch.stop();
        System.err.println(stopwatch.getDuration());
    }
}