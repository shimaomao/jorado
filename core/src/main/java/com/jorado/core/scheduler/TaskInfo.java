package com.jorado.core.scheduler;

import com.jorado.core.util.TimespanUnit;

import java.util.Calendar;

public class TaskInfo {

    int taskId;
    Object argument;
    Calendar nextRunTime;
    TimespanUnit timeUnit;
    ITask task;

    public Object getArgument() {
        return argument;
    }

    public void setArgument(Object argument) {
        this.argument = argument;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public ITask getTask() {
        return task;
    }

    public TimespanUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimespanUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public void setTask(ITask task) {
        this.task = task;
    }

    public Calendar getNextRunTime() {
        return nextRunTime;
    }

    public void setNextRunTime(Calendar nextRunTime) {
        this.nextRunTime = nextRunTime;
    }

    public TaskInfo(int taskId, Calendar nextRunTime) {
        super();
        this.taskId = taskId;
        this.nextRunTime = nextRunTime;
        this.timeUnit = TimespanUnit.SECOND;
    }

}
