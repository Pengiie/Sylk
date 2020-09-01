package dev.penguinz.Sylk.particles;

import dev.penguinz.Sylk.Time;
import dev.penguinz.Sylk.animation.values.AnimatableColor;
import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.maths.Transform;
import dev.penguinz.Sylk.util.maths.Vector2;

public class Particle {

    private Transform transform;
    private Vector2 velocity;

    private final Vector2 friction;
    private Color color;
    private final float lifetime;

    private boolean isAlive = false;

    private float currentLifetime = 0;

    public Particle(Transform transform, Vector2 velocity, Color color, float friction, float lifetime) {
        this.transform = transform;
        this.velocity = velocity;
        this.color = color;
        this.friction = new Vector2(friction);
        this.lifetime = lifetime;
    }

    public boolean update() {
        this.currentLifetime += Time.deltaTime();
        if(this.currentLifetime >= this.lifetime)
            return true;

        this.transform.position.add(velocity);
        this.velocity.sub(friction);

        return false;
    }

    public Transform getTransform() {
        return transform;
    }
}
