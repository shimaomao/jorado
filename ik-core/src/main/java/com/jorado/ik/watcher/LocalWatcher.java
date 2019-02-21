package com.jorado.ik.watcher;

import com.jorado.ik.Logger;
import com.jorado.ik.util.StringUtils;
import org.apache.lucene.analysis.util.ResourceLoader;

import java.io.InputStream;
import java.util.Properties;

public class LocalWatcher implements Watcher {

    private ResourceLoader loader;
    private long lastUpdateTime = Integer.MIN_VALUE;

    public LocalWatcher(ResourceLoader loader) {
        this.loader = loader;
    }

    @Override
    public Properties changed(String conf) {
        try {
            Properties p = new Properties();
            InputStream confStream = loader.openResource(conf);
            p.load(confStream);
            confStream.close();
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
            Logger.errorFormat(String.format("Local file watcher error,conf:%s,error:%s", conf, e.getMessage()));
            return null;
        }
    }
}
