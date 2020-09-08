package dev.penguinz.SylkTests;

import dev.penguinz.Sylk.Application;
import dev.penguinz.Sylk.ApplicationBuilder;
import dev.penguinz.Sylk.OrthographicCamera;
import dev.penguinz.Sylk.Time;
import dev.penguinz.Sylk.event.Event;
import dev.penguinz.Sylk.graphics.MainRenderer;
import dev.penguinz.Sylk.graphics.Material;
import dev.penguinz.Sylk.graphics.VAO;
import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.Layer;
import dev.penguinz.Sylk.util.RefContainer;
import dev.penguinz.Sylk.util.maths.Transform;
import dev.penguinz.Sylk.util.maths.Vector2;

public class PhysicsSandbox implements Layer {

    private RefContainer<Transform> transform;
    //private RigidBody rigidBody;

    private RefContainer<Transform> platformTransform;
    //private RigidBody platform;

    private OrthographicCamera camera;
    private MainRenderer renderer;

    @Override
    public void init() {
        //Application.getInstance().getPhysics().setGravity(new Vector2(0, 0));

        this.transform = new RefContainer<>(new Transform(new Vector2(), new Vector2(0.5f)));
        /*this.rigidBody = new RigidBody(transform, RigidBody.Type.DYNAMIC, new BoxCollider(transform.value)).
                setRestitution(0.4f).
                setDensity(0.1f).
                setFriction(0.5f);*/

        platformTransform = new RefContainer<>(new Transform(new Vector2(-1, -0.8f), new Vector2(2, 0.25f)));
        //this.platform = new RigidBody(platformTransform, RigidBody.Type.STATIC, new BoxCollider(platformTransform));

        this.camera = new OrthographicCamera(5);
        this.renderer = new MainRenderer();
    }

    @Override
    public void update() {
        /*this.rigidBody.applyImpulseForce(new Vector2(
                Application.getInstance().getInput().getHorizontalInput() * Time.deltaTime(),
                Application.getInstance().getInput().getVerticalInput() * Time.deltaTime()));*/
    }

    @Override
    public void render() {
        this.renderer.begin(camera);
        this.renderer.render(VAO.quad, transform.value, new Material());
        this.renderer.render(VAO.quad, platformTransform.value, new Material(new Color(0.8f, 0.4f, 0.4f, 0.6f)));
        this.renderer.finish();
    }

    @Override
    public void onEvent(Event event) {

    }

    @Override
    public void dispose() {
        this.renderer.dispose();
    }

    public static void main(String[] args) {
        new ApplicationBuilder().setTitle("Physics Sandbox").withLayers(new PhysicsSandbox()).buildAndRun();
    }
}
