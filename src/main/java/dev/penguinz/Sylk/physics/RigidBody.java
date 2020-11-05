package dev.penguinz.Sylk.physics;

import dev.penguinz.Sylk.util.maths.Vector2;

import java.util.ArrayList;
import java.util.List;

public class RigidBody {

    private List<Collider> colliders = new ArrayList<>();

    private Vector2 velocity;

    public float friction = 0;
    public float restitution = 0;
    public float mass = 1;
    public boolean isStatic = false;

    public RigidBody() {
        this.velocity = new Vector2();
    }

    public void addForce(Vector2 force, ForceType forceType) {
    }

    public List<Collider> getColliders() {
        return colliders;
    }

    public Vector2 getVelocity() {
        return velocity;
    }
}
