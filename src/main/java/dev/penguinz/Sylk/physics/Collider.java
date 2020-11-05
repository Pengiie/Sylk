package dev.penguinz.Sylk.physics;

public interface Collider {

    boolean collideBoundingBox();

    default boolean isColliding(Collider other) {
        return false;
    }

}
