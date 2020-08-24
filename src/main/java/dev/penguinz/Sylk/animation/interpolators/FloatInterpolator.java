package dev.penguinz.Sylk.animation.interpolators;

import dev.penguinz.Sylk.util.InterpolationUtils;

public class FloatInterpolator implements Interpolator<Float> {

    @Override
    public Float interpolate(Float from, Float to, float progress) {
        return InterpolationUtils.interpolate(from, to, progress);
    }
}
