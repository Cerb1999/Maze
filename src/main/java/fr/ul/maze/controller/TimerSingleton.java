package fr.ul.maze.controller;

import fr.ul.maze.controller.tasks.CustomTask;
import fr.ul.maze.controller.tasks.GameTimerTask;
import fr.ul.maze.controller.tasks.TaskType;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class TimerSingleton {
    private static TimerSingleton instance = null;
    private final Map<TaskType, CustomTask> tasks;

    private TimerSingleton() {
        this.tasks = Collections.synchronizedMap(new EnumMap<>(TaskType.class));
    }

    public synchronized static TimerSingleton init() {
        if (instance != null)
        {
            throw new AssertionError("You already initialized me");
        }

        instance = new TimerSingleton();
        return instance;
    }

    public static TimerSingleton getInstance() {
        return instance;
    }


    public void addTask(CustomTask customTask, TaskType taskType)
    {
        if(tasks.containsKey(taskType)) tasks.get(taskType).refresh();
        else {
            tasks.put(taskType, customTask);
            customTask.launch();
        }
    }

    public void stopTasks() {
        this.tasks.forEach((taskType,task) -> task.stop());
    }
    public void startTasks() {
        this.tasks.forEach((taskType,task) -> task.start());
    }

    public void removeTask(CustomTask task) {
        if(this.tasks.containsValue(task)) {
            tasks.values().remove(task);
        }
    }

    public void clearTasks() {
        this.tasks.forEach((taskType,task) -> task.clear());
        this.tasks.clear();
    }

    public String getTime() {
        return tasks.get(TaskType.GAME).getTime();
    }
}