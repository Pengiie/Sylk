package dev.penguinz.SylkTests;

import dev.penguinz.Sylk.Application;
import dev.penguinz.Sylk.ApplicationBuilder;
import dev.penguinz.Sylk.OrthographicCamera;
import dev.penguinz.Sylk.event.Event;
import dev.penguinz.Sylk.graphics.MainRenderer;
import dev.penguinz.Sylk.graphics.Material;
import dev.penguinz.Sylk.graphics.RenderLayer;
import dev.penguinz.Sylk.graphics.VAO;
import dev.penguinz.Sylk.input.Key;
import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.Layer;
import dev.penguinz.Sylk.util.maths.Transform;
import dev.penguinz.Sylk.util.maths.Vector2;

public class HelloTriangle implements Layer {

    private OrthographicCamera camera;
    private MainRenderer renderer;
    private MainRenderer renderer2;

    @Override
    public void init() {
        this.camera = new OrthographicCamera();
        this.renderer = new MainRenderer();
        this.renderer2 = new MainRenderer(RenderLayer.RENDER1);

        //Application.getInstance().addPostEffect(RenderLayer.RENDER0, new BloomEffect(camera, 30f, 12, 1f));
        //Application.getInstance().addPostEffect(RenderLayer.RENDER1, new VignetteEffect(0.6f, 0.4f));
    }

    @Override
    public void update() {
        camera.update();
        if(Application.getInstance().getInput().isKeyPressed(Key.KEY_F11 ))
            Application.getInstance().toggleFullscreen();
    }

    @Override
    public void render() {
        this.renderer.begin(camera);
        this.renderer.render(VAO.quad, new Transform(), new Material());
        this.renderer.finish();

        this.renderer2.begin(camera);
        this.renderer2.render(VAO.triangle, new Transform(new Vector2(-0.25f, 0.25f)), new Material(new Color(1, 0.5f, 1, 0.8f)));
        this.renderer2.finish();
    }

    @Override
    public void onEvent(Event event) {
        camera.onEvent(event);
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
