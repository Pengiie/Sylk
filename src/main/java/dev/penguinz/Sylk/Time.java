package dev.penguinz.Sylk;

import org.lwjgl.glfw.GLFW;

public class Time {

    private static final Time instance = new Time();

    private float deltaTime;
    private double lastTime;

    private boolean cycledOnce = false;

    void update() {
        if(cycledOnce) {
            deltaTime = (float) (GLFW.glfwGetTime() - lastTime);
        }
        lastTime = GLFW.glfwGetTime();
        cycledOnce = true;
    }

    static Time getInstance() {
        return instance;
    }

    public static float deltaTime() {
        return instance.deltaTime;
    }
}
