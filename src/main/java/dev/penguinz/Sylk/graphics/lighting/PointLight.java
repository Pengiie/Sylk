package dev.penguinz.Sylk.graphics.lighting;

import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.maths.Vector2;

public class PointLight extends Light {

    public final Vector2 position;
    public final float intensity;

    public PointLight(Color color, Vector2 position, float intensity) {
        super(color);
        this.position = position;
        this.intensity = intensity;
    }

}
