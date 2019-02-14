package com.jorado.core.scheduler;

import com.jorado.core.logger.LoggerFactory;
import com.jorado.core.util.TimespanUnit;

import java.util.Calendar;
import java.util.Date;

public class TaskInfoBuilder {

    static int taskId = -1;

    public static <T extends ITask> TaskInfo Create(Class<T> t, TimespanUnit timeUnit) {
        return Create(t, null, new Date(), timeUnit);
    }

    public static <T extends ITask> TaskInfo Create(Class<T> t, Date runTime) {
        return Create(t, null, runTime, TimespanUnit.SECOND);
    }

    public static <T extends ITask> TaskInfo Create(Class<T> t, Date runTime, TimespanUnit timeUnit) {

        return Create(t, null, runTime, timeUnit);
    }


    public static <T extends ITask> TaskInfo Create(Class<T> t, Object argument, TimespanUnit timeUnit) {

        return Create(t, argument, new Date(), timeUnit);
    }

    public static <T extends ITask> TaskInfo Create(Class<T> t, Object argument, Date runTime, TimespanUnit timeUnit) {
        Calendar runNextTime = Calendar.getInstance();
        runNextTime.setTime(runTime);
        TaskInfo info = new TaskInfo(++taskId, runNextTime);
        info.setArgument(argument);
        info.setTimeUnit(timeUnit);
        try {
            info.setTask(t.newInstance());
        } catch (Exception ex) {
            LoggerFactory.getLogger(TaskInfoBuilder.class).error(ex);
        }
        return info;
    }

}
