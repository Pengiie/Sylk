package dev.penguinz.Sylk.animation.interpolators;

public interface Interpolator<T> {

    T interpolate(T from, T to, float progress);

}
