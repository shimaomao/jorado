package com.jorado.search.hotword.config;

import com.jorado.zookeeper.LoadConfig;

/*
 * Created by len.zhang on 2018/4/17.
 *
 */
public final class RemoteSettings {
    private RemoteSettings() {
    }

    static LoadConfig config = LoadConfig.newInstance("conf");

    public static boolean groupEnabled() {
        String o = config.getProperty("groupEnabled", "1");
        return "1".equals(o);
    }

    public static boolean enabled() {
        String o = config.getProperty("enabled", "1");
        return "1".equals(o);
    }

    public static int showMaxRows() {
        String o = config.getProperty("showMaxRows", "0");
        return Integer.valueOf(o);
    }

    public static boolean outputLog() {
        String o = config.getProperty("outputLog", "0");
        return "1".equals(o);
    }

    public static boolean updateMatchRows() {
        String o = config.getProperty("updateMatchRows", "0");
        return "1".equals(o);
    }

    public static boolean manualUpdateCompanyMatchRows() {
        String o = config.getProperty("manualUpdateCompanyMatchRows", "0");
        return "1".equals(o);
    }

    public static boolean manualUpdatePositionMatchRows() {
        String o = config.getProperty("manualUpdatePositionMatchRows", "0");
        return "1".equals(o);
    }

    public static boolean manualSyncOnlineCompany() {
        String o = config.getProperty("manualSyncOnlineCompany", "0");
        return "1".equals(o);
    }

    public static boolean onlineCompanySyncEnabled() {
        String o = config.getProperty("onlineCompanySyncEnabled", "0");
        return "1".equals(o);
    }

}
