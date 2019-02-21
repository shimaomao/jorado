package com.jorado.zkconfig;


public class RemoteLoader extends ConfigLoader {

    ZKPManager watcher;

    public RemoteLoader(ZKPManager watcher) {
        this.watcher = watcher;
    }

    @Override
    public ZKPConfig get(String path) {
        String content = watcher.readData(path, true);
        ZKPConfig config = this.get(path, content);
        return config;
    }

}
