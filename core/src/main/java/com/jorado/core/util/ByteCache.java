package com.jorado.core.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class ByteCache {

    private static final AtomicLong IDS = new AtomicLong();

    private static final ConcurrentMap<String, byte[]> cache = new ConcurrentHashMap<String, byte[]>();

    public static String put(byte[] source) {
        String id = String.valueOf(IDS.incrementAndGet());
        cache.putIfAbsent(id, source);
        return id;
    }

    public static byte[] getAndRemove(String id) {
        return cache.remove(id);
    }

}