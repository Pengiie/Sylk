package dev.penguinz.Sylk;

import dev.penguinz.Sylk.logging.LogLevel;
import dev.penguinz.Sylk.util.Layer;

/**
 * Builder for an {@link Application}.
 */
public class ApplicationBuilder {

    private String title = "Sylk Engine";
    private int width = 1280, height = 720;
    private boolean resizable = false, fullscreen = false;
    private Layer[] layers = new Layer[0];
    private String icon = null, iconSmall = null;
    private final LogLevel logLevel = LogLevel.INFO;

    /**
     * Sets the application title.
     * @param title the title of the application.
     * @return a reference to this object.
     */
    public ApplicationBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the starting window width.
     * @param width the desired window width.
     * @return a reference to this object.
     */
    public ApplicationBuilder setWidth(int width) {
        this.width = width;
        return this;
    }

    /**
     * Sets the starting window height.
     * @param height the desired window height.
     * @return a reference to this object.
     */
    public ApplicationBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    /**
     * Sets if the window can be resized.
     * @param resizable should the window be resizable.
     * @return a reference to this object.
     */
    public ApplicationBuilder setResizable(boolean resizable) {
        this.resizable = resizable;
        return this;
    }

    /**
     * Sets if the window should start in fullscreen.
     * @param fullscreen should the window start in fullscreen.
     * @return a reference to this object.
     */
    public ApplicationBuilder setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
        return this;
    }

    /**
     * The layers to launch the application with.
     * @param layers layers to startup with.
     * @return a reference to this object.
     */
    public ApplicationBuilder withLayers(Layer... layers) {
        this.layers = layers;
        return this;
    }

    /**
     * Sets the window icon to the specified image.
     * @param icon path to the image.
     * @return a reference to this object.
     */
    public ApplicationBuilder setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    private Application build() {
        return new Application(title, width, height, resizable, fullscreen, icon, layers, logLevel);
    }

    /**
     * Creates and runs the application.
     */
    public void buildAndRun() {
        build().run();
    }
}
