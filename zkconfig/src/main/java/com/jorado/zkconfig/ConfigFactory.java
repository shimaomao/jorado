package com.jorado.zkconfig;

import com.jorado.core.logger.Logger;
import com.jorado.core.logger.LoggerFactory;

import java.util.HashMap;
import java.util.List;

public class ConfigFactory {

    private static final Logger logger = LoggerFactory.getLogger(ConfigFactory.class);

    private static ZKPManager watcher = new ZKPManager();
    private static HashMap<String, ZPConfig> configMap = new HashMap<>();
    private static HashMap<String, ZPConfig> appConfigs = new HashMap<>();
    private static AbsConfigCreater remoteCreater = new RemoteCreater(watcher);
    private static AbsConfigCreater fileCreater = new FileCreater();

    static {
        init(AppProperties.ZOOKEEPER_PATH);
    }

    public static boolean contain(String path) {
        return configMap.containsKey(path);
    }

    public static ZKPManager getWatcher() {
        return watcher;
    }

    public static void init(String appPath) {
        watcher.connectionHandler = (path, watcher) -> {
            List<IZKPWatcher> handlers = WatcherPool.getList(WatcherPool.HandleMethod.CONNECT);
            for (IZKPWatcher handler : handlers) {
                handler.watch(path, watcher);
            }
        };
        watcher.updateHandler = (path, watcher) -> {
            try {
                List<IZKPWatcher> handlers = WatcherPool.getList(WatcherPool.HandleMethod.UPDATE);
                for (IZKPWatcher handler : handlers) {
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
                List<IZKPWatcher> handlers = WatcherPool.getList(WatcherPool.HandleMethod.EXPIRED);
                for (IZKPWatcher handler : handlers) {
                    handler.watch(path, watcher);
                }
                System.out.println("zookeeper session 过期");
                System.out.println(path);
                ZKPRouser.reload(AppProperties.ZOOKEEPER_PATH);
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
            List<IZKPWatcher> handlers = WatcherPool.getList(WatcherPool.HandleMethod.CHANGE);
            for (IZKPWatcher handler : handlers) {
                handler.watch(path, watcher);
            }
            System.out.println("childrenChangedHandler:" + path);
            System.out.println(appConfigs.get(path));
            System.out.println("++++++++++++++++++++++++++++++++++++++++++");
        };

        watcher.createConnection();
        List<String> paths = watcher.getChildren(appPath, false);
        if (paths == null)
            return;
        for (String path : paths) {
            build(appPath + "/" + path);
        }
    }

    public synchronized static void build(String path) {
        ZPConfig rconfig = remoteCreater.get(path);
        ZPConfig fconfig = fileCreater.get(path);

        if (rconfig == null && fconfig == null) {
            System.out.println("没有获得任何配置文件！");
            return;
        }

        if (rconfig == null) {
            fconfig.adjust();
            configMap.put(path, fconfig);
            return;
        }

        if (fconfig == null) {
            rconfig.adjust();
            configMap.put(path, rconfig);
            remoteCreater.save(path, rconfig);
            return;
        }

        if (fconfig.getVersion() > rconfig.getVersion()) {
            fconfig.adjust();
            configMap.put(path, fconfig);
        } else {
            rconfig.adjust();
            configMap.put(path, rconfig);
            remoteCreater.save(path, rconfig);
        }
    }

    public synchronized static ZPConfig get(String path) {
        if (!configMap.containsKey(path)) {
            build(path);
        }
        ZPConfig config = configMap.get(path);
        if (!appConfigs.containsKey(path)) {
            appConfigs.put(path, config);
        }
        return config;
    }
}
