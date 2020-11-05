package dev.penguinz.Sylk;

import dev.penguinz.Sylk.assets.AssetManager;
import dev.penguinz.Sylk.event.Event;
import dev.penguinz.Sylk.event.window.WindowCloseEvent;
import dev.penguinz.Sylk.graphics.ApplicationRenderer;
import dev.penguinz.Sylk.graphics.RenderLayer;
import dev.penguinz.Sylk.graphics.VAO;
import dev.penguinz.Sylk.graphics.post.effects.PostEffect;
import dev.penguinz.Sylk.input.InputManager;
import dev.penguinz.Sylk.logging.LogLevel;
import dev.penguinz.Sylk.logging.Logger;
import dev.penguinz.Sylk.physics.World;
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
    private ApplicationRenderer renderer;
    private AssetManager assetManager;
    private World physicsWorld;

    private final List<Layer> layers = new ArrayList<>();
    private final List<Layer> toAttachLayers = new ArrayList<>();
    private final List<Layer> toRemoveLayers = new ArrayList<>();

    private final ApplicationPropertySet properties;

    private int fps;

    Application(String title, int width, int height, boolean resizable, boolean fullscreen, String icon, Layer[] startupLayers, LogLevel logLevel) {
        instance = this;
        this.properties = new ApplicationPropertySet(title, width, height, resizable, fullscreen, icon, startupLayers, logLevel);
    }

    public void attachLayers(Layer... layers) {
        for (Layer layer : layers) {
            this.toAttachLayers.add(layer);
            layer.init();
        }
    }

    public void removeLayers(Layer... layers) {
        for (Layer layer : layers) {
            this.toRemoveLayers.add(layer);
            layer.dispose();
        }
    }

    public <T extends Layer> T getLayer(Class<T> layerClass) {
        for (Layer layer : layers) {
            if(layer.getClass().isAssignableFrom(layerClass)) {
                @SuppressWarnings("unchecked")
                T matchedLayer = (T) layer;
                return matchedLayer;
            }
        }
        throw new RuntimeException("Could not find layer of class: "+layerClass.getSimpleName());
    }

    public void onEvent(Event event) {
        if(event instanceof WindowCloseEvent) {
            isRunning = false;
            event.handle();
        }

        if(this.renderer != null)
            this.renderer.onEvent(event);

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
        this.physicsWorld = new World(-9.8f);

        this.window = new Window(properties.title, properties.width, properties.height, properties.resizable, properties.fullscreen, properties.icon);

        this.renderer = new ApplicationRenderer();

        attachLayers(properties.startupLayers);

        while(isRunning) {
            Time.getInstance().update();
            this.fps = (int) (1/Time.deltaTime());
            window.prepare();

            this.assetManager.update();

            layers.addAll(toAttachLayers);
            toAttachLayers.clear();

            for (Layer layer : layers) {
                layer.update();
            }
            for (Layer layer : layers) {
                layer.render();
            }
            this.physicsWorld.step(Time.deltaTime());

            layers.removeAll(toRemoveLayers);
            toRemoveLayers.clear();


            this.renderer.render();

            window.update();
        }
        dispose();
    }

    private void dispose() {
        window.dispose();
        assetManager.dispose();
        renderer.dispose();
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
     * Gets the physics world.
     * @return the physics world.
     */
    public World getPhysics() {
        return physicsWorld;
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

    public void bindRenderLayer(RenderLayer layer) {
        this.renderer.renderToLayer(layer);
    }

    /**
     * Adds a post processing effect to the specified render layer.
     * @param layer the render layer to add the effect to.
     * @param effect the effect to add to the render layer. {@link dev.penguinz.Sylk.graphics.post.effects.BloomEffect}, {@link dev.penguinz.Sylk.graphics.post.effects.VignetteEffect}
     */
    public void addPostEffect(RenderLayer layer, PostEffect effect) {
        this.renderer.addEffect(layer, effect);
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
        public final String icon;
        public final Layer[] startupLayers;
        public final LogLevel logLevel;

        public ApplicationPropertySet(String title, int width, int height, boolean resizable, boolean fullscreen, String icon, Layer[] startupLayers, LogLevel logLevel) {
            this.title = title;
            this.width = width;
            this.height = height;
            this.resizable = resizable;
            this.fullscreen = fullscreen;
            this.icon = icon;
            this.startupLayers = startupLayers;
            this.logLevel = logLevel;
        }
    }

}
