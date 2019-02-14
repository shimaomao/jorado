package com.jorado.logger;

import com.jorado.logger.util.Stopwatch;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EventClientTest {

    @Test
    public void baseTest() {
        EventClient.getDefault().submitLog("");
    }

    @Test
    public void main() throws Exception {
        EventClient.getDefault().openConsole();

        Stopwatch stopwatch = Stopwatch.begin();

        Map<Integer, String> map = new HashMap();
        for (int i = 0; i < 100; i++) {
            map.put(new Random().nextInt(80285575), "11&&VN0");
        }
        for (int i = 0; i < 10; i++) {
            EventClient.getDefault().createException(new Exception("错误"), false).setMessage("出错了")
                    .addData("test", 1)
                    .addData("b", 2)
                    .addData("b", 3)
                    .addData("c", null)
                    .addTags("solr", "db", "api")
                    .addData("consistentHash.virtualNodes", map)
                    .asyncSubmit();

            Thread.sleep(1000 * 1);
        }
        stopwatch.stop();
        System.out.println(EventClient.getDefault().getTotal());
        System.out.println(EventClient.getDefault().getLastReferenceId());
        EventClient.getDefault().closeConsole();
        EventClient.getDefault().submitLog(stopwatch.toString());
    }

    @Test
    public void test() {
        EventClient.getDefault().openConsole();
        EventBuilder eventBuilder = EventClient.getDefault().createLog("测试");
        try {
            Thread.sleep(1 * 1000);
        } catch (Exception ex) {

        } finally {
            eventBuilder.submit();
        }
        EventClient.getDefault().closeConsole();
    }


    @Test
    public void testCache() {

        EventClient.getDefault().openConsole();

        for (int i = 0; i < 1; i++) {

            Stopwatch stopwatch = Stopwatch.begin();
            EventBuilder eventBuilder = EventClient.getDefault().createLog("测试");

            eventBuilder.submit();
            eventBuilder.submit();
            stopwatch.stop();
            System.out.println(stopwatch.getDuration());
        }


        System.out.println(EventClient.getDefault().getTotal());
        System.out.println(EventClient.getDefault().getLastReferenceId());
        EventClient.getDefault().closeConsole();
    }

    @Test
    public void testTimeout() throws Exception {

        EventClient.getDefault().openConsole();

        EventBuilder eventBuilder = EventClient.getDefault().createLog("测试");

        Thread.sleep(1000);

        eventBuilder.submit();

        eventBuilder = EventClient.getDefault().createLog("测试");

        Thread.sleep(1000);
        eventBuilder.submit(2000);
        eventBuilder.setTimeout(500).submit();
        EventClient.getDefault().closeConsole();
    }
}