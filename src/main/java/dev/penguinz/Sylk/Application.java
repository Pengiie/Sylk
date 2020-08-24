package dev.penguinz.Sylk;

import dev.penguinz.Sylk.event.Event;
import dev.penguinz.Sylk.event.window.WindowCloseEvent;
import dev.penguinz.Sylk.graphics.VAO;
import dev.penguinz.Sylk.input.InputManager;
import dev.penguinz.Sylk.logging.LogLevel;
import dev.penguinz.Sylk.logging.Logger;
import dev.penguinz.Sylk.util.Layer;

import java.util.ArrayList;
import java.util.List;

public class Application {

    private static Application instance;

    private boolean isRunning;

    private Logger logger;
    private Window window;
    private final List<Layer> layers = new ArrayList<>();

    private final ApplicationPropertySet properties;

    private int fps;

    Application(String title, int width, int height, boolean resizable, boolean fullscreen, Layer[] startupLayers, LogLevel logLevel) {
        instance = this;
        this.properties = new ApplicationPropertySet(title, width, height, resizable, fullscreen, startupLayers, logLevel);
    }

    public void attachLayers(Layer... layers) {
        for (Layer layer : layers) {
            this.layers.add(layer);
            layer.init();
        }
    }

    public void removeLayers(Layer... layers) {
        for (Layer layer : layers) {
            this.layers.remove(layer);
            layer.dispose();
        }
    }

    public void onEvent(Event event) {
        if(event instanceof WindowCloseEvent) {
            isRunning = false;
            event.handle();
        }

        if(!event.isHandled()) {
            for (Layer layer : layers) {
                if(event.isHandled())
                    return;
                layer.onEvent(event);
            }
        }
    }

    public void run() {
        isRunning = true;

        this.logger = new Logger(properties.logLevel);

        this.window = new Window(properties.title, properties.width, properties.height, properties.resizable, properties.fullscreen);

        attachLayers(properties.startupLayers);

        while(isRunning) {
            Time.getInstance().update();
            this.fps = (int) (1/Time.deltaTime());
            window.prepare();

            for (Layer layer : layers) {
                layer.update();
            }
            for (Layer layer : layers) {
                layer.render();
            }

            window.update();
        }
        dispose();
    }

    private void dispose() {
        window.dispose();
        VAO.quad.dispose();
        VAO.triangle.dispose();
    }

    public Logger getLogger() {
        return logger;
    }

    public InputManager getInput() {
        return window.getInputManager();
    }

    public void terminate() {
        this.isRunning = false;
    }

    public float getWindowWidth() {
        return window.getWidth();
    }

    public float getWindowHeight() {
        return window.getHeight();
    }

    public void setFullscreen(boolean fullscreen) {
        this.window.setFullscreen(fullscreen);
    }

    public void toggleFullscreen() {
        this.window.toggleFullscreen();
    }

    public int getFps() {
        return fps;
    }

    public static Application getInstance() {
        return instance;
    }

    private static class ApplicationPropertySet {
        public final String title;
        public final int width, height;
        public final boolean resizable;
        public final boolean fullscreen;
        public final Layer[] startupLayers;
        public final LogLevel logLevel;

        public ApplicationPropertySet(String title, int width, int height, boolean resizable, boolean fullscreen, Layer[] startupLayers, LogLevel logLevel) {
            this.title = title;
            this.width = width;
            this.height = height;
            this.resizable = resizable;
            this.fullscreen = fullscreen;
            this.startupLayers = startupLayers;
            this.logLevel = logLevel;
        }
    }

}
