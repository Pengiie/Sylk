package dev.penguinz.Sylk;

import dev.penguinz.Sylk.event.Event;
import dev.penguinz.Sylk.event.window.WindowResizeEvent;
import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.MatrixUtils;
import dev.penguinz.Sylk.util.maths.Transform;
import dev.penguinz.Sylk.util.maths.Vector2;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public abstract class Camera {

    public final Transform transform;

    private Matrix4f projectionMatrix;
    private final Matrix4f projViewMatrix = new Matrix4f();

    public Color backgroundColor = new Color(Color.black);

    public Camera() {
        this(new Transform());
    }

    public Camera(Transform transform) {
        this.transform = transform;
    }

    protected void updateProjectionMatrix() {
        this.projectionMatrix = createProjectionMatrix();
    }

    public void update() {
        projectionMatrix.mul(MatrixUtils.createViewMatrix(transform), projViewMatrix);

        if(backgroundColor != null) {
            GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
            GL11.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
        }
    }

    public void onEvent(Event event) {
        if(event instanceof WindowResizeEvent) {
            updateProjectionMatrix();
            event.handle();
        }
    }

    public Matrix4f getProjViewMatrix() {
        return projViewMatrix;
    }

    protected abstract Matrix4f createProjectionMatrix();

    public abstract Vector2 convertToWorldCoordinates(Vector2 screenCoordinates);

}
