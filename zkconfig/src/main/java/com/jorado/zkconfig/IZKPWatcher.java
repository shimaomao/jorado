package com.jorado.zkconfig;


public interface IZKPWatcher {
    void watch(String path, ZKPManager watcher);
}
