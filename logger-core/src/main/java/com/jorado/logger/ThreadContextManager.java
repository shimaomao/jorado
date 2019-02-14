package com.jorado.logger;

import com.jorado.logger.concurrent.threadcontext.ThreadContext;
import com.jorado.logger.config.EventConfiguration;
import com.jorado.logger.util.ClassLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ThreadContextManager {

    public static void registerThreadContext(EventConfiguration config) {

        List<Sortable> threadContexts = scanClasss(config);

        ThreadContext tc = (ThreadContext) threadContexts.get(0);

        config.registerThreadContext(tc);
    }

    private static List<Sortable> scanClasss(EventConfiguration config) {
        final String packageName = "com.jorado.logger";
        List<Sortable> sortables = new ArrayList<>();
        Set<Class<?>> classes = ClassLoader.getClasses(packageName, ThreadContext.class);
        for (Class c : classes) {
            try {
                ThreadContext sortable = (ThreadContext) c.newInstance();
                sortables.add(sortable);
            } catch (Exception ex) {
                config.getLogger().error("Scan thread context error", ex);
            }
        }
        sortables.sort(new OrderedComparator(false));
        return sortables;
    }
}
