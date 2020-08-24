package dev.penguinz.Sylk.util;

public class InterpolationUtils {

    public static float interpolate(float from, float to, float progress) {
        return from + (to-from)*progress;
    }

}
