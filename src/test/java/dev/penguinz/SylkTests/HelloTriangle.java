package dev.penguinz.SylkTests;

import dev.penguinz.Sylk.*;
import dev.penguinz.Sylk.assets.Texture;
import dev.penguinz.Sylk.event.Event;
import dev.penguinz.Sylk.graphics.*;
import dev.penguinz.Sylk.input.Key;
import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.Layer;
import dev.penguinz.Sylk.util.maths.Transform;
import dev.penguinz.Sylk.util.maths.Vector2;
import org.joml.Vector2f;

public class HelloTriangle implements Layer {

    private OrthographicCamera camera;
    private MainRenderer renderer;

    @Override
    public void init() {
        this.camera = new OrthographicCamera();
        this.renderer = new MainRenderer();
    }

    @Override
    public void update() {
        camera.update();
        if(Application.getInstance().getInput().isKeyPressed(Key.KEY_F11 ))
            Application.getInstance().toggleFullscreen();
    }

    @Override
    public void render() {
        Material mat = new Material();
        mat.glows = true;
        this.renderer.begin(camera);
        this.renderer.render(VAO.triangle, new Transform(), mat);
        this.renderer.finish();
    }

    @Override
    public void onEvent(Event event) {
        camera.onEvent(event);
        this.renderer.onEvent(event);
    }

    @Override
    public void dispose() {
        this.renderer.dispose();
    }

    public static void main(String[] args) {
        new ApplicationBuilder().
            setTitle("Hello Triangle").
            setResizable(true).
            withLayers(new HelloTriangle()).
            buildAndRun();
    }
}
