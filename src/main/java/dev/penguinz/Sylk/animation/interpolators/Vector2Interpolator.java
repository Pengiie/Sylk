package dev.penguinz.Sylk.animation.interpolators;

import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.InterpolationUtils;
import dev.penguinz.Sylk.util.maths.Vector2;

public class Vector2Interpolator implements Interpolator<Vector2> {

    @Override
    public Vector2 interpolate(Vector2 from, Vector2 to, float progress) {
        return new Vector2(
                InterpolationUtils.interpolate(from.x, to.x, progress),
                InterpolationUtils.interpolate(from.y, to.y, progress)
        );
    }

}
