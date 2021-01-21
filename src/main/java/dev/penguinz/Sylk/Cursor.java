package dev.penguinz.Sylk;

import org.lwjgl.glfw.GLFW;

public enum Cursor {

    ARROW(GLFW.GLFW_ARROW_CURSOR),
    HAND(GLFW.GLFW_HAND_CURSOR),
    IBEAM(GLFW.GLFW_IBEAM_CURSOR),
    HRESIZE(GLFW.GLFW_HRESIZE_CURSOR),
    VRESIZE(GLFW.GLFW_VRESIZE_CURSOR);

    public final int glfwType;
    Cursor(int glfwType) {
        this.glfwType = glfwType;
    }

}
