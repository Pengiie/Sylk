package dev.penguinz.Sylk.util.maths;

import org.joml.Vector2f;

public class Vector2 {

    public float x;

    public float y;

    public Vector2() {
        this(0, 0);
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vector2 right) {
        this.x += right.x;
        this.y += right.y;
    }

    public void sub(Vector2 right) {
        this.x -= right.x;
        this.y -= right.y;
    }

    public static Vector2 add(Vector2 left, Vector2 right) {
        return new Vector2(left.x + right.x, left.y + right.y);
    }

    public static Vector2 sub(Vector2 left, Vector2 right) {
        return new Vector2(left.x - right.x, left.y - right.y);
    }

    public void normalize() {
        float total = (float) Math.sqrt(this.x * this.x + this.y * this.y);
        this.x = this.x / total;
        this.y = this.y / total;
    }

}
