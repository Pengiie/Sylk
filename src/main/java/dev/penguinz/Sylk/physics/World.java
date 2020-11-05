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

    public void step(float time) {
        HashMap<RigidBody, ArrayList<CollisionData>> collisions = new HashMap<>();

        for (RigidBody rigidBody : rigidBodies) {
            rigidBody.addForce(Vector2.mul(gravity, time), ForceType.IMPULSE);
            rigidBody.update(time);
        }

        for (RigidBody rigidBody : rigidBodies) {
            if(rigidBody.isKinematic || rigidBody.getType().equals(RigidBody.Type.STATIC))
                continue;
            for (RigidBody other : rigidBodies) {
                if(other == rigidBody)
                    continue;
                if(other.isKinematic)
                    continue;
                boolean foundCollision = false;
                CollisionData collisionData = null;
                for (Collider collider : rigidBody.getColliders()) {
                    for (Collider otherCollider : other.getColliders()) {
                        collisionData = collider.isColliding(otherCollider);
                        if(collisionData.isColliding) {
                            foundCollision = true;
                            collisionData.rigidBody = other;
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
                    collisions.get(rigidBody).add(collisionData);
                }
            }
        }

        for (RigidBody rigidBody : collisions.keySet()) {
            for (CollisionData collisionData : collisions.get(rigidBody)) {
                RigidBody other = collisionData.rigidBody;

                Vector2 relativeVelocity = Vector2.sub(other.getVelocity(), rigidBody.getVelocity());

                float velocityAlongNormal = Vector2.dot(collisionData.collisionNormal, relativeVelocity);
                float restitution = Math.min(rigidBody.restitution, other.restitution);

                float j = velocityAlongNormal * -(1+restitution);
                j /= 1 / rigidBody.mass + 1 / other.mass;

                Vector2 impulse = Vector2.mul(collisionData.collisionNormal, j);
                rigidBody.addForce(Vector2.mul(impulse, -1 / rigidBody.mass), ForceType.IMPULSE);

                final float percent = 0.4f;
                final float slop = 0.107f;
                Vector2 correction = Vector2.mul(collisionData.collisionNormal, Math.max(collisionData.penetrationDepth - slop, 0.0f ) / (1/rigidBody.mass+ 1/other.mass) * percent);
                rigidBody.translate(Vector2.mul(correction, 1/rigidBody.mass));
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
