package dev.penguinz.Sylk.physics;

import dev.penguinz.Sylk.util.maths.Vector2;

public class CollisionData {

    public final boolean isColliding;
    public final Vector2 collisionNormal;
    public final float penetrationDepth;
    public RigidBody rigidBody;

    public CollisionData(boolean isColliding, Vector2 collisionNormal, float penetrationDepth) {
        this.isColliding = isColliding;
        this.collisionNormal = collisionNormal;
        this.penetrationDepth = penetrationDepth;
    }
}
