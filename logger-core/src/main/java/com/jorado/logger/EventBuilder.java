package com.jorado.logger;

import com.jorado.logger.consts.DataTypes;
import com.jorado.logger.consts.EventTypes;
import com.jorado.logger.data.ContextData;
import com.jorado.logger.data.Time;
import com.jorado.logger.util.Stopwatch;
import com.jorado.logger.util.StringUtils;

import java.util.Map;

/**
 * 事件建造器
 */
public final class EventBuilder {

    private EventClient client;
    private Event target;
    private ContextData contextData;
    private Stopwatch stopwatch;
    private long timeout;

    public EventBuilder(Event ev) {
        this(ev, EventClient.getDefault());
    }

    public EventBuilder(Event ev, EventClient client) {
        this(ev, client, new ContextData());
    }

    public EventBuilder(Event ev, EventClient client, ContextData contextData) {
        this.stopwatch = Stopwatch.begin();
        this.target = ev;
        this.client = client;
        this.contextData = contextData;
    }

    public EventBuilder addData(Map<String, Object> dataMap) {
        if (null != dataMap) {
            for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                this.addData(entry.getKey(), entry.getValue());
            }
        }
        return this;
    }

    public EventBuilder addData(String name, Object data) {
        if (null != data)
            this.target.addData(name, data);
        return this;
    }

    public EventBuilder addInnerData(String name, Object data) {
        if (null != data)
            this.target.addData(name, this.client.getConfiguration().getSerializer().serialize(data, 0));
        return this;
    }

    public EventBuilder addTags(String... tags) {
        this.target.addTags(tags);
        return this;
    }

    public EventBuilder setMessage(String message) {
        this.target.setMessage(message);
        return this;
    }

    public EventBuilder setReferenceId(String referenceId) {
        this.target.setReferenceId(referenceId);
        return this;
    }

    public EventBuilder setSource(String source) {
        this.target.setSource(source);
        return this;
    }

    public EventBuilder setType(String type) {
        this.target.setType(type);
        return this;
    }

    public EventBuilder setError() {
        this.target.setType(EventTypes.ERROR);
        return this;
    }

    public Event getTarget() {
        return target;
    }

    public String getReferenceId() {
        return target.getReferenceId();
    }

    public EventBuilder setException(Throwable ex) {
        return setException(ex, false);
    }

    public EventBuilder setException(Throwable ex, boolean isUnhandledError) {
        setType(DataTypes.ERROR);
        this.contextData.setException(ex);
        if (isUnhandledError)
            this.contextData.markAsUnhandledError();
        return this;
    }

    public EventBuilder setTimeout(long timeout) {
        this.timeout = timeout;
        return this;
    }

    public ContextData getContextData() {
        return contextData;
    }

    /**
     * 同步提交
     *
     * @return
     */
    public String submit() {

        if (this.getTarget().isSubmitted())
            return this.getTarget().getReferenceId();

        this.stopwatch.stop();

        if (this.timeout > 0 && this.stopwatch.getDuration() < this.timeout)
            return this.getTarget().getReferenceId();

        Time time = new Time(this.stopwatch.getDuration(), this.stopwatch.toString());
        this.getTarget().addData("qtime", time);
        return this.client.submitEvent(this.getTarget(), getContextData());
    }

    /**
     * 异步提交
     */
    public void asyncSubmit() {

        if (this.getTarget().isSubmitted())
            return;

        this.stopwatch.stop();

        if (this.timeout > 0 && this.stopwatch.getDuration() < this.timeout)
            return;

        Time time = new Time(this.stopwatch.getDuration(), this.stopwatch.toString());
        this.getTarget().addData("qtime", time);

        String referenceId = this.client.getConfiguration().getThreadContext().getOrSetData("ReferenceId", StringUtils.uuid());

        this.getTarget().setReferenceId(referenceId);

        ThreadPoolManager.addJobToEventExecutor(() -> {
            this.client.submitEvent(this.getTarget(), getContextData());
        });
    }

    /**
     * 同步提交，超过了指定时间才提交
     *
     * @param timeout 超时时间
     * @return
     */
    public String submit(long timeout) {
        return this.setTimeout(timeout).submit();
    }

    /**
     * 异步提交，超过了指定时间才提交
     *
     * @param timeout 超时时间
     */
    public void asyncSubmit(long timeout) {
        this.setTimeout(timeout).asyncSubmit();
    }
}
