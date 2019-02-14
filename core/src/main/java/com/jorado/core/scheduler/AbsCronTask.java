package com.jorado.core.scheduler;

import com.jorado.core.logger.Logger;
import com.jorado.core.logger.LoggerFactory;
import com.jorado.core.util.TimeUtils;
import com.jorado.core.util.TimespanUnit;

import java.util.Calendar;
import java.util.Date;

public abstract class AbsCronTask extends AbsScheduleTask {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public abstract void Execute();

    @Override
    public void Execute(TaskInfo taskInfo) {
        if (taskInfo.argument != null) {
            String[] times = (String[]) taskInfo.argument;
            for (String t : times) {
                String[] hm = t.split(":");
                Calendar nowTime = Calendar.getInstance();
                System.out.println(TimeUtils.getStringDateShort(nowTime.getTime()));
                nowTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hm[0]));
                nowTime.set(Calendar.MINUTE, Integer.parseInt(hm[1]));
                Date runTime = nowTime.getTime();
                if (TimeUtils.compare(runTime, TimeUtils.EnumCompare.LT, new Date())) {
                    nowTime.add(Calendar.DAY_OF_MONTH, 1);
                    runTime = nowTime.getTime();
                }
                logger.info(TimeUtils.getStringDateShort(runTime));
                TaskInfo tinfo = TaskInfoBuilder.Create(this.getClass(), null, runTime, TimespanUnit.DAY);
                RunTaskScheduler.getInstance().Add(tinfo);
            }
            taskInfo.argument = null;
            TaskPool.remove(taskInfo.getTaskId());
        } else {
            Execute();
        }
    }

}
