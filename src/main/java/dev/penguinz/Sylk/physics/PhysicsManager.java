package dev.penguinz.Sylk.physics;

import dev.penguinz.Sylk.Time;
import dev.penguinz.Sylk.util.maths.Vector2;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import java.util.ArrayList;
import java.util.List;

public class PhysicsManager {

    private final World world;

    private final List<RigidBody> rigidBodies = new ArrayList<>();

    public PhysicsManager() {
        this.world = new World(new Vec2(0, -9.8f));
        this.world.setAllowSleep(true);
    }

    public void step() {
        world.step(Time.deltaTime(), 6, 2);
        for (RigidBody rigidBody : rigidBodies) {
            rigidBody.update();
        }
    }

    Body registerRigidBody(RigidBody rigidBody) {
        this.rigidBodies.add(rigidBody);
        return world.createBody(rigidBody.bodyDef);
    }

    public void setGravity(Vector2 gravity) {
        this.world.setGravity(new Vec2(gravity.x, gravity.y));
    }

}
