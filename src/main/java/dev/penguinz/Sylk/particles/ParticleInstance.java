package dev.penguinz.Sylk.particles;

import dev.penguinz.Sylk.Time;
import dev.penguinz.Sylk.animation.values.AnimatableColor;
import dev.penguinz.Sylk.animation.values.AnimatableFloat;
import dev.penguinz.Sylk.animation.values.AnimatableVector2;
import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.maths.Vector2;

public class ParticleInstance {

    private Vector2 position;
    private AnimatableVector2 size;
    private AnimatableFloat rotation;
    private Vector2 velocity;

    private final float friction;
    private AnimatableColor color;
    private final float lifetime;

    private float currentLifetime = 0;

    public ParticleInstance(Vector2 startPosition, AnimatableVector2 size, AnimatableFloat rotation, Vector2 velocity, AnimatableColor color, float friction, float lifetime) {
        this.position = new Vector2(startPosition);
        this.size = size;
        this.rotation = rotation;
        this.velocity = new Vector2(velocity);
        this.color = color;
        this.friction = friction;
        this.lifetime = lifetime;
    }

    public boolean update() {
        this.currentLifetime += Time.deltaTime();
        if(this.currentLifetime >= this.lifetime)
            return true;

        this.position.add(Vector2.mul(velocity, Time.deltaTime()));
        this.velocity.mul(friction);

        return false;
    }

    public Color getColor() {
        return color.value;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getSize() {
        return size.value;
    }

    public float getRotation() {
        return rotation.value;
    }
}
