package com.jorado.core.scheduler;

public abstract class AbsScheduleTask implements ITask, IResumeTask {

    @Override
    public abstract void Execute(TaskInfo taskInfo);

    @Override
    public void resumeTask(TaskInfo taskInfo) {
        if (TaskPool.ContainsTaskId(taskInfo.getTaskId())) {
            taskInfo.nextRunTime = taskInfo.getTimeUnit().addNextTime();
        }
    }

}
