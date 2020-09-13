package dev.penguinz.Sylk.tasks;

import dev.penguinz.Sylk.Time;

import java.util.ArrayList;
import java.util.List;

public class TaskScheduler {

    private List<Task> tasks = new ArrayList<>();

    public Task scheduleDelayedTask(Runnable runnable, float delay) {
        Task task = new Task(runnable, delay, false);
        tasks.add(task);
        return task;
    }

    public Task scheduleRepeatingTask(Runnable runnable, float repeatDelay) {
        Task task = new Task(runnable, repeatDelay, true);
        task.runnable.run();
        tasks.add(task);
        return task;
    }

    public void update() {
        for (int i = 0; i < tasks.size(); i++) {
            if(!tasks.get(i).isRunning()) {
                tasks.remove(i);
                i--;
            }
        }

        for (Task task : tasks) {
            if(task.lastTime + task.time <= Time.getTime()) {
                task.runnable.run();
                task.lastTime = Time.getTime();
                if(!task.repeated)
                    task.cancel();
            }
        }
    }

}
