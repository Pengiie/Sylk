package dev.penguinz.Sylk.util;

import dev.penguinz.Sylk.util.maths.Transform;
import dev.penguinz.Sylk.util.maths.Vector2;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class MatrixUtils {

    public static Matrix4f createTransformMatrix(Transform transform) {
        Matrix4f matrix = new Matrix4f();
        matrix.translate(transform.position.x, transform.position.y, 0);
        matrix.rotateAroundLocal(new Quaternionf().
                fromAxisAngleRad(0,0, 1, transform.rotation),
                transform.position.x + transform.rotationAnchor.x,
                transform.position.y + transform.rotationAnchor.y, 0);
        matrix.scale(transform.getScale().x, transform.getScale().y, 1);
        return matrix;
    }

    public static Matrix4f createTransformMatrix(Vector2 position, Vector2 scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.translate(position.x, position.y, 0);
        matrix.scale(scale.x, scale.y, 1);
        return matrix;
    }

    public static Matrix4f createViewMatrix(Transform transform) {
        Matrix4f matrix = new Matrix4f();
        matrix.translate(-transform.position.x, -transform.position.y, 0);
        matrix.rotateY((float) Math.toRadians(-transform.rotation));
        return matrix;
    }

}
