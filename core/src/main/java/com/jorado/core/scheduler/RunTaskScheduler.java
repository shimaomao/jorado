package com.jorado.core.scheduler;

import com.jorado.core.logger.Logger;
import com.jorado.core.logger.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class RunTaskScheduler {

    private RunTaskScheduler() {

    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static RunTaskScheduler taskScheduler = new RunTaskScheduler();

    public static RunTaskScheduler getInstance() {
        return taskScheduler;
    }

    ScheduledExecutorService schedule = Executors.newScheduledThreadPool(2);

    public void start() {
        schedule.scheduleWithFixedDelay(new TaskExecuter(), 0, 1, TimeUnit.SECONDS);
    }

    public void Add(TaskInfo taskInfo) {
        TaskPool.add(taskInfo);
    }

    public void stop() {

    }

    public void clearTask() {
        List<Future<TaskInfo>> newList = new ArrayList<Future<TaskInfo>>();
        for (Future<TaskInfo> future : futureList) {
            if (!future.isDone())
                newList.add(future);
        }
        synchronized (futureList) {
            futureList = newList;
        }
    }

    public List<Future<TaskInfo>> futureList = new ArrayList<Future<TaskInfo>>();

    class TaskCallable implements Callable<TaskInfo> {
        public TaskCallable(TaskInfo info) {
            taskInfo = info;
        }

        TaskInfo taskInfo;

        @Override
        public TaskInfo call() {
            try {
                taskInfo.getTask().Execute(taskInfo);
            } catch (Exception ex) {
                logger.error(ex);
            }

            if (taskInfo.getTask() instanceof IResumeTask) {
                IResumeTask iaddtask = (IResumeTask) taskInfo.getTask();
                iaddtask.resumeTask(taskInfo);
            }
            return taskInfo;
        }

    }

    ExecutorService testExecutor = Executors.newFixedThreadPool(100);

    class TaskExecuter implements Runnable {
        @Override
        public void run() {
            RunTaskScheduler.this.clearTask();

            List<TaskInfo> _tasks = TaskPool.getWaitRunTasks();
            if (_tasks.size() > 0) {
                synchronized (futureList) {
                    for (TaskInfo taskInfo : _tasks) {
                        Future<TaskInfo> future = testExecutor.submit(new TaskCallable(taskInfo));
                        futureList.add(future);
                    }
                }
            }

        }
    }

}
