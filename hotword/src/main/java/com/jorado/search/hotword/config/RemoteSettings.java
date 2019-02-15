package com.jorado.search.hotword.config;

import com.jorado.zkconfig.AppSettings;

/*
 * Created by len.zhang on 2018/4/17.
 *
 */
public final class RemoteSettings {
    private RemoteSettings() {
    }

    public static boolean groupEnabled() {
        String o = AppSettings.getInstance().get("groupEnabled", "1");
        return "1".equals(o);
    }

    public static boolean enabled() {
        String o =  AppSettings.getInstance().get("enabled", "1");
        return "1".equals(o);
    }

    public static int showMaxRows() {
        String o =  AppSettings.getInstance().get("showMaxRows", "0");
        return Integer.valueOf(o);
    }

    public static boolean outputLog() {
        String o =  AppSettings.getInstance().get("outputLog", "0");
        return "1".equals(o);
    }

    public static boolean updateMatchRows() {
        String o =  AppSettings.getInstance().get("updateMatchRows", "0");
        return "1".equals(o);
    }

    public static boolean manualUpdateCompanyMatchRows() {
        String o =  AppSettings.getInstance().get("manualUpdateCompanyMatchRows", "0");
        return "1".equals(o);
    }

    public static boolean manualUpdatePositionMatchRows() {
        String o =  AppSettings.getInstance().get("manualUpdatePositionMatchRows", "0");
        return "1".equals(o);
    }

    public static boolean manualSyncOnlineCompany() {
        String o =  AppSettings.getInstance().get("manualSyncOnlineCompany", "0");
        return "1".equals(o);
    }

    public static boolean onlineCompanySyncEnabled() {
        String o =  AppSettings.getInstance().get("onlineCompanySyncEnabled", "0");
        return "1".equals(o);
    }

}
