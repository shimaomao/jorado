package com.jorado.qos.command;

import com.jorado.core.logger.Logger;
import com.jorado.core.logger.LoggerFactory;
import com.jorado.core.util.ClassUtils;
import com.jorado.qos.Startup;

import java.util.*;

public class CommandLoader {

    private static final Logger logger = LoggerFactory.getLogger(Startup.class);

    private static volatile Map<String, Class<?>> commandMap = new LinkedHashMap<>();

    static {
        registerCommand();
    }

    public static boolean hasCommand(String commandName) {
        return commandMap.containsKey(commandName.toLowerCase());
    }

    public static List<Class<?>> getAllCommandClass() {
        return loadCommand();
    }

    public static Class<?> getCommandClass(String commandName) {
        return commandMap.get(commandName.toLowerCase());
    }

    private static void registerCommand() {
        List<Class<?>> commands = loadCommand();
        commands.forEach(n -> commandMap.put(n.getSimpleName().toLowerCase(), n));
    }

    /**
     * 加载内部插件
     *
     * @return
     */
    private static List<Class<?>> loadCommand() {
        final String packageName = "com.jorado.qos";
        List<Class<?>> commands = new ArrayList<>();
        Set<Class<?>> classes = ClassUtils.getClasses(packageName, Command.class);
        classes.forEach(n -> {
            try {
                commands.add(n);
            } catch (Exception ex) {
                logger.error("Scan command error", ex);
            }
        });
        return commands;
    }
}
