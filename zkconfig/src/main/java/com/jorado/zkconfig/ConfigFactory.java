package com.jorado.zkconfig;

import com.jorado.core.logger.Logger;
import com.jorado.core.logger.LoggerFactory;

import java.util.HashMap;
import java.util.List;

public class ConfigFactory {

    private static final Logger logger = LoggerFactory.getLogger(ConfigFactory.class);

    private static ZKPManager watcher = new ZKPManager();
    private static HashMap<String, ZKPConfig> configMap = new HashMap<>();
    private static HashMap<String, ZKPConfig> appConfigs = new HashMap<>();
    private static ConfigLoader remoteLoader = new RemoteLoader(watcher);
    private static ConfigLoader localLoader = new LocalLoader();

    static {
        init(ZKPSettings.ZOOKEEPER_PATH);
    }

    public static boolean contains(String path) {
        return configMap.containsKey(path);
    }

    public static ZKPManager getWatcher() {
        return watcher;
    }

    public static void init(String appPath) {
        watcher.connectionHandler = (path, watcher) -> {
            List<ZKPWatcher> handlers = WatcherPool.getList(WatcherPool.HandleMethod.CONNECT);
            for (ZKPWatcher handler : handlers) {
                handler.watch(path, watcher);
            }
        };
        watcher.updateHandler = (path, watcher) -> {
            try {
                List<ZKPWatcher> handlers = WatcherPool.getList(WatcherPool.HandleMethod.UPDATE);
                for (ZKPWatcher handler : handlers) {
                    handler.watch(path, watcher);
                }
                build(path);
                if (appConfigs.containsKey(path)) {
                    appConfigs.get(path).updateConfig(appConfigs.get(path));
                }
                System.out.println("++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("updateHandler:" + path);
                System.out.println(appConfigs.get(path));
                System.out.println("++++++++++++++++++++++++++++++++++++++++++");

            } catch (Exception ex) {
                System.out.println("==============================");
                System.out.println(path);
                System.out.println(ex.getMessage().toString());
                logger.error("Update exception for zookeeper : ", ex);
            }
        };
        watcher.expiredHandler = (path, watcher) -> {
            try {
                List<ZKPWatcher> handlers = WatcherPool.getList(WatcherPool.HandleMethod.EXPIRED);
                for (ZKPWatcher handler : handlers) {
                    handler.watch(path, watcher);
                }
                System.out.println("zookeeper session 过期");
                System.out.println(path);
                ZKPRouser.reload(ZKPSettings.ZOOKEEPER_PATH);
            } catch (Exception ex) {

            }
        };
        watcher.childrenChangedHandler = (path, watcher) -> {
            System.out.println("++++++++++++++++++++++++++++++++++++++++++");
            System.out.println(path);

            build(path);
            configMap.get(path).update();
            if (appConfigs.containsKey(path)) {
                appConfigs.get(path).updateConfig(appConfigs.get(path));
            }
            watcher.getChildren(path, true);
            List<ZKPWatcher> handlers = WatcherPool.getList(WatcherPool.HandleMethod.CHANGE);
            for (ZKPWatcher handler : handlers) {
                handler.watch(path, watcher);
            }
            System.out.println("childrenChangedHandler:" + path);
            System.out.println(appConfigs.get(path));
            System.out.println("++++++++++++++++++++++++++++++++++++++++++");
        };

        watcher.open();
        List<String> paths = watcher.getChildren(appPath, false);
        if (paths == null) {
            return;
        }
        for (String path : paths) {
            build(appPath + "/" + path);
        }
    }

    public synchronized static void build(String path) {

        ZKPConfig rconfig = remoteLoader.get(path);
        ZKPConfig lconfig = localLoader.get(path);

        if (rconfig == null && lconfig == null) {
            System.out.println("没有获得任何配置文件！");
            return;
        }

        if (rconfig == null) {
            lconfig.adjust();
            configMap.put(path, lconfig);
            return;
        }

        if (lconfig == null) {
            rconfig.adjust();
            configMap.put(path, rconfig);
            remoteLoader.save(path, rconfig);
            return;
        }

        if (lconfig.getVersion() > rconfig.getVersion()) {
            lconfig.adjust();
            configMap.put(path, lconfig);
        } else {
            rconfig.adjust();
            configMap.put(path, rconfig);
            remoteLoader.save(path, rconfig);
        }
    }

    public synchronized static <T> T get(String path) {
        if (!configMap.containsKey(path)) {
            build(path);
        }
        ZKPConfig config = configMap.get(path);
        if (!appConfigs.containsKey(path)) {
            appConfigs.put(path, config);
        }
        return (T) config;
    }
}
