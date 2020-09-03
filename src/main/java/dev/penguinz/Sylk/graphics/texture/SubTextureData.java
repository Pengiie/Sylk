package dev.penguinz.Sylk.graphics.texture;

import dev.penguinz.Sylk.util.RefContainer;
import dev.penguinz.Sylk.util.maths.Vector2;

public class SubTextureData {

    private final Vector2 min, max;

    public SubTextureData(Vector2 min, Vector2 max) {
        this.min = min;
        this.max = max;
    }

    public Vector2 getMin() {
        return min;
    }

    public Vector2 getMax() {
        return max;
    }
}
