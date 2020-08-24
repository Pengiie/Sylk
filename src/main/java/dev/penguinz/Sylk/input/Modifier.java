package dev.penguinz.Sylk.input;

import org.lwjgl.glfw.GLFW;

public enum Modifier {

    SHIFT(GLFW.GLFW_MOD_SHIFT),
    CONTROL(GLFW.GLFW_MOD_CONTROL),
    ALT(GLFW.GLFW_MOD_ALT),
    CAPS_LOCK(GLFW.GLFW_MOD_CAPS_LOCK),
    NUM_LOCK(GLFW.GLFW_MOD_NUM_LOCK);

    protected final int modCode;

    Modifier(int modCode) {
        this.modCode = modCode;
    }

}
