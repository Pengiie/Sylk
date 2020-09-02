package dev.penguinz.Sylk.particles;

import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.maths.Vector2;

public class ParticleBuilder {

    private Vector2 startSize = new Vector2(1), endSize = startSize;
    private Color startColor = Color.white, endColor = startColor;
    private float startRotation = 0, endRotation = 1;
    private float friction = 1;

    public ParticleBuilder setColor(Color color) {
        this.startColor = new Color(color);
        this.endColor = new Color(color);
        return this;
    }

    public ParticleBuilder setStartColor(Color startColor) {
        this.startColor = new Color(startColor);
        return this;
    }

    public ParticleBuilder setEndColor(Color endColor) {
        this.endColor = new Color(endColor);
        return this;
    }

    public ParticleBuilder setSize(Vector2 size) {
        this.startSize = new Vector2(size);
        this.endSize = new Vector2(size);
        return this;
    }

    public ParticleBuilder setStartSize(Vector2 startSize) {
        this.startSize = new Vector2(startSize);
        return this;
    }

    public ParticleBuilder setEndSize(Vector2 endSize) {
        this.endSize = new Vector2(endSize);
        return this;
    }

    public ParticleBuilder setFriction(float friction) {
        this.friction = friction;
        return this;
    }

    public ParticleBuilder setRotation(float rotation) {
        this.startRotation = rotation;
        this.endRotation = rotation;
        return this;
    }

    public ParticleBuilder setStartRotation(float startRotation) {
        this.startRotation = startRotation;
        return this;
    }

    public ParticleBuilder setEndRotation(float endRotation) {
        this.endRotation = endRotation;
        return this;
    }

    public Particle build() {
        return new Particle(startSize, endSize, startColor, endColor, startRotation, endRotation, friction);
    }
}
