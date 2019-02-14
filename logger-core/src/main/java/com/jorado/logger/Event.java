package com.jorado.logger;

import com.jorado.logger.consts.DataTypes;
import com.jorado.logger.consts.EventTypes;
import com.jorado.logger.data.BaseData;
import com.jorado.logger.data.collection.TagSet;
import com.jorado.logger.util.DateUtils;
import com.jorado.logger.util.StringUtils;

import java.util.Objects;

public final class Event extends BaseData {

    public Event() {
        this.occurredTime = DateUtils.timeNowMillis();
        this.tags = new TagSet();
    }

    private String referenceId;
    private String type;
    private String message;
    private String source;
    private String occurredTime;
    private TagSet tags;
    private transient boolean submitted;

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {

        this.referenceId = referenceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSource() {

        return source;
    }

    public void setSource(String source) {

        this.source = source;
    }

    public String getOccurredTime() {
        return occurredTime;
    }

    public void setOccurredTime(String occurredTime) {
        this.occurredTime = occurredTime;
    }

    public TagSet getTags() {

        return tags;
    }

    public void setTags(TagSet tags) {

        this.tags = tags;
    }

    public Event addTags(String... tags) {
        for (String s : tags) {
            this.getTags().add(s);
        }
        return this;
    }

    public boolean isError() {
        return StringUtils.isNotNullOrWhiteSpace(this.getType()) && this.getType().equals(EventTypes.ERROR);
    }

    public boolean isLog() {
        return StringUtils.isNotNullOrWhiteSpace(this.getType()) && this.getType().equals(EventTypes.LOG);
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    public boolean isWebApplication() {
        return this.containsData(DataTypes.REQUEST_INFO);
    }

    public void dispose() {
        this.clearData();
        this.getTags().clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return isSubmitted() == event.isSubmitted() &&
                Objects.equals(getReferenceId(), event.getReferenceId()) &&
                Objects.equals(getType(), event.getType()) &&
                Objects.equals(getMessage(), event.getMessage()) &&
                Objects.equals(getSource(), event.getSource()) &&
                Objects.equals(getOccurredTime(), event.getOccurredTime()) &&
                Objects.equals(getTags(), event.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReferenceId(), getType(), getMessage(), getSource(), getOccurredTime(), getTags(), isSubmitted());
    }
}
