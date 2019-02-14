package com.jorado.zkconfig;


public class RemoteCreater extends AbsConfigCreater {

    ZKPManager watcher;

    public RemoteCreater(ZKPManager watcher) {
        this.watcher = watcher;
    }

    @Override
    public ZPConfig get(String path) {
        String content = watcher.readData(path, true);
        ZPConfig config = this.get(path, content);
        return config;
    }

}
