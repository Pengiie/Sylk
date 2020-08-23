package dev.penguinz.Sylk.util.maths;

public class Transform {

    public static final Vector2 triangleRotationAnchor = new Vector2(0.5f, 0.33f);

    public final Vector2 position;
    public final Vector2 rotationAnchor;
    public float rotation;
    private final Vector2 scale;

    public Transform() {
        this(new Vector2(), 0, new Vector2(0.5f, 0.5f), new Vector2(1, 1));
    }

    public Transform(Vector2 position) {
        this(position, 0, new Vector2(0.5f, 0.5f), new Vector2(1, 1));
    }
    public Transform(Vector2 position, Vector2 scale) {
        this(position, 0, new Vector2(scale.x/2, scale.y/2), scale);
    }

    public Transform(Vector2 position, float rotation, Vector2 rotationAnchor, Vector2 scale) {
        this.position = position;
        this.rotation = rotation;
        this.rotationAnchor = rotationAnchor;
        this.scale = scale;
    }

    public void setScale(float x, float y) {
        float xRatio = this.rotationAnchor.x / this.scale.x;
        float yRatio = this.rotationAnchor.y / this.scale.y;
        this.scale.x = x;
        this.scale.y = y;
        this.rotationAnchor.x = x * xRatio;
        this.rotationAnchor.y = y * yRatio;
    }

    public Vector2 getScale() {
        return scale;
    }
}
