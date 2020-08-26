package dev.penguinz.Sylk;

import dev.penguinz.Sylk.assets.AssetManager;
import dev.penguinz.Sylk.assets.options.AssetOptions;
import dev.penguinz.Sylk.event.Event;
import dev.penguinz.Sylk.event.window.WindowCloseEvent;
import dev.penguinz.Sylk.graphics.VAO;
import dev.penguinz.Sylk.input.InputManager;
import dev.penguinz.Sylk.logging.LogLevel;
import dev.penguinz.Sylk.logging.Logger;
import dev.penguinz.Sylk.util.Layer;

import java.util.ArrayList;
import java.util.List;

/**
 * The main application of the engine.
 */
public class Application {

    private static Application instance;

    private boolean isRunning;

    private Logger logger;
    private Window window;
    private AssetManager assetManager;
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
        this.assetManager = new AssetManager();

        this.window = new Window(properties.title, properties.width, properties.height, properties.resizable, properties.fullscreen);

        attachLayers(properties.startupLayers);

        while(isRunning) {
            Time.getInstance().update();
            this.fps = (int) (1/Time.deltaTime());
            window.prepare();

            this.assetManager.update();

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
        assetManager.dispose();
        VAO.quad.dispose();
        VAO.triangle.dispose();
    }

    /**
     * Gets the logger tied to this application.
     * @return the logger.
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Gets the inputs of this application instance.
     * @return the inputs
     */
    public InputManager getInput() {
        return window.getInputManager();
    }

    /**
     * Gets the asset manager.
     * @return the application asset manager.
     */
    public AssetManager getAssets() {
        return assetManager;
    }

    /**
     * Terminates the application.
     */
    public void terminate() {
        this.isRunning = false;
    }

    /**
     * Gets the current window width.
     * @return the current window width.
     */
    public float getWindowWidth() {
        return window.getWidth();
    }

    /**
     * Gets the current window height.
     * @return the current window height.
     */
    public float getWindowHeight() {
        return window.getHeight();
    }

    /**
     * Sets the fullscreen state of the window.
     * @param fullscreen the fullscreen state.
     */
    public void setFullscreen(boolean fullscreen) {
        this.window.setFullscreen(fullscreen);
    }

    /**
     * Toggles between fullscreen and not fullscreen with the window.
     */
    public void toggleFullscreen() {
        this.window.toggleFullscreen();
    }

    /**
     * Gets the current fps at the moment.
     * @return the current fps.
     */
    public int getFps() {
        return fps;
    }

    /**
     * Gets the current instance of {@link Application}.
     * @return the application instance.
     */
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
