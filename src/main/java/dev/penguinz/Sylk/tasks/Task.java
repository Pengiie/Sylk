package dev.penguinz.Sylk.tasks;

import dev.penguinz.Sylk.Time;

public class Task {

    final Runnable runnable;
    final float time, lastTime = Time.getTime();
    final boolean repeated;

    private boolean running = true;

    public Task(Runnable runnable, float time, boolean repeated) {
        this.runnable = runnable;
        this.time = time;
        this.repeated = repeated;
    }

    public void cancel() {
        this.running = false;
    }

    boolean isRunning() {
        return running;
    }
}
