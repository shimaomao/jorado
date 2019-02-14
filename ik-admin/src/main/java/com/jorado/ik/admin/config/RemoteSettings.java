package com.jorado.ik.admin.config;

import com.jorado.zookeeper.LoadConfig;

/*
 * Created by len.zhang on 2018/4/17.
 *
 */
public final class RemoteSettings {
    private RemoteSettings() {
    }

    static LoadConfig config = LoadConfig.newInstance("conf");

    public static boolean outputLog() {
        String o = config.getProperty("outputLog", "0");
        return "1".equals(o);
    }

    public static String host() {
        String o = config.getProperty("host", "");
        return o;
    }

}
