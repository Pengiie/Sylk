package dev.penguinz.Sylk;

import dev.penguinz.Sylk.event.Event;
import dev.penguinz.Sylk.event.window.WindowCloseEvent;
import dev.penguinz.Sylk.event.window.WindowResizeEvent;
import dev.penguinz.Sylk.input.InputManager;
import dev.penguinz.Sylk.util.Disposable;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window implements Disposable {

    private final String title;
    private int windowedWidth, windowedHeight;
    private int width, height;
    private final boolean resizable;
    private boolean fullscreen;

    private long windowHandle;
    private boolean hasContext = false;
    private boolean glfwInitialized = false;

    private InputManager inputManager;

    private final GLFWWindowCloseCallback closeCallback = new GLFWWindowCloseCallback() {
        @Override
        public void invoke(long window) {
            dispatchEvent(new WindowCloseEvent());
        }
    };

    private final GLFWFramebufferSizeCallback resizeCallback = new GLFWFramebufferSizeCallback() {
        @Override
        public void invoke(long window, int width, int height) {
            dispatchEvent(new WindowResizeEvent(width, height));
        }
    };

    public Window(String title, int width, int height, boolean resizable, boolean fullscreen) {
        this.title = title;
        this.windowedWidth = this.width = width;
        this.windowedHeight = this.height = height;
        this.resizable = resizable;
        this.fullscreen = fullscreen;

        init();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit() && !glfwInitialized)
            throw new IllegalStateException("Could not initialize GLFW");
        this.glfwInitialized = true;

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        if(fullscreen)
            if(vidmode == null)
                throw new IllegalArgumentException("Exception trying to get primary monitor video mode");

        windowHandle = glfwCreateWindow(fullscreen ? vidmode.width() : width, fullscreen ? vidmode.height() : height, title, fullscreen ? glfwGetPrimaryMonitor() : NULL, NULL);
        if(windowHandle == NULL)
            throw new RuntimeException("Window could not be created");

        glfwSetWindowCloseCallback(windowHandle, closeCallback);
        glfwSetFramebufferSizeCallback(windowHandle, resizeCallback);

        this.inputManager = new InputManager(windowHandle, this::dispatchEvent);

        if(!fullscreen)
            centerWindow();

        updateFramebufferSize();

        glfwMakeContextCurrent(windowHandle);

        glfwSwapInterval(1);

        glfwShowWindow(windowHandle);

        GL.createCapabilities();
        hasContext = true;

        setViewport();
    }

    private void centerWindow() {
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
    }

    private void updateFramebufferSize() {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);

            glfwGetFramebufferSize(windowHandle, width, height);

            this.width = width.get(0);
            this.height = height.get(0);
        }
    }

    private void setViewport() {
        GL11.glViewport(0, 0, this.width, this.height);
    }

    public void prepare() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        GL11.glClearColor(1, 0, 0, 0.5f);
        //GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
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
            if(!fullscreen) {
                this.windowedWidth = this.width;
                this.windowedHeight = this.height;
            }
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

    public void toggleFullscreen() {
        setFullscreen(!fullscreen);
    }

    public void setFullscreen(boolean fullscreen) {
        if(fullscreen == this.fullscreen)
            return;
        this.fullscreen = fullscreen;

        if(this.fullscreen) {
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            if(vidmode == null)
                throw new IllegalArgumentException("Exception trying to get primary monitor video mode");

            glfwSetWindowMonitor(windowHandle, glfwGetPrimaryMonitor(), 0, 0, vidmode.width(), vidmode.height(), vidmode.refreshRate());
            glfwSetWindowSize(windowHandle, vidmode.width(), vidmode.height());

            updateFramebufferSize();
        } else {
            glfwSetWindowMonitor(windowHandle, NULL, 50, 50, windowedWidth, windowedHeight, 0);
            glfwSetWindowSize(windowHandle, windowedWidth, windowedHeight);

            centerWindow();
        }
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
