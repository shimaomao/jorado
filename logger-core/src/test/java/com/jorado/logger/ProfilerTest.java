package com.jorado.logger;

import com.jorado.logger.profiler.Profiler;
import com.jorado.logger.profiler.Stepping;
import org.junit.Test;

public class ProfilerTest {

    @Test
    public void test() throws Exception {

        Profiler profiler = Profiler.current();

        Stepping stepping1 = profiler.step("s-1");
        Thread.sleep(1000);

        Stepping stepping11 = profiler.step("s-1-1");
        Thread.sleep(2500);

        Stepping stepping111 = profiler.step("s-1-1-1");
        Thread.sleep(2500);
        stepping111.stop();

        Stepping stepping112 = profiler.step("s-1-1-2");
        Thread.sleep(2500);
        stepping112.stop();

        stepping11.stop();

        Stepping stepping12 = profiler.step("s-1-2");
        Thread.sleep(1000);
        stepping12.stop();

        stepping1.stop();
        profiler.stop();

        System.out.println(profiler.toString());
    }
}
