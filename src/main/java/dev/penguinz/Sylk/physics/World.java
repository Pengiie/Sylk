package dev.penguinz.Sylk.physics;

import dev.penguinz.Sylk.util.maths.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class World {

    private Vector2 gravity;
    private List<RigidBody> rigidBodies = new ArrayList<>();

    public World(float gravity) {
        this.gravity = new Vector2(gravity);
    }

    public World(Vector2 gravity) {
        this.gravity = gravity;
    }

    public void step() {
        HashMap<RigidBody, List<RigidBody>> collisions = new HashMap<>();

        for (RigidBody rigidBody : rigidBodies) {
            for (RigidBody other : rigidBodies) {
                boolean foundCollision = false;
                for (Collider collider : rigidBody.getColliders()) {
                    for (Collider otherCollider : other.getColliders()) {
                        if(collider.isColliding(otherCollider)) {
                            foundCollision = true;
                            break;
                        }
                    }
                    if(foundCollision)
                        break;
                }
                if(foundCollision) {
                    if(!collisions.containsKey(rigidBody)) {
                        collisions.put(rigidBody, new ArrayList<>());
                    }
                    collisions.get(rigidBody).add(other);
                }
            }
        }

        for (RigidBody rigidBody : collisions.keySet()) {
            for (RigidBody other : collisions.get(rigidBody)) {
                Vector2 relativeVelocity = Vector2.sub(other.getVelocity(), rigidBody.getVelocity());
                float restitution = Math.min(rigidBody.restitution, other.restitution);

            }
        }
    }

    public Vector2 getGravity() {
        return gravity;
    }

    public void setGravity(Vector2 gravity) {
        this.gravity = gravity;
    }

    public RigidBody addRigidBody(RigidBody rigidBody) {
        this.rigidBodies.add(rigidBody);
        return rigidBody;
    }
}
