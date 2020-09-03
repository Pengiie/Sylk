package dev.penguinz.Sylk;

import dev.penguinz.Sylk.util.maths.Transform;
import dev.penguinz.Sylk.util.maths.Vector2;
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
        update();
    }

    @Override
    protected Matrix4f createProjectionMatrix() {
        this.aspectRatio = Application.getInstance().getWindowWidth() / Application.getInstance().getWindowHeight();
        return new Matrix4f().setOrtho2D(-this.aspectRatio * this.zoom, this.aspectRatio * this.zoom, -this.zoom, this.zoom);
    }

    @Override
    public Vector2 convertToWorldCoordinates(Vector2 screenCoordinates) {
        return new Vector2(
                screenCoordinates.x / Application.getInstance().getWindowWidth() * getWindowRight() * 2 - getWindowRight() + this.transform.position.x,
                -(screenCoordinates.y / Application.getInstance().getWindowHeight() * getWindowTop() * 2 - getWindowTop()) + this.transform.position.y
        );
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
