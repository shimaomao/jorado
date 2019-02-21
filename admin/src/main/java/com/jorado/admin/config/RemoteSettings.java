package com.jorado.admin.config;

import com.jorado.zkconfig.AppSettings;

/*
 * Created by len.zhang on 2018/4/17.
 *
 */
public final class RemoteSettings {
    private RemoteSettings() {
    }

    public static String host() {
        String o = AppSettings.getInstance().get("host", "");
        return o;
    }

}
