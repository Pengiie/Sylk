package dev.penguinz.SylkTests;

import dev.penguinz.Sylk.Application;
import dev.penguinz.Sylk.ApplicationBuilder;
import dev.penguinz.Sylk.OrthographicCamera;
import dev.penguinz.Sylk.audio.AudioSource;
import dev.penguinz.Sylk.audio.WavSound;
import dev.penguinz.Sylk.event.Event;
import dev.penguinz.Sylk.graphics.RenderLayer;
import dev.penguinz.Sylk.graphics.post.effects.BloomEffect;
import dev.penguinz.Sylk.particles.Particle;
import dev.penguinz.Sylk.particles.ParticleBuilder;
import dev.penguinz.Sylk.particles.ParticleEmitter;
import dev.penguinz.Sylk.particles.ParticleRenderer;
import dev.penguinz.Sylk.tasks.TaskScheduler;
import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.Layer;
import dev.penguinz.Sylk.util.maths.Vector2;

public class ParticleSandbox implements Layer {

    private OrthographicCamera camera;

    private ParticleRenderer particleRenderer;
    private ParticleEmitter emitter;

    private TaskScheduler taskScheduler = new TaskScheduler();

    private final Particle particle =
            new ParticleBuilder().
            setStartColor(new Color(0.8f, 0.4f, 0.4f, 1f)).
            setEndColor(new Color(0.5f, 0.2f, 0.2f, 0.5f)).
            setSize(new Vector2(0.25f, 0.25f)).
            setStartRotation(0).
            setEndRotation((float) Math.PI)
            .build();

    @Override
    public void init() {
        this.camera = new OrthographicCamera();
        this.particleRenderer = new ParticleRenderer(RenderLayer.RENDER0);
        this.emitter = new ParticleEmitter(new Vector2());

        Application.getInstance().addPostEffect(RenderLayer.RENDER0, new BloomEffect(camera, 20, 4, 1.2f, 1));
    }

    @Override
    public void update() {
        if(Application.getInstance().getInput().isMouseButtonPressed(0)) {
            Vector2 position = camera.convertToWorldCoordinates(new Vector2(Application.getInstance().getInput().getMousePosX(), Application.getInstance().getInput().getMousePosY()));
            taskScheduler.scheduleRepeatingTask(() -> {
                emitter.position = position;
                for (int i = 0; i < 10; i++) {
                    emitter.initialRotation = (float) (Math.random() * Math.PI * 2);
                    emitter.initialSize = new Vector2((float) Math.random() / 4 - 0.125f);
                    emitter.emit(particle, new Vector2((float) Math.random() - 0.5f, (float) Math.random() - 0.5f), 4);
                }
            }, 2500);
        }

        camera.update();
        emitter.update();

        taskScheduler.update();
    }

    @Override
    public void render() {
        this.particleRenderer.begin(camera);
        this.particleRenderer.renderEmitter(emitter);
        this.particleRenderer.finish();
    }

    @Override
    public void onEvent(Event event) {
    }

    @Override
    public void dispose() {
        this.particleRenderer.dispose();
    }

    public static void main(String[] args) {
        new ApplicationBuilder().setTitle("Particle Sandbox").withLayers(new ParticleSandbox()).buildAndRun();
    }
}
