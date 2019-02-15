package com.jorado.zkconfig;

import com.jorado.core.logger.Logger;
import com.jorado.core.logger.LoggerFactory;
import com.jorado.core.util.IOUtils;
import com.jorado.core.util.JsonUtils;

public abstract class AbsConfigCreater {

    private Logger logger = LoggerFactory.getLogger(AbsConfigCreater.class);

    public abstract ZPConfig get(String path);

    public synchronized ZPConfig get(String path, String content) {
        String className = path.substring(path.lastIndexOf("/") + 1);
        ZPConfig config = null;
        try {
            Class<?> type = Class.forName(className);
            config = (ZPConfig) JsonUtils.fromJson(content, type);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return config;
    }

    public synchronized void save(String path, ZPConfig config) {
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
