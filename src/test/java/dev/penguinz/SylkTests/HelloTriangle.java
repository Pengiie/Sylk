package dev.penguinz.SylkTests;

import dev.penguinz.Sylk.Application;
import dev.penguinz.Sylk.ApplicationBuilder;
import dev.penguinz.Sylk.OrthographicCamera;
import dev.penguinz.Sylk.event.Event;
import dev.penguinz.Sylk.graphics.MainRenderer;
import dev.penguinz.Sylk.graphics.Material;
import dev.penguinz.Sylk.graphics.RenderLayer;
import dev.penguinz.Sylk.graphics.VAO;
import dev.penguinz.Sylk.graphics.lighting.AmbientLight;
import dev.penguinz.Sylk.graphics.lighting.DirectionalLight;
import dev.penguinz.Sylk.graphics.lighting.Light;
import dev.penguinz.Sylk.graphics.lighting.PointLight;
import dev.penguinz.Sylk.graphics.post.effects.BloomEffect;
import dev.penguinz.Sylk.graphics.post.effects.VignetteEffect;
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
        Application.getInstance().addPostEffect(RenderLayer.RENDER1, new VignetteEffect(0.6f, 0.4f));
    }

    @Override
    public void update() {
        camera.update();
        if(Application.getInstance().getInput().isKeyPressed(Key.KEY_F11 ))
            Application.getInstance().toggleFullscreen();
    }

    @Override
    public void render() {
        Light light = new AmbientLight(Color.white, 1);

        this.renderer.begin(camera);
        this.renderer.addLight(light);
        this.renderer.render(VAO.quad, new Transform(), new Material(new Color(0.4f, 0.4f, 0.8f)));
        this.renderer.finish();

        this.renderer2.begin(camera);
        this.renderer2.addLight(light);
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
