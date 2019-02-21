package com.jorado.ik;

import com.jorado.ik.util.StringUtils;
import com.jorado.ik.watcher.LocalWatcher;
import com.jorado.ik.watcher.RemoteWatcher;
import com.jorado.ik.watcher.Watcher;
import org.apache.lucene.analysis.util.ResourceLoader;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class ReloaderRegister {

    private static Map<String, Watcher> localWatchers = new ConcurrentHashMap<>();
    private static Map<String, Watcher> remoteWatchers = new ConcurrentHashMap<>();

    public static synchronized void register(final Reloadable reloadable, final ResourceLoader loader) {

        if (StringUtils.isNotNullOrWhiteSpace(reloadable.getResourceName())) {
            String result = registerLocal(reloadable, loader);
            Logger.infoFormat("Register local watcher for [%s] ->%s", reloadable.getResourceName(), result);
        }

        if (StringUtils.isNotNullOrWhiteSpace(reloadable.getRemoteResourceName())) {
            String result = registerRemote(reloadable);
            Logger.infoFormat("Register remote watcher for [%s] ->%s", reloadable.getRemoteResourceName(), result);
        }
    }

    private static String registerLocal(final Reloadable reloadable, final ResourceLoader loader) {
        String name = reloadable.getBeanName();
        if (localWatchers.containsKey(name)) {
            return "already";
        }
        Watcher watcher = new LocalWatcher(loader);
        localWatchers.put(name, watcher);
        localUpdate(reloadable, loader);
        ScheduledExecutor.submit(() -> localUpdate(reloadable, loader), 60 * 1000);
        return "ok";

    }

    private static String registerRemote(final Reloadable reloadable) {
        String name = reloadable.getBeanName();
        if (remoteWatchers.containsKey(name)) {
            return "already";
        }
        Watcher watcher = new RemoteWatcher();
        remoteWatchers.put(name, watcher);
        remoteUpdate(reloadable);
        ScheduledExecutor.submit(() -> remoteUpdate(reloadable), 10 * 1000);
        return "ok";
    }

    private static void localUpdate(final Reloadable reloadable, final ResourceLoader loader) {

        String resourceName = reloadable.getResourceName();
        try {
            Watcher watcher = localWatchers.get(reloadable.getBeanName());
            Properties properties = watcher.changed(resourceName);
            if (null != properties) {
                Logger.infoFormat("Local [%s] begin reloading...", resourceName);
                reloadable.update(loader, properties.getProperty("files"));
                Logger.infoFormat("Local [%s] reload over", resourceName);
            }

        } catch (Exception e) {
            Logger.errorFormat("Local resource reload error,resource name:[%s]", resourceName);
            e.printStackTrace();
        }
    }

    private static void remoteUpdate(final Reloadable reloadable) {
        String resourceName = reloadable.getRemoteResourceName();
        try {
            Watcher watcher = remoteWatchers.get(reloadable.getBeanName());
            Properties properties = watcher.changed(resourceName);
            if (null != properties) {
                Logger.infoFormat("Remote [%s] begin reloading...", resourceName);
                reloadable.updateRemote(properties.getProperty("files"));
                Logger.infoFormat("Remote [%s] reload over", resourceName);
            }

        } catch (Exception e) {
            Logger.errorFormat("Remote resource reload error,resource name:[%s]", resourceName);
            e.printStackTrace();
        }
    }
}
