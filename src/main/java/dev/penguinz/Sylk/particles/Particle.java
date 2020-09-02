package dev.penguinz.Sylk.particles;

import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.maths.Vector2;

public class Particle {

    public final Vector2 startSize, endSize;
    public final Color startColor, endColor;
    public final float friction;

    public boolean isSizeAnimated, isColorAnimated;

    public Particle(Vector2 startSize, Vector2 endSize, Color startColor, Color endColor, float friction) {
        this.startSize = startSize;
        this.endSize = endSize;
        this.isSizeAnimated = !startSize.equals(endSize);
        this.startColor = startColor;
        this.endColor = endColor;
        this.isColorAnimated = !startColor.equals(endColor);
        this.friction = friction;
    }

}
