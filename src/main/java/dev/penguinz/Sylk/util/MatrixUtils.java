package dev.penguinz.Sylk.util;

import dev.penguinz.Sylk.util.maths.Transform;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Arrays;

public class MatrixUtils {

    public static Matrix4f createTransformMatrix(Transform transform) {
        Matrix4f matrix = new Matrix4f();
        matrix.translate(transform.position.x, transform.position.y, 0);
        matrix.rotateAroundLocal(new Quaternionf().fromAxisAngleDeg(0,0, 1, transform.rotation), transform.rotationAnchor.x, transform.rotationAnchor.y, 0);
        matrix.scale(transform.getScale().x, transform.getScale().y, 1);
        return matrix;
    }

    public static Matrix4f createViewMatrix(Transform transform) {
        Matrix4f matrix = new Matrix4f();
        matrix.translate(-transform.position.x, -transform.position.y, 0);
        matrix.rotateY((float) Math.toRadians(-transform.rotation));
        return matrix;
    }

}
