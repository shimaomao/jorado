package com.jorado.zkconfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WatcherPool {

    public enum HandleMethod {
        CONNECT, UPDATE, CHANGE, EXPIRED
    }

    private static Map<HandleMethod, List<ZKPWatcher>> handleMap = new HashMap<>();

    static {
        handleMap.put(HandleMethod.CONNECT, new ArrayList<>());
        handleMap.put(HandleMethod.UPDATE, new ArrayList<>());
        handleMap.put(HandleMethod.CHANGE, new ArrayList<>());
        handleMap.put(HandleMethod.EXPIRED, new ArrayList<>());
    }

    public static void add(HandleMethod handle, ZKPWatcher handler) {
        List<ZKPWatcher> list = handleMap.get(handle);
        list.add(handler);
        handleMap.put(handle, list);
    }

    public synchronized static List<ZKPWatcher> getList(HandleMethod handle) {
        return handleMap.get(handle);
    }
}
