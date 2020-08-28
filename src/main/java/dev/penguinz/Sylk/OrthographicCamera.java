package dev.penguinz.Sylk;

import dev.penguinz.Sylk.util.maths.Transform;
import org.joml.Matrix4f;

public class OrthographicCamera extends Camera {

    public float zoom;
    public float aspectRatio;

    public OrthographicCamera() {
        this(new Transform(), 1);
    }

    public OrthographicCamera(float zoom) {
        this(new Transform(), zoom);
    }

    public OrthographicCamera(Transform transform) {
        this(transform, 1);
    }

    public OrthographicCamera(Transform transform, float zoom) {
        super(transform);
        this.zoom = zoom;
        updateProjectionMatrix();
    }

    @Override
    protected Matrix4f createProjectionMatrix() {
        this.aspectRatio = Application.getInstance().getWindowWidth() / Application.getInstance().getWindowHeight();
        return new Matrix4f().setOrtho2D(-this.aspectRatio * this.zoom, this.aspectRatio * this.zoom, -this.zoom, this.zoom);
    }

    public float getWindowTop() {
        return zoom;
    }

    public float getWindowBottom() {
        return -zoom;
    }

    public float getWindowRight() {
        return zoom * aspectRatio;
    }

    public float getWindowLeft() {
        return -zoom * aspectRatio;
    }
}
