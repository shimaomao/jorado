package com.jorado.zkconfig;

import com.jorado.zkconfig.util.FileUtils;
import com.jorado.core.util.StringUtils;

public class FileCreater extends AbsConfigCreater {

    @Override
    public ZPConfig get(String path) {
        String localPath = covertToLocal(path);
        if (!FileUtils.exists(localPath)) {
            return null;
        }
        String content = FileUtils.read(localPath);
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        ZPConfig config = this.get(path, content);
        return config;
    }

}
