package com.jorado.search.core.config;

public class Client {
    private String name;
    private String description;
    private String addTime;
    private int enabled;
    private String field;
    private int maxRows;
    private int maxStart;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public int getEnabled() {
        return enabled;
    }

    public boolean isEnabled() {
        return getEnabled() > 0;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public int getMaxRows() {
        return maxRows;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public int getMaxStart() {
        return maxStart;
    }

    public void setMaxStart(int maxStart) {
        this.maxStart = maxStart;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", addTime='" + addTime + '\'' +
                ", enabled=" + enabled +
                ", field='" + field + '\'' +
                ", maxRows=" + maxRows +
                ", maxStart=" + maxStart +
                '}';
    }
}