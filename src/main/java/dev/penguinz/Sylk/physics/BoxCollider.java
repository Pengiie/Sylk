package dev.penguinz.Sylk.physics;

import dev.penguinz.Sylk.util.RefContainer;
import dev.penguinz.Sylk.util.maths.Transform;
import dev.penguinz.Sylk.util.maths.Vector2;
import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

public class BoxCollider extends Collider {

    public BoxCollider(RefContainer<Transform> transform) {
        this(transform.value);
    }

    public BoxCollider(Transform transform) {
        this(transform.getScale(), new Vector2(transform.getScale().x, transform.getScale().y));
    }

    public BoxCollider(Vector2 size, Vector2 offset) {
        super(new PolygonShape());
        PolygonShape polygonShape = (PolygonShape) shape;
        polygonShape.setAsBox(size.x/2, size.y/2, new Vec2(0, size.y/2), 0);
    }
}
