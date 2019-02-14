package com.jorado.templateengine;

import com.jorado.logger.util.IOUtils;
import com.jorado.logger.util.Stopwatch;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultTest {

    private Map<String, Object> dataContext;
    private int runCount = 20;

    @Before
    public void init() {

        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            userList.add(new User("张江松", 2));
        }
        dataContext = new HashMap<>();
        dataContext.put("userList", userList);
    }

    @Test
    public void test() {
        for (int i = 0; i < runCount; i++) {
            test1();
            System.out.println();
        }
    }

    @Test
    public void test1() {

        EngineProxy engineProxy = new EngineProxy();
        String templateBody = IOUtils.readResource("velocity.txt");
        engineProxy.parse(templateBody, dataContext);
        long totalTime = 0;
        for (int i = 0; i < runCount; i++) {
            Stopwatch stopwatch = Stopwatch.begin();
            engineProxy.parse(templateBody + i, dataContext);
            stopwatch.stop();
            totalTime += stopwatch.getDuration();
            //System.out.println(stopwatch.toString());
        }
        System.out.print(String.format(" Velocity平均耗时:%d", totalTime / runCount));
    }

    public class User {

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

}

