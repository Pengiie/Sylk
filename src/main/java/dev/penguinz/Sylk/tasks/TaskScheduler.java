package dev.penguinz.Sylk.tasks;

import dev.penguinz.Sylk.Time;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskScheduler {

    private List<Task> tasks = new ArrayList<>();

    public Task scheduleDelayedTask(Runnable runnable, float delay) {
        Task task = new Task(runnable, delay * 0.001f, false);
        tasks.add(task);
        return task;
    }

    public Task scheduleRepeatingTask(Runnable runnable, float repeatDelay) {
        Task task = new Task(runnable, repeatDelay * 0.001f, true);
        tasks.add(task);
        return task;
    }

    public void update() {
        tasks = tasks.stream().filter(Task::isRunning).collect(Collectors.toList());

        for (Task task : tasks) {
            if(task.lastTime + task.time <= Time.getTime()) {
                task.runnable.run();
                if(!task.repeated)
                    task.cancel();
            }
        }
    }

}
