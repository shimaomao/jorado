package com.jorado.zkconfig;

import com.jorado.core.util.IOUtils;
import com.jorado.core.util.StringUtils;

public class FileCreater extends AbsConfigCreater {

    @Override
    public ZPConfig get(String path) {
        String localPath = covertToLocal(path);
        if (!IOUtils.exists(localPath)) {
            return null;
        }
        String content = IOUtils.read(localPath);
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        ZPConfig config = this.get(path, content);
        return config;
    }

}
