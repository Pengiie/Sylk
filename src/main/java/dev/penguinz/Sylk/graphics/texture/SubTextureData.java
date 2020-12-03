package dev.penguinz.Sylk.graphics.texture;

import dev.penguinz.Sylk.util.RefContainer;
import dev.penguinz.Sylk.util.maths.Vector2;

public class SubTextureData {

    private Vector2 min, max;
    private Vector2 size;

    public SubTextureData(Vector2 min, Vector2 max) {
        this.min = min;
        this.max = max;
        updateSize();
    }

    public void updateSize() {
        this.size = Vector2.sub(max, min);
    }

    public Vector2 getMin() {
        return min;
    }

    public Vector2 getMax() {
        return max;
    }

    public void setMin(Vector2 min) {
        this.min = min;
        updateSize();
    }

    public void setMax(Vector2 max) {
        this.max = max;
        updateSize();
    }

    public void reset(Vector2 min, Vector2 max) {
        this.min = min;
        this.max = max;
        updateSize();
    }

    public Vector2 getSize() {
        return size;
    }
}
