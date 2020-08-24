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
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Vector4f toVector() {
        return new Vector4f(r, g, b, a);
    }

}
