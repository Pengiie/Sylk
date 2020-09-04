package dev.penguinz.Sylk.graphics.lighting;

import dev.penguinz.Sylk.util.Color;

public class AmbientLight extends Light {

    public float intensity;

    public AmbientLight(Color color, float intensity) {
        super(color);
        this.intensity = intensity;
    }
}
