package dev.penguinz.Sylk.physics;

import dev.penguinz.Sylk.util.maths.Vector2;

public class AABB {
    private Vector2 min;
    private Vector2 max;

    public AABB(Vector2 min, Vector2 max) {
        this.min = min;
        this.max = max;
    }
}
