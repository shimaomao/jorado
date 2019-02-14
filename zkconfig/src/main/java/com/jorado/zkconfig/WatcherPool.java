package com.jorado.zkconfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WatcherPool {
    public enum HandleMethod {
        CONNECT, UPDATE, CHANGE, EXPIRED
    }

    private static Map<HandleMethod, List<IZKPWatcher>> handleMap = new HashMap<>();

    static {
        handleMap.put(HandleMethod.CONNECT, new ArrayList<IZKPWatcher>());
        handleMap.put(HandleMethod.UPDATE, new ArrayList<IZKPWatcher>());
        handleMap.put(HandleMethod.CHANGE, new ArrayList<IZKPWatcher>());
        handleMap.put(HandleMethod.EXPIRED, new ArrayList<IZKPWatcher>());
    }

    public static void add(HandleMethod handle, IZKPWatcher handler) {
        List<IZKPWatcher> list = handleMap.get(handle);
        list.add(handler);
        handleMap.put(handle, list);
    }

    public synchronized static List<IZKPWatcher> getList(HandleMethod handle) {
        return handleMap.get(handle);
    }
}
