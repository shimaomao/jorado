package com.jorado.search.hotword;

import com.jorado.logger.EventClient;
import com.jorado.logger.util.Stopwatch;
import org.junit.Test;

public class DefaultTest {

    @Test
    public void test1() {

        tryTest();
    }

    private int tryTest() {
        Stopwatch stopwatch = Stopwatch.begin();
        try {

            return 1;

        } catch (Exception ex) {
            throw ex;


        } finally {
            stopwatch.stop();
            EventClient.getDefault().submitLog(String.format("[%s]结束,总耗时:[%s]", this.getClass(), stopwatch.toString()));
        }
    }
}
