package com.jorado.logger.listener;

import com.jorado.logger.AutoLoad;
import com.jorado.logger.EventContext;
import com.jorado.logger.OrderedComparator;
import com.jorado.logger.Sortable;
import com.jorado.logger.config.EventConfiguration;
import com.jorado.logger.exception.NoListenerFoundException;
import com.jorado.logger.listener.impl.ConsoleListener;
import com.jorado.logger.util.ClassLoader;
import com.jorado.logger.util.MetaUtils;

import java.util.*;

/**
 * 输出控制器，使用方可根据需要自行实现数据输出，譬如发送到邮箱（未来通过扫描客户端自动注册或者手动注册）
 * EventClient.getDefault().getConfiguration().registerListener(T)
 */
public final class ListenerManager {
    private ListenerManager() {
    }

    public static void run(EventContext context) {
        if (context.getListeners().isEmpty()) {
            throw new NoListenerFoundException();
        }

        for (Map.Entry<String, Listener> entry : context.getListeners().entrySet()) {
            Listener listener = entry.getValue();
            try {
                if (!listener.enabled()) {
                    continue;
                }
                listener.run(context);
            } catch (Exception ex) {
                context.getLogger().error(String.format("Run listener [%s] error", listener.getClass().getSimpleName()), ex);
            }
        }

    }

    public static void registerDefaultListeners(EventConfiguration config) {
        List<Sortable> listeners = loadListeners(config);
        listeners.addAll(load3rdListener());
        listeners.sort(new OrderedComparator(false));

        listeners.forEach(n -> {
            Listener l = (Listener) n;
            if (MetaUtils.hasAnnotation(l.getClass(), AutoLoad.class)) {
                config.registerListener(l);
            }
        });
    }

    public static void registerConsoleListener(EventConfiguration config) {
        config.registerListener(new ConsoleListener());
    }

    public static void unregisterConsoleListener(EventConfiguration config) {
        config.unregisterListener(ConsoleListener.class);
    }

    /**
     * 加载内部监听器
     *
     * @param config
     * @return
     */
    private static List<Sortable> loadListeners(EventConfiguration config) {
        final String packageName = "com.jorado.logger";
        List<Sortable> sortables = new ArrayList<>();
        Set<Class<?>> classes = ClassLoader.getClasses(packageName, Listener.class);
        classes.forEach(n -> {
            try {
                Listener sortable = (Listener) n.newInstance();
                if (sortable.enabled()) {
                    sortables.add(sortable);
                }
            } catch (Exception ex) {
                config.getLogger().error("Scan listener error", ex);
            }
        });
        return sortables;
    }

    /**
     * 加载第三方监听器
     *
     * @return
     */
    private static List<Sortable> load3rdListener() {
        ServiceLoader<Listener> serviceLoader = ServiceLoader.load(Listener.class);
        List<Sortable> sortables = new ArrayList<>();
        serviceLoader.forEach(n -> {
            if (n.enabled()) {
                sortables.add(n);
            }
        });
        return sortables;
    }
}
