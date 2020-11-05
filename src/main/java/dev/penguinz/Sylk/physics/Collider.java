package dev.penguinz.Sylk.physics;

public interface Collider {

    CollisionData isCollidingWithAABB(AABB other);

    default CollisionData isColliding(Collider other) {
        if(other instanceof AABB)
            return isCollidingWithAABB((AABB) other);
        return new CollisionData(false, null, 0);
    }

}
