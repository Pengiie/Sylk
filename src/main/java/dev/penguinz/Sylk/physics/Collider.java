package dev.penguinz.Sylk.physics;

import org.jbox2d.collision.shapes.Shape;

public class Collider {

    final Shape shape;

    public Collider(Shape shape) {
        this.shape = shape;
    }

}
