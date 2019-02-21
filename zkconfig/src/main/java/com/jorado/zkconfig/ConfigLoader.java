package com.jorado.zkconfig;

import com.jorado.core.logger.Logger;
import com.jorado.core.logger.LoggerFactory;
import com.jorado.core.util.IOUtils;
import com.jorado.core.util.JsonUtils;

public abstract class ConfigLoader {

    private Logger logger = LoggerFactory.getLogger(ConfigLoader.class);

    public abstract ZKPConfig get(String path);

    public synchronized ZKPConfig get(String path, String content) {
        String className = path.substring(path.lastIndexOf("/") + 1);
        ZKPConfig config = null;
        try {
            Class<?> type = Class.forName(className);
            config = (ZKPConfig) JsonUtils.fromJson(content, type);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return config;
    }

    public synchronized void save(String path, ZKPConfig config) {
        String content = JsonUtils.toJson(config);
        String localPath = covertToLocal(path);
        IOUtils.saveFile(localPath, content);
    }

    public synchronized String covertToLocal(String path) {
        String root = this.getClass().getResource("/").getPath();
        String fileDir = "";
        try {
            int start = path.substring(1).indexOf("/");
            int end = path.lastIndexOf("/");
            fileDir = path.substring(start + 1, end);
        } catch (Exception ex) {
            logger.error("covertToLocal error", ex);
        }
        String dir = root + "/conf" + fileDir;

        if (!IOUtils.exists(dir)) {
            IOUtils.createDirectory(dir);
        }

        String fileName = path.substring(path.lastIndexOf("/")) + ".json";
        return dir + fileName;
    }
}
