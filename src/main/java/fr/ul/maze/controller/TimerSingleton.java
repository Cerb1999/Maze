package fr.ul.maze.controller;

import fr.ul.maze.controller.tasks.CustomTask;
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

    /** initialize instance of singleton
     * @return instance
     */
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

    /**
     * add task to map,
     *  if same type of task exists then timer is refreshed
     *  else add task to map and launch it
     * @param customTask newly added task
     * @param taskType type of task (enum)
     */
    public void addTask(CustomTask customTask, TaskType taskType)
    {
        if(tasks.containsKey(taskType)) tasks.get(taskType).refresh();
        else {
            tasks.put(taskType, customTask);
            customTask.launch();
        }
    }

    /**
     * stop each tasks (ex: pause)
     */
    public void stopTasks() {
        this.tasks.forEach((taskType,task) -> task.stop());
    }

    /**
     * start all tasks (ex: unpause)
     */
    public void startTasks() {
        this.tasks.forEach((taskType,task) -> task.start());
    }

    /**
     * remove a task from map (ex: when it ends)
     * @param task task to be added
     */
    public void removeTask(CustomTask task) {
        if(this.tasks.containsValue(task)) {
            tasks.values().remove(task);
        }
    }

    /**
     * clear all timers and empty the map
     */
    public void clearTasks() {
        this.tasks.forEach((taskType,task) -> task.clear());
        this.tasks.clear();
    }

    /**
     * countdown from timer
     * @return time
     */
    public String getTime() {
        return tasks.get(TaskType.GAME).getTime();
    }
}