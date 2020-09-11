package dev.penguinz.Sylk.physics;

public class World {

    private float gravity;

    public World(float gravity) {
        this.gravity = gravity;
    }

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }
}
