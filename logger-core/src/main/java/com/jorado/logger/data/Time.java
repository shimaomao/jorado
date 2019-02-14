package com.jorado.logger.data;

import java.io.Serializable;

public class Time implements Serializable {

    private long duration;

    private String description;

    public Time(long duration, String description) {
        this.duration = duration;
        this.description = description;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
