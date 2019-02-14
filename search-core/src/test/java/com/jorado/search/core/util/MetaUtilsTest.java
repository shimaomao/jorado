package com.jorado.search.core.util;


import com.jorado.logger.util.Stopwatch;
import org.junit.Test;
import org.springframework.context.annotation.Description;

@Description("hello")
public class MetaUtilsTest {

    @Test
    public void getDescriptionTest() {

        Stopwatch stopwatch = Stopwatch.begin();

        for (int i = 0; i < 10000; i++) {
            String description = MetaUtils.getDescription(this.getClass());
        }
        stopwatch.stop();
        System.out.println(stopwatch.getDuration());
    }
}