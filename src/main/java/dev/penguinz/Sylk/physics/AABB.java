package dev.penguinz.Sylk.physics;

import dev.penguinz.Sylk.util.maths.Transform;
import dev.penguinz.Sylk.util.maths.Vector2;

import java.util.Vector;

public class AABB implements Collider {
    private Vector2 min;
    private Vector2 max;

    public AABB(Transform transform) {
        this(transform.position, Vector2.add(transform.position, transform.getScale()));
    }

    public AABB(Vector2 min, Vector2 max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public CollisionData isCollidingWithAABB(AABB other) {
        boolean isCollidingX = max.x >= other.min.x &&
                other.max.x >= min.x;

        boolean isCollidingY = max.y >= other.min.y &&
                other.max.y >= min.y;

        Vector2 rawCenter = Vector2.sub(max, min).mul(0.5f);
        Vector2 center = Vector2.add(rawCenter, new Vector2(min));

        Vector2 rawOtherCenter = Vector2.sub(other.max, other.min).mul(0.5f);
        Vector2 otherCenter = Vector2.add(rawOtherCenter, other.min);

        Vector2 normal;
        float penetrationDepth = 0;
        Vector2 relativePosition = Vector2.sub(otherCenter, center);
        if(Math.abs(relativePosition.x) > Math.abs(relativePosition.y)) {
            if(relativePosition.x > 0)
                normal = new Vector2(-1, 0);
            else
                normal = new Vector2(1, 0);
            penetrationDepth = Math.abs(relativePosition.x) - (rawCenter.x + rawOtherCenter.x);
        } else {
            if(relativePosition.y > 0)
                normal = new Vector2(0, -1);
            else
                normal = new Vector2(0, 1);
            penetrationDepth = Math.abs(relativePosition.y) - (rawCenter.y + rawOtherCenter.y);
        }

        return new CollisionData(isCollidingX && isCollidingY, normal, Math.abs(penetrationDepth));
    }
}
