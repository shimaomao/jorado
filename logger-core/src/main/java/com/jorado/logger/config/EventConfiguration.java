package com.jorado.logger.config;

import com.jorado.logger.ThreadContextManager;
import com.jorado.logger.concurrent.threadcontext.ThreadContext;
import com.jorado.logger.data.collection.SettingsMap;
import com.jorado.logger.data.serializer.Serializer;
import com.jorado.logger.listener.Listener;
import com.jorado.logger.listener.ListenerManager;
import com.jorado.logger.logging.Logger;
import com.jorado.logger.plugin.Plugin;
import com.jorado.logger.plugin.PluginManager;
import com.jorado.logger.util.IOUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 未来此配置类里面的部分内容会通过扫描客户端配置文件来进行初始化
 * （譬如自动扫描用户自行事件的数据收集插件，序列化器等）
 */
public class EventConfiguration {

    private static volatile Map<String, Plugin> pluginMap = new LinkedHashMap<>();
    private static volatile Map<String, Listener> listenerMap = new LinkedHashMap<>();
    private static volatile ThreadContext threadContext;
    private ClientConfiguration clientConfiguration;
    private boolean enabled;
    private boolean counterEnabled;

    public EventConfiguration() {
        enabled = true;
        counterEnabled = false;
        clientConfiguration = new DefaultClientConfiguration();
        init();
    }

    public ClientConfiguration getClientConfiguration() {
        return clientConfiguration;
    }

    public void setClientConfiguration(ClientConfiguration clientConfiguration) {
        this.clientConfiguration = clientConfiguration;
    }

    public void close() {
        enabled = false;
    }

    public void open() {
        enabled = true;
    }

    public boolean isEnabled() {
        return enabled && clientConfiguration.isEnabled();
    }

    public Serializer getSerializer() {
        return clientConfiguration.getSerializer();
    }

    public Logger getLogger() {
        return clientConfiguration.getLogger();
    }

    public SettingsMap getClientSettings() {
        return clientConfiguration.getSettings();
    }

    public Map<String, Plugin> getPlugins() {
        return pluginMap;
    }

    public Map<String, Listener> getListeners() {
        return listenerMap;
    }

    public ThreadContext getThreadContext() {
        return threadContext;
    }

    public synchronized <T extends ThreadContext> void registerThreadContext(T threadContext) {

        if (null == threadContext) {
            return;
        }

        EventConfiguration.threadContext = threadContext;
        getLogger().info(String.format("ThreadContext [%s] register ok", threadContext.getClass().getName()));
    }

    public synchronized <T extends Plugin> void registerPlugin(T plugin) {

        if (null == plugin) {
            return;
        }

        String key = plugin.getClass().getName();

        if (pluginMap.containsKey(key)) {
            getLogger().warn(String.format("Plugin [%s] already registered", key));
            return;
        }

        if (!plugin.enabled()) {
            getLogger().warn(String.format("Plugin [%s] register fail,plugin not enabled", key));
            return;
        }

        pluginMap.put(key, plugin);
        getLogger().info(String.format("Plugin [%s] register ok", key));
    }

    public synchronized <T extends Listener> void registerListener(T listener) {

        if (null == listener) {
            return;
        }

        String key = listener.getClass().getName();

        if (listenerMap.containsKey(key)) {
            getLogger().warn(String.format("Listener [%s] already registered", key));
            return;
        }

        if (!listener.enabled()) {
            getLogger().info(String.format("Listener [%s] register fail,listener not enabled", key));
            return;
        }

        listenerMap.put(key, listener);
        getLogger().info(String.format("Listener [%s] register ok", key));
    }

    public synchronized <T extends Plugin> void unregisterPlugin(Class<T>... plugins) {
        for (Class t : plugins) {
            String key = t.getName();
            pluginMap.remove(key);
            getLogger().info(String.format("Plugin [%s] unregister ok", key));
        }
    }

    public synchronized <T extends Listener> void unregisterListener(Class<T>... listeners) {
        for (Class t : listeners) {
            String key = t.getName();
            listenerMap.remove(key);
            getLogger().info(String.format("Listener [%s] unregister ok", key));
        }
    }

    public synchronized void clearPlugin() {
        pluginMap.clear();
        getLogger().info("Plugin clear ok");
    }

    public synchronized void clearListener() {
        listenerMap.clear();
        getLogger().info("Listener clear ok");
    }

    public void openConsole() {
        ListenerManager.registerConsoleListener(this);
        System.out.println("console print opend");
        System.out.println("============================================");
    }

    public void closeConsole() {
        ListenerManager.unregisterConsoleListener(this);
        System.out.println("============================================");
        System.out.println("console print closed");
    }

    public void openCounter() {
        counterEnabled = true;
    }

    public void closeCounter() {
        counterEnabled = false;
    }

    public boolean isCounterEnabled() {
        return counterEnabled && clientConfiguration.isEnabled();
    }

    private void init() {
        System.out.println(IOUtils.readResource("banner.txt"));
        PluginManager.registerDefaultPlugins(this);
        ListenerManager.registerDefaultListeners(this);
        ThreadContextManager.registerThreadContext(this);
    }
}
