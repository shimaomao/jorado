package com.jorado.core.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class StringCache {

    private static final AtomicLong IDS = new AtomicLong();

    private static final ConcurrentMap<String, String> cache = new ConcurrentHashMap<String, String>();

    public static String put(String source) {
        String id = String.valueOf(IDS.incrementAndGet());
        cache.putIfAbsent(id, source);
        return id;
    }

    public static String getAndRemove(String id) {
        return cache.remove(id);
    }

}