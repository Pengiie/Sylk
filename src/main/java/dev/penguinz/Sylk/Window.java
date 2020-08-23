package dev.penguinz.Sylk;

import dev.penguinz.Sylk.event.Event;
import dev.penguinz.Sylk.event.window.WindowCloseEvent;
import dev.penguinz.Sylk.event.window.WindowResizeEvent;
import dev.penguinz.Sylk.input.InputManager;
import dev.penguinz.Sylk.util.Disposable;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window implements Disposable {

    private final String title;
    private int width, height;
    private final boolean resizable;

    private long windowHandle;
    private boolean hasContext = false;

    private InputManager inputManager;

    private final GLFWWindowCloseCallback closeCallback = new GLFWWindowCloseCallback() {
        @Override
        public void invoke(long window) {
            dispatchEvent(new WindowCloseEvent());
        }
    };

    private final GLFWWindowSizeCallback resizeCallback = new GLFWWindowSizeCallback() {
        @Override
        public void invoke(long window, int width, int height) {
            dispatchEvent(new WindowResizeEvent(width, height));
        }
    };



    public Window(String title, int width, int height, boolean resizable) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.resizable = resizable;

        init();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit())
            throw new IllegalStateException("Could not initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
        if(windowHandle == NULL)
            throw new RuntimeException("Window could not be created");

        glfwSetWindowCloseCallback(windowHandle, closeCallback);
        glfwSetWindowSizeCallback(windowHandle, resizeCallback);

        this.inputManager = new InputManager(windowHandle);

        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(windowHandle, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            if(vidmode != null)
                glfwSetWindowPos(windowHandle,
                    (vidmode.width() - pWidth.get(0))/2,
                    (vidmode.height() - pHeight.get(0))/2);
        }

        glfwMakeContextCurrent(windowHandle);

        glfwSwapInterval(1);

        glfwShowWindow(windowHandle);

        GL.createCapabilities();
        hasContext = true;

        setViewport();
    }

    private void setViewport() {
        GL11.glViewport(0, 0, this.width, this.height);
    }

    public void prepare() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void update() {
        glfwSwapBuffers(windowHandle);
        inputManager.clearInputs();
        glfwPollEvents();
    }

    public void onEvent(Event event) {
        if(event instanceof WindowResizeEvent) {
            this.width = ((WindowResizeEvent) event).width;
            this.height = ((WindowResizeEvent) event).height;
            if(hasContext)
                setViewport();
        }
    }

    public void dispatchEvent(Event event) {
        onEvent(event);

        Application.getInstance().onEvent(event);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    @Override
    public void dispose() {
        Callbacks.glfwFreeCallbacks(windowHandle);
        glfwDestroyWindow(windowHandle);

        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }
}
