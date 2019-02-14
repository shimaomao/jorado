package com.jorado.logger;

import com.jorado.logger.concurrent.threadcontext.ThreadContext;
import com.jorado.logger.data.ContextData;
import com.jorado.logger.data.collection.SettingsMap;
import com.jorado.logger.data.serializer.Serializer;
import com.jorado.logger.listener.Listener;
import com.jorado.logger.logging.Logger;
import com.jorado.logger.plugin.Plugin;

import java.util.Map;

public class EventContext {

    private EventClient client;
    private Event event;
    private ContextData contextData;
    private boolean canceled;

    public EventContext(EventClient client, Event ev, ContextData contextData) {
        this.client = client;
        this.event = ev;
        this.contextData = contextData;
    }

    public EventClient getClient() {
        return client;
    }

    public void setClient(EventClient client) {
        this.client = client;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public ContextData getContextData() {
        return contextData;
    }

    public void setContextData(ContextData contextData) {
        this.contextData = contextData;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public Logger getLogger() {
        return client.getConfiguration().getLogger();
    }

    public Serializer getSerializer() {
        return client.getConfiguration().getSerializer();
    }

    public Map<String, Listener> getListeners() {
        return client.getConfiguration().getListeners();
    }

    public Map<String, Plugin> getPlugins() {
        return client.getConfiguration().getPlugins();
    }

    public SettingsMap getClientSettings() {
        return client.getConfiguration().getClientSettings();
    }

    public boolean isCounterEnabled() {
        return client.getConfiguration().isCounterEnabled();
    }

    public ThreadContext getThreadContext() {
        return client.getConfiguration().getThreadContext();
    }
}
