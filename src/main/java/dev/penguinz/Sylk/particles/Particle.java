package dev.penguinz.Sylk.particles;

import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.maths.Vector2;

public class Particle {

    public final Vector2 startSize, endSize;
    public final Color startColor, endColor;
    public final float startRotation, endRotation;
    public final float friction;

    public boolean isSizeAnimated, isColorAnimated, isRotationAnimated;

    public Particle(Vector2 startSize, Vector2 endSize, Color startColor, Color endColor, float startRotation, float endRotation, float friction) {
        this.startSize = startSize;
        this.endSize = endSize;
        this.isSizeAnimated = !startSize.equals(endSize);
        this.startColor = startColor;
        this.endColor = endColor;
        this.isColorAnimated = !startColor.equals(endColor);
        this.startRotation = startRotation;
        this.endRotation = endRotation;
        this.isRotationAnimated = startRotation != endRotation;
        this.friction = friction;
    }

}
