package dev.penguinz.SylkTests;

import dev.penguinz.Sylk.Application;
import dev.penguinz.Sylk.ApplicationBuilder;
import dev.penguinz.Sylk.OrthographicCamera;
import dev.penguinz.Sylk.Time;
import dev.penguinz.Sylk.event.Event;
import dev.penguinz.Sylk.graphics.MainRenderer;
import dev.penguinz.Sylk.graphics.Material;
import dev.penguinz.Sylk.graphics.RenderLayer;
import dev.penguinz.Sylk.graphics.VAO;
import dev.penguinz.Sylk.graphics.lighting.AmbientLight;
import dev.penguinz.Sylk.graphics.lighting.Light;
import dev.penguinz.Sylk.physics.AABB;
import dev.penguinz.Sylk.physics.ForceType;
import dev.penguinz.Sylk.physics.RigidBody;
import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.Layer;
import dev.penguinz.Sylk.util.RefContainer;
import dev.penguinz.Sylk.util.maths.Transform;
import dev.penguinz.Sylk.util.maths.Vector2;

public class PhysicsSandbox implements Layer {

    private RefContainer<Transform> transform;
    private RigidBody rigidBody;

    private RefContainer<Transform> platformTransform;
    private RigidBody platform;

    private OrthographicCamera camera;
    private MainRenderer renderer;

    @Override
    public void init() {
        Application.getInstance().getPhysics().setGravity(new Vector2(0, -1.0f));

        this.transform = new RefContainer<>(new Transform(new Vector2(), new Vector2(0.5f)));
        this.rigidBody = new RigidBody(transform, RigidBody.Type.DYNAMIC).
                addCollider(new AABB(transform.value));
        Application.getInstance().getPhysics().addRigidBody(this.rigidBody);

        this.platformTransform = new RefContainer<>(new Transform(new Vector2(-1, -0.8f), new Vector2(2, 0.25f)));
        this.platform = new RigidBody(platformTransform, RigidBody.Type.STATIC).
            addCollider(new AABB(platformTransform.value));
        Application.getInstance().getPhysics().addRigidBody(this.platform);

        this.camera = new OrthographicCamera();
        this.renderer = new MainRenderer();
    }

    @Override
    public void update() {
        camera.update();

        this.rigidBody.addForce(new Vector2(
                Application.getInstance().getInput().getHorizontalInput() * Time.deltaTime(),
                Application.getInstance().getInput().getVerticalInput() * Time.deltaTime()), ForceType.IMPULSE);
    }

    @Override
    public void render() {
        Light ambientLight = new AmbientLight(Color.white, 1);
        this.renderer.begin(camera);
        this.renderer.addLight(ambientLight);
        this.renderer.render(VAO.quad, transform.value, new Material(Color.white));
        this.renderer.render(VAO.quad, platformTransform.value, new Material(new Color(0.8f, 0.4f, 0.7f, 0.8f)));
        this.renderer.finish();
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
        new ApplicationBuilder().setTitle("Physics Sandbox").withLayers(new PhysicsSandbox()).buildAndRun();
    }
}
