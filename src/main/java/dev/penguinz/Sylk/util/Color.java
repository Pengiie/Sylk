package dev.penguinz.Sylk.util;

import org.joml.Vector4f;

/**
 * A four component red, green, blue, and alpha color.
 */
public class Color {

    public static final Color white = new Color(1, 1, 1, 1);
    public static final Color black = new Color(0, 0, 0, 1);

    public float r, g, b, a;

    /**
     * Creates a new instance from another color.
     * @param color the color to copy.
     */
    public Color(Color color) {
        this.r = color.r;
        this.g = color.g;
        this.b = color.b;
        this.a = color.a;
    }

    /**
     * Creates a new color with the specified rgb values and an alpha of 1.
     * @param r red component.
     * @param g green component.
     * @param b blue component.
     */
    public Color(float r, float g, float b) {
        this(r, g, b, 1);
    }

    /**
     * Creates a new color with the specified rgb values.
     * @param r red component.
     * @param g green component.
     * @param b blue component.
     * @param a alpha component.
     */
    public Color(float r, float g, float b, float a) {
        this.r = r > 1 ? r / 255 : r;
        this.g = g > 1 ? g / 255 : g;
        this.b = b > 1 ? b / 255 : b;
        this.a = a > 1 ? a / 255 : a;
    }

    public boolean equals(Color other) {
        return this.r == other.r && this.g == other.g && this.b == other.b && this.a == other.a;
    }

    public Vector4f toVector() {
        return new Vector4f(r, g, b, a);
    }

}
