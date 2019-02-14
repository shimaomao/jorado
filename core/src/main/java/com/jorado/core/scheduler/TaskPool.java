package com.jorado.core.scheduler;

import java.util.*;

public class TaskPool {

    static Map<Integer, TaskInfo> _tasks = new HashMap<Integer, TaskInfo>();


    public static synchronized boolean ContainsTaskId(int taskId) {
        return _tasks.containsKey(taskId);
    }

    public static synchronized void add(TaskInfo taskInfo) {
        _tasks.put(taskInfo.taskId, taskInfo);

    }

    public static synchronized void remove(int taskId) {
        _tasks.remove(taskId);

    }

    public static synchronized void clear() {
        _tasks.clear();

    }

    public static synchronized List<TaskInfo> getWaitRunTasks() {
        List<TaskInfo> tasks = new ArrayList<TaskInfo>();
        Calendar now = Calendar.getInstance();
        for (int tid : _tasks.keySet()) {
            TaskInfo task = _tasks.get(tid);
            int i = now.compareTo(task.nextRunTime);
            if (i >= 0) {
                tasks.add(task);
                task.nextRunTime.add(Calendar.YEAR, 30);
            }
        }
        return tasks;
    }

    public static void main(String[] args) {
        Calendar next = Calendar.getInstance();
        next.add(Calendar.MINUTE, 2);
        TaskPool.add(new TaskInfo(1, next));
        List<TaskInfo> _tasks = getWaitRunTasks();
        System.out.println(_tasks);
    }

}
