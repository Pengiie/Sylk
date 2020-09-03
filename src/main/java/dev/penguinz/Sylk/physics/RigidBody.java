package dev.penguinz.Sylk.physics;

import dev.penguinz.Sylk.Application;
import dev.penguinz.Sylk.util.RefContainer;
import dev.penguinz.Sylk.util.maths.Transform;
import dev.penguinz.Sylk.util.maths.Vector2;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class RigidBody {

    private final RefContainer<Transform> transformRef;

    final BodyDef bodyDef;
    private final Body body;

    public RigidBody(RefContainer<Transform> transformRef, Type type, Collider collider) {
        this.transformRef = transformRef;

        this.bodyDef = new BodyDef();
        bodyDef.type = type.type;
        bodyDef.position.set(transformRef.value != null ? new Vec2(transformRef.value.position.x, transformRef.value.position.y) : new Vec2());
        bodyDef.angle = transformRef.value != null ? transformRef.value.rotation : 0;

        this.body = Application.getInstance().getPhysics().registerRigidBody(this);
        setCollider(collider);
        this.body.setFixedRotation(true);
    }

    public void applyForce(Vector2 force) {
        this.body.applyForceToCenter(new Vec2(force.x, force.y));
    }

    public void applyImpulseForce(Vector2 force) {
        this.body.applyLinearImpulse(new Vec2(force.x, force.y), body.getWorldCenter());
    }

    public RigidBody setCollider(Collider collider) {
        this.body.createFixture(collider.shape, 0);
        return this;
    }

    public RigidBody setDensity(float density) {
        this.body.getFixtureList().setDensity(density);
        return this;
    }

    public RigidBody setFriction(float friction) {
        this.body.getFixtureList().setFriction(friction);
        return this;
    }

    public RigidBody setRestitution(float restitution) {
        this.body.getFixtureList().setRestitution(restitution);
        return this;
    }

    private Fixture getFixture() {
        return this.body.getFixtureList();
    }

    void update() {
        if(body != null && transformRef.value != null) {
            transformRef.value.position.x = body.getPosition().x;
            transformRef.value.position.y = body.getPosition().y;
            transformRef.value.rotation = body.getAngle();
        }
    }

    public enum Type {
        STATIC(BodyType.STATIC), KINEMATIC(BodyType.KINEMATIC), DYNAMIC(BodyType.DYNAMIC), TRIGGER(BodyType.STATIC);

        final BodyType type;

        Type(BodyType type) {
            this.type = type;
        }
    }

}
