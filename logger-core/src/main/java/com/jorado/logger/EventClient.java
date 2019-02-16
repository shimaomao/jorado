package com.jorado.logger;

import com.jorado.logger.config.EventConfiguration;
import com.jorado.logger.consts.DataTypes;
import com.jorado.logger.consts.EventTypes;
import com.jorado.logger.data.ContextData;
import com.jorado.logger.exception.NullEventException;
import com.jorado.logger.listener.ListenerManager;
import com.jorado.logger.logging.LogLevel;
import com.jorado.logger.plugin.PluginManager;
import com.jorado.logger.util.StringUtils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 事件代理
 */
public class EventClient implements Cloneable {

    private AtomicLong counter = new AtomicLong(0);

    private volatile String lastReferenceId;

    private EventConfiguration configuration;

    private static class EventClientHolder {
        private final static EventClient instance = new EventClient();
    }

    public EventClient() {
        this(new EventConfiguration());
    }

    public EventClient(EventConfiguration configuration) {
        this.configuration = configuration;
    }

    public EventConfiguration getConfiguration() {
        return configuration;
    }

    public static EventClient getDefault() {
        return EventClientHolder.instance;
    }

    public String getLastReferenceId() {
        return lastReferenceId;
    }

    public Long getTotal() {
        return counter.get();
    }

    public String submitEvent(Event ev) {
        return submitEvent(ev, new ContextData());
    }

    public String submitEvent(Event ev, ContextData contextData) {

        if (!configuration.isEnabled()) {
            return null;
        }

        if (null == ev) {
            throw new NullEventException();
        }

        if (ev.isSubmitted()) {
            return ev.getReferenceId();
        }

        if (StringUtils.isNullOrWhiteSpace(ev.getType())) {
            ev.setType(EventTypes.LOG);
        }

        EventContext context = new EventContext(this, ev, contextData);
        PluginManager.run(context);

        if (StringUtils.isNullOrWhiteSpace(ev.getReferenceId())) {
            ev.setReferenceId(StringUtils.uuid());
        }

        if (context.isCanceled()) {
            context.getLogger().info(String.format("Event submit cancelled by event pipeline: refid=%s type=%s message=%s", ev.getReferenceId(), ev.getMessage(), ev.getType()));
            return ev.getReferenceId();
        }

        ListenerManager.run(context);

        lastReferenceId = ev.getReferenceId();

        if (context.isCounterEnabled()) {
            counter.getAndIncrement();
        }

        ev.setSubmitted(true);

        //手动清除数据，加快gc回收
        ev.dispose();

        return ev.getReferenceId();
    }

    public String submitUnhandledException(Throwable exception) {
        return createException(exception, true).submit();
    }

    public String submitException(Throwable exception) {
        return createException(exception, false).submit();
    }

    public String submitUnhandledException(String message, Throwable exception) {
        return createException(exception, true).setMessage(message).submit();
    }

    public String submitException(String message, Throwable exception) {
        return createException(exception, false).setMessage(message).submit();
    }

    public String submitException(String message) {
        return createException(message).submit();
    }

    public String submitLog(String message) {

        return createLog(message).submit();
    }

    public String submitLog(String source, String message) {

        return createLog(source, message).submit();
    }

    public String submitLog(String source, String message, String level) {

        return createLog(source, message, level).submit();
    }

    public String submitLog(String source, String message, LogLevel level) {
        return createLog(source, message, level.toString()).submit();
    }

    public String submitLog(String message, LogLevel level) {

        return createLog(null, message, level.toString()).submit();
    }

    //---------------异步提交

    public void asyncSubmitUnhandledException(Throwable exception) {
        createException(exception, true).asyncSubmit();
    }

    public void asyncSubmitException(Throwable exception) {
        createException(exception, false).asyncSubmit();
    }

    public void asyncSubmitUnhandledException(String message, Throwable exception) {
        createException(exception, true).setMessage(message).asyncSubmit();
    }

    public void asyncSubmitException(String message, Throwable exception) {
        createException(exception, false).setMessage(message).asyncSubmit();
    }

    public void asyncSubmitException(String message) {
        createException(message).asyncSubmit();
    }

    public void asyncSubmitLog(String message) {

        createLog(message).asyncSubmit();
    }

    public void asyncSubmitLog(String source, String message) {

        createLog(source, message).asyncSubmit();
    }

    public void asyncSubmitLog(String source, String message, String level) {

        createLog(source, message, level).asyncSubmit();
    }

    public void asyncSubmitLog(String source, String message, LogLevel level) {
        createLog(source, message, level.toString()).asyncSubmit();
    }

    public void asyncSubmitLog(String message, LogLevel level) {

        createLog(null, message, level.toString()).asyncSubmit();
    }

    //---------------异步提交

    public EventBuilder createException(Throwable exception, boolean isUnhandledError) {
        ContextData contextData = new ContextData();
        contextData.setException(exception);
        if (isUnhandledError) {
            contextData.markAsUnhandledError();
        }
        return createEvent(contextData).setType(EventTypes.ERROR);
    }

    public EventBuilder createException(String message, Throwable exception) {
        return createException(exception, false).setMessage(message);
    }

    public EventBuilder createUnhandledException(String message, Throwable exception) {
        return createException(exception, true).setMessage(message);
    }

    public EventBuilder createException(Throwable exception) {
        return createException(exception, false);
    }

    public EventBuilder createUnhandledException(Throwable exception) {
        return createException(exception, true);
    }

    public EventBuilder createException(String message) {
        return createLog(message).setType(EventTypes.ERROR);
    }

    public EventBuilder createLog(String message) {

        return createEvent().setType(EventTypes.LOG).setMessage(message);
    }

    public EventBuilder createLog(String source, String message) {

        return createLog(message).setSource(source);
    }

    public EventBuilder createLog(String source, String message, String level) {
        EventBuilder builder = createLog(source, message);
        if (!StringUtils.isNullOrWhiteSpace(level)) {
            builder.addData(DataTypes.LEVEL, level.trim());
        }

        return builder;
    }

    public EventBuilder createLog(String source, String message, LogLevel level) {
        return createLog(source, message, level.toString());
    }

    public EventBuilder createLog(String message, LogLevel level) {
        return createLog(null, message, level.toString());
    }

    public EventBuilder createEvent() {
        return createEvent(new ContextData());
    }

    public EventBuilder createEvent(ContextData contextData) {
        Event event = new Event();
        event.setReferenceId(configuration.getThreadContext().getOrSetData("ReferenceId", StringUtils.uuid()));
        return new EventBuilder(event, this, contextData);
    }

    public void close() {
        configuration.close();
    }

    public void open() {
        configuration.open();
    }

    public void closeConsole() {
        configuration.closeConsole();
    }

    public void openConsole() {
        configuration.openConsole();
    }
}
