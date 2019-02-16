package com.jorado.logger.plugin;

import com.jorado.logger.AutoLoad;
import com.jorado.logger.EventContext;
import com.jorado.logger.OrderedComparator;
import com.jorado.logger.Sortable;
import com.jorado.logger.config.EventConfiguration;
import com.jorado.logger.util.ClassLoader;
import com.jorado.logger.util.MetaUtils;

import java.util.*;

/**
 * 插件管理器，使用方可根据需要自行实现数据添加器（未来通过扫描客户端自动注册或者手动注册）
 * EventClient.getDefault().getConfiguration().registerPlugin(T);
 */
public final class PluginManager {
    private PluginManager() {
    }

    public static void run(EventContext context) {
        for (Map.Entry<String, Plugin> entry : context.getPlugins().entrySet()) {
            Plugin plugin = entry.getValue();
            try {
                if (!plugin.enabled()) {
                    continue;
                }
                plugin.run(context);
                if (context.isCanceled()) {
                    context.getLogger().info(String.format("Event submit cancelled by plugin [%s] : refid=%s type=%s message=%s", plugin.getClass().getSimpleName(), context.getEvent().getReferenceId(), context.getEvent().getType(), context.getEvent().getMessage()));
                    return;
                }
            } catch (Exception ex) {
                context.getLogger().error(String.format("Run plugin [%s] error", plugin.getClass().getSimpleName()), ex);
            }
        }
    }

    public static void registerDefaultPlugins(EventConfiguration config) {
        List<Sortable> plugins = loadPlugins(config);
        plugins.addAll(load3rdPlugins());
        plugins.sort(new OrderedComparator(false));

        plugins.forEach(n -> {
            Plugin p = (Plugin) n;
            if (MetaUtils.hasAnnotation(p.getClass(), AutoLoad.class)) {
                config.registerPlugin(p);
            }
        });
    }

    /**
     * 加载内部插件
     *
     * @param config
     * @return
     */
    private static List<Sortable> loadPlugins(EventConfiguration config) {
        final String packageName = "com.jorado.logger";
        List<Sortable> sortables = new ArrayList<>();
        Set<Class<?>> classes = ClassLoader.getClasses(packageName, Plugin.class);
        classes.forEach(n -> {
            try {
                Plugin sortable = (Plugin) n.newInstance();
                if (sortable.enabled()) {
                    sortables.add(sortable);
                }
            } catch (Exception ex) {
                config.getLogger().error("Scan plugin error", ex);
            }
        });
        return sortables;
    }

    /**
     * 加载第三方插件
     *
     * @return
     */
    private static List<Sortable> load3rdPlugins() {
        ServiceLoader<Plugin> serviceLoader = ServiceLoader.load(Plugin.class);
        List<Sortable> sortables = new ArrayList<>();
        serviceLoader.forEach(n -> {
            if (n.enabled()) {
                sortables.add(n);
            }
        });
        return sortables;
    }
}
