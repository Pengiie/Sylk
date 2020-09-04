package dev.penguinz.Sylk.graphics.lighting;

import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.maths.Vector2;

public class DirectionalLight extends Light {

    public final Vector2 position;
    public final Vector2 direction;
    public final float intensity;
    public final float angle;

    public DirectionalLight(Color color, Vector2 position, Vector2 direction, float intensity, float angle) {
        super(color);
        this.position = position;
        this.direction = direction;
        this.intensity = intensity;
        this.angle = angle;
    }

}
