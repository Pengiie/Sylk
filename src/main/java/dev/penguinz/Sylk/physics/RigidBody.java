package dev.penguinz.Sylk.physics;

import dev.penguinz.Sylk.Time;
import dev.penguinz.Sylk.util.RefContainer;
import dev.penguinz.Sylk.util.maths.Transform;
import dev.penguinz.Sylk.util.maths.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class RigidBody {

    private final RefContainer<Transform> transform;
    private final List<Collider> colliders = new ArrayList<>();

    private Vector2 velocity;

    private RigidBody.Type type;
    public float friction = 0;
    public float restitution = 0.3f;
    public float mass = 1;
    public float density = 1;
    public boolean isKinematic = false;

    public RigidBody(Transform transform, RigidBody.Type type) {
        this(new RefContainer<>(transform), type);
    }

    public RigidBody(RefContainer<Transform> transform, RigidBody.Type type) {
        this.transform = transform;
        this.velocity = new Vector2();
        this.type = type;
    }

    void update(float time) {
        if(type != Type.STATIC)
            this.transform.value.position.add(Vector2.mul(velocity, time));
    }

    public void addForce(Vector2 force, ForceType forceType) {
        if(type == Type.STATIC)
            return;
        switch(forceType) {
            case IMPULSE:
                this.velocity.add(force);
                break;
        }
    }

    public void translate(Vector2 translation) {
        this.transform.value.position.add(translation);
    }

    public RigidBody addCollider(Collider collider) {
        this.colliders.add(collider);
        return this;
    }

    List<Collider> getColliders() {
        return colliders;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Type getType() {
        return type;
    }

    public RigidBody setKinematic(boolean kinematic) {
        isKinematic = kinematic;
        return this;
    }

    public RigidBody setMass(float mass) {
        this.mass = mass;
        return this;
    }

    public RigidBody setRestitution(float restitution) {
        this.restitution = restitution;
        return this;
    }

    public RigidBody setFriction(float friction) {
        this.friction = friction;
        return this;
    }

    public RigidBody setDensity(float density) {
        this.density = density;
        return this;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public enum Type {
        STATIC, DYNAMIC;
    }
}
