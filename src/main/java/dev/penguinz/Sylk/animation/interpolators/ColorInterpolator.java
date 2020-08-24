package dev.penguinz.Sylk.animation.interpolators;

import dev.penguinz.Sylk.util.Color;
import dev.penguinz.Sylk.util.InterpolationUtils;

public class ColorInterpolator implements Interpolator<Color> {

    @Override
    public Color interpolate(Color from, Color to, float progress) {
        return new Color(
                InterpolationUtils.interpolate(from.r, to.r, progress),
                InterpolationUtils.interpolate(from.g, to.g, progress),
                InterpolationUtils.interpolate(from.b, to.b, progress),
                InterpolationUtils.interpolate(from.a, to.a, progress)
        );
    }

}
