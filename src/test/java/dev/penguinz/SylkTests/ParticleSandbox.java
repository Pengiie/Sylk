package dev.penguinz.SylkTests;

import dev.penguinz.Sylk.Camera;
import dev.penguinz.Sylk.OrthographicCamera;
import dev.penguinz.Sylk.event.Event;
import dev.penguinz.Sylk.util.Layer;

public class ParticleSandbox implements Layer {

    private Camera camera;

    @Override
    public void init() {
        this.camera = new OrthographicCamera();
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

    }

    @Override
    public void onEvent(Event event) {

    }

    @Override
    public void dispose() {

    }
}
