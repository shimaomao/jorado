package com.jorado.zkconfig;


public interface ZKPWatcher {
    void watch(String path, ZKPManager watcher);
}
