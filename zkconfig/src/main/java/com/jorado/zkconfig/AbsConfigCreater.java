package com.jorado.zkconfig;

import com.jorado.core.logger.Logger;
import com.jorado.core.logger.LoggerFactory;
import com.jorado.zkconfig.util.FileUtils;
import com.jorado.core.util.JsonUtils;

public abstract class AbsConfigCreater {

    private Logger logger= LoggerFactory.getLogger(AbsConfigCreater.class);

    public abstract ZPConfig get(String path);

    public synchronized ZPConfig get(String path, String content) {
        String className = path.substring(path.lastIndexOf("/") + 1);
        ZPConfig config = null;
        try {
            Class<?> type = Class.forName(className);
            config = (ZPConfig) JsonUtils.fromGson(content, type);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return config;
    }

    public synchronized void save(String path, ZPConfig config) {
        Class<? extends ZPConfig> type = config.getClass();
        String content = JsonUtils.toGson(config, type);
        String localPath = covertToLocal(path);
        FileUtils.save(localPath, content);
    }

    public synchronized String covertToLocal(String path) {
        String root = this.getClass().getResource("/").getPath();
        String fileDir = "";
        try {
            int start = path.substring(1).indexOf("/");
            int end = path.lastIndexOf("/");
            fileDir = path.substring(start + 1, end);
        } catch (Exception ex) {
            logger.error("covertToLocal error",ex);
        }
        String dir = root + "/conf" + fileDir;

        if (!FileUtils.exists(dir)) {
            FileUtils.createDir(dir);
        }

        String fileName = path.substring(path.lastIndexOf("/")) + ".json";
        return dir + fileName;
    }
}
