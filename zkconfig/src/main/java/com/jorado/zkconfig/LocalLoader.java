package com.jorado.zkconfig;

import com.jorado.core.util.IOUtils;
import com.jorado.core.util.StringUtils;

public class LocalLoader extends ConfigLoader {

    @Override
    public ZKPConfig get(String path) {
        String localPath = covertToLocal(path);
        if (!IOUtils.exists(localPath)) {
            return null;
        }
        String content = IOUtils.read(localPath);
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        ZKPConfig config = this.get(path, content);
        return config;
    }

}
