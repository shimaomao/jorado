package com.jorado.logger;

import org.junit.Test;

public class CacheTest {

    @Test
    public void cache() throws Exception {

        Object o = CacheManager.getDefault().get("test");
        System.out.println(o);

        CacheManager.getDefault().set("test", 1, 10);
        o = CacheManager.getDefault().get("test");
        System.out.println(o);
        Thread.sleep(9 * 1000);

        o = CacheManager.getDefault().get("test");
        System.out.println(o);

    }


    @Test
    public void cache1() throws Exception {

        Object o = CacheManager.getDefault().get("g", "test");
        System.out.println(o);

        CacheManager.getDefault().set("g", "test", 1, 10);
        o = CacheManager.getDefault().get("g", "test");
        System.out.println(o);
    }
}
