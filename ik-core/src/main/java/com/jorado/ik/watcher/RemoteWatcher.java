package com.jorado.ik.watcher;

import com.jorado.ik.Logger;
import com.jorado.ik.util.HttpUtils;
import com.jorado.ik.util.StringUtils;

import java.io.StringReader;
import java.util.Properties;

public class RemoteWatcher implements Watcher {

    private long lastUpdateTime = Integer.MIN_VALUE;

    @Override
    public Properties changed(String conf) {
        try {
            String v = HttpUtils.get(conf);
            Properties p = new Properties();
            p.load(new StringReader(v));
            String lastupdate = p.getProperty("lastupdate", "0");
            Long t = new Long(lastupdate);

            if (t > this.lastUpdateTime) {
                this.lastUpdateTime = t.longValue();
                String paths = p.getProperty("files");
                return StringUtils.isNullOrWhiteSpace(paths) ? null : p;
            } else {
                this.lastUpdateTime = t.longValue();
                return null;
            }
        } catch (Exception e) {
            Logger.errorFormat("Remote file watcher error,conf:%s,error:%s", conf, e.getMessage());
            return null;
        }
    }
}
