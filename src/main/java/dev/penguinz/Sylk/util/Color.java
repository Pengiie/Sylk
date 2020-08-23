package dev.penguinz.Sylk.util;

import org.joml.Vector4f;

public class Color {

    public static final Color white = new Color(1, 1, 1, 1);
    public static final Color black = new Color(0, 0, 0, 1);

    public float r, g, b, a;

    public Color(Color color) {
        this.r = color.r;
        this.g = color.g;
        this.b = color.b;
        this.a = color.a;
    }

    public Color(float r, float g, float b) {
        this(r, g, b, 1);
    }

    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Vector4f toVector() {
        return new Vector4f(r, g, b, a);
    }

}
