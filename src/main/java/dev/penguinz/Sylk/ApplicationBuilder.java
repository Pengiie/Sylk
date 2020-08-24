package dev.penguinz.Sylk;

import dev.penguinz.Sylk.logging.LogLevel;
import dev.penguinz.Sylk.util.Layer;

public class ApplicationBuilder {

    private String title = "Sylk Engine";
    private int width = 1280, height = 720;
    private boolean resizable = false, fullscreen = false;
    private Layer[] layers = new Layer[0];
    private final LogLevel logLevel = LogLevel.INFO;

    public ApplicationBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public ApplicationBuilder setWidth(int width) {
        this.width = width;
        return this;
    }

    public ApplicationBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    public ApplicationBuilder setResizable(boolean resizable) {
        this.resizable = resizable;
        return this;
    }

    public ApplicationBuilder setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
        return this;
    }

    public ApplicationBuilder withLayers(Layer... layers) {
        this.layers = layers;
        return this;
    }

    private Application build() {
        return new Application(title, width, height, resizable, fullscreen, layers, logLevel);
    }

    public void buildAndRun() {
        build().run();
    }
}
